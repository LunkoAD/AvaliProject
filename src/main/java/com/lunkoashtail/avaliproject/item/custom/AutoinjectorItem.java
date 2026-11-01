package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.component.FluidAmount;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import com.lunkoashtail.avaliproject.vitals.MedicalAction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AutoinjectorItem extends MedicalItem {

    public static final float CAPACITY_ML = 30f;

    public AutoinjectorItem(Properties properties) {
        super(MedicalAction.MORPHINE_AUTOINJECTOR, properties);
    }

    public static float contentsOf(ItemStack stack) {
        FluidAmount amount = stack.get(ModDataComponents.FLUID_AMOUNT);
        return amount != null ? amount.amountMl() : 0f;
    }

    private static boolean empty(Player player, ItemStack stack) {
        if (contentsOf(stack) > 0.5f) return false;
        if (player.level().isClientSide()) {
            player.displayClientMessage(Component.literal("The autoinjector is empty. Right-click a morphine vial to fill it."), true);
        }
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (empty(player, stack)) return InteractionResultHolder.fail(stack);
        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (empty(player, stack)) return InteractionResult.FAIL;
        return super.interactLivingEntity(stack, player, target, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        float ml = contentsOf(stack);
        tooltip.add(ml > 0.5f
                ? Component.literal("Morphine: " + (int) ml + " / " + (int) CAPACITY_ML + " mL").withStyle(ChatFormatting.AQUA)
                : Component.literal("Empty").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
