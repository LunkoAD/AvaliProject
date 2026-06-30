package com.lunkoashtail.avaliproject.item.client;

import com.lunkoashtail.avaliproject.item.custom.AvaliSpearItem;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

public class AvaliSpearItemModel extends GeoModel<AvaliSpearItem> {
    @Override
    public ResourceLocation getAnimationResource(AvaliSpearItem animatable) {
        return ResourceLocation.parse("avaliproject:animations/avali_spear.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(AvaliSpearItem animatable) {
        return ResourceLocation.parse("avaliproject:geo/avali_spear.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AvaliSpearItem animatable) {
        return ResourceLocation.parse("avaliproject:textures/item/avali_spear.png");
    }
}
