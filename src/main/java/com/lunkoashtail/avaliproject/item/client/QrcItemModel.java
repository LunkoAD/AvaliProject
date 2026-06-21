package com.lunkoashtail.avaliproject.item.client;

import com.lunkoashtail.avaliproject.item.custom.QrcItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class QrcItemModel extends GeoModel<QrcItem> {
    @Override
    public ResourceLocation getAnimationResource(QrcItem animatable) {
        return ResourceLocation.parse("avaliproject:animations/qrc.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(QrcItem animatable) {
        return ResourceLocation.parse("avaliproject:geo/qrc.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(QrcItem animatable) {
        return ResourceLocation.parse("avaliproject:textures/item/qrc.png");
    }
}