package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.entity.ModEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.fluids.FluidType;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import java.util.List;

public class ChrgakbzEntity extends Animal implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ChrgakbzEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ChrgakbzEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ChrgakbzEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";

    public ChrgakbzEntity(EntityType<ChrgakbzEntity> type, Level world) {
        super(type, world);
        xpReward = 2;
        setNoAi(false);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0);
        this.moveControl = new MoveControl(this) {
            @Override
            public void tick() {
                if (ChrgakbzEntity.this.isInWater())
                    ChrgakbzEntity.this.setDeltaMovement(ChrgakbzEntity.this.getDeltaMovement().add(0, 0.005, 0));
                if (this.operation == MoveControl.Operation.MOVE_TO && !ChrgakbzEntity.this.getNavigation().isDone()) {
                    double dx = this.wantedX - ChrgakbzEntity.this.getX();
                    double dy = this.wantedY - ChrgakbzEntity.this.getY();
                    double dz = this.wantedZ - ChrgakbzEntity.this.getZ();
                    float f = (float) (Mth.atan2(dz, dx) * (double) (180 / Math.PI)) - 90;
                    float f1 = (float) (this.speedModifier * ChrgakbzEntity.this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                    ChrgakbzEntity.this.setYRot(this.rotlerp(ChrgakbzEntity.this.getYRot(), f, 10));
                    ChrgakbzEntity.this.yBodyRot = ChrgakbzEntity.this.getYRot();
                    ChrgakbzEntity.this.yHeadRot = ChrgakbzEntity.this.getYRot();
                    if (ChrgakbzEntity.this.isInWater()) {
                        ChrgakbzEntity.this.setSpeed((float) ChrgakbzEntity.this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                        float f2 = -(float) (Mth.atan2(dy, (float) Math.sqrt(dx * dx + dz * dz)) * (180 / Math.PI));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), -85, 85);
                        ChrgakbzEntity.this.setXRot(this.rotlerp(ChrgakbzEntity.this.getXRot(), f2, 5));
                        float f3 = Mth.cos(ChrgakbzEntity.this.getXRot() * (float) (Math.PI / 180.0));
                        ChrgakbzEntity.this.setZza(f3 * f1);
                        ChrgakbzEntity.this.setYya((float) (f1 * dy));
                    } else {
                        ChrgakbzEntity.this.setSpeed(f1 * 0.05F);
                    }
                } else {
                    ChrgakbzEntity.this.setSpeed(0);
                    ChrgakbzEntity.this.setYya(0);
                    ChrgakbzEntity.this.setZza(0);
                }
            }
        };
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "chrgakbz");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new WaterBoundPathNavigation(this, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1, 40));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.2));
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        this.spawnAtLocation(new ItemStack(Items.SCUTE, 1+looting));
    }

    @Override
    public SoundEvent getAmbientSound() {
        return this.isInWater() ? null : SoundEvents.TURTLE_AMBIENT_LAND;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.TURTLE_HURT;
    }


    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.TURTLE_DEATH;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.DROWN))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1f);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
        ChrgakbzEntity retval = ModEntities.CHRGAKBZ.get().create(serverWorld);
        retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null,null);
        return retval;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return List.of().contains(stack.getItem());
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if(type == ForgeMod.WATER_TYPE.get()){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return world.isUnobstructed(this);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }

    public static void init(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.CHRGAKBZ.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getBlockState(pos).is(Blocks.WATER) && world.getBlockState(pos.above()).is(Blocks.WATER)), SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 60);
        builder = builder.add(Attributes.ARMOR, 5);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        builder = builder.add(ForgeMod.SWIM_SPEED.get(), 0.3);
        return builder;
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if (this.isInWaterOrBubble()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("Swim"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("Idle"));
        }
        return PlayState.STOP;
    }

    String prevAnim = "empty";

    private PlayState procedurePredicate(AnimationState event) {
        if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
            if (!this.animationprocedure.equals(prevAnim))
                event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        } else if (animationprocedure.equals("empty")) {
            prevAnim = "empty";
            return PlayState.STOP;
        }
        prevAnim = this.animationprocedure;
        return PlayState.CONTINUE;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove(ChrgakbzEntity.RemovalReason.KILLED);
            this.dropExperience();
        }
    }

    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
    }

    public void setAnimation(String animation) {
        this.entityData.set(ANIMATION, animation);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}