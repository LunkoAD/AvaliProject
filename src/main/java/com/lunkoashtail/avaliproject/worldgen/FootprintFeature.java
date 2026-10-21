package com.lunkoashtail.avaliproject.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.event.EventHooks;

import java.util.Optional;

public abstract class FootprintFeature extends Feature<NoneFeatureConfiguration> {

    protected FootprintFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    protected static void set(WorldGenLevel level, BlockPos pos, BlockState state) {
        level.setBlock(pos, state, Block.UPDATE_CLIENTS);
    }

    protected static void placeLootContainer(WorldGenLevel level, BlockPos pos, BlockState state,
                                             ResourceKey<LootTable> lootTable, RandomSource random) {
        set(level, pos, state);
        RandomizableContainer.setBlockEntityLootTable(level, random, pos, lootTable);
    }

    protected static Optional<BlockPos> findFloor(WorldGenLevel level, BlockPos origin, int maxDepth) {
        BlockPos.MutableBlockPos cursor = origin.mutable();
        for (int i = 0; i < maxDepth; i++) {
            BlockState below = level.getBlockState(cursor.below());
            if (level.getBlockState(cursor).isAir() && below.isFaceSturdy(level, cursor.below(), Direction.UP)) {
                return Optional.of(cursor.immutable());
            }
            cursor.move(Direction.DOWN);
        }
        return Optional.empty();
    }

    protected static boolean hasHeadroom(WorldGenLevel level, BlockPos floor, int radius, int height) {
        for (BlockPos pos : BlockPos.betweenClosed(floor.offset(-radius, 0, -radius), floor.offset(radius, height - 1, radius))) {
            if (!level.getBlockState(pos).canBeReplaced()) return false;
        }
        return true;
    }

    protected static <T extends Mob> void spawnGuardian(WorldGenLevel level, EntityType<T> type, BlockPos pos) {
        T mob = type.create(level.getLevel());
        if (mob == null) return;
        mob.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, level.getRandom().nextFloat() * 360.0F, 0.0F);
        EventHooks.finalizeMobSpawn(mob, level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null);
        mob.setPersistenceRequired();
        level.addFreshEntityWithPassengers(mob);
    }
}
