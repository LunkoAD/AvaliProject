package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.CaklerahEntity;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

public class CaklerahModel extends GeoModel<CaklerahEntity> {
    @Override
    public ResourceLocation getAnimationResource(CaklerahEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/caklerah.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(CaklerahEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/caklerah.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CaklerahEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/caklerah/" + entity.getTexture() + ".png");
    }

}
