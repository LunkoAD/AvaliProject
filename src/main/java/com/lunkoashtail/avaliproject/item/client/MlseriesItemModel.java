package com.lunkoashtail.avaliproject.item.client;

import com.lunkoashtail.avaliproject.item.custom.MlseriesItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MlseriesItemModel extends GeoModel<MlseriesItem> {
    @Override
    public ResourceLocation getAnimationResource(MlseriesItem animatable) {
        return ResourceLocation.parse("avaliproject:animations/mlseries.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MlseriesItem animatable) {
        return ResourceLocation.parse("avaliproject:geo/mlseries.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MlseriesItem animatable) {
        return ResourceLocation.parse("avaliproject:textures/item/mlseries.png");
    }
}