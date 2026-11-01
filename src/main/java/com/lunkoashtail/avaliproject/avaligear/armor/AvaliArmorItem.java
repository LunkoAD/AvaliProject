package com.lunkoashtail.avaliproject.avaligear.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AvaliArmorItem extends ArmorItem {
    private final String model;
    private final String texture;
    private final boolean visor;
    private final List<String> hiddenParts;

    public AvaliArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties, String model, String texture,
                          boolean visor, List<String> hiddenParts) {
        super(material, type, properties);
        this.model = model;
        this.texture = texture;
        this.visor = visor;
        this.hiddenParts = List.copyOf(hiddenParts);
    }

    public String model() {
        return model;
    }

    public String texture() {
        return texture;
    }

    public boolean hasVisor() {
        return visor;
    }

    public List<String> hiddenParts() {
        return hiddenParts;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.avaliproject.avali_fit").withStyle(ChatFormatting.DARK_AQUA));
        if (visor) {
            tooltip.add(Component.translatable("tooltip.avaliproject.visor_toggle").withStyle(ChatFormatting.GRAY));
        }
    }
}
