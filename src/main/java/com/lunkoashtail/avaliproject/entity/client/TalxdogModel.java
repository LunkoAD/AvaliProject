package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.TalxdogEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class TalxdogModel extends GeoModel<TalxdogEntity> {
    @Override
    public ResourceLocation getAnimationResource(TalxdogEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/talxdog.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TalxdogEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/talxdog.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TalxdogEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/talxdog/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(TalxdogEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}
