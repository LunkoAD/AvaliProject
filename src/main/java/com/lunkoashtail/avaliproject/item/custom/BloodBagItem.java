package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.component.BloodContents;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BloodBagItem extends MedicalItem {

    public BloodBagItem() {
        super(com.lunkoashtail.avaliproject.vitals.MedicalAction.BLOOD_BAG, new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        BloodContents contents = stack.get(ModDataComponents.BLOOD_CONTENTS);
        if (contents != null) {
            Style style = Style.EMPTY.withColor(TextColor.fromRgb(contents.species().bloodColorRgb()));
            tooltip.add(Component.literal((int) contents.amountMl() + " mL — " + contents.species().bloodName()).withStyle(style));
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
