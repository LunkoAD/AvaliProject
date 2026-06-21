package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.StalkerEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;

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
    public void setCustomAnimations(StalkerEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
