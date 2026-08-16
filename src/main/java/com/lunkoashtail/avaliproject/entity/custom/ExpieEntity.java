package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.creativetab.GalaxyCategory;
import com.lunkoashtail.avaliproject.entity.ExpieContextLines;
import com.lunkoashtail.avaliproject.entity.ExpieMoodData;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.ai.ExpieDialogueController;
import com.lunkoashtail.avaliproject.entity.ai.ExpieFaceInteractorGoal;
import com.lunkoashtail.avaliproject.entity.ai.ExpieFollowPlayerGoal;
import com.lunkoashtail.avaliproject.entity.ai.ExpieSleepCurlGoal;
import com.lunkoashtail.avaliproject.entity.client.ExpieVariant;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.client.ClientPayloadHandlers;
import com.lunkoashtail.avaliproject.sound.ModSounds;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class ExpieEntity extends Animal implements GeoEntity, Merchant {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ExpieDialogueController dialogue = new ExpieDialogueController(this);

    public ExpieDialogueController getDialogue() { return this.dialogue; }
    public float getMoodValue() { return this.getData(ModAttachments.EXPIE_MOOD).get(); }

    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ExpieEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> ANIMATION =
            SynchedEntityData.defineId(ExpieEntity.class, EntityDataSerializers.STRING);
    public String animationprocedure = "empty";
    private String prevAnim = "empty";

    @Nullable
    private UUID interactingPlayer;
    private long lastInteractionTick;

    @Nullable
    public UUID getInteractingPlayer() { return interactingPlayer; }
    public void setInteractingPlayer(@Nullable UUID playerId) { this.interactingPlayer = playerId; }
    public long getLastInteractionTick() { return lastInteractionTick; }
    public void touchInteraction() { this.lastInteractionTick = this.level().getGameTime(); }

    private static final EntityDataAccessor<Optional<UUID>> CLINGY_TARGET =
            SynchedEntityData.defineId(ExpieEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    @Nullable
    public UUID getClingyTarget() { return this.entityData.get(CLINGY_TARGET).orElse(null); }
    public void setClingyTarget(@Nullable UUID playerId) { this.entityData.set(CLINGY_TARGET, Optional.ofNullable(playerId)); }

    private long lastHugTick;

    public long getLastHugTick() { return lastHugTick; }
    public void setLastHugTick(long tick) { this.lastHugTick = tick; }

    public ExpieEntity(EntityType<ExpieEntity> type, Level level) {
        super(type, level);
        xpReward = 5;
        setNoAi(false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new ExpieFaceInteractorGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ExpieSleepCurlGoal(this));
        this.goalSelector.addGoal(2, new ExpieFollowPlayerGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.EXPIE_CALL.get();
    }

    @Override
    protected SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource damageSource) {
        return ModSounds.EXPIE_HURT.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return super.getAmbientSoundInterval() * 10;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.interactingPlayer != null || this.isPassenger()) {
            this.getNavigation().stop();
            Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(0, motion.y, 0);
        }
        if (this.isPassenger() && !(this.getVehicle() instanceof Player rider && rider.isAlive() && rider.level() == this.level())) {
            this.stopRiding();
        }
        this.tickMood();
        this.dialogue.tick();
    }


    private static final int MOOD_TICK_INTERVAL = 100;
    private static final float PROXIMITY_MOOD_GAIN = 0.4f;
    private static final float ISOLATION_MOOD_LOSS = 0.6f;
    private static final float HOLD_PLUSH_MOOD_GAIN = 0.5f;
    private static final float LONG_NO_HUG_MOOD_LOSS = 0.2f;
    private static final long NO_HUG_GRACE_TICKS = 12000L;
    private static final double PROXIMITY_RANGE = 12.0;
    private static final float DAMAGE_MOOD_LOSS_PER_POINT = 0.75f;
    private static final float HUG_MOOD_GAIN = 8f;
    private static final float PLUSH_GIFT_MOOD_GAIN = 10f;

    private void tickMood() {
        if (this.level().isClientSide()) return;
        if (this.level().getGameTime() % MOOD_TICK_INTERVAL != 0) return;

        ExpieMoodData mood = this.getData(ModAttachments.EXPIE_MOOD);

        if (!this.getHeldPlush().isEmpty()) {
            mood.add(HOLD_PLUSH_MOOD_GAIN);
        }

        UUID clingyTarget = this.getClingyTarget();
        boolean bondedNearby = clingyTarget != null
                && this.level().getPlayerByUUID(clingyTarget) instanceof Player bonded
                && this.distanceToSqr(bonded) <= PROXIMITY_RANGE * PROXIMITY_RANGE;
        mood.add(bondedNearby ? PROXIMITY_MOOD_GAIN : -ISOLATION_MOOD_LOSS);

        if (this.lastHugTick != 0 && this.level().getGameTime() - this.lastHugTick > NO_HUG_GRACE_TICKS) {
            mood.add(-LONG_NO_HUG_MOOD_LOSS);
        }

        if (mood.isAtRockBottom()) {
            this.dieOfDespair();
        }
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float amount) {
        super.actuallyHurt(damageSource, amount);
        if (!this.level().isClientSide() && this.isAlive()) {
            this.getData(ModAttachments.EXPIE_MOOD).add(-amount * DAMAGE_MOOD_LOSS_PER_POINT);
        }
    }

    private void dieOfDespair() {
        UUID clingyTarget = this.getClingyTarget();
        Player notify = clingyTarget != null ? this.level().getPlayerByUUID(clingyTarget) : null;
        if (notify == null) {
            notify = this.level().getNearestPlayer(this, 32.0);
        }
        if (notify instanceof ServerPlayer serverPlayer) {
            serverPlayer.displayClientMessage(Component.literal(
                    this.getDisplayName().getString() + " couldn't bear it anymore. It's gone."), false);
        }
        this.discard();
    }


    public boolean isComfortClinging() {
        return this.isPassenger() && this.getVehicle() instanceof Player;
    }

    private boolean toggleComfortCling(Player player) {
        if (this.isComfortClinging()) {
            this.stopRiding();
            return true;
        }
        if (this.isPassenger() || player.isPassenger()) return false;
        return this.startRiding(player, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.STEP_HEIGHT, 0.6);
    }


    private static final int CAVE_MAX_Y = 40;

    public static void init(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.EXPIE.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, level, reason, pos, random) ->
                        pos.getY() < CAVE_MAX_Y
                                && !level.canSeeSky(pos)
                                && Animal.checkAnimalSpawnRules(entityType, level, reason, pos, random),
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }


    private static final EntityDataAccessor<Boolean> SLEEPING_NEAR_PLAYER =
            SynchedEntityData.defineId(ExpieEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(ANIMATION, "undefined");
        builder.define(SLEEPING_NEAR_PLAYER, false);
        builder.define(HELD_PLUSH, ItemStack.EMPTY);
        builder.define(CLINGY_TARGET, Optional.empty());
    }

    private int getTypeVariant() { return this.entityData.get(VARIANT); }
    public ExpieVariant getVariant() { return ExpieVariant.byId(this.getTypeVariant() & 255); }
    private void setVariant(ExpieVariant variant) { this.entityData.set(VARIANT, variant.getId() & 255); }

    public boolean isSleepingNearPlayer() { return this.entityData.get(SLEEPING_NEAR_PLAYER); }
    public void setSleepingNearPlayer(boolean value) { this.entityData.set(SLEEPING_NEAR_PLAYER, value); }


    private static final EntityDataAccessor<ItemStack> HELD_PLUSH =
            SynchedEntityData.defineId(ExpieEntity.class, EntityDataSerializers.ITEM_STACK);

    public ItemStack getHeldPlush() { return this.entityData.get(HELD_PLUSH); }
    public void setHeldPlush(ItemStack stack) { this.entityData.set(HELD_PLUSH, stack); }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getTypeVariant());
        UUID clingyTarget = this.getClingyTarget();
        if (clingyTarget != null) compound.putUUID("ClingyTarget", clingyTarget);
        compound.putLong("LastHugTick", this.lastHugTick);
        compound.put("HeldPlush", this.getHeldPlush().saveOptional(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Variant")) this.entityData.set(VARIANT, compound.getInt("Variant"));
        if (compound.hasUUID("ClingyTarget")) this.setClingyTarget(compound.getUUID("ClingyTarget"));
        if (compound.contains("LastHugTick")) this.lastHugTick = compound.getLong("LastHugTick");
        if (compound.contains("HeldPlush")) {
            this.setHeldPlush(ItemStack.parseOptional(this.registryAccess(), compound.getCompound("HeldPlush")));
        }
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        ItemStack held = this.getHeldPlush();
        if (!held.isEmpty()) {
            this.spawnAtLocation(held);
            this.setHeldPlush(ItemStack.EMPTY);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType,
                                         @Nullable SpawnGroupData spawnGroupData) {
        this.setVariant(Util.getRandom(ExpieVariant.values(), this.random));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }


    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof SpawnEggItem) {
            return super.mobInteract(player, hand);
        }
        if (stack.isEmpty() && !this.getHeldPlush().isEmpty()) {
            if (!this.level().isClientSide()) {
                ItemStack given = this.getHeldPlush();
                this.setHeldPlush(ItemStack.EMPTY);
                if (!player.getInventory().add(given)) {
                    player.drop(given, false);
                }
                this.playSound(ModSounds.EXPIE_INTERACT.get(), 1.0f, 0.9f);
            }
            return InteractionResult.SUCCESS;
        }
        if (!stack.isEmpty() && PLUSHIE_PRICE.contains(stack.getItem()) && this.getHeldPlush().isEmpty()) {
            if (!this.level().isClientSide()) {
                this.setHeldPlush(stack.copyWithCount(1));
                stack.shrink(1);
                this.playSound(ModSounds.EXPIE_INTERACT.get(), 1.0f, 1.1f);
                this.getData(ModAttachments.EXPIE_MOOD).add(PLUSH_GIFT_MOOD_GAIN);
                if (player instanceof ServerPlayer serverPlayer) {
                    String line = ExpieContextLines.PLUSH_GIFT_LINES.get(
                            this.random.nextInt(ExpieContextLines.PLUSH_GIFT_LINES.size()));
                    serverPlayer.displayClientMessage(Component.literal(this.getDisplayName().getString() + " " + line), false);
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (player.isShiftKeyDown() && stack.isEmpty() && player.getUUID().equals(this.getClingyTarget())) {
            if (!this.level().isClientSide() && this.toggleComfortCling(player)) {
                return InteractionResult.SUCCESS;
            }
        }
        if (this.level().isClientSide()) {
            ClientPayloadHandlers.openExpieInteractionScreen(this.getId());
        } else if (player instanceof ServerPlayer serverPlayer) {
            this.setInteractingPlayer(serverPlayer.getUUID());
            this.touchInteraction();
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide());
    }

    public void playHugFeedback() {
        this.setAnimation("animation.Expie.hug");
        this.playSound(ModSounds.EXPIE_INTERACT.get(), 1.0f, 1.2f);
        if (!this.level().isClientSide()) {
            this.getData(ModAttachments.EXPIE_MOOD).add(HUG_MOOD_GAIN);
        }
    }

    public String getSyncedAnimation() { return this.entityData.get(ANIMATION); }
    public void setAnimation(String animation) { this.entityData.set(ANIMATION, animation); }


    private static final Set<Item> PLUSHIE_PRICE = Set.of(
            ModItems.EXPIE_PLUSH.get(), ModItems.AKITU_PLUSHIE.get(), ModItems.AMS_PLUSHIE.get(), ModItems.BOT_PLUSHIE.get(),
            ModItems.BROWN_BUNNY_PLUSHIE.get(), ModItems.BUNNY_PLUSHIE.get(), ModItems.DAS_PLUSHIE.get(), ModItems.FRIEND_PLUSH.get(),
            ModItems.GORT_PLUSHIE.get(), ModItems.JIMMY_PLUSHIE.get(), ModItems.MOFFEE_PLUSHIE.get(), ModItems.ORANGE_PLUSHIE.get(),
            ModItems.PENTER_PLUSHIE.get(), ModItems.RED_FOX_PLUSHIE.get(), ModItems.ROBOT_PLUSHIE.get(), ModItems.SHARK_PLUSHIE.get(),
            ModItems.TACO_PLUSHIE.get(), ModItems.WEH_PLUSHIE.get(), ModItems.WICK_PLUSHIE.get());

    private static final Set<Item> CHEAP_PRICE = Set.of(
            ModItems.CHUNK_OF_PLASTIC.get(), ModItems.SCRAP_METAL.get(), ModItems.SCRAP_CUBE.get(), ModItems.SCRAP_PANEL.get(),
            ModItems.ROPE.get(), ModItems.FOLIAGE.get(), ModItems.TRASH_BAG.get(), ModItems.TEMPORARY_BOTTLE.get(),
            ModItems.APPLE_JUICE.get(), ModItems.ALCOHOL.get(), ModItems.GEOFRUIT.get(), ModItems.NUMBERRY.get(),
            ModItems.GLOWPLANT_FRUIT.get(), ModItems.RIPPED_DRESSING.get());

    private static final Set<Item> MID_PRICE = Set.of(
            ModItems.AUTO_INJECTOR.get(), ModItems.EMPTY_BLOOD_BAG.get(), ModItems.BLOOD_BAG.get(), ModItems.CHEST_DRAIN.get(),
            ModItems.LOCKPICKING_KIT.get(), ModItems.SYRINGE.get(), ModItems.BRUISE_KIT.get(), ModItems.MED_KIT.get(),
            ModItems.MINDWIPE.get(), ModItems.NALOXONE.get(), ModItems.SODIUM_NITROPRUSSIDE.get(), ModItems.STREPTOKINASE.get(),
            ModItems.ANTISERUM.get());

    private static final Set<Item> HIGH_PRICE = Set.of(
            ModItems.FENTANYL.get(), ModItems.HEROIN.get(), ModItems.MORPHINE.get(), ModItems.OPIUM.get(),
            ModItems.SALINE.get(), ModItems.MANUAL_DEFIBRILLATOR.get());

    private static int priceFor(Item item) {
        if (PLUSHIE_PRICE.contains(item)) return 3;
        if (HIGH_PRICE.contains(item)) return 8;
        if (MID_PRICE.contains(item)) return 4;
        if (CHEAP_PRICE.contains(item)) return 1;
        return 2;
    }

    @Nullable
    private Player tradingPlayer;
    @Nullable
    private MerchantOffers offers;

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = buildOffers();
        }
        return this.offers;
    }

    private static final int MAX_OFFERS = 6;

    private MerchantOffers buildOffers() {
        MerchantOffers offers = new MerchantOffers();
        Item spawnEgg = ModItems.EXPIE_SPAWN_EGG.get();
        Item crate = ModBlocks.CONTAINER_CRATE.get().asItem();

        List<Item> pool = new ArrayList<>();
        BuiltInRegistries.ITEM.getTag(GalaxyCategory.EXPIE.tag()).ifPresent(holders -> {
            for (var holder : holders) {
                Item item = holder.value();
                if (item == spawnEgg || item == crate) continue;
                pool.add(item);
            }
        });
        Util.shuffle(pool, this.random);

        for (Item item : pool.subList(0, Math.min(MAX_OFFERS, pool.size()))) {
            offers.add(new MerchantOffer(new ItemCost(Items.EMERALD, priceFor(item)),
                    new ItemStack(item, 1), 999, 1, 0.05f));
        }
        return offers;
    }

    @Override
    public void overrideOffers(MerchantOffers offers) {
        this.offers = offers;
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
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

    

    private PlayState movementPredicate(AnimationState event) {
        if (!this.animationprocedure.equals("empty")) {
            return PlayState.STOP;
        }
        if (this.isComfortClinging() || this.isSleepingNearPlayer()) {
            return PlayState.STOP;
        }

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

    private PlayState procedurePredicate(AnimationState event) {
        if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED
                || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
            if (!this.animationprocedure.equals(prevAnim)) event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
            }
        } else if (animationprocedure.equals("empty")) {
            return PlayState.STOP;
        }
        prevAnim = this.animationprocedure;
        return PlayState.CONTINUE;
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
