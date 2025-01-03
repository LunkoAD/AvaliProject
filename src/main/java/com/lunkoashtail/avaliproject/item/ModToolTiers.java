package com.lunkoashtail.avaliproject.item;

import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier AEROGEL = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_AEROGEL_TOOL,
            1996, 7f, 3f, 20, () -> Ingredient.of(ModItems.AEROGEL));
    public static final Tier HARDLIGHT = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_HARDLIGHT_TOOL,
            1996, 9f, 4f, 20, () -> Ingredient.of(ModItems.PROTOSTEEL_INGOT));
    public static final Tier CERAMIC = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_CERAMIC_TOOL,
            1996, 8f, 5f, 20, () -> Ingredient.of(ModItems.VILOUS_CERAMIC_INGOT));
}