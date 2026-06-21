package com.lunkoashtail.avaliproject.datagen;

import com.google.common.collect.ImmutableList;
import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.fluid.Ammonia;
import com.lunkoashtail.avaliproject.datagen.avalon.AvalonBiomes;
import com.lunkoashtail.avaliproject.datagen.avalon.AvalonTags;
import earth.terrarium.adastra.common.registry.ModBiomeSources;
import earth.terrarium.adastra.common.tags.ModBiomeTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
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
import net.minecraftforge.common.Tags;

import java.util.stream.Stream;

public class ModNoiseGenProvider extends NoiseRouterData {
    public static final ResourceKey<NoiseGeneratorSettings> AVALON_NOISE = ResourceKey.create(Registries.NOISE_SETTINGS,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "excessive_oceans"));



    public static void bootstrapType(BootstapContext<NoiseGeneratorSettings> context) {
        HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> DensityRegistry = context.lookup(Registries.DENSITY_FUNCTION);
        context.register(AVALON_NOISE, new NoiseGeneratorSettings(
                new NoiseSettings(-64, 384, 1, 1),
                Blocks.STONE.defaultBlockState(),
                ModBlocks.AMMONIA_BLOCK.get().defaultBlockState(),
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

    public static DensityFunction getFunction(HolderGetter<DensityFunction> pDensityFunctions, ResourceKey<DensityFunction> pKey) {
        return new DensityFunctions.HolderHolder(pDensityFunctions.getOrThrow(pKey));
    }

    private static ResourceKey<DensityFunction> createKey(String pLocation) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.fromNamespaceAndPath("minecraft",pLocation));
    }

    public static DensityFunction noiseGradientDensity(DensityFunction pMinFunction, DensityFunction pMaxFunction) {
        DensityFunction densityfunction = DensityFunctions.mul(pMaxFunction, pMinFunction);
        return DensityFunctions.mul(DensityFunctions.constant(4.0D), densityfunction.quarterNegative());
    }

    //I am loosing my mind. Why can't ocean biomes be linked to the actual depth of the terrain!?
    //whyyyyyyyyyyyyyyy - @989onan

    //execute in avaliproject:avalon run tp ~ ~ ~
    //above is for the billions of times I will need to test this god forsaken noise gen. - @989onan

    public static NoiseRouter overworld(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
        DensityFunction densityfunction = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5D);
        DensityFunction densityfunction1 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67D);
        DensityFunction densityfunction2 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143D);
        DensityFunction densityfunction3 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_LAVA));
        DensityFunction densityfunction4 = getFunction(pDensityFunctions, createKey( "shift_x"));
        DensityFunction densityfunction5 = getFunction(pDensityFunctions, createKey( "shift_z"));
        DensityFunction densityfunction6 = DensityFunctions.shiftedNoise2d(densityfunction4, densityfunction5, 0.25D, pNoiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction densityfunction7 = DensityFunctions.shiftedNoise2d(densityfunction4, densityfunction5, 0.25D, pNoiseParameters.getOrThrow(Noises.VEGETATION));
        DensityFunction densityfunction8 = getFunction(pDensityFunctions, createKey( "overworld/factor"));
        DensityFunction densityfunction9 = getFunction(pDensityFunctions, createKey( "overworld/depth"));
        DensityFunction densityfunction10 = noiseGradientDensity(DensityFunctions.cache2d(densityfunction8), densityfunction9);
        DensityFunction densityfunction11 = getFunction(pDensityFunctions, createKey( "overworld/sloped_cheese"));
        DensityFunction densityfunction12 = DensityFunctions.min(densityfunction11, DensityFunctions.mul(DensityFunctions.constant(5.0D), getFunction(pDensityFunctions, createKey( "overworld/caves/entrances"))));
        DensityFunction densityfunction13 = DensityFunctions.rangeChoice(densityfunction11, -1000000.0D, 1.5625D, densityfunction12, underground(pDensityFunctions, pNoiseParameters, densityfunction11));
        DensityFunction densityfunction14 = DensityFunctions.min(densityfunction13, getFunction(pDensityFunctions, createKey( "overworld/caves/noodle")));
        DensityFunction densityfunction14_final = DensityFunctions.add(densityfunction13, DensityFunctions.constant(-0.4));
        return new NoiseRouter(
                densityfunction,
                densityfunction1,
                densityfunction2,
                densityfunction3,
                densityfunction6,
                densityfunction7,
                getFunction(pDensityFunctions, createKey("overworld/continents")),
                getFunction(pDensityFunctions, createKey("overworld/erosion")),
                densityfunction9,
                getFunction(pDensityFunctions, createKey("overworld/ridges")),
                DensityFunctions.add(densityfunction10, DensityFunctions.constant(-0.703125D)).clamp(-64.0D, 64.0D),
                densityfunction14_final,
                densityfunction,
                densityfunction,
                densityfunction);
    }

    private static DensityFunction underground(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters, DensityFunction p_256658_) {
        DensityFunction densityfunction = getFunction(pDensityFunctions, createKey("overworld/caves/spaghetti_2d"));
        DensityFunction densityfunction1 = getFunction(pDensityFunctions, createKey("overworld/caves/spaghetti_roughness_function"));
        DensityFunction densityfunction2 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.CAVE_LAYER), 8.0D);
        DensityFunction densityfunction3 = DensityFunctions.mul(DensityFunctions.constant(4.0D), densityfunction2.square());
        DensityFunction densityfunction4 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.CAVE_CHEESE), 0.6666666666666666D);
        DensityFunction densityfunction5 = DensityFunctions.add(DensityFunctions.add(DensityFunctions.constant(0.27D), densityfunction4).clamp(-1.0D, 1.0D), DensityFunctions.add(DensityFunctions.constant(1.5D), DensityFunctions.mul(DensityFunctions.constant(-0.64D), p_256658_)).clamp(0.0D, 0.5D));
        DensityFunction densityfunction6 = DensityFunctions.add(densityfunction3, densityfunction5);
        DensityFunction densityfunction7 = DensityFunctions.min(DensityFunctions.min(densityfunction6, getFunction(pDensityFunctions, createKey( "overworld/caves/entrances"))), DensityFunctions.add(densityfunction, densityfunction1));
        DensityFunction densityfunction8 = getFunction(pDensityFunctions, createKey("overworld/caves/pillars"));
        DensityFunction densityfunction9 = DensityFunctions.rangeChoice(densityfunction8, -1000000.0D, 0.03D, DensityFunctions.constant(-1000000.0D), densityfunction8);
        return DensityFunctions.max(densityfunction7, densityfunction9);
    }


    public static SurfaceRules.RuleSource makeStateRule(Block pBlock) {
        return SurfaceRules.state(pBlock.defaultBlockState());
    }

}
