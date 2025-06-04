package com.lunkoashtail.avaliproject.worldgen.dimensions;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.BiomeData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.OptionalLong;
import java.util.function.Function;

public class Register {

    public static final ResourceKey<LevelStem> AVALON_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avalon"));
    public static final ResourceKey<Level> AVALON_LEVEL = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avalon"));
    public static final ResourceKey<DimensionType> AVALON = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avalon_type"));


    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(AVALON, new DimensionType(
                OptionalLong.of(12000),
                true,
                false,
                false,
                false,
                1.0,
                true,
                true,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0F,
                new DimensionType.MonsterSettings(
                        false,
                        false,
                        ConstantInt.of(0),
                        0
                )


        ));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                    new Climate.ParameterList<>(
                            List.of(
                                    Pair.of(
                                            new Climate.ParameterPoint(
                                                    Climate.Parameter.point(-0.4f),
                                                    Climate.Parameter.point(0.2f),
                                                    Climate.Parameter.span(0.1f,0),
                                                    Climate.Parameter.point(0.0f),
                                                    Climate.Parameter.point(0.0f),
                                                    Climate.Parameter.point(0.0f),
                                                    0
                                            ),  ForgeRegistries.BIOMES.getHolder(Biomes.SNOWY_PLAINS).get()
                                    ),
                                    Pair.of(
                                            new Climate.ParameterPoint(
                                                    Climate.Parameter.point(-0.4f),
                                                    Climate.Parameter.point(0.2f),
                                                    Climate.Parameter.span(0.1f,0),
                                                    Climate.Parameter.point(0.0f),
                                                    Climate.Parameter.point(0.0f),
                                                    Climate.Parameter.point(0.0f),
                                                    0
                                            ),  ForgeRegistries.BIOMES.getHolder(Biomes.SNOWY_PLAINS).get()
                                    ),
                                    Pair.of(
                                            new Climate.ParameterPoint(
                                                    Climate.Parameter.point(-0.4f),
                                                    Climate.Parameter.point(0.2f),
                                                    Climate.Parameter.span(0.1f,0),
                                                    Climate.Parameter.point(0.0f),
                                                    Climate.Parameter.point(0.0f),
                                                    Climate.Parameter.point(0.0f),
                                                    0
                                            ),  ForgeRegistries.BIOMES.getHolder(Biomes.SNOWY_PLAINS).get()
                                    )
                            )

                    )
            ),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED));

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(Register.AVALON), noiseBasedChunkGenerator);

        context.register(AVALON_KEY, stem);
    }

    //public static final RegistryObject<Level> AVALON_LEVEL = LEVELS.register("avalon", () -> );
}
