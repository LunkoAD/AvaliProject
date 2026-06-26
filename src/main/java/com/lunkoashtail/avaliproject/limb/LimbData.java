package com.lunkoashtail.avaliproject.limb;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Arrays;

/**
 * Holds per-limb bleeding values for all six limbs simultaneously.
 *
 * Bleed values are integers in [0, MAX_BLEED].  0 = healthy, MAX_BLEED = critical.
 * The class is intentionally mutable: NeoForge stores one shared instance per player
 * via AttachmentType, and in-place mutation is the intended pattern.
 *
 * Expandable: adding new effect fields (pain, fracture, infection) is straightforward —
 * add an int[] array indexed by Limb.ordinal() and a matching codec entry.
 */
public class LimbData {

    /** Ceiling for bleed values — clamp all writes to this. */
    public static final int MAX_BLEED = 100;

    // Indexed by Limb.ordinal()
    private final int[] bleedValues = new int[Limb.values().length];

    /** Default constructor — all limbs healthy (0 bleed). */
    public LimbData() {}

    /**
     * Full constructor used by the codec during deserialization.
     * Parameter order must match the codec field order below.
     */
    public LimbData(int head, int leftArm, int rightArm, int back, int leftLeg, int rightLeg) {
        bleedValues[Limb.HEAD.ordinal()]      = clamp(head);
        bleedValues[Limb.LEFT_ARM.ordinal()]  = clamp(leftArm);
        bleedValues[Limb.RIGHT_ARM.ordinal()] = clamp(rightArm);
        bleedValues[Limb.BACK.ordinal()]      = clamp(back);
        bleedValues[Limb.LEFT_LEG.ordinal()]  = clamp(leftLeg);
        bleedValues[Limb.RIGHT_LEG.ordinal()] = clamp(rightLeg);
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public int getBleed(Limb limb) {
        return bleedValues[limb.ordinal()];
    }

    public void setBleed(Limb limb, int value) {
        bleedValues[limb.ordinal()] = clamp(value);
    }

    /** Adds {@code amount} bleed to {@code limb}, clamped to MAX_BLEED. */
    public void addBleed(Limb limb, int amount) {
        setBleed(limb, getBleed(limb) + amount);
    }

    /** Removes {@code amount} bleed from {@code limb}, floored at 0. */
    public void reduceBleed(Limb limb, int amount) {
        setBleed(limb, getBleed(limb) - amount);
    }

    /** Sum of all six limb bleed values. */
    public int getTotalBleed() {
        int total = 0;
        for (int v : bleedValues) total += v;
        return total;
    }

    /** Returns true when at least one limb has bleed > 0. */
    public boolean isAnyBleeding() {
        for (int v : bleedValues) if (v > 0) return true;
        return false;
    }

    // -------------------------------------------------------------------------
    // Codec — used by AttachmentType for world-save persistence
    // -------------------------------------------------------------------------

    /**
     * Uses optionalFieldOf so old save data without a field defaults to 0 rather
     * than failing to parse.  The constructor parameter order matches the group order.
     */
    public static final Codec<LimbData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.optionalFieldOf("head",      0).forGetter(d -> d.getBleed(Limb.HEAD)),
            Codec.INT.optionalFieldOf("left_arm",  0).forGetter(d -> d.getBleed(Limb.LEFT_ARM)),
            Codec.INT.optionalFieldOf("right_arm", 0).forGetter(d -> d.getBleed(Limb.RIGHT_ARM)),
            Codec.INT.optionalFieldOf("back",      0).forGetter(d -> d.getBleed(Limb.BACK)),
            Codec.INT.optionalFieldOf("left_leg",  0).forGetter(d -> d.getBleed(Limb.LEFT_LEG)),
            Codec.INT.optionalFieldOf("right_leg", 0).forGetter(d -> d.getBleed(Limb.RIGHT_LEG))
        ).apply(instance, LimbData::new)
    );

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private static int clamp(int v) {
        return Math.max(0, Math.min(MAX_BLEED, v));
    }

    @Override
    public String toString() {
        return "LimbData" + Arrays.toString(bleedValues);
    }
}
