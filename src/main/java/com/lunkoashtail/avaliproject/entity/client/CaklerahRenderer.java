package com.lunkoashtail.avaliproject.entity.client;

import com.google.common.collect.Maps;
import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.CaklerahEntity;
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

public class CaklerahRenderer extends GeoEntityRenderer<CaklerahEntity> {
    public CaklerahRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CaklerahModel());
        this.shadowRadius = 0.5f;
    }

    private static final Map<CaklerahVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CaklerahVariant.class), map -> {
                map.put(CaklerahVariant.DEFAULT,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/caklerah/caklerah.png"));
                map.put(CaklerahVariant.CHOCOMILK,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/caklerah/caklerah_chocomilk.png"));
                map.put(CaklerahVariant.MILK,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/caklerah/caklerah_milk.png"));
            });

    @Override
    public ResourceLocation getTextureLocation(CaklerahEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
    }

    @Override
    public RenderType getRenderType(CaklerahEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, CaklerahEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        float scale = 1f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
    }
}
