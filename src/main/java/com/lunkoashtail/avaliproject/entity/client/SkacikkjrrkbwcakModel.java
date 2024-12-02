package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.SkacikkjrrkbwcakEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class SkacikkjrrkbwcakModel extends GeoModel<SkacikkjrrkbwcakEntity> {
    @Override
    public ResourceLocation getAnimationResource(SkacikkjrrkbwcakEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/skacikkjrrkbwcak.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(SkacikkjrrkbwcakEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/skacikkjrrkbwcak.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SkacikkjrrkbwcakEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(SkacikkjrrkbwcakEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}
