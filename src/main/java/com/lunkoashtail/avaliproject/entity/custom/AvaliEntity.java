package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.client.ClientPayloadHandlers;
import com.lunkoashtail.avaliproject.diplomacy.DiplomacyData;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.ai.FaceInteractorGoal;
import com.lunkoashtail.avaliproject.entity.ai.ProposeRecruitGoal;
import com.lunkoashtail.avaliproject.entity.client.AvaliVariant;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.network.AvaliSocializeInteractionPayload;
import com.lunkoashtail.avaliproject.network.AvaliTrustSyncPayload;
import com.lunkoashtail.avaliproject.pack.AvaliTrustMemory;
import com.lunkoashtail.avaliproject.sound.ModSounds;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.DifficultyInstance;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import java.util.UUID;

public class AvaliEntity extends TamableAnimal implements GeoEntity, Merchant {
    private static final int BASE_RECRUIT_COST = 50;
    private static final int MAINHAND_SLOT = 0, OFFHAND_SLOT = 1, HEAD_SLOT = 2, CHEST_SLOT = 3, LEGS_SLOT = 4, FEET_SLOT = 5;

    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(AvaliEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> MALE =
            SynchedEntityData.defineId(AvaliEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(AvaliEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(AvaliEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(AvaliEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure = "empty";

    @Nullable
    private SimpleContainer equipmentContainer;

    @Nullable
    private UUID interactingPlayer;

    @Nullable
    public UUID getInteractingPlayer() { return interactingPlayer; }
    public void setInteractingPlayer(@Nullable UUID playerId) { this.interactingPlayer = playerId; }

    private long lastInteractionTick;
    public long getLastInteractionTick() { return lastInteractionTick; }

    public void touchInteraction() {
        this.lastInteractionTick = this.level().getGameTime();
    }

    public AvaliEntity(EntityType<AvaliEntity> type, Level world) {
        super(type, world);
        xpReward = 2;
        setNoAi(false);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHOOT, false);
        builder.define(ANIMATION, "undefined");
        builder.define(TEXTURE, "avalipenguin");
        builder.define(VARIANT, 0);
        builder.define(MALE, true);
    }

    public boolean isMale() { return this.entityData.get(MALE); }
    public void setMale(boolean male) { this.entityData.set(MALE, male); }

    public void setTexture(String texture) { this.entityData.set(TEXTURE, texture); }
    public String getTexture() { return this.entityData.get(TEXTURE); }

    private int getTypeVariant() { return this.entityData.get(VARIANT); }
    public AvaliVariant getVariant() { return AvaliVariant.byId(this.getTypeVariant() & 255); }
    private void setVariant(AvaliVariant variant) { this.entityData.set(VARIANT, variant.getId() & 255); }


    public AvaliTrustMemory getTrustMemory() {
        return this.getData(ModAttachments.AVALI_TRUST);
    }

    public int getScaledRecruitCost(DiplomacyData diplomacy) {
        return Math.max(1, (int) Math.round(BASE_RECRUIT_COST * diplomacy.rank().recruitCostMultiplier()));
    }

    public void recruit(Player player) {
        this.tame(player);
        this.setPersistenceRequired();
        this.level().broadcastEntityEvent(this, (byte) 7);
    }

    public void playHugOrSocializeFeedback(int event) {
        this.level().broadcastEntityEvent(this, (byte) 7);

        switch (event){
            case AvaliSocializeInteractionPayload.ACTION_HUG, AvaliSocializeInteractionPayload.ACTION_FLIRT,
                 AvaliSocializeInteractionPayload.ACTION_JOKE, AvaliSocializeInteractionPayload.ACTION_PLAY ->{
                this.playSound(ModSounds.AVALI_HAPPY.get(), 1.0f, 1.0f);
            }
            case AvaliSocializeInteractionPayload.ACTION_BE_RUDE->{
                    this.playSound(ModSounds.AVALI_SURPISE.get(), 1.0f, 1.0f);
            }
            default -> {
                this.playSound(ModSounds.AVALI_TALK.get(), 1.0f, 1.0f);
            }
        }
    }


    @Nullable
    private Player tradingPlayer;
    @Nullable
    private MerchantOffers offers;

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Override
    @Nullable
    public Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = buildFixedOffers();
        }
        return this.offers;
    }

    private static MerchantOffers buildFixedOffers() {
        MerchantOffers offers = new MerchantOffers();
        offers.add(new MerchantOffer(new ItemCost(ModItems.LUME_BIT.get(), 8),
                new ItemStack(ModItems.AEROGEL_BATTERY.get(), 1), 999, 1, 0.05f));
        offers.add(new MerchantOffer(new ItemCost(ModItems.LUME_BIT.get(), 16),
                new ItemStack(ModItems.AEROGEL.get(), 4), 999, 1, 0.05f));
        offers.add(new MerchantOffer(new ItemCost(ModItems.LUME_BIT.get(), 200),
                new ItemStack(ModItems.SYNC_CRYSTAL.get(), 1), 999, 5, 0.05f));

        offers.add(new MerchantOffer(new ItemCost(ModItems.LUME_BIT.get(), 3),
                new ItemStack(ModItems.KIRIKIRI_PIE.get(), 1), 999, 1, 0.05f));
        offers.add(new MerchantOffer(new ItemCost(ModItems.LUME_BIT.get(), 3),
                new ItemStack(ModItems.AVALI_MUFFIN.get(), 2), 999, 1, 0.05f));
        offers.add(new MerchantOffer(new ItemCost(ModItems.LUME_BIT.get(), 4),
                new ItemStack(ModItems.AVALON_TACO.get(), 1), 999, 1, 0.05f));
        offers.add(new MerchantOffer(new ItemCost(ModItems.LUME_BIT.get(), 4),
                new ItemStack(ModItems.PIRUZA.get(), 1), 999, 1, 0.05f));
        offers.add(new MerchantOffer(new ItemCost(ModItems.LUME_BIT.get(), 5),
                new ItemStack(ModItems.AVALI_BBQ.get(), 1), 999, 1, 0.05f));
        offers.add(new MerchantOffer(new ItemCost(ModItems.LUME_BIT.get(), 3),
                new ItemStack(ModItems.KIRI_CIDER.get(), 1), 999, 1, 0.05f));
        return offers;
    }

    @Override
    public void overrideOffers(MerchantOffers offers) {
        this.offers = offers;
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
        this.touchInteraction();
        if (this.tradingPlayer instanceof ServerPlayer serverPlayer) {
            DiplomacyData diplomacy = serverPlayer.getData(ModAttachments.DIPLOMACY_DATA);
            serverPlayer.setData(ModAttachments.DIPLOMACY_DATA, diplomacy.addRenown(2));
        }
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int xp) {
    }

    @Override
    public boolean showProgressBar() {
        return true;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.villager.yes"));
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide();
    }


    public SimpleContainer getEquipmentContainer() {
        if (equipmentContainer == null) {
            equipmentContainer = new SimpleContainer(6);
        }
        equipmentContainer.setItem(MAINHAND_SLOT, this.getItemBySlot(EquipmentSlot.MAINHAND).copy());
        equipmentContainer.setItem(OFFHAND_SLOT, this.getItemBySlot(EquipmentSlot.OFFHAND).copy());
        equipmentContainer.setItem(HEAD_SLOT, this.getItemBySlot(EquipmentSlot.HEAD).copy());
        equipmentContainer.setItem(CHEST_SLOT, this.getItemBySlot(EquipmentSlot.CHEST).copy());
        equipmentContainer.setItem(LEGS_SLOT, this.getItemBySlot(EquipmentSlot.LEGS).copy());
        equipmentContainer.setItem(FEET_SLOT, this.getItemBySlot(EquipmentSlot.FEET).copy());
        return equipmentContainer;
    }

    public void syncEquipmentFromContainer() {
        if (equipmentContainer == null) return;
        this.setItemSlot(EquipmentSlot.MAINHAND, equipmentContainer.getItem(MAINHAND_SLOT));
        this.setItemSlot(EquipmentSlot.OFFHAND, equipmentContainer.getItem(OFFHAND_SLOT));
        this.setItemSlot(EquipmentSlot.HEAD, equipmentContainer.getItem(HEAD_SLOT));
        this.setItemSlot(EquipmentSlot.CHEST, equipmentContainer.getItem(CHEST_SLOT));
        this.setItemSlot(EquipmentSlot.LEGS, equipmentContainer.getItem(LEGS_SLOT));
        this.setItemSlot(EquipmentSlot.FEET, equipmentContainer.getItem(FEET_SLOT));
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FaceInteractorGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected boolean canPerformAttack(LivingEntity entity) {
                return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < (this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth()) && this.mob.getSensing().hasLineOfSight(entity);
            }
        });
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1) {
            @Override
            public boolean canUse() {
                return !AvaliEntity.this.isTame() && super.canUse();
            }
        });
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(6, new ProposeRecruitGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
    }

    protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource source, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(serverLevel, source, recentlyHitIn);
        ItemStack feathers = new ItemStack(Items.FEATHER);
        feathers.setCount((int)(serverLevel.random.nextFloat()*5f)); //random 0-4 feathers
        this.spawnAtLocation(feathers);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSounds.AVALI_IDLE.get();
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PARROT_STEP);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.parrot.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.parrot.death"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL)) return false;
        return super.hurt(source, amount);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
        compound.putInt("Variant", this.getTypeVariant());
        compound.putBoolean("Male", this.isMale());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture")) this.setTexture(compound.getString("Texture"));
        if (compound.contains("Variant")) this.entityData.set(VARIANT, compound.getInt("Variant"));
        if (compound.contains("Male")) this.setMale(compound.getBoolean("Male"));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType,
                                        @Nullable SpawnGroupData pSpawnGroupData) {
        AvaliVariant variant = Util.getRandom(AvaliVariant.values(), this.random);
        this.setVariant(variant);
        this.setMale(this.random.nextBoolean());
        this.setCustomName(Component.literal(AvaliNames.random(this.random)));
        this.setCustomNameVisible(true);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.refreshDimensions();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.interactingPlayer != null) {
            this.getNavigation().stop();
            net.minecraft.world.phys.Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(0, motion.y, 0);
        }
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose).scale(0.65f);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
        ItemStack itemstack = sourceentity.getItemInHand(hand);
        if (itemstack.getItem() instanceof SpawnEggItem) {
            return super.mobInteract(sourceentity, hand);
        }
        if (this.level().isClientSide()) {
            ClientPayloadHandlers.openAvaliInteractionScreen(
                    this.getId(), this.isTame(), this.isTame() && this.isOwnedBy(sourceentity));
        } else if (sourceentity instanceof ServerPlayer serverPlayer) {
            this.setInteractingPlayer(serverPlayer.getUUID());
            this.touchInteraction();
            AvaliTrustSyncPayload.sendTo(serverPlayer, this);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide());
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
        AvaliEntity retval = ModEntities.AVALI.get().create(serverWorld);
        if (retval != null)
            retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null);
        return retval;
    }

    public static void init(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.AVALI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && world.getRawBrightness(pos, 0) > 8), RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 15);
        builder = builder.add(Attributes.ARMOR, 2);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
        return builder;
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("Walk"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("Idle"));
        }
        return PlayState.STOP;
    }

    String prevAnim = "empty";

    private PlayState procedurePredicate(AnimationState event) {
        if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
            if (!this.animationprocedure.equals(prevAnim)) event.getController().forceAnimationReset();
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
            this.remove(AvaliEntity.RemovalReason.KILLED);
            this.dropExperience(this);
        }
    }

    public String getSyncedAnimation() { return this.entityData.get(ANIMATION); }
    public void setAnimation(String animation) { this.entityData.set(ANIMATION, animation); }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }
}
