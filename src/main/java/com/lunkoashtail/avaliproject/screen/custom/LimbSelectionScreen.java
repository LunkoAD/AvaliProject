package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.limb.BleedingTier;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Wheel-style limb selection screen.
 *
 * ── Layout ─────────────────────────────────────────────────────────────────
 *   Six limb buttons are placed at 60° intervals around WHEEL_RADIUS, starting
 *   at the top (12 o'clock) and going clockwise:
 *
 *     HEAD → RIGHT_ARM → RIGHT_LEG → BACK → LEFT_LEG → LEFT_ARM
 *
 *   The local player entity is rendered in the centre so the wheel reads as a
 *   body diagram.
 *
 * ── Effect icons ────────────────────────────────────────────────────────────
 *   Each button shows a 16×16 bleed-tier icon in its top-right corner when the
 *   limb has any bleeding.  Icons are loaded from:
 *
 *     assets/avaliproject/textures/gui/effect/
 *       minor_bleeding_effect.png          (bleed  1–25)
 *       bleeding_effect.png                (bleed 26–50)
 *       heavy_bleeding_effect.png          (bleed 51–75)
 *       catastrophic_bleeding_effect.png   (bleed 76–100)
 *
 *   Place 16×16 PNG files at those paths — the game renders pink/white squares
 *   if a file is missing, so add the PNGs before shipping.
 *
 * ── Two operating modes ────────────────────────────────────────────────────
 *   Item mode   (onLimbSelected ≠ null): clicking a limb fires the callback
 *               (which opens the minigame) without calling onClose().
 *   Inspect mode (onLimbSelected == null): opened by the H keybinding; clicking
 *               a limb closes the wheel without further action.
 */
public class LimbSelectionScreen extends Screen {

    // -------------------------------------------------------------------------
    // Geometry
    // -------------------------------------------------------------------------

    /** Radius (px) of the circle on which button centres sit. */
    private static final int WHEEL_RADIUS = 90;
    /** Half-size of a button square; clickable area = 2*BTN_R × 2*BTN_R. */
    private static final int BTN_R        = 22;
    /** Size of the bleed-tier icon rendered inside each button. */
    private static final int ICON_SIZE    = 16;

    // -------------------------------------------------------------------------
    // Colours
    // -------------------------------------------------------------------------
    private static final int COL_OVERLAY      = 0xBB060612;
    private static final int COL_BTN_IDLE     = 0xFF1A1A2E;
    private static final int COL_BTN_HOVER    = 0xFF22334C;
    private static final int COL_BORDER_IDLE  = 0xFF4466AA;
    private static final int COL_BORDER_HOVER = 0xFF88AADD;
    private static final int COL_TEXT_IDLE    = 0xFFAABBCC;
    private static final int COL_TEXT_HOVER   = 0xFFFFFFFF;
    private static final int COL_TITLE        = 0xFFCCCCFF;
    private static final int COL_HINT         = 0xFF666666;
    private static final int COL_HEALTHY      = 0xFF44BB66;

    // -------------------------------------------------------------------------
    // Wheel order — clockwise from 12 o'clock
    // -------------------------------------------------------------------------
    private static final Limb[] WHEEL_ORDER = {
            Limb.HEAD,      // 270° (top)
            Limb.RIGHT_ARM, // 330° (upper-right)
            Limb.RIGHT_LEG, //  30° (lower-right)
            Limb.BACK,      //  90° (bottom)
            Limb.LEFT_LEG,  // 150° (lower-left)
            Limb.LEFT_ARM   // 210° (upper-left)
    };

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------

    @Nullable private final Consumer<Limb> onLimbSelected;
    @Nullable private Limb hoveredLimb = null;

    /** Precomputed button centres (filled in init()). */
    private final int[] btnX = new int[6];
    private final int[] btnY = new int[6];
    private int centerX, centerY;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * @param onLimbSelected callback fired with the chosen Limb, or null for inspect mode
     */
    public LimbSelectionScreen(@Nullable Consumer<Limb> onLimbSelected) {
        super(Component.translatable("screen.avaliproject.limb_selection"));
        this.onLimbSelected = onLimbSelected;
    }

    // -------------------------------------------------------------------------
    // Init
    // -------------------------------------------------------------------------

    @Override
    protected void init() {
        super.init();
        centerX = width  / 2;
        centerY = height / 2;

        for (int i = 0; i < WHEEL_ORDER.length; i++) {
            double angleRad = Math.toRadians(-90.0 + i * 60.0); // start top, step CW
            btnX[i] = centerX + (int) (Math.cos(angleRad) * WHEEL_RADIUS);
            btnY[i] = centerY + (int) (Math.sin(angleRad) * WHEEL_RADIUS);
        }
    }

    // -------------------------------------------------------------------------
    // Rendering
    // -------------------------------------------------------------------------

    @Override
    public void renderBackground(GuiGraphics gfx, int mx, int my, float partial) {
        // Suppress MC's blur post-effect — we draw our own overlay in render().
    }

    @Override
    public void render(GuiGraphics gfx, int mx, int my, float partial) {
        gfx.fill(0, 0, width, height, COL_OVERLAY);

        updateHoveredLimb(mx, my);

        drawWheelSpokes(gfx);
        drawPlayerModel(gfx, mx, my);
        drawLimbButtons(gfx);
        drawTitle(gfx);
        drawTooltip(gfx);

        super.render(gfx, mx, my, partial);
    }

    /** Faint spokes/rim connecting the six button positions. */
    private void drawWheelSpokes(GuiGraphics gfx) {
        for (int i = 0; i < 6; i++) {
            drawLine(gfx, btnX[i], btnY[i], btnX[(i + 1) % 6], btnY[(i + 1) % 6], 0x33334466);
            drawLine(gfx, centerX, centerY, btnX[i], btnY[i], 0x22334466);
        }
    }

    /**
     * Renders the local player entity centred in the wheel.
     * Scale 22 keeps it small enough to leave room for the buttons.
     */
    private void drawPlayerModel(GuiGraphics gfx, int mx, int my) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        InventoryScreen.renderEntityInInventoryFollowsMouse(
                gfx,
                centerX - 28, centerY - 46,
                centerX + 28, centerY + 46,
                22, 0.0f, (float) mx, (float) my, player
        );
    }

    /** Draws all six buttons, reading bleed data from the client-side attachment. */
    private void drawLimbButtons(GuiGraphics gfx) {
        Player player = Minecraft.getInstance().player;
        LimbData data = (player != null) ? player.getData(ModAttachments.LIMB_DATA) : new LimbData();

        for (int i = 0; i < WHEEL_ORDER.length; i++) {
            Limb limb  = WHEEL_ORDER[i];
            int  bleed = data.getBleed(limb);
            drawButton(gfx, btnX[i], btnY[i], limb, bleed, limb == hoveredLimb);
        }
    }

    /**
     * Draws a single limb button centred at (cx, cy).
     *
     * Structure (top → bottom inside the 44×44 square):
     *   ┌──────────────────────┐
     *   │ [tier icon]   [name] │  ← icon top-right, name centred
     *   │       name line 2    │
     *   │  ████████░░░░░░░░░   │  ← bleed bar at bottom
     *   └──────────────────────┘
     *
     * The tier icon is only drawn when bleed > 0.
     */
    private void drawButton(GuiGraphics gfx, int cx, int cy, Limb limb, int bleed, boolean hovered) {
        int x = cx - BTN_R, y = cy - BTN_R;
        int w = BTN_R * 2,  h = BTN_R * 2;   // 44 × 44

        // Background + border
        gfx.fill(x, y, x + w, y + h, hovered ? COL_BTN_HOVER : COL_BTN_IDLE);
        int bc = hovered ? COL_BORDER_HOVER : COL_BORDER_IDLE;
        gfx.fill(x,         y,         x + w, y + 1,     bc);
        gfx.fill(x,         y + h - 1, x + w, y + h,     bc);
        gfx.fill(x,         y,         x + 1, y + h,     bc);
        gfx.fill(x + w - 1, y,         x + w, y + h,     bc);

        // ── Bleeding tier icon (top-right corner) ──────────────────────────
        // Rendered BEFORE the text so the text can overlap if the button is tiny.
        BleedingTier tier = BleedingTier.fromBleedValue(bleed);
        if (tier != null) {
            int iconX = x + w - ICON_SIZE - 2; // 2 px from right edge
            int iconY = y + 2;                  // 2 px from top edge
            drawTierIcon(gfx, tier, iconX, iconY);
        }

        // ── Limb name (centred; split on first space for two-word names) ────
        int tc = hovered ? COL_TEXT_HOVER : COL_TEXT_IDLE;
        String[] parts = limb.getDisplayName().getString().split(" ", 2);
        // If there's a tier icon occupying the top-right, shift text left slightly
        int textCX = (tier != null) ? cx - 4 : cx;
        int textY  = (parts.length == 2) ? cy - 8 : cy - 4;
        for (String part : parts) {
            gfx.drawCenteredString(font, part, textCX, textY, tc);
            textY += font.lineHeight + 1;
        }

        // ── Bleed bar (bottom of button) ──────────────────────────────────
        int barW = w - 8, barH = 4;
        int barX = x + 4, barY = y + h - barH - 4;
        gfx.fill(barX, barY, barX + barW, barY + barH, 0xFF111111); // track
        if (bleed > 0 && tier != null) {
            int fillW = Math.max(2, (int) ((float) bleed / LimbData.MAX_BLEED * barW));
            gfx.fill(barX, barY, barX + fillW, barY + barH, tier.getColor());
        }
    }

    private void drawTierIcon(GuiGraphics gfx, BleedingTier tier, int iconX, int iconY) {
        // 7-arg blit: assumes 256×256 texture, reads uOffset/vOffset in pixel coords.
        gfx.blit(tier.icon, iconX, iconY, 0, 0, ICON_SIZE, ICON_SIZE);
    }

    private void drawTitle(GuiGraphics gfx) {
        String title = (onLimbSelected != null)
                ? Component.translatable("screen.avaliproject.limb_selection.choose").getString()
                : Component.translatable("screen.avaliproject.limb_selection").getString();
        gfx.drawCenteredString(font, title, width / 2, 10, COL_TITLE);
        gfx.drawCenteredString(font, "[Esc / Right-click to close]", width / 2, height - 14, COL_HINT);
    }

    /**
     * Tooltip bar shown at the bottom of the screen while hovering a limb button.
     * Shows the limb name, bleeding tier name (or "Healthy"), and bleed percentage.
     */
    private void drawTooltip(GuiGraphics gfx) {
        if (hoveredLimb == null) return;

        Player player = Minecraft.getInstance().player;
        LimbData data = (player != null) ? player.getData(ModAttachments.LIMB_DATA) : new LimbData();
        int bleed = data.getBleed(hoveredLimb);

        BleedingTier tier = BleedingTier.fromBleedValue(bleed);

        String statusStr;
        int statusCol;
        if (tier == null) {
            statusStr = "Healthy";
            statusCol = COL_HEALTHY;
        } else {
            // e.g. "Heavy Bleeding (63%)"
            statusStr = tier.getDisplayName().getString() + " (" + bleed + "%)";
            statusCol = tier.getColor();
        }

        String label = hoveredLimb.getDisplayName().getString() + "  —  " + statusStr;
        gfx.drawCenteredString(font, label, width / 2, height - 28, statusCol);

        if (onLimbSelected != null) {
            gfx.drawCenteredString(font, "Click to treat this limb", width / 2, height - 16, 0x88FFFFFF);
        }
    }

    // -------------------------------------------------------------------------
    // Input
    // -------------------------------------------------------------------------

    private void updateHoveredLimb(int mx, int my) {
        hoveredLimb = null;
        for (int i = 0; i < WHEEL_ORDER.length; i++) {
            if (Math.abs(mx - btnX[i]) <= BTN_R && Math.abs(my - btnY[i]) <= BTN_R) {
                hoveredLimb = WHEEL_ORDER[i];
                return;
            }
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 1) { onClose(); return true; } // right-click → cancel

        if (button == 0 && hoveredLimb != null) {
            if (onLimbSelected != null) {
                // Callback sets the next screen; do NOT call onClose() or it overwrites it.
                onLimbSelected.accept(hoveredLimb);
            } else {
                onClose(); // inspect mode
            }
            return true;
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { onClose(); return true; } // Escape
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() { return false; }

    // -------------------------------------------------------------------------
    // Rendering utility
    // -------------------------------------------------------------------------

    /** Bresenham-style 1 px line using gfx.fill(). */
    private static void drawLine(GuiGraphics gfx, int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1, dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        if (steps == 0) return;
        float sx = dx / (float) steps, sy = dy / (float) steps;
        float cx = x1, cy = y1;
        for (int i = 0; i <= steps; i++) {
            gfx.fill((int) cx, (int) cy, (int) cx + 1, (int) cy + 1, color);
            cx += sx; cy += sy;
        }
    }
}
