package com.lunkoashtail.avaliproject.datagen.avalon;

import com.lunkoashtail.avaliproject.datagen.ModPlacedFeatures;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.util.Mth;
import net.minecraft.util.random.Weight;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.Nullable;

public class AvalonGenerators {


    public static final MobSpawnSettings.SpawnerData settingsAvali = new MobSpawnSettings.SpawnerData(ModEntities.AVALI.get(), Weight.of(2),1,1);


    public static void addWildlife(MobSpawnSettings.Builder spawner){
        //spawner.addSpawn(MobCategory.CREATURE, settingsAvali);
    }

    public static void addAvalis(MobSpawnSettings.Builder spawner){
        //spawner.addSpawn(MobCategory.CREATURE, settingsAvali);
    }

    public static void addWaterLife(MobSpawnSettings.Builder spawner){
        //spawner.addSpawn(MobCategory.CREATURE, settingsAvali);
    }

    public static void addAvalonOres(BiomeGenerationSettings.Builder pBuilder){
        pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.AEGISALT_ORES_PLACED_KEY);
        pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.AERO_CRYSTAL_ORES_PLACED_KEY);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.PIRU_NODULE_PLACED_KEY);
    }

    public static Biome frozenOcean(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers, boolean pIsDeep) {
        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();

        float f = pIsDeep ? 0.5F : 0.0F;
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        BiomeDefaultFeatures.addIcebergs(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addBlueIce(biomegenerationsettings$builder);
        AvalonAllGen(biomegenerationsettings$builder);

        addWaterLife(mobspawnsettings$builder);
        return (new Biome.BiomeBuilder()).hasPrecipitation(true).temperature(f).temperatureAdjustment(Biome.TemperatureModifier.FROZEN).downfall(0.5F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(3750089).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(f)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
    }

    public static Biome windsweptHills(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers, boolean pIsForest) {
        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        AvalonAllGen(biomegenerationsettings$builder);

        BiomeDefaultFeatures.addMountainTrees(biomegenerationsettings$builder);
        MobSpawnSettings.Builder spawnbuilder = new MobSpawnSettings.Builder();
        addAvalis(spawnbuilder);
        return biome(true, -0.5F,0.4F, 4020182, 329011, (Integer)null, (Integer)null, spawnbuilder, biomegenerationsettings$builder, Musics.GAME);
    }

    public static Biome cities(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers, boolean pIsForest) {
        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        AvalonAllGen(biomegenerationsettings$builder);

        BiomeDefaultFeatures.addMountainTrees(biomegenerationsettings$builder);
        MobSpawnSettings.Builder spawnbuilder = new MobSpawnSettings.Builder();
        addWildlife(spawnbuilder);
        return biome(true, -0.5F,0.4F, 4020182, 329011, (Integer)null, (Integer)null, spawnbuilder, biomegenerationsettings$builder, Musics.GAME);
    }


    //freeze the land, add ores, and add clay/dirt. - @989onan
    //finally, add avalon ores - @989onan
    public static void AvalonAllGen(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        addAvalonOres(builder);
    }

    public static Biome taiga(BiomeGenerationSettings.Builder biomeBuilder) {
        boolean pIsCold = true;
        MobSpawnSettings.Builder spawnbuilder = new MobSpawnSettings.Builder();
        //BiomeDefaultFeatures.farmAnimals(builder);
//        mobspawnsettings$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
        //BiomeDefaultFeatures.commonSpawns(mobspawnsettings$builder);
        float f = pIsCold ? -0.5F : 0.25F;
        addWildlife(spawnbuilder);
        BiomeDefaultFeatures.addTaigaTrees(biomeBuilder);
        AvalonAllGen(biomeBuilder);

        return biome(true, f, pIsCold ? 0.4F : 0.8F, pIsCold ? 4020182 : 4159204, 329011, (Integer)null, (Integer)null, spawnbuilder, biomeBuilder, Musics.GAME);//TODO: WE NEED MUSIC - @989onan
    }

    public static Biome plains(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers, boolean pIsSunflowerPlains, boolean pIsCold, boolean pIsIceSpikes) {
        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        addWildlife(mobspawnsettings$builder);
        AvalonAllGen(biomegenerationsettings$builder);
        if (pIsCold) {
            mobspawnsettings$builder.creatureGenerationProbability(0.07F);

            if (pIsIceSpikes) {
                biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE);
                biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);
            }
        }

        BiomeDefaultFeatures.addDefaultOres(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
        if (pIsCold) {
            BiomeDefaultFeatures.addSnowyTrees(biomegenerationsettings$builder);
        }

        float f = pIsCold ? 0.0F : 0.8F;
        return biome(true, f, pIsCold ? 0.5F : 0.4F, pIsCold ? 4020182 : 4159204,329011, (Integer)null, (Integer)null, mobspawnsettings$builder, biomegenerationsettings$builder, Musics.GAME);//TODO: WE NEED MUSIC - @989onan
    }

    private static Biome biome(boolean pHasPrecipitation, float pTemperature, float pDownfall, int pWaterColor, int pWaterFogColor, @javax.annotation.Nullable Integer pGrassColorOverride, @javax.annotation.Nullable Integer pFoliageColorOverride, MobSpawnSettings.Builder pMobSpawnSettings, BiomeGenerationSettings.Builder pGenerationSettings, @Nullable Music pBackgroundMusic) {
        BiomeSpecialEffects.Builder biomespecialeffects$builder = (new BiomeSpecialEffects.Builder()).waterColor(pWaterColor).waterFogColor(pWaterFogColor).fogColor(12638463).skyColor(calculateSkyColor(pTemperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(pBackgroundMusic);
        //pMobSpawnSettings.addSpawn(MobCategory.CREATURE, settingsAvali);

        if (pGrassColorOverride != null) {
            biomespecialeffects$builder.grassColorOverride(pGrassColorOverride);
        }

        if (pFoliageColorOverride != null) {
            biomespecialeffects$builder.foliageColorOverride(pFoliageColorOverride);
        }

        return (new Biome.BiomeBuilder()).hasPrecipitation(pHasPrecipitation).temperature(pTemperature).downfall(pDownfall).specialEffects(biomespecialeffects$builder.build()).mobSpawnSettings(pMobSpawnSettings.build()).generationSettings(pGenerationSettings.build()).build();
    }

    protected static int calculateSkyColor(float pTemperature) {
        float $$1 = pTemperature / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }
}
