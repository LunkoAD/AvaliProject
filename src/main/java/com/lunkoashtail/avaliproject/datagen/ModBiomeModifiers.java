package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

import static com.lunkoashtail.avaliproject.util.ModTags.Biomes.AVALON;

public class ModBiomeModifiers {
    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_LUME_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AVALON),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LUME_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_TITANIUM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.TITANIUM_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_THERMAL_CRYSTAL_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AVALON),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THERMAL_CRYSTAL_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_DURASTEEL_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DURASTEEL_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_AERO_CRYSTAL_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AVALON),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.AERO_CRYSTAL_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_AEGISALT_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.AEGISALT_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_VILOUS_CERAMIC_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.VILOUS_CERAMIC_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_ARCAITES_CRYSTAL_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AVALON),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ARCAITES_CRYSTAL_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_NOVULITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NOVULITE_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_AGATE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.AGATE_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));


        context.register(ADD_PIRU_NODULE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AVALON),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PIRU_NODULE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_NAKATI_NODULE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AVALON),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NAKATI_NODULE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_KIRI_NODULE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AVALON),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.KIRI_NODULE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_GROOU_NODULE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AVALON),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GROOU_NODULE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_EXPIE_SPAWN, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.EXPIE.get(), 20, 1, 2))));

        context.register(ADD_CONTAINER_CRATE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CONTAINER_CRATE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_DECORATION));

        context.register(ADD_GEOTREE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GEOTREE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(ADD_HYDREED, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.HYDREED_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(ADD_GLOWPLANT, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GLOWPLANT_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_DECORATION));

        bootstrapSpeciesContent(context);
    }

    private static void bootstrapSpeciesContent(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_CRYSTALITHIUM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CRYSTALITHIUM_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_MOONSTONE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MOONSTONE_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_MOONSTONE_ORE_COASTAL, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BEACH), biomes.getOrThrow(Biomes.STONY_SHORE),
                        biomes.getOrThrow(Biomes.WARM_OCEAN), biomes.getOrThrow(Biomes.LUKEWARM_OCEAN),
                        biomes.getOrThrow(Biomes.DEEP_LUKEWARM_OCEAN), biomes.getOrThrow(Biomes.OCEAN),
                        biomes.getOrThrow(Biomes.DEEP_OCEAN)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MOONSTONE_ORES_COASTAL_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_SUNSTONE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SUNSTONE_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_SUNSTONE_ORE_SUNNY, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SAVANNA), biomes.getOrThrow(Biomes.SAVANNA_PLATEAU),
                        biomes.getOrThrow(Biomes.WINDSWEPT_SAVANNA), biomes.getOrThrow(Biomes.DESERT),
                        biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.SUNFLOWER_PLAINS)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SUNSTONE_ORES_SUNNY_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_LARIMAR_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LARIMAR_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_SHELL_PATCHES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BEACH), biomes.getOrThrow(Biomes.STONY_SHORE),
                        biomes.getOrThrow(Biomes.LUKEWARM_OCEAN), biomes.getOrThrow(Biomes.WARM_OCEAN)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SHELL_PATCH_PLACED_KEY),
                        placedFeatures.getOrThrow(ModPlacedFeatures.SHELL_PATCH_UNDERWATER_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_PALM_TREES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BEACH), biomes.getOrThrow(Biomes.JUNGLE),
                        biomes.getOrThrow(Biomes.SPARSE_JUNGLE)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PALM_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_TROPICAL_FRUIT_TREES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.JUNGLE), biomes.getOrThrow(Biomes.SPARSE_JUNGLE),
                        biomes.getOrThrow(Biomes.BAMBOO_JUNGLE)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PASSION_FRUIT_TREE_PLACED_KEY),
                        placedFeatures.getOrThrow(ModPlacedFeatures.PAPAYA_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_FELKIN_SPAWN, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SAVANNA), biomes.getOrThrow(Biomes.SAVANNA_PLATEAU),
                        biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.SNOWY_PLAINS)),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.FELKIN.get(), 8, 2, 5))));
        context.register(ADD_GREATER_YINGLET_SPAWN, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BEACH), biomes.getOrThrow(Biomes.SAVANNA),
                        biomes.getOrThrow(Biomes.DESERT), biomes.getOrThrow(Biomes.PLAINS)),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.GREATER_YINGLET.get(), 6, 2, 4))));
        context.register(ADD_MANOKIT_BEACH_SPAWN, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BEACH)),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.BEACHDWELLER_MANOKIT.get(), 5, 1, 3),
                        new MobSpawnSettings.SpawnerData(ModEntities.ARCANE_MANOKIT.get(), 1, 1, 1))));
        context.register(ADD_MANOKIT_JUNGLE_SPAWN, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.JUNGLE), biomes.getOrThrow(Biomes.SPARSE_JUNGLE)),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.TRIBAL_MANOKIT.get(), 5, 1, 3),
                        new MobSpawnSettings.SpawnerData(ModEntities.ARCANE_MANOKIT.get(), 1, 1, 1))));
        context.register(ADD_WICKERBEAST_SPAWN, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.DARK_FOREST), biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA)),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.WICKERBEAST.get(), 3, 1, 2))));

        context.register(ADD_WICKER_ENCLAVE_NEST_CAVE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.DRIPSTONE_CAVES), biomes.getOrThrow(Biomes.LUSH_CAVES)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WICKER_ENCLAVE_NEST_CAVE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(ADD_WICKER_ENCLAVE_NEST_SURFACE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.DARK_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WICKER_ENCLAVE_NEST_SURFACE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_BEACH_BURROW, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BEACH)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BEACH_BURROW_PLACED_KEY)),
                GenerationStep.Decoration.SURFACE_STRUCTURES));
        context.register(ADD_TROPICAL_DEN, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.JUNGLE), biomes.getOrThrow(Biomes.SPARSE_JUNGLE),
                        biomes.getOrThrow(Biomes.BAMBOO_JUNGLE)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.TROPICAL_DEN_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    public static final ResourceKey<BiomeModifier> ADD_WICKER_ENCLAVE_NEST_CAVE = registerKey("add_wicker_enclave_nest_cave");
    public static final ResourceKey<BiomeModifier> ADD_WICKER_ENCLAVE_NEST_SURFACE = registerKey("add_wicker_enclave_nest_surface");
    public static final ResourceKey<BiomeModifier> ADD_BEACH_BURROW = registerKey("add_beach_burrow");
    public static final ResourceKey<BiomeModifier> ADD_TROPICAL_DEN = registerKey("add_tropical_den");

    public static final ResourceKey<BiomeModifier> ADD_FELKIN_SPAWN = registerKey("add_felkin_spawn");
    public static final ResourceKey<BiomeModifier> ADD_GREATER_YINGLET_SPAWN = registerKey("add_greater_yinglet_spawn");
    public static final ResourceKey<BiomeModifier> ADD_MANOKIT_BEACH_SPAWN = registerKey("add_manokit_beach_spawn");
    public static final ResourceKey<BiomeModifier> ADD_MANOKIT_JUNGLE_SPAWN = registerKey("add_manokit_jungle_spawn");
    public static final ResourceKey<BiomeModifier> ADD_WICKERBEAST_SPAWN = registerKey("add_wickerbeast_spawn");
    public static final ResourceKey<BiomeModifier> ADD_CRYSTALITHIUM_ORE = registerKey("add_crystalithium_ore");
    public static final ResourceKey<BiomeModifier> ADD_MOONSTONE_ORE = registerKey("add_moonstone_ore");
    public static final ResourceKey<BiomeModifier> ADD_MOONSTONE_ORE_COASTAL = registerKey("add_moonstone_ore_coastal");
    public static final ResourceKey<BiomeModifier> ADD_SUNSTONE_ORE = registerKey("add_sunstone_ore");
    public static final ResourceKey<BiomeModifier> ADD_SUNSTONE_ORE_SUNNY = registerKey("add_sunstone_ore_sunny");
    public static final ResourceKey<BiomeModifier> ADD_LARIMAR_ORE = registerKey("add_larimar_ore");
    public static final ResourceKey<BiomeModifier> ADD_SHELL_PATCHES = registerKey("add_shell_patches");
    public static final ResourceKey<BiomeModifier> ADD_PALM_TREES = registerKey("add_palm_trees");
    public static final ResourceKey<BiomeModifier> ADD_TROPICAL_FRUIT_TREES = registerKey("add_tropical_fruit_trees");
    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name));
    }
    public static final ResourceKey<BiomeModifier> ADD_LUME_ORE = registerKey("add_lume_ore");
    public static final ResourceKey<BiomeModifier> ADD_TITANIUM_ORE = registerKey("add_titanium_ore");
    public static final ResourceKey<BiomeModifier> ADD_THERMAL_CRYSTAL_ORE = registerKey("add_thermal_crystal_ore");
    public static final ResourceKey<BiomeModifier> ADD_DURASTEEL_ORE = registerKey("add_durasteel_ore");
    public static final ResourceKey<BiomeModifier> ADD_AERO_CRYSTAL_ORE = registerKey("add_aero_crystal_ore");
    public static final ResourceKey<BiomeModifier> ADD_AEGISALT_ORE = registerKey("add_aegisalt_ore");
    public static final ResourceKey<BiomeModifier> ADD_VILOUS_CERAMIC_ORE = registerKey("add_vilous_ceramic_ore");
    public static final ResourceKey<BiomeModifier> ADD_ARCAITES_CRYSTAL_ORE = registerKey("add_arcaites_crystal_ore");
    public static final ResourceKey<BiomeModifier> ADD_NOVULITE_ORE = registerKey("add_novulite_ore");
    public static final ResourceKey<BiomeModifier> ADD_AGATE_ORE = registerKey("add_agate_ore");

    public static final ResourceKey<BiomeModifier> ADD_PIRU_NODULE = registerKey("add_piru_nodule");
    public static final ResourceKey<BiomeModifier> ADD_NAKATI_NODULE = registerKey("add_nakati_nodule");
    public static final ResourceKey<BiomeModifier> ADD_KIRI_NODULE = registerKey("add_kiri_nodule");
    public static final ResourceKey<BiomeModifier> ADD_GROOU_NODULE = registerKey("add_groou_nodule");

    public static final ResourceKey<BiomeModifier> ADD_EXPIE_SPAWN = registerKey("add_expie_spawn");
    public static final ResourceKey<BiomeModifier> ADD_CONTAINER_CRATE = registerKey("add_container_crate");
    public static final ResourceKey<BiomeModifier> ADD_GEOTREE = registerKey("add_geotree");
    public static final ResourceKey<BiomeModifier> ADD_HYDREED = registerKey("add_hydreed");
    public static final ResourceKey<BiomeModifier> ADD_GLOWPLANT = registerKey("add_glowplant");
}