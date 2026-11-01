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
    public void setCustomAnimations(SporeEntity animatable, long instanceId, AnimationState<SporeEntity> animationState) {
        GeoBone neck = getAnimationProcessor().getBone("neck");
        if (neck != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            GeoBoneOffsets.apply(neck, 0.0F, entityData.netHeadYaw() * Mth.DEG_TO_RAD, 0.0F);
        }
    }
}
