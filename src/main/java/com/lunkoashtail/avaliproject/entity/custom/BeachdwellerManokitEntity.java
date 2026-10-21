package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class BeachdwellerManokitEntity extends AbstractManokitEntity {
    public BeachdwellerManokitEntity(EntityType<? extends BeachdwellerManokitEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public ManokitVariant getVariant() {
        return ManokitVariant.BEACHDWELLER;
    }
}
