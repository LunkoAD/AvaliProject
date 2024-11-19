package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.PenguinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class PenguinModel extends HierarchicalModel<PenguinEntity> {
    private final ModelPart penguin;
    private final ModelPart head;
    public PenguinModel(ModelPart root) {
        this.penguin = root.getChild("penguin");
        this.head = penguin.getChild("upper_body_group").getChild("body").getChild("head_group");
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition penguin = partdefinition.addOrReplaceChild("penguin", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));
        PartDefinition upper_body_group = penguin.addOrReplaceChild("upper_body_group", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition body = upper_body_group.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 25).addBox(-6.0F, -6.0F, -5.0F, 12.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition leftwing = body.addOrReplaceChild("leftwing", CubeListBuilder.create(), PartPose.offset(-6.0F, -4.0F, 0.0F));
        PartDefinition wing_r1 = leftwing.addOrReplaceChild("wing_r1", CubeListBuilder.create().texOffs(48, 23).addBox(-12.0F, -14.0F, 1.0F, 6.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 13.0F, 9.0F, 0.0F, -1.5708F, 0.0F));
        PartDefinition rightwing = body.addOrReplaceChild("rightwing", CubeListBuilder.create(), PartPose.offset(6.0F, -4.0F, 0.0F));
        PartDefinition wing_r2 = rightwing.addOrReplaceChild("wing_r2", CubeListBuilder.create().texOffs(48, 23).addBox(-3.0F, -6.5F, -1.0F, 6.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.5F, 0.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition head_group = body.addOrReplaceChild("head_group", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));
        PartDefinition headmain = head_group.addOrReplaceChild("headmain", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -7.0F, -4.0F, 10.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(28, 7).addBox(-5.0F, -7.0F, -4.0F, 10.0F, 7.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cube_r1 = headmain.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 1).addBox(1.0F, -4.0F, -1.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, 0.0F, 0.0F, 0.4363F, 0.0F));
        PartDefinition beak = head_group.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(8, 15).addBox(-1.0F, 1.0F, -8.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition feet = penguin.addOrReplaceChild("feet", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));
        PartDefinition righty = feet.addOrReplaceChild("righty", CubeListBuilder.create().texOffs(45, 39).mirror().addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 0.0F, 0.0F));
        PartDefinition lefty = feet.addOrReplaceChild("lefty", CubeListBuilder.create().texOffs(45, 39).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    @Override
    public void setupAnim(PenguinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);
        this.animateWalk(PenguinAnimations.ANIM_PENGUIN_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, PenguinAnimations.ANIM_PENGUIN_IDLE, ageInTicks, 1f);
    }
    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);
        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        penguin.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
    @Override
    public ModelPart root() {
        return penguin;
    }
}