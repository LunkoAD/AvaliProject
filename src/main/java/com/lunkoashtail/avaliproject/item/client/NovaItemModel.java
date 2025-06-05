package com.lunkoashtail.avaliproject.item.client;

import com.lunkoashtail.avaliproject.item.custom.NovaItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NovaItemModel extends GeoModel<NovaItem> {
    @Override
    public ResourceLocation getAnimationResource(NovaItem animatable) {
        return ResourceLocation.parse("avaliproject:animations/nova.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(NovaItem animatable) {
        return ResourceLocation.parse("avaliproject:geo/nova.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NovaItem animatable) {
        return ResourceLocation.parse("avaliproject:textures/item/nova.png");
    }
}