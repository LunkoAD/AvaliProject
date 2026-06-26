package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.screen.custom.LimbSelectionScreen;
import com.lunkoashtail.avaliproject.screen.custom.SyringeMinigameScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SyringeItem extends Item {

    public SyringeItem() {
        super(new Item.Properties().stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            // Player selects the injection site first, then the syringe minigame opens.
            Minecraft.getInstance().setScreen(new LimbSelectionScreen(selectedLimb ->
                    Minecraft.getInstance().setScreen(
                            new SyringeMinigameScreen(SyringeMinigameScreen.DrugType.SYRINGE, selectedLimb))
            ));
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}
