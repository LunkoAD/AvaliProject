package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.block.custom.ProtogenStationBlock;
import com.lunkoashtail.avaliproject.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ProtogenStationBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private RawAnimation idle;
    private RawAnimation active;

    public ProtogenStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PROTOGEN_STATION_BE.get(), pos, state);
    }

    public String getStationName() {
        return BuiltInRegistries.BLOCK.getKey(getBlockState().getBlock()).getPath();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "station", 10, this::predicate));
    }

    private PlayState predicate(AnimationState<ProtogenStationBlockEntity> state) {
        BlockState block = getBlockState();
        if (idle == null && block.getBlock() instanceof ProtogenStationBlock station) {
            String prefix = "animation." + getStationName() + ".";
            idle = RawAnimation.begin().thenLoop(prefix + station.getIdleAnimation());
            active = RawAnimation.begin().thenLoop(prefix + station.getActiveAnimation());
        }
        if (idle == null) return PlayState.STOP;
        return state.setAndContinue(block.getValue(ProtogenStationBlock.ACTIVE) ? active : idle);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
