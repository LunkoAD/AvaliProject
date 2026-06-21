package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.AvaliDroneEntity;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

public class AvaliDroneModel extends GeoModel<AvaliDroneEntity> {
    @Override
    public ResourceLocation getAnimationResource(AvaliDroneEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/avali_drone.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(AvaliDroneEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/avali_drone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AvaliDroneEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/avali_drone/" + entity.getTexture() + ".png");
    }

}