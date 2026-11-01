package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenGeneratorBlockEntity;
import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenPower;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ProtogenGeneratorScreen extends ProtogenMachineScreen<ProtogenGeneratorMenu> {
    private static final ResourceLocation FLAME = ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");
    private static final int SWITCH_X = 110;
    private static final int SWITCH_Y = 24;
    private static final int SWITCH_WIDTH = 70;
    private static final int SWITCH_HEIGHT = 22;
    private static final int KNOB_WIDTH = 24;
    private static final int INFO_X = 64;

    public ProtogenGeneratorScreen(ProtogenGeneratorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, ProtogenGeneratorMenu.INVENTORY_TOP + 82);
    }

    @Override
    protected void renderMachine(GuiGraphics gfx, int mouseX, int mouseY) {
        int fuelX = leftPos + ProtogenGeneratorMenu.FUEL_X;
        int fuelY = topPos + ProtogenGeneratorMenu.FUEL_Y;
        if (menu.getSlot(ProtogenMachineMenu.PLAYER_SLOT_COUNT).getItem().isEmpty()) {
            drawGhost(gfx, new ItemStack(ModItems.ARCAITES_CRYSTAL.get()), fuelX, fuelY);
        }
        int burn = menu.getBurnTime();
        if (burn > 0) {
            int lit = Mth.ceil(burn * 13.0F / ProtogenGeneratorBlockEntity.BURN_TICKS) + 1;
            gfx.blitSprite(FLAME, 14, 14, 0, 14 - lit, fuelX + 1, fuelY - 18 + 14 - lit, 14, lit);
        }

        boolean on = menu.isSwitchedOn();
        int switchX = leftPos + SWITCH_X;
        int switchY = topPos + SWITCH_Y;
        drawInset(gfx, switchX, switchY, SWITCH_WIDTH, SWITCH_HEIGHT, on ? GOOD : BAD);
        int knobX = on ? switchX + SWITCH_WIDTH - KNOB_WIDTH - 2 : switchX + 2;
        drawPanel(gfx, knobX, switchY + 2, KNOB_WIDTH, SWITCH_HEIGHT - 4);
        Component state = Component.translatable(on ? "gui.avaliproject.protogen_generator.on" : "gui.avaliproject.protogen_generator.off");
        int labelCenter = on ? switchX + (SWITCH_WIDTH - KNOB_WIDTH) / 2 : switchX + KNOB_WIDTH + 2 + (SWITCH_WIDTH - KNOB_WIDTH - 2) / 2;
        gfx.drawCenteredString(font, state, labelCenter, switchY + (SWITCH_HEIGHT - 8) / 2, 0xFFFFFF);

        Component status;
        int color;
        if (on && burn > 0) {
            status = Component.translatable("gui.avaliproject.protogen_generator.running");
            color = GOOD_TEXT;
        } else if (on) {
            status = Component.translatable("gui.avaliproject.protogen_generator.no_fuel");
            color = WARN_TEXT;
        } else {
            status = Component.translatable("gui.avaliproject.protogen_generator.stopped");
            color = BAD_TEXT;
        }
        gfx.drawString(font, status, leftPos + INFO_X, topPos + 54, color, false);
        gfx.drawString(font, Component.translatable("gui.avaliproject.protogen_generator.range", ProtogenPower.RANGE),
                leftPos + INFO_X, topPos + 66, TEXT, false);
    }

    @Override
    protected boolean handleMouseClicked(double mouseX, double mouseY, int button) {
        double localX = mouseX - leftPos - SWITCH_X;
        double localY = mouseY - topPos - SWITCH_Y;
        if (button == 0 && localX >= 0 && localX < SWITCH_WIDTH && localY >= 0 && localY < SWITCH_HEIGHT && minecraft != null && minecraft.gameMode != null) {
            minecraft.gameMode.handleInventoryButtonClick(menu.containerId, ProtogenGeneratorMenu.BUTTON_TOGGLE);
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        return super.handleMouseClicked(mouseX, mouseY, button);
    }
}
