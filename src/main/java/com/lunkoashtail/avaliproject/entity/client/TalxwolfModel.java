package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.TalxwolfEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class TalxwolfModel extends GeoModel<TalxwolfEntity> {
    @Override
    public ResourceLocation getAnimationResource(TalxwolfEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/talxwolf.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TalxwolfEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/talxwolf.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TalxwolfEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/talxwolf/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(TalxwolfEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}
