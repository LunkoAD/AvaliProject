package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
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
import software.bernie.geckolib.animation.RawAnimation;

public class NorthernFelkinEntity extends RideableGeoSpeciesCreatureEntity {
    private static final Animations ANIMATIONS = new Animations(
            RawAnimation.begin().thenLoop("animation.NFelkin.idle"),
            RawAnimation.begin().thenLoop("animation.NFelkin.walk"),
            null, null, null, null, null, null);
    private static final RawAnimation BITE = RawAnimation.begin().thenPlay("animation.NFelkin.bite");
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(NorthernFelkinEntity.class, EntityDataSerializers.INT);

    public NorthernFelkinEntity(EntityType<? extends NorthernFelkinEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 36.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.ARMOR, 3.0)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.STEP_HEIGHT, 1.1)
                .add(Attributes.SAFE_FALL_DISTANCE, 6.0);
    }

    @Override
    protected void registerCombatGoals() {
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true));
    }

    @Override
    protected boolean isTameItem(ItemStack stack) {
        return stack.is(ModTags.Items.FELKIN_FOOD);
    }

    @Override
    public String getTextureName() {
        return getVariant().textureName();
    }

    @Override
    protected Animations animations() {
        return ANIMATIONS;
    }

    @Override
    protected RawAnimation attackAnimation() {
        return BITE;
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
        if (child instanceof NorthernFelkinEntity felkin) {
            felkin.setVariant(Util.getRandom(Variant.values(), this.random));
        }
        return child;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        if (!isBaby()) {
            spawnAtLocation(new ItemStack(ModItems.FELKIN_HIDE.get(), 2 + this.random.nextInt(2)));
        }
    }

    public enum Variant {
        AURORA("northern_felkin_aurora"),
        GRYM("northern_felkin_grym");

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
