package com.lunkoashtail.avaliproject.recipe.custom;

import com.lunkoashtail.avaliproject.component.CanteenContents;
import com.lunkoashtail.avaliproject.component.FluidType;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import com.lunkoashtail.avaliproject.recipe.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class FilterWaterRecipe extends CustomRecipe {

    public FilterWaterRecipe(CraftingBookCategory category) {
        super(category);
    }

    private static boolean eligible(CanteenContents contents) {
        return contents.get(FluidType.IRRADIATED_WATER) > 0f;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (FluidRecipeHelper.countNonCanteenNonEmptySlots(input) != 1) return false;
        if (FluidRecipeHelper.countItem(input, Items.CHARCOAL) != 1) return false;
        return FluidRecipeHelper.findSingleCanteenSlot(input, FilterWaterRecipe::eligible) != -1;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        int index = FluidRecipeHelper.findSingleCanteenSlot(input, FilterWaterRecipe::eligible);
        if (index == -1) return ItemStack.EMPTY;

        ItemStack result = input.getItem(index).copy();
        CanteenContents contents = FluidRecipeHelper.contentsOf(result);
        float irradiated = contents.get(FluidType.IRRADIATED_WATER);
        result.set(ModDataComponents.CANTEEN_CONTENTS, contents
                .withRemoved(FluidType.IRRADIATED_WATER, irradiated)
                .withAdded(FluidType.WATER, irradiated));
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.FILTER_WATER.get();
    }
}
