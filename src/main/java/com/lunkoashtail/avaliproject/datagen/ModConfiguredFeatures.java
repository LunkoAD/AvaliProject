package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_LUME_ORES_KEY = registerKey("lume_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_TITANIUM_ORES_KEY = registerKey("titanium_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_DURASTEEL_ORES_KEY = registerKey("durasteel_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_AERO_CRYSTAL_ORES_KEY = registerKey("aero_crystal_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_SYNC_CRYSTAL_ORES_KEY = registerKey("sync_crystal_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_THERMAL_CRYSTAL_ORES_KEY = registerKey("thermal_crystal_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_AEGISALT_ORES_KEY = registerKey("aegisalt_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_VILOUS_CERAMIC_ORES_KEY = registerKey("vilous_ceramic__ores");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PIRU_NODULE_KEY = registerKey("piru_nodule");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NAKATI_NODULE_KEY = registerKey("nakati_nodule");
    public static final ResourceKey<ConfiguredFeature<?, ?>> KIRI_NODULE_KEY = registerKey("kiri_nodule");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GROOU_NODULE_KEY = registerKey("groou_nodule");



    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldLumeOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.LUME_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.LUME_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldTitaniumOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.TITANIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.TITANIUM_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldDurasteelOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.DURASTEEL_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DURASTEEL_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldAeroCrystalOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.AERO_CRYSTAL_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldSyncCrystalOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.SYNC_CRYSTAL_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldThermalCrystalOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.THERMAL_CRYSTAL_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldAegisaltOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.AEGISALT_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.AEGISALT_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldVilousCeramicOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.VILOUS_CERAMIC_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_LUME_ORES_KEY, Feature.ORE, new OreConfiguration(overworldLumeOres, 4));
        register(context, OVERWORLD_TITANIUM_ORES_KEY, Feature.ORE, new OreConfiguration(overworldTitaniumOres, 4));
        register(context, OVERWORLD_DURASTEEL_ORES_KEY, Feature.ORE, new OreConfiguration(overworldDurasteelOres, 4));
        register(context, OVERWORLD_AERO_CRYSTAL_ORES_KEY, Feature.ORE, new OreConfiguration(overworldAeroCrystalOres, 4));
        register(context, OVERWORLD_SYNC_CRYSTAL_ORES_KEY, Feature.ORE, new OreConfiguration(overworldSyncCrystalOres, 4));
        register(context, OVERWORLD_THERMAL_CRYSTAL_ORES_KEY, Feature.ORE, new OreConfiguration(overworldThermalCrystalOres, 4));
        register(context, OVERWORLD_AEGISALT_ORES_KEY, Feature.ORE, new OreConfiguration(overworldAegisaltOres, 4));
        register(context, OVERWORLD_VILOUS_CERAMIC_ORES_KEY, Feature.ORE, new OreConfiguration(overworldVilousCeramicOres, 4));


        register(context, PIRU_NODULE_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PIRU_NODULE.get())))));
        register(context, NAKATI_NODULE_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.NAKATI_NODULE.get())))));
        register(context, KIRI_NODULE_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.KIRI_NODULE.get())))));
        register(context, GROOU_NODULE_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.GROOU_NODULE.get())))));



    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}