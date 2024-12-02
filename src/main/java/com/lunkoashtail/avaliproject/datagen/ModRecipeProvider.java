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
        List<ItemLike> LUME_SMELTABLES = List.of(
                ModBlocks.LUME_ORE, ModBlocks.LUME_DEEPSLATE_ORE);
        List<ItemLike> TITANIUM_SMELTABLES = List.of(
                ModBlocks.TITANIUM_ORE, ModBlocks.TITANIUM_DEEPSLATE_ORE, ModItems.RAW_TITANIUM);
        List<ItemLike> DURASTEEL_SMELTABLES = List.of(
                ModBlocks.DURASTEEL_ORE.get(),ModBlocks.DURASTEEL_DEEPSLATE_ORE.get(), ModItems.RAW_DURASTEEL);
        List<ItemLike> AERO_CRYSTAL_SMELTABLES = List.of(
                ModBlocks.AERO_CRYSTAL_ORE, ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE);
        List<ItemLike> SYNC_CRYSTAL_SMELTABLES = List.of(
                ModBlocks.SYNC_CRYSTAL_ORE, ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE);
        List<ItemLike> THERMAL_CRYSTAL_SMELTABLES = List.of(
                ModBlocks.THERMAL_CRYSTAL_ORE, ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE);
        List<ItemLike> AEGISALT_SMELTABLES = List.of(
                ModBlocks.AEGISALT_ORE, ModBlocks.AEGISALT_DEEPSLATE_ORE, ModItems.RAW_AEGISALT);
        List<ItemLike> VILOUS_CERAMIC_SMELTABLES = List.of(
                ModBlocks.VILOUS_CERAMIC_ORE, ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE, ModItems.VILOUS_CLAY);
        List<ItemLike> ARCAITES_CRYSTAL_SMELTABLES = List.of(
                ModBlocks.ARCAITES_CRYSTAL_ORE, ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE);
        List<ItemLike> NOVULITE_SMELTABLES = List.of(
                ModBlocks.NOVULITE_ORE, ModBlocks.NOVULITE_DEEPSLATE_ORE);
        List<ItemLike> AGATE_SMELTABLES = List.of(
                ModBlocks.AGATE_ORE, ModBlocks.AGATE_DEEPSLATE_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LUME.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.LUME_BIT.get())
                .unlockedBy("avaliproject:has_lume_bit", has(ModItems.LUME_BIT))
                .save(recipeOutput, "avaliproject:lume_from_lume_bits");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LUME_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.LUME.get())
                .unlockedBy("avaliproject:has_lume", has(ModItems.LUME))
                .save(recipeOutput, "avaliproject:lume_block_from_lume");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.AVALI_SWORD.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" B ")
                .define('A', ModItems.AEROGEL.get())
                .define('B', ModItems.AEROMER.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL))
                .unlockedBy("has_aeromer", has(ModItems.AEROMER))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.AVALI_PICKAXE.get())
                .pattern("ACC")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', ModItems.AEROGEL.get())
                .define('B', ModItems.AEROMER.get())
                .define('C', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL))
                .unlockedBy("has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("has_woven_graphene", has(ModItems.WOVEN_GRAPHENE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.AVALI_HOE.get())
                .pattern(" AC")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', ModItems.AEROGEL.get())
                .define('B', ModItems.AEROMER.get())
                .define('C', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL))
                .unlockedBy("has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("has_woven_graphene", has(ModItems.WOVEN_GRAPHENE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.AVALI_AXE.get())
                .pattern(" CA")
                .pattern(" BA")
                .pattern(" B ")
                .define('A', ModItems.AEROGEL.get())
                .define('B', ModItems.AEROMER.get())
                .define('C', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL))
                .unlockedBy("has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("has_woven_graphene", has(ModItems.WOVEN_GRAPHENE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AEROMER.get())
                .pattern("BAB")
                .pattern("ABA")
                .pattern("BAB")
                .define('A', ModItems.AERO_CRYSTAL.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("has_aero_crystal", has(ModItems.AERO_CRYSTAL))
                .unlockedBy("has_woven_graphene", has(ModItems.WOVEN_GRAPHENE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AEROGEL.get(), 3)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.AERO_CRYSTAL.get())
                .unlockedBy("avaliproject:has_aero_crystal", has(ModItems.AERO_CRYSTAL))
                .save(recipeOutput, "avaliproject:aerogel_from_aero_crystal");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOVEN_GRAPHENE.get(), 4)
                .pattern("BAB")
                .pattern("ABA")
                .pattern("BAB")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.COAL)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_coal", has(Items.COAL))
                .save(recipeOutput, "avaliproject:woven_grapene");

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.AVALI_BBQ.get(), 3)
                .pattern("BA ")
                .pattern("ABA")
                .pattern(" AB")
                .define('A', Items.COOKED_CHICKEN)
                .define('B', ModItems.NAKATI_BARK.get())
                .unlockedBy("has_cooked_chicken", has(Items.COOKED_CHICKEN))
                .unlockedBy("has_nakati_bark", has(ModItems.NAKATI_BARK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.AVALON_TACO.get())
                .pattern("ABA")
                .pattern(" A ")
                .define('A', ModItems.PIRU_FLOUR)
                .define('B', ModItems.SPICY_JERKY.get())
                .unlockedBy("has_piru_flour", has(ModItems.PIRU_FLOUR))
                .unlockedBy("has_spicy_jerky", has(ModItems.SPICY_JERKY))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PROTOSTEEL_INGOT.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', ModItems.NANITE_INJECTOR)
                .define('B', ModItems.DURASTEEL_INGOT.get())
                .unlockedBy("has_nanite_injector", has(ModItems.NANITE_INJECTOR))
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.PIRUZA.get())
                .pattern("BAB")
                .pattern("ACA")
                .pattern("DDD")
                .define('C', Items.MILK_BUCKET)
                .define('B', ModItems.KIRI_FRUIT.get())
                .define('A', ModItems.SPICY_JERKY.get())
                .define('D', ModItems.PIRU_FLOUR.get())
                .unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET))
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT))
                .unlockedBy("has_spicy_jerky", has(ModItems.SPICY_JERKY))
                .unlockedBy("has_piru_flour", has(ModItems.PIRU_FLOUR))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.KIRI_CIDER.get())
                .pattern(" A ")
                .pattern("CBC")
                .pattern(" A ")
                .define('A', ModItems.NAKATI_BARK.get())
                .define('B', ModItems.AVALI_BOTTLE.get())
                .define('C', ModItems.KIRI_FRUIT.get())
                .unlockedBy("has_avali_bottle", has(ModItems.AVALI_BOTTLE))
                .unlockedBy("has_nakati_bark", has(ModItems.NAKATI_BARK))
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.KIRI_JAM.get())
                .pattern("BAB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A', ModItems.KIRI_FRUIT.get())
                .define('B', Items.GLASS_PANE)
                .unlockedBy("has_bowl", has(Items.GLASS_PANE))
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.KIRI_CURRY.get())
                .pattern("AD ")
                .pattern("BC ")
                .define('A', ModItems.KIRI_FRUIT.get())
                .define('B', Items.BOWL)
                .define('C', ModItems.SPICY_JERKY.get())
                .define('D', ModItems.PIRU_NOODLE.get())
                .unlockedBy("has_piru_noodle", has(ModItems.PIRU_NOODLE))
                .unlockedBy("has_spicy_jerky", has(ModItems.SPICY_JERKY))
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT))
                .unlockedBy("has_bowl", has(Items.BOWL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.KIRIKIRI_PIE.get())
                .pattern("DAD")
                .pattern("CBC")
                .pattern("UUU")
                .define('A', Items.EGG)
                .define('B', Items.MILK_BUCKET)
                .define('C', Items.SUGAR)
                .define('D', ModItems.KIRI_FRUIT.get())
                .define('U', ModItems.PIRU_FLOUR.get())
                .unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET))
                .unlockedBy("has_egg", has(Items.EGG))
                .unlockedBy("has_sugar", has(Items.SUGAR))
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT))
                .unlockedBy("has_piru_flour", has(ModItems.PIRU_FLOUR))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.GROOU_JUICE.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', ModItems.GROOU.get())
                .define('B', ModItems.AVALI_BOTTLE.get())
                .unlockedBy("has_avali_bottle", has(ModItems.AVALI_BOTTLE))
                .unlockedBy("has_groou", has(ModItems.GROOU))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AVALI_BOTTLE.get())
                .pattern("C C")
                .pattern(" C ")
                .define('C', ModItems.AEROGEL.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.PIRU_NOODLE.get())
                .pattern("CBC")
                .pattern("BCB")
                .pattern("CBC")
                .define('C', ModItems.PIRU_FLOUR.get())
                .define('B', ModItems.KIRI_FRUIT.get())
                .unlockedBy("has_piru_flour", has(ModItems.PIRU_FLOUR))
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOVEN_FABRIC.get())
                .pattern("BB ")
                .pattern("BB ")
                .define('B', ModItems.FIBER.get())
                .unlockedBy("has_fiber", has(ModItems.FIBER))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.AVALI_MUFFIN.get(), 2)
                .pattern("OOO")
                .pattern("DAD")
                .pattern("BBB")
                .define('A', Items.EGG)
                .define('D', Items.SUGAR)
                .define('B', ModItems.PIRU_FLOUR.get())
                .define('O', ModItems.KIRI_JAM.get())
                .unlockedBy("has_egg", has(Items.EGG))
                .unlockedBy("has_sugar", has(Items.SUGAR))
                .unlockedBy("has_piru_flour", has(ModItems.PIRU_FLOUR))
                .unlockedBy("has_kiri_jam", has(ModItems.KIRI_JAM))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NANITE_INJECTOR.get(), 2)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('B', Items.DIAMOND)
                .define('A', ModItems.DURASTEEL_INGOT.get())
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.PIRU_FLOUR.get(), 2)
                .requires(ModItems.PIRU_FROND)
                .unlockedBy("avaliproject:has_piru_frond", has(ModItems.PIRU_FROND))
                .save(recipeOutput, "avaliproject:piru_flour_from_piru_frond");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LUME_BIT.get(), 9)
                .requires(ModItems.LUME)
                .unlockedBy("avaliproject:has_lume", has(ModItems.LUME))
                .save(recipeOutput, "avaliproject:lume_bits_from_lume");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LUME.get(), 9)
                .requires(ModBlocks.LUME_BLOCK)
                .unlockedBy("avaliproject:has_lume_from_lume_block", has(ModBlocks.LUME_BLOCK))
                .save(recipeOutput, "avaliproject:lume_from_lume_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SUGAR, 3)
                .requires(ModItems.GROOU)
                .unlockedBy("has_groou", has(ModItems.GROOU))
                .save(recipeOutput, "avaliproject:sugar_from_groou");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.GROOU, 1)
                .requires(ModBlocks.GROOU_NODULE)
                .unlockedBy("has_groou_nodule", has(ModBlocks.GROOU_NODULE))
                .save(recipeOutput, "avaliproject:groou_from_groou_nodule");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.PIRU_COLONY, 1)
                .requires(ModBlocks.PIRU_NODULE)
                .unlockedBy("has_groou_nodule", has(ModBlocks.PIRU_NODULE))
                .save(recipeOutput, "avaliproject:piru_colony_from_piru_nodule");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.NAKATI_OVOID, 1)
                .requires(ModBlocks.NAKATI_NODULE)
                .unlockedBy("has_groou_nodule", has(ModBlocks.NAKATI_NODULE))
                .save(recipeOutput, "avaliproject:nakati_ovoid_from_nakati_nodule");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.KIRI_FRUIT, 1)
                .requires(ModBlocks.KIRI_NODULE)
                .unlockedBy("has_kiri_nodule", has(ModBlocks.KIRI_NODULE))
                .save(recipeOutput, "avaliproject:kiri_fruit_from_kiri_nodule");
    ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SERGAL_CHEESE, 4)
                .requires(Items.MILK_BUCKET)
                .unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET))
                .save(recipeOutput, "avaliproject:sergal_cheese_from_milk_bucket");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PROTOGEN_RAM.get(), 4)
                .pattern("AAA")
                .pattern("CBC")
                .define('B', Items.IRON_NUGGET)
                .define('C', Items.GOLD_NUGGET)
                .define('A', ModItems.DURASTEEL_INGOT.get())
                .unlockedBy("has_gold_nugget", has(Items.GOLD_NUGGET))
                .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET))
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.PROTOGEN_AXE.get())
                .pattern(" BB")
                .pattern(" CA")
                .pattern(" C ")
                .define('C', ModItems.DURASTEEL_INGOT.get())
                .define('B', ModItems.PROTOSTEEL_INGOT.get())
                .define('A', ModItems.NANITE_INJECTOR.get())
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT))
                .unlockedBy("has_protosteel_ingot", has(ModItems.PROTOSTEEL_INGOT))
                .unlockedBy("has_nanite_injector", has(ModItems.NANITE_INJECTOR))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.PROTOGEN_SWORD.get())
                .pattern(" C ")
                .pattern("ACA")
                .pattern(" B ")
                .define('B', ModItems.DURASTEEL_INGOT.get())
                .define('C', ModItems.PROTOSTEEL_INGOT.get())
                .define('A', ModItems.NANITE_INJECTOR.get())
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT))
                .unlockedBy("has_protosteel_ingot", has(ModItems.PROTOSTEEL_INGOT))
                .unlockedBy("has_nanite_injector", has(ModItems.NANITE_INJECTOR))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.AVALI_SPEAR.get())
                .pattern("  A")
                .pattern("DC ")
                .pattern("C  ")
                .define('A', ModItems.AEROGEL.get())
                .define('C', ModItems.AEROMER.get())
                .define('D', ModItems.REFINED_AEGISALT.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL))
                .unlockedBy("has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("has_refined_aegisalt", has(ModItems.REFINED_AEGISALT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NOVULITE_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.NOVULITE.get())
                .unlockedBy("avaliproject:has_novulite", has(ModItems.NOVULITE))
                .save(recipeOutput, "avaliproject:novulite_block_from_novulite");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NOVULITE.get(), 9)
                .requires(ModBlocks.NOVULITE_BLOCK)
                .unlockedBy("avaliproject:has_novulite_block", has(ModBlocks.NOVULITE_BLOCK))
                .save(recipeOutput, "avaliproject:novulite_from_novulite_block");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AGATE_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.AGATE.get())
                .unlockedBy("avaliproject:has_agate", has(ModItems.AGATE))
                .save(recipeOutput, "avaliproject:agate_block_from_agate");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AGATE.get(), 9)
                .requires(ModBlocks.AGATE_BLOCK)
                .unlockedBy("avaliproject:has_agate_block", has(ModBlocks.AGATE_BLOCK))
                .save(recipeOutput, "avaliproject:agate_from_agate_block");


        oreSmelting(recipeOutput, ARCAITES_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.ARCAITES_CRYSTAL.get(), 0.25f, 200, "arcaites_crystal");
        oreBlasting(recipeOutput, ARCAITES_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.ARCAITES_CRYSTAL.get(), 0.25f, 100, "arcaites_crystal");
        oreSmelting(recipeOutput, VILOUS_CERAMIC_SMELTABLES, RecipeCategory.MISC, ModItems.VILOUS_CERAMIC_INGOT.get(), 0.25f, 200, "vilous_ceramic");
        oreBlasting(recipeOutput, VILOUS_CERAMIC_SMELTABLES, RecipeCategory.MISC, ModItems.VILOUS_CERAMIC_INGOT.get(), 0.25f, 100, "vilous_ceramic");
        oreSmelting(recipeOutput, DURASTEEL_SMELTABLES, RecipeCategory.MISC, ModItems.DURASTEEL_INGOT.get(), 0.25f, 200, "Durasteel");
        oreBlasting(recipeOutput, DURASTEEL_SMELTABLES, RecipeCategory.MISC, ModItems.DURASTEEL_INGOT.get(), 0.25f, 100, "Durasteel");
        oreSmelting(recipeOutput, AERO_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.AERO_CRYSTAL.get(), 0.25f, 200, "aero_crystal");
        oreBlasting(recipeOutput, AERO_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.AERO_CRYSTAL.get(), 0.25f, 100, "aero_crystal");
        oreSmelting(recipeOutput, SYNC_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.SYNC_CRYSTAL.get(), 0.25f, 200, "sync_crystal");
        oreBlasting(recipeOutput, SYNC_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.SYNC_CRYSTAL.get(), 0.25f, 100, "sync_crystal");
        oreSmelting(recipeOutput, THERMAL_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.THERMAL_CRYSTAL.get(), 0.25f, 200, "thermal_crystal");
        oreBlasting(recipeOutput, THERMAL_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.THERMAL_CRYSTAL.get(), 0.25f, 100, "thermal_crystal");
        oreSmelting(recipeOutput, AEGISALT_SMELTABLES, RecipeCategory.MISC, ModItems.REFINED_AEGISALT.get(), 0.25f, 200, "refined_aegisalt");
        oreBlasting(recipeOutput, AEGISALT_SMELTABLES, RecipeCategory.MISC, ModItems.REFINED_AEGISALT.get(), 0.25f, 100, "refined_aegisalt");
        oreSmelting(recipeOutput, TITANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 0.25f, 200, "titanium");
        oreBlasting(recipeOutput, TITANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 0.25f, 100, "titanium");
        oreSmelting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.LUME_BIT.get(), 0.25f, 200, "lume_bit");
        oreBlasting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.LUME_BIT.get(), 0.25f, 100, "lume_bit");
        oreSmelting(recipeOutput, AGATE_SMELTABLES, RecipeCategory.MISC, ModItems.AGATE.get(), 0.25f, 200, "agate");
        oreBlasting(recipeOutput, AGATE_SMELTABLES, RecipeCategory.MISC, ModItems.AGATE.get(), 0.25f, 100, "agate");
        oreSmelting(recipeOutput, NOVULITE_SMELTABLES, RecipeCategory.MISC, ModItems.NOVULITE.get(), 0.25f, 200, "novulite");
        oreBlasting(recipeOutput, NOVULITE_SMELTABLES, RecipeCategory.MISC, ModItems.NOVULITE.get(), 0.25f, 100, "novulite");

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
