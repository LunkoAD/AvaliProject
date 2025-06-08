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
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.util.Mth;
import net.minecraft.util.random.Weight;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
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
        context.register(PERMAFROST_FORESTS, AvalonGenerators.taiga(biomeBuilder));
        context.register(SNOWY_PLAINS, AvalonGenerators.plains(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER),false,true,false));
        context.register(SHIFTING_ICE, AvalonGenerators.frozenOcean(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER), true));
        context.register(AVALON_CITIES, AvalonGenerators.cities(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER),false));
    }





}

