package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.SporeEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class SporeModel extends GeoModel<SporeEntity> {
    @Override
    public ResourceLocation getAnimationResource(SporeEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/spore.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(SporeEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/spore.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SporeEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/spore/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(SporeEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
