package com.lunkoashtail.avaliproject.entity.client;

import com.google.common.collect.Maps;
import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import net.minecraft.Util;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

import java.util.Map;

public class AvaliRenderer extends GeoEntityRenderer<AvaliEntity> {
    public AvaliRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AvaliModel());
        this.shadowRadius = 0.5f;
    }

    private static final Map<AvaliVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(AvaliVariant.class), map -> {
                map.put(AvaliVariant.PENGUIN,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/avali/avalipenguin.png"));
                map.put(AvaliVariant.BLANK,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/avali/avaliblank.png"));
            });

    @Override
    public ResourceLocation getTextureLocation(AvaliEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
    }

    @Override
    public RenderType getRenderType(AvaliEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, AvaliEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        float scale = 0.65f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
    }
}
