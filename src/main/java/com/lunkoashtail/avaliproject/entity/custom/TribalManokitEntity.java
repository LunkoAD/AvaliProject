package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class TribalManokitEntity extends AbstractManokitEntity {
    public TribalManokitEntity(EntityType<? extends TribalManokitEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public ManokitVariant getVariant() {
        return ManokitVariant.TRIBAL;
    }
}
