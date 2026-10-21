package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesSkins;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class WickerbeastEntity extends GeoSpeciesCreatureEntity implements SkinnedSpeciesEntity {
    private static final Animations ANIMATIONS = Animations.biped("animation.Wickerbeast.");
    private static final String SKIN_TAG = "Skin";
    private static final EntityDataAccessor<String> SKIN =
            SynchedEntityData.defineId(WickerbeastEntity.class, EntityDataSerializers.STRING);

    public WickerbeastEntity(EntityType<? extends WickerbeastEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SKIN, "");
    }

    @Override
    public Species skinSpecies() {
        return Species.WICKERBEAST;
    }

    @Override
    public String getSkin() {
        return this.entityData.get(SKIN);
    }

    public void setSkin(String skin) {
        this.entityData.set(SKIN, skin);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnGroupData) {
        setSkin(SpeciesSkins.random(Species.WICKERBEAST, this.random));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString(SKIN_TAG, getSkin());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setSkin(SpeciesSkins.knownOrRandom(Species.WICKERBEAST, tag.getString(SKIN_TAG), this.random));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.ARMOR, 4.0)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    protected void registerCombatGoals() {
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true));
    }

    @Override
    protected boolean isTameItem(ItemStack stack) {
        return stack.is(ModItems.CRYSTALITHIUM_CRYSTAL.get());
    }

    @Override
    public String getTextureName() {
        return "wickerbeast";
    }

    @Override
    protected Animations animations() {
        return ANIMATIONS;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        if (isBaby()) return;
        spawnAtLocation(new ItemStack(ModItems.MANE_TUFT.get(), 1 + this.random.nextInt(2)));
        if (this.random.nextFloat() < 0.35F) spawnAtLocation(new ItemStack(ModItems.CLAW_SHARD.get()));
        if (this.random.nextFloat() < 0.15F) spawnAtLocation(new ItemStack(ModItems.WICKER_HORN_FRAGMENT.get()));
    }
}
