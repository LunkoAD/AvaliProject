package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.GoatAgudnerEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class GoatAgudnerModel extends GeoModel<GoatAgudnerEntity> {
    @Override
    public ResourceLocation getAnimationResource(GoatAgudnerEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/goat_agudner.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(GoatAgudnerEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/goat_agudner.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GoatAgudnerEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/goat_agudner/goat_agudner.png");
    }

    @Override
    public void setCustomAnimations(GoatAgudnerEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
