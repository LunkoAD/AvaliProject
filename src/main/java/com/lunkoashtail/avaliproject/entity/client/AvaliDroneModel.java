package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.AvaliDroneEntity;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class AvaliDroneModel extends GeoModel<AvaliDroneEntity> {
    @Override
    public ResourceLocation getAnimationResource(AvaliDroneEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/avali_drone.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(AvaliDroneEntity entity, GeoRenderer<AvaliDroneEntity> renderer) {
        return ResourceLocation.parse("avaliproject:geo/avali_drone.geo.json");
    }

    @Override
    public ResourceLocation getModelResource(AvaliDroneEntity animatable) {
        return null;
    }

    @Override
    public ResourceLocation getTextureResource(AvaliDroneEntity entity, GeoRenderer<AvaliDroneEntity> renderer) {
        return ResourceLocation.parse("avaliproject:textures/entity/avali_drone/" + entity.getTexture() + ".png");
    }

    @Override
    public ResourceLocation getTextureResource(AvaliDroneEntity animatable) {
        return null;
    }

}