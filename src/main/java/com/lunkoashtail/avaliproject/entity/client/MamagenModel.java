package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.MamagenEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class MamagenModel extends GeoModel<MamagenEntity> {
    @Override
    public ResourceLocation getAnimationResource(MamagenEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/mamagen.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MamagenEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/mamagen.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MamagenEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/mamagen/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(MamagenEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}
