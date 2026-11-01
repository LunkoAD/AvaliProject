package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.item.custom.AutoinjectorItem;
import com.lunkoashtail.avaliproject.item.custom.MorphineItem;
import com.lunkoashtail.avaliproject.network.AutoinjectorLoadPayload;
import com.lunkoashtail.avaliproject.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class AutoinjectorLoadScreen extends Screen {

    private enum Phase { SELECT, DRAW }

    private static final float DRAG_FOR_FULL = 90f;
    private static final int ROW_W = 220;
    private static final int ROW_H = 20;

    private static final int COL_BORDER    = 0xFF334466;
    private static final int COL_ROW       = 0xFF15151F;
    private static final int COL_ROW_HOVER = 0xFF223049;

    private record Candidate(int slotIndex, ItemStack stack, float contents) {}

    private final InteractionHand hand;
    private Phase phase = Phase.SELECT;
    private final List<Candidate> candidates = new ArrayList<>();
    private Candidate selected;
    private float vialMl;
    private float drawnAmount;

    public AutoinjectorLoadScreen(InteractionHand hand) {
        super(Component.translatable("screen.avaliproject.autoinjector_load"));
        this.hand = hand;
    }

    @Override
    protected void init() {
        super.init();
        candidates.clear();
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        vialMl = MorphineItem.dosageOf(player.getItemInHand(hand));

        Inventory inv = player.getInventory();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!(stack.getItem() instanceof AutoinjectorItem) || stack.getCount() != 1) continue;
            float contents = AutoinjectorItem.contentsOf(stack);
            if (contents >= AutoinjectorItem.CAPACITY_ML - 0.5f) continue;
            candidates.add(new Candidate(i, stack, contents));
        }
    }

    private float maxDraw() {
        return selected == null ? 0f : Math.min(vialMl, AutoinjectorItem.CAPACITY_ML - selected.contents());
    }

    @Override
    public void renderBackground(GuiGraphics gfx, int mx, int my, float partial) {
    }

    @Override
    public void render(GuiGraphics gfx, int mx, int my, float partial) {
        gfx.fill(0, 0, width, height, 0xCC000000);
        if (phase == Phase.SELECT) renderSelect(gfx, mx, my);
        else renderDraw(gfx);
        super.render(gfx, mx, my, partial);
    }

    private void renderSelect(GuiGraphics gfx, int mx, int my) {
        int top = height / 2 - (candidates.size() * ROW_H) / 2;
        gfx.drawCenteredString(font, "Select an autoinjector to fill", width / 2, top - 20, 0xFFDDDDFF);

        if (candidates.isEmpty()) {
            gfx.drawCenteredString(font, "No autoinjector with room in your inventory", width / 2, height / 2, 0xFFAA5555);
            gfx.drawCenteredString(font, "[Right-click or Esc to cancel]", width / 2, height - 18, 0x666666);
            return;
        }

        for (int i = 0; i < candidates.size(); i++) {
            Candidate c = candidates.get(i);
            int rx = width / 2 - ROW_W / 2;
            int ry = top + i * ROW_H;
            boolean hovered = mx >= rx && mx <= rx + ROW_W && my >= ry && my <= ry + ROW_H;
            gfx.fill(rx, ry, rx + ROW_W, ry + ROW_H, hovered ? COL_ROW_HOVER : COL_ROW);
            gfx.fill(rx, ry, rx + ROW_W, ry + 1, COL_BORDER);
            gfx.renderItem(c.stack(), rx + 2, ry + 2);
            gfx.drawString(font, "Autoinjector", rx + 22, ry + 6, 0xFFEEEEFF, false);
            String fill = c.contents() > 0.5f ? (int) c.contents() + " / " + (int) AutoinjectorItem.CAPACITY_ML + " mL" : "Empty";
            gfx.drawString(font, fill, rx + ROW_W - 70, ry + 6, 0xFF88CCFF, false);
        }
        gfx.drawCenteredString(font, "[Right-click or Esc to cancel]", width / 2, height - 18, 0x666666);
    }

    private void renderDraw(GuiGraphics gfx) {
        int cx = width / 2, cy = height / 2;
        gfx.drawCenteredString(font, "Filling autoinjector with morphine", cx, cy - 70, 0xFFDDDDFF);
        gfx.drawCenteredString(font, "Hold RIGHT-click and move mouse to adjust", cx, cy - 58, 0xAABBCC);

        gfx.pose().pushPose();
        gfx.pose().translate(cx - 24, cy - 30, 0);
        gfx.pose().scale(3f, 3f, 1f);
        gfx.renderItem(selected.stack(), 0, 0);
        gfx.pose().popPose();

        float max = maxDraw();
        float frac = max <= 0f ? 0f : drawnAmount / max;
        int barW = 160, barH = 10;
        int barX = cx - barW / 2, barY = cy + 60;
        gfx.fill(barX - 1, barY - 1, barX + barW + 1, barY + barH + 1, 0xFF111111);
        gfx.fill(barX, barY, barX + barW, barY + barH, 0xFF1C1C2A);
        gfx.fill(barX, barY, barX + (int) (barW * Math.min(1f, frac)), barY + barH, 0xFF9A5AC8);
        gfx.drawCenteredString(font, (int) drawnAmount + " / " + (int) max + " mL", cx, barY + barH + 6, 0x99BBEE);
        gfx.drawCenteredString(font, "[Hold RIGHT-click to adjust  |  Left-click to confirm  |  Esc to cancel]", cx, height - 18, 0x666666);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (phase == Phase.SELECT) {
            if (button == 1) { onClose(); return true; }
            if (button == 0 && !candidates.isEmpty()) {
                int top = height / 2 - (candidates.size() * ROW_H) / 2;
                for (int i = 0; i < candidates.size(); i++) {
                    int rx = width / 2 - ROW_W / 2;
                    int ry = top + i * ROW_H;
                    if (mx >= rx && mx <= rx + ROW_W && my >= ry && my <= ry + ROW_H) {
                        selected = candidates.get(i);
                        drawnAmount = maxDraw();
                        phase = Phase.DRAW;
                        return true;
                    }
                }
            }
            return super.mouseClicked(mx, my, button);
        }
        if (button == 0) {
            commit();
            return true;
        }
        return button == 1 || super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseDragged(double mx, double my, int button, double dx, double dy) {
        if (button != 1 || phase != Phase.DRAW) return super.mouseDragged(mx, my, button, dx, dy);
        float max = maxDraw();
        drawnAmount = Math.max(0f, Math.min(max, drawnAmount - (float) (dy / DRAG_FOR_FULL) * max));
        return true;
    }

    private void commit() {
        if (selected != null && drawnAmount > 0.5f) {
            PacketDistributor.sendToServer(new AutoinjectorLoadPayload(hand == InteractionHand.MAIN_HAND, selected.slotIndex(), drawnAmount));
            Player p = Minecraft.getInstance().player;
            if (p != null) {
                p.level().playLocalSound(p.getX(), p.getY(), p.getZ(), ModSounds.SYRINGE_STAB.get(), SoundSource.PLAYERS, 0.5f, 1.3f, false);
            }
        }
        onClose();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { onClose(); return true; }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
