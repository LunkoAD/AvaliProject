package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class CoconutItem extends ItemNameBlockItem {
    public CoconutItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null && holdsAxeInOtherHand(player, context.getHand())) {
            return open(context.getLevel(), player, context.getHand()).getResult();
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!holdsAxeInOtherHand(player, hand)) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        return open(level, player, hand);
    }

    private static InteractionHand otherHand(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
    }

    private static boolean holdsAxeInOtherHand(Player player, InteractionHand hand) {
        return player.getItemInHand(otherHand(hand)).is(ItemTags.AXES);
    }

    private static InteractionResultHolder<ItemStack> open(Level level, Player player, InteractionHand hand) {
        ItemStack coconut = player.getItemInHand(hand);
        InteractionHand toolHand = otherHand(hand);
        ItemStack tool = player.getItemInHand(toolHand);
        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.WOOD_BREAK, SoundSource.PLAYERS, 1.0F, 1.3F);
        if (!level.isClientSide()) {
            tool.hurtAndBreak(1, player, LivingEntity.getSlotForHand(toolHand));
            coconut.consume(1, player);
            ItemStack opened = new ItemStack(ModItems.OPEN_COCONUT.get());
            if (!player.getInventory().add(opened)) {
                player.drop(opened, false);
            }
        }
        return InteractionResultHolder.sidedSuccess(coconut, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.avaliproject.coconut").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
