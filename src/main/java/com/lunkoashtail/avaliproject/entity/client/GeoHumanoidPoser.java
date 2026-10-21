package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.client.event.SpeciesRenderHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationProcessor;

public final class GeoHumanoidPoser {
    private static final String BODY = "body";
    private static final String RIGHT_ARM = "rightarm";
    private static final String LEFT_ARM = "leftarm";
    private static final String RIGHT_LEG = "rightthigh";
    private static final String LEFT_LEG = "leftthigh";
    private static final float CROUCH_LEAN = 0.5F;

    private final HumanoidModel<LivingEntity> active = createProxy();
    private final HumanoidModel<LivingEntity> rest = createProxy();
    private final boolean animatedSwim;
    private final boolean animatedCrouch;

    public GeoHumanoidPoser(boolean animatedSwim, boolean animatedCrouch) {
        this.animatedSwim = animatedSwim;
        this.animatedCrouch = animatedCrouch;
    }

    private static HumanoidModel<LivingEntity> createProxy() {
        return new HumanoidModel<>(LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 64).bakeRoot());
    }

    public float apply(AnimationProcessor<?> processor, Object animatable, AnimationState<?> state) {
        if (!(animatable instanceof LivingEntity entity)) return 0.0F;
        Player owner = SpeciesRenderHandler.ownerOf(entity);
        if (owner == null) return 0.0F;
        GeoBone rightArm = processor.getBone(RIGHT_ARM);
        GeoBone leftArm = processor.getBone(LEFT_ARM);
        if (rightArm == null || leftArm == null) return 0.0F;

        float partialTick = state.getPartialTick();
        float limbSwing = state.getLimbSwing();
        float limbSwingAmount = state.getLimbSwingAmount();
        float ageInTicks = owner.tickCount + partialTick;
        float headYaw = Mth.wrapDegrees(Mth.rotLerp(partialTick, owner.yHeadRotO, owner.yHeadRot)
                - Mth.rotLerp(partialTick, owner.yBodyRotO, owner.yBodyRot));
        float headPitch = Mth.lerp(partialTick, owner.xRotO, owner.getXRot());

        pose(active, owner, true, partialTick, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        pose(rest, owner, false, partialTick, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);

        float lean = !animatedCrouch && owner.isCrouching() ? CROUCH_LEAN : 0.0F;
        float bodyX = active.body.xRot - rest.body.xRot;
        float bodyY = active.body.yRot - rest.body.yRot;
        float bodyZ = active.body.zRot - rest.body.zRot;
        GeoBone body = processor.getBone(BODY);
        if (body != null) {
            GeoBoneOffsets.apply(body, -(bodyX + lean), -bodyY, bodyZ);
        }
        applyChild(rightArm, active.rightArm, rest.rightArm, body != null ? bodyX : 0.0F, body != null ? bodyY : 0.0F,
                body != null ? bodyZ : 0.0F);
        applyChild(leftArm, active.leftArm, rest.leftArm, body != null ? bodyX : 0.0F, body != null ? bodyY : 0.0F,
                body != null ? bodyZ : 0.0F);
        applyChild(processor.getBone(RIGHT_LEG), active.rightLeg, rest.rightLeg, 0.0F, 0.0F, 0.0F);
        applyChild(processor.getBone(LEFT_LEG), active.leftLeg, rest.leftLeg, 0.0F, 0.0F, 0.0F);
        return lean;
    }

    private static void applyChild(@Nullable GeoBone bone, ModelPart active, ModelPart rest,
                                   float parentX, float parentY, float parentZ) {
        if (bone == null) return;
        float x = active.xRot - rest.xRot - parentX;
        float y = active.yRot - rest.yRot - parentY;
        float z = active.zRot - rest.zRot - parentZ;
        GeoBoneOffsets.apply(bone, -x, -y, z);
    }

    private void pose(HumanoidModel<LivingEntity> model, Player owner, boolean live, float partialTick, float limbSwing,
                      float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        model.attackTime = live ? owner.getAttackAnim(partialTick) : 0.0F;
        model.riding = live && owner.isPassenger() && owner.getVehicle() != null && owner.getVehicle().shouldRiderSit();
        model.young = false;
        model.crouching = false;
        model.swimAmount = live && !animatedSwim ? owner.getSwimAmount(partialTick) : 0.0F;
        HumanoidModel.ArmPose mainPose = live ? HumanoidPoser.armPose(owner, InteractionHand.MAIN_HAND) : HumanoidModel.ArmPose.EMPTY;
        HumanoidModel.ArmPose offPose = live ? HumanoidPoser.armPose(owner, InteractionHand.OFF_HAND) : HumanoidModel.ArmPose.EMPTY;
        if (mainPose.isTwoHanded()) {
            offPose = owner.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
        }
        boolean rightHanded = owner.getMainArm() == HumanoidArm.RIGHT;
        model.rightArmPose = rightHanded ? mainPose : offPose;
        model.leftArmPose = rightHanded ? offPose : mainPose;
        model.setupAnim(owner, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
    }
}
