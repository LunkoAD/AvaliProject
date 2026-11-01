package com.lunkoashtail.avaliproject.avaligear.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AvaliEggBlock extends Block {
    public static final MapCodec<AvaliEggBlock> CODEC = simpleCodec(p -> new AvaliEggBlock(p, true));
    private static final VoxelShape FERTILIZED = Block.box(2.5, 0.0, 2.5, 13.5, 16.0, 13.5);
    private static final VoxelShape INERT = Block.box(3.5, 0.0, 3.5, 12.5, 12.0, 12.5);

    private final boolean fertilized;

    public AvaliEggBlock(Properties properties, boolean fertilized) {
        super(properties);
        this.fertilized = fertilized;
    }

    public boolean isFertilized() {
        return fertilized;
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return fertilized ? FERTILIZED : INERT;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSupportCenter(level, pos.below(), Direction.UP);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }
}
