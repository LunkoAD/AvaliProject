package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.client.SpeciesCosmeticTextures;
import com.lunkoashtail.avaliproject.entity.client.geo.GeoModelLoader;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.lunkoashtail.avaliproject.species.Species;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class AvaliRenderer extends MobRenderer<AvaliEntity, AvaliModel> {
    private static final float SHADOW_RADIUS = 0.5F;
    private static final float RENDER_SCALE = 0.65F;
    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "geo/avali.geo.json");
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/avali/avali_cynavali.png");

    public AvaliRenderer(EntityRendererProvider.Context context) {
        super(context, new AvaliModel(GeoModelLoader.load(context.getResourceManager(), MODEL)), SHADOW_RADIUS);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new AvaliEquipmentLayer(this, context.getItemRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(AvaliEntity entity) {
        return SpeciesCosmeticTextures.resolve(entity, SpeciesCosmeticTextures.skinOr(Species.AVALI, entity.getTexture(), TEXTURE));
    }

    @Override
    protected void scale(AvaliEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(RENDER_SCALE, RENDER_SCALE, RENDER_SCALE);
    }

    @Override
    protected void setupRotations(AvaliEntity entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
        HumanoidPoser.applyBodyRotations(entity, poseStack, partialTick, RENDER_SCALE, true);
    }

    @Override
    public Vec3 getRenderOffset(AvaliEntity entity, float partialTick) {
        return HumanoidPoser.crouchOffset(entity, getModel().poser().hasTorso(), RENDER_SCALE);
    }
}
