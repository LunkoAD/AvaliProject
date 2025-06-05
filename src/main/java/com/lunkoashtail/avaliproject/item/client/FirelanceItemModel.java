package com.lunkoashtail.avaliproject.item.client;

import com.lunkoashtail.avaliproject.item.custom.FirelanceItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FirelanceItemModel extends GeoModel<FirelanceItem> {
    @Override
    public ResourceLocation getAnimationResource(FirelanceItem animatable) {
        return ResourceLocation.parse("avaliproject:animations/firelance.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(FirelanceItem animatable) {
        return ResourceLocation.parse("avaliproject:geo/firelance.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FirelanceItem animatable) {
        return ResourceLocation.parse("avaliproject:textures/item/firelance.png");
    }
}