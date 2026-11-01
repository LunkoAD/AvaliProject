package com.lunkoashtail.avaliproject.avaligear.ship;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Comparator;

public class CatalinaConsoleBlock extends CatalinaHullBlock {
    public static final MapCodec<CatalinaConsoleBlock> CODEC = simpleCodec(CatalinaConsoleBlock::new);

    public CatalinaConsoleBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        return level.getEntitiesOfClass(CatalinaEntity.class, new AABB(pos).inflate(14))
                .stream()
                .min(Comparator.comparingDouble(s -> s.distanceToSqr(player)))
                .map(ship -> ship.boardAsPilot(player) ? InteractionResult.CONSUME : InteractionResult.PASS)
                .orElse(InteractionResult.PASS);
    }
}
