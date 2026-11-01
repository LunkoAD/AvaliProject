package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.block.custom.ProtogenColor;
import com.lunkoashtail.avaliproject.block.entity.custom.GrowthVatBlockEntity;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GrowthVatScreen extends ProtogenMachineScreen<GrowthVatMenu> {
    private static final String[] SLOT_LABELS = {
            "gui.avaliproject.growth_vat.label.dna",
            "gui.avaliproject.growth_vat.label.nutrient",
            "gui.avaliproject.growth_vat.label.embryo",
            "gui.avaliproject.growth_vat.label.dye"
    };
    private static final String[] SLOT_TOOLTIPS = {
            "gui.avaliproject.growth_vat.slot.dna",
            "gui.avaliproject.growth_vat.slot.nutrient",
            "gui.avaliproject.growth_vat.slot.embryo",
            "gui.avaliproject.growth_vat.slot.dye"
    };
    private static final int BAR_Y = 80;
    private static final int BAR_X = 14;
    private static final int BAR_HEIGHT = 12;
    private static final int STATUS_Y = 98;
    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 18;
    private static final int BUTTON_X = GrowthVatMenu.WIDTH - BUTTON_WIDTH - 14;
    private static final int BUTTON_Y = BAR_Y - 3;
    private static final int BAR_WIDTH = BUTTON_X - BAR_X - 6;

    private final ItemStack[] ghosts = {
            new ItemStack(ModItems.DNA.get()), new ItemStack(ModItems.NUTRIENT_PASTE.get()),
            new ItemStack(ModItems.EMBRYO.get()), new ItemStack(Items.LIGHT_BLUE_DYE)
    };

    public GrowthVatScreen(GrowthVatMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, GrowthVatMenu.WIDTH, GrowthVatMenu.INVENTORY_TOP + 82);
    }

    @Override
    protected void renderMachine(GuiGraphics gfx, int mouseX, int mouseY) {
        drawChip(gfx, font, leftPos + imageWidth - 62, topPos + 4, 54,
                Component.translatable("gui.avaliproject.protogen_station.power"), menu.isPowered());

        for (int slot = 0; slot < GrowthVatMenu.SLOT_X.length; slot++) {
            int slotX = leftPos + GrowthVatMenu.SLOT_X[slot];
            int slotY = topPos + GrowthVatMenu.SLOT_Y;
            drawSmallLabel(gfx, font, Component.translatable(SLOT_LABELS[slot]), slotX + 8, topPos + 28, ACCENT);
            if (vatSlot(slot).getItem().isEmpty()) drawGhost(gfx, ghosts[slot], slotX, slotY);
        }
        int embryoCenter = leftPos + GrowthVatMenu.SLOT_X[GrowthVatBlockEntity.SLOT_EMBRYO] + 8;
        if (menu.isGrowing()) {
            drawSmallLabel(gfx, font, Component.translatable("gui.avaliproject.growth_vat.incubating"), embryoCenter, topPos + 61, MUTED);
        }
        int dyeCenter = leftPos + GrowthVatMenu.SLOT_X[GrowthVatBlockEntity.SLOT_DYE] + 8;
        drawSmallLabel(gfx, font, Component.literal(ProtogenRosterPanel.prettySkin(chosenColor().getSerializedName())), dyeCenter, topPos + 61, TEXT);

        drawSmallLabel(gfx, font, Component.translatable("gui.avaliproject.growth_vat.progress"), leftPos + BAR_X + BAR_WIDTH / 2, topPos + BAR_Y - 9, ACCENT);
        float fraction = menu.isGrowing() ? menu.getProgressFraction() : 0.0F;
        drawBar(gfx, leftPos + BAR_X, topPos + BAR_Y, BAR_WIDTH, BAR_HEIGHT, fraction, menu.isPowered() ? 0xFF2FD3E6 : 0xFF4A6970);
        gfx.drawCenteredString(font, bold(Component.literal(Math.round(fraction * 100) + "%")),
                leftPos + BAR_X + BAR_WIDTH / 2, topPos + BAR_Y + 2, 0xFFFFFF);

        gfx.drawString(font, bold(statusLine()), leftPos + BAR_X, topPos + STATUS_Y, statusColor(), false);
        if (menu.isGrowing()) {
            gfx.drawString(font, bold(Component.translatable("gui.avaliproject.growth_vat.day",
                            String.format("%.1f", fraction * GrowthVatBlockEntity.GROWTH_DAYS), GrowthVatBlockEntity.GROWTH_DAYS, stageNumber(fraction))),
                    leftPos + BAR_X, topPos + STATUS_Y + 11, MUTED, false);
        }
        boolean hovered = inside(mouseX, mouseY, leftPos + BUTTON_X, topPos + BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        drawButton(gfx, font, leftPos + BUTTON_X, topPos + BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("gui.avaliproject.growth_vat.finish"), menu.isComplete(), hovered);
    }

    private static Component bold(Component text) {
        return text.copy().withStyle(ChatFormatting.BOLD);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        gfx.drawString(font, bold(title), titleLabelX, titleLabelY, ACCENT, false);
        gfx.drawString(font, bold(playerInventoryTitle), inventoryLabelX, inventoryLabelY, MUTED, false);
    }

    private Slot vatSlot(int index) {
        return menu.getSlot(ProtogenMachineMenu.PLAYER_SLOT_COUNT + index);
    }

    private ProtogenColor chosenColor() {
        return vatSlot(GrowthVatBlockEntity.SLOT_DYE).getItem().getItem() instanceof DyeItem dye
                ? ProtogenColor.fromDye(dye.getDyeColor()) : ProtogenColor.CLASSIC;
    }

    private static int stageNumber(float fraction) {
        return 1 + Math.min(2, (int) (fraction * 3));
    }

    private Component statusLine() {
        if (menu.isComplete()) return Component.translatable("gui.avaliproject.growth_vat.ready");
        if (!menu.isPowered()) return Component.translatable("gui.avaliproject.growth_vat.no_power");
        if (!menu.isGrowing()) return Component.translatable("gui.avaliproject.growth_vat.waiting");
        return Component.translatable("gui.avaliproject.growth_vat.growing");
    }

    private int statusColor() {
        if (menu.isComplete()) return GOOD_TEXT;
        if (!menu.isPowered()) return BAD_TEXT;
        return TEXT;
    }

    @Override
    protected boolean handleMouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && menu.isComplete() && inside(mouseX, mouseY, leftPos + BUTTON_X, topPos + BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT)
                && minecraft != null && minecraft.gameMode != null) {
            minecraft.gameMode.handleInventoryButtonClick(menu.containerId, GrowthVatMenu.BUTTON_FINISH);
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        return super.handleMouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void renderTooltip(GuiGraphics gfx, int mouseX, int mouseY) {
        if (menu.getCarried().isEmpty() && hoveredSlot != null && !hoveredSlot.hasItem()) {
            int index = hoveredSlot.index - ProtogenMachineMenu.PLAYER_SLOT_COUNT;
            if (index >= 0 && index < SLOT_TOOLTIPS.length) {
                gfx.renderTooltip(font, Component.translatable(SLOT_TOOLTIPS[index]), mouseX, mouseY);
                return;
            }
        }
        super.renderTooltip(gfx, mouseX, mouseY);
    }
}
