package com.lunkoashtail.avaliproject.entity.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class RideableGeoSpeciesCreatureEntity extends GeoSpeciesCreatureEntity implements PlayerRideable {
    protected RideableGeoSpeciesCreatureEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    protected float riddenSpeedMultiplier() {
        return 1.45F;
    }

    @Override
    protected InteractionResult ownerInteract(Player player, InteractionHand hand, ItemStack stack) {
        boolean client = level().isClientSide();
        if (stack.isEmpty() && !isBaby() && !isVehicle()) {
            if (!client) {
                setOrderedToSit(false);
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(client);
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (getFirstPassenger() instanceof Player player && isTame() && isOwnedBy(player)) {
            return player;
        }
        return super.getControllingPassenger();
    }

    @Override
    protected void tickRidden(Player player, Vec3 travelVector) {
        super.tickRidden(player, travelVector);
        setRot(player.getYRot(), player.getXRot() * 0.5F);
        this.yRotO = this.yBodyRot = this.yHeadRot = getYRot();
    }

    @Override
    protected Vec3 getRiddenInput(Player player, Vec3 travelVector) {
        float strafe = player.xxa * 0.5F;
        float forward = player.zza;
        if (forward <= 0.0F) {
            forward *= 0.25F;
        }
        return new Vec3(strafe, 0.0, forward);
    }

    @Override
    protected float getRiddenSpeed(Player player) {
        return (float) getAttributeValue(Attributes.MOVEMENT_SPEED) * riddenSpeedMultiplier();
    }
}
