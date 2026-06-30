package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ExpieRenderer extends GeoEntityRenderer<ExpieEntity> {

    // Model is ~2.04 blocks tall; scale to 1.5 blocks (1.5 / 2.04 ≈ 0.735)
    private static final float SCALE = 0.735f;

    public ExpieRenderer(EntityRendererProvider.Context context) {
        super(context, new ExpieModel());
    }

    @Override
    public void render(ExpieEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(SCALE, SCALE, SCALE);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
