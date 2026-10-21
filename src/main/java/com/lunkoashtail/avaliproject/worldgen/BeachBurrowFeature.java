package com.lunkoashtail.avaliproject.worldgen;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.custom.ShellCoverBlock;
import com.lunkoashtail.avaliproject.block.custom.ShellType;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BeachBurrowFeature extends FootprintFeature {
    private static final int RADIUS = 2;

    public BeachBurrowFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos surface = context.origin();
        if (!level.getBlockState(surface).isAir() || !level.getBlockState(surface.below()).is(BlockTags.SAND)) return false;

        BlockPos burrow = surface.below();
        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dz = -RADIUS; dz <= RADIUS; dz++) {
                int ring = Math.max(Math.abs(dx), Math.abs(dz));
                BlockPos floor = burrow.offset(dx, 0, dz);
                if (ring == 1) {
                    set(level, floor, Blocks.SANDSTONE.defaultBlockState());
                    set(level, floor.below(), Blocks.SANDSTONE.defaultBlockState());
                } else if (ring == RADIUS && random.nextInt(3) == 0 && level.getBlockState(floor.above()).isAir()
                        && level.getBlockState(floor).is(BlockTags.SAND)) {
                    ShellType type = ShellType.values()[random.nextInt(ShellType.values().length)];
                    set(level, floor.above(), ModBlocks.SHELL_COVER.get().defaultBlockState().setValue(ShellCoverBlock.TYPE, type));
                }
            }
        }

        placeLootContainer(level, burrow, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.UP),
                ModLootTables.BEACH_BURROW, random);
        set(level, burrow.below(), Blocks.SANDSTONE.defaultBlockState());
        set(level, burrow.offset(random.nextBoolean() ? 1 : -1, 1, 0), ModBlocks.SEA_GLASS.get().defaultBlockState());
        return true;
    }
}
