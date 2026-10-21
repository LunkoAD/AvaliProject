package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.client.event.SpeciesRenderHandler;
import com.lunkoashtail.avaliproject.entity.client.geo.GeoModelParts;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public final class HumanoidPoser {
    private static final float VANILLA_ARM_Y = 2.0F;
    private static final float VANILLA_LEG_Y = 12.0F;
    private static final float VANILLA_SHOULDER_TO_HIP = 10.0F;
    private static final float NECK_CROUCH_SHARE = 0.8F;

    private final HumanoidModel<LivingEntity> proxy = new HumanoidModel<>(
            LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 64).bakeRoot());
    @Nullable private final ModelPart head;
    @Nullable private final ModelPart torso;
    @Nullable private final ModelPart neck;
    @Nullable private final ModelPart rightArm;
    @Nullable private final ModelPart leftArm;
    @Nullable private final ModelPart rightLeg;
    @Nullable private final ModelPart leftLeg;
    private final float proportion;
    private boolean swimStrokes = true;

    public HumanoidPoser(GeoModelParts parts, String headBone, @Nullable String torsoBone, @Nullable String neckBone,
                         String rightArmBone, String leftArmBone, String rightLegBone, String leftLegBone) {
        this.head = parts.part(headBone);
        this.torso = torsoBone == null ? null : parts.part(torsoBone);
        this.neck = neckBone == null ? null : parts.part(neckBone);
        this.rightArm = parts.part(rightArmBone);
        this.leftArm = parts.part(leftArmBone);
        this.rightLeg = parts.part(rightLegBone);
        this.leftLeg = parts.part(leftLegBone);
        float shoulderToHip = parts.geoPivotY(rightArmBone) - parts.geoPivotY(rightLegBone);
        this.proportion = shoulderToHip > 0.0F ? shoulderToHip / VANILLA_SHOULDER_TO_HIP : 1.0F;
    }

    public HumanoidPoser disableSwimStrokes() {
        this.swimStrokes = false;
        return this;
    }

    public boolean hasTorso() {
        return torso != null;
    }

    public void apply(LivingEntity entity, float attackTime, boolean sitting, float limbSwing, float limbSwingAmount,
                      float ageInTicks, float netHeadYaw, float headPitch) {
        Player owner = SpeciesRenderHandler.ownerOf(entity);
        LivingEntity source = owner != null ? owner : entity;
        float partialTick = Mth.clamp(ageInTicks - entity.tickCount, 0.0F, 1.0F);

        proxy.attackTime = attackTime;
        proxy.riding = sitting;
        proxy.young = false;
        proxy.crouching = source.isCrouching();
        proxy.swimAmount = swimStrokes ? source.getSwimAmount(partialTick) : 0.0F;
        HumanoidModel.ArmPose mainPose = armPose(source, InteractionHand.MAIN_HAND);
        HumanoidModel.ArmPose offPose = armPose(source, InteractionHand.OFF_HAND);
        if (mainPose.isTwoHanded()) {
            offPose = source.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
        }
        boolean rightHanded = source.getMainArm() == HumanoidArm.RIGHT;
        proxy.rightArmPose = rightHanded ? mainPose : offPose;
        proxy.leftArmPose = rightHanded ? offPose : mainPose;
        proxy.setupAnim(source, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        addRotation(head, proxy.head);
        if (head != null) head.y += proxy.head.y * proportion;
        addLimb(rightArm, proxy.rightArm, VANILLA_ARM_Y);
        addLimb(leftArm, proxy.leftArm, VANILLA_ARM_Y);
        addLimb(rightLeg, proxy.rightLeg, VANILLA_LEG_Y);
        addLimb(leftLeg, proxy.leftLeg, VANILLA_LEG_Y);
        if (torso != null) {
            addRotation(torso, proxy.body);
            torso.y += proxy.body.y * proportion;
        } else if (neck != null) {
            neck.xRot += proxy.body.xRot * NECK_CROUCH_SHARE;
            neck.yRot += proxy.body.yRot;
        }
    }

    private void addLimb(@Nullable ModelPart target, ModelPart source, float neutralY) {
        if (target == null) return;
        addRotation(target, source);
        target.y += (source.y - neutralY) * proportion;
        target.z += source.z * proportion;
    }

    private static void addRotation(@Nullable ModelPart target, ModelPart source) {
        if (target == null) return;
        target.xRot += source.xRot;
        target.yRot += source.yRot;
        target.zRot += source.zRot;
    }

    static HumanoidModel.ArmPose armPose(LivingEntity entity, InteractionHand hand) {
        ItemStack stack = entity.getItemInHand(hand);
        if (stack.isEmpty()) return HumanoidModel.ArmPose.EMPTY;
        if (entity.getUsedItemHand() == hand && entity.getUseItemRemainingTicks() > 0) {
            UseAnim useAnim = stack.getUseAnimation();
            switch (useAnim) {
                case BLOCK -> { return HumanoidModel.ArmPose.BLOCK; }
                case BOW -> { return HumanoidModel.ArmPose.BOW_AND_ARROW; }
                case SPEAR -> { return HumanoidModel.ArmPose.THROW_SPEAR; }
                case CROSSBOW -> { return HumanoidModel.ArmPose.CROSSBOW_CHARGE; }
                case SPYGLASS -> { return HumanoidModel.ArmPose.SPYGLASS; }
                case TOOT_HORN -> { return HumanoidModel.ArmPose.TOOT_HORN; }
                case BRUSH -> { return HumanoidModel.ArmPose.BRUSH; }
                default -> { }
            }
        } else if (!entity.swinging && stack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(stack)) {
            return HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
        HumanoidModel.ArmPose custom = IClientItemExtensions.of(stack).getArmPose(entity, hand, stack);
        return custom != null ? custom : HumanoidModel.ArmPose.ITEM;
    }

    public static void applyBodyRotations(LivingEntity entity, PoseStack poseStack, float partialTick, float modelScale,
                                          boolean tiltWhenSwimming) {
        Player owner = SpeciesRenderHandler.ownerOf(entity);
        if (owner == null) return;
        float swimAmount = owner.getSwimAmount(partialTick);
        if (owner.isFallFlying()) {
            float flyTicks = owner.getFallFlyingTicks() + partialTick;
            float blend = Mth.clamp(flyTicks * flyTicks / 100.0F, 0.0F, 1.0F);
            if (!owner.isAutoSpinAttack()) {
                poseStack.mulPose(Axis.XP.rotationDegrees(blend * (-90.0F - owner.getViewXRot(partialTick))));
            }
            Vec3 view = owner.getViewVector(partialTick);
            Vec3 motion = owner instanceof AbstractClientPlayer clientPlayer
                    ? clientPlayer.getDeltaMovementLerped(partialTick) : owner.getDeltaMovement();
            double motionSqr = motion.horizontalDistanceSqr();
            double viewSqr = view.horizontalDistanceSqr();
            if (motionSqr > 0.0 && viewSqr > 0.0) {
                double cos = (motion.x * view.x + motion.z * view.z) / Math.sqrt(motionSqr * viewSqr);
                double cross = motion.x * view.z - motion.z * view.x;
                poseStack.mulPose(Axis.YP.rotation((float) (Math.signum(cross) * Math.acos(cos))));
            }
        } else if (tiltWhenSwimming && swimAmount > 0.0F) {
            boolean inFluid = owner.isInWater() || owner.isInFluidType((fluidType, height) -> owner.canSwimInFluidType(fluidType));
            float target = inFluid ? -90.0F - owner.getXRot() : -90.0F;
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(swimAmount, 0.0F, target)));
            if (owner.isVisuallySwimming()) {
                poseStack.translate(0.0F, -modelScale, 0.3F * modelScale);
            }
        }
    }

    public static Vec3 crouchOffset(LivingEntity entity, boolean lowers, float modelScale) {
        Player owner = SpeciesRenderHandler.ownerOf(entity);
        if (!lowers || owner == null || !owner.isCrouching()) return Vec3.ZERO;
        return new Vec3(0.0, -2.0 / 16.0 * modelScale, 0.0);
    }
}
