package com.lunkoashtail.avaliproject.datagen.avalon;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AvalonBiomes {
    public static final ResourceKey<Biome> SNOWY_PLAINS = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "snowy_plains"));
    public static final ResourceKey<Biome> SHIFTING_ICE = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "shifting_ice"));
    public static final ResourceKey<Biome> PERMAFROST_FORESTS = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "permafrost_forests"));
    public static final ResourceKey<Biome> AVALON_CITIES = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avalon_cities"));




    public static void boostrap(BootstapContext<Biome> context) {

        //we are copying from existing biomes for now.
        //TODO: Avalon cities should spawn avalon buildings that are "villages" but bigger and cooler. - @989onan

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        context.register(PERMAFROST_FORESTS, taiga(biomeBuilder));
        context.register(SNOWY_PLAINS, OverworldBiomes.plains(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER),false,true,false));
        context.register(SHIFTING_ICE, OverworldBiomes.frozenOcean(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER), true));
        context.register(AVALON_CITIES, OverworldBiomes.windsweptHills(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER),false));

    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        //BiomeDefaultFeatures.addFerns(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
        BiomeDefaultFeatures.addTaigaTrees(builder);
    }

    public static Biome taiga(BiomeGenerationSettings.Builder biomeBuilder) {
        boolean pIsCold = true;
        MobSpawnSettings.Builder spawnbuilder = new MobSpawnSettings.Builder();


        //BiomeDefaultFeatures.farmAnimals(builder);
//        mobspawnsettings$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
        //BiomeDefaultFeatures.commonSpawns(mobspawnsettings$builder);
        float f = pIsCold ? -0.5F : 0.25F;
        globalOverworldGeneration(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultFlowers(builder);
        //BiomeDefaultFeatures.addTaigaGrass(builder);
        //BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
        if (pIsCold) {
            //BiomeDefaultFeatures.addRareBerryBushes(builder);
        } else {
            //BiomeDefaultFeatures.addCommonBerryBushes(builder);
        }

        return biome(true, f, pIsCold ? 0.4F : 0.8F, pIsCold ? 4020182 : 4159204, 329011, (Integer)null, (Integer)null, spawnbuilder, biomeBuilder, Musics.GAME);//TODO: WE NEED MUSIC - @989onan
    }
    private static Biome biome(boolean pHasPrecipitation, float pTemperature, float pDownfall, int pWaterColor, int pWaterFogColor, @javax.annotation.Nullable Integer pGrassColorOverride, @javax.annotation.Nullable Integer pFoliageColorOverride, MobSpawnSettings.Builder pMobSpawnSettings, BiomeGenerationSettings.Builder pGenerationSettings, @Nullable Music pBackgroundMusic) {
        BiomeSpecialEffects.Builder biomespecialeffects$builder = (new BiomeSpecialEffects.Builder()).waterColor(pWaterColor).waterFogColor(pWaterFogColor).fogColor(12638463).skyColor(calculateSkyColor(pTemperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(pBackgroundMusic);
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

