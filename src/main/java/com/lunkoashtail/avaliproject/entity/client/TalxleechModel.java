package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.TalxleechEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class TalxleechModel extends GeoModel<TalxleechEntity> {
    @Override
    public ResourceLocation getAnimationResource(TalxleechEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/talxleech.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TalxleechEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/talxleech.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TalxleechEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/talxleech/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(TalxleechEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}
