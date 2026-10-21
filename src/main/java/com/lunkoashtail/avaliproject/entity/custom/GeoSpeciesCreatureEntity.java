package com.lunkoashtail.avaliproject.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class GeoSpeciesCreatureEntity extends SpeciesCreatureEntity implements GeoEntity {
    protected static final Animations SIMPLE_ANIMATIONS = new Animations(
            loop("Idle"), loop("Walk"), loop("Run"), null, null, null, null, null);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected GeoSpeciesCreatureEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    public abstract String getTextureName();

    protected Animations animations() {
        return SIMPLE_ANIMATIONS;
    }

    @Nullable
    protected RawAnimation attackAnimation() {
        return null;
    }

    private static RawAnimation loop(String name) {
        return RawAnimation.begin().thenLoop(name);
    }

    private PlayState movementPredicate(AnimationState<GeoSpeciesCreatureEntity> state) {
        return state.setAndContinue(selectAnimation(state.isMoving() && !isInSittingPose()));
    }

    private RawAnimation selectAnimation(boolean moving) {
        Animations anims = animations();
        if (anims.swim() != null && (isSwimming() || (isInWater() && !onGround()))) {
            return anims.swim();
        }
        if (getPose() == Pose.SWIMMING && anims.crawl() != null) {
            return moving ? anims.crawl() : anims.or(anims.crawlIdle(), anims.crawl());
        }
        if (isCrouching() && anims.crouchWalk() != null) {
            return moving ? anims.crouchWalk() : anims.or(anims.crouchIdle(), anims.crouchWalk());
        }
        if (!moving) {
            return anims.idle();
        }
        return isRunning() ? anims.or(anims.run(), anims.walk()) : anims.walk();
    }

    protected boolean isRunning() {
        LivingEntity controller = getControllingPassenger();
        if (controller instanceof Player player) {
            return player.isSprinting();
        }
        return isSprinting() || isAggressive();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        RawAnimation attack = attackAnimation();
        if (attack != null) {
            controllers.add(new AnimationController<>(this, "attack", 0, state -> PlayState.STOP)
                    .triggerableAnim("attack", attack));
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && attackAnimation() != null) {
            triggerAnim("attack", "attack");
        }
        return hit;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public record Animations(RawAnimation idle, RawAnimation walk, @Nullable RawAnimation run,
                             @Nullable RawAnimation crouchIdle, @Nullable RawAnimation crouchWalk,
                             @Nullable RawAnimation crawlIdle, @Nullable RawAnimation crawl,
                             @Nullable RawAnimation swim) {

        public static Animations biped(String prefix) {
            return new Animations(loop(prefix + "Idle"), loop(prefix + "walk"), loop(prefix + "run"),
                    loop(prefix + "crouch_idle"), loop(prefix + "crouch_walk"),
                    loop(prefix + "crawl_idle"), loop(prefix + "crawling"), loop(prefix + "Swimming"));
        }

        private RawAnimation or(@Nullable RawAnimation preferred, RawAnimation fallback) {
            return preferred != null ? preferred : fallback;
        }
    }
}
