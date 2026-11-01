package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.client.ClientPayloadHandlers;
import com.lunkoashtail.avaliproject.component.DrugDosage;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MorphineItem extends Item {

    public static final float VIAL_ML = 30f;

    public MorphineItem() {
        super(new Item.Properties().stacksTo(1));
    }

    public static float dosageOf(ItemStack stack) {
        DrugDosage dosage = stack.get(ModDataComponents.DRUG_DOSAGE);
        return dosage != null ? dosage.dosage() : VIAL_ML;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.set(ModDataComponents.DRUG_DOSAGE, new DrugDosage(VIAL_ML));
        return stack;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!level.isClientSide() && !stack.has(ModDataComponents.DRUG_DOSAGE)) {
            stack.set(ModDataComponents.DRUG_DOSAGE, new DrugDosage(VIAL_ML));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) ClientPayloadHandlers.openAutoinjectorLoadScreen(hand);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal((int) dosageOf(stack) + " mL").withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.translatable("tooltip.avaliproject.morphine_vial").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
