package com.lunkoashtail.avaliproject.item.client;

import com.lunkoashtail.avaliproject.item.custom.StormItem;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

public class StormItemModel extends GeoModel<StormItem> {
    @Override
    public ResourceLocation getAnimationResource(StormItem animatable) {
        return ResourceLocation.parse("avaliproject:animations/storm.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(StormItem animatable) {
        return ResourceLocation.parse("avaliproject:geo/storm.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StormItem animatable) {
        return ResourceLocation.parse("avaliproject:textures/item/stormsurge.png");
    }
}