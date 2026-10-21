package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class ExpiePlushLayer extends BlockAndItemGeoLayer<ExpieEntity> {
    public ExpiePlushLayer(GeoRenderer<ExpieEntity> renderer) {
        super(renderer);
    }

    @Override
    @Nullable
    protected ItemStack getStackForBone(GeoBone bone, ExpieEntity animatable) {
        if (!bone.getName().equals("body")) return null;
        ItemStack stack = animatable.getHeldPlush();
        return stack.isEmpty() ? null : stack;
    }

    @Override
    protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, ExpieEntity animatable) {
        return ItemDisplayContext.FIXED;
    }

    private static final float CHEST_UP = 0.55f;
    private static final float CHEST_FORWARD = -0.55f;

    @Override
    protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, ExpieEntity animatable,
                                       MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0, CHEST_UP, CHEST_FORWARD);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
        poseStack.popPose();
    }
}
