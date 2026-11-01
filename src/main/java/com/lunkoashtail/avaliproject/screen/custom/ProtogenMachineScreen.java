package com.lunkoashtail.avaliproject.screen.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class ProtogenMachineScreen<M extends ProtogenMachineMenu<?>> extends AbstractContainerScreen<M> {
    public static final int TEXT = 0xA9DDE6;
    public static final int MUTED = 0x6F8F98;
    public static final int ACCENT = 0x3FD8EA;
    public static final int GOOD = 0xFF2F9E6E;
    public static final int BAD = 0xFFB0413E;
    public static final int WARN = 0xFFC7922E;
    public static final int GOOD_TEXT = 0x7FF2C0;
    public static final int BAD_TEXT = 0xFF9C98;
    public static final int WARN_TEXT = 0xFFD27F;
    public static final int ROW = 0xFF141B20;
    public static final int ROW_HOVER = 0xFF22313A;
    public static final int ROW_SELECTED = 0xFF1C5A4C;
    private static final int OUTLINE = 0xFF05080A;
    private static final int FACE = 0xFF161D22;
    private static final int FRAME = 0xFF2A3A42;
    private static final int GLOW = 0xFF2BB8C9;
    private static final int SLOT_FACE = 0xFF0C1215;
    private static final int SLOT_EDGE = 0xFF2C4049;
    private static final float REFERENCE_WIDTH = 400.0F;
    private static final float REFERENCE_HEIGHT = 320.0F;
    private static final float MIN_SCALE = 0.5F;
    private static final float MAX_SCALE = 2.0F;
    private static final int SCREEN_MARGIN = 16;


    protected ProtogenMachineScreen(M menu, Inventory inventory, Component title, int imageHeight) {
        this(menu, inventory, title, ProtogenMachineMenu.WIDTH, imageHeight);
    }

    protected ProtogenMachineScreen(M menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
        super(menu, inventory, title);
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.inventoryLabelX = menu.getPlayerInventoryLeft();
        this.inventoryLabelY = imageHeight - 94;
    }

    protected int sidePanelWidth() {
        return 0;
    }

    protected float scaleFactor() {
        var window = Minecraft.getInstance().getWindow();
        float width = window.getGuiScaledWidth();
        float height = window.getGuiScaledHeight();
        float scale = Math.min(width / REFERENCE_WIDTH, height / REFERENCE_HEIGHT);
        float fit = Math.min(width / (imageWidth + sidePanelWidth() + SCREEN_MARGIN), height / (imageHeight + SCREEN_MARGIN));
        return Mth.clamp(Math.min(scale, fit), MIN_SCALE, MAX_SCALE);
    }

    @Override
    protected void init() {
        super.init();
        float scale = scaleFactor();
        leftPos = (int) ((width / scale - imageWidth) / 2);
        topPos = (int) ((height / scale - imageHeight) / 2);
    }

    @Override
    public void renderBackground(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        renderBg(gfx, partialTick, mouseX, mouseY);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        renderTransparentBackground(gfx);
        float scale = scaleFactor();
        int scaledMouseX = (int) (mouseX / scale);
        int scaledMouseY = (int) (mouseY / scale);
        gfx.pose().pushPose();
        gfx.pose().scale(scale, scale, 1.0F);
        super.render(gfx, scaledMouseX, scaledMouseY, partialTick);
        renderTooltip(gfx, scaledMouseX, scaledMouseY);
        gfx.pose().popPose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float scale = scaleFactor();
        return handleMouseClicked(mouseX / scale, mouseY / scale, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        float scale = scaleFactor();
        return super.mouseReleased(mouseX / scale, mouseY / scale, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        float scale = scaleFactor();
        return super.mouseDragged(mouseX / scale, mouseY / scale, button, dragX / scale, dragY / scale);
    }

    protected boolean handleMouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        float scale = scaleFactor();
        return handleMouseScrolled(mouseX / scale, mouseY / scale, scrollX, scrollY);
    }

    protected boolean handleMouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        drawPanel(gfx, leftPos, topPos, imageWidth, imageHeight);
        gfx.fill(leftPos + 4, topPos + 17, leftPos + imageWidth - 4, topPos + 18, FRAME);
        if (menu.hasPlayerInventory()) {
            int line = topPos + inventoryLabelY - 4;
            gfx.fill(leftPos + 4, line, leftPos + imageWidth - 4, line + 1, FRAME);
        }
        for (Slot slot : menu.slots) {
            drawSlot(gfx, leftPos + slot.x - 1, topPos + slot.y - 1);
        }
        renderMachine(gfx, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        gfx.drawString(font, title, titleLabelX, titleLabelY, ACCENT, false);
        if (menu.hasPlayerInventory()) {
            gfx.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, MUTED, false);
        }
    }

    protected abstract void renderMachine(GuiGraphics gfx, int mouseX, int mouseY);

    public List<Rect2i> getExtraAreas() {
        float scale = scaleFactor();
        int left = Mth.floor((leftPos - sidePanelWidth()) * scale);
        int top = Mth.floor(topPos * scale);
        return List.of(new Rect2i(left, top, Mth.ceil((imageWidth + sidePanelWidth()) * scale), Mth.ceil(imageHeight * scale)));
    }

    public static void drawPanel(GuiGraphics gfx, int x, int y, int width, int height) {
        gfx.fill(x + 1, y, x + width - 1, y + height, OUTLINE);
        gfx.fill(x, y + 1, x + width, y + height - 1, OUTLINE);
        gfx.fill(x + 1, y + 1, x + width - 1, y + height - 1, FRAME);
        gfx.fill(x + 2, y + 2, x + width - 2, y + height - 2, FACE);
        gfx.fill(x + 2, y + 2, x + 8, y + 3, GLOW);
        gfx.fill(x + 2, y + 2, x + 3, y + 8, GLOW);
        gfx.fill(x + width - 8, y + height - 3, x + width - 2, y + height - 2, GLOW);
        gfx.fill(x + width - 3, y + height - 8, x + width - 2, y + height - 2, GLOW);
    }

    public static void drawSlot(GuiGraphics gfx, int x, int y) {
        drawInset(gfx, x, y, 18, 18, SLOT_FACE);
        gfx.fill(x, y, x + 3, y + 1, GLOW);
        gfx.fill(x, y, x + 1, y + 3, GLOW);
        gfx.fill(x + 15, y + 17, x + 18, y + 18, GLOW);
        gfx.fill(x + 17, y + 15, x + 18, y + 18, GLOW);
    }

    public static void drawInset(GuiGraphics gfx, int x, int y, int width, int height, int face) {
        gfx.fill(x, y, x + width, y + height, SLOT_EDGE);
        gfx.fill(x + 1, y + 1, x + width - 1, y + height - 1, face);
    }

    public static void drawBar(GuiGraphics gfx, int x, int y, int width, int height, float fraction, int color) {
        drawInset(gfx, x, y, width, height, SLOT_FACE);
        for (int stripe = x + 2; stripe < x + width - 2; stripe += 4) {
            gfx.fill(stripe, y + 2, stripe + 1, y + height - 2, 0xFF141E23);
        }
        int filled = Math.round((width - 4) * Math.max(0.0F, Math.min(1.0F, fraction)));
        if (filled > 0) {
            gfx.fill(x + 2, y + 2, x + 2 + filled, y + height - 2, color);
            gfx.fill(x + 2, y + 2, x + 2 + filled, y + 3, 0x60FFFFFF);
        }
    }

    public static final int CHIP_HEIGHT = 14;

    public static void drawChip(GuiGraphics gfx, Font font, int x, int y, int width, Component label, boolean ok) {
        gfx.fill(x, y, x + width, y + CHIP_HEIGHT, ok ? GOOD : BAD);
        gfx.fill(x + 1, y + 1, x + width - 1, y + CHIP_HEIGHT - 1, ok ? 0xFF143A2C : 0xFF3A1515);
        gfx.drawCenteredString(font, label, x + width / 2, y + 3, ok ? GOOD_TEXT : BAD_TEXT);
    }

    public static void drawButton(GuiGraphics gfx, Font font, int x, int y, int width, int height, Component label,
                                  boolean enabled, boolean hovered) {
        int edge = enabled ? GLOW : FRAME;
        int face = !enabled ? 0xFF11171B : hovered ? 0xFF1D4550 : 0xFF14303A;
        gfx.fill(x, y, x + width, y + height, edge);
        gfx.fill(x + 1, y + 1, x + width - 1, y + height - 1, face);
        gfx.drawCenteredString(font, label, x + width / 2, y + (height - 8) / 2, enabled ? 0xE6FCFF : MUTED);
    }

    public static void drawSmallLabel(GuiGraphics gfx, Font font, Component label, int centerX, int y, int color) {
        gfx.pose().pushPose();
        gfx.pose().translate(centerX, y, 0);
        gfx.pose().scale(0.8F, 0.8F, 1.0F);
        Component bold = label.copy().withStyle(ChatFormatting.BOLD);
        gfx.drawString(font, bold, -font.width(bold) / 2, 0, color, false);
        gfx.pose().popPose();
    }

    public static void drawGhost(GuiGraphics gfx, ItemStack stack, int x, int y) {
        gfx.renderFakeItem(stack, x, y);
        gfx.fill(RenderType.guiGhostRecipeOverlay(), x, y, x + 16, y + 16, 0xA00C1215);
    }

    protected static boolean inside(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
