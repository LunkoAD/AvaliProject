package com.lunkoashtail.avaliproject.worldgen;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Optional;

public class WickerEnclaveNestFeature extends FootprintFeature {
    private static final int RADIUS = 2;
    private static final int SEARCH_DEPTH = 12;
    private static final int SITE_ATTEMPTS = 24;
    private static final int SITE_SPREAD = 6;

    public WickerEnclaveNestFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        Optional<BlockPos> found = findSite(level, context.origin(), random);
        if (found.isEmpty()) return false;
        BlockPos floor = found.get();

        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dz = -RADIUS; dz <= RADIUS; dz++) {
                if (Math.abs(dx) == RADIUS && Math.abs(dz) == RADIUS) continue;
                BlockPos ground = floor.offset(dx, -1, dz);
                set(level, ground, random.nextInt(3) == 0 ? Blocks.ROOTED_DIRT.defaultBlockState() : Blocks.COARSE_DIRT.defaultBlockState());
                if ((dx != 0 || dz != 0) && random.nextInt(4) == 0) {
                    set(level, floor.offset(dx, 0, dz), Blocks.MOSS_CARPET.defaultBlockState());
                }
            }
        }

        placeLootContainer(level, floor, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.UP), ModLootTables.WICKER_ENCLAVE_NEST, random);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos corner = floor.relative(direction, RADIUS).relative(direction.getClockWise(), 1);
            set(level, corner.below(), random.nextBoolean()
                    ? ModBlocks.CRYSTALITHIUM_ORE.get().defaultBlockState()
                    : ModBlocks.CRYSTALITHIUM_DEEPSLATE_ORE.get().defaultBlockState());
            set(level, corner, Blocks.AMETHYST_CLUSTER.defaultBlockState().setValue(AmethystClusterBlock.FACING, Direction.UP));
        }

        spawnGuardian(level, ModEntities.WICKERBEAST.get(), floor.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random)));
        return true;
    }

    private static Optional<BlockPos> findSite(WorldGenLevel level, BlockPos origin, RandomSource random) {
        for (int attempt = 0; attempt < SITE_ATTEMPTS; attempt++) {
            BlockPos candidate = attempt == 0 ? origin : origin.offset(
                    random.nextInt(SITE_SPREAD * 2 + 1) - SITE_SPREAD,
                    random.nextInt(SITE_SPREAD * 4 + 1) - SITE_SPREAD * 2,
                    random.nextInt(SITE_SPREAD * 2 + 1) - SITE_SPREAD);
            Optional<BlockPos> floor = findFloor(level, candidate, SEARCH_DEPTH);
            if (floor.isPresent() && hasHeadroom(level, floor.get(), RADIUS, 3)) {
                return floor;
            }
        }
        return Optional.empty();
    }
}
