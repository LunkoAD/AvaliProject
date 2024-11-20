package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.entity.custom.SkskceegehkjaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SkskceegehkjaModel extends HierarchicalModel<SkskceegehkjaEntity> {
    private final ModelPart Skskceegehkja;
    private final ModelPart Body;
    private final ModelPart TorsoGroup;
    private final ModelPart Torso;
    private final ModelPart TorsoLayer;
    private final ModelPart TorsoBottom;
    private final ModelPart Tail;
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart HeadGroup;
    private final ModelPart Head;
    private final ModelPart Vibrissae;
    private final ModelPart LeftVibrissae;
    private final ModelPart RightVibrissae;
    private final ModelPart Whiskers;
    private final ModelPart LeftWhisker;
    private final ModelPart RightWhisker;
    private final ModelPart Horns;
    private final ModelPart LeftHorn;
    private final ModelPart RightHorn;
    private final ModelPart Legs;
    private final ModelPart FrontLeftLeg;
    private final ModelPart FrontRightLeg;
    private final ModelPart BackLeftLeg;
    private final ModelPart BackRightLeg;

    public SkskceegehkjaModel (ModelPart root) {
        this.Skskceegehkja = root.getChild("Skskceegehkja");
        this.Body = this.Skskceegehkja.getChild("Body");
        this.TorsoGroup = this.Body.getChild("TorsoGroup");
        this.Torso = this.TorsoGroup.getChild("Torso");
        this.TorsoLayer = this.TorsoGroup.getChild("TorsoLayer");
        this.TorsoBottom = this.TorsoGroup.getChild("TorsoBottom");
        this.Tail = this.Body.getChild("Tail");
        this.bone = this.Tail.getChild("bone");
        this.bone2 = this.bone.getChild("bone2");
        this.HeadGroup = this.Body.getChild("HeadGroup");
        this.Head = this.HeadGroup.getChild("Head");
        this.Vibrissae = this.HeadGroup.getChild("Vibrissae");
        this.LeftVibrissae = this.Vibrissae.getChild("LeftVibrissae");
        this.RightVibrissae = this.Vibrissae.getChild("RightVibrissae");
        this.Whiskers = this.HeadGroup.getChild("Whiskers");
        this.LeftWhisker = this.Whiskers.getChild("LeftWhisker");
        this.RightWhisker = this.Whiskers.getChild("RightWhisker");
        this.Horns = this.HeadGroup.getChild("Horns");
        this.LeftHorn = this.Horns.getChild("LeftHorn");
        this.RightHorn = this.Horns.getChild("RightHorn");
        this.Legs = this.Skskceegehkja.getChild("Legs");
        this.FrontLeftLeg = this.Legs.getChild("FrontLeftLeg");
        this.FrontRightLeg = this.Legs.getChild("FrontRightLeg");
        this.BackLeftLeg = this.Legs.getChild("BackLeftLeg");
        this.BackRightLeg = this.Legs.getChild("BackRightLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Skskceegehkja = partdefinition.addOrReplaceChild("Skskceegehkja", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Body = Skskceegehkja.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 21.0F));

        PartDefinition TorsoGroup = Body.addOrReplaceChild("TorsoGroup", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Torso = TorsoGroup.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -19.0F, -28.0F, 22.0F, 23.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(74, 64).addBox(-10.0F, -17.0F, -13.0F, 20.0F, 21.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-11.0F, -19.0F, -1.0F, 22.0F, 23.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -14.0F));

        PartDefinition TorsoLayer = TorsoGroup.addOrReplaceChild("TorsoLayer", CubeListBuilder.create().texOffs(74, 0).addBox(-11.5F, -19.5F, -42.5F, 23.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 76).addBox(-10.5F, -17.5F, -26.5F, 21.0F, 14.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(74, 32).addBox(-11.5F, -19.5F, -15.5F, 23.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition TorsoBottom = TorsoGroup.addOrReplaceChild("TorsoBottom", CubeListBuilder.create().texOffs(0, 112).addBox(-11.0F, 5.0F, -28.0F, 22.0F, 0.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(74, 112).addBox(-10.0F, 5.0F, -13.0F, 20.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(64, 97).addBox(-11.0F, 5.0F, -1.0F, 22.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -14.0F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(138, 64).addBox(-4.5F, 0.0F, -1.0F, 8.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -18.0F, 0.0F));

        PartDefinition bone = Tail.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(116, 124).addBox(-5.5F, 0.0F, 0.0F, 10.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 11.0F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 127).addBox(-5.5F, -2.0F, 0.0F, 11.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 106).addBox(-4.0F, -2.0F, 11.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 2.0F, 14.0F));

        PartDefinition HeadGroup = Body.addOrReplaceChild("HeadGroup", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, -42.0F));

        PartDefinition Head = HeadGroup.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Head_r1 = Head.addOrReplaceChild("Head_r1", CubeListBuilder.create().texOffs(44, 127).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 18.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(74, 124).addBox(-6.0F, -4.0F, -7.0F, 12.0F, 19.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition Vibrissae = HeadGroup.addOrReplaceChild("Vibrissae", CubeListBuilder.create(), PartPose.offset(-2.0F, -2.766F, -5.6876F));

        PartDefinition LeftVibrissae = Vibrissae.addOrReplaceChild("LeftVibrissae", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Plane_r1 = LeftVibrissae.addOrReplaceChild("Plane_r1", CubeListBuilder.create().texOffs(138, 78).addBox(0.0F, -3.0F, -10.0F, 0.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, -0.1329F, -0.173F, 0.023F));

        PartDefinition RightVibrissae = Vibrissae.addOrReplaceChild("RightVibrissae", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Plane_r2 = RightVibrissae.addOrReplaceChild("Plane_r2", CubeListBuilder.create().texOffs(138, 78).mirror().addBox(0.0F, -3.0F, -10.0F, 0.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1329F, 0.173F, -0.023F));

        PartDefinition Whiskers = HeadGroup.addOrReplaceChild("Whiskers", CubeListBuilder.create(), PartPose.offset(-2.0F, -2.766F, -5.6876F));

        PartDefinition LeftWhisker = Whiskers.addOrReplaceChild("LeftWhisker", CubeListBuilder.create(), PartPose.offset(-2.0F, 14.0118F, 0.1641F));

        PartDefinition Whiskers_r1 = LeftWhisker.addOrReplaceChild("Whiskers_r1", CubeListBuilder.create().texOffs(0, 101).addBox(-2.0F, 0.0F, -3.0F, 10.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, 0.0783F, 0.6566F, 0.5979F));

        PartDefinition RightWhisker = Whiskers.addOrReplaceChild("RightWhisker", CubeListBuilder.create(), PartPose.offset(6.0F, 14.0118F, 0.1641F));

        PartDefinition Whiskers_r2 = RightWhisker.addOrReplaceChild("Whiskers_r2", CubeListBuilder.create().texOffs(0, 101).mirror().addBox(-8.0F, 0.0F, -3.0F, 10.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0783F, -0.6566F, -0.5979F));

        PartDefinition Horns = HeadGroup.addOrReplaceChild("Horns", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition LeftHorn = Horns.addOrReplaceChild("LeftHorn", CubeListBuilder.create(), PartPose.offset(6.2805F, 8.6224F, -10.029F));

        PartDefinition cube_r1 = LeftHorn.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(70, 88).addBox(0.0F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5411F, 1.0911F, -0.5534F));

        PartDefinition cube_r2 = LeftHorn.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 76).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4856F, -4.183F, -0.7188F, -0.3832F, 1.1302F, -0.3773F));

        PartDefinition cube_r3 = LeftHorn.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(140, 147).addBox(-3.0F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2955F, -8.1242F, 0.1062F, -0.6711F, 1.0413F, -0.7018F));

        PartDefinition cube_r4 = LeftHorn.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(140, 140).addBox(-3.0F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7036F, -10.9971F, 2.7039F, -1.0237F, 0.7068F, -1.1521F));

        PartDefinition cube_r5 = LeftHorn.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(138, 110).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0305F, -10.3799F, 5.7219F, -1.1514F, 0.24F, -1.4232F));

        PartDefinition RightHorn = Horns.addOrReplaceChild("RightHorn", CubeListBuilder.create(), PartPose.offset(-6.2805F, 8.6224F, -10.029F));

        PartDefinition cube_r6 = RightHorn.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(70, 88).mirror().addBox(-1.0F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5411F, -1.0911F, 0.5534F));

        PartDefinition cube_r7 = RightHorn.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(64, 76).mirror().addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.4856F, -4.183F, -0.7188F, -0.3832F, -1.1302F, 0.3773F));

        PartDefinition cube_r8 = RightHorn.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(140, 147).mirror().addBox(0.0F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2955F, -8.1242F, 0.1062F, -0.6711F, -1.0413F, 0.7018F));

        PartDefinition cube_r9 = RightHorn.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(140, 140).mirror().addBox(0.0F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.7036F, -10.9971F, 2.7039F, -1.0237F, -0.7068F, 1.1521F));

        PartDefinition cube_r10 = RightHorn.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(138, 110).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0305F, -10.3799F, 5.7219F, -1.1514F, -0.24F, 1.4232F));

        PartDefinition Legs = Skskceegehkja.addOrReplaceChild("Legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition FrontLeftLeg = Legs.addOrReplaceChild("FrontLeftLeg", CubeListBuilder.create().texOffs(16, 140).addBox(0.0F, -6.0F, -3.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(64, 92).addBox(1.0F, 1.0F, -4.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -3.0F, -16.0F));

        PartDefinition Toe_r1 = FrontLeftLeg.addOrReplaceChild("Toe_r1", CubeListBuilder.create().texOffs(22, 106).addBox(0.0F, -1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -2.25F, 0.0F, 0.3054F, 0.0F));

        PartDefinition Toe_r2 = FrontLeftLeg.addOrReplaceChild("Toe_r2", CubeListBuilder.create().texOffs(64, 88).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, -2.25F, 0.0F, -0.3054F, 0.0F));

        PartDefinition FrontRightLeg = Legs.addOrReplaceChild("FrontRightLeg", CubeListBuilder.create().texOffs(16, 140).mirror().addBox(-3.0F, -6.0F, -3.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 92).mirror().addBox(-2.0F, 1.0F, -4.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, -3.0F, -16.0F));

        PartDefinition Toe_r3 = FrontRightLeg.addOrReplaceChild("Toe_r3", CubeListBuilder.create().texOffs(22, 106).mirror().addBox(-1.0F, -1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.0F, -2.25F, 0.0F, -0.3054F, 0.0F));

        PartDefinition Toe_r4 = FrontRightLeg.addOrReplaceChild("Toe_r4", CubeListBuilder.create().texOffs(64, 88).mirror().addBox(0.0F, -1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 2.0F, -2.25F, 0.0F, 0.3054F, 0.0F));

        PartDefinition BackLeftLeg = Legs.addOrReplaceChild("BackLeftLeg", CubeListBuilder.create().texOffs(28, 140).addBox(0.0F, -6.0F, -3.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 106).addBox(1.0F, 1.0F, -4.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -3.0F, 19.0F));

        PartDefinition Toe_r5 = BackLeftLeg.addOrReplaceChild("Toe_r5", CubeListBuilder.create().texOffs(34, 106).addBox(0.0F, -1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -2.25F, 0.0F, 0.3054F, 0.0F));

        PartDefinition Toe_r6 = BackLeftLeg.addOrReplaceChild("Toe_r6", CubeListBuilder.create().texOffs(40, 106).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, -2.25F, 0.0F, -0.3054F, 0.0F));

        PartDefinition BackRightLeg = Legs.addOrReplaceChild("BackRightLeg", CubeListBuilder.create().texOffs(28, 140).mirror().addBox(-3.0F, -6.0F, -3.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(28, 106).mirror().addBox(-2.0F, 1.0F, -4.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, -3.0F, 19.0F));

        PartDefinition Toe_r7 = BackRightLeg.addOrReplaceChild("Toe_r7", CubeListBuilder.create().texOffs(40, 106).mirror().addBox(0.0F, -1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 2.0F, -2.25F, 0.0F, 0.3054F, 0.0F));

        PartDefinition Toe_r8 = BackRightLeg.addOrReplaceChild("Toe_r8", CubeListBuilder.create().texOffs(34, 106).mirror().addBox(-1.0F, -1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.0F, -2.25F, 0.0F, -0.3054F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(SkskceegehkjaEntity Entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        Skskceegehkja.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return Skskceegehkja;
    }

}

