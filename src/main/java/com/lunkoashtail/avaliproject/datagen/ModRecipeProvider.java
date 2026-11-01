package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.WoodSet;
import com.lunkoashtail.avaliproject.item.DisabledContent;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.recipe.custom.BiochemRecipe;
import com.lunkoashtail.avaliproject.recipe.custom.FentanylCraftRecipe;
import com.lunkoashtail.avaliproject.recipe.custom.MorphineRecipe;
import com.lunkoashtail.avaliproject.recipe.custom.OpiumRecipe;
import com.lunkoashtail.avaliproject.recipe.custom.SalineRecipe;
import com.lunkoashtail.avaliproject.recipe.custom.WaterBottleRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import com.lunkoashtail.avaliproject.item.custom.TrinketItem;
import net.minecraft.world.item.ItemStack;
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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.AVALI_SWORD.get())
                .requires(ModItems.AVALI_SWORD)
                .requires(ModItems.AEROGEL_BATTERY)
                .unlockedBy("avaliproject:has_aerogel_battery", has(ModItems.AEROGEL_BATTERY))
                .save(recipeOutput, "avaliproject:avali_sword_restore");

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

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SERGAL_LANCE.get())
                .pattern("C")
                .pattern("S")
                .pattern("S")
                .define('S', Items.STICK)
                .define('C', ModItems.VILOUS_CERAMIC_INGOT.get())
                .unlockedBy("has_vilous_ceramic", has(ModItems.VILOUS_CERAMIC_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.VILOUS_CERAMIC_BLOCK.get().asItem())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .define('C', ModItems.VILOUS_CERAMIC_INGOT.get())
                .unlockedBy("has_vilous_ceramic", has(ModItems.VILOUS_CERAMIC_INGOT))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.VILOUS_CERAMIC_INGOT.get().asItem(), 9)
                .requires(ModBlocks.VILOUS_CERAMIC_BLOCK.asItem())
                .unlockedBy("has_vilous_ceramic_block", has(ModBlocks.VILOUS_CERAMIC_BLOCK.asItem()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SERGAL_MACE.get())
                .pattern("CBC")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('B', ModBlocks.VILOUS_CERAMIC_BLOCK.get().asItem())
                .define('C', ModItems.VILOUS_CERAMIC_INGOT.get())
                .unlockedBy("has_vilous_ceramic_block", has(ModBlocks.VILOUS_CERAMIC_BLOCK.get().asItem()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SERGAL_GREATSWORD.get())
                .pattern("CC")
                .pattern("CC")
                .pattern(" S")
                .define('S', Items.STICK)
                .define('C', ModItems.VILOUS_CERAMIC_INGOT.get())
                .unlockedBy("has_vilous_ceramic", has(ModItems.VILOUS_CERAMIC_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SERGAL_SWORD.get())
                .pattern("C")
                .pattern("C")
                .pattern("S")
                .define('S', Items.STICK)
                .define('C', ModItems.VILOUS_CERAMIC_INGOT.get())
                .unlockedBy("has_vilous_ceramic", has(ModItems.VILOUS_CERAMIC_INGOT))
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
        storageBlock(recipeOutput, ModItems.CARNELIAN.get(), ModBlocks.CARNELIAN_BLOCK.get());
        storageBlock(recipeOutput, ModItems.JADEITE.get(), ModBlocks.JADEITE_BLOCK.get());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.OPEN_DRAGON_FRUIT.get())
                .requires(ModItems.DRAGON_FRUIT.get())
                .unlockedBy("avaliproject:has_dragon_fruit", has(ModItems.DRAGON_FRUIT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AVALI_DANCE_MUSIC_DISC.get())
                .pattern("CAC")
                .pattern("ADA")
                .pattern("CAC")
                .define('A', ModItems.SYNC_CRYSTAL.get())
                .define('C', Items.FLINT)
                .define('D', Items.AMETHYST_SHARD)
                .unlockedBy("has_sync_crystal", has(ModItems.SYNC_CRYSTAL))
                .unlockedBy("has_flint", has(Items.FLINT))
                .unlockedBy("has_amethyst_crystal", has(Items.AMETHYST_SHARD))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MERP_MUSIC_DISC.get())
                .pattern("CAC")
                .pattern("ADA")
                .pattern("CAC")
                .define('A', ModItems.AGATE.get())
                .define('C', Items.FLINT)
                .define('D', Items.AMETHYST_SHARD)
                .unlockedBy("has_agate", has(ModItems.AGATE))
                .unlockedBy("has_flint", has(Items.FLINT))
                .unlockedBy("has_amethyst_crystal", has(Items.AMETHYST_SHARD))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CYBERNETIC_HEART_MUSIC_DISC.get())
                .pattern("CAC")
                .pattern("ADA")
                .pattern("CAC")
                .define('A', ModItems.NOVULITE.get())
                .define('C', Items.FLINT)
                .define('D', Items.AMETHYST_SHARD)
                .unlockedBy("has_novulite", has(ModItems.NOVULITE))
                .unlockedBy("has_flint", has(Items.FLINT))
                .unlockedBy("has_amethyst_crystal", has(Items.AMETHYST_SHARD))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.SOFT_TAPESTRY.get())
                .pattern("BAB")
                .pattern("ABA")
                .pattern("BAB")
                .define('A', Items.ORANGE_WOOL)
                .define('B', Items.YELLOW_WOOL)
                .unlockedBy("has_orange_wool", has(Items.ORANGE_WOOL))
                .unlockedBy("has_yellow_wool", has(Items.YELLOW_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_FABRIC_BLOCK.get())
                .pattern("BBA")
                .pattern("BAB")
                .pattern("BBA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ALT_AVALI_FABRIC_BLOCK.get())
                .pattern("BBB")
                .pattern("ABA")
                .pattern("BAB")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.PROTOGEN_SUPPORT_BLOCK.get())
                .pattern("BAB")
                .pattern("BAB")
                .pattern("BAB")
                .define('A', Items.GRAY_CONCRETE)
                .define('B', Items.BLACK_CONCRETE)
                .unlockedBy("has_gray_concrete", has(Items.GRAY_CONCRETE))
                .unlockedBy("has_black_concrete", has(Items.BLACK_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ALT_PROTOGEN_SUPPORT_BLOCK.get())
                .pattern("AAA")
                .pattern("BBB")
                .pattern("AAA")
                .define('A', Items.GRAY_CONCRETE)
                .define('B', Items.BLACK_CONCRETE)
                .unlockedBy("has_gray_concrete", has(Items.GRAY_CONCRETE))
                .unlockedBy("has_black_concrete", has(Items.BLACK_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.DETAILED_PROTOGEN_BLOCK.get())
                .pattern("BAB")
                .pattern("BAB")
                .pattern("BAB")
                .define('A', Items.GRAY_CONCRETE)
                .define('B', Items.BLACK_CONCRETE)
                .unlockedBy("has_black_concrete", has(Items.BLACK_CONCRETE))
                .unlockedBy("has_light_blue_concrete", has(Items.LIGHT_BLUE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ALT_DETAILED_PROTOGEN_BLOCK.get())
                .pattern("AAA")
                .pattern("BBB")
                .pattern("AAA")
                .define('A', Items.BLACK_CONCRETE)
                .define('B', Items.LIGHT_BLUE_CONCRETE)
                .unlockedBy("has_black_concrete", has(Items.BLACK_CONCRETE))
                .unlockedBy("has_light_blue_concrete", has(Items.LIGHT_BLUE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_PATTERN_BLOCK_1.get())
                .pattern("AAB")
                .pattern("BAA")
                .pattern("ABA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_PATTERN_BLOCK_2.get())
                .pattern("ABA")
                .pattern("AAB")
                .pattern("BAA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_PATTERN_BLOCK_3.get())
                .pattern("ABA")
                .pattern("ABA")
                .pattern("ABA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_PATTERN_BLOCK_4.get())
                .pattern("BAB")
                .pattern("ABA")
                .pattern("ABA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_WALL_PATTERN_BLOCK_1.get())
                .pattern("AAA")
                .pattern("BAB")
                .pattern("ABA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_WALL_PATTERN_BLOCK_2.get())
                .pattern("AAA")
                .pattern("BBA")
                .pattern("AAB")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_WALL_PATTERN_BLOCK_3.get())
                .pattern("AAA")
                .pattern("BBB")
                .pattern("AAA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_WALL_PATTERN_BLOCK_4.get())
                .pattern("BAA")
                .pattern("ABB")
                .pattern("ABA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);



        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_1.get())
                .pattern("AAB")
                .pattern("ABA")
                .pattern("ABB")
                .define('B', Items.WHITE_WOOL)
                .define('A', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_2.get())
                .pattern("BAA")
                .pattern("ABB")
                .pattern("BBA")
                .define('A', Items.WHITE_WOOL)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_3.get())
                .pattern("ABB")
                .pattern("BAA")
                .pattern("ABA")
                .define('A', Items.WHITE_WOOL)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_4.get())
                .pattern("BAB")
                .pattern("BAA")
                .pattern("ABB")
                .define('A', Items.WHITE_WOOL)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_5.get())
                .pattern("BAA")
                .pattern("ABA")
                .pattern("BBA")
                .define('A', Items.WHITE_WOOL)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_6.get())
                .pattern("ABB")
                .pattern("AAB")
                .pattern("ABB")
                .define('A', Items.WHITE_WOOL)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_CARVING_1.get())
                .pattern("AAA")
                .pattern("BAA")
                .pattern("BBA")
                .define('A', Items.ACACIA_PLANKS)
                .define('B', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_acacia_planks", has(Items.ACACIA_PLANKS))
                .unlockedBy("has_dark_oak_planks", has(Items.DARK_OAK_PLANKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_CARVING_2.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("BBA")
                .define('A', Items.ACACIA_PLANKS)
                .define('B', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_acacia_planks", has(Items.ACACIA_PLANKS))
                .unlockedBy("has_dark_oak_planks", has(Items.DARK_OAK_PLANKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_CARVING_3.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("BBB")
                .define('A', Items.ACACIA_PLANKS)
                .define('B', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_acacia_planks", has(Items.ACACIA_PLANKS))
                .unlockedBy("has_dark_oak_planks", has(Items.DARK_OAK_PLANKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_CARVING_4.get())
                .pattern("AAA")
                .pattern("AAB")
                .pattern("BBB")
                .define('A', Items.ACACIA_PLANKS)
                .define('B', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_acacia_planks", has(Items.ACACIA_PLANKS))
                .unlockedBy("has_dark_oak_planks", has(Items.DARK_OAK_PLANKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_CARVING_5.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("ABB")
                .define('A', Items.ACACIA_PLANKS)
                .define('B', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_acacia_planks", has(Items.ACACIA_PLANKS))
                .unlockedBy("has_dark_oak_planks", has(Items.DARK_OAK_PLANKS))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MLSERIES_HILT.get())
                .pattern("ABB")
                .pattern("AAA")
                .pattern("BAA")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MLSERIES_MUZZLE.get())
                .pattern(" AA")
                .pattern("BAA")
                .pattern(" AA")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.AEROGEL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MLSERIES_MAIN.get())
                .pattern("AA ")
                .pattern("ACB")
                .pattern(" D ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .define('D', ModItems.AEROGEL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL))
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRELANCE_SCOPE.get())
                .pattern("ABA")
                .pattern("BDB")
                .pattern(" C ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', Items.IRON_INGOT)
                .define('D', Items.SPYGLASS)
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_spyglass", has(Items.SPYGLASS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRELANCE_MUZZLE.get())
                .pattern("AAA")
                .pattern("ABB")
                .pattern("A  ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRELANCE_HILT.get())
                .pattern("BB ")
                .pattern("A A")
                .pattern("AA ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRELANCE_MAIN.get())
                .pattern("BB ")
                .pattern("DCB")
                .pattern("BB ")
                .define('B', ModItems.AEROMER.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .define('D', ModItems.AEROGEL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL))
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MLSERIES.get())
                .pattern("   ")
                .pattern("ABC")
                .define('A', ModItems.MLSERIES_MUZZLE.get())
                .define('B', ModItems.MLSERIES_MAIN.get())
                .define('C', ModItems.MLSERIES_HILT.get())
                .unlockedBy("avaliproject:has_mlseries_muzzle", has(ModItems.MLSERIES_MUZZLE))
                .unlockedBy("avaliproject:has_mlseries_main", has(ModItems.MLSERIES_MAIN))
                .unlockedBy("avaliproject:has_mlseries_hilt", has(ModItems.MLSERIES_HILT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.FIRELANCE.get())
                .pattern(" D ")
                .pattern("ABC")
                .define('A', ModItems.FIRELANCE_HILT.get())
                .define('B', ModItems.FIRELANCE_MAIN.get())
                .define('C', ModItems.FIRELANCE_MUZZLE.get())
                .define('D', ModItems.FIRELANCE_SCOPE.get())
                .unlockedBy("avaliproject:has_firelance_hilt", has(ModItems.FIRELANCE_HILT))
                .unlockedBy("avaliproject:has_firelance_main", has(ModItems.FIRELANCE_MAIN))
                .unlockedBy("avaliproject:has_firelance_muzzle", has(ModItems.FIRELANCE_MUZZLE))
                .unlockedBy("avaliproject:has_firelance_scope", has(ModItems.FIRELANCE_SCOPE))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.QRC_MUZZLE.get())
                .pattern("AAA")
                .pattern("CBA")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', Items.IRON_BARS)
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .unlockedBy("has_iron_bars", has(Items.IRON_BARS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.QRC_MAIN.get())
                .pattern("AAA")
                .pattern("ACA")
                .pattern("AB ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.QRC_HILT.get())
                .pattern("AAA")
                .pattern("BBA")
                .pattern(" BA")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NOVA_MUZZLE.get())
                .pattern("BAB")
                .pattern("ABC")
                .define('B', ModItems.AEROMER.get())
                .define('A', ModItems.WOVEN_GRAPHENE.get())
                .define('C', Items.IRON_BARS)
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .unlockedBy("has_iron_bars", has(Items.IRON_BARS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NOVA_HILT.get())
                .pattern("ABA")
                .pattern("AAA")
                .pattern("AB ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NOVA_MAIN.get())
                .pattern("ABA")
                .pattern("AC ")
                .pattern("AA ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NOVA_SCOPE.get())
                .pattern(" BD")
                .pattern("BBB")
                .define('B', ModItems.AEROMER.get())
                .define('D', ModItems.AEROGEL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.QRC.get())
                .pattern("ABC")
                .define('A', ModItems.QRC_MUZZLE.get())
                .define('B', ModItems.QRC_MAIN.get())
                .define('C', ModItems.QRC_HILT.get())
                .unlockedBy("avaliproject:has_qrc_muzzle", has(ModItems.QRC_MUZZLE))
                .unlockedBy("avaliproject:has_qrc_main", has(ModItems.QRC_MAIN))
                .unlockedBy("avaliproject:has_qrc_hilt", has(ModItems.QRC_HILT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.NOVA.get())
                .pattern(" D ")
                .pattern("ABC")
                .define('A', ModItems.NOVA_HILT.get())
                .define('B', ModItems.NOVA_MAIN.get())
                .define('C', ModItems.NOVA_MUZZLE.get())
                .define('D', ModItems.NOVA_SCOPE.get())
                .unlockedBy("avaliproject:has_nova_hilt", has(ModItems.NOVA_HILT))
                .unlockedBy("avaliproject:has_nova_main", has(ModItems.NOVA_MAIN))
                .unlockedBy("avaliproject:has_nova_muzzle", has(ModItems.NOVA_MUZZLE))
                .unlockedBy("avaliproject:has_nova_scope", has(ModItems.NOVA_SCOPE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.AVALI_DRONE.get())
                .pattern("BAB")
                .define('A', ModItems.AVALI_DRONE_CORE.get())
                .define('B', ModItems.AVALI_DRONE_ROTORS.get())
                .unlockedBy("avaliproject:has_avali_drone_core", has(ModItems.AVALI_DRONE_CORE))
                .unlockedBy("avaliproject:has_avali_drone_rotors", has(ModItems.AVALI_DRONE_ROTORS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.STORM.get())
                .pattern("BC ")
                .pattern("A  ")
                .define('A', ModItems.STORM_HILT.get())
                .define('B', ModItems.STORM_CORE.get())
                .define('C', ModItems.STORM_MUZZLE)
                .unlockedBy("avaliproject:has_storm_hilt", has(ModItems.STORM_HILT))
                .unlockedBy("avaliproject:has_storm_core", has(ModItems.STORM_CORE))
                .unlockedBy("avaliproject:has_storm_muzzle", has(ModItems.STORM_MUZZLE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BLIZZARD.get())
                .pattern("BC ")
                .pattern("A  ")
                .define('A', ModItems.BLIZZARD_HILT.get())
                .define('B', ModItems.BLIZZARD_CORE.get())
                .define('C', ModItems.BLIZZARD_MUZZLE)
                .unlockedBy("avaliproject:has_blizzard_hilt", has(ModItems.BLIZZARD_HILT))
                .unlockedBy("avaliproject:has_blizzard_core", has(ModItems.BLIZZARD_CORE))
                .unlockedBy("avaliproject:has_blizzard_muzzle", has(ModItems.BLIZZARD_MUZZLE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AVALI_DRONE_ROTORS.get())
                .pattern("ACA")
                .pattern("CBC")
                .pattern("ACA")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', Items.IRON_INGOT)
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE))
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AVALI_DRONE_CORE.get())
                .pattern(" B ")
                .pattern("BAB")
                .pattern("CBC")
                .define('A', ModItems.SYNC_CRYSTAL.get())
                .define('B', ModItems.AEROMER.get())
                .define('C', Items.IRON_BARS)
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL))
                .unlockedBy("has_iron_bars", has(Items.IRON_BARS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AVALI_DATA_CHIT.get())
                .pattern("AA ")
                .pattern("AA ")
                .pattern("BB ")
                .define('B', ModItems.SYNC_CRYSTAL.get())
                .define('A', ModItems.LUME.get())
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL))
                .unlockedBy("avaliproject:has_lume", has(ModItems.LUME))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STORM_HILT.get())
                .pattern("A  ")
                .pattern("AC ")
                .pattern("ABC")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.AEROGEL.get())
                .define('C', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL))
                .unlockedBy("avaliproject:has_woven_graphene", has(ModItems.WOVEN_GRAPHENE))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STORM_MUZZLE.get())
                .pattern("ABA")
                .pattern("AAA")
                .pattern("C  ")
                .define('A', ModItems.AEROMER.get())
                .define('C', ModItems.AEROGEL.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL))
                .unlockedBy("avaliproject:has_woven_graphene", has(ModItems.WOVEN_GRAPHENE))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STORM_CORE.get())
                .pattern("BAC")
                .pattern("ABA")
                .pattern("BBB")
                .define('B', ModItems.AEROMER.get())
                .define('A', ModItems.AEROGEL.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLIZZARD_CORE.get())
                .pattern("BAB")
                .pattern("ABA")
                .pattern("BCB")
                .define('B', ModItems.AEROMER.get())
                .define('A', ModItems.AEROGEL.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLIZZARD_MUZZLE.get())
                .pattern("ABA")
                .pattern("BBC")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', Items.IRON_BARS)
                .unlockedBy("avaliproject:has_iron_bars", has(Items.IRON_BARS))
                .unlockedBy("avaliproject:has_woven_graphene", has(ModItems.WOVEN_GRAPHENE))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLIZZARD_HILT.get())
                .pattern("A B")
                .pattern(" C ")
                .pattern("  A")
                .define('A', ModItems.AEROMER.get())
                .define('C', ModItems.AEROGEL.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL))
                .unlockedBy("avaliproject:has_woven_graphene", has(ModItems.WOVEN_GRAPHENE))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER))
                .save(recipeOutput);


        oreSmelting(recipeOutput, ARCAITES_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.ARCAITES_CRYSTAL.get(), 0.25f, 200, "arcaites_crystal");
        oreBlasting(recipeOutput, ARCAITES_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.ARCAITES_CRYSTAL.get(), 0.25f, 100, "arcaites_crystal");
        oreSmelting(recipeOutput, VILOUS_CERAMIC_SMELTABLES, RecipeCategory.MISC, ModItems.VILOUS_CERAMIC_INGOT.get(), 0.25f, 200, "vilous_ceramic");
        oreBlasting(recipeOutput, VILOUS_CERAMIC_SMELTABLES, RecipeCategory.MISC, ModItems.VILOUS_CERAMIC_INGOT.get(), 0.25f, 100, "vilous_ceramic");
        oreSmelting(recipeOutput, DURASTEEL_SMELTABLES, RecipeCategory.MISC, ModItems.DURASTEEL_INGOT.get(), 0.25f, 200, "Durasteel");
        oreBlasting(recipeOutput, DURASTEEL_SMELTABLES, RecipeCategory.MISC, ModItems.DURASTEEL_INGOT.get(), 0.25f, 100, "Durasteel");
        oreSmelting(recipeOutput, AERO_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.AERO_CRYSTAL.get(), 0.25f, 200, "aero_crystal");
        oreBlasting(recipeOutput, AERO_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.AERO_CRYSTAL.get(), 0.25f, 100, "aero_crystal");
        oreSmelting(recipeOutput, THERMAL_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.THERMAL_CRYSTAL.get(), 0.25f, 200, "thermal_crystal");
        oreBlasting(recipeOutput, THERMAL_CRYSTAL_SMELTABLES, RecipeCategory.MISC, ModItems.THERMAL_CRYSTAL.get(), 0.25f, 100, "thermal_crystal");
        oreSmelting(recipeOutput, AEGISALT_SMELTABLES, RecipeCategory.MISC, ModItems.REFINED_AEGISALT.get(), 0.25f, 200, "refined_aegisalt");
        oreBlasting(recipeOutput, AEGISALT_SMELTABLES, RecipeCategory.MISC, ModItems.REFINED_AEGISALT.get(), 0.25f, 100, "refined_aegisalt");
        oreSmelting(recipeOutput, TITANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 0.25f, 200, "titanium");
        oreBlasting(recipeOutput, TITANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 0.25f, 100, "titanium");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_SWORD.get())
                .pattern("T")
                .pattern("T")
                .pattern("S")
                .define('T', ModItems.TITANIUM_INGOT.get())
                .define('S', Items.STICK)
                .unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TITANIUM_PICKAXE.get())
                .pattern("TTT")
                .pattern(" S ")
                .pattern(" S ")
                .define('T', ModItems.TITANIUM_INGOT.get())
                .define('S', Items.STICK)
                .unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TITANIUM_AXE.get())
                .pattern("TT")
                .pattern("TS")
                .pattern(" S")
                .define('T', ModItems.TITANIUM_INGOT.get())
                .define('S', Items.STICK)
                .unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TITANIUM_SHOVEL.get())
                .pattern("T")
                .pattern("S")
                .pattern("S")
                .define('T', ModItems.TITANIUM_INGOT.get())
                .define('S', Items.STICK)
                .unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TITANIUM_HOE.get())
                .pattern("TT")
                .pattern(" S")
                .pattern(" S")
                .define('T', ModItems.TITANIUM_INGOT.get())
                .define('S', Items.STICK)
                .unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_HELMET.get())
                .pattern("TTT")
                .pattern("T T")
                .define('T', ModItems.TITANIUM_INGOT.get())
                .unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_CHESTPLATE.get())
                .pattern("T T")
                .pattern("TTT")
                .pattern("TTT")
                .define('T', ModItems.TITANIUM_INGOT.get())
                .unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_LEGGINGS.get())
                .pattern("TTT")
                .pattern("T T")
                .pattern("T T")
                .define('T', ModItems.TITANIUM_INGOT.get())
                .unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_BOOTS.get())
                .pattern("T T")
                .pattern("T T")
                .define('T', ModItems.TITANIUM_INGOT.get())
                .unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT))
                .save(recipeOutput);
        oreSmelting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.LUME_BIT.get(), 0.25f, 200, "lume_bit");
        oreBlasting(recipeOutput, LUME_SMELTABLES, RecipeCategory.MISC, ModItems.LUME_BIT.get(), 0.25f, 100, "lume_bit");
        oreSmelting(recipeOutput, AGATE_SMELTABLES, RecipeCategory.MISC, ModItems.AGATE.get(), 0.25f, 200, "agate");
        oreBlasting(recipeOutput, AGATE_SMELTABLES, RecipeCategory.MISC, ModItems.AGATE.get(), 0.25f, 100, "agate");
        oreSmelting(recipeOutput, NOVULITE_SMELTABLES, RecipeCategory.MISC, ModItems.NOVULITE.get(), 0.25f, 200, "novulite");
        oreBlasting(recipeOutput, NOVULITE_SMELTABLES, RecipeCategory.MISC, ModItems.NOVULITE.get(), 0.25f, 100, "novulite");


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DRESSING.get())
                .requires(ModItems.RIPPED_DRESSING.get(), 2)
                .unlockedBy("avaliproject:has_ripped_dressing", has(ModItems.RIPPED_DRESSING))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RIPPED_DRESSING.get(), 2)
                .requires(ModItems.FOLIAGE.get())
                .unlockedBy("avaliproject:has_foliage", has(ModItems.FOLIAGE))
                .save(recipeOutput, "avaliproject:ripped_dressing_from_foliage");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHUNK_OF_PLASTIC.get(), 2)
                .requires(ModItems.TRASH_BAG.get())
                .unlockedBy("avaliproject:has_trash_bag", has(ModItems.TRASH_BAG))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLEXIGLASS.get())
                .requires(ModItems.CHUNK_OF_PLASTIC.get(), 4)
                .unlockedBy("avaliproject:has_chunk_of_plastic", has(ModItems.CHUNK_OF_PLASTIC))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BUNDLE_OF_WIRES.get())
                .requires(Items.COPPER_INGOT)
                .requires(ModItems.CHUNK_OF_PLASTIC.get(), 3)
                .unlockedBy("avaliproject:has_chunk_of_plastic", has(ModItems.CHUNK_OF_PLASTIC))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AUTO_INJECTOR.get())
                .pattern(" BD")
                .pattern("AEA")
                .pattern(" C ")
                .define('A', ModItems.FLEXIGLASS.get())
                .define('B', ModItems.SCRAP_TUBE.get())
                .define('C', ModItems.CHUNK_OF_PLASTIC.get())
                .define('D', ModItems.BUNDLE_OF_WIRES.get())
                .define('E', ModItems.CIRCUIT_BOARD.get())
                .unlockedBy("avaliproject:has_bundle_of_wires", has(ModItems.BUNDLE_OF_WIRES))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SCRAP_CUBE.get(), 2)
                .requires(ModItems.SCRAP_METAL.get(), 4)
                .unlockedBy("avaliproject:has_scrap_metal", has(ModItems.SCRAP_METAL))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SCRAP_TUBE.get(), 2)
                .requires(ModItems.SCRAP_CUBE.get(), 4)
                .unlockedBy("avaliproject:has_scrap_cube", has(ModItems.SCRAP_CUBE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SCRAP_PANEL.get(), 2)
                .requires(ModItems.SCRAP_CUBE.get(), 2)
                .unlockedBy("avaliproject:has_scrap_cube", has(ModItems.SCRAP_CUBE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRUISE_KIT.get())
                .requires(ModItems.DRESSING.get())
                .requires(ModItems.OPIUM.get())
                .unlockedBy("avaliproject:has_dressing", has(ModItems.DRESSING))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ROPE.get())
                .requires(ModItems.FOLIAGE.get(), 2)
                .unlockedBy("avaliproject:has_foliage", has(ModItems.FOLIAGE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CANTEEN.get())
                .requires(ModItems.ROPE.get())
                .requires(ModItems.CHUNK_OF_PLASTIC.get(), 2)
                .unlockedBy("avaliproject:has_rope", has(ModItems.ROPE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHEST_DRAIN.get())
                .requires(ModItems.AUTO_INJECTOR.get())
                .requires(ModItems.CHUNK_OF_PLASTIC.get())
                .requires(ModItems.FLEXIGLASS.get(), 2)
                .requires(ModItems.SCRAP_TUBE.get())
                .unlockedBy("avaliproject:has_auto_injector", has(ModItems.AUTO_INJECTOR))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMPTY_BLOOD_BAG.get())
                .pattern(" A ")
                .pattern("BCB")
                .pattern(" D ")
                .define('A', ModItems.CHUNK_OF_PLASTIC.get())
                .define('B', ModItems.FLEXIGLASS.get())
                .define('C', ModItems.BUNDLE_OF_WIRES.get())
                .define('D', ModItems.AUTO_INJECTOR.get())
                .unlockedBy("avaliproject:has_auto_injector", has(ModItems.AUTO_INJECTOR))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LOCKPICKING_KIT.get(), 3)
                .requires(ModItems.SCRAP_PANEL.get())
                .requires(ModItems.SCRAP_TUBE.get())
                .requires(ModItems.CHUNK_OF_PLASTIC.get())
                .unlockedBy("avaliproject:has_scrap_panel", has(ModItems.SCRAP_PANEL))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STERILIZED_DRESSING.get())
                .requires(ModItems.DRESSING.get())
                .requires(ModItems.ANTISEPTIC.get())
                .unlockedBy("avaliproject:has_dressing", has(ModItems.DRESSING))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SYRINGE.get())
                .requires(ModItems.CANTEEN.get())
                .requires(ModItems.CHUNK_OF_PLASTIC.get())
                .requires(ModItems.SCRAP_TUBE.get())
                .unlockedBy("avaliproject:has_canteen", has(ModItems.CANTEEN))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TOURNIQUET.get())
                .requires(ModItems.ROPE.get(), 2)
                .requires(Items.STRING)
                .requires(ModItems.CHUNK_OF_PLASTIC.get())
                .unlockedBy("avaliproject:has_rope", has(ModItems.ROPE))
                .save(recipeOutput);

        buildSpeciesContentRecipes(recipeOutput);

        SpecialRecipeBuilder.special(BiochemRecipe::new).save(recipeOutput, "avaliproject:biochem");
        SpecialRecipeBuilder.special(com.lunkoashtail.avaliproject.recipe.custom.FilterWaterRecipe::new)
                .save(recipeOutput, "avaliproject:filter_water");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COMBAT_PEN.get())
                .requires(ModItems.SYRINGE.get())
                .requires(Items.SUGAR)
                .requires(Items.GLOWSTONE_DUST)
                .requires(Items.REDSTONE)
                .requires(Items.IRON_NUGGET)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE))
                .save(recipeOutput);
        SpecialRecipeBuilder.special(OpiumRecipe::new).save(recipeOutput, "avaliproject:opium");
        SpecialRecipeBuilder.special(MorphineRecipe::new).save(recipeOutput, "avaliproject:morphine");
        SpecialRecipeBuilder.special(SalineRecipe::new).save(recipeOutput, "avaliproject:saline");
        SpecialRecipeBuilder.special(FentanylCraftRecipe::new).save(recipeOutput, "avaliproject:fentanyl_craft");
        SpecialRecipeBuilder.special(WaterBottleRecipe::new).save(recipeOutput, "avaliproject:water_bottle_craft");

    }

    private void buildSpeciesContentRecipes(RecipeOutput recipeOutput) {
        oreSmelting(recipeOutput, List.of(ModBlocks.CRYSTALITHIUM_ORE, ModBlocks.CRYSTALITHIUM_DEEPSLATE_ORE),
                RecipeCategory.MISC, ModItems.CRYSTALITHIUM_CRYSTAL.get(), 0.7f, 200, "crystalithium");
        oreSmelting(recipeOutput, List.of(ModBlocks.MOONSTONE_ORE, ModBlocks.MOONSTONE_DEEPSLATE_ORE),
                RecipeCategory.MISC, ModItems.MOONSTONE.get(), 0.7f, 200, "moonstone");
        oreSmelting(recipeOutput, List.of(ModBlocks.SUNSTONE_ORE, ModBlocks.SUNSTONE_DEEPSLATE_ORE),
                RecipeCategory.MISC, ModItems.SUNSTONE.get(), 0.7f, 200, "sunstone");
        oreSmelting(recipeOutput, List.of(ModBlocks.LARIMAR_ORE, ModBlocks.LARIMAR_DEEPSLATE_ORE),
                RecipeCategory.MISC, ModItems.LARIMAR.get(), 1.0f, 200, "larimar");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BONE_JEWELRY.get())
                .requires(Items.BONE)
                .requires(ModItems.SHELL.get())
                .unlockedBy("avaliproject:has_shell", has(ModItems.SHELL))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PENDANT.get())
                .requires(ModItems.PEARL.get())
                .requires(Items.STRING)
                .requires(ModBlocks.SEA_GLASS.get())
                .unlockedBy("avaliproject:has_pearl", has(ModItems.PEARL))
                .save(recipeOutput);

        List<ItemLike> trinketGems = List.of(ModItems.MOONSTONE, ModItems.SUNSTONE, ModItems.LARIMAR, ModItems.SEA_OPAL,
                ModItems.JADEITE, ModItems.CARNELIAN, ModItems.AMMOLITE, ModItems.ABALONE_SHARD);
        for (ItemLike gem : trinketGems) {
            String gemName = getItemName(gem);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,
                            TrinketItem.withGem(new ItemStack(ModItems.MANOKIT_TRINKET.get()), gemName))
                    .requires(gem)
                    .requires(ModItems.BONE_JEWELRY.get())
                    .unlockedBy("avaliproject:has_bone_jewelry", has(ModItems.BONE_JEWELRY))
                    .save(recipeOutput, "avaliproject:manokit_trinket_" + gemName);
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CRYSTALITHIUM_TRINKET.get())
                .requires(ModItems.CRYSTALITHIUM_CRYSTAL.get(), 2)
                .requires(ModItems.CLAW_SHARD.get())
                .requires(Items.STRING)
                .unlockedBy("avaliproject:has_crystalithium_crystal", has(ModItems.CRYSTALITHIUM_CRYSTAL))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SEA_GLASS.get())
                .requires(Items.GLASS)
                .requires(ModItems.CORAL_PIECE.get())
                .unlockedBy("avaliproject:has_coral_piece", has(ModItems.CORAL_PIECE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CORAL_PIECE.get(), 2)
                .requires(Ingredient.of(Items.TUBE_CORAL, Items.BRAIN_CORAL, Items.BUBBLE_CORAL, Items.FIRE_CORAL, Items.HORN_CORAL,
                        Items.TUBE_CORAL_FAN, Items.BRAIN_CORAL_FAN, Items.BUBBLE_CORAL_FAN, Items.FIRE_CORAL_FAN, Items.HORN_CORAL_FAN))
                .unlockedBy("avaliproject:has_coral", has(Items.BRAIN_CORAL))
                .save(recipeOutput);

        if (!DisabledContent.isDisabled(ModBlocks.CORAL_CULTIVATION_BLOCK)) {
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.CORAL_CULTIVATION_BLOCK.get())
                    .pattern("CGC")
                    .pattern("GPG")
                    .pattern("CGC")
                    .define('C', ModItems.CORAL_PIECE.get())
                    .define('G', ModBlocks.SEA_GLASS.get())
                    .define('P', ModItems.PEARL.get())
                    .unlockedBy("avaliproject:has_pearl", has(ModItems.PEARL))
                    .save(recipeOutput, "avaliproject:coral_cultivation_block_from_pearl");
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.CORAL_CULTIVATION_BLOCK.get())
                    .pattern("CGC")
                    .pattern("GMG")
                    .pattern("CGC")
                    .define('C', ModItems.CORAL_PIECE.get())
                    .define('G', ModBlocks.SEA_GLASS.get())
                    .define('M', ModItems.MOONSTONE.get())
                    .unlockedBy("avaliproject:has_moonstone", has(ModItems.MOONSTONE))
                    .save(recipeOutput, "avaliproject:coral_cultivation_block_from_moonstone");
        }
        if (!DisabledContent.isDisabled(ModBlocks.MOONLIT_OFFERING_ALTAR)) {
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.MOONLIT_OFFERING_ALTAR.get())
                    .pattern(" M ")
                    .pattern("GSG")
                    .pattern("SSS")
                    .define('M', ModItems.MOONSTONE.get())
                    .define('G', ModBlocks.SEA_GLASS.get())
                    .define('S', Items.SMOOTH_STONE)
                    .unlockedBy("avaliproject:has_moonstone", has(ModItems.MOONSTONE))
                    .save(recipeOutput);
        }

        serum(recipeOutput, ModItems.HUMAN_SERUM.get(), Items.APPLE);
        serum(recipeOutput, ModItems.EXPIE_SERUM.get(), ModItems.GEOFRUIT.get());
        serum(recipeOutput, ModItems.AVALI_SERUM.get(), ModItems.LUME.get());
        serum(recipeOutput, ModItems.SERGAL_SERUM.get(), ModItems.AGATE.get());
        serum(recipeOutput, ModItems.PROTOGEN_SERUM.get(), ModItems.NOVULITE.get());
        serum(recipeOutput, ModItems.WICKERBEAST_SERUM.get(), ModItems.MANE_TUFT.get());
        serum(recipeOutput, ModItems.MANOKIT_SERUM.get(), ModItems.SEA_OPAL.get());

        ModBlocks.WOOD_SETS.forEach(set -> woodSet(recipeOutput, set));
    }

    private void woodSet(RecipeOutput recipeOutput, WoodSet set) {
        ItemLike planks = set.planks.get();
        Ingredient planksIngredient = Ingredient.of(planks);
        String hasPlanks = getHasName(planks);

        planksFromLogs(recipeOutput, planks, set.logsItemTag, 4);
        woodFromLogs(recipeOutput, set.wood.get(), set.log.get());
        stairBuilder(set.stairs.get(), planksIngredient).unlockedBy(hasPlanks, has(planks)).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, set.slab.get(), planks);
        fenceBuilder(set.fence.get(), planksIngredient).unlockedBy(hasPlanks, has(planks)).save(recipeOutput);
        fenceGateBuilder(set.fenceGate.get(), planksIngredient).unlockedBy(hasPlanks, has(planks)).save(recipeOutput);
        buttonBuilder(set.button.get(), planksIngredient).unlockedBy(hasPlanks, has(planks)).save(recipeOutput);
        pressurePlate(recipeOutput, set.pressurePlate.get(), planks);
        signBuilder(set.signItem.get(), planksIngredient).unlockedBy(hasPlanks, has(planks)).save(recipeOutput);
        hangingSign(recipeOutput, set.hangingSignItem.get(), set.log.get());
    }

    private void storageBlock(RecipeOutput recipeOutput, ItemLike gem, ItemLike block) {
        String gemName = getItemName(gem);
        String blockName = getItemName(block);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', gem)
                .unlockedBy("avaliproject:has_" + gemName, has(gem))
                .save(recipeOutput, "avaliproject:" + blockName + "_from_" + gemName);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, gem, 9)
                .requires(block)
                .unlockedBy("avaliproject:has_" + blockName, has(block))
                .save(recipeOutput, "avaliproject:" + gemName + "_from_" + blockName);
    }

    private void serum(RecipeOutput recipeOutput, ItemLike serum, ItemLike speciesDrop) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, serum)
                .requires(Items.GLASS_BOTTLE)
                .requires(speciesDrop)
                .unlockedBy("avaliproject:has_" + getItemName(speciesDrop), has(speciesDrop))
                .save(recipeOutput);
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
