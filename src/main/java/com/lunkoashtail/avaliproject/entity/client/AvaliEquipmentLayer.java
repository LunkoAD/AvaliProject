package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class AvaliEquipmentLayer extends RenderLayer<AvaliEntity, AvaliModel> {
    private static final Map<EquipmentSlot, String> SLOT_BONES = Map.of(
            EquipmentSlot.HEAD, AvaliModel.HEAD,
            EquipmentSlot.CHEST, AvaliModel.BODY,
            EquipmentSlot.LEGS, AvaliModel.RIGHT_LEG,
            EquipmentSlot.FEET, AvaliModel.RIGHT_FOOT);

    private final ItemRenderer itemRenderer;

    public AvaliEquipmentLayer(RenderLayerParent<AvaliEntity, AvaliModel> parent, ItemRenderer itemRenderer) {
        super(parent);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AvaliEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        for (Map.Entry<EquipmentSlot, String> entry : SLOT_BONES.entrySet()) {
            ItemStack stack = entity.getItemBySlot(entry.getKey());
            if (stack.isEmpty()) continue;
            poseStack.pushPose();
            if (getParentModel().parts().translateTo(entry.getValue(), poseStack)) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                itemRenderer.renderStatic(entity, stack, ItemDisplayContext.FIXED, false, poseStack, bufferSource,
                        entity.level(), packedLight, OverlayTexture.NO_OVERLAY, entity.getId());
            }
            poseStack.popPose();
        }
    }
}
