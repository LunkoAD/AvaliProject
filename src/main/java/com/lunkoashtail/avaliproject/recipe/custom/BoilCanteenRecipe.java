package com.lunkoashtail.avaliproject.recipe.custom;

import com.lunkoashtail.avaliproject.component.CanteenContents;
import com.lunkoashtail.avaliproject.component.FluidType;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import com.lunkoashtail.avaliproject.recipe.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;

public class BoilCanteenRecipe extends SmeltingRecipe {

    public BoilCanteenRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result,
                             float experience, int cookingTime) {
        super(group, category, ingredient, result, experience, cookingTime);
    }

    static boolean canBoil(ItemStack stack) {
        return FluidRecipeHelper.isCanteen(stack) && FluidRecipeHelper.contentsOf(stack).get(FluidType.WATER) > 0f;
    }

    static ItemStack boil(ItemStack stack) {
        ItemStack result = stack.copyWithCount(1);
        CanteenContents contents = FluidRecipeHelper.contentsOf(result);
        float water = contents.get(FluidType.WATER);
        result.set(ModDataComponents.CANTEEN_CONTENTS, contents
                .withRemoved(FluidType.WATER, water)
                .withAdded(FluidType.BOILED_WATER, water));
        return result;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return canBoil(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return boil(input.item());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BOIL_CANTEEN.get();
    }
}
