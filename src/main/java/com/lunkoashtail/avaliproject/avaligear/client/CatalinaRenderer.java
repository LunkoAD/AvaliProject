package com.lunkoashtail.avaliproject.avaligear.client;

import com.lunkoashtail.avaliproject.avaligear.ship.CatalinaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class CatalinaRenderer extends GeoEntityRenderer<CatalinaEntity> {
    public CatalinaRenderer(EntityRendererProvider.Context context) {
        super(context, new CatalinaModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 4.0F;
        this.shadowStrength = 0.6F;
    }

    @Override
    public RenderType getRenderType(CatalinaEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }

    @Override
    protected void applyRotations(CatalinaEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw,
                                  float partialTick, float nativeScale) {
        float yaw = Mth.rotLerp(partialTick, animatable.yRotO, animatable.getYRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw));
    }
}
