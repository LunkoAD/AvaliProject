package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.network.SyringeEffectPayload;
import com.lunkoashtail.avaliproject.sound.ModSounds;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Random;

/**
 * SyringeMinigameScreen — an interactive injection minigame.
 *
 * HOW IT WORKS:
 *
 *  Phase 1 — AIMING:
 *    The player moves their mouse over a procedurally drawn forearm.
 *    A pulsing vein is visible inside the arm; a cross-hair appears near it.
 *    Left-clicking within VEIN_RADIUS pixels of the vein inserts the needle.
 *    Clicking off-target has no effect (and spawns a "ouch" blood particle).
 *
 *  Phase 2 — INSERTED:
 *    The needle tip is now fixed in the skin.
 *    The player must drag the mouse DOWNWARD to push the plunger.
 *    Each pixel of downward movement fills injection progress.
 *    The needle is automatically ejected if:
 *      – The cursor drifts more than WIGGLE_TOLERANCE px horizontally, or
 *      – A sharp upward flick (dy < −10 px in one frame) occurs, or
 *      – The left mouse button is released.
 *    Partial progress is RETAINED across ejections — re-insert and continue.
 *
 *  Phase 3 — SUCCESS (at SUCCESS_THRESHOLD progress):
 *    Drug effects are applied, an injection sound plays, and the screen
 *    auto-closes after a short delay.
 *
 * TEXTURE PATHS (optional — game works with primitives if absent):
 *    assets/avaliproject/textures/gui/syringe/arm.png
 *    assets/avaliproject/textures/gui/syringe/syringe.png
 */
public class SyringeMinigameScreen extends Screen {

    // =========================================================================
    // Nested types
    // =========================================================================

    /** Which drug is being injected — drives fluid colour and applied effects. */
    public enum DrugType { FENTANYL, HEROIN, SYRINGE }

    private enum Phase { AIMING, INSERTED, SUCCESS }

    // =========================================================================
    // Tuning constants
    // =========================================================================

    /** Pixel radius around the vein centre within which a click counts as a hit. */
    private static final int VEIN_RADIUS = 10;

    /**
     * Maximum horizontal cursor drift (px from insertion X) before the needle
     * is automatically ejected. Keeps the player from just wildly waving the mouse.
     */
    private static final int WIGGLE_TOLERANCE = 35;

    /**
     * Total downward mouse travel (px) needed to push the full dose.
     * Larger = harder / requires more deliberate downward dragging.
     */
    private static final float DRAG_FOR_FULL = 90f;

    /** Injection fraction at which SUCCESS is triggered. */
    private static final float SUCCESS_THRESHOLD = 0.85f;

    // =========================================================================
    // Visual / layout constants
    // =========================================================================

    // Arm
    private static final int ARM_W = 54;
    private static final int ARM_H = 132;

    // Syringe sprite sizes
    private static final int SYR_W      = 12;
    private static final int SYR_H      = 38;
    private static final int NEEDLE_LEN = 14;
    private static final int PLUNGER_H  = 6;

    // Colours
    private static final int COL_PANEL  = 0xBB080812;
    private static final int COL_BORDER = 0xFF334466;

    // =========================================================================
    // State
    // =========================================================================

    private final DrugType drugType;
    /** Which limb the injection is targeting — stored for display and future limb-specific logic. */
    @Nullable
    private final Limb targetLimb;
    private Phase phase = Phase.AIMING;

    // Screen layout — computed in init()
    private int armCX, armCY;
    private int veinX, veinY; // vein target position on screen

    // Snapshot taken at needle insertion
    private int    insertX, insertY;   // on-screen position of the needle tip (= vein coords)
    private double insertMouseX;       // raw cursor X when the player clicked — used for drift detection

    // Cumulative injection progress (0→1); survives eject/re-insert cycles
    private float injectionProgress = 0f;

    // Auto-close countdown after success (ticks)
    private int closeCountdown = 0;

    // Vein-pulse animation
    private float   veinPulse    = 0f;
    private boolean veinPulseUp  = true;

    // Hand-shake driven by status effects (px of random jitter per frame)
    private final Random rng = new Random();
    private float shakeX, shakeY;

    // Blood / fluid particles  [x, y, vx, vy, life(0–1)]
    private static final int MAX_PARTICLES = 28;
    private final float[][] particles = new float[MAX_PARTICLES][5];
    private int particleCount = 0;

    // =========================================================================
    // Constructor
    // =========================================================================

    /** Convenience constructor: no specific limb targeted. */
    public SyringeMinigameScreen(DrugType drugType) {
        this(drugType, null);
    }

    /**
     * Full constructor.
     *
     * @param targetLimb the limb selected on the wheel (null if opened without a limb context)
     */
    public SyringeMinigameScreen(DrugType drugType, @Nullable Limb targetLimb) {
        super(Component.translatable("screen.avaliproject.syringe_minigame"));
        this.drugType   = drugType;
        this.targetLimb = targetLimb;
    }

    // =========================================================================
    // Init
    // =========================================================================

    @Override
    protected void init() {
        super.init();
        armCX = width  / 2;
        armCY = height / 2 + 5;
        // Vein target sits slightly to the left of arm centre, a quarter of the way up
        veinX = armCX - 7;
        veinY = armCY - 18;
    }

    // =========================================================================
    // Rendering
    // =========================================================================

    @Override
    public void renderBackground(GuiGraphics gfx, int mx, int my, float partial) {
        // Intentionally empty — prevents MC's blur post-effect.
        // The dark overlay is applied in render() instead.
    }

    @Override
    public void render(GuiGraphics gfx, int mx, int my, float partial) {
        gfx.fill(0, 0, width, height, 0xCC000000);

        // Shift the logical cursor by the shake offset so unsteady players
        // physically feel their hand trembling against the vein target.
        int smx = mx + (int) shakeX;
        int smy = my + (int) shakeY;

        drawStatsPanel(gfx);
        drawArm(gfx);
        drawVein(gfx, smx, smy);
        drawParticles(gfx);
        drawSyringe(gfx, smx, smy);
        drawHUD(gfx, smx, smy);

        super.render(gfx, mx, my, partial);
    }

    // ---- Left-side stats panel ----

    private void drawStatsPanel(GuiGraphics gfx) {
        int pw = 148, ph = 124;
        int px = 8, py = height / 2 - ph / 2;
        gfx.fill(px, py, px + pw, py + ph, COL_PANEL);
        gfx.fill(px,          py,          px + pw,     py + 1,      COL_BORDER);
        gfx.fill(px,          py + ph - 1, px + pw,     py + ph,     COL_BORDER);
        gfx.fill(px,          py,          px + 1,      py + ph,     COL_BORDER);
        gfx.fill(px + pw - 1, py,          px + pw,     py + ph,     COL_BORDER);
    }

    // ---- Forearm silhouette (black fur, matches BandageMinigameScreen) ----

    private void drawArm(GuiGraphics gfx) {
        int x = armCX - ARM_W / 2;
        int y = armCY - ARM_H / 2;

        final int BASE   = 0xFF0E0E0E; // main body fill
        final int MID    = 0xFF181818; // slightly lighter mid-tone
        final int HILIGHT = 0xFF262626; // subtle edge highlight (light catching fur tips)
        final int DEEP   = 0xFF050505; // deepest shadow on the inner curve
        final int FUR1   = 0xFF1C1C1C; // shorter strand
        final int FUR2   = 0xFF282828; // longer strand tip

        // Rounded cylinder body
        gfx.fill(x + 2,  y + 5,             x + ARM_W - 2,  y + ARM_H - 5,  BASE);
        gfx.fill(x,      y + 12,            x + ARM_W,       y + ARM_H - 12, BASE);
        // Top cap
        gfx.fill(x + 5,  y + 2,             x + ARM_W - 5,  y + 5,          BASE);
        gfx.fill(x + 9,  y,                 x + ARM_W - 9,  y + 2,          BASE);
        // Bottom cap
        gfx.fill(x + 5,  y + ARM_H - 5,    x + ARM_W - 5,  y + ARM_H - 2,  BASE);
        gfx.fill(x + 9,  y + ARM_H - 2,    x + ARM_W - 9,  y + ARM_H,      BASE);

        // Roundness shading: dark groove down the centre
        gfx.fill(x + ARM_W / 2 - 4, y + 4,  x + ARM_W / 2 + 4, y + ARM_H - 4, DEEP);
        // Slight mid-tone on the lit (right) side
        gfx.fill(x + ARM_W - 10,    y + 8,  x + ARM_W - 3,      y + ARM_H - 8, MID);
        // Thin highlight streak on the far right edge
        gfx.fill(x + ARM_W - 4,     y + 10, x + ARM_W - 2,      y + ARM_H - 10, HILIGHT);

        // Fur strands on the left edge
        for (int fy = y + 6; fy < y + ARM_H - 6; fy += 3) {
            boolean longStrand = ((fy - y) % 9 == 0);
            int len = longStrand ? 4 : 2;
            int col = longStrand ? FUR2 : FUR1;
            gfx.fill(x - len, fy, x, fy + 1, col);
            if (longStrand) gfx.fill(x - len - 1, fy, x - len, fy + 1, DEEP);
        }

        // Fur strands on the right edge (offset so left/right don't align)
        for (int fy = y + 6; fy < y + ARM_H - 6; fy += 3) {
            boolean longStrand = ((fy - y) % 9 == 3);
            int len = longStrand ? 4 : 2;
            int col = longStrand ? FUR2 : FUR1;
            gfx.fill(x + ARM_W, fy, x + ARM_W + len, fy + 1, col);
            if (longStrand) gfx.fill(x + ARM_W + len, fy, x + ARM_W + len + 1, fy + 1, DEEP);
        }

        // Fur tufts at the top cap
        for (int fx = x + 9; fx < x + ARM_W - 9; fx += 4) {
            boolean tall = ((fx - x) % 8 == 1);
            int tuftH = tall ? 3 : 2;
            gfx.fill(fx, y - tuftH, fx + 2, y, tall ? FUR2 : FUR1);
        }

        // Fur tufts at the bottom cap
        for (int fx = x + 9; fx < x + ARM_W - 9; fx += 4) {
            boolean tall = ((fx - x) % 8 == 1);
            int tuftH = tall ? 3 : 2;
            gfx.fill(fx, y + ARM_H, fx + 2, y + ARM_H + tuftH, tall ? FUR2 : FUR1);
        }
    }

    // ---- Vein and injection target ----

    private void drawVein(GuiGraphics gfx, int smx, int smy) {
        boolean near = (phase == Phase.AIMING) && distToVein(smx, smy) <= VEIN_RADIUS * 2.5;

        // Subtle blue vein line running along the inside of the arm
        int lineAlpha = near ? (int)(0x65 + veinPulse * 0x55) : 0x48;
        gfx.fill(veinX - 1, armCY - ARM_H / 2 + 14, veinX + 2, armCY + ARM_H / 2 - 14,
                (lineAlpha << 24) | 0x5566EE);

        // Target bulge — brighter when the cursor is nearby
        int bulgeAlpha = near ? (int)(0x88 + veinPulse * 0x66) : 0x58;
        gfx.fill(veinX - 4, veinY - 5, veinX + 5, veinY + 5, (bulgeAlpha << 24) | 0x7799FF);
        gfx.fill(veinX - 2, veinY - 3, veinX + 3, veinY + 3,
                (Math.min(0xFF, bulgeAlpha + 0x44) << 24) | 0x99AAFF);

        // Cross-hair guide dots (hint at the sweet spot while AIMING)
        if (phase == Phase.AIMING) {
            int chAlpha = near ? 0xCC : 0x44;
            int chCol   = (chAlpha << 24) | 0xFFEE88;
            gfx.fill(veinX - 9, veinY,     veinX - 6, veinY + 1, chCol);
            gfx.fill(veinX + 6, veinY,     veinX + 9, veinY + 1, chCol);
            gfx.fill(veinX,     veinY - 9, veinX + 1, veinY - 6, chCol);
            gfx.fill(veinX,     veinY + 6, veinX + 1, veinY + 9, chCol);
        }

        // Small red entry mark at the puncture site while needle is inside
        if (phase == Phase.INSERTED || phase == Phase.SUCCESS) {
            gfx.fill(insertX - 2, insertY - 1, insertX + 3, insertY + 2, 0xCCFF3333);
        }
    }

    // ---- Syringe cursor ----

    /**
     * Draws the syringe with the needle tip at the logical cursor.
     *
     * While AIMING  : tip follows the (shake-adjusted) cursor exactly.
     * While INSERTED: tip is fixed at the vein; the barrel hangs above it.
     *
     * The plunger moves progressively downward into the barrel as
     * {@code injectionProgress} increases, and the fluid level depletes to match.
     */
    private void drawSyringe(GuiGraphics gfx, int mx, int my) {
        int tipX = (phase == Phase.AIMING) ? mx     : insertX;
        int tipY = (phase == Phase.AIMING) ? my     : insertY;

        // ---- Needle shaft ----
        int needleTopY = tipY - NEEDLE_LEN;
        gfx.fill(tipX,     needleTopY,     tipX + 1, tipY,             0xFFDDDDDD);
        gfx.fill(tipX - 1, needleTopY,     tipX + 2, needleTopY + 3,   0xFFAAAAAA); // bevel

        // ---- Barrel (glass tube) ----
        int barX = tipX - SYR_W / 2;
        int barY = needleTopY - SYR_H;

        gfx.fill(barX,              barY,             barX + SYR_W,     barY + SYR_H,     0x44BBBBBB); // glass fill
        gfx.fill(barX,              barY,             barX + SYR_W,     barY + 1,          0xFFDDDDDD); // top edge
        gfx.fill(barX,              barY + SYR_H - 1, barX + SYR_W,     barY + SYR_H,     0xFFDDDDDD); // bottom edge
        gfx.fill(barX,              barY,             barX + 1,          barY + SYR_H,     0xFFDDDDDD); // left edge
        gfx.fill(barX + SYR_W - 1,  barY,             barX + SYR_W,     barY + SYR_H,     0xFFDDDDDD); // right edge

        // Fluid colour by drug type
        int fluidCol = switch (drugType) {
            case FENTANYL -> 0xCCFFCC44; // amber / golden
            case HEROIN   -> 0xCCDDBB88; // tan / off-white
            case SYRINGE  -> 0xCCAACCFF; // clear blue (saline)
        };

        // Fluid occupies the lower part of the barrel; empties from the top as progress rises
        int innerH   = SYR_H - 4;
        int fluidPx  = (int)(innerH * (1f - injectionProgress));
        if (fluidPx > 0) {
            int fluidTop = barY + 2 + (innerH - fluidPx);
            gfx.fill(barX + 2, fluidTop, barX + SYR_W - 2, barY + SYR_H - 2, fluidCol);
        }

        // Graduation tick marks on the right side of the barrel
        for (int i = 1; i <= 3; i++) {
            int markY = barY + i * SYR_H / 4;
            gfx.fill(barX + SYR_W - 4, markY, barX + SYR_W - 1, markY + 1, 0x88FFFFFF);
        }
        // Glassy highlight streak down the left interior
        gfx.fill(barX + 2, barY + 2, barX + 4, barY + SYR_H - 2, 0x33FFFFFF);

        // ---- Plunger ----
        // Starts flush with the barrel top; moves down as the dose is pushed in.
        int maxTravel    = SYR_H - PLUNGER_H - 4;
        int plungerOffset = (phase == Phase.AIMING) ? 0 : (int)(maxTravel * injectionProgress);
        int pY           = barY + plungerOffset;

        gfx.fill(barX - 3,     pY,             barX + SYR_W + 3, pY + PLUNGER_H,  0xFFAAAAAA); // finger wings
        gfx.fill(barX + 1,     pY + 1,         barX + SYR_W - 1, pY + PLUNGER_H,  0xFF777777); // rubber head
        gfx.fill(barX + 2,     pY + 2,         barX + SYR_W - 2, pY + 3,          0xFF555555); // grip indent

        // Rod visible above the barrel when the plunger is at the top
        if (plungerOffset == 0) {
            gfx.fill(tipX - 1, barY - 11, tipX + 2, barY, 0xFF999999);
        }
    }

    // ---- Particles ----

    private void drawParticles(GuiGraphics gfx) {
        for (int i = 0; i < particleCount; i++) {
            int alpha = (int)(particles[i][4] * 210f);
            if (alpha < 6) continue;
            int px = (int) particles[i][0], py = (int) particles[i][1];
            gfx.fill(px, py, px + 2, py + 2, (alpha << 24) | 0xFF3333);
        }
    }

    // ---- HUD ----

    private void drawHUD(GuiGraphics gfx, int mx, int my) {
        int hx = 14;
        int hy = height / 2 - 56;

        // Drug label
        String drugName = switch (drugType) {
            case FENTANYL -> "Fentanyl";
            case HEROIN   -> "Heroin";
            case SYRINGE  -> "Syringe";
        };
        String limbSuffix = (targetLimb != null) ? " → " + targetLimb.getDisplayName().getString() : "";
        gfx.drawString(font, "Injection: " + drugName + limbSuffix, hx, hy, 0xFFDDDDFF, false);

        // Phase instruction
        String phaseLabel = switch (phase) {
            case AIMING   -> "Step 1: Aim at the glowing vein";
            case INSERTED -> "Step 2: Drag mouse down to inject";
            case SUCCESS  -> "Injection complete!";
        };
        gfx.drawString(font, phaseLabel, hx, hy + 12, 0xAABBCC, false);

        // Injection progress bar (shown once insertion has been attempted at least once)
        if (injectionProgress > 0f || phase != Phase.AIMING) {
            int barW = 130, barH = 9;
            int barX = hx, barY = hy + 30;

            gfx.fill(barX - 1, barY - 1, barX + barW + 1, barY + barH + 1, 0xFF111111); // border
            gfx.fill(barX, barY, barX + barW, barY + barH, 0xFF1C1C2A);                  // track

            int fillW   = (int)(barW * Math.min(injectionProgress, 1f));
            int barCol  = injectionProgress >= SUCCESS_THRESHOLD ? 0xFF44BB66 : 0xFFAA5555;
            gfx.fill(barX, barY, barX + fillW, barY + barH, barCol);
            gfx.fill(barX, barY, barX + fillW, barY + 3, 0x33FFFFFF); // sheen

            // Threshold marker (shows how far left to go)
            int threshX = barX + (int)(barW * SUCCESS_THRESHOLD);
            gfx.fill(threshX, barY - 2, threshX + 1, barY + barH + 2, 0xFFFFFF44);

            gfx.drawString(font, (int)(injectionProgress * 100) + "% injected", barX, barY + barH + 4, 0x99BBEE, false);
        }

        // Hand-shake warning
        float shakeMag = getShakeMagnitude();
        if (shakeMag > 2f) {
            String warn = shakeMag > 5f ? "Hands shaking badly!" : "Hands unsteady";
            gfx.drawString(font, warn, hx, hy + 68, 0xFFFFAA22, false);
        }

        // Aim-proximity readout while hunting the vein
        if (phase == Phase.AIMING) {
            double dist = distToVein(mx, my);
            if (dist < VEIN_RADIUS * 4) {
                int pct = (int)(100.0 * Math.max(0, 1.0 - dist / (VEIN_RADIUS * 4.0)));
                gfx.drawCenteredString(font, "Aim: " + pct + "%",
                        width / 2, armCY + ARM_H / 2 + 14, 0xFFCCCC44);
            }
        }

        // Bottom hint line
        if (phase != Phase.SUCCESS) {
            String hint = switch (phase) {
                case AIMING   -> "Click on the vein to insert needle";
                case INSERTED -> "Drag DOWN  |  Move sideways or pull up to remove";
                default       -> "";
            };
            gfx.drawCenteredString(font, hint,                        width / 2, height - 30, 0xCCCCCC);
            gfx.drawCenteredString(font, "[Right-click or Esc to cancel]", width / 2, height - 18, 0x666666);
        } else {
            gfx.drawCenteredString(font, "Injected successfully!", width / 2, armCY - ARM_H / 2 - 22, 0xFF55FF88);
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
        tickVeinPulse();
        if (closeCountdown > 0 && --closeCountdown == 0) onClose();
    }

    private void tickVeinPulse() {
        float step = 0.035f;
        if (veinPulseUp) { veinPulse = Math.min(1f, veinPulse + step); if (veinPulse >= 1f) veinPulseUp = false; }
        else             { veinPulse = Math.max(0f, veinPulse - step); if (veinPulse <= 0f) veinPulseUp = true;  }
    }

    private void updateShake() {
        float mag = getShakeMagnitude();
        shakeX = mag > 0f ? (rng.nextFloat() - 0.5f) * mag : 0f;
        shakeY = mag > 0f ? (rng.nextFloat() - 0.5f) * mag : 0f;
    }

    /**
     * Returns the pixel magnitude of cursor jitter caused by the player's current
     * status effects. Applied each frame to the logical cursor before any hit-tests.
     */
    private float getShakeMagnitude() {
        Player p = minecraft.player;
        if (p == null) return 0f;
        float s = 0f;
        if (p.hasEffect(MobEffects.POISON))            s += 3.5f;
        if (p.hasEffect(MobEffects.WITHER))            s += 6f;
        if (p.hasEffect(MobEffects.WEAKNESS))          s += 2f;
        if (p.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) s += 1.5f;
        if (p.hasEffect(MobEffects.CONFUSION))          s += 4f;
        if (p.getHealth() < p.getMaxHealth() * 0.25f)  s += 4.5f;
        return s;
    }

    private void tickParticles() {
        for (int i = particleCount - 1; i >= 0; i--) {
            particles[i][0] += particles[i][2];
            particles[i][1] += particles[i][3];
            particles[i][2] *= 0.82f;
            particles[i][3] *= 0.82f;
            particles[i][4] -= 0.04f;
            if (particles[i][4] <= 0f) {
                if (i < particleCount - 1)
                    System.arraycopy(particles[particleCount - 1], 0, particles[i], 0, 5);
                particleCount--;
            }
        }
    }

    private void spawnBloodParticle(int x, int y) {
        if (particleCount >= MAX_PARTICLES) return;
        float[] p = particles[particleCount++];
        p[0] = x + rng.nextInt(5) - 2;
        p[1] = y + rng.nextInt(5) - 2;
        p[2] = (rng.nextFloat() - 0.5f) * 2.5f;
        p[3] = rng.nextFloat() * 1.8f; // bias downward
        p[4] = 0.5f + rng.nextFloat() * 0.5f;
    }

    // =========================================================================
    // Mouse input
    // =========================================================================

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 1) { onClose(); return true; } // right-click → cancel

        if (button == 0 && phase == Phase.AIMING) {
            // Apply shake to the effective click position before the hit-test.
            // This means unsteady players will click in the wrong place even if
            // they aimed correctly with the raw cursor.
            attemptInsertion(mx + shakeX, my + shakeY, mx);
            return true;
        }
        return super.mouseClicked(mx, my, button);
    }

    /**
     * Checks whether the effective cursor is within VEIN_RADIUS of the vein target.
     * If yes, transitions to INSERTED and records the reference X for drift detection.
     *
     * @param emx effective (shake-adjusted) cursor X
     * @param emy effective (shake-adjusted) cursor Y
     * @param rawMx raw (un-shaken) cursor X, stored for drift reference
     */
    private void attemptInsertion(double emx, double emy, double rawMx) {
        if (distToVein((int) emx, (int) emy) <= VEIN_RADIUS) {
            phase         = Phase.INSERTED;
            insertX       = veinX;
            insertY       = veinY;
            insertMouseX  = rawMx;
            spawnBloodParticle(veinX,     veinY);
            spawnBloodParticle(veinX + 1, veinY + 2);
            playInsertSound();
        } else {
            // Missed: cosmetic "ouch" particle where they poked
            spawnBloodParticle((int) emx, (int) emy);
        }
    }

    /**
     * Core injection physics — called each frame while LMB is held and the needle
     * is inside the vein.
     *
     * Only downward mouse movement (dy > 0) accumulates injection progress.
     * The player fails to inject by:
     *   – Drifting horizontally beyond WIGGLE_TOLERANCE
     *   – Pulling the mouse sharply upward (dy < −10 in one event)
     *   – Releasing the mouse button (handled in mouseReleased)
     *
     * Progress is clamped to [0, 1] and persists across ejection/re-insertion cycles,
     * so the player can take multiple attempts to fully inject the dose.
     */
    @Override
    public boolean mouseDragged(double mx, double my, int button, double dx, double dy) {
        if (button != 0 || phase != Phase.INSERTED) {
            return super.mouseDragged(mx, my, button, dx, dy);
        }

        // Horizontal drift guard: wiggling sideways mimics a real needle dislodging
        if (Math.abs(mx - insertMouseX) > WIGGLE_TOLERANCE) {
            ejectNeedle();
            return true;
        }

        // Sharp upward flick guard: pulling up hard removes the needle
        if (dy < -10.0) {
            ejectNeedle();
            return true;
        }

        // Accumulate only downward movement as injection progress
        if (dy > 0) {
            injectionProgress = Math.min(1f, injectionProgress + (float)(dy / DRAG_FOR_FULL));
            if (rng.nextInt(7) == 0) spawnBloodParticle(insertX, insertY);
        }

        if (injectionProgress >= SUCCESS_THRESHOLD) {
            triggerSuccess();
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double mx, double my, int button) {
        if (button == 0 && phase == Phase.INSERTED) {
            ejectNeedle(); // lifting the finger removes the needle; progress is kept
            return true;
        }
        return super.mouseReleased(mx, my, button);
    }

    /**
     * Returns to the AIMING phase without resetting injection progress.
     * The player can re-insert the needle and continue from where they left off.
     */
    private void ejectNeedle() {
        if (phase != Phase.INSERTED) return;
        phase = Phase.AIMING;
    }

    // =========================================================================
    // Keyboard
    // =========================================================================

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { onClose(); return true; } // Escape
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() { return false; }

    // =========================================================================
    // Success / effects
    // =========================================================================

    private void triggerSuccess() {
        phase          = Phase.SUCCESS;
        closeCountdown = 80; // ~4 s at 20 tps, then screen closes

        // Tell the server to apply the drug effects.
        // Server-side application is required so the effects tick down properly
        // and their HUD icons disappear when they expire.
        PacketDistributor.sendToServer(new SyringeEffectPayload(drugType.ordinal()));

        playInjectSound();
    }

    private void playInsertSound() {
        Player p = minecraft.player;
        if (p == null) return;
        p.level().playLocalSound(p.getX(), p.getY(), p.getZ(),
                ModSounds.SYRINGE_STAB.get(), SoundSource.PLAYERS,
                0.6f, 1.0f + rng.nextFloat() * 0.2f, false);
    }

    private void playInjectSound() {
        Player p = minecraft.player;
        if (p == null) return;
        p.level().playLocalSound(p.getX(), p.getY(), p.getZ(),
                ModSounds.SYRINGE_INJECT.get(), SoundSource.PLAYERS,
                0.9f, 1.0f, false);
    }

    // =========================================================================
    // Utility
    // =========================================================================

    private double distToVein(int mx, int my) {
        double dx = mx - veinX, dy = my - veinY;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
