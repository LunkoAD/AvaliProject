package com.lunkoashtail.avaliproject.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class FruitSaplingBlock extends SaplingBlock {
    private final boolean growsOnSand;

    public FruitSaplingBlock(TreeGrower treeGrower, boolean growsOnSand, Properties properties) {
        super(treeGrower, properties);
        this.growsOnSand = growsOnSand;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || (growsOnSand && state.is(BlockTags.SAND));
    }
}
