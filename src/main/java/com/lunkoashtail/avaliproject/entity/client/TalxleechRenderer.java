package com.lunkoashtail.avaliproject.entity.client;

import com.google.common.collect.Maps;
import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.TalxleechEntity;
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

public class TalxleechRenderer extends GeoEntityRenderer<TalxleechEntity> {
    public TalxleechRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TalxleechModel());
        this.shadowRadius = 0.5f;
    }

    private static final Map<TalxleechVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(TalxleechVariant.class), map -> {
                map.put(TalxleechVariant.BROWN,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/talxleech/browntalxleech.png"));
                map.put(TalxleechVariant.REDSOIL,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/talxleech/redsoiltalxleech.png"));
                map.put(TalxleechVariant.SICKBROWN,
                        ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/talxleech/sickbrowntalxleech.png"));
            });

    @Override
    public ResourceLocation getTextureLocation(TalxleechEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
    }

    @Override
    public RenderType getRenderType(TalxleechEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, TalxleechEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        float scale = 1f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
    }
}
