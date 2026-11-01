package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenWorkStationBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


public class ProtogenStationScreen extends ProtogenMachineScreen<ProtogenStationMenu> {
    private static final ItemStack[] TOOL_GHOSTS = {
            new ItemStack(Items.IRON_PICKAXE), new ItemStack(Items.IRON_SHOVEL), new ItemStack(Items.IRON_AXE)
    };

    private final ProtogenRosterPanel roster;

    public ProtogenStationScreen(ProtogenStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, ProtogenStationMenu.inventoryTop(menu.getKind()) + 82);
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
        int left = leftPos + ProtogenMachineMenu.INVENTORY_LEFT;
        int chipWidth = 52;
        int chipStep = (162 - chipWidth) / 2;
        drawChip(gfx, font, left, topPos + 22, chipWidth, Component.translatable("gui.avaliproject.protogen_station.power"),
                menu.hasStatus(ProtogenWorkStationBlockEntity.STATUS_POWERED));
        drawChip(gfx, font, left + chipStep, topPos + 22, chipWidth, Component.translatable("gui.avaliproject.protogen_station.protogen"),
                menu.hasStatus(ProtogenWorkStationBlockEntity.STATUS_PROTOGEN));
        drawChip(gfx, font, left + chipStep * 2, topPos + 22, chipWidth, Component.translatable("gui.avaliproject.protogen_station.bed"),
                menu.hasStatus(ProtogenWorkStationBlockEntity.STATUS_BED));
        gfx.drawString(font, statusLine(), left, topPos + 42, TEXT, false);

        switch (menu.getKind()) {
            case MINING -> renderMining(gfx);
            case RESEARCH -> renderResearch(gfx);
            default -> {
            }
        }
    }

    private void renderMining(GuiGraphics gfx) {
        for (int slot = 0; slot < ProtogenWorkStationBlockEntity.TOOL_SLOTS; slot++) {
            if (menu.getSlot(ProtogenMachineMenu.PLAYER_SLOT_COUNT + slot).getItem().isEmpty()) {
                drawGhost(gfx, TOOL_GHOSTS[slot], leftPos + ProtogenMachineMenu.INVENTORY_LEFT + slot * 18, topPos + ProtogenStationMenu.TOOL_ROW_Y);
            }
        }
        gfx.drawString(font, Component.translatable("gui.avaliproject.protogen_station.tools"),
                leftPos + ProtogenMachineMenu.INVENTORY_LEFT + 60, topPos + ProtogenStationMenu.TOOL_ROW_Y + 5, TEXT, false);
        int max = Math.max(1, menu.getMaxProgress());
        drawBar(gfx, leftPos + imageWidth - ProtogenMachineMenu.INVENTORY_LEFT - 72, topPos + ProtogenStationMenu.TOOL_ROW_Y + 3, 72, 12,
                menu.getProgress() / (float) max, 0xFF7A6A55);
    }

    private void renderResearch(GuiGraphics gfx) {
        int max = Math.max(1, menu.getMaxProgress());
        drawBar(gfx, leftPos + ProtogenMachineMenu.INVENTORY_LEFT, topPos + ProtogenStationMenu.RESEARCH_BAR_Y, 112, 12,
                menu.getProgress() / (float) max, 0xFF7FD63F);
        gfx.drawString(font, Component.translatable("gui.avaliproject.protogen_station.bottles", menu.getBottlesMade()),
                leftPos + ProtogenMachineMenu.INVENTORY_LEFT + 118, topPos + ProtogenStationMenu.RESEARCH_BAR_Y + 2, TEXT, false);
    }

    private Component statusLine() {
        if (!menu.hasStatus(ProtogenWorkStationBlockEntity.STATUS_POWERED)) return Component.translatable("gui.avaliproject.protogen_station.need_power");
        if (!menu.hasStatus(ProtogenWorkStationBlockEntity.STATUS_PROTOGEN)) return Component.translatable("gui.avaliproject.protogen_station.need_protogen");
        if (!menu.hasStatus(ProtogenWorkStationBlockEntity.STATUS_BED)) return Component.translatable("gui.avaliproject.protogen_station.need_bed");
        if (menu.hasStatus(ProtogenWorkStationBlockEntity.STATUS_FULL)) return Component.translatable("gui.avaliproject.protogen_station.full");
        if (menu.hasStatus(ProtogenWorkStationBlockEntity.STATUS_NO_TOOL)) return Component.translatable("gui.avaliproject.protogen_station.need_tool");
        if (menu.hasStatus(ProtogenWorkStationBlockEntity.STATUS_DONE)) return Component.translatable("gui.avaliproject.protogen_station.done");
        return switch (menu.getKind()) {
            case MINING -> Component.translatable("gui.avaliproject.protogen_station.mining", menu.getBlockPos().getY() - menu.getProgress());
            case FARMING -> Component.translatable("gui.avaliproject.protogen_station.farming");
            default -> Component.translatable("gui.avaliproject.protogen_station.researching");
        };
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
