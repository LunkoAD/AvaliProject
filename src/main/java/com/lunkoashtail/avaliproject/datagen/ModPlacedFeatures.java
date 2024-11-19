package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import java.util.List;

import static com.lunkoashtail.avaliproject.datagen.ModConfiguredFeatures.OVERWORLD_VILOUS_CERAMIC_ORES_KEY;
import static com.lunkoashtail.avaliproject.datagen.ModConfiguredFeatures.registerKey;

public class ModPlacedFeatures {
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);


        register(context, LUME_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_LUME_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
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
        register(context, SYNC_CRYSTAL_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_SYNC_CRYSTAL_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, VILOUS_CERAMIC_ORES_PLACED_KEY, configuredFeatures.getOrThrow(OVERWORLD_VILOUS_CERAMIC_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));


        register(context, PIRU_NODULE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PIRU_NODULE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, NAKATI_NODULE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NAKATI_NODULE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, GROOU_NODULE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GROOU_NODULE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, KIRI_NODULE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.KIRI_NODULE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));


    }

    public static final ResourceKey<PlacedFeature> PIRU_NODULE_PLACED_KEY = registerKey("piru_nodule_placed");
    public static final ResourceKey<PlacedFeature> NAKATI_NODULE_PLACED_KEY = registerKey("nakati_nodule_placed");
    public static final ResourceKey<PlacedFeature> GROOU_NODULE_PLACED_KEY = registerKey("groou_nodule_placed");
    public static final ResourceKey<PlacedFeature> KIRI_NODULE_PLACED_KEY = registerKey("kiri_nodule_placed");


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
    public static final ResourceKey<PlacedFeature> SYNC_CRYSTAL_ORES_PLACED_KEY = registerKey("sync_crystal_ores_placed");
    public static final ResourceKey<PlacedFeature> VILOUS_CERAMIC_ORES_PLACED_KEY = registerKey("vilous_ceramic_ores_placed");

}