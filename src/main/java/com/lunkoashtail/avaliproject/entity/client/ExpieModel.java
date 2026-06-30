package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import net.minecraft.util.Mth;

public class ExpieModel extends GeoModel<ExpieEntity> {

    @Override
    public ResourceLocation getAnimationResource(ExpieEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/expie.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ExpieEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/expie.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ExpieEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/expie/expie.png");
    }

    @Override
    public void setCustomAnimations(ExpieEntity animatable, long instanceId, AnimationState<ExpieEntity> animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
