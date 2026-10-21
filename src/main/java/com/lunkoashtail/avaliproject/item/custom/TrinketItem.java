package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TrinketItem extends Item {
    private final String tooltipKey;

    public TrinketItem(String tooltipKey, Properties properties) {
        super(properties);
        this.tooltipKey = tooltipKey;
    }

    public static String getGem(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.TRINKET_GEM.get(), "");
    }

    public static ItemStack withGem(ItemStack stack, String gem) {
        stack.set(ModDataComponents.TRINKET_GEM.get(), gem);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        String gem = getGem(stack);
        if (!gem.isEmpty()) {
            tooltipComponents.add(Component.translatable("tooltip.avaliproject.trinket_gem",
                    Component.translatable("item.avaliproject." + gem)).withStyle(ChatFormatting.AQUA));
            tooltipComponents.add(Component.translatable("tooltip.avaliproject.trinket_gem." + gem).withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
