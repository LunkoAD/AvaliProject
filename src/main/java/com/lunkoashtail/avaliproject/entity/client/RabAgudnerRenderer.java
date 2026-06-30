package com.lunkoashtail.avaliproject.entity.client;

import com.google.common.collect.Maps;
import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.RabAgudnerEntity;
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

public class RabAgudnerRenderer extends GeoEntityRenderer<RabAgudnerEntity> {
    public RabAgudnerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RabAgudnerModel());
        this.shadowRadius = 0.4f;
    }

    private static final Map<RabAgudnerVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(RabAgudnerVariant.class), map -> {
                map.put(RabAgudnerVariant.FIDDLE,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/rabbit_agudner/fiddle_the_agudner.png"));
                map.put(RabAgudnerVariant.MAROON,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/rabbit_agudner/maroon_rabbit_agudner.png"));
            });

    @Override
    public ResourceLocation getTextureLocation(RabAgudnerEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
    }

    @Override
    public RenderType getRenderType(RabAgudnerEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, RabAgudnerEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        float scale = 1f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
    }
}
