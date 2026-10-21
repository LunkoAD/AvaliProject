package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.GlassShardEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.minecraft.resources.ResourceLocation;

public class GlassShardRenderer extends EntityRenderer<GlassShardEntity> {

    private static final float SCALE = 0.28f;

    private final BlockRenderDispatcher blockRenderer;

    public GlassShardRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(GlassShardEntity entity, float entityYaw, float partialTick,
                        PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 0.02, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
        poseStack.scale(SCALE, SCALE, SCALE);
        poseStack.translate(-0.5, 0, -0.5);

        this.blockRenderer.renderSingleBlock(entity.getGlassState(), poseStack, bufferSource,
                packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(GlassShardEntity entity) {
        return ResourceLocation.withDefaultNamespace("textures/misc/white.png");
    }
}
