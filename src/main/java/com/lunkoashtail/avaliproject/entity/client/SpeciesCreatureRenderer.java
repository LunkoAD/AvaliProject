package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.client.SpeciesCosmeticTextures;
import com.lunkoashtail.avaliproject.entity.custom.GeoSpeciesCreatureEntity;
import com.lunkoashtail.avaliproject.entity.custom.SkinnedSpeciesEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SpeciesCreatureRenderer<T extends GeoSpeciesCreatureEntity> extends GeoEntityRenderer<T> {
    private static final float BABY_SCALE = 0.5F;

    private final float scale;

    public SpeciesCreatureRenderer(EntityRendererProvider.Context context, String name, float scale, float shadowRadius) {
        super(context, new SpeciesCreatureModel<>(name));
        this.scale = scale;
        this.shadowRadius = shadowRadius;
        this.addRenderLayer(new SpeciesHeldItemLayer<>(this));
    }

    public static <T extends GeoSpeciesCreatureEntity> EntityRendererProvider<T> provider(String name, float scale, float shadowRadius) {
        return context -> new SpeciesCreatureRenderer<>(context, name, scale, shadowRadius);
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        ResourceLocation base = super.getTextureLocation(animatable);
        if (animatable instanceof SkinnedSpeciesEntity skinned) {
            base = SpeciesCosmeticTextures.skinOr(skinned.skinSpecies(), skinned.getSkin(), base);
        }
        return SpeciesCosmeticTextures.resolve(animatable, base);
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public void preRender(PoseStack poseStack, T entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer,
                          boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        float finalScale = entity.isBaby() ? scale * BABY_SCALE : scale;
        this.scaleHeight = finalScale;
        this.scaleWidth = finalScale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
    }

    @Override
    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick, nativeScale);
        HumanoidPoser.applyBodyRotations(animatable, poseStack, partialTick, 1.0F, false);
    }
}
