package com.lunkoashtail.avaliproject.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public record MachineFootprint(int minX, int maxX, int height, int minZ, int maxZ, int heightPixels,
                               float renderOffsetX, float renderOffsetZ) {
    public static final MachineFootprint GROWTH_VAT = new MachineFootprint(0, 1, 5, 0, 1, 80, 0.5F, 0.5F);
    public static final MachineFootprint SYNCH_POD = new MachineFootprint(-2, 1, 3, -1, 1, 44, -0.25F, -0.375F);
    public static final MachineFootprint GENERATOR = new MachineFootprint(-1, 1, 2, -1, 0, 32, 0.0F, -0.5F);
    public static final MachineFootprint RESEARCH = new MachineFootprint(0, 1, 2, 0, 0, 24, 0.5F, 0.0F);
    public static final MachineFootprint SMALL_STATION = new MachineFootprint(0, 0, 2, 0, 0, 24, 0.0F, 0.0F);

    @Nullable
    public static MachineFootprint of(Block block) {
        if (block instanceof GrowthVatBlock) return GROWTH_VAT;
        if (block instanceof SynchPodBlock) return SYNCH_POD;
        if (block instanceof ProtogenStationBlock station) {
            return switch (station.kind()) {
                case GENERATOR -> GENERATOR;
                case RESEARCH -> RESEARCH;
                default -> SMALL_STATION;
            };
        }
        return null;
    }

    public List<BlockPos> cells(BlockPos master, Direction facing) {
        List<BlockPos> cells = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                int worldX = rotatedX(x, z, facing);
                int worldZ = rotatedZ(x, z, facing);
                for (int y = 0; y < height; y++) {
                    cells.add(master.offset(worldX, y, worldZ));
                }
            }
        }
        return cells;
    }

    public boolean canPlace(LevelReader level, BlockPos master, Direction facing, Predicate<BlockPos> replaceable) {
        for (BlockPos cell : cells(master, facing)) {
            if (cell.equals(master)) continue;
            if (level.isOutsideBuildHeight(cell) || !replaceable.test(cell)) return false;
        }
        return true;
    }

    public VoxelShape cellShape(int layer) {
        int top = Math.max(1, Math.min(16, heightPixels - layer * 16));
        return top >= 16 ? Shapes.block() : Block.box(0, 0, 0, 16, top, 16);
    }

    public AABB renderBounds(BlockPos master, Direction facing) {
        AABB box = new AABB(master);
        for (BlockPos cell : cells(master, facing)) {
            box = box.minmax(new AABB(cell));
        }
        return box.inflate(0.5);
    }

    @Nullable
    public BlockPos findExit(LevelReader level, BlockPos master, Direction facing) {
        List<BlockPos> cells = cells(master, facing);
        List<Direction> order = List.of(facing, facing.getClockWise(), facing.getCounterClockWise(), facing.getOpposite());
        for (Direction direction : order) {
            for (BlockPos cell : cells) {
                if (cell.getY() != master.getY()) continue;
                BlockPos candidate = cell.relative(direction);
                if (cells.contains(candidate)) continue;
                if (level.getBlockState(candidate).getCollisionShape(level, candidate).isEmpty()
                        && level.getBlockState(candidate.above()).getCollisionShape(level, candidate.above()).isEmpty()) {
                    return candidate;
                }
            }
        }
        return null;
    }

    private static int rotatedX(int x, int z, Direction facing) {
        return switch (facing) {
            case SOUTH -> -x;
            case WEST -> z;
            case EAST -> -z;
            default -> x;
        };
    }

    private static int rotatedZ(int x, int z, Direction facing) {
        return switch (facing) {
            case SOUTH -> -z;
            case WEST -> -x;
            case EAST -> x;
            default -> z;
        };
    }

    public static Direction facingOf(BlockState state) {
        return state.hasProperty(ProtogenStationBlock.FACING) ? state.getValue(ProtogenStationBlock.FACING) : Direction.NORTH;
    }
}
