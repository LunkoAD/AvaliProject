package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.block.custom.ProtogenStationBlock;
import com.lunkoashtail.avaliproject.block.custom.StationKind;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class ProtogenPower {
    public static final int RANGE = 32;
    private static final Map<Level, Set<BlockPos>> GENERATORS = new WeakHashMap<>();

    private ProtogenPower() {
    }

    public static void add(Level level, BlockPos pos) {
        GENERATORS.computeIfAbsent(level, key -> new HashSet<>()).add(pos.immutable());
    }

    public static void remove(Level level, BlockPos pos) {
        Set<BlockPos> generators = GENERATORS.get(level);
        if (generators != null) generators.remove(pos);
    }

    public static boolean isPowered(Level level, BlockPos pos) {
        Set<BlockPos> generators = GENERATORS.get(level);
        if (generators == null) return false;
        for (BlockPos generator : generators) {
            if (generator.distSqr(pos) > RANGE * RANGE) continue;
            BlockState state = level.getBlockState(generator);
            if (state.getBlock() instanceof ProtogenStationBlock station && station.kind() == StationKind.GENERATOR
                    && state.getValue(ProtogenStationBlock.ACTIVE)) {
                return true;
            }
        }
        return false;
    }
}
