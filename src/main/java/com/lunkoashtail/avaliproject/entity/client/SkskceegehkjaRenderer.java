package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.SkskceegehkjaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SkskceegehkjaRenderer extends MobRenderer<SkskceegehkjaEntity, com.lunkoashtail.avaliproject.entity.client.SkskceegehkjaModel> {
    public SkskceegehkjaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new com.lunkoashtail.avaliproject.entity.client.SkskceegehkjaModel(pContext.bakeLayer(ModModelLayers.SKSKCEEGEHKJA)), 0.75f);
    }
    @Override
    public ResourceLocation getTextureLocation(SkskceegehkjaEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/skskceegehkja/skskceegehkja.png");
    }
    @Override
    public void render(SkskceegehkjaEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pPoseStack.scale(0.45f, 0.45f, 0.45f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}