package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.screen.custom.LimbSelectionScreen;
import com.lunkoashtail.avaliproject.screen.custom.SyringeMinigameScreen;
import com.lunkoashtail.avaliproject.species.Species;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HeroinItem extends Item {

    public HeroinItem() {
        super(new Item.Properties().stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            if (player.getData(ModAttachments.SPECIES) != Species.EXPIE) {
                player.sendSystemMessage(Component.literal("You're not an expie"));
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
            Minecraft.getInstance().setScreen(new LimbSelectionScreen(selectedLimb ->
                    Minecraft.getInstance().setScreen(
                            new SyringeMinigameScreen(SyringeMinigameScreen.DrugType.HEROIN, selectedLimb))
            ));
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}
