package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.BleedingTier;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.limb.PainData;
import com.lunkoashtail.avaliproject.network.LimbConditionsSyncPayload;
import com.lunkoashtail.avaliproject.network.LimbDataSyncPayload;
import com.lunkoashtail.avaliproject.network.PainSyncPayload;
import com.lunkoashtail.avaliproject.network.SpeciesSyncPayload;
import com.lunkoashtail.avaliproject.species.Species;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;


























@EventBusSubscriber(modid = AvaliProject.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class BleedingEventHandler {

    private static final float MIN_DAMAGE_FOR_BLEED     = 2.0f;
    private static final float COMBAT_BLEED_CHANCE_BASE = 0.25f;
    private static final float FALL_BLEED_BLOCKS        = 7.0f;

    



    private static final int BLEED_TICK_INTERVAL = 40;

    private static final float PAIN_PER_DAMAGE_POINT = 1.2f;
    private static final int PAIN_TICK_INTERVAL = 40;
    private static final float PAIN_DECAY_PER_INTERVAL = 0.6f;
    private static final float HIGH_PAIN_SLOWDOWN_THRESHOLD = 50f;
    private static final float SEVERE_PAIN_SLOWDOWN_THRESHOLD = 80f;

    
    
    

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        PacketDistributor.sendToPlayer(player, LimbDataSyncPayload.from(player.getData(ModAttachments.LIMB_DATA)));
        PacketDistributor.sendToPlayer(player, PainSyncPayload.from(player.getData(ModAttachments.PAIN_DATA)));
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        PacketDistributor.sendToPlayer(player, new SpeciesSyncPayload(player.getData(ModAttachments.SPECIES).ordinal()));
        PacketDistributor.sendToPlayer(player, LimbDataSyncPayload.from(player.getData(ModAttachments.LIMB_DATA)));
        PacketDistributor.sendToPlayer(player, PainSyncPayload.from(player.getData(ModAttachments.PAIN_DATA)));
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        player.getData(ModAttachments.LIMB_DATA).clear();
        player.getData(ModAttachments.PAIN_DATA).clear();
        player.getData(ModAttachments.LIMB_CONDITIONS).clear();

        PacketDistributor.sendToPlayer(player, LimbDataSyncPayload.from(player.getData(ModAttachments.LIMB_DATA)));
        PacketDistributor.sendToPlayer(player, PainSyncPayload.from(player.getData(ModAttachments.PAIN_DATA)));
        PacketDistributor.sendToPlayer(player, LimbConditionsSyncPayload.from(player.getData(ModAttachments.LIMB_CONDITIONS)));
    }

    
    
    

    



    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.getData(ModAttachments.SPECIES) != Species.EXPIE) return;
        if (event.getDistance() <= FALL_BLEED_BLOCKS) return;

        Limb[] limbs = Limb.values();
        Limb limb = limbs[player.getRandom().nextInt(limbs.length)];
        int severity = (int) ((event.getDistance() - FALL_BLEED_BLOCKS) * 3f) + player.getRandom().nextInt(6);
        applyBleedAndSync(player, limb, severity);
    }

    



    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.getData(ModAttachments.SPECIES) != Species.EXPIE) return;

        addPain(player, event.getNewDamage() * PAIN_PER_DAMAGE_POINT);

        if (player.getData(ModAttachments.SPECIES) != Species.EXPIE) return;

        float damage = event.getNewDamage();
        if (damage < MIN_DAMAGE_FOR_BLEED) return;

        DamageSource source = event.getSource();
        if (source.getEntity() != null) {
            handleCombatBleed(player, damage);
        }
    }

    private static void handleCombatBleed(ServerPlayer player, float damage) {
        float chance = COMBAT_BLEED_CHANCE_BASE + (damage / 30f);
        if (player.getRandom().nextFloat() < chance) {
            Limb[] limbs = Limb.values();
            Limb limb = limbs[player.getRandom().nextInt(limbs.length)];
            int severity = (int) (damage * 1.2f) + player.getRandom().nextInt(8);
            applyBleedAndSync(player, limb, severity);
        }
    }

    


    private static void applyBleedAndSync(ServerPlayer player, Limb limb, int amount) {
        LimbData data = player.getData(ModAttachments.LIMB_DATA);
        data.addBleed(limb, amount);
        PacketDistributor.sendToPlayer(player, LimbDataSyncPayload.from(data));
    }

    
    
    

    












    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!player.isAlive() || player.isDeadOrDying()) return;
        if (player.getData(ModAttachments.SPECIES) != Species.EXPIE) return;
        if (player.tickCount % BLEED_TICK_INTERVAL != 0) return;

        LimbData data = player.getData(ModAttachments.LIMB_DATA);
        if (!data.isAnyBleeding()) return;

        
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

        
        
        
        
        
        
        
        
    }


    private static void addPain(ServerPlayer player, float amount) {
        if (amount <= 0f) return;
        PainData pain = player.getData(ModAttachments.PAIN_DATA);
        pain.add(amount);
        PacketDistributor.sendToPlayer(player, PainSyncPayload.from(pain));
    }

    @SubscribeEvent
    public static void onPlayerTickPain(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!player.isAlive() || player.isDeadOrDying()) return;
        if (player.tickCount % PAIN_TICK_INTERVAL != 0) return;

        PainData pain = player.getData(ModAttachments.PAIN_DATA);
        if (pain.get() <= 0f) return;

        pain.add(-PAIN_DECAY_PER_INTERVAL);

        if (pain.get() >= SEVERE_PAIN_SLOWDOWN_THRESHOLD) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, PAIN_TICK_INTERVAL + 5, 1, false, false));
        } else if (pain.get() >= HIGH_PAIN_SLOWDOWN_THRESHOLD) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, PAIN_TICK_INTERVAL + 5, 0, false, false));
        }

        PacketDistributor.sendToPlayer(player, PainSyncPayload.from(pain));
    }
}
