package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.screen.custom.BandageMinigameScreen;
import com.lunkoashtail.avaliproject.screen.custom.LimbSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BandageItem extends Item {

    public BandageItem() {
        super(new Item.Properties().stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            // Step 1 — player picks the limb they want to bandage.
            // Step 2 — bandage minigame opens for that specific limb.
            Minecraft.getInstance().setScreen(new LimbSelectionScreen(selectedLimb -> {
                int bleed = player.getData(ModAttachments.LIMB_DATA).getBleed(selectedLimb);
                Minecraft.getInstance().setScreen(new BandageMinigameScreen(selectedLimb, bleed));
            }));
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.avaliproject.bandage.tooltip"));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
