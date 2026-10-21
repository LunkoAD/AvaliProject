package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.util.HitscanUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class AvaliDroneEntity extends TamableAnimal implements RangedAttackMob, GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT =
            SynchedEntityData.defineId(AvaliDroneEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION =
            SynchedEntityData.defineId(AvaliDroneEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE =
            SynchedEntityData.defineId(AvaliDroneEntity.class, EntityDataSerializers.STRING);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public String animationprocedure = "empty";
    private String prevAnim = "empty";

    public AvaliDroneEntity(EntityType<AvaliDroneEntity> type, Level world) {
        super(type, world);
        this.xpReward = 0;
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.FIRELANCE.get()));
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHOOT, false);
        builder.define(ANIMATION, "undefined");
        builder.define(TEXTURE, "avali_drone");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
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
    protected PathNavigation createNavigation(Level world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new HitscanAttackGoal(this, 1.25, 25, ATTACK_RANGE));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1, 10, 2));
        this.goalSelector.addGoal(3, new HoverWanderGoal());
        this.goalSelector.addGoal(4, new OwnerHurtByTargetGoal(this));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, true, false) {
            @Override
            public boolean canUse() {
                return !AvaliDroneEntity.this.isTame() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !AvaliDroneEntity.this.isTame() && super.canContinueToUse();
            }
        });
        this.targetSelector.addGoal(5, new OwnerHurtTargetGoal(this));
    }

    private class HoverWanderGoal extends RandomStrollGoal {
        HoverWanderGoal() {
            super(AvaliDroneEntity.this, 3, 20);
        }

        @Override
        protected Vec3 getPosition() {
            RandomSource random = AvaliDroneEntity.this.getRandom();
            double x = AvaliDroneEntity.this.getX() + (random.nextFloat() * 2 - 1) * 16;
            double z = AvaliDroneEntity.this.getZ() + (random.nextFloat() * 2 - 1) * 16;
            double groundY = groundHeightAt(BlockPos.containing(x, AvaliDroneEntity.this.getY(), z));
            return new Vec3(x, groundY + HOVER_HEIGHT, z);
        }
    }


    private static final double HOVER_HEIGHT = 3.5;
    private static final double HOVER_MAX_STEP = 0.3;
    private static final double GROUND_SMOOTH_STEP = 0.15;
    private static final double GROUND_RESAMPLE_DISTANCE_SQR = 0.6 * 0.6;

    private double smoothedGroundY = Double.NaN;
    private double groundSampleX = Double.NaN;
    private double groundSampleZ = Double.NaN;
    private double lastGroundSample;

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide())
            applyHover();
    }

    @Override
    public void travel(Vec3 travelVector) {
        super.travel(new Vec3(travelVector.x, 0.0, travelVector.z));
    }

    private void applyHover() {
        double dx = this.getX() - groundSampleX;
        double dz = this.getZ() - groundSampleZ;
        if (Double.isNaN(groundSampleX) || dx * dx + dz * dz > GROUND_RESAMPLE_DISTANCE_SQR) {
            lastGroundSample = groundHeightAt(this.blockPosition());
            groundSampleX = this.getX();
            groundSampleZ = this.getZ();
        }
        double groundY = lastGroundSample;

        if (Double.isNaN(smoothedGroundY))
            smoothedGroundY = groundY;
        else
            smoothedGroundY += Mth.clamp(groundY - smoothedGroundY, -GROUND_SMOOTH_STEP, GROUND_SMOOTH_STEP);

        double targetY = smoothedGroundY + HOVER_HEIGHT;

        double currentY = this.getY();
        double step = Mth.clamp(targetY - currentY, -HOVER_MAX_STEP, HOVER_MAX_STEP);
        double candidateY = currentY + step;

        boolean blocked = !this.level().noBlockCollision(this, hoverBoxAt(candidateY));
        if (!blocked)
            this.setPos(this.getX(), candidateY, this.getZ());

        Vec3 motion = this.getDeltaMovement();
        this.setDeltaMovement(motion.x, 0, motion.z);
    }

    private AABB hoverBoxAt(double y) {
        return this.getDimensions(this.getPose()).makeBoundingBox(this.getX(), y, this.getZ());
    }

    private double groundHeightAt(BlockPos pos) {
        return this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos).getY();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
        this.setNoGravity(true);
    }


    private static final float ATTACK_RANGE = 10f;
    private static final double ATTACK_HITSCAN_RANGE = 16;
    private static final double ATTACK_KNOCKBACK = 0.4;

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (this.level() instanceof ServerLevel serverLevel) {
            double damage = this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            Vec3 origin = this.getEyePosition();
            Vec3 look = target.getEyePosition().subtract(origin).normalize();
            HitscanUtil.fire(serverLevel, this, origin, look, damage, ATTACK_KNOCKBACK, ATTACK_HITSCAN_RANGE);
        }
    }

    private static class HitscanAttackGoal extends Goal {
        private final Mob mob;
        private final RangedAttackMob attacker;
        private final double speed;
        private final int attackIntervalMin;
        private final int attackIntervalMax;
        private final float attackRadius;
        private final float attackRadiusSqr;

        @Nullable
        private LivingEntity target;
        private int seeTime;
        private int attackTime = -1;

        HitscanAttackGoal(RangedAttackMob attacker, double speed, int attackInterval, float attackRadius) {
            this.attacker = attacker;
            this.mob = (Mob) attacker;
            this.speed = speed;
            this.attackIntervalMin = attackInterval;
            this.attackIntervalMax = attackInterval;
            this.attackRadius = attackRadius;
            this.attackRadiusSqr = attackRadius * attackRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity candidate = this.mob.getTarget();
            if (candidate == null || !candidate.isAlive())
                return false;
            this.target = candidate;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void stop() {
            this.target = null;
            this.seeTime = 0;
            this.attackTime = -1;
            ((AvaliDroneEntity) this.attacker).entityData.set(SHOOT, false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            double distanceSqr = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
            boolean sees = this.mob.getSensing().hasLineOfSight(this.target);
            this.seeTime = sees ? this.seeTime + 1 : 0;

            if (distanceSqr <= this.attackRadiusSqr && this.seeTime >= 5)
                this.mob.getNavigation().stop();
            else
                this.mob.getNavigation().moveTo(this.target, this.speed);
            this.mob.getLookControl().setLookAt(this.target, 30f, 30f);

            if (--this.attackTime == 0) {
                if (!sees) {
                    ((AvaliDroneEntity) this.attacker).entityData.set(SHOOT, false);
                    return;
                }
                ((AvaliDroneEntity) this.attacker).entityData.set(SHOOT, true);
                float distanceFactor = Mth.clamp((float) Math.sqrt(distanceSqr) / this.attackRadius, 0.1f, 1.0f);
                this.attacker.performRangedAttack(this.target, distanceFactor);
                this.attackTime = Mth.floor(distanceFactor * (this.attackIntervalMax - this.attackIntervalMin) + this.attackIntervalMin);
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(distanceSqr) / this.attackRadius, this.attackIntervalMin, this.attackIntervalMax));
            } else {
                ((AvaliDroneEntity) this.attacker).entityData.set(SHOOT, false);
            }
        }
    }


    @Override
    public boolean isFood(ItemStack stack) {
        return List.of(ModItems.AVALI_DATA_CHIT.get()).contains(stack.getItem());
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean clientSide = this.level().isClientSide();

        if (stack.getItem() instanceof SpawnEggItem)
            return super.mobInteract(player, hand);

        if (clientSide) {
            boolean canRespond = (this.isTame() && this.isOwnedBy(player)) || this.isFood(stack);
            return canRespond ? InteractionResult.sidedSuccess(true) : InteractionResult.PASS;
        }

        if (this.isTame()) {
            if (!this.isOwnedBy(player))
                return InteractionResult.PASS;
            if (this.isFood(stack) && this.getHealth() < this.getMaxHealth()) {
                this.usePlayerItem(player, hand, stack);
                FoodProperties food = stack.getFoodProperties(this);
                this.heal(food != null ? (float) food.nutrition() : 1f);
                return InteractionResult.sidedSuccess(false);
            }
            return super.mobInteract(player, hand);
        }

        if (this.isFood(stack)) {
            this.usePlayerItem(player, hand, stack);
            if (this.random.nextInt(3) == 0 && !EventHooks.onAnimalTame(this, player)) {
                this.tame(player);
                this.level().broadcastEntityEvent(this, (byte) 7);
            } else {
                this.level().broadcastEntityEvent(this, (byte) 6);
            }
            this.setPersistenceRequired();
            return InteractionResult.sidedSuccess(false);
        }

        InteractionResult result = super.mobInteract(player, hand);
        if (result == InteractionResult.SUCCESS || result == InteractionResult.CONSUME)
            this.setPersistenceRequired();
        return result;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mate) {
        AvaliDroneEntity offspring = ModEntities.AVALI_DRONE.get().create(serverLevel);
        if (offspring != null)
            offspring.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(offspring.blockPosition()), MobSpawnType.BREEDING, null);
        return offspring;
    }


    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.death"));
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(serverLevel, source, recentlyHit);
        this.spawnAtLocation(new ItemStack(Items.IRON_INGOT, 6));
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience(this);
        }
    }


    public static void init(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.AVALI_DRONE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) ->
                        world.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && world.getRawBrightness(pos, 0) > 8,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ARMOR, 5)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.STEP_HEIGHT, 0.6)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3)
                .add(Attributes.FLYING_SPEED, 0.3);
    }


    private PlayState movementPredicate(AnimationState event) {
        if (!this.animationprocedure.equals("empty"))
            return PlayState.STOP;
        boolean moving = event.isMoving() || !(event.getLimbSwingAmount() > -0.15f && event.getLimbSwingAmount() < 0.15f);
        return event.setAndContinue(RawAnimation.begin().thenLoop(moving ? "Fly" : "Idle"));
    }

    private PlayState procedurePredicate(AnimationState event) {
        boolean stopped = event.getController().getAnimationState() == AnimationController.State.STOPPED;
        boolean changed = !this.animationprocedure.equals(this.prevAnim);

        if (this.animationprocedure.equals("empty")) {
            this.prevAnim = "empty";
            return PlayState.STOP;
        }
        if (stopped || changed) {
            if (changed)
                event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        }
        this.prevAnim = this.animationprocedure;
        return PlayState.CONTINUE;
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
