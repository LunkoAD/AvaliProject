package com.lunkoashtail.avaliproject.block.entity.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenStationBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import com.lunkoashtail.avaliproject.block.custom.MachineFootprint;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ProtogenStationRenderer extends GeoBlockRenderer<ProtogenStationBlockEntity> {
    public ProtogenStationRenderer() {
        super(new GeoModel<>() {
            @Override
            public ResourceLocation getModelResource(ProtogenStationBlockEntity animatable) {
                return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "geo/" + animatable.getStationName() + ".geo.json");
            }

            @Override
            public ResourceLocation getTextureResource(ProtogenStationBlockEntity animatable) {
                return texture(animatable.getStationName());
            }

            @Override
            public ResourceLocation getAnimationResource(ProtogenStationBlockEntity animatable) {
                return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "animations/" + animatable.getStationName() + ".animation.json");
            }
        });
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    public static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/block/" + name + ".png");
    }

    @Override
    public RenderType getRenderType(ProtogenStationBlockEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public AABB getRenderBoundingBox(ProtogenStationBlockEntity blockEntity) {
        MachineFootprint footprint = MachineFootprint.of(blockEntity.getBlockState().getBlock());
        return footprint == null ? new AABB(blockEntity.getBlockPos()).inflate(1.0)
                : footprint.renderBounds(blockEntity.getBlockPos(), MachineFootprint.facingOf(blockEntity.getBlockState()));
    }

    @Override
    protected void rotateBlock(Direction facing, PoseStack poseStack) {
        super.rotateBlock(facing, poseStack);
        ProtogenStationBlockEntity blockEntity = getAnimatable();
        MachineFootprint footprint = blockEntity == null ? null : MachineFootprint.of(blockEntity.getBlockState().getBlock());
        if (footprint != null) poseStack.translate(footprint.renderOffsetX(), 0.0F, footprint.renderOffsetZ());
    }
}
