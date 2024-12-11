package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.PrimagenEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class PrimagenModel extends GeoModel<PrimagenEntity> {
    @Override
    public ResourceLocation getAnimationResource(PrimagenEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/primagen.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(PrimagenEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/primagen.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PrimagenEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(PrimagenEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}
