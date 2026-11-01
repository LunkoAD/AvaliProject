package com.lunkoashtail.avaliproject.block.custom;

import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenGeneratorBlockEntity;
import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenStationBlockEntity;
import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenWorkStationBlockEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ProtogenStationBlock extends BaseEntityBlock {
    public static final MapCodec<ProtogenStationBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            StationKind.CODEC.fieldOf("kind").forGetter(ProtogenStationBlock::kind),
            Codec.STRING.fieldOf("idle_animation").forGetter(ProtogenStationBlock::getIdleAnimation),
            Codec.STRING.fieldOf("active_animation").forGetter(ProtogenStationBlock::getActiveAnimation),
            Codec.INT.fieldOf("width").forGetter(block -> block.width),
            Codec.INT.fieldOf("height").forGetter(block -> block.height),
            Codec.INT.fieldOf("depth").forGetter(block -> block.depth),
            propertiesCodec()).apply(instance, ProtogenStationBlock::new));
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    private final StationKind kind;
    private final String idleAnimation;
    private final String activeAnimation;
    private final int width;
    private final int height;
    private final int depth;

    public ProtogenStationBlock(StationKind kind, String idleAnimation, String activeAnimation, int width, int height, int depth,
                                BlockBehaviour.Properties properties) {
        super(properties);
        this.kind = kind;
        this.idleAnimation = idleAnimation;
        this.activeAnimation = activeAnimation;
        this.width = width;
        this.height = height;
        this.depth = depth;
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ACTIVE, false));
    }

    public StationKind kind() {
        return kind;
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
        MachineFootprint footprint = MachineFootprint.of(this);
        return footprint == null ? Shapes.block() : footprint.cellShape(0);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        if (!MachinePartBlock.canPlace(context, this, facing)) return null;
        return defaultBlockState().setValue(FACING, facing);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        MachinePartBlock.placeParts(level, pos, state);
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
        if (level.getBlockEntity(pos) instanceof ProtogenStationBlockEntity station && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(station, pos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof ProtogenStationBlockEntity station) station.onBroken();
            MachinePartBlock.removeParts(level, pos, state);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return kind == StationKind.GENERATOR
                ? new ProtogenGeneratorBlockEntity(pos, state)
                : new ProtogenWorkStationBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return (tickLevel, tickPos, tickState, blockEntity) -> {
            if (blockEntity instanceof ProtogenStationBlockEntity station) station.serverTick();
        };
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
