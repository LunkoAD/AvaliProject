package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.client.SpeciesCosmeticTextures;
import com.lunkoashtail.avaliproject.entity.client.geo.GeoModelLoader;
import com.lunkoashtail.avaliproject.entity.custom.AbstractManokitEntity;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesCosmetics;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class ManokitRenderer<T extends AbstractManokitEntity> extends MobRenderer<T, ManokitModel<T>> {
    private static final float SHADOW_RADIUS = 0.4F;
    private static final float HOLOHOLO_RENDER_SCALE = 2.3F;

    private final ManokitVariant variant;
    private final ResourceLocation texture;

    public ManokitRenderer(EntityRendererProvider.Context context, ManokitVariant variant) {
        super(context, new ManokitModel<>(variant, GeoModelLoader.load(context.getResourceManager(),
                ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, variant.geoPath()))), SHADOW_RADIUS);
        this.variant = variant;
        this.texture = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, variant.texturePath());
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    public static <T extends AbstractManokitEntity> EntityRendererProvider<T> provider(ManokitVariant variant) {
        return context -> new ManokitRenderer<>(context, variant);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        ResourceLocation fallback = SpeciesCosmetics.defaultFor(Species.MANOKIT, variant)
                .map(SpeciesCosmetics.Entry::texture)
                .orElse(texture);
        ResourceLocation skin = SpeciesCosmetics.find(Species.MANOKIT, variant, entity.getSkin())
                .map(entry -> SpeciesCosmeticTextures.existingOr(entry.texture(), fallback))
                .orElse(fallback);
        return SpeciesCosmeticTextures.resolve(entity, skin);
    }

    @Override
    protected void scale(T entity, PoseStack poseStack, float partialTick) {
        float scale = variant.renderScale();
        if (variant == ManokitVariant.TRIBAL && ManokitModel.inHoloholo(entity)) {
            scale *= HOLOHOLO_RENDER_SCALE;
        }
        poseStack.scale(scale, scale, scale);
    }

    @Override
    protected void setupRotations(T entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
        HumanoidPoser.applyBodyRotations(entity, poseStack, partialTick, variant.renderScale(),
                !variant.paddleSwims());
    }

    @Override
    public Vec3 getRenderOffset(T entity, float partialTick) {
        return HumanoidPoser.crouchOffset(entity, getModel().poser().hasTorso(), variant.renderScale());
    }
}
