package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.block.custom.GrowthVatBlock;
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

public class GrowthVatBlockEntity extends BlockEntity implements GeoBlockEntity {
    private static final RawAnimation[] STAGES = {
            RawAnimation.begin().thenLoop("animation.growth_vat.empty"),
            RawAnimation.begin().thenLoop("animation.growth_vat.stage_1"),
            RawAnimation.begin().thenLoop("animation.growth_vat.stage_2"),
            RawAnimation.begin().thenLoop("animation.growth_vat.stage_3")
    };

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public GrowthVatBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GROWTH_VAT_BE.get(), pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "vat", 10, this::predicate));
    }

    private PlayState predicate(AnimationState<GrowthVatBlockEntity> state) {
        return state.setAndContinue(STAGES[getBlockState().getValue(GrowthVatBlock.STAGE)]);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
