package com.lunkoashtail.avaliproject.block.custom;

import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoralCultivationBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<CoralCultivationBlock> CODEC = simpleCodec(CoralCultivationBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 12.0, 15.0);
    private static final int PULSE_INTERVAL = 40;
    private static final int BASE_RADIUS = 5;
    private static final int MANOKIT_BONUS_RADIUS = 2;
    private static final int BASE_DURATION = 100;
    private static final int MANOKIT_DURATION = 200;

    public CoralCultivationBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<CoralCultivationBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public static boolean isActive(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.getValue(WATERLOGGED)) return true;
        for (Direction direction : Direction.values()) {
            if (level.getFluidState(pos.relative(direction)).is(FluidTags.WATER)) return true;
        }
        return false;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide() && !oldState.is(this)) {
            level.scheduleTick(pos, this, PULSE_INTERVAL);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isActive(state, level, pos)) {
            pulse(level, pos);
        }
        level.scheduleTick(pos, this, PULSE_INTERVAL);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.getBlockTicks().hasScheduledTick(pos, this)) {
            level.scheduleTick(pos, this, PULSE_INTERVAL);
        }
    }

    private static void pulse(ServerLevel level, BlockPos pos) {
        double reach = BASE_RADIUS + MANOKIT_BONUS_RADIUS;
        AABB area = new AABB(pos).inflate(reach);
        for (Player player : level.getEntitiesOfClass(Player.class, area)) {
            boolean manokit = player.getData(ModAttachments.SPECIES) == Species.MANOKIT;
            double radius = manokit ? reach : BASE_RADIUS;
            if (player.distanceToSqr(pos.getCenter()) > radius * radius) continue;
            int duration = manokit ? MANOKIT_DURATION : BASE_DURATION;
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, duration, 0, true, true));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, 0, true, true));
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, duration, 0, true, true));
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!isActive(state, level, pos) || random.nextInt(3) != 0) return;
        double x = pos.getX() + 0.2 + random.nextDouble() * 0.6;
        double y = pos.getY() + 0.8;
        double z = pos.getZ() + 0.2 + random.nextDouble() * 0.6;
        level.addParticle(state.getValue(WATERLOGGED) ? ParticleTypes.BUBBLE_COLUMN_UP : ParticleTypes.DOLPHIN,
                x, y, z, 0.0, 0.04, 0.0);
        level.addParticle(ParticleTypes.GLOW, x, y, z, 0.0, 0.02, 0.0);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(WATERLOGGED,
                context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level,
                                     BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}
