package com.lunkoashtail.avaliproject.item;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.util.Aerogel;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier AEROGEL = TierSortingRegistry.registerTier(
            new ForgeTier(1996, 7, 3f, 20, 25, ModTags.Blocks.INCORRECT_FOR_AEROGEL_TOOL, () -> Ingredient.of(ModItems.AEROGEL.get())),
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "aerogel"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier HARDLIGHT = TierSortingRegistry.registerTier(new ForgeTier(
            1996, 9, 4f, 20,25,ModTags.Blocks.INCORRECT_FOR_HARDLIGHT_TOOL, () -> Ingredient.of(ModItems.PROTOSTEEL_INGOT.get())),
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "hardlight"), List.of(AEROGEL,Tiers.NETHERITE), List.of());
    public static final Tier CERAMIC = TierSortingRegistry.registerTier(new ForgeTier(
            1996, 8, 5f, 20,25,ModTags.Blocks.INCORRECT_FOR_CERAMIC_TOOL, () -> Ingredient.of(ModItems.VILOUS_CERAMIC_INGOT.get())),
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "ceramic"), List.of(AEROGEL,Tiers.NETHERITE,HARDLIGHT), List.of());
}