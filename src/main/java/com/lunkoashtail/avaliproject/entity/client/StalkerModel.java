package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.StalkerEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class StalkerModel extends GeoModel<StalkerEntity> {
    @Override
    public ResourceLocation getAnimationResource(StalkerEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/stalker.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(StalkerEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/stalker.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StalkerEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/stalker/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(StalkerEntity animatable, long instanceId, AnimationState<StalkerEntity> animationState) {
        GeoBone neck = getAnimationProcessor().getBone("neck");
        if (neck != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            GeoBoneOffsets.apply(neck, 0.0F, entityData.netHeadYaw() * Mth.DEG_TO_RAD, 0.0F);
        }
    }
}
