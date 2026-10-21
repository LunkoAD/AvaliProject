package com.lunkoashtail.avaliproject.worldgen;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.item.DisabledContent;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Optional;

public class TropicalDenFeature extends FootprintFeature {
    private static final int RADIUS = 2;
    private static final int WALL_HEIGHT = 3;
    private static final float ALTAR_CHANCE = 0.3F;

    public TropicalDenFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        Optional<BlockPos> found = findFloor(level, context.origin(), 4);
        if (found.isEmpty() || !hasHeadroom(level, found.get(), RADIUS, WALL_HEIGHT + 1)) return false;
        BlockPos floor = found.get();

        BlockState roof = (random.nextBoolean() ? ModBlocks.PAPAYA_LEAVES : ModBlocks.PASSION_FRUIT_LEAVES).get()
                .defaultBlockState().setValue(LeavesBlock.PERSISTENT, true);

        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dz = -RADIUS; dz <= RADIUS; dz++) {
                BlockPos column = floor.offset(dx, 0, dz);
                set(level, column.below(), Blocks.JUNGLE_PLANKS.defaultBlockState());
                boolean corner = Math.abs(dx) == RADIUS && Math.abs(dz) == RADIUS;
                if (corner) {
                    for (int y = 0; y < WALL_HEIGHT; y++) {
                        set(level, column.above(y), ModBlocks.PALM.log.get().defaultBlockState());
                    }
                }
                set(level, column.above(WALL_HEIGHT), roof);
            }
        }

        Direction facing = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        placeLootContainer(level, floor.relative(facing.getOpposite(), RADIUS - 1),
                Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, facing), ModLootTables.TROPICAL_DEN, random);
        if (!DisabledContent.isDisabled(ModBlocks.MOONLIT_OFFERING_ALTAR) && random.nextFloat() < ALTAR_CHANCE) {
            set(level, floor.relative(facing, RADIUS - 1), ModBlocks.MOONLIT_OFFERING_ALTAR.get().defaultBlockState());
        }
        return true;
    }
}
