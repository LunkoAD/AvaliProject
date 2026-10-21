package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FelkinEntity extends RideableGeoSpeciesCreatureEntity {
    public FelkinEntity(EntityType<? extends FelkinEntity> type, Level level) {
        super(type, level);
        this.xpReward = 3;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 26.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.STEP_HEIGHT, 1.1)
                .add(Attributes.SAFE_FALL_DISTANCE, 6.0);
    }

    @Override
    protected boolean isTameItem(ItemStack stack) {
        return stack.is(ModTags.Items.FELKIN_FOOD);
    }

    @Override
    public String getTextureName() {
        return "felkin";
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        if (!isBaby()) {
            spawnAtLocation(new ItemStack(ModItems.FELKIN_HIDE.get(), 1 + this.random.nextInt(2)));
        }
    }
}
