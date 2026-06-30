package com.lunkoashtail.avaliproject.entity.custom;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class ExpieEntity extends Monster implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ExpieEntity(EntityType<ExpieEntity> type, Level level) {
        super(type, level);
        xpReward = 5;
        setNoAi(false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.STEP_HEIGHT, 0.6);
    }

    // ── Animation controllers ───────────────────────────────────────────────

    private PlayState movementPredicate(AnimationState event) {
        Pose pose = this.getPose();
        double horizSpeed = this.getDeltaMovement().horizontalDistanceSqr();
        boolean isMoving = horizSpeed > 0.001;

        if (pose == Pose.FALL_FLYING) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.Expie.Swimming"));
        }
        if (pose == Pose.SWIMMING) {
            if (this.isSwimming()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("animation.Expie.Swimming"));
            }
            // Pose.SWIMMING + not in water = crawling in 1-block tunnel
            if (isMoving) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.Expie.crawling"));
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.Expie.crawl_idle"));
        }
        if (pose == Pose.CROUCHING) {
            if (isMoving) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.Expie.crouch_walk"));
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.Expie.crouch_idle"));
        }
        if (isMoving) {
            if (this.isSprinting()) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.Expie.run"));
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.Expie.walk"));
        }
        return event.setAndContinue(RawAnimation.begin().thenLoop("animation.Expie.Idle"));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
