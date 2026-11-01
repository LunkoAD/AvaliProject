package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.component.CanteenContents;
import com.lunkoashtail.avaliproject.component.FluidEntry;
import com.lunkoashtail.avaliproject.component.FluidType;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.vitals.Radiation;
import com.lunkoashtail.avaliproject.vitals.Vitals;
import com.lunkoashtail.avaliproject.vitals.VitalsEventHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CanteenItem extends Item {

    public static final int FILL_DURATION_TICKS = 40;
    public static final int DRINK_DURATION_TICKS = 32;
    public static final float SIP_ML = 50f;
    public static final float THIRST_PER_SIP = 15f;
    public static final float RADIATION_PER_SIP = 2f;
    private static final float RAW_WATER_SICKNESS_CHANCE = 0.15f;
    private static final FluidType[] DRINKABLE = {FluidType.BOILED_WATER, FluidType.WATER, FluidType.IRRADIATED_WATER};

    public CanteenItem() {
        super(new Item.Properties().stacksTo(1));
    }

    private static BlockHitResult findWaterSource(Level level, Player player) {
        return getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
    }

    private static boolean isWaterSource(Level level, BlockHitResult hit) {
        if (hit.getType() != HitResult.Type.BLOCK) return false;
        FluidState fluidState = level.getFluidState(hit.getBlockPos());
        return fluidState.is(FluidTags.WATER) && fluidState.isSource();
    }

    @Nullable
    private static FluidType drinkableFluid(ItemStack stack) {
        CanteenContents contents = stack.getOrDefault(ModDataComponents.CANTEEN_CONTENTS, CanteenContents.EMPTY);
        for (FluidType type : DRINKABLE) {
            if (contents.get(type) > 0f) return type;
        }
        return null;
    }

    private static boolean isFilling(Level level, Player player) {
        return isWaterSource(level, findWaterSource(level, player));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CanteenContents contents = stack.getOrDefault(ModDataComponents.CANTEEN_CONTENTS, CanteenContents.EMPTY);

        if (isFilling(level, player)) {
            if (contents.totalMl() >= CanteenContents.CAPACITY_ML) return InteractionResultHolder.pass(stack);
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
        if (drinkableFluid(stack) != null) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player && isFilling(player.level(), player)) return FILL_DURATION_TICKS;
        return DRINK_DURATION_TICKS;
    }

    private static FluidType fillType(Level level, BlockHitResult hit) {
        return Radiation.isContaminatedWater(level, hit.getBlockPos()) ? FluidType.IRRADIATED_WATER : FluidType.WATER;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide() || !(livingEntity instanceof Player player)) return;
        BlockHitResult hit = findWaterSource(level, player);
        if (!isWaterSource(level, hit)) return;

        int elapsed = FILL_DURATION_TICKS - remainingUseDuration;
        CanteenContents contents = stack.getOrDefault(ModDataComponents.CANTEEN_CONTENTS, CanteenContents.EMPTY);
        float room = CanteenContents.CAPACITY_ML - contents.totalMl();
        float step = CanteenContents.CAPACITY_ML / FILL_DURATION_TICKS;
        if (room > 0 && elapsed > 0) {
            stack.set(ModDataComponents.CANTEEN_CONTENTS, contents.withAdded(fillType(level, hit), Math.min(room, step)));
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (level.isClientSide() || !(entity instanceof Player player)) return stack;
        BlockHitResult hit = findWaterSource(level, player);
        if (isWaterSource(level, hit)) {
            CanteenContents contents = stack.getOrDefault(ModDataComponents.CANTEEN_CONTENTS, CanteenContents.EMPTY);
            float room = CanteenContents.CAPACITY_ML - contents.totalMl();
            if (room > 0) stack.set(ModDataComponents.CANTEEN_CONTENTS, contents.withAdded(fillType(level, hit), room));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
            return stack;
        }

        FluidType type = drinkableFluid(stack);
        if (type == null) return stack;
        CanteenContents contents = stack.getOrDefault(ModDataComponents.CANTEEN_CONTENTS, CanteenContents.EMPTY);
        float sip = Math.min(SIP_ML, contents.get(type));
        stack.set(ModDataComponents.CANTEEN_CONTENTS, contents.withRemoved(type, sip));
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0f, 1.0f);

        if (player instanceof ServerPlayer serverPlayer && VitalsEventHandler.isActive(serverPlayer)) {
            Vitals vitals = serverPlayer.getData(ModAttachments.VITALS);
            float scale = sip / SIP_ML;
            vitals.add(Vitals.Stat.THIRST, THIRST_PER_SIP * scale);
            if (type == FluidType.IRRADIATED_WATER) {
                vitals.add(Vitals.Stat.RADIATION, RADIATION_PER_SIP * scale);
            } else if (type == FluidType.WATER && level.getRandom().nextFloat() < RAW_WATER_SICKNESS_CHANCE) {
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.HUNGER, 400, 0));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 0));
            }
            VitalsEventHandler.sync(serverPlayer);
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        CanteenContents contents = stack.get(ModDataComponents.CANTEEN_CONTENTS);
        if (contents == null || contents.isEmpty()) {
            tooltip.add(Component.literal("Empty").withStyle(ChatFormatting.GRAY));
        } else {
            for (FluidEntry entry : contents.fluids()) {
                tooltip.add(Component.empty()
                        .append(entry.type().displayName())
                        .append(Component.literal(": " + (int) entry.amountMl() + " mL"))
                        .withStyle(entry.type() == FluidType.IRRADIATED_WATER ? ChatFormatting.GREEN : ChatFormatting.AQUA));
            }
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
