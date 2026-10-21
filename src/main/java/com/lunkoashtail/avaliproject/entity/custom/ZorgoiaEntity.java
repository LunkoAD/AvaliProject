package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animation.RawAnimation;

public class ZorgoiaEntity extends RideableGeoSpeciesCreatureEntity {
    private static final Animations ANIMATIONS = new Animations(
            RawAnimation.begin().thenLoop("animation.Zorgoia.Idle"),
            RawAnimation.begin().thenLoop("animation.Zorgoia.Walk"),
            null, null, null, null, null, null);
    private static final RawAnimation BITE = RawAnimation.begin().thenPlay("animation.Zorgoia.Bite");

    public ZorgoiaEntity(EntityType<? extends ZorgoiaEntity> type, Level level) {
        super(type, level);
        this.xpReward = 8;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 0.27)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.ARMOR, 4.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.STEP_HEIGHT, 1.5)
                .add(Attributes.SAFE_FALL_DISTANCE, 8.0);
    }

    @Override
    protected void registerCombatGoals() {
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true));
    }

    @Override
    protected boolean isTameItem(ItemStack stack) {
        return stack.is(ModTags.Items.ZORGOIA_FOOD);
    }

    @Override
    protected float riddenSpeedMultiplier() {
        return 1.6F;
    }

    @Override
    public String getTextureName() {
        return "zorgoia";
    }

    @Override
    protected Animations animations() {
        return ANIMATIONS;
    }

    @Override
    protected RawAnimation attackAnimation() {
        return BITE;
    }
}
