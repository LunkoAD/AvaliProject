package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.item.custom.CanteenItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public class CanteenHudOverlay {

    private static final int BAR_W = 60;
    private static final int BAR_H = 4;
    private static final int BAR_Y_OFFSET = 32;

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;
        if (!mc.player.isUsingItem()) return;

        ItemStack using = mc.player.getUseItem();
        if (!(using.getItem() instanceof CanteenItem)) return;

        int remaining = mc.player.getUseItemRemainingTicks();
        float progress = 1f - (remaining / (float) CanteenItem.FILL_DURATION_TICKS);
        progress = Math.max(0f, Math.min(1f, progress));

        GuiGraphics gfx = event.getGuiGraphics();
        int screenW = mc.getWindow().getGuiScaledWidth();
        int screenH = mc.getWindow().getGuiScaledHeight();
        int barX = (screenW - BAR_W) / 2;
        int barY = screenH - BAR_Y_OFFSET;

        gfx.fill(barX - 1, barY - 1, barX + BAR_W + 1, barY + BAR_H + 1, 0xFF111111);
        gfx.fill(barX, barY, barX + BAR_W, barY + BAR_H, 0xFF1C1C2A);
        gfx.fill(barX, barY, barX + (int) (BAR_W * progress), barY + BAR_H, 0xFF4488DD);
    }
}
