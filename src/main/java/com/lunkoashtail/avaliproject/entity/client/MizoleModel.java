package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.MizoleEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class MizoleModel extends GeoModel<MizoleEntity> {
    @Override
    public ResourceLocation getAnimationResource(MizoleEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/mizole.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MizoleEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/mizole.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MizoleEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/mizole/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(MizoleEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}