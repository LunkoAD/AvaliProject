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
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        List<ItemLike> LUME_SMELTABLES = List.of(
                ModBlocks.LUME_ORE.get(), ModBlocks.LUME_DEEPSLATE_ORE.get());
        List<ItemLike> TITANIUM_SMELTABLES = List.of(
                ModBlocks.TITANIUM_ORE.get(), ModBlocks.TITANIUM_DEEPSLATE_ORE.get(), ModItems.RAW_TITANIUM.get());
        List<ItemLike> DURASTEEL_SMELTABLES = List.of(
                ModBlocks.DURASTEEL_ORE.get(),ModBlocks.DURASTEEL_DEEPSLATE_ORE.get(), ModItems.RAW_DURASTEEL.get());
        List<ItemLike> AERO_CRYSTAL_SMELTABLES = List.of(
                ModBlocks.AERO_CRYSTAL_ORE.get(), ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get());
        List<ItemLike> SYNC_CRYSTAL_SMELTABLES = List.of(
                ModBlocks.SYNC_CRYSTAL_ORE.get(), ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE.get());
        List<ItemLike> THERMAL_CRYSTAL_SMELTABLES = List.of(
                ModBlocks.THERMAL_CRYSTAL_ORE.get(), ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get());
        List<ItemLike> AEGISALT_SMELTABLES = List.of(
                ModBlocks.AEGISALT_ORE.get(), ModBlocks.AEGISALT_DEEPSLATE_ORE.get(), ModItems.RAW_AEGISALT.get());
        List<ItemLike> VILOUS_CERAMIC_SMELTABLES = List.of(
                ModBlocks.VILOUS_CERAMIC_ORE.get(), ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.get(), ModItems.VILOUS_CLAY.get());
        List<ItemLike> ARCAITES_CRYSTAL_SMELTABLES = List.of(
                ModBlocks.ARCAITES_CRYSTAL_ORE.get(), ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE.get());
        List<ItemLike> NOVULITE_SMELTABLES = List.of(
                ModBlocks.NOVULITE_ORE.get(), ModBlocks.NOVULITE_DEEPSLATE_ORE.get());
        List<ItemLike> AGATE_SMELTABLES = List.of(
                ModBlocks.AGATE_ORE.get(), ModBlocks.AGATE_DEEPSLATE_ORE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LUME.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.LUME_BIT.get())
                .unlockedBy("avaliproject:has_lume_bit", has(ModItems.LUME_BIT.get()))
                .save(recipeOutput, "avaliproject:lume_from_lume_bits");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LUME_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.LUME.get())
                .unlockedBy("avaliproject:has_lume", has(ModItems.LUME.get()))
                .save(recipeOutput, "avaliproject:lume_block_from_lume");

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.AVALI_SWORD.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" B ")
                .define('A', ModItems.AEROGEL.get())
                .define('B', ModItems.AEROMER.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL.get()))
                .unlockedBy("has_aeromer", has(ModItems.AEROMER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.AVALI_PICKAXE.get())
                .pattern("ACC")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', ModItems.AEROGEL.get())
                .define('B', ModItems.AEROMER.get())
                .define('C', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL.get()))
                .unlockedBy("has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("has_woven_graphene", has(ModItems.WOVEN_GRAPHENE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.AVALI_HOE.get())
                .pattern(" AC")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', ModItems.AEROGEL.get())
                .define('B', ModItems.AEROMER.get())
                .define('C', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL.get()))
                .unlockedBy("has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("has_woven_graphene", has(ModItems.WOVEN_GRAPHENE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SERGAL_SWORD.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" B ")
                .define('A', ModItems.VILOUS_CERAMIC_INGOT.get())
                .define('B', Items.STICK)
                .unlockedBy("has_vilous", has(ModItems.VILOUS_CERAMIC_INGOT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.VILOUS_CERAMIC_INGOT_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.VILOUS_CERAMIC_INGOT.get())
                .unlockedBy("has_vilous", has(ModItems.VILOUS_CERAMIC_INGOT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SERGAL_MACE.get())
                .pattern("ACA")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', ModItems.VILOUS_CERAMIC_INGOT.get())
                .define('B', Items.STICK)
                .define('C', ModBlocks.VILOUS_CERAMIC_INGOT_BLOCK.get())
                .unlockedBy("has_vilous_block", has(ModBlocks.VILOUS_CERAMIC_INGOT_BLOCK.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SERGAL_GREATSWORD.get())
                .pattern("AA ")
                .pattern("AA ")
                .pattern(" B ")
                .define('A', ModItems.VILOUS_CERAMIC_INGOT.get())
                .define('B', Items.STICK)
                .unlockedBy("has_vilous", has(ModItems.VILOUS_CERAMIC_INGOT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SERGAL_LANCE.get())
                .pattern(" A ")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', ModItems.VILOUS_CERAMIC_INGOT.get())
                .define('B', Items.STICK)
                .unlockedBy("has_vilous", has(ModItems.VILOUS_CERAMIC_INGOT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.AVALI_AXE.get())
                .pattern(" CA")
                .pattern(" BA")
                .pattern(" B ")
                .define('A', ModItems.AEROGEL.get())
                .define('B', ModItems.AEROMER.get())
                .define('C', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL.get()))
                .unlockedBy("has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("has_woven_graphene", has(ModItems.WOVEN_GRAPHENE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AEROMER.get())
                .pattern("BAB")
                .pattern("ABA")
                .pattern("BAB")
                .define('A', ModItems.AERO_CRYSTAL.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("has_aero_crystal", has(ModItems.AERO_CRYSTAL.get()))
                .unlockedBy("has_woven_graphene", has(ModItems.WOVEN_GRAPHENE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AEROGEL.get(), 3)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.AERO_CRYSTAL.get())
                .unlockedBy("avaliproject:has_aero_crystal", has(ModItems.AERO_CRYSTAL.get()))
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
                .unlockedBy("has_nakati_bark", has(ModItems.NAKATI_BARK.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.AVALON_TACO.get())
                .pattern("ABA")
                .pattern(" A ")
                .define('A', ModItems.PIRU_FLOUR.get())
                .define('B', ModItems.SPICY_JERKY.get())
                .unlockedBy("has_piru_flour", has(ModItems.PIRU_FLOUR.get()))
                .unlockedBy("has_spicy_jerky", has(ModItems.SPICY_JERKY.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PROTOSTEEL_INGOT.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', ModItems.NANITE_INJECTOR.get())
                .define('B', ModItems.DURASTEEL_INGOT.get())
                .unlockedBy("has_nanite_injector", has(ModItems.NANITE_INJECTOR.get()))
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT.get()))
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
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT.get()))
                .unlockedBy("has_spicy_jerky", has(ModItems.SPICY_JERKY.get()))
                .unlockedBy("has_piru_flour", has(ModItems.PIRU_FLOUR.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.KIRI_CIDER.get())
                .pattern(" A ")
                .pattern("CBC")
                .pattern(" A ")
                .define('A', ModItems.NAKATI_BARK.get())
                .define('B', ModItems.AVALI_BOTTLE.get())
                .define('C', ModItems.KIRI_FRUIT.get())
                .unlockedBy("has_avali_bottle", has(ModItems.AVALI_BOTTLE.get()))
                .unlockedBy("has_nakati_bark", has(ModItems.NAKATI_BARK.get()))
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.KIRI_JAM.get())
                .pattern("BAB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A', ModItems.KIRI_FRUIT.get())
                .define('B', Items.GLASS_PANE)
                .unlockedBy("has_bowl", has(Items.GLASS_PANE))
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.KIRI_CURRY.get())
                .pattern("AD ")
                .pattern("BC ")
                .define('A', ModItems.KIRI_FRUIT.get())
                .define('B', Items.BOWL)
                .define('C', ModItems.SPICY_JERKY.get())
                .define('D', ModItems.PIRU_NOODLE.get())
                .unlockedBy("has_piru_noodle", has(ModItems.PIRU_NOODLE.get()))
                .unlockedBy("has_spicy_jerky", has(ModItems.SPICY_JERKY.get()))
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT.get()))
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
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT.get()))
                .unlockedBy("has_piru_flour", has(ModItems.PIRU_FLOUR.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.GROOU_JUICE.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', ModItems.GROOU.get())
                .define('B', ModItems.AVALI_BOTTLE.get())
                .unlockedBy("has_avali_bottle", has(ModItems.AVALI_BOTTLE.get()))
                .unlockedBy("has_groou", has(ModItems.GROOU.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AVALI_BOTTLE.get())
                .pattern("C C")
                .pattern(" C ")
                .define('C', ModItems.AEROGEL.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.PIRU_NOODLE.get())
                .pattern("CBC")
                .pattern("BCB")
                .pattern("CBC")
                .define('C', ModItems.PIRU_FLOUR.get())
                .define('B', ModItems.KIRI_FRUIT.get())
                .unlockedBy("has_piru_flour", has(ModItems.PIRU_FLOUR.get()))
                .unlockedBy("has_kiri_fruit", has(ModItems.KIRI_FRUIT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOVEN_FABRIC.get())
                .pattern("BB ")
                .pattern("BB ")
                .define('B', ModItems.FIBER.get())
                .unlockedBy("has_fiber", has(ModItems.FIBER.get()))
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
                .unlockedBy("has_piru_flour", has(ModItems.PIRU_FLOUR.get()))
                .unlockedBy("has_kiri_jam", has(ModItems.KIRI_JAM.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NANITE_INJECTOR.get(), 2)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('B', Items.DIAMOND)
                .define('A', ModItems.DURASTEEL_INGOT.get())
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.PIRU_FLOUR.get(), 2)
                .requires(ModItems.PIRU_FROND.get())
                .unlockedBy("avaliproject:has_piru_frond", has(ModItems.PIRU_FROND.get()))
                .save(recipeOutput, "avaliproject:piru_flour_from_piru_frond");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LUME_BIT.get(), 9)
                .requires(ModItems.LUME.get())
                .unlockedBy("avaliproject:has_lume", has(ModItems.LUME.get()))
                .save(recipeOutput, "avaliproject:lume_bits_from_lume");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LUME.get(), 9)
                .requires(ModBlocks.LUME_BLOCK.get())
                .unlockedBy("avaliproject:has_lume_from_lume_block", has(ModBlocks.LUME_BLOCK.get()))
                .save(recipeOutput, "avaliproject:lume_from_lume_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SUGAR, 3)
                .requires(ModItems.GROOU.get())
                .unlockedBy("has_groou", has(ModItems.GROOU.get()))
                .save(recipeOutput, "avaliproject:sugar_from_groou");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.GROOU.get(), 1)
                .requires(ModBlocks.GROOU_NODULE.get())
                .unlockedBy("has_groou_nodule", has(ModBlocks.GROOU_NODULE.get()))
                .save(recipeOutput, "avaliproject:groou_from_groou_nodule");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.PIRU_COLONY.get(), 1)
                .requires(ModBlocks.PIRU_NODULE.get())
                .unlockedBy("has_groou_nodule", has(ModBlocks.PIRU_NODULE.get()))
                .save(recipeOutput, "avaliproject:piru_colony_from_piru_nodule");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.NAKATI_OVOID.get(), 1)
                .requires(ModBlocks.NAKATI_NODULE.get())
                .unlockedBy("has_groou_nodule", has(ModBlocks.NAKATI_NODULE.get()))
                .save(recipeOutput, "avaliproject:nakati_ovoid_from_nakati_nodule");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.KIRI_FRUIT.get(), 1)
                .requires(ModBlocks.KIRI_NODULE.get())
                .unlockedBy("has_kiri_nodule", has(ModBlocks.KIRI_NODULE.get()))
                .save(recipeOutput, "avaliproject:kiri_fruit_from_kiri_nodule");
    ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SERGAL_CHEESE.get(), 4)
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
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.PROTOGEN_AXE.get())
                .pattern(" BB")
                .pattern(" CA")
                .pattern(" C ")
                .define('C', ModItems.DURASTEEL_INGOT.get())
                .define('B', ModItems.PROTOSTEEL_INGOT.get())
                .define('A', ModItems.NANITE_INJECTOR.get())
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT.get()))
                .unlockedBy("has_protosteel_ingot", has(ModItems.PROTOSTEEL_INGOT.get()))
                .unlockedBy("has_nanite_injector", has(ModItems.NANITE_INJECTOR.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.PROTOGEN_SWORD.get())
                .pattern(" C ")
                .pattern("ACA")
                .pattern(" B ")
                .define('B', ModItems.DURASTEEL_INGOT.get())
                .define('C', ModItems.PROTOSTEEL_INGOT.get())
                .define('A', ModItems.NANITE_INJECTOR.get())
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT.get()))
                .unlockedBy("has_protosteel_ingot", has(ModItems.PROTOSTEEL_INGOT.get()))
                .unlockedBy("has_nanite_injector", has(ModItems.NANITE_INJECTOR.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.AVALI_SPEAR.get())
                .pattern("  A")
                .pattern("DC ")
                .pattern("C  ")
                .define('A', ModItems.AEROGEL.get())
                .define('C', ModItems.AEROMER.get())
                .define('D', ModItems.REFINED_AEGISALT.get())
                .unlockedBy("has_aerogel", has(ModItems.AEROGEL.get()))
                .unlockedBy("has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("has_refined_aegisalt", has(ModItems.REFINED_AEGISALT.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NOVULITE_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.NOVULITE.get())
                .unlockedBy("avaliproject:has_novulite", has(ModItems.NOVULITE.get()))
                .save(recipeOutput, "avaliproject:novulite_block_from_novulite");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NOVULITE.get(), 9)
                .requires(ModBlocks.NOVULITE_BLOCK.get())
                .unlockedBy("avaliproject:has_novulite_block", has(ModBlocks.NOVULITE_BLOCK.get()))
                .save(recipeOutput, "avaliproject:novulite_from_novulite_block");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AGATE_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.AGATE.get())
                .unlockedBy("avaliproject:has_agate", has(ModItems.AGATE.get()))
                .save(recipeOutput, "avaliproject:agate_block_from_agate");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AGATE.get(), 9)
                .requires(ModBlocks.AGATE_BLOCK.get())
                .unlockedBy("avaliproject:has_agate_block", has(ModBlocks.AGATE_BLOCK.get()))
                .save(recipeOutput, "avaliproject:agate_from_agate_block");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AVALI_DANCE_MUSIC_DISC.get())
                .pattern("CAC")
                .pattern("ADA")
                .pattern("CAC")
                .define('A', ModItems.SYNC_CRYSTAL.get())
                .define('C', Items.FLINT)
                .define('D', Items.AMETHYST_SHARD)
                .unlockedBy("has_sync_crystal", has(ModItems.SYNC_CRYSTAL.get()))
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
                .unlockedBy("has_agate", has(ModItems.AGATE.get()))
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
                .unlockedBy("has_novulite", has(ModItems.NOVULITE.get()))
                .unlockedBy("has_flint", has(Items.FLINT))
                .unlockedBy("has_amethyst_crystal", has(Items.AMETHYST_SHARD))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.SOFT_TAPESTRY.get(), 9)
                .pattern("BAB")
                .pattern("ABA")
                .pattern("BAB")
                .define('A', Items.ORANGE_WOOL)
                .define('B', Items.YELLOW_WOOL)
                .unlockedBy("has_orange_wool", has(Items.ORANGE_WOOL))
                .unlockedBy("has_yellow_wool", has(Items.YELLOW_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_FABRIC_BLOCK.get(), 9)
                .pattern("BBA")
                .pattern("BAB")
                .pattern("BBA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ALT_AVALI_FABRIC_BLOCK.get(), 9)
                .pattern("BBB")
                .pattern("ABA")
                .pattern("BAB")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.PROTOGEN_SUPPORT_BLOCK.get(), 9)
                .pattern("BAB")
                .pattern("BAB")
                .pattern("BAB")
                .define('A', Items.GRAY_CONCRETE)
                .define('B', Items.BLACK_CONCRETE)
                .unlockedBy("has_gray_concrete", has(Items.GRAY_CONCRETE))
                .unlockedBy("has_black_concrete", has(Items.BLACK_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ALT_PROTOGEN_SUPPORT_BLOCK.get(), 9)
                .pattern("AAA")
                .pattern("BBB")
                .pattern("AAA")
                .define('A', Items.GRAY_CONCRETE)
                .define('B', Items.BLACK_CONCRETE)
                .unlockedBy("has_gray_concrete", has(Items.GRAY_CONCRETE))
                .unlockedBy("has_black_concrete", has(Items.BLACK_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.DETAILED_PROTOGEN_BLOCK.get(), 9)
                .pattern("BAB")
                .pattern("BAB")
                .pattern("BAB")
                .define('A', Items.GRAY_CONCRETE)
                .define('B', Items.BLACK_CONCRETE)
                .unlockedBy("has_black_concrete", has(Items.BLACK_CONCRETE))
                .unlockedBy("has_light_blue_concrete", has(Items.LIGHT_BLUE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ALT_DETAILED_PROTOGEN_BLOCK.get(), 9)
                .pattern("AAA")
                .pattern("BBB")
                .pattern("AAA")
                .define('A', Items.BLACK_CONCRETE)
                .define('B', Items.LIGHT_BLUE_CONCRETE)
                .unlockedBy("has_black_concrete", has(Items.BLACK_CONCRETE))
                .unlockedBy("has_light_blue_concrete", has(Items.LIGHT_BLUE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_PATTERN_BLOCK_1.get(), 9)
                .pattern("AAB")
                .pattern("BAA")
                .pattern("ABA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_PATTERN_BLOCK_2.get(), 9)
                .pattern("ABA")
                .pattern("AAB")
                .pattern("BAA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_PATTERN_BLOCK_3.get(), 9)
                .pattern("ABA")
                .pattern("ABA")
                .pattern("ABA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_PATTERN_BLOCK_4.get(), 9)
                .pattern("BAB")
                .pattern("ABA")
                .pattern("ABA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_WALL_PATTERN_BLOCK_1.get(), 9)
                .pattern("AAA")
                .pattern("BAB")
                .pattern("ABA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_WALL_PATTERN_BLOCK_2.get(), 9)
                .pattern("AAA")
                .pattern("BBA")
                .pattern("AAB")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_WALL_PATTERN_BLOCK_3.get(), 9)
                .pattern("AAA")
                .pattern("BBB")
                .pattern("AAA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_WALL_PATTERN_BLOCK_4.get(), 9)
                .pattern("BAA")
                .pattern("ABB")
                .pattern("ABA")
                .define('A', Items.ORANGE_CONCRETE)
                .define('B', Items.WHITE_CONCRETE)
                .unlockedBy("has_orange_concrete", has(Items.ORANGE_CONCRETE))
                .unlockedBy("has_white_concrete", has(Items.WHITE_CONCRETE))
                .save(recipeOutput);



        /*ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_1.get(), 9)
                .pattern("AAB")
                .pattern("ABA")
                .pattern("ABB")
                .define('B', Items.WHITE_WOOL)
                .define('A', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_2.get(), 9)
                .pattern("BAA")
                .pattern("ABB")
                .pattern("BBA")
                .define('A', Items.WHITE_WOOL)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_3.get(), 9)
                .pattern("ABB")
                .pattern("BAA")
                .pattern("ABA")
                .define('A', Items.WHITE_WOOL)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_4.get(), 9)
                .pattern("BAB")
                .pattern("BAA")
                .pattern("ABB")
                .define('A', Items.WHITE_WOOL)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_5.get(), 9)
                .pattern("BAA")
                .pattern("ABA")
                .pattern("BBA")
                .define('A', Items.WHITE_WOOL)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_NIGHTLY_FABRIC_6.get(), 9)
                .pattern("ABB")
                .pattern("AAB")
                .pattern("ABB")
                .define('A', Items.WHITE_WOOL)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_CARVING_1.get(), 9)
                .pattern("AAA")
                .pattern("BAA")
                .pattern("BBA")
                .define('A', Items.ACACIA_PLANKS)
                .define('B', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_acacia_planks", has(Items.ACACIA_PLANKS))
                .unlockedBy("has_dark_oak_planks", has(Items.DARK_OAK_PLANKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_CARVING_2.get(), 9)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("BBA")
                .define('A', Items.ACACIA_PLANKS)
                .define('B', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_acacia_planks", has(Items.ACACIA_PLANKS))
                .unlockedBy("has_dark_oak_planks", has(Items.DARK_OAK_PLANKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_CARVING_3.get(), 9)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("BBB")
                .define('A', Items.ACACIA_PLANKS)
                .define('B', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_acacia_planks", has(Items.ACACIA_PLANKS))
                .unlockedBy("has_dark_oak_planks", has(Items.DARK_OAK_PLANKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_CARVING_4.get(), 9)
                .pattern("AAA")
                .pattern("AAB")
                .pattern("BBB")
                .define('A', Items.ACACIA_PLANKS)
                .define('B', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_acacia_planks", has(Items.ACACIA_PLANKS))
                .unlockedBy("has_dark_oak_planks", has(Items.DARK_OAK_PLANKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AVALI_CARVING_5.get(), 9)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("ABB")
                .define('A', Items.ACACIA_PLANKS)
                .define('B', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_acacia_planks", has(Items.ACACIA_PLANKS))
                .unlockedBy("has_dark_oak_planks", has(Items.DARK_OAK_PLANKS))
                .save(recipeOutput);*/


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MLSERIES_HILT.get())
                .pattern("ABB")
                .pattern("AAA")
                .pattern("BAA")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MLSERIES_MUZZLE.get())
                .pattern(" AA")
                .pattern("BAA")
                .pattern(" AA")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.AEROGEL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MLSERIES_MAIN.get())
                .pattern("AA ")
                .pattern("ACB")
                .pattern(" D ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .define('D', ModItems.AEROGEL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL.get()))
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRELANCE_SCOPE.get())
                .pattern("ABA")
                .pattern("BDB")
                .pattern(" C ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', Items.IRON_INGOT)
                .define('D', Items.SPYGLASS)
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_spyglass", has(Items.SPYGLASS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRELANCE_MUZZLE.get())
                .pattern("AAA")
                .pattern("ABB")
                .pattern("A  ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRELANCE_HILT.get())
                .pattern("BB ")
                .pattern("A A")
                .pattern("AA ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRELANCE_MAIN.get())
                .pattern("BB ")
                .pattern("DCB")
                .pattern("BB ")
                .define('B', ModItems.AEROMER.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .define('D', ModItems.AEROGEL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL.get()))
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MLSERIES.get())
                .pattern("   ")
                .pattern("ABC")
                .define('A', ModItems.MLSERIES_MUZZLE.get())
                .define('B', ModItems.MLSERIES_MAIN.get())
                .define('C', ModItems.MLSERIES_HILT.get())
                .unlockedBy("avaliproject:has_mlseries_muzzle", has(ModItems.MLSERIES_MUZZLE.get()))
                .unlockedBy("avaliproject:has_mlseries_main", has(ModItems.MLSERIES_MAIN.get()))
                .unlockedBy("avaliproject:has_mlseries_hilt", has(ModItems.MLSERIES_HILT.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.FIRELANCE.get())
                .pattern(" D ")
                .pattern("ABC")
                .define('A', ModItems.FIRELANCE_HILT.get())
                .define('B', ModItems.FIRELANCE_MAIN.get())
                .define('C', ModItems.FIRELANCE_MUZZLE.get())
                .define('D', ModItems.FIRELANCE_SCOPE.get())
                .unlockedBy("avaliproject:has_firelance_hilt", has(ModItems.FIRELANCE_HILT.get()))
                .unlockedBy("avaliproject:has_firelance_main", has(ModItems.FIRELANCE_MAIN.get()))
                .unlockedBy("avaliproject:has_firelance_muzzle", has(ModItems.FIRELANCE_MUZZLE.get()))
                .unlockedBy("avaliproject:has_firelance_scope", has(ModItems.FIRELANCE_SCOPE.get()))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.QRC_MUZZLE.get())
                .pattern("AAA")
                .pattern("CBA")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', Items.IRON_BARS)
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .unlockedBy("has_iron_bars", has(Items.IRON_BARS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.QRC_MAIN.get())
                .pattern("AAA")
                .pattern("ACA")
                .pattern("AB ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.QRC_HILT.get())
                .pattern("AAA")
                .pattern("BBA")
                .pattern(" BA")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NOVA_MUZZLE.get())
                .pattern("BAB")
                .pattern("ABC")
                .define('B', ModItems.AEROMER.get())
                .define('A', ModItems.WOVEN_GRAPHENE.get())
                .define('C', Items.IRON_BARS)
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .unlockedBy("has_iron_bars", has(Items.IRON_BARS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NOVA_HILT.get())
                .pattern("ABA")
                .pattern("AAA")
                .pattern("AB ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NOVA_MAIN.get())
                .pattern("ABA")
                .pattern("AC ")
                .pattern("AA ")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NOVA_SCOPE.get())
                .pattern(" BD")
                .pattern("BBB")
                .define('B', ModItems.AEROMER.get())
                .define('D', ModItems.AEROGEL.get())
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.QRC.get())
                .pattern("ABC")
                .define('A', ModItems.QRC_MUZZLE.get())
                .define('B', ModItems.QRC_MAIN.get())
                .define('C', ModItems.QRC_HILT.get())
                .unlockedBy("avaliproject:has_qrc_muzzle", has(ModItems.QRC_MUZZLE.get()))
                .unlockedBy("avaliproject:has_qrc_main", has(ModItems.QRC_MAIN.get()))
                .unlockedBy("avaliproject:has_qrc_hilt", has(ModItems.QRC_HILT.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.NOVA.get())
                .pattern(" D ")
                .pattern("ABC")
                .define('A', ModItems.NOVA_HILT.get())
                .define('B', ModItems.NOVA_MAIN.get())
                .define('C', ModItems.NOVA_MUZZLE.get())
                .define('D', ModItems.NOVA_SCOPE.get())
                .unlockedBy("avaliproject:has_nova_hilt", has(ModItems.NOVA_HILT.get()))
                .unlockedBy("avaliproject:has_nova_main", has(ModItems.NOVA_MAIN.get()))
                .unlockedBy("avaliproject:has_nova_muzzle", has(ModItems.NOVA_MUZZLE.get()))
                .unlockedBy("avaliproject:has_nova_scope", has(ModItems.NOVA_SCOPE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.AVALI_DRONE.get())
                .pattern("BAB")
                .define('A', ModItems.AVALI_DRONE_CORE.get())
                .define('B', ModItems.AVALI_DRONE_ROTORS.get())
                .unlockedBy("avaliproject:has_avali_drone_core", has(ModItems.AVALI_DRONE_CORE.get()))
                .unlockedBy("avaliproject:has_avali_drone_rotors", has(ModItems.AVALI_DRONE_ROTORS.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.STORM.get())
                .pattern("BC ")
                .pattern("A  ")
                .define('A', ModItems.STORM_HILT.get())
                .define('B', ModItems.STORM_CORE.get())
                .define('C', ModItems.STORM_MUZZLE.get())
                .unlockedBy("avaliproject:has_storm_hilt", has(ModItems.STORM_HILT.get()))
                .unlockedBy("avaliproject:has_storm_core", has(ModItems.STORM_CORE.get()))
                .unlockedBy("avaliproject:has_storm_muzzle", has(ModItems.STORM_MUZZLE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BLIZZARD.get())
                .pattern("BC ")
                .pattern("A  ")
                .define('A', ModItems.BLIZZARD_HILT.get())
                .define('B', ModItems.BLIZZARD_CORE.get())
                .define('C', ModItems.BLIZZARD_MUZZLE.get())
                .unlockedBy("avaliproject:has_blizzard_hilt", has(ModItems.BLIZZARD_HILT.get()))
                .unlockedBy("avaliproject:has_blizzard_core", has(ModItems.BLIZZARD_CORE.get()))
                .unlockedBy("avaliproject:has_blizzard_muzzle", has(ModItems.BLIZZARD_MUZZLE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AVALI_DRONE_ROTORS.get())
                .pattern("ACA")
                .pattern("CBC")
                .pattern("ACA")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', Items.IRON_INGOT)
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_woven_grapene", has(ModItems.WOVEN_GRAPHENE.get()))
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AVALI_DRONE_CORE.get())
                .pattern(" B ")
                .pattern("BAB")
                .pattern("CBC")
                .define('A', ModItems.SYNC_CRYSTAL.get())
                .define('B', ModItems.AEROMER.get())
                .define('C', Items.IRON_BARS)
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL.get()))
                .unlockedBy("has_iron_bars", has(Items.IRON_BARS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AVALI_DATA_CHIT.get())
                .pattern("AA ")
                .pattern("AA ")
                .pattern("BB ")
                .define('B', ModItems.SYNC_CRYSTAL.get())
                .define('A', ModItems.LUME.get())
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL.get()))
                .unlockedBy("avaliproject:has_lume", has(ModItems.LUME.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STORM_HILT.get())
                .pattern("A  ")
                .pattern("AC ")
                .pattern("ABC")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.AEROGEL.get())
                .define('C', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL.get()))
                .unlockedBy("avaliproject:has_woven_graphene", has(ModItems.WOVEN_GRAPHENE.get()))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STORM_MUZZLE.get())
                .pattern("ABA")
                .pattern("AAA")
                .pattern("C  ")
                .define('A', ModItems.AEROMER.get())
                .define('C', ModItems.AEROGEL.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL.get()))
                .unlockedBy("avaliproject:has_woven_graphene", has(ModItems.WOVEN_GRAPHENE.get()))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STORM_CORE.get())
                .pattern("BAC")
                .pattern("ABA")
                .pattern("BBB")
                .define('B', ModItems.AEROMER.get())
                .define('A', ModItems.AEROGEL.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL.get()))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL.get()))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLIZZARD_CORE.get())
                .pattern("BAB")
                .pattern("ABA")
                .pattern("BCB")
                .define('B', ModItems.AEROMER.get())
                .define('A', ModItems.AEROGEL.get())
                .define('C', ModItems.SYNC_CRYSTAL.get())
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL.get()))
                .unlockedBy("avaliproject:has_sync_crystal", has(ModItems.SYNC_CRYSTAL.get()))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLIZZARD_MUZZLE.get())
                .pattern("ABA")
                .pattern("BBC")
                .define('A', ModItems.AEROMER.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .define('C', Items.IRON_BARS)
                .unlockedBy("avaliproject:has_iron_bars", has(Items.IRON_BARS))
                .unlockedBy("avaliproject:has_woven_graphene", has(ModItems.WOVEN_GRAPHENE.get()))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLIZZARD_HILT.get())
                .pattern("A B")
                .pattern(" C ")
                .pattern("  A")
                .define('A', ModItems.AEROMER.get())
                .define('C', ModItems.AEROGEL.get())
                .define('B', ModItems.WOVEN_GRAPHENE.get())
                .unlockedBy("avaliproject:has_aerogel", has(ModItems.AEROGEL.get()))
                .unlockedBy("avaliproject:has_woven_graphene", has(ModItems.WOVEN_GRAPHENE.get()))
                .unlockedBy("avaliproject:has_aeromer", has(ModItems.AEROMER.get()))
                .save(recipeOutput);


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

    protected static void oreSmelting(Consumer<FinishedRecipe> recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }
}
