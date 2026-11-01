package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.client.ProtogenVariant;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesSkins;
import net.minecraft.world.entity.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;
import net.minecraft.Util;
import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenWorkStationBlockEntity;
import com.lunkoashtail.avaliproject.entity.ai.ProtogenStationWorkGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;


public class ProtogenEntity extends Monster implements GeoEntity, CosmeticVariantTarget, SkinnedSpeciesEntity {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ProtogenEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ProtogenEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ProtogenEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ProtogenEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> SKIN = SynchedEntityData.defineId(ProtogenEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(ProtogenEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final String SKIN_TAG = "Skin";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";
    @Nullable
    private BlockPos workStation;

    public ProtogenEntity(EntityType<ProtogenEntity> type, Level world) {
        super(type, world);
        xpReward = 2;
        setNoAi(false);
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected boolean canPerformAttack(LivingEntity entity) {
                return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < (this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth()) && this.mob.getSensing().hasLineOfSight(entity);
            }
        });
        this.goalSelector.addGoal(1, new ProtogenStationWorkGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
    }

    protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource source, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(serverLevel, source, recentlyHitIn);
        this.spawnAtLocation(new ItemStack(ModItems.PROTOGEN_RAM.get()));
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.death"));
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose).scale(1f);
    }

    public static void init(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.PROTOGEN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && world.getRawBrightness(pos, 0) > 8), RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 20);
        builder = builder.add(Attributes.ARMOR, 2);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
        builder = builder.add(Attributes.FOLLOW_RANGE, 12);
        builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
        return builder;
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

            ) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("Walk"));
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
            this.remove(ProtogenEntity.RemovalReason.KILLED);
            this.dropExperience(this);
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


     
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHOOT, false);
        builder.define(ANIMATION, "undefined");
        builder.define(TEXTURE, "protogenblue");
        builder.define(SKIN, "");
        builder.define(VARIANT, 0);
        builder.define(OWNER, Optional.empty());
    }
    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public ProtogenVariant getVariant() {
        return ProtogenVariant.byId(this.getTypeVariant() & 255);
    }

    @Override
    public void setCosmeticVariant(int variantId) {
        this.entityData.set(VARIANT, variantId & 255);
    }

    private void setVariant(ProtogenVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getTypeVariant());
        pCompound.putString(SKIN_TAG, this.getSkin());
        this.getOwnerUUID().ifPresent(owner -> pCompound.putUUID("Owner", owner));
        if (this.workStation != null) pCompound.putLong("WorkStation", this.workStation.asLong());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(VARIANT, pCompound.getInt("Variant"));
        this.setSkin(SpeciesSkins.knownOrRandom(Species.PROTOGEN, pCompound.getString(SKIN_TAG), this.random));
        this.entityData.set(OWNER, pCompound.hasUUID("Owner") ? Optional.of(pCompound.getUUID("Owner")) : Optional.empty());
        this.workStation = pCompound.contains("WorkStation") ? BlockPos.of(pCompound.getLong("WorkStation")) : null;
        this.applyStationLeash();
        if (this.isTame()) this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    public Optional<UUID> getOwnerUUID() {
        return this.entityData.get(OWNER);
    }

    public boolean isTame() {
        return this.getOwnerUUID().isPresent();
    }

    public boolean isOwnedBy(Player player) {
        return this.getOwnerUUID().map(player.getUUID()::equals).orElse(false);
    }

    public void tameBy(Player player) {
        this.entityData.set(OWNER, Optional.of(player.getUUID()));
        this.setTarget(null);
        this.setPersistenceRequired();
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Nullable
    public BlockPos getWorkStation() {
        return this.workStation;
    }

    public void setWorkStation(@Nullable BlockPos pos) {
        this.workStation = pos == null ? null : pos.immutable();
        if (pos == null) this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        this.applyStationLeash();
    }

    private void applyStationLeash() {
        if (this.workStation == null) {
            this.clearRestriction();
        } else {
            this.restrictTo(this.workStation, ProtogenWorkStationBlockEntity.WORK_RADIUS);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide() || this.workStation == null) return;
        LivingEntity target = this.getTarget();
        double leash = ProtogenWorkStationBlockEntity.WORK_RADIUS + 8;
        if (target == null) return;
        Vec3 station = Vec3.atCenterOf(this.workStation);
        double dx = target.getX() - station.x;
        double dz = target.getZ() - station.z;
        if (dx * dx + dz * dz > leash * leash) {
            this.setTarget(null);
        }
    }

    public void leaveWorkStation() {
        if (this.workStation != null && this.level().getBlockEntity(this.workStation) instanceof ProtogenWorkStationBlockEntity station) {
            station.forgetProtogen(this.getUUID());
        }
        this.setWorkStation(null);
    }

    @Nullable
    public ProtogenWorkStationBlockEntity resolveWorkStation() {
        if (this.workStation == null || !this.level().isLoaded(this.workStation)) return null;
        if (this.level().getBlockEntity(this.workStation) instanceof ProtogenWorkStationBlockEntity station && station.isRegistered(this)) {
            return station;
        }
        this.setWorkStation(null);
        return null;
    }

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide()) this.leaveWorkStation();
        super.die(source);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.isTame() && super.removeWhenFarAway(distanceToClosestPlayer);
    }

    @Override
    public boolean shouldDespawnInPeaceful() {
        return !this.isTame();
    }

    @Override
    public boolean isPreventingPlayerRest(Player player) {
        return !this.isTame();
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return !this.isTame() && super.canBeSeenAsEnemy();
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (target instanceof Player player && this.isOwnedBy(player)) return false;
        return super.canAttack(target);
    }

    @Override
    public Species skinSpecies() {
        return Species.PROTOGEN;
    }

    @Override
    public String getSkin() {
        return this.entityData.get(SKIN);
    }

    public void setSkin(String skin) {
        this.entityData.set(SKIN, skin);
    }

    @SuppressWarnings("deprecation")

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType,
                                        @Nullable SpawnGroupData pSpawnGroupData) {
        ProtogenVariant variant = Util.getRandom(ProtogenVariant.values(), this.random);
        this.setVariant(variant);
        this.setSkin(SpeciesSkins.random(Species.PROTOGEN, this.random));
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }
}