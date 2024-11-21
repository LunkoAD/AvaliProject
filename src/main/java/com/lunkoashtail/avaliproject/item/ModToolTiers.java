package com.lunkoashtail.avaliproject.item;

import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier AEROGEL = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_AEROGEL_TOOL,
            1996, 8f, 3f, 20, () -> Ingredient.of(ModItems.AEROGEL));
    public static final Tier HARDLIGHT = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_HARDLIGHT_TOOL,
            1996, 8f, 4f, 20, () -> Ingredient.of(ModItems.PROTOSTEEL_INGOT));
}