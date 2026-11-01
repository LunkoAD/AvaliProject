package com.lunkoashtail.avaliproject.block.entity.client;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import com.lunkoashtail.avaliproject.block.custom.MachineFootprint;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ProtogenMachineRenderer<T extends BlockEntity & GeoAnimatable> extends GeoBlockRenderer<T> {
    public ProtogenMachineRenderer(String name) {
        super(new ProtogenMachineModel<>(name));
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        MachineFootprint footprint = MachineFootprint.of(blockEntity.getBlockState().getBlock());
        return footprint == null ? new AABB(blockEntity.getBlockPos()).inflate(1.0)
                : footprint.renderBounds(blockEntity.getBlockPos(), MachineFootprint.facingOf(blockEntity.getBlockState()));
    }

    @Override
    protected void rotateBlock(Direction facing, PoseStack poseStack) {
        super.rotateBlock(facing, poseStack);
        T blockEntity = getAnimatable();
        MachineFootprint footprint = blockEntity == null ? null : MachineFootprint.of(blockEntity.getBlockState().getBlock());
        if (footprint != null) poseStack.translate(footprint.renderOffsetX(), 0.0F, footprint.renderOffsetZ());
    }
}
