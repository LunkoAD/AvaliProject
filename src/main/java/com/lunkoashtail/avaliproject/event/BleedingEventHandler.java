package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.BleedingTier;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.network.LimbDataSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Server-side handler for:
 *  1. Opening wounds (applying bleed) when a player takes damage.
 *  2. Draining health each tick based on per-limb BleedingTier damage values.
 *
 * ── Wound application ───────────────────────────────────────────────────────
 *   Fall damage  → legs ± back
 *     chance = FALL_BASE + damage/40     (20% at 2 HP → ~45% at 10 HP)
 *   Combat damage → random limb
 *     chance = COMBAT_BASE + damage/30   (25% at 2 HP → ~58% at 10 HP)
 *   Severity scales with incoming damage, clamped to LimbData.MAX_BLEED (100).
 *
 * ── Health drain (tier-based) ────────────────────────────────────────────────
 *   Every BLEED_TICK_INTERVAL ticks the server sums the damagePerInterval value
 *   for each limb's current BleedingTier and calls player.hurt() once.
 *   Multiple bleeding limbs stack additively.
 *
 *   Tier      │ Bleed range │ HP per 2 s
 *   ──────────┼─────────────┼──────────
 *   Minor     │    1 – 25   │  0.5
 *   Bleeding  │   26 – 50   │  1.5
 *   Heavy     │   51 – 75   │  3.0
 *   Catast.   │   76 – 100  │  5.0
 *
 * ── Expanding ────────────────────────────────────────────────────────────────
 *   New effects (fractures, pain, infection) go in LimbData + a new check here.
 */
@EventBusSubscriber(modid = AvaliProject.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class BleedingEventHandler {

    private static final float MIN_DAMAGE_FOR_BLEED     = 2.0f;
    private static final float FALL_BLEED_CHANCE_BASE   = 0.20f;
    private static final float COMBAT_BLEED_CHANCE_BASE = 0.25f;

    /**
     * Tick interval for bleed damage (server ticks).
     * 40 ticks = 2 seconds at 20 TPS.
     */
    private static final int BLEED_TICK_INTERVAL = 40;

    // -------------------------------------------------------------------------
    // Damage → wound application
    // -------------------------------------------------------------------------

    /**
     * Fires after final post-armor damage is determined, before HP reduction.
     * We use the reduced value so armoured players get lower bleed chance.
     *
     * NOTE: Uses LivingDamageEvent.Pre (NeoForge 21.x).
     * If your build uses a different name, change the event type here only.
     */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        float damage = event.getNewDamage();
        if (damage < MIN_DAMAGE_FOR_BLEED) return;

        DamageSource source = event.getSource();

        if (source.is(DamageTypes.FALL)) {
            handleFallBleed(player, damage);
        } else if (source.getEntity() != null) {
            // Mob attack, player attack, or projectile
            handleCombatBleed(player, damage);
        }
    }

    /**
     * Fall damage: primarily wounds one or both legs; severe falls also wound the back.
     *
     * Severity = damage × 1.5 + random(0–7), clamped by LimbData.
     */
    private static void handleFallBleed(ServerPlayer player, float damage) {
        float chance = FALL_BLEED_CHANCE_BASE + (damage / 40f);
        if (player.getRandom().nextFloat() < chance) {
            Limb leg = player.getRandom().nextBoolean() ? Limb.LEFT_LEG : Limb.RIGHT_LEG;
            int severity = (int) (damage * 1.5f) + player.getRandom().nextInt(8);
            applyBleedAndSync(player, leg, severity);
        }

        // Severe fall (> 8 HP after armor) has a secondary 30% chance to bruise the back
        if (damage >= 8f && player.getRandom().nextFloat() < 0.30f) {
            int severity = (int) damage + player.getRandom().nextInt(5);
            applyBleedAndSync(player, Limb.BACK, severity);
        }
    }

    /**
     * Combat damage: wounds a random limb.
     *
     * Severity = damage × 1.2 + random(0–7).
     */
    private static void handleCombatBleed(ServerPlayer player, float damage) {
        float chance = COMBAT_BLEED_CHANCE_BASE + (damage / 30f);
        if (player.getRandom().nextFloat() < chance) {
            Limb[] limbs = Limb.values();
            Limb limb = limbs[player.getRandom().nextInt(limbs.length)];
            int severity = (int) (damage * 1.2f) + player.getRandom().nextInt(8);
            applyBleedAndSync(player, limb, severity);
        }
    }

    /**
     * Applies bleed to the given limb and immediately syncs LimbData to the client.
     */
    private static void applyBleedAndSync(ServerPlayer player, Limb limb, int amount) {
        LimbData data = player.getData(ModAttachments.LIMB_DATA);
        data.addBleed(limb, amount);
        PacketDistributor.sendToPlayer(player, LimbDataSyncPayload.from(data));
    }

    // -------------------------------------------------------------------------
    // Bleeding health drain — tier-based per limb
    // -------------------------------------------------------------------------

    /**
     * Runs every BLEED_TICK_INTERVAL server ticks.
     *
     * For each limb, determines the BleedingTier from its current bleed value
     * and adds that tier's damagePerInterval to the total.  Multiple limbs stack.
     *
     * Examples:
     *   One MINOR limb (bleed 15)         →  0.5 HP per 2 s
     *   One CATASTROPHIC limb (bleed 90)  →  5.0 HP per 2 s
     *   MINOR + HEAVY simultaneously      →  3.5 HP per 2 s
     *
     * Damage is generic (bypasses armor) but still consumed by absorption hearts.
     */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % BLEED_TICK_INTERVAL != 0) return;

        LimbData data = player.getData(ModAttachments.LIMB_DATA);
        if (!data.isAnyBleeding()) return;

        // Sum damagePerInterval across all actively bleeding limbs
        float totalDamage = 0f;
        for (Limb limb : Limb.values()) {
            BleedingTier tier = BleedingTier.fromBleedValue(data.getBleed(limb));
            if (tier != null) {
                totalDamage += tier.damagePerInterval;
            }
        }

        if (totalDamage > 0f) {
            player.hurt(player.damageSources().generic(), totalDamage);
        }

        // ── Optional: slow natural recovery ──────────────────────────────────
        // Un-comment to let wounds heal very slowly on their own over time.
        // A bandage is still needed to stop severe bleeding before it kills.
        //
        // for (Limb limb : Limb.values()) {
        //     if (data.getBleed(limb) > 0) data.reduceBleed(limb, 1);
        // }
        // PacketDistributor.sendToPlayer(player, LimbDataSyncPayload.from(data));
    }
}
