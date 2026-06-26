package com.lunkoashtail.avaliproject.limb;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Four tiers of bleeding severity, mapped to bleed value ranges of 25 points each.
 *
 * ── Tier ranges (out of LimbData.MAX_BLEED = 100) ───────────────────────────
 *   MINOR_BLEEDING        1 – 25   (light wound, manageable)
 *   BLEEDING             26 – 50   (open wound, needs attention)
 *   HEAVY_BLEEDING       51 – 75   (serious, act fast)
 *   CATASTROPHIC_BLEEDING 76 – 100  (life-threatening)
 *
 * ── Damage per tick interval (BLEED_TICK_INTERVAL = 40 ticks = 2 seconds) ───
 *   Values are PER LIMB — multiple bleeding limbs stack additively.
 *   Example: two CATASTROPHIC limbs = 10 HP lost every 2 seconds.
 *
 * ── Icon textures ────────────────────────────────────────────────────────────
 *   Place 16×16 PNG files at:
 *     src/main/resources/assets/avaliproject/textures/gui/effect/
 *       minor_bleeding_effect.png
 *       bleeding_effect.png
 *       heavy_bleeding_effect.png
 *       catastrophic_bleeding_effect.png
 *
 * ── Expandability ────────────────────────────────────────────────────────────
 *   To add a fifth tier, append it here and adjust the boundary in fromBleedValue().
 *   All other code (LimbSelectionScreen, BleedingEventHandler) uses fromBleedValue()
 *   and does not need to change.
 */
public enum BleedingTier {

    // name key           minBleed  maxBleed  damagePerInterval  icon file stem
    MINOR_BLEEDING        (1,   25,  0.5f,  "minor_bleeding_effect"),
    BLEEDING              (26,  50,  1.5f,  "bleeding_effect"),
    HEAVY_BLEEDING        (51,  75,  3.0f,  "heavy_bleeding_effect"),
    CATASTROPHIC_BLEEDING (76, 100,  5.0f,  "catastrophic_bleeding_effect");

    /** Lower bound of the bleed range that maps to this tier (inclusive). */
    public final int minBleed;
    /** Upper bound of the bleed range that maps to this tier (inclusive). */
    public final int maxBleed;
    /**
     * HP lost every BLEED_TICK_INTERVAL server ticks while this limb is at this tier.
     * Stacks additively with other bleeding limbs.
     */
    public final float damagePerInterval;
    /**
     * Texture path for the effect icon shown in LimbSelectionScreen.
     * Full path: assets/avaliproject/textures/gui/effect/<stem>.png
     */
    public final ResourceLocation icon;

    BleedingTier(int minBleed, int maxBleed, float damagePerInterval, String iconStem) {
        this.minBleed           = minBleed;
        this.maxBleed           = maxBleed;
        this.damagePerInterval  = damagePerInterval;
        this.icon = ResourceLocation.fromNamespaceAndPath(
                AvaliProject.MOD_ID, "textures/gui/effect/" + iconStem + ".png");
    }

    // -------------------------------------------------------------------------
    // Lookup
    // -------------------------------------------------------------------------

    /**
     * Returns the tier corresponding to the given bleed value, or null when bleed == 0.
     *
     * Tier boundaries are evenly spaced (25 points each) matching the MIN/MAX fields above.
     * The method iterates in declaration order so the first matching tier wins,
     * which is correct since ranges are non-overlapping and ascending.
     */
    @Nullable
    public static BleedingTier fromBleedValue(int bleed) {
        if (bleed <= 0) return null;
        for (BleedingTier tier : values()) {
            if (bleed <= tier.maxBleed) return tier;
        }
        // Safety fallback for values above MAX_BLEED (shouldn't happen after clamping)
        return CATASTROPHIC_BLEEDING;
    }

    // -------------------------------------------------------------------------
    // Display
    // -------------------------------------------------------------------------

    public Component getDisplayName() {
        return Component.translatable("bleeding_tier.avaliproject." + name().toLowerCase());
    }

    /**
     * Returns an ARGB colour suitable for text / UI tinting at this tier.
     * Colours progress from yellow → orange → red → dark red.
     */
    public int getColor() {
        return switch (this) {
            case MINOR_BLEEDING        -> 0xFFFFDD44; // yellow
            case BLEEDING              -> 0xFFFF8833; // orange
            case HEAVY_BLEEDING        -> 0xFFFF3333; // red
            case CATASTROPHIC_BLEEDING -> 0xFF990000; // dark red
        };
    }
}
