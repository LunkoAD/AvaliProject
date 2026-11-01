package com.lunkoashtail.avaliproject.block.custom;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MachinePartBlock extends Block {
    public static final MapCodec<MachinePartBlock> CODEC = simpleCodec(MachinePartBlock::new);
    private static final int RANGE = 3;
    public static final IntegerProperty OFFSET_X = IntegerProperty.create("dx", 0, RANGE * 2);
    public static final IntegerProperty OFFSET_Y = IntegerProperty.create("dy", 0, 4);
    public static final IntegerProperty OFFSET_Z = IntegerProperty.create("dz", 0, RANGE * 2);

    public MachinePartBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(OFFSET_X, RANGE).setValue(OFFSET_Y, 0).setValue(OFFSET_Z, RANGE));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OFFSET_X, OFFSET_Y, OFFSET_Z);
    }

    public static BlockPos masterOf(BlockState state, BlockPos pos) {
        return pos.offset(RANGE - state.getValue(OFFSET_X), -state.getValue(OFFSET_Y), RANGE - state.getValue(OFFSET_Z));
    }

    @Nullable
    private static BlockState master(BlockGetter level, BlockState state, BlockPos pos) {
        BlockState master = level.getBlockState(masterOf(state, pos));
        return MachineFootprint.of(master.getBlock()) != null ? master : null;
    }

    public static boolean canPlace(BlockPlaceContext context, Block block, Direction facing) {
        MachineFootprint footprint = MachineFootprint.of(block);
        return footprint == null || footprint.canPlace(context.getLevel(), context.getClickedPos(), facing,
                cell -> context.getLevel().getBlockState(cell).canBeReplaced(context));
    }

    public static void placeParts(Level level, BlockPos master, BlockState state) {
        MachineFootprint footprint = MachineFootprint.of(state.getBlock());
        if (footprint == null || level.isClientSide()) return;
        BlockState part = ModBlocks.MACHINE_PART.get().defaultBlockState();
        for (BlockPos cell : footprint.cells(master, MachineFootprint.facingOf(state))) {
            if (cell.equals(master)) continue;
            level.setBlock(cell, part
                    .setValue(OFFSET_X, cell.getX() - master.getX() + RANGE)
                    .setValue(OFFSET_Y, cell.getY() - master.getY())
                    .setValue(OFFSET_Z, cell.getZ() - master.getZ() + RANGE), Block.UPDATE_ALL);
        }
    }

    public static void removeParts(Level level, BlockPos master, BlockState state) {
        MachineFootprint footprint = MachineFootprint.of(state.getBlock());
        if (footprint == null || level.isClientSide()) return;
        for (BlockPos cell : footprint.cells(master, MachineFootprint.facingOf(state))) {
            BlockState existing = level.getBlockState(cell);
            if (existing.getBlock() instanceof MachinePartBlock && masterOf(existing, cell).equals(master)) {
                level.setBlock(cell, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            }
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        BlockState master = master(level, state, pos);
        MachineFootprint footprint = master == null ? null : MachineFootprint.of(master.getBlock());
        return footprint == null ? Shapes.block() : footprint.cellShape(state.getValue(OFFSET_Y));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockState master = master(level, state, pos);
        if (master == null) return InteractionResult.PASS;
        BlockPos masterPos = masterOf(state, pos);
        return master.useWithoutItem(level, player, new BlockHitResult(hitResult.getLocation(), hitResult.getDirection(), masterPos, hitResult.isInside()));
    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        BlockState master = master(level, state, pos);
        return master == null ? super.getDestroyProgress(state, player, level, pos)
                : master.getDestroyProgress(player, level, masterOf(state, pos));
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockState master = master(level, state, pos);
        if (master != null && !level.isClientSide()) {
            boolean drop = !player.isCreative() && player.hasCorrectToolForDrops(master);
            level.destroyBlock(masterOf(state, pos), drop, player);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && !level.isClientSide() && master(level, state, pos) != null) {
            level.destroyBlock(masterOf(state, pos), true);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        BlockState master = master(level, state, pos);
        return master == null ? ItemStack.EMPTY : new ItemStack(master.getBlock());
    }
}
