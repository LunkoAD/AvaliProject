package com.lunkoashtail.avaliproject.block.custom;

import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenStationBlockEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ProtogenStationBlock extends BaseEntityBlock {
    public static final MapCodec<ProtogenStationBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("idle_animation").forGetter(ProtogenStationBlock::getIdleAnimation),
            Codec.STRING.fieldOf("active_animation").forGetter(ProtogenStationBlock::getActiveAnimation),
            Codec.INT.fieldOf("width").forGetter(block -> block.width),
            Codec.INT.fieldOf("height").forGetter(block -> block.height),
            Codec.INT.fieldOf("depth").forGetter(block -> block.depth),
            propertiesCodec()).apply(instance, ProtogenStationBlock::new));
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    private final String idleAnimation;
    private final String activeAnimation;
    private final int width;
    private final int height;
    private final int depth;
    private final VoxelShape shapeX;
    private final VoxelShape shapeZ;

    public ProtogenStationBlock(String idleAnimation, String activeAnimation, int width, int height, int depth,
                                BlockBehaviour.Properties properties) {
        super(properties);
        this.idleAnimation = idleAnimation;
        this.activeAnimation = activeAnimation;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.shapeX = Block.box(8.0 - width / 2.0, 0.0, 8.0 - depth / 2.0, 8.0 + width / 2.0, height, 8.0 + depth / 2.0);
        this.shapeZ = Block.box(8.0 - depth / 2.0, 0.0, 8.0 - width / 2.0, 8.0 + depth / 2.0, height, 8.0 + width / 2.0);
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ACTIVE, false));
    }

    public String getIdleAnimation() {
        return idleAnimation;
    }

    public String getActiveAnimation() {
        return activeAnimation;
    }

    @Override
    protected MapCodec<ProtogenStationBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(FACING).getAxis() == Direction.Axis.Z ? shapeX : shapeZ;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        boolean active = !state.getValue(ACTIVE);
        level.setBlock(pos, state.setValue(ACTIVE, active), Block.UPDATE_ALL);
        level.playSound(null, pos, active ? SoundEvents.BEACON_ACTIVATE : SoundEvents.BEACON_DEACTIVATE,
                SoundSource.BLOCKS, 0.6F, 1.4F);
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ProtogenStationBlockEntity(pos, state);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
