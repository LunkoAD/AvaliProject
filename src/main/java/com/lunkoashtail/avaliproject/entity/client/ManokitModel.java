package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.client.event.SpeciesRenderHandler;
import com.lunkoashtail.avaliproject.entity.client.geo.GeoModelParts;
import com.lunkoashtail.avaliproject.entity.custom.AbstractManokitEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ManokitModel<T extends AbstractManokitEntity> extends EntityModel<T> implements ArmedModel {
    private static final float WALK_FREQUENCY = 0.6662F;
    private static final float SIT_HIP_CLEARANCE = 2.0F;
    private static final float HAND_ITEM_X = 1.0F / 16.0F;
    private static final float HAND_ITEM_Y = -0.625F;
    private static final float HAND_ITEM_Z = 0.125F;
    private static final float LEG_TRAIL = 80.0F * Mth.DEG_TO_RAD;
    private static final float PADDLE_SPEED = 0.3F;
    private static final float PADDLE_SWING = 0.25F;
    private static final float PADDLE_SWING_MOVING = 0.35F;
    private static final float NECK_DIP = 0.5F;
    private static final float HEAD_DIP = 0.2F;
    private static final float NECK_SHIFT_SPEED = 0.12F;
    private static final float NECK_SHIFT_PITCH = 0.06F;
    private static final float NECK_SHIFT_YAW = 0.08F;
    private static final String HOLOHOLO_MODIFIER = "tribal_holoholo";
    private static final float HIP_HEIGHT = 12.0F;
    private static final float FRONT_PAW_DROP = 2.0F;
    private static final float HEAD_LOWER = 2.0F;
    private static final float HEAD_FORWARD = 5.0F;
    private static final float TAIL_DROOP = 0.35F;
    private static final float TAIL_LOWER = 4.0F;
    private static final float TAIL_BACK = 6.0F;
    private static final float TAIL_SCALE = 1.4F;
    private static final float HEAD_SCALE = 0.85F;
    private static final float HEAD_CENTER_Y = -4.0F;
    private static final float HEAD_CENTER_Z = -1.5F;
    private static final String FIN_BONE = "Fin";
    private static final float TORSO_THICKNESS = 4.0F;
    private static final float TORSO_GROWTH = 2.0F;
    private static final float TORSO_LENGTH = 12.0F;
    private static final float TORSO_EXTEND_FRONT = 2.0F;
    private static final float TORSO_EXTEND_BACK = 1.0F;
    private static final float GAIT_SWING = 1.4F;

    private final ManokitVariant variant;
    private final GeoModelParts parts;
    private final HumanoidPoser poser;
    private final float sitDrop;
    @Nullable private final ModelPart body;
    @Nullable private final ModelPart tail;
    @Nullable private final ModelPart neck;
    @Nullable private final ModelPart head;
    @Nullable private final ModelPart rightArm;
    @Nullable private final ModelPart leftArm;
    @Nullable private final ModelPart rightLeg;
    @Nullable private final ModelPart leftLeg;
    @Nullable private final ModelPart torso;
    @Nullable private final ModelPart fin;
    private boolean thickTorso;

    public ManokitModel(ManokitVariant variant, GeoModelParts parts) {
        super(RenderType::entityTranslucent);
        this.variant = variant;
        this.parts = parts;
        this.poser = new HumanoidPoser(parts, variant.headBone(), variant.torsoBone(), variant.neckBone(),
                variant.rightArmBone(), variant.leftArmBone(), variant.rightLegBone(), variant.leftLegBone());
        this.body = parts.part(variant.bodyBone());
        this.tail = parts.part(variant.tailBone());
        this.neck = variant.neckBone() == null ? null : parts.part(variant.neckBone());
        this.head = parts.part(variant.headBone());
        this.rightArm = parts.part(variant.rightArmBone());
        this.leftArm = parts.part(variant.leftArmBone());
        this.rightLeg = parts.part(variant.rightLegBone());
        this.leftLeg = parts.part(variant.leftLegBone());
        this.torso = variant.torsoBone() == null ? null : parts.part(variant.torsoBone());
        this.fin = parts.part(FIN_BONE);
        if (variant.paddleSwims()) {
            poser.disableSwimStrokes();
        }
        this.sitDrop = Math.max(0.0F, parts.geoPivotY(variant.leftLegBone()) - SIT_HIP_CLEARANCE);
    }

    public HumanoidPoser poser() {
        return poser;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        parts.resetPose();
        resetScale(tail);
        resetScale(head);
        resetScale(torso);
        thickTorso = false;
        boolean sitting = entity.isInSittingPose();
        poser.apply(entity, attackTime, sitting, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (variant.paddleSwims()) {
            paddle(entity, limbSwingAmount, ageInTicks);
        } else if (variant == ManokitVariant.TRIBAL && inHoloholo(entity)) {
            fourLegged(limbSwing, limbSwingAmount);
        }

        if (sitting && body != null) {
            body.y += sitDrop;
        }

        if (tail != null) {
            float swing = limbSwing * WALK_FREQUENCY;
            tail.yRot += Mth.sin(ageInTicks * 0.1F) * 0.15F + Mth.cos(swing) * 0.2F * limbSwingAmount;
        }
    }

    private void paddle(T entity, float limbSwingAmount, float ageInTicks) {
        Player owner = SpeciesRenderHandler.ownerOf(entity);
        LivingEntity source = owner != null ? owner : entity;
        float swim = source.getSwimAmount(Mth.clamp(ageInTicks - entity.tickCount, 0.0F, 1.0F));
        if (swim <= 0.0F) return;

        float stroke = Mth.sin(ageInTicks * PADDLE_SPEED) * (PADDLE_SWING + PADDLE_SWING_MOVING * Math.min(1.0F, limbSwingAmount));
        blendTo(rightLeg, swim, LEG_TRAIL + stroke);
        blendTo(leftLeg, swim, LEG_TRAIL - stroke);
        blendTo(rightArm, swim, 0.0F);
        blendTo(leftArm, swim, 0.0F);

        float shift = ageInTicks * NECK_SHIFT_SPEED;
        if (neck != null) {
            neck.xRot += swim * (NECK_DIP + Mth.sin(shift) * NECK_SHIFT_PITCH);
            neck.yRot += swim * Mth.sin(shift * 0.5F) * NECK_SHIFT_YAW;
        }
        if (head != null) {
            head.xRot += swim * HEAD_DIP;
        }
    }

    static boolean inHoloholo(LivingEntity entity) {
        Player owner = SpeciesRenderHandler.ownerOf(entity);
        if (owner == null) return false;
        AttributeInstance scale = owner.getAttribute(Attributes.SCALE);
        if (scale == null) return false;
        for (var modifier : scale.getModifiers()) {
            if (modifier.id().getPath().contains(HOLOHOLO_MODIFIER)) return true;
        }
        return false;
    }

    private void fourLegged(float limbSwing, float limbSwingAmount) {
        if (body != null) {
            PartPose rest = body.getInitialPose();
            body.xRot = rest.xRot + Mth.HALF_PI;
            body.y = rest.y - HIP_HEIGHT;
            body.z = rest.z + HIP_HEIGHT;
        }
        if (torso != null) {
            torso.zScale = (TORSO_THICKNESS + TORSO_GROWTH) / TORSO_THICKNESS;
            torso.z += TORSO_GROWTH / 2.0F;
            torso.yScale = (TORSO_LENGTH + TORSO_EXTEND_FRONT + TORSO_EXTEND_BACK) / TORSO_LENGTH;
            torso.y -= TORSO_EXTEND_FRONT;
            thickTorso = true;
        }
        if (fin != null) {
            fin.z += TORSO_GROWTH / 2.0F;
            fin.y += TORSO_EXTEND_FRONT;
        }
        if (head != null) {
            PartPose headRest = head.getInitialPose();
            float yaw = head.yRot - headRest.yRot;
            head.yRot = headRest.yRot;
            head.zRot -= yaw;
            head.xRot -= Mth.HALF_PI;
            head.z -= HEAD_LOWER;
            head.xScale = HEAD_SCALE;
            head.yScale = HEAD_SCALE;
            head.zScale = HEAD_SCALE;
            head.y -= HEAD_FORWARD;
            pivotAroundCenter(head, headRest);
        }
        if (tail != null) {
            tail.xRot -= Mth.HALF_PI + TAIL_DROOP;
            tail.z -= TAIL_LOWER;
            tail.y += TAIL_BACK + TORSO_EXTEND_FRONT + TORSO_EXTEND_BACK;
            tail.xScale = TAIL_SCALE;
            tail.yScale = TAIL_SCALE;
            tail.zScale = TAIL_SCALE;
        }
        float swing = limbSwing * WALK_FREQUENCY;
        float amount = Math.min(1.0F, limbSwingAmount) * GAIT_SWING;
        standLeg(rightArm, legX(rightLeg, rightArm), FRONT_PAW_DROP, Mth.cos(swing + Mth.PI) * amount);
        standLeg(leftArm, legX(leftLeg, leftArm), FRONT_PAW_DROP, Mth.cos(swing) * amount);
        standLeg(rightLeg, legX(rightLeg, rightLeg), 0.0F, Mth.cos(swing) * amount);
        standLeg(leftLeg, legX(leftLeg, leftLeg), 0.0F, Mth.cos(swing + Mth.PI) * amount);
    }

    private static void pivotAroundCenter(ModelPart part, PartPose rest) {
        Vector3f center = new Vector3f(0.0F, HEAD_CENTER_Y * part.yScale, HEAD_CENTER_Z * part.zScale);
        Vector3f still = new Quaternionf().rotationZYX(rest.zRot, rest.yRot, rest.xRot - Mth.HALF_PI).transform(new Vector3f(center));
        Vector3f turned = new Quaternionf().rotationZYX(part.zRot, part.yRot, part.xRot).transform(new Vector3f(center));
        part.x += still.x - turned.x;
        part.y += still.y - turned.y;
        part.z += still.z - turned.z;
    }

    private static void resetScale(@Nullable ModelPart part) {
        if (part == null) return;
        part.xScale = 1.0F;
        part.yScale = 1.0F;
        part.zScale = 1.0F;
    }

    private static float legX(@Nullable ModelPart hindLeg, @Nullable ModelPart fallback) {
        if (hindLeg != null) return hindLeg.getInitialPose().x;
        return fallback == null ? 0.0F : fallback.getInitialPose().x;
    }

    private static void standLeg(@Nullable ModelPart part, float x, float drop, float stride) {
        if (part == null) return;
        PartPose rest = part.getInitialPose();
        part.x = x;
        part.y = rest.y;
        part.z = rest.z - drop;
        part.xRot = rest.xRot - Mth.HALF_PI + stride;
        part.yRot = rest.yRot;
        part.zRot = rest.zRot;
    }

    private static void blendTo(@Nullable ModelPart part, float amount, float xOffset) {
        if (part == null) return;
        PartPose rest = part.getInitialPose();
        part.xRot = Mth.lerp(amount, part.xRot, rest.xRot + xOffset);
        part.yRot = Mth.lerp(amount, part.yRot, rest.yRot);
        part.zRot = Mth.lerp(amount, part.zRot, rest.zRot);
        part.x = Mth.lerp(amount, part.x, rest.x);
        part.y = Mth.lerp(amount, part.y, rest.y);
        part.z = Mth.lerp(amount, part.z, rest.z);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (!thickTorso || torso == null) {
            parts.root().render(poseStack, buffer, packedLight, packedOverlay, color);
            return;
        }
        setVisible(fin, false);
        setVisible(tail, false);
        parts.root().render(poseStack, buffer, packedLight, packedOverlay, color);
        setVisible(fin, true);
        setVisible(tail, true);
        float stretchLength = torso.yScale;
        float stretchThickness = torso.zScale;
        torso.yScale = 1.0F;
        torso.zScale = 1.0F;
        renderOnTorso(fin, poseStack, buffer, packedLight, packedOverlay, color);
        renderOnTorso(tail, poseStack, buffer, packedLight, packedOverlay, color);
        torso.yScale = stretchLength;
        torso.zScale = stretchThickness;
    }

    private void renderOnTorso(@Nullable ModelPart part, PoseStack poseStack, VertexConsumer buffer, int packedLight,
                               int packedOverlay, int color) {
        if (part == null) return;
        poseStack.pushPose();
        parts.translateTo(variant.torsoBone(), poseStack);
        part.render(poseStack, buffer, packedLight, packedOverlay, color);
        poseStack.popPose();
    }

    private static void setVisible(@Nullable ModelPart part, boolean visible) {
        if (part != null) part.visible = visible;
    }

    @Override
    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        String bone = side == HumanoidArm.RIGHT ? variant.rightHandBone() : variant.leftHandBone();
        if (!parts.translateTo(bone, poseStack) || variant.playerShaped()) return;
        poseStack.translate(side == HumanoidArm.RIGHT ? HAND_ITEM_X : -HAND_ITEM_X, HAND_ITEM_Y, HAND_ITEM_Z);
    }
}
