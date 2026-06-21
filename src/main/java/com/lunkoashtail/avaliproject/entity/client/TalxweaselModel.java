package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.TalxweaselEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class TalxweaselModel extends GeoModel<TalxweaselEntity> {
    @Override
    public ResourceLocation getAnimationResource(TalxweaselEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/talxweasel.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TalxweaselEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/talxweasel.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TalxweaselEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/talxweasel/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(TalxweaselEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}
