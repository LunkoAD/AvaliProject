package com.lunkoashtail.avaliproject.avaligear.client;

import com.lunkoashtail.avaliproject.avaligear.AvaliGear;
import com.lunkoashtail.avaliproject.avaligear.ship.CatalinaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CatalinaModel extends GeoModel<CatalinaEntity> {
    private static final ResourceLocation MODEL = AvaliGear.id("geo/catalina.geo.json");
    private static final ResourceLocation ANIMATION = AvaliGear.id("animations/catalina.animation.json");
    private static final ResourceLocation TEXTURE = AvaliGear.id("textures/entity/catalina/catalina.png");

    @Override
    public ResourceLocation getModelResource(CatalinaEntity animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(CatalinaEntity animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(CatalinaEntity animatable) {
        return ANIMATION;
    }
}
