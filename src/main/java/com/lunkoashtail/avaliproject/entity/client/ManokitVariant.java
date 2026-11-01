package com.lunkoashtail.avaliproject.entity.client;

import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Nullable;

public enum ManokitVariant {
    BEACHDWELLER("beachdweller", 1.0F, false, "Body", null, "Neck",
            "Head", "ArmR", "ArmL", "LegR", "LegL", "Tail", "RightItemPivot", "LeftItemPivot"),
    TRIBAL("tribal", 0.9375F, true, "root", "Body", null,
            "Head", "RightArm", "LeftArm", "RightLeg", "LeftLeg", "Tail", "RightArm", "LeftArm"),
    ARCANE("arcane", 1.0F, false, "Body", null, "Neck",
            "Head", "ArmR", "ArmL", "LegR", "LegL", "Tail", "RightItemPivot", "LeftItemPivot");

    public static final Codec<ManokitVariant> CODEC = Codec.STRING.xmap(ManokitVariant::byId, ManokitVariant::id);
    public static final ManokitVariant DEFAULT = TRIBAL;

    private final String name;
    private final float renderScale;
    private final boolean playerShaped;
    private final String bodyBone;
    @Nullable private final String torsoBone;
    @Nullable private final String neckBone;
    private final String headBone;
    private final String rightArmBone;
    private final String leftArmBone;
    private final String rightLegBone;
    private final String leftLegBone;
    private final String tailBone;
    private final String rightHandBone;
    private final String leftHandBone;

    ManokitVariant(String name, float renderScale, boolean playerShaped, String bodyBone, @Nullable String torsoBone,
                   @Nullable String neckBone, String headBone,
                   String rightArmBone, String leftArmBone, String rightLegBone, String leftLegBone, String tailBone,
                   String rightHandBone, String leftHandBone) {
        this.name = name;
        this.renderScale = renderScale;
        this.playerShaped = playerShaped;
        this.bodyBone = bodyBone;
        this.torsoBone = torsoBone;
        this.neckBone = neckBone;
        this.headBone = headBone;
        this.rightArmBone = rightArmBone;
        this.leftArmBone = leftArmBone;
        this.rightLegBone = rightLegBone;
        this.leftLegBone = leftLegBone;
        this.tailBone = tailBone;
        this.rightHandBone = rightHandBone;
        this.leftHandBone = leftHandBone;
    }

    public String id() {
        return name;
    }

    public String geoPath() {
        return "geo/manokit_" + name + ".geo.json";
    }

    public String texturePath() {
        return "textures/entity/manokit/" + name + "/uv/manokit_" + name + ".png";
    }

    public float renderScale() {
        return renderScale;
    }

    public boolean playerShaped() {
        return playerShaped;
    }

    public boolean paddleSwims() {
        return !playerShaped;
    }

    public float firstPersonArmTwist() {
        return playerShaped ? 0.0F : -90.0F;
    }

    public String bodyBone() {
        return bodyBone;
    }

    @Nullable
    public String torsoBone() {
        return torsoBone;
    }

    @Nullable
    public String neckBone() {
        return neckBone;
    }

    public String headBone() {
        return headBone;
    }

    public String rightArmBone() {
        return rightArmBone;
    }

    public String leftArmBone() {
        return leftArmBone;
    }

    public String rightLegBone() {
        return rightLegBone;
    }

    public String leftLegBone() {
        return leftLegBone;
    }

    public String tailBone() {
        return tailBone;
    }

    public String rightHandBone() {
        return rightHandBone;
    }

    public String leftHandBone() {
        return leftHandBone;
    }

    @Nullable
    public static ManokitVariant byIdOrNull(String id) {
        for (ManokitVariant variant : values()) {
            if (variant.name.equals(id)) return variant;
        }
        return null;
    }

    public static ManokitVariant byId(String id) {
        for (ManokitVariant variant : values()) {
            if (variant.name.equals(id)) return variant;
        }
        return DEFAULT;
    }
}
