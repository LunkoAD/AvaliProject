package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.client.SpeciesCosmeticTextures;
import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ExpieRenderer extends GeoEntityRenderer<ExpieEntity> {

    
    private static final float SCALE = 0.735f;

    public ExpieRenderer(EntityRendererProvider.Context context) {
        super(context, new ExpieModel());
        this.addRenderLayer(new ExpiePlushLayer(this));
        this.addRenderLayer(new SpeciesHeldItemLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ExpieEntity entity) {
        return SpeciesCosmeticTextures.resolve(entity, SpeciesCosmeticTextures.skinOr(entity.skinSpecies(), entity.getSkin(),
                super.getTextureLocation(entity)));
    }

    @Override
    public void render(ExpieEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(SCALE, SCALE, SCALE);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    @Override
    protected void applyRotations(ExpieEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick, nativeScale);
        HumanoidPoser.applyBodyRotations(animatable, poseStack, partialTick, 1.0F, false);
    }
}
