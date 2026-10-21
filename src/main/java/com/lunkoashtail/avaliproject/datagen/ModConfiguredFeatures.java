package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.custom.ShellCoverBlock;
import com.lunkoashtail.avaliproject.worldgen.CoconutDecorator;
import com.lunkoashtail.avaliproject.block.custom.ShellType;
import net.minecraft.core.Direction;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.material.Fluids;
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
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_THERMAL_CRYSTAL_ORES_KEY = registerKey("thermal_crystal_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_AEGISALT_ORES_KEY = registerKey("aegisalt_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_VILOUS_CERAMIC_ORES_KEY = registerKey("vilous_ceramic_ores");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_ARCAITES_CRYSTAL_ORES_KEY = registerKey("arcaites_crystal_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_NOVULITE_ORES_KEY = registerKey("novulite_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_AGATE_ORES_KEY = registerKey("agate_ores");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PIRU_NODULE_KEY = registerKey("piru_nodule");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NAKATI_NODULE_KEY = registerKey("nakati_nodule");
    public static final ResourceKey<ConfiguredFeature<?, ?>> KIRI_NODULE_KEY = registerKey("kiri_nodule");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GROOU_NODULE_KEY = registerKey("groou_nodule");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONTAINER_CRATE_KEY = registerKey("container_crate");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GEOTREE_KEY = registerKey("geotree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HYDREED_KEY = registerKey("hydreed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWPLANT_KEY = registerKey("glowplant");



    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_CRYSTALITHIUM_ORES_KEY = registerKey("crystalithium_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_MOONSTONE_ORES_KEY = registerKey("moonstone_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_SUNSTONE_ORES_KEY = registerKey("sunstone_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_LARIMAR_ORES_KEY = registerKey("larimar_ores");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SHELL_PATCH_KEY = registerKey("shell_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHELL_PATCH_UNDERWATER_KEY = registerKey("shell_patch_underwater");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_TREE_KEY = registerKey("palm_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PASSION_FRUIT_TREE_KEY = registerKey("passion_fruit_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PAPAYA_TREE_KEY = registerKey("papaya_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> WICKER_ENCLAVE_NEST_KEY = registerKey("wicker_enclave_nest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BEACH_BURROW_KEY = registerKey("beach_burrow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TROPICAL_DEN_KEY = registerKey("tropical_den");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        bootstrapSpeciesContent(context);
        bootstrapFootprints(context);

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
        List<OreConfiguration.TargetBlockState> overworldThermalCrystalOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.THERMAL_CRYSTAL_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldAegisaltOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.AEGISALT_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.AEGISALT_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldVilousCeramicOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.VILOUS_CERAMIC_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldArcaitesCrystalOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ARCAITES_CRYSTAL_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldNovuliteOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.NOVULITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.NOVULITE_DEEPSLATE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldAgateOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.AGATE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.AGATE_DEEPSLATE_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_LUME_ORES_KEY, Feature.ORE, new OreConfiguration(overworldLumeOres, 3));
        register(context, OVERWORLD_TITANIUM_ORES_KEY, Feature.ORE, new OreConfiguration(overworldTitaniumOres, 4));
        register(context, OVERWORLD_DURASTEEL_ORES_KEY, Feature.ORE, new OreConfiguration(overworldDurasteelOres, 4));
        register(context, OVERWORLD_AERO_CRYSTAL_ORES_KEY, Feature.ORE, new OreConfiguration(overworldAeroCrystalOres, 4));
        register(context, OVERWORLD_THERMAL_CRYSTAL_ORES_KEY, Feature.ORE, new OreConfiguration(overworldThermalCrystalOres, 4));
        register(context, OVERWORLD_AEGISALT_ORES_KEY, Feature.ORE, new OreConfiguration(overworldAegisaltOres, 4));
        register(context, OVERWORLD_VILOUS_CERAMIC_ORES_KEY, Feature.ORE, new OreConfiguration(overworldVilousCeramicOres, 4));
        register(context, OVERWORLD_ARCAITES_CRYSTAL_ORES_KEY, Feature.ORE, new OreConfiguration(overworldArcaitesCrystalOres, 3));
        register(context, OVERWORLD_NOVULITE_ORES_KEY, Feature.ORE, new OreConfiguration(overworldNovuliteOres, 3));
        register(context, OVERWORLD_AGATE_ORES_KEY, Feature.ORE, new OreConfiguration(overworldAgateOres, 3));


        register(context, PIRU_NODULE_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PIRU_NODULE.get())))));
        register(context, NAKATI_NODULE_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.NAKATI_NODULE.get())))));
        register(context, KIRI_NODULE_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.KIRI_NODULE.get())))));
        register(context, GROOU_NODULE_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.GROOU_NODULE.get())))));

        register(context, CONTAINER_CRATE_KEY, com.lunkoashtail.avaliproject.worldgen.ModFeatures.CONTAINER_CRATE_FEATURE.get(),
                net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE);

        register(context, GEOTREE_KEY, com.lunkoashtail.avaliproject.worldgen.ModFeatures.CAVE_BLOCK_FEATURE.get(),
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.GEOTREE.get())));
        register(context, HYDREED_KEY, com.lunkoashtail.avaliproject.worldgen.ModFeatures.CAVE_BLOCK_FEATURE.get(),
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.HYDREED.get())));
        register(context, GLOWPLANT_KEY, com.lunkoashtail.avaliproject.worldgen.ModFeatures.CAVE_BLOCK_FEATURE.get(),
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.GLOWPLANT.get())));

    }

    private static void bootstrapSpeciesContent(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        register(context, OVERWORLD_CRYSTALITHIUM_ORES_KEY, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.CRYSTALITHIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.CRYSTALITHIUM_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        register(context, OVERWORLD_MOONSTONE_ORES_KEY, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.MOONSTONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.MOONSTONE_DEEPSLATE_ORE.get().defaultBlockState())), 6));
        register(context, OVERWORLD_SUNSTONE_ORES_KEY, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.SUNSTONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.SUNSTONE_DEEPSLATE_ORE.get().defaultBlockState())), 6));
        register(context, OVERWORLD_LARIMAR_ORES_KEY, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.LARIMAR_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.LARIMAR_DEEPSLATE_ORE.get().defaultBlockState())), 4));

        BlockPredicate shellFloor = BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(),
                Blocks.SAND, Blocks.GRAVEL, Blocks.DIRT, Blocks.SANDSTONE, Blocks.PRISMARINE);
        register(context, SHELL_PATCH_KEY, Feature.RANDOM_PATCH, new RandomPatchConfiguration(24, 6, 2,
                PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(shellStates(false)),
                        BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, shellFloor))));
        register(context, SHELL_PATCH_UNDERWATER_KEY, Feature.RANDOM_PATCH, new RandomPatchConfiguration(24, 6, 2,
                PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(shellStates(true)),
                        BlockPredicate.allOf(BlockPredicate.matchesFluids(Fluids.WATER), shellFloor))));

        register(context, PALM_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.PALM.log.get()),
                new BendingTrunkPlacer(5, 2, 0, 3, UniformInt.of(1, 2)),
                BlockStateProvider.simple(ModBlocks.PALM_LEAVES.get()),
                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Blocks.SAND)).ignoreVines()
                .decorators(List.of(new CoconutDecorator(0.6F))).build());
        register(context, PASSION_FRUIT_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.PASSION_FRUIT.log.get()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(ModBlocks.PASSION_FRUIT_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines().build());
        register(context, PAPAYA_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.PAPAYA.log.get()),
                new StraightTrunkPlacer(5, 2, 1),
                BlockStateProvider.simple(ModBlocks.PAPAYA_LEAVES.get()),
                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines().build());
    }

    private static void bootstrapFootprints(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, WICKER_ENCLAVE_NEST_KEY, com.lunkoashtail.avaliproject.worldgen.ModFeatures.WICKER_ENCLAVE_NEST_FEATURE.get(),
                net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE);
        register(context, BEACH_BURROW_KEY, com.lunkoashtail.avaliproject.worldgen.ModFeatures.BEACH_BURROW_FEATURE.get(),
                net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE);
        register(context, TROPICAL_DEN_KEY, com.lunkoashtail.avaliproject.worldgen.ModFeatures.TROPICAL_DEN_FEATURE.get(),
                net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE);
    }

    private static BlockStateProvider shellStates(boolean waterlogged) {
        SimpleWeightedRandomList.Builder<BlockState> states = SimpleWeightedRandomList.builder();
        for (ShellType type : ShellType.values()) {
            states.add(ModBlocks.SHELL_COVER.get().defaultBlockState()
                    .setValue(ShellCoverBlock.TYPE, type)
                    .setValue(ShellCoverBlock.WATERLOGGED, waterlogged), 1);
        }
        return new WeightedStateProvider(states);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}