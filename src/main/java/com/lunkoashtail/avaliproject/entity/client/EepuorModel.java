package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.EepuorEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class EepuorModel extends GeoModel<EepuorEntity> {
    @Override
    public ResourceLocation getAnimationResource(EepuorEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/eepuor.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(EepuorEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/eepuor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EepuorEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/eepuor/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(EepuorEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}
