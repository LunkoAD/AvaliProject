package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.network.ReduceBleedPayload;
import com.lunkoashtail.avaliproject.sound.ModSounds;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Random;

/**
 * BandageMinigameScreen — an interactive wound-wrapping minigame.
 *
 * HOW IT WORKS:
 *  - A limb silhouette is shown in the centre of the screen.
 *  - The player must hold LEFT MOUSE and move in a circular orbit around
 *    the limb centre to "wrap" a bandage.
 *  - Consistent circular motion accumulates coverage (0 → 100%).
 *    Three complete revolutions fill it to 100%.
 *  - Reversing direction penalises progress.
 *  - Bad status effects (Nausea, Poison, Wither, low health) add random
 *    jitter to the effective cursor position, making it harder to wrap neatly.
 *  - Reaching 80% coverage triggers success: bleed is reduced and the screen
 *    closes after a short delay.
 *
 * TEXTURES (optional — game works with primitives if files are absent):
 *  Place 16×16 or larger PNGs at:
 *    assets/avaliproject/textures/gui/bandage/limb.png
 *    assets/avaliproject/textures/gui/bandage/bandage_roll.png
 *    assets/avaliproject/textures/gui/bandage/bandage_layer_1.png  (first wrap)
 *    assets/avaliproject/textures/gui/bandage/bandage_layer_2.png  (second wrap)
 *    assets/avaliproject/textures/gui/bandage/bandage_layer_3.png  (tight finish)
 */
public class BandageMinigameScreen extends Screen {

    // -------------------------------------------------------------------------
    // Texture resource locations (drop PNG files here to enable them)
    // -------------------------------------------------------------------------
    private static final ResourceLocation TEX_LIMB =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/gui/bandage/limb.png");
    private static final ResourceLocation TEX_ROLL =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/gui/bandage/bandage_roll.png");
    private static final ResourceLocation TEX_LAYER_1 =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/gui/bandage/bandage_layer_1.png");
    private static final ResourceLocation TEX_LAYER_2 =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/gui/bandage/bandage_layer_2.png");
    private static final ResourceLocation TEX_LAYER_3 =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/gui/bandage/bandage_layer_3.png");

    // -------------------------------------------------------------------------
    // Minigame tuning constants
    // -------------------------------------------------------------------------

    /** Coverage fraction (0–1) required to succeed. */
    private static final float SUCCESS_THRESHOLD = 0.80f;

    /**
     * Total radians of consistent circular motion needed to fill coverage to 1.0.
     * 3 full revolutions = 3 × 2π ≈ 18.85 rad.
     */
    private static final float RADS_FOR_FULL_COVERAGE = (float) (3.0 * 2.0 * Math.PI);

    /** Mouse must be at least this many pixels from limb centre to count as wrapping. */
    private static final float MIN_WRAP_RADIUS = 14f;

    /** Mouse farther than this is too wild to count as wrapping. */
    private static final float MAX_WRAP_RADIUS_MULT = 8f; // multiplied by LIMB_W

    // -------------------------------------------------------------------------
    // Visual constants
    // -------------------------------------------------------------------------
    private static final int LIMB_W = 44;
    private static final int LIMB_H = 110;

    private static final int COL_SKIN       = 0xFFD4A574;
    private static final int COL_SKIN_SHADE = 0x55000000;
    private static final int COL_SKIN_LIGHT = 0x22FFFFFF;
    private static final int COL_WOUND      = 0xFF8B0000;
    private static final int COL_LINEN      = 0xCCEDE0CE;
    private static final int COL_LINEN_LINE = 0x55907050;
    private static final int COL_BORDER     = 0xFF334466;
    private static final int COL_PANEL      = 0xBB080812;

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------
    private final Limb limb;
    private final int initialBleed;

    // Wrapping progress
    private float coverage = 0f;   // 0.0 → 1.0
    private boolean lmbHeld = false;
    private double lastAngle = Double.NaN;
    private int wrapDir = 0;       // +1 CW, -1 CCW, 0 undecided
    private int streak = 0;        // consecutive frames with consistent direction

    // Shake (updated every tick)
    private final Random rng = new Random();
    private float shakeX, shakeY;

    // Limb centre on screen (computed in init())
    private int limbCX, limbCY;

    // Completion state
    private boolean succeeded = false;
    private int closeCountdown = 0;  // counts down in tick(); 0 = don't auto-close

    // Wrapping particles for visual flair [x, y, vx, vy]
    private static final int MAX_PARTICLES = 32;
    private final float[][] particles = new float[MAX_PARTICLES][4];
    private int particleCount = 0;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    public BandageMinigameScreen(Limb limb, int initialBleed) {
        super(Component.translatable("screen.avaliproject.bandage_minigame"));
        this.limb = limb;
        this.initialBleed = initialBleed;
    }

    @Override
    protected void init() {
        super.init();
        limbCX = width / 2;
        limbCY = height / 2 + 5;
    }

    // =========================================================================
    // Rendering
    // =========================================================================

    @Override
    public void renderBackground(GuiGraphics gfx, int mx, int my, float partial) {
        // Override to do nothing — prevents MC 1.21.1 from applying its blur post-effect.
        // Our dark overlay is drawn in render() instead.
    }

    @Override
    public void render(GuiGraphics gfx, int mx, int my, float partial) {
        gfx.fill(0, 0, width, height, 0xCC000000);
        drawStatsPanel(gfx);
        drawLimb(gfx);
        drawWound(gfx);
        drawBandageLayers(gfx);
        drawParticles(gfx);
        drawBandageRollCursor(gfx, mx + (int) shakeX, my + (int) shakeY);
        drawHUD(gfx);
        super.render(gfx, mx, my, partial);
    }

    /** Left-side dark panel for stats. */
    private void drawStatsPanel(GuiGraphics gfx) {
        int pw = 130, ph = 100;
        int px = 8, py = height / 2 - ph / 2;
        gfx.fill(px, py, px + pw, py + ph, COL_PANEL);
        // Border lines
        gfx.fill(px,          py,          px + pw,     py + 1,      COL_BORDER);
        gfx.fill(px,          py + ph - 1, px + pw,     py + ph,     COL_BORDER);
        gfx.fill(px,          py,          px + 1,      py + ph,     COL_BORDER);
        gfx.fill(px + pw - 1, py,          px + pw,     py + ph,     COL_BORDER);
    }

    private void drawLimb(GuiGraphics gfx) {
        int x = limbCX - LIMB_W / 2;
        int y = limbCY - LIMB_H / 2;

        // Colour palette for black fur
        final int BASE    = 0xFF0E0E0E; // main body fill
        final int MID     = 0xFF181818; // slightly lighter mid-tone
        final int HILIGHT = 0xFF262626; // subtle edge highlight (light catching fur tips)
        final int DEEP    = 0xFF050505; // deepest shadow on the inner curve
        final int FUR1    = 0xFF1C1C1C; // shorter strand
        final int FUR2    = 0xFF282828; // longer strand tip

        // --- Rounded cylinder body ---
        gfx.fill(x + 2,  y + 5,      x + LIMB_W - 2,  y + LIMB_H - 5,  BASE);
        gfx.fill(x,      y + 12,     x + LIMB_W,       y + LIMB_H - 12, BASE);
        // Top cap
        gfx.fill(x + 5,  y + 2,      x + LIMB_W - 5,  y + 5,            BASE);
        gfx.fill(x + 9,  y,          x + LIMB_W - 9,  y + 2,            BASE);
        // Bottom cap
        gfx.fill(x + 5,  y + LIMB_H - 5,  x + LIMB_W - 5,  y + LIMB_H - 2, BASE);
        gfx.fill(x + 9,  y + LIMB_H - 2,  x + LIMB_W - 9,  y + LIMB_H,     BASE);

        // --- Roundness shading: dark groove down the centre ---
        gfx.fill(x + LIMB_W / 2 - 4, y + 4, x + LIMB_W / 2 + 4, y + LIMB_H - 4, DEEP);
        // Slight mid-tone on the lit (right) side
        gfx.fill(x + LIMB_W - 10, y + 8, x + LIMB_W - 3, y + LIMB_H - 8, MID);
        // Thin highlight streak on the far right edge
        gfx.fill(x + LIMB_W - 4, y + 10, x + LIMB_W - 2, y + LIMB_H - 10, HILIGHT);

        // --- Fur strands on the left edge ---
        // Alternate short/long strands every 3 px for a natural look
        for (int fy = y + 6; fy < y + LIMB_H - 6; fy += 3) {
            int rel = (fy - y);
            boolean longStrand = (rel % 9 == 0);
            int len = longStrand ? 4 : 2;
            int col = longStrand ? FUR2 : FUR1;
            gfx.fill(x - len, fy, x, fy + 1, col);
            // extra pixel to soften the fur tip
            if (longStrand) gfx.fill(x - len - 1, fy, x - len, fy + 1, DEEP);
        }

        // --- Fur strands on the right edge ---
        for (int fy = y + 6; fy < y + LIMB_H - 6; fy += 3) {
            int rel = (fy - y);
            boolean longStrand = (rel % 9 == 3); // offset so left/right don't align
            int len = longStrand ? 4 : 2;
            int col = longStrand ? FUR2 : FUR1;
            gfx.fill(x + LIMB_W, fy, x + LIMB_W + len, fy + 1, col);
            if (longStrand) gfx.fill(x + LIMB_W + len, fy, x + LIMB_W + len + 1, fy + 1, DEEP);
        }

        // --- Fur tufts at the top cap ---
        for (int fx = x + 9; fx < x + LIMB_W - 9; fx += 4) {
            boolean tall = ((fx - x) % 8 == 1);
            int tuftH = tall ? 3 : 2;
            gfx.fill(fx, y - tuftH, fx + 2, y, tall ? FUR2 : FUR1);
        }

        // --- Fur tufts at the bottom cap ---
        for (int fx = x + 9; fx < x + LIMB_W - 9; fx += 4) {
            boolean tall = ((fx - x) % 8 == 1);
            int tuftH = tall ? 3 : 2;
            gfx.fill(fx, y + LIMB_H, fx + 2, y + LIMB_H + tuftH, tall ? FUR2 : FUR1);
        }
    }

    /** Red wound marker; fades out as bandage coverage increases. */
    private void drawWound(GuiGraphics gfx) {
        if (coverage >= 0.30f) return; // hidden under early bandage wraps
        int alpha = (int) (0xCC * (1f - coverage / 0.30f));
        int woundCol = (alpha << 24) | (COL_WOUND & 0x00FFFFFF);
        gfx.fill(limbCX - 7, limbCY - 4, limbCX + 7, limbCY + 4, woundCol);
        // Small scratch marks
        gfx.fill(limbCX - 12, limbCY - 2, limbCX - 7, limbCY - 1, (alpha << 24) | 0x5A0000);
        gfx.fill(limbCX + 6,  limbCY + 1, limbCX + 11, limbCY + 2, (alpha << 24) | 0x5A0000);
    }

    /**
     * Draws white linen bandage wrap on the limb, filling from bottom upward as
     * coverage grows. Diagonal line pattern simulates the spiral wrap texture.
     * Replace with layered blit calls once you have the layer PNG assets.
     */
    private void drawBandageLayers(GuiGraphics gfx) {
        if (coverage <= 0f) return;

        int x = limbCX - LIMB_W / 2 - 2; // 2px wider than limb on each side
        int bW = LIMB_W + 4;
        int y = limbCY - LIMB_H / 2;

        // Height of the visible bandage region (fills upward from bottom)
        int filledPx = (int) (LIMB_H * Math.min(coverage, 1f));
        int bY = y + LIMB_H - filledPx; // top of bandage rect

        // Base linen fill
        gfx.fill(x, bY, x + bW, y + LIMB_H, COL_LINEN);

        // Diagonal wrap lines — alternating horizontal offset creates spiral illusion
        int lineSpacing = 7;
        for (int row = 0; row < filledPx; row += lineSpacing) {
            int lineY = bY + row;
            int offset = ((row / lineSpacing) % 2) * 5;
            gfx.fill(x + offset, lineY, x + bW - offset, lineY + 2, COL_LINEN_LINE);
        }

        // Edge highlights on the bandage roll edges
        gfx.fill(x,          bY, x + 1,      y + LIMB_H, 0x33FFFFFF);
        gfx.fill(x + bW - 1, bY, x + bW,     y + LIMB_H, 0x33FFFFFF);

        // High-coverage tint: yellowed, tight wraps
        if (coverage > 0.65f) {
            int tintAlpha = (int) ((coverage - 0.65f) / 0.35f * 30);
            gfx.fill(x, bY, x + bW, y + LIMB_H, (tintAlpha << 24) | 0xC8A060);
        }

        // Success flash: briefly brighten the entire bandage
        if (succeeded && closeCountdown > 60) {
            int flash = (int) ((closeCountdown - 60) / 20f * 0x66);
            gfx.fill(x, bY, x + bW, y + LIMB_H, (flash << 24) | 0xFFFFFF);
        }
    }

    /** Tiny cloth particles spawned during active wrapping. */
    private void drawParticles(GuiGraphics gfx) {
        for (int i = 0; i < particleCount; i++) {
            float spd = Math.abs(particles[i][2]) + Math.abs(particles[i][3]);
            int alpha = (int) Math.min(180, spd * 90);
            if (alpha < 5) continue;
            int px = (int) particles[i][0];
            int py = (int) particles[i][1];
            gfx.fill(px, py, px + 2, py + 2, (alpha << 24) | 0xE8D8A8);
        }
    }

    /**
     * Draws the bandage-roll cursor sprite at the (possibly shaken) mouse position,
     * and an arc of dots that visualises the current wrap direction and progress.
     *
     * Replace the dot-ring with gfx.blit(TEX_ROLL, ...) once you have the PNG asset.
     */
    private void drawBandageRollCursor(GuiGraphics gfx, int mx, int my) {
        // Outer dot-ring to represent the bandage roll
        int r = 7;
        int ringCol = lmbHeld ? 0xFFEED8A0 : 0x99EED8A0;
        for (int deg = 0; deg < 360; deg += 20) {
            double rad = Math.toRadians(deg);
            int px = mx + (int) (Math.cos(rad) * r);
            int py = my + (int) (Math.sin(rad) * r);
            gfx.fill(px, py, px + 2, py + 2, ringCol);
        }
        // Centre fill
        gfx.fill(mx - 4, my - 4, mx + 4, my + 4, 0xFFD0B880);
        gfx.fill(mx - 2, my - 2, mx + 2, my + 2, 0xFFF0E0B0);

        if (!lmbHeld) return;

        // Arc of dots showing wrap progress and direction
        // Up to 16 dots for the first 50% of coverage; 16–32 once past half.
        int arcR = 15;
        int totalDots = 32;
        int activeDots = Math.min(totalDots, (int) (totalDots * coverage * 1.5f));
        double startAngle = -Math.PI / 2; // start at top
        double arcSign = (wrapDir >= 0) ? 1.0 : -1.0;

        for (int i = 0; i < activeDots; i++) {
            double angle = startAngle + arcSign * i * (2 * Math.PI / totalDots);
            int ax = mx + (int) (Math.cos(angle) * arcR);
            int ay = my + (int) (Math.sin(angle) * arcR);
            // Colour transitions green → bright green as coverage grows
            int dotCol = lerpColor(0xFF336633, 0xFF88FF88, (float) i / Math.max(activeDots - 1, 1));
            gfx.fill(ax, ay, ax + 2, ay + 2, dotCol);
        }
    }

    private void drawHUD(GuiGraphics gfx) {
        int hx = 13;
        int hy = height / 2 - 44;

        // Limb label
        String limbLabel = limb.getDisplayName().getString();
        gfx.drawString(font, Component.translatable("screen.avaliproject.bandage_minigame"), hx, hy, 0xFFDDDDFF, false);
        gfx.drawString(font, "Treating: " + limbLabel, hx, hy + 12, 0xAABBCC, false);

        // Coverage bar
        int barW = 108, barH = 9;
        int barX = hx, barY = hy + 28;
        gfx.fill(barX - 1, barY - 1, barX + barW + 1, barY + barH + 1, 0xFF111111);
        gfx.fill(barX, barY, barX + barW, barY + barH, 0xFF1C1C2A);
        int fillW = (int) (barW * Math.min(coverage, 1f));
        int barColor = coverage >= SUCCESS_THRESHOLD ? 0xFF44BB66 : 0xFF4488AA;
        gfx.fill(barX, barY, barX + fillW, barY + barH, barColor);
        // Sheen on bar
        gfx.fill(barX, barY, barX + fillW, barY + 3, 0x33FFFFFF);
        gfx.drawString(font, (int) (coverage * 100) + "% coverage", barX, barY + barH + 3, 0x99BBEE, false);

        // Bleed indicator
        Player player = minecraft.player;
        if (player != null) {
            int bleed = player.getData(ModAttachments.LIMB_DATA).getBleed(limb);
            int bleedColor = bleed >= 4 ? 0xFFFF3344 : bleed >= 2 ? 0xFFFF8844 : 0xFF66BB66;
            gfx.drawString(font, "Bleed: " + bleed, hx, barY + barH + 17, bleedColor, false);
        }

        // Shake warning
        float shakeMag = getShakeMagnitude();
        if (shakeMag > 2f) {
            String warnMsg = shakeMag > 5f ? "Hands shaking badly!" : "Hands unsteady";
            gfx.drawString(font, warnMsg, hx, barY + barH + 31, 0xFFFFAA22, false);
        }

        // Bottom hint
        if (!succeeded) {
            String hint = lmbHeld
                    ? (wrapDir == 0 ? "Keep moving!" : "Keep circling the wound!")
                    : "Hold [LMB] and circle around the wound";
            gfx.drawCenteredString(font, hint, width / 2, height - 30, 0xCCCCCC);
            gfx.drawCenteredString(font, "[Right-click or Esc to cancel]", width / 2, height - 18, 0x666666);
        } else {
            // Success text above limb
            int sTY = limbCY - LIMB_H / 2 - 20;
            gfx.drawCenteredString(font, "Wound bandaged!", width / 2, sTY, 0xFF44FF88);
        }
    }

    // =========================================================================
    // Tick / update
    // =========================================================================

    @Override
    public void tick() {
        super.tick();
        updateShake();
        tickParticles();
        if (closeCountdown > 0 && --closeCountdown == 0) onClose();
    }

    private void updateShake() {
        float mag = getShakeMagnitude();
        if (mag > 0f) {
            shakeX = (rng.nextFloat() - 0.5f) * mag;
            shakeY = (rng.nextFloat() - 0.5f) * mag;
        } else {
            shakeX = 0f;
            shakeY = 0f;
        }
    }

    /**
     * Calculates the total shake magnitude from status effects and low health.
     * Higher values = more cursor jitter and reduced wrapping efficiency.
     */
    private float getShakeMagnitude() {
        Player p = minecraft.player;
        if (p == null) return 0f;
        float s = 0f;
        if (p.hasEffect(MobEffects.POISON))           s += 3f;
        if (p.hasEffect(MobEffects.WITHER))           s += 5f;
        if (p.hasEffect(MobEffects.WEAKNESS))         s += 2f;
        if (p.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) s += 1f;
        if (p.getHealth() < p.getMaxHealth() * 0.25f) s += 4f;
        return s;
    }

    private void tickParticles() {
        for (int i = particleCount - 1; i >= 0; i--) {
            particles[i][0] += particles[i][2];
            particles[i][1] += particles[i][3];
            particles[i][2] *= 0.82f; // decelerate
            particles[i][3] *= 0.82f;
            float spd = Math.abs(particles[i][2]) + Math.abs(particles[i][3]);
            if (spd < 0.04f) {
                // Remove by swap-with-last
                if (i < particleCount - 1) particles[i] = particles[particleCount - 1];
                particleCount--;
            }
        }
    }

    private void spawnParticle(double px, double py) {
        if (particleCount >= MAX_PARTICLES) return;
        float vx = (rng.nextFloat() - 0.5f) * 2.5f;
        float vy = (rng.nextFloat() - 0.8f) * 2.5f; // bias upward
        particles[particleCount][0] = (float) px;
        particles[particleCount][1] = (float) py;
        particles[particleCount][2] = vx;
        particles[particleCount][3] = vy;
        particleCount++;
    }

    // =========================================================================
    // Mouse input
    // =========================================================================

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0) { // left button → begin wrapping
            lmbHeld = true;
            lastAngle = Math.atan2(my - limbCY, mx - limbCX);
            wrapDir = 0;
            streak = 0;
            return true;
        }
        if (button == 1) { // right button → cancel
            onClose();
            return true;
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseDragged(double mx, double my, int button, double dx, double dy) {
        if (button == 0 && lmbHeld && !succeeded) {
            // Apply shake offset so unsteady players feel the jitter physically
            processWrapping(mx + shakeX, my + shakeY);
            return true;
        }
        return super.mouseDragged(mx, my, button, dx, dy);
    }

    @Override
    public boolean mouseReleased(double mx, double my, int button) {
        if (button == 0) {
            lmbHeld = false;
            wrapDir = 0;
            return true;
        }
        return super.mouseReleased(mx, my, button);
    }

    /**
     * Core wrapping physics — called each frame while LMB is held.
     *
     * Converts mouse displacement into an angle delta relative to the limb centre.
     * Consistent circular motion (consistent sign of delta) accumulates coverage;
     * reversals are penalised. Shake is already baked into (emx, emy).
     *
     * @param emx effective mouse X (with shake offset applied)
     * @param emy effective mouse Y (with shake offset applied)
     */
    private void processWrapping(double emx, double emy) {
        // Bootstrap: record the first angle without accumulating anything
        if (Double.isNaN(lastAngle)) {
            lastAngle = Math.atan2(emy - limbCY, emx - limbCX);
            return;
        }

        double currAngle = Math.atan2(emy - limbCY, emx - limbCX);
        double delta = currAngle - lastAngle;

        // Normalise to [-π, π] to handle the ±π discontinuity
        while (delta >  Math.PI) delta -= 2 * Math.PI;
        while (delta < -Math.PI) delta += 2 * Math.PI;

        // Reject micro-twitches and impossibly large jumps (lag/teleport)
        if (Math.abs(delta) < 0.003 || Math.abs(delta) > 1.6) {
            lastAngle = currAngle;
            return;
        }

        // Require the cursor to be in the valid "wrapping annulus" around the limb
        double ddx = emx - limbCX, ddy = emy - limbCY;
        double dist = Math.sqrt(ddx * ddx + ddy * ddy);
        if (dist < MIN_WRAP_RADIUS || dist > LIMB_W * MAX_WRAP_RADIUS_MULT) {
            lastAngle = currAngle;
            return;
        }

        // --- Direction tracking ---
        int newDir = delta > 0 ? 1 : -1;

        if (wrapDir == 0) {
            // First movement after click: lock in direction
            wrapDir = newDir;
            streak = 1;
        } else if (newDir == wrapDir) {
            // Consistent direction: build streak (capped to avoid overflow)
            streak = Math.min(streak + 1, 400);
        } else {
            // Reversal: lose some coverage and reset
            coverage = Math.max(0f, coverage - 0.05f);
            wrapDir = newDir;
            streak = 0;
        }

        // --- Coverage gain ---
        // Shake penalises gain: very shaky hands are up to 65% less effective
        float shakePenalty = Math.max(0.25f, 1f - getShakeMagnitude() / 14f);

        // Streak bonus rewards sustained circular motion (up to 1.5×)
        float streakBonus = Math.min(1f + streak * 0.004f, 1.5f);

        float gain = (float) (Math.abs(delta) / RADS_FOR_FULL_COVERAGE) * shakePenalty * streakBonus;
        coverage = Math.min(1f, coverage + gain);

        lastAngle = currAngle;

        // Spawn cloth particles occasionally during active wrapping
        if (streak % 9 == 1 && rng.nextFloat() < 0.6f) {
            spawnParticle(emx + (rng.nextFloat() - 0.5f) * 20, emy + (rng.nextFloat() - 0.5f) * 20);
        }

        // Play cloth-rustle sound every ~25 frames of consistent wrapping
        if (streak % 25 == 1) {
            playWrapSound();
        }

        // Check for success
        if (!succeeded && coverage >= SUCCESS_THRESHOLD) {
            triggerSuccess();
        }
    }

    private void playWrapSound() {
        Player p = minecraft.player;
        if (p == null) return;
        p.level().playLocalSound(
                p.getX(), p.getY(), p.getZ(),
                ModSounds.BANDAGE_WRAP.get(), SoundSource.PLAYERS,
                0.35f, 0.85f + rng.nextFloat() * 0.3f, false
        );
    }

    private void triggerSuccess() {
        succeeded = true;
        closeCountdown = 80; // show result for ~4 s, then close

        Player p = minecraft.player;
        if (p == null) return;

        // Tell the server to reduce bleed by 30; it will echo an updated sync packet back.
        PacketDistributor.sendToServer(new ReduceBleedPayload(limb.ordinal(), 30));

        p.level().playLocalSound(
                p.getX(), p.getY(), p.getZ(),
                ModSounds.BANDAGE_SUCCESS.get(), SoundSource.PLAYERS,
                0.9f, 1.0f, false
        );
    }

    // =========================================================================
    // Keyboard
    // =========================================================================

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // GLFW_KEY_ESCAPE
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        // Keep game time running while the player is bandaging
        return false;
    }

    // =========================================================================
    // Utility
    // =========================================================================

    /** ARGB colour lerp between two packed-int colours. */
    private static int lerpColor(int from, int to, float t) {
        int fa = (from >> 24) & 0xFF, fr = (from >> 16) & 0xFF, fg = (from >> 8) & 0xFF, fb = from & 0xFF;
        int ta = (to   >> 24) & 0xFF, tr = (to   >> 16) & 0xFF, tg = (to   >> 8) & 0xFF, tb = to   & 0xFF;
        int a = fa + (int) ((ta - fa) * t);
        int r = fr + (int) ((tr - fr) * t);
        int g = fg + (int) ((tg - fg) * t);
        int b = fb + (int) ((tb - fb) * t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}