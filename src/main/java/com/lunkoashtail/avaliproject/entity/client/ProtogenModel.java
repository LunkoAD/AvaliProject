package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.ProtogenEntity;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class ProtogenModel extends GeoModel<ProtogenEntity> {
    private final GeoHumanoidPoser poser = new GeoHumanoidPoser(false, false);

    @Override
    public ResourceLocation getAnimationResource(ProtogenEntity entity) {
        return ResourceLocation.parse("avaliproject:animations/protogen.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ProtogenEntity entity) {
        return ResourceLocation.parse("avaliproject:geo/protogen.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ProtogenEntity entity) {
        return ResourceLocation.parse("avaliproject:textures/entity/protogen/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(ProtogenEntity animatable, long instanceId, AnimationState<ProtogenEntity> animationState) {
        float lean = poser.apply(getAnimationProcessor(), animatable, animationState);
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD + lean);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
