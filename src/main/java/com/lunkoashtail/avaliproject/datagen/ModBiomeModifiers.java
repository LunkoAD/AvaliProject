package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.datagen.avalon.AvalonBiomes;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.Weight;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.data.worldgen.BootstapContext;

import java.util.List;

import static com.lunkoashtail.avaliproject.datagen.Velous.VelousGenerators.settingsSergal;
import static com.lunkoashtail.avaliproject.datagen.avalon.AvalonGenerators.settingsAvali;
import static com.lunkoashtail.avaliproject.datagen.avalon.AvalonGenerators.settingsSksceeGehkja;

public class ModBiomeModifiers {
    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);



        context.register(ADD_TITANIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.TITANIUM_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_THERMAL_CRYSTAL_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THERMAL_CRYSTAL_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_DURASTEEL_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DURASTEEL_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_VILOUS_CERAMIC_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.VILOUS_CERAMIC_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_ARCAITES_CRYSTAL_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ARCAITES_CRYSTAL_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_NOVULITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NOVULITE_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_AGATE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.AGATE_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));


        //spawns
        context.register(SPAWN_AVALIS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.getOrThrow(ModTags.Biomes.AVALON), List.of(settingsAvali)));
        context.register(SPAWN_SKSCEEGEHKJA, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.getOrThrow(ModTags.Biomes.AVALON), List.of(settingsSksceeGehkja)));
        context.register(SPAWN_SERGALS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.getOrThrow(BiomeTags.IS_FOREST), List.of(settingsSergal)));
    }
    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name));
    }

    public static final ResourceKey<BiomeModifier> ADD_LUME_ORE = registerKey("add_lume_ore");
    public static final ResourceKey<BiomeModifier> ADD_TITANIUM_ORE = registerKey("add_titanium_ore");
    public static final ResourceKey<BiomeModifier> ADD_THERMAL_CRYSTAL_ORE = registerKey("add_thermal_crystal_ore");
    public static final ResourceKey<BiomeModifier> ADD_DURASTEEL_ORE = registerKey("add_durasteel_ore");
    public static final ResourceKey<BiomeModifier> ADD_VILOUS_CERAMIC_ORE = registerKey("add_vilous_ceramic_ore");
    public static final ResourceKey<BiomeModifier> ADD_ARCAITES_CRYSTAL_ORE = registerKey("add_arcaites_crystal_ore");
    public static final ResourceKey<BiomeModifier> ADD_NOVULITE_ORE = registerKey("add_novulite_ore");
    public static final ResourceKey<BiomeModifier> ADD_AGATE_ORE = registerKey("add_agate_ore");


    public static final ResourceKey<BiomeModifier> SPAWN_AVALIS = registerKey("spawn_avalis");
    public static final ResourceKey<BiomeModifier> SPAWN_SERGALS = registerKey("spawn_sergals");
    public static final ResourceKey<BiomeModifier> SPAWN_SKSCEEGEHKJA = registerKey("spawn_sksceegehkja");
}