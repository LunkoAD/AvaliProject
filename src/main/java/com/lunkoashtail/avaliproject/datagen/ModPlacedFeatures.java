package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import java.util.List;

import static com.lunkoashtail.avaliproject.datagen.ModConfiguredFeatures.*;

public class ModPlacedFeatures {
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);


        register(context, LUME_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_LUME_ORES_KEY),
                ModOrePlacements.commonOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, DURASTEEL_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_DURASTEEL_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, TITANIUM_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_TITANIUM_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, AEGISALT_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_AEGISALT_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, THERMAL_CRYSTAL_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_THERMAL_CRYSTAL_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, AERO_CRYSTAL_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_AERO_CRYSTAL_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, VILOUS_CERAMIC_ORES_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_VILOUS_CERAMIC_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, ARCAITES_CRYSTAL_ORES_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_ARCAITES_CRYSTAL_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, NOVULITE_ORES_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_NOVULITE_ORES_KEY),
                ModOrePlacements.commonOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, AGATE_ORES_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_AGATE_ORES_KEY),
                ModOrePlacements.commonOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));


        register(context, PIRU_NODULE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PIRU_NODULE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, NAKATI_NODULE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NAKATI_NODULE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, GROOU_NODULE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GROOU_NODULE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, KIRI_NODULE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.KIRI_NODULE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(context, CONTAINER_CRATE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CONTAINER_CRATE_KEY),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(40)), BiomeFilter.biome()));

        register(context, GEOTREE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GEOTREE_KEY),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(40)), BiomeFilter.biome()));
        register(context, HYDREED_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.HYDREED_KEY),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(40)), BiomeFilter.biome()));
        register(context, GLOWPLANT_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GLOWPLANT_KEY),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(40)), BiomeFilter.biome()));

        bootstrapSpeciesContent(context, configuredFeatures);
        bootstrapFootprints(context, configuredFeatures);
    }

    private static void bootstrapSpeciesContent(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures) {
        register(context, CRYSTALITHIUM_ORES_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_CRYSTALITHIUM_ORES_KEY),
                ModOrePlacements.commonOrePlacement(3,
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
        register(context, MOONSTONE_ORES_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_MOONSTONE_ORES_KEY),
                ModOrePlacements.commonOrePlacement(3,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(48))));
        register(context, MOONSTONE_ORES_COASTAL_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_MOONSTONE_ORES_KEY),
                ModOrePlacements.commonOrePlacement(3,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(40))));
        register(context, SUNSTONE_ORES_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_SUNSTONE_ORES_KEY),
                ModOrePlacements.commonOrePlacement(2,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(64))));
        register(context, SUNSTONE_ORES_SUNNY_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_SUNSTONE_ORES_KEY),
                ModOrePlacements.commonOrePlacement(3,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(80))));
        register(context, LARIMAR_ORES_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_LARIMAR_ORES_KEY),
                ModOrePlacements.rareOrePlacement(2,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(32))));

        register(context, SHELL_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(SHELL_PATCH_KEY),
                List.of(RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, SHELL_PATCH_UNDERWATER_PLACED_KEY, configuredFeatures.getOrThrow(SHELL_PATCH_UNDERWATER_KEY),
                List.of(RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()));

        register(context, PALM_TREE_PLACED_KEY, configuredFeatures.getOrThrow(PALM_TREE_KEY),
                palmPlacement());
        register(context, PASSION_FRUIT_TREE_PLACED_KEY, configuredFeatures.getOrThrow(PASSION_FRUIT_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1), ModBlocks.PASSION_FRUIT_SAPLING.get()));
        register(context, PAPAYA_TREE_PLACED_KEY, configuredFeatures.getOrThrow(PAPAYA_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1), ModBlocks.PAPAYA_SAPLING.get()));
    }

    private static List<PlacementModifier> palmPlacement() {
        List<PlacementModifier> modifiers = new java.util.ArrayList<>(VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1)));
        modifiers.add(BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(
                BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.SAND),
                BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT))));
        return modifiers;
    }

    private static final PlacementModifier GROUND_BELOW_CANOPY = HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES);

    private static void bootstrapFootprints(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures) {
        register(context, WICKER_ENCLAVE_NEST_CAVE_PLACED_KEY, configuredFeatures.getOrThrow(WICKER_ENCLAVE_NEST_KEY),
                List.of(RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(40)), BiomeFilter.biome()));
        register(context, WICKER_ENCLAVE_NEST_SURFACE_PLACED_KEY, configuredFeatures.getOrThrow(WICKER_ENCLAVE_NEST_KEY),
                List.of(RarityFilter.onAverageOnceEvery(48), InSquarePlacement.spread(), GROUND_BELOW_CANOPY, BiomeFilter.biome()));
        register(context, BEACH_BURROW_PLACED_KEY, configuredFeatures.getOrThrow(BEACH_BURROW_KEY),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, TROPICAL_DEN_PLACED_KEY, configuredFeatures.getOrThrow(TROPICAL_DEN_KEY),
                List.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), GROUND_BELOW_CANOPY, BiomeFilter.biome()));
    }

    public static final ResourceKey<PlacedFeature> WICKER_ENCLAVE_NEST_CAVE_PLACED_KEY = registerKey("wicker_enclave_nest_cave_placed");
    public static final ResourceKey<PlacedFeature> WICKER_ENCLAVE_NEST_SURFACE_PLACED_KEY = registerKey("wicker_enclave_nest_surface_placed");
    public static final ResourceKey<PlacedFeature> BEACH_BURROW_PLACED_KEY = registerKey("beach_burrow_placed");
    public static final ResourceKey<PlacedFeature> TROPICAL_DEN_PLACED_KEY = registerKey("tropical_den_placed");
    public static final ResourceKey<PlacedFeature> CRYSTALITHIUM_ORES_PLACED_KEY = registerKey("crystalithium_ores_placed");
    public static final ResourceKey<PlacedFeature> MOONSTONE_ORES_PLACED_KEY = registerKey("moonstone_ores_placed");
    public static final ResourceKey<PlacedFeature> MOONSTONE_ORES_COASTAL_PLACED_KEY = registerKey("moonstone_ores_coastal_placed");
    public static final ResourceKey<PlacedFeature> SUNSTONE_ORES_PLACED_KEY = registerKey("sunstone_ores_placed");
    public static final ResourceKey<PlacedFeature> SUNSTONE_ORES_SUNNY_PLACED_KEY = registerKey("sunstone_ores_sunny_placed");
    public static final ResourceKey<PlacedFeature> LARIMAR_ORES_PLACED_KEY = registerKey("larimar_ores_placed");
    public static final ResourceKey<PlacedFeature> SHELL_PATCH_PLACED_KEY = registerKey("shell_patch_placed");
    public static final ResourceKey<PlacedFeature> SHELL_PATCH_UNDERWATER_PLACED_KEY = registerKey("shell_patch_underwater_placed");
    public static final ResourceKey<PlacedFeature> PALM_TREE_PLACED_KEY = registerKey("palm_tree_placed");
    public static final ResourceKey<PlacedFeature> PASSION_FRUIT_TREE_PLACED_KEY = registerKey("passion_fruit_tree_placed");
    public static final ResourceKey<PlacedFeature> PAPAYA_TREE_PLACED_KEY = registerKey("papaya_tree_placed");

    public static final ResourceKey<PlacedFeature> PIRU_NODULE_PLACED_KEY = registerKey("piru_nodule_placed");
    public static final ResourceKey<PlacedFeature> NAKATI_NODULE_PLACED_KEY = registerKey("nakati_nodule_placed");
    public static final ResourceKey<PlacedFeature> GROOU_NODULE_PLACED_KEY = registerKey("groou_nodule_placed");
    public static final ResourceKey<PlacedFeature> KIRI_NODULE_PLACED_KEY = registerKey("kiri_nodule_placed");
    public static final ResourceKey<PlacedFeature> CONTAINER_CRATE_PLACED_KEY = registerKey("container_crate_placed");
    public static final ResourceKey<PlacedFeature> GEOTREE_PLACED_KEY = registerKey("geotree_placed");
    public static final ResourceKey<PlacedFeature> HYDREED_PLACED_KEY = registerKey("hydreed_placed");
    public static final ResourceKey<PlacedFeature> GLOWPLANT_PLACED_KEY = registerKey("glowplant_placed");


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name));
    }
    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    public static final ResourceKey<PlacedFeature> LUME_ORES_PLACED_KEY = registerKey("lume_ores_placed");
    public static final ResourceKey<PlacedFeature> DURASTEEL_ORES_PLACED_KEY = registerKey("durasteel_ores_placed");
    public static final ResourceKey<PlacedFeature> TITANIUM_ORES_PLACED_KEY = registerKey("titanium_ores_placed");
    public static final ResourceKey<PlacedFeature> AEGISALT_ORES_PLACED_KEY = registerKey("aegisalt_ores_placed");
    public static final ResourceKey<PlacedFeature> THERMAL_CRYSTAL_ORES_PLACED_KEY = registerKey("thermal_crystal_ores_placed");
    public static final ResourceKey<PlacedFeature> AERO_CRYSTAL_ORES_PLACED_KEY = registerKey("aero_crystal_ores_placed");
    public static final ResourceKey<PlacedFeature> VILOUS_CERAMIC_ORES_PLACED_KEY = registerKey("vilous_ceramic_ores_placed");
    public static final ResourceKey<PlacedFeature> ARCAITES_CRYSTAL_ORES_PLACED_KEY = registerKey("arcaites_crystal_ores_placed");
    public static final ResourceKey<PlacedFeature> NOVULITE_ORES_PLACED_KEY = registerKey("novulite_ores_placed");
    public static final ResourceKey<PlacedFeature> AGATE_ORES_PLACED_KEY = registerKey("agate_ores_placed");

}