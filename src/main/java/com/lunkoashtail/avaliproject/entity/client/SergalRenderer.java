package com.lunkoashtail.avaliproject.entity.client;

import com.google.common.collect.Maps;
import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.SergalEntity;
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

public class SergalRenderer extends GeoEntityRenderer<SergalEntity> {
    public SergalRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SergalModel());
        this.shadowRadius = 0.5f;
    }

    private static final Map<SergalVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SergalVariant.class), map -> {
                map.put(SergalVariant.BLACK,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_black.png"));
                map.put(SergalVariant.GREY,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_grey.png"));
                map.put(SergalVariant.BLUE,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_blue.png"));
                map.put(SergalVariant.BROWN,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_brown.png"));
                map.put(SergalVariant.BROWN_ALT,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_brown_alt.png"));
                map.put(SergalVariant.CRIMSON,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_crimson.png"));
                map.put(SergalVariant.FROST,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_frost.png"));
                map.put(SergalVariant.GREEN,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_green.png"));
                map.put(SergalVariant.ORANGE,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_orange.png"));
                map.put(SergalVariant.PEACH,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_peach.png"));
                map.put(SergalVariant.PINK,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_pink.png"));
                map.put(SergalVariant.PURPLE,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/sergal/sergal_purple.png"));
            });

    @Override
    public ResourceLocation getTextureLocation(SergalEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
    }

    @Override
    public RenderType getRenderType(SergalEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, SergalEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        float scale = 1.2f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
    }
}
