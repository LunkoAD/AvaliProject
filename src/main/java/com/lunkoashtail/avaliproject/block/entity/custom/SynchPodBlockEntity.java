package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.block.custom.SynchPodBlock;
import com.lunkoashtail.avaliproject.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
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

public class SynchPodBlockEntity extends BlockEntity implements GeoBlockEntity {
    private static final String EMPTY_NAME = "animation.synch_pod.empty";
    private static final String OCCUPIED_NAME = "animation.synch_pod.occupied";
    private static final String CLOSE_NAME = "animation.synch_pod.close";
    private static final RawAnimation EMPTY = RawAnimation.begin().thenLoop(EMPTY_NAME);
    private static final RawAnimation OCCUPIED = RawAnimation.begin().thenLoop(OCCUPIED_NAME);
    private static final RawAnimation OPEN = RawAnimation.begin().thenPlayAndHold("animation.synch_pod.open");
    private static final RawAnimation CLOSE_EMPTY = RawAnimation.begin().thenPlay(CLOSE_NAME).thenLoop(EMPTY_NAME);
    private static final RawAnimation CLOSE_OCCUPIED = RawAnimation.begin().thenPlay(CLOSE_NAME).thenLoop(OCCUPIED_NAME);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean wasOpen;
    private boolean closing;
    private boolean closingOccupied;

    public SynchPodBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SYNCH_POD_BE.get(), pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "pod", 5, this::predicate));
    }

    private PlayState predicate(AnimationState<SynchPodBlockEntity> state) {
        BlockState block = getBlockState();
        boolean occupied = block.getValue(SynchPodBlock.OCCUPIED);
        if (block.getValue(SynchPodBlock.OPEN)) {
            wasOpen = true;
            closing = false;
            return state.setAndContinue(OPEN);
        }
        if (wasOpen) {
            wasOpen = false;
            closing = true;
            closingOccupied = occupied;
        }
        if (closing && closingOccupied != occupied) {
            closing = false;
        }
        if (closing) {
            return state.setAndContinue(occupied ? CLOSE_OCCUPIED : CLOSE_EMPTY);
        }
        return state.setAndContinue(occupied ? OCCUPIED : EMPTY);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
