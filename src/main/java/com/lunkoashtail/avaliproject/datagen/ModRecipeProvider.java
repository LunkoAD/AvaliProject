package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> LUME_SMELTABLES = List.of(ModItems.LUME_BIT,
                ModBlocks.LUME_ORE, ModBlocks.LUME_DEEPSLATE_ORE);
        List<ItemLike> TITANIUM_SMELTABLES = List.of(ModItems.TITANIUM_INGOT,
                ModBlocks.TITANIUM_ORE, ModBlocks.TITANIUM_DEEPSLATE_ORE);
        List<ItemLike> DURASTEEL_SMELTABLES = List.of(ModItems.DURASTEEL_INGOT,
                ModBlocks.DURASTEEL_ORE, ModBlocks.DURASTEEL_DEEPSLATE_ORE);
        List<ItemLike> AERO_CRYSTAL_SMELTABLES = List.of(ModItems.AERO_CRYSTAL,
                ModBlocks.AERO_CRYSTAL_ORE, ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE);
        List<ItemLike> SYNC_CRYSTAL_SMELTABLES = List.of(ModItems.SYNC_CRYSTAL,
                ModBlocks.SYNC_CRYSTAL_ORE, ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE);
        List<ItemLike> THERMAL_CRYSTAL_SMELTABLES = List.of(ModItems.REFINED_AEGISALT,
                ModBlocks.THERMAL_CRYSTAL_ORE, ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE);
                List<ItemLike> AEGISALT_SMELTABLES = List.of(ModItems.REFINED_AEGISALT,
                ModBlocks.AERO_CRYSTAL_ORE, ModBlocks.AEGISALT_DEEPSLATE_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LUME.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.LUME_BIT.get())
                .unlockedBy("has_lume_bit", has(ModItems.LUME_BIT)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LUME_BIT.get(), 9)
                .requires(ModItems.LUME)
                .unlockedBy("has_lume", has(ModItems.LUME)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SUGAR, 3)
                .requires(ModItems.GROOU)
                .unlockedBy("has_groou", has(ModItems.GROOU))
                .save(recipeOutput, "avaliproject:sugar_from_groou");

        oreSmelting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.LUME_BIT.get(), 0.25f, 200, "lume");
        oreBlasting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.LUME_BIT.get(), 0.25f, 100, "lume");
        oreSmelting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.DURASTEEL_INGOT.get(), 0.25f, 200, "Durasteel");
        oreBlasting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.DURASTEEL_INGOT.get(), 0.25f, 100, "Durasteel");
        oreSmelting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.AERO_CRYSTAL.get(), 0.25f, 200, "aero_crystal");
        oreBlasting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.AERO_CRYSTAL.get(), 0.25f, 100, "aero_crystal");
        oreSmelting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.SYNC_CRYSTAL.get(), 0.25f, 200, "sync_crystal");
        oreBlasting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.SYNC_CRYSTAL.get(), 0.25f, 100, "sync_crystal");
        oreSmelting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.THERMAL_CRYSTAL.get(), 0.25f, 200, "thermal_crystal");
        oreBlasting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.THERMAL_CRYSTAL.get(), 0.25f, 100, "thermal_crystal");
        oreSmelting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.REFINED_AEGISALT.get(), 0.25f, 200, "refined_aegisalt");
        oreBlasting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.REFINED_AEGISALT.get(), 0.25f, 100, "refined_aegisalt");
        oreSmelting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 0.25f, 200, "titanium");
        oreBlasting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 0.25f, 100, "titanium");
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, AvaliProject.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
