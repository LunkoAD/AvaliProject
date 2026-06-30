package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.BosAgudnerEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class BosAgudnerModel extends GeoModel<BosAgudnerEntity> {
    @Override
    public ResourceLocation getAnimationResource(BosAgudnerEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/bos_agudner.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(BosAgudnerEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/bos_agudner.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BosAgudnerEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/bos_agudner/bos_agudner.png");
    }

    @Override
    public void setCustomAnimations(BosAgudnerEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
