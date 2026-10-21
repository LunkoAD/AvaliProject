package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animation.RawAnimation;

public class DutchAngelDragonEntity extends RideableGeoSpeciesCreatureEntity {
    private static final Animations GROUND_ANIMATIONS = new Animations(
            RawAnimation.begin().thenLoop("animation.dadragon.idle"),
            RawAnimation.begin().thenLoop("animation.dadragon.walk"),
            null, null, null, null, null, null);
    private static final Animations AIR_ANIMATIONS = new Animations(
            RawAnimation.begin().thenLoop("animation.dadragon.fly_idle"),
            RawAnimation.begin().thenLoop("animation.dadragon.fly"),
            null, null, null, null, null, null);

    public DutchAngelDragonEntity(EntityType<? extends DutchAngelDragonEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.26)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.STEP_HEIGHT, 1.1)
                .add(Attributes.GRAVITY, 0.04)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER, 0.0);
    }

    @Override
    protected boolean isTameItem(ItemStack stack) {
        return stack.is(ModTags.Items.DUTCH_ANGEL_DRAGON_FOOD);
    }

    @Override
    public String getTextureName() {
        return "dutch_angel_dragon_telephone";
    }

    @Override
    protected Animations animations() {
        boolean airborne = !onGround() && !isInWater() && !isPassenger() && getDeltaMovement().y < -0.08;
        return airborne ? AIR_ANIMATIONS : GROUND_ANIMATIONS;
    }
}
