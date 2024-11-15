package com.lunkoashtail.avaliproject.util;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class Aerogel extends Item {

    public Aerogel(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.avaliproject.aerogel.shift_down"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.avaliproject.aerogel"));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}