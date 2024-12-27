package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.FemaleNevreanEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class FemaleNevreanModel extends GeoModel<FemaleNevreanEntity> {
    @Override
    public ResourceLocation getAnimationResource(FemaleNevreanEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/female_nevrean.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(FemaleNevreanEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/female_nevrean.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FemaleNevreanEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/nevrean/female/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(FemaleNevreanEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}