package com.lunkoashtail.avaliproject.entity.custom;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
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

public class GreaterYingletEntity extends GeoSpeciesCreatureEntity {
    private static final Animations ANIMATIONS = Animations.biped("animation.GYinglet.");
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(GreaterYingletEntity.class, EntityDataSerializers.INT);

    public GreaterYingletEntity(EntityType<? extends GreaterYingletEntity> type, Level level) {
        super(type, level);
        this.xpReward = 2;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void registerCombatGoals() {
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.3, true));
    }

    @Override
    protected boolean isTameItem(ItemStack stack) {
        return stack.is(ItemTags.FISHES);
    }

    @Override
    protected Animations animations() {
        return ANIMATIONS;
    }

    @Override
    public String getTextureName() {
        return getVariant().textureName();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(VARIANT));
    }

    public void setVariant(Variant variant) {
        this.entityData.set(VARIANT, variant.ordinal());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getVariant().ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Variant")) setVariant(Variant.byId(compound.getInt("Variant")));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnGroupData) {
        setVariant(Util.getRandom(Variant.values(), this.random));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        AgeableMob child = super.getBreedOffspring(level, otherParent);
        if (child instanceof GreaterYingletEntity yinglet) {
            Variant inherited = otherParent instanceof GreaterYingletEntity other && this.random.nextBoolean()
                    ? other.getVariant() : getVariant();
            yinglet.setVariant(inherited);
        }
        return child;
    }

    public enum Variant {
        DUSTY("dusty_greater_yinglet"),
        WHEAT("wheat_greater_yinglet");

        private final String textureName;

        Variant(String textureName) {
            this.textureName = textureName;
        }

        public String textureName() {
            return textureName;
        }

        public static Variant byId(int id) {
            Variant[] values = values();
            return values[Math.floorMod(id, values.length)];
        }
    }
}
