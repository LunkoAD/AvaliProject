package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.GeoSpeciesCreatureEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SpeciesCreatureModel<T extends GeoSpeciesCreatureEntity> extends GeoModel<T> {
    private final String name;
    private final ResourceLocation model;
    private final ResourceLocation animation;
    private final GeoHumanoidPoser poser = new GeoHumanoidPoser(true, true);

    public SpeciesCreatureModel(String name) {
        this.name = name;
        this.model = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "geo/" + name + ".geo.json");
        this.animation = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "animations/" + name + ".animation.json");
    }

    @Override
    public ResourceLocation getModelResource(T entity) {
        return model;
    }

    @Override
    public ResourceLocation getAnimationResource(T entity) {
        return animation;
    }

    @Override
    public ResourceLocation getTextureResource(T entity) {
        return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID,
                "textures/entity/" + name + "/" + entity.getTextureName() + ".png");
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            float pitch = Mth.wrapDegrees(entityData.headPitch()) * Mth.DEG_TO_RAD;
            float yaw = Mth.wrapDegrees(entityData.netHeadYaw()) * Mth.DEG_TO_RAD;
            GeoBone neck = getAnimationProcessor().getBone("neck");
            if (neck != null) {
                GeoBoneOffsets.apply(head, pitch, 0.0F, 0.0F);
                GeoBoneOffsets.apply(neck, 0.0F, yaw, 0.0F);
            } else {
                GeoBoneOffsets.apply(head, pitch, yaw, 0.0F);
            }
        }
        poser.apply(getAnimationProcessor(), animatable, animationState);
    }
}
