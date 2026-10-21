package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.compat.origins.OriginsCompat;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.server.level.ServerPlayer;
import com.lunkoashtail.avaliproject.network.SpeciesSyncPayload;
import com.lunkoashtail.avaliproject.species.SpeciesUtil;

@EventBusSubscriber(modid = AvaliProject.MOD_ID)
public class SpeciesContentEvents {
    private static final ResourceLocation MANOKIT_SWIM = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "manokit_swim");
    private static final int UPDATE_INTERVAL = 10;
    private static final double MANOKIT_SWIM_BONUS = 0.35;
    private static final float WICKERBEAST_EXTRA_CRYSTAL_CHANCE = 0.25F;

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getEntity() instanceof ServerPlayer tracker && event.getTarget() instanceof Player target) {
            PacketDistributor.sendToPlayer(tracker, SpeciesSyncPayload.of(target));
        }
    }

    @SubscribeEvent
    public static void onChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            SpeciesUtil.sync(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide() || player.tickCount % UPDATE_INTERVAL != 0) return;

        Species species = player.getData(ModAttachments.SPECIES);
        boolean javaStats = !OriginsCompat.isActive();
        toggleModifier(player, NeoForgeMod.SWIM_SPEED, MANOKIT_SWIM, MANOKIT_SWIM_BONUS,
                javaStats && species == Species.MANOKIT);

    }

    @SubscribeEvent
    public static void onBreathe(LivingBreatheEvent event) {
        if (event.getEntity() instanceof Player player
                && player.getData(ModAttachments.SPECIES) == Species.MANOKIT
                && player.isEyeInFluidType(NeoForgeMod.WATER_TYPE.value())) {
            event.setCanBreathe(true);
        }
    }

    private static void toggleModifier(Player player, Holder<Attribute> attribute, ResourceLocation id, double amount, boolean active) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) return;
        if (active) {
            if (!instance.hasModifier(id)) {
                instance.addTransientModifier(new AttributeModifier(id, amount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
        } else if (instance.hasModifier(id)) {
            instance.removeModifier(id);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        BlockState state = event.getState();
        if (!(event.getLevel() instanceof ServerLevel level) || player.isCreative()) return;
        if (!state.is(ModBlocks.CRYSTALITHIUM_ORE.get()) && !state.is(ModBlocks.CRYSTALITHIUM_DEEPSLATE_ORE.get())) return;
        if (player.getData(ModAttachments.SPECIES) != Species.WICKERBEAST || !player.hasCorrectToolForDrops(state, level, event.getPos())) return;

        ItemStack tool = player.getMainHandItem();
        var silkTouch = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
        if (tool.getEnchantmentLevel(silkTouch) > 0) return;

        if (level.getRandom().nextFloat() < WICKERBEAST_EXTRA_CRYSTAL_CHANCE) {
            Block.popResource(level, event.getPos(), new ItemStack(ModItems.CRYSTALITHIUM_CRYSTAL.get()));
        }
    }
}
