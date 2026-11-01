package com.lunkoashtail.avaliproject.recipe.custom;

import com.lunkoashtail.avaliproject.recipe.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class BoilCanteenCampfireRecipe extends CampfireCookingRecipe {

    public BoilCanteenCampfireRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result,
                                     float experience, int cookingTime) {
        super(group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return BoilCanteenRecipe.canBoil(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return BoilCanteenRecipe.boil(input.item());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BOIL_CANTEEN_CAMPFIRE.get();
    }
}
