package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.client.geo.GeoModelParts;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.Nullable;

public class AvaliModel extends EntityModel<AvaliEntity> implements ArmedModel, HeadedModel {
    public static final String HEAD = "Head";
    public static final String BODY = "Body";
    public static final String RIGHT_ARM = "RightArm";
    public static final String LEFT_ARM = "LeftArm";
    public static final String RIGHT_LEG = "RightLeg";
    public static final String LEFT_LEG = "LeftLeg";
    public static final String RIGHT_FOOT = "Foot Right 1";
    private static final String TAIL = "Tail 0";

    private static final float WALK_FREQUENCY = 0.6662F;
    private static final float SIT_HIP_CLEARANCE = 2.0F;

    private final GeoModelParts parts;
    private final HumanoidPoser poser;
    private final float sitDrop;
    @Nullable private final ModelPart body;
    @Nullable private final ModelPart head;
    @Nullable private final ModelPart rightArm;
    @Nullable private final ModelPart leftArm;
    @Nullable private final ModelPart tail;

    public AvaliModel(GeoModelParts parts) {
        super(RenderType::entityTranslucent);
        this.parts = parts;
        this.poser = new HumanoidPoser(parts, HEAD, BODY, null, RIGHT_ARM, LEFT_ARM, RIGHT_LEG, LEFT_LEG);
        this.body = parts.part(BODY);
        this.head = parts.part(HEAD);
        this.rightArm = parts.part(RIGHT_ARM);
        this.leftArm = parts.part(LEFT_ARM);
        this.tail = parts.part(TAIL);
        this.sitDrop = Math.max(0.0F, parts.geoPivotY(LEFT_LEG) - SIT_HIP_CLEARANCE);
    }

    public GeoModelParts parts() {
        return parts;
    }

    public HumanoidPoser poser() {
        return poser;
    }

    @Override
    public void setupAnim(AvaliEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        parts.resetPose();
        boolean sitting = entity.isInSittingPose();
        poser.apply(entity, attackTime, sitting, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (sitting) {
            lower(body);
            lower(head);
            lower(rightArm);
            lower(leftArm);
        }

        if (tail != null) {
            float swing = limbSwing * WALK_FREQUENCY;
            tail.yRot += Mth.sin(ageInTicks * 0.1F) * 0.15F + Mth.cos(swing) * 0.2F * limbSwingAmount;
        }
    }

    private void lower(@Nullable ModelPart part) {
        if (part != null) part.y += sitDrop;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        parts.root().render(poseStack, buffer, packedLight, packedOverlay, color);
    }

    @Override
    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        parts.translateTo(side == HumanoidArm.RIGHT ? RIGHT_ARM : LEFT_ARM, poseStack);
    }

    @Override
    public ModelPart getHead() {
        return head != null ? head : parts.root();
    }
}
