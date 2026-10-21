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
    private final GeoHumanoidPoser poser = new GeoHumanoidPoser(true, true);

    private static final float CLING_BODY_ROT_X   = -12f * Mth.DEG_TO_RAD;
    private static final float CLING_ARM_ROT_X    = 135f * Mth.DEG_TO_RAD;
    private static final float CLING_ARM_ROT_Z    = 20f * Mth.DEG_TO_RAD;
    private static final float CLING_THIGH_ROT_X  = 65f * Mth.DEG_TO_RAD;
    private static final float CLING_LEG_ROT_X    = -85f * Mth.DEG_TO_RAD;
    private static final float CLING_TAIL_ROT_X   = 55f * Mth.DEG_TO_RAD;
    private static final float RIDING_EXTRA_LEAN  = -18f * Mth.DEG_TO_RAD;

    private static final float HOLD_ARM_ROT_X = 60f * Mth.DEG_TO_RAD;
    private static final float HOLD_ARM_ROT_Z = 48f * Mth.DEG_TO_RAD;

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
        return switch (entity.getVariant()) {
            case WHITE -> ResourceLocation.parse("avaliproject:textures/entity/expie/expie_white.png");
            case NORMAL -> ResourceLocation.parse("avaliproject:textures/entity/expie/expie.png");
        };
    }

    @Override
    public void setCustomAnimations(ExpieEntity animatable, long instanceId, AnimationState<ExpieEntity> animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

        boolean clinging = animatable.isComfortClinging();
        boolean curledUp = clinging || animatable.isSleepingNearPlayer();
        if (curledUp) {
            float lean = clinging ? RIDING_EXTRA_LEAN : 0f;
            setBoneRotation("body", CLING_BODY_ROT_X + lean, 0f, 0f);
            setBoneRotation("rightarm", CLING_ARM_ROT_X, 0f, -CLING_ARM_ROT_Z);
            setBoneRotation("leftarm", CLING_ARM_ROT_X, 0f, CLING_ARM_ROT_Z);
            setBoneRotation("rightthigh", CLING_THIGH_ROT_X, 0f, 0f);
            setBoneRotation("leftthigh", CLING_THIGH_ROT_X, 0f, 0f);
            setBoneRotation("rightleg", CLING_LEG_ROT_X, 0f, 0f);
            setBoneRotation("leftleg", CLING_LEG_ROT_X, 0f, 0f);
            setBoneRotation("basetail", CLING_TAIL_ROT_X, 0f, 0f);
            setBoneRotation("endtail", CLING_TAIL_ROT_X, 0f, 0f);
        } else if (!animatable.getHeldPlush().isEmpty()) {
            setBoneRotation("rightarm", HOLD_ARM_ROT_X, 0f, -HOLD_ARM_ROT_Z);
            setBoneRotation("leftarm", HOLD_ARM_ROT_X, 0f, HOLD_ARM_ROT_Z);
        } else {
            poser.apply(getAnimationProcessor(), animatable, animationState);
        }
    }

    private void setBoneRotation(String boneName, float rotX, float rotY, float rotZ) {
        GeoBone bone = getAnimationProcessor().getBone(boneName);
        if (bone == null) return;
        bone.setRotX(rotX);
        bone.setRotY(rotY);
        bone.setRotZ(rotZ);
    }
}
