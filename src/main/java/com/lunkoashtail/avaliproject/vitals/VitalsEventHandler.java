package com.lunkoashtail.avaliproject.vitals;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.Config;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.network.VitalsSyncPayload;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.vitals.Vitals.Stat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import com.lunkoashtail.avaliproject.component.FluidAmount;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.item.custom.CanteenItem;
import com.lunkoashtail.avaliproject.item.custom.MedicalItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.CanContinueSleepingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = AvaliProject.MOD_ID)
public class VitalsEventHandler {

    private static final int TICK_INTERVAL = 20;
    private static final Set<UUID> PILL_SLEEPERS = ConcurrentHashMap.newKeySet();
    private static final Set<UUID> FORCED_CRAWL_SERVER = ConcurrentHashMap.newKeySet();
    private static final Set<UUID> FORCED_CRAWL_CLIENT = ConcurrentHashMap.newKeySet();

    public static boolean isActive(Player player) {
        return Config.realisticVitals && player.getData(ModAttachments.SPECIES) == Species.EXPIE
                && !player.isCreative() && !player.isSpectator();
    }

    public static boolean isIncapacitated(Player player) {
        return isActive(player) && player.getData(ModAttachments.VITALS).get(Stat.CONSCIOUSNESS) < VitalsLogic.INCAPACITATED;
    }

    public static void sync(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, VitalsSyncPayload.from(player));
    }

    public static void startPillSleep(ServerPlayer player) {
        PILL_SLEEPERS.add(player.getUUID());
        player.startSleeping(player.blockPosition());
        player.serverLevel().updateSleepingPlayerList();
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        updateForcedPose(player);
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (!serverPlayer.isAlive() || serverPlayer.isDeadOrDying()) return;
        if (serverPlayer.tickCount % TICK_INTERVAL != 0) return;
        if (!isActive(serverPlayer)) return;

        VitalsLogic.tick(serverPlayer);
        if (serverPlayer.isAlive()) sync(serverPlayer);
    }

    private static void updateForcedPose(Player player) {
        Set<UUID> forced = player.level().isClientSide() ? FORCED_CRAWL_CLIENT : FORCED_CRAWL_SERVER;
        boolean down = isIncapacitated(player) && !player.isSleeping() && !player.isPassenger();
        if (down) {
            forced.add(player.getUUID());
            player.setForcedPose(Pose.SWIMMING);
        } else if (forced.remove(player.getUUID())) {
            player.setForcedPose(null);
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!isActive(player)) return;
        float damage = event.getNewDamage();
        if (damage <= 0f) return;

        DamageSource source = event.getSource();
        RandomSource random = player.getRandom();
        Limb limb = pickLimb(source, random);
        LimbHealth limbs = player.getData(ModAttachments.LIMB_HEALTH);
        boolean penetrating = source.is(DamageTypeTags.IS_PROJECTILE) || source.is(DamageTypeTags.IS_EXPLOSION);
        limbs.addSkin(limb, -damage * (penetrating ? 5f : 3f));
        limbs.addMuscle(limb, -damage * (source.is(DamageTypeTags.IS_FALL) ? 3.5f : 2.5f));
        if (limb == Limb.BACK && penetrating && damage >= 5f) {
            player.getData(ModAttachments.VITALS).add(Stat.HEMOTHORAX, damage * 2f);
        }
        if (limb == Limb.HEAD && damage >= 8f) {
            player.getData(ModAttachments.VITALS).add(Stat.CONSCIOUSNESS, -damage * 3f);
        }
    }

    private static Limb pickLimb(DamageSource source, RandomSource random) {
        if (source.is(DamageTypeTags.IS_FALL)) return random.nextBoolean() ? Limb.LEFT_LEG : Limb.RIGHT_LEG;
        float roll = random.nextFloat();
        if (roll < 0.1f) return Limb.HEAD;
        if (roll < 0.4f) return Limb.BACK;
        if (roll < 0.55f) return Limb.LEFT_ARM;
        if (roll < 0.7f) return Limb.RIGHT_ARM;
        if (roll < 0.85f) return Limb.LEFT_LEG;
        return Limb.RIGHT_LEG;
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        player.getData(ModAttachments.VITALS).reset();
        player.getData(ModAttachments.LIMB_HEALTH).clear();
        PILL_SLEEPERS.remove(player.getUUID());
        FORCED_CRAWL_SERVER.remove(player.getUUID());
        player.setForcedPose(null);
        sync(player);
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) sync(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        FORCED_CRAWL_SERVER.remove(player.getUUID());
        player.setForcedPose(null);
        player.setPose(net.minecraft.world.entity.Pose.STANDING);
        sync(player);
    }

    @SubscribeEvent
    public static void onFinishUsing(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !isActive(player)) return;
        ItemStack stack = event.getItem();
        if (stack.getItem() instanceof MedicalItem || stack.getItem() instanceof CanteenItem) return;
        float thirst = thirstFor(stack);
        if (thirst <= 0f) return;
        player.getData(ModAttachments.VITALS).add(Stat.THIRST, thirst);
        sync(player);
    }

    private static float thirstFor(ItemStack stack) {
        if (stack.is(ModItems.WATER_BOTTLE.get())) {
            FluidAmount amount = stack.get(ModDataComponents.FLUID_AMOUNT);
            return amount != null ? amount.amountMl() / 10f : 20f;
        }
        if (stack.is(ModItems.APPLE_JUICE.get())) return 30f;
        if (stack.is(Items.POTION)) return 25f;
        if (stack.is(Items.MILK_BUCKET)) return 20f;
        if (stack.is(Items.HONEY_BOTTLE)) return 10f;
        if (stack.is(Items.MELON_SLICE)) return 6f;
        if (stack.getUseAnimation() == UseAnim.DRINK) return 15f;
        return 0f;
    }

    @SubscribeEvent
    public static void onCanContinueSleeping(CanContinueSleepingEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!PILL_SLEEPERS.contains(player.getUUID())) return;
        if (event.getProblem() == Player.BedSleepingProblem.NOT_POSSIBLE_HERE) event.setContinueSleeping(true);
    }

    @SubscribeEvent
    public static void onWakeUp(PlayerWakeUpEvent event) {
        PILL_SLEEPERS.remove(event.getEntity().getUUID());
    }

    private static void blockIfIncapacitated(Player player, ICancellableEvent event) {
        if (isIncapacitated(player)) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        blockIfIncapacitated(event.getEntity(), event);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        blockIfIncapacitated(event.getEntity(), event);
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        blockIfIncapacitated(event.getEntity(), event);
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        blockIfIncapacitated(event.getEntity(), event);
    }

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        blockIfIncapacitated(event.getEntity(), event);
    }
}
