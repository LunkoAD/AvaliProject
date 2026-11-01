package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.client.PackHeadRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;


public class SynchPodScreen extends ProtogenMachineScreen<SynchPodMenu> {
    private static final int HEAD_SIZE = 56;

    private final ProtogenRosterPanel roster;

    public SynchPodScreen(SynchPodMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 150);
        this.roster = new ProtogenRosterPanel(menu.getBlockPos(), menu.blockEntity::registeredProtogen);
    }

    @Override
    protected void init() {
        super.init();
        leftPos += (ProtogenRosterPanel.WIDTH + ProtogenRosterPanel.GAP) / 2;
        roster.setBounds(leftPos - ProtogenRosterPanel.WIDTH - ProtogenRosterPanel.GAP, topPos, imageHeight);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        roster.tick();
    }

    @Override
    protected void renderMachine(GuiGraphics gfx, int mouseX, int mouseY) {
        roster.render(gfx, font, mouseX, mouseY);
        drawChip(gfx, font, leftPos + imageWidth - 62, topPos + 4, 54, Component.translatable("gui.avaliproject.protogen_station.power"), menu.isPowered());

        int headX = leftPos + 14;
        int headY = topPos + 28;
        drawInset(gfx, headX - 1, headY - 1, HEAD_SIZE + 2, HEAD_SIZE + 2, 0xFF0C1215);
        int textX = headX + HEAD_SIZE + 10;
        if (menu.blockEntity.isOccupied()) {
            LivingEntity dummy = ProtogenRosterPanel.dummyFor(menu.blockEntity.getStoredSkin());
            if (dummy != null) PackHeadRenderer.renderHead(gfx, dummy, headX, headY, HEAD_SIZE);
            gfx.drawString(font, Component.translatable("gui.avaliproject.synch_pod.occupied"), textX, headY + 10, TEXT, false);
            gfx.drawString(font, ProtogenRosterPanel.prettySkin(menu.blockEntity.getStoredSkin()), textX, headY + 24, ACCENT, false);
        } else {
            gfx.drawString(font, Component.translatable("gui.avaliproject.synch_pod.empty"), textX, headY + 10, TEXT, false);
            gfx.drawWordWrap(font, Component.translatable("gui.avaliproject.synch_pod.select"), textX, headY + 24, imageWidth - (textX - leftPos) - 10, TEXT);
        }
        gfx.drawWordWrap(font, Component.translatable("gui.avaliproject.synch_pod.hint"), leftPos + 14, topPos + 100, imageWidth - 28, MUTED);
    }

    @Override
    protected boolean handleMouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && roster.mouseClicked(mouseX, mouseY)) return true;
        return super.handleMouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected boolean handleMouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return roster.mouseScrolled(mouseX, mouseY, scrollY) || super.handleMouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        boolean inRoster = mouseX >= guiLeft - ProtogenRosterPanel.WIDTH - ProtogenRosterPanel.GAP && mouseX < guiLeft
                && mouseY >= guiTop && mouseY < guiTop + imageHeight;
        return !inRoster && super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, mouseButton);
    }

    @Override
    protected int sidePanelWidth() {
        return ProtogenRosterPanel.WIDTH + ProtogenRosterPanel.GAP;
    }
}
