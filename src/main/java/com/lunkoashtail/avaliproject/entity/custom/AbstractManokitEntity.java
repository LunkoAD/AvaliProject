package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesCosmetics;
import com.lunkoashtail.avaliproject.util.ModTags;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractManokitEntity extends SpeciesCreatureEntity {
    private static final float SHELL_DROP_CHANCE = 0.5F;
    private static final float PEARL_DROP_CHANCE = 0.2F;
    private static final String SKIN_TAG = "Skin";
    private static final EntityDataAccessor<String> SKIN =
            SynchedEntityData.defineId(AbstractManokitEntity.class, EntityDataSerializers.STRING);

    protected AbstractManokitEntity(EntityType<? extends AbstractManokitEntity> type, Level level) {
        super(type, level);
        this.xpReward = 3;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 18.0)
                .add(Attributes.MOVEMENT_SPEED, 0.27)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(NeoForgeMod.SWIM_SPEED, 1.6);
    }

    public abstract ManokitVariant getVariant();

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SKIN, "");
    }

    public String getSkin() {
        return this.entityData.get(SKIN);
    }

    public void setSkin(String skin) {
        this.entityData.set(SKIN, skin);
    }

    private String randomSkin() {
        List<SpeciesCosmetics.Entry> skins = SpeciesCosmetics.forModel(Species.MANOKIT, getVariant());
        return skins.isEmpty() ? "" : skins.get(this.random.nextInt(skins.size())).id();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnGroupData) {
        if (getSkin().isEmpty()) setSkin(randomSkin());
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        AgeableMob child = super.getBreedOffspring(level, otherParent);
        if (child instanceof AbstractManokitEntity manokit) {
            String otherSkin = otherParent instanceof AbstractManokitEntity other ? other.getSkin() : getSkin();
            manokit.setSkin(this.random.nextBoolean() ? getSkin() : otherSkin);
        }
        return child;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString(SKIN_TAG, getSkin());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        String skin = tag.getString(SKIN_TAG);
        boolean known = SpeciesCosmetics.find(Species.MANOKIT, getVariant(), skin).isPresent();
        setSkin(known ? skin : randomSkin());
    }

    @Override
    protected boolean isTameItem(ItemStack stack) {
        return stack.is(ModTags.Items.TROPICAL_FRUITS);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return type != NeoForgeMod.WATER_TYPE.value() && super.canDrownInFluidType(type);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        if (isBaby()) return;
        if (this.random.nextFloat() < SHELL_DROP_CHANCE) spawnAtLocation(new ItemStack(ModItems.SHELL.get()));
        if (this.random.nextFloat() < PEARL_DROP_CHANCE) spawnAtLocation(new ItemStack(ModItems.PEARL.get()));
    }
}
