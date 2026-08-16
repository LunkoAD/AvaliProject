package com.lunkoashtail.avaliproject.datagen;

import com.google.common.collect.ImmutableList;
import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
//import com.lunkoashtail.avaliproject.block.fluid.Ammonia;
import com.lunkoashtail.avaliproject.datagen.avalon.AvalonBiomes;
import com.lunkoashtail.avaliproject.datagen.avalon.AvalonTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseBasedStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.WaterFluid;
import net.neoforged.neoforge.common.Tags;

import java.util.stream.Stream;

public class ModNoiseGenProvider extends NoiseRouterData {
    public static final ResourceKey<NoiseGeneratorSettings> AVALON_NOISE = ResourceKey.create(Registries.NOISE_SETTINGS,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "excessive_oceans"));



    public static void bootstrapType(BootstrapContext<NoiseGeneratorSettings> context) {
        HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> DensityRegistry = context.lookup(Registries.DENSITY_FUNCTION);
        context.register(AVALON_NOISE, new NoiseGeneratorSettings(
                new NoiseSettings(-64, 384, 1, 1),
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                //we need a custom fluid for ammonia please thank you - @989onan
                //ModBlocks.AMMONIA_BLOCK.get().defaultBlockState(),
                NoiseRouterData.overworld(DensityRegistry, pNoiseParameters,true,false),
                SurfaceRuleData.overworld(),
                (new OverworldBiomeBuilder()).spawnTarget(),
                63,
                false,
                true,
                false,
                false
        ));



    }

}