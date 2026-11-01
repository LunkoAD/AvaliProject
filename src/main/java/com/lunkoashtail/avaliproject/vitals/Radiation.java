package com.lunkoashtail.avaliproject.vitals;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.custom.ProtogenStationBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class Radiation {

    public static final TagKey<Block> RADIOACTIVE_BLOCKS = TagKey.create(Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "radioactive_blocks"));
    public static final TagKey<Biome> RADIOACTIVE_BIOMES_STRONG = TagKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "radioactive_biomes_strong"));
    public static final TagKey<Biome> RADIOACTIVE_BIOMES_WEAK = TagKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "radioactive_biomes_weak"));

    public static final float NATURAL_DECAY_PER_SECOND = 0.01f;

    private static final int SCAN_RADIUS = 4;
    private static final float ORE_RATE = 0.008f;
    private static final float ORE_CEILING = 6f;
    private static final float GENERATOR_RATE = 0.006f;
    private static final float GENERATOR_CEILING = 1.5f;
    private static final float STRONG_BIOME_RATE = 0.03f;
    private static final float STRONG_BIOME_CEILING = 14f;
    private static final float WEAK_BIOME_RATE = 0.012f;
    private static final float WEAK_BIOME_CEILING = 5f;

    private Radiation() {}

    public static float exposurePerSecond(Level level, BlockPos center, float currentGy) {
        float ore = 0f;
        float generator = 0f;
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-SCAN_RADIUS, -SCAN_RADIUS, -SCAN_RADIUS),
                center.offset(SCAN_RADIUS, SCAN_RADIUS + 1, SCAN_RADIUS))) {
            BlockState state = level.getBlockState(pos);
            if (state.isAir()) continue;
            float falloff = 1f / (1f + (float) pos.distSqr(center) / 8f);
            if (state.is(RADIOACTIVE_BLOCKS)) {
                ore += ORE_RATE * falloff;
            } else if (state.is(ModBlocks.PROTOGEN_GENERATOR.get())
                    && state.hasProperty(ProtogenStationBlock.ACTIVE)
                    && state.getValue(ProtogenStationBlock.ACTIVE)) {
                generator += GENERATOR_RATE * falloff;
            }
        }

        float total = 0f;
        if (currentGy < ORE_CEILING) total += ore;
        if (currentGy < GENERATOR_CEILING) total += generator;
        var biome = level.getBiome(center);
        if (biome.is(RADIOACTIVE_BIOMES_STRONG) && currentGy < STRONG_BIOME_CEILING) total += STRONG_BIOME_RATE;
        else if (biome.is(RADIOACTIVE_BIOMES_WEAK) && currentGy < WEAK_BIOME_CEILING) total += WEAK_BIOME_RATE;
        return total;
    }

    public static boolean isContaminatedWater(Level level, BlockPos pos) {
        var biome = level.getBiome(pos);
        if (biome.is(RADIOACTIVE_BIOMES_STRONG) || biome.is(RADIOACTIVE_BIOMES_WEAK)) return true;
        for (BlockPos scan : BlockPos.betweenClosed(pos.offset(-SCAN_RADIUS, -SCAN_RADIUS, -SCAN_RADIUS),
                pos.offset(SCAN_RADIUS, SCAN_RADIUS, SCAN_RADIUS))) {
            if (level.getBlockState(scan).is(RADIOACTIVE_BLOCKS)) return true;
        }
        return false;
    }
}
