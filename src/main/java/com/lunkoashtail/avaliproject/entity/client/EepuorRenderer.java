package com.lunkoashtail.avaliproject.entity.client;

import com.google.common.collect.Maps;
import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.EepuorEntity;
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

public class EepuorRenderer extends GeoEntityRenderer<EepuorEntity> {
    public EepuorRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EepuorModel());
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new EepuorLayer(this));
    }

    private static final Map<EepuorVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(EepuorVariant.class), map -> {
                map.put(EepuorVariant.GLOW,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/eepuor/eepuorglow.png"));
                map.put(EepuorVariant.CYAN,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/eepuor/eepuor_cyan.png"));
                map.put(EepuorVariant.SWAMP_GREEN,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/eepuor/eepuor_swamp_green.png"));
                map.put(EepuorVariant.PALE_DEEP,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/eepuor/eepuor_pale_deep.png"));
            });

    @Override
    public ResourceLocation getTextureLocation(EepuorEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
    }

    @Override
    public RenderType getRenderType(EepuorEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, EepuorEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        float scale = 1f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
    }
}
