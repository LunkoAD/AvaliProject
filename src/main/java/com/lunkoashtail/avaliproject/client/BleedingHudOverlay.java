package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.BleedingTier;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

/**
 * Renders active bleeding tier icons on the HUD (top-right corner).
 *
 * One row per bleeding limb, stacked downward:
 *
 *   "Left Arm (42%)"  [icon]
 *   "Head (80%)"      [icon]
 *
 * Only limbs with bleed > 0 are shown.
 * Hidden when F1 (hideGui) is active.
 */
@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class BleedingHudOverlay {

    private static final int ICON_SIZE = 16;
    private static final int ROW_H    = ICON_SIZE + 2;
    private static final int MARGIN   = 4; // gap from screen edge and between rows

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        LimbData data = mc.player.getData(ModAttachments.LIMB_DATA);
        if (!data.isAnyBleeding()) return;

        GuiGraphics gfx = event.getGuiGraphics();
        int screenW = mc.getWindow().getGuiScaledWidth();
        int iconX   = screenW - ICON_SIZE - MARGIN;

        int y = MARGIN;
        for (Limb limb : Limb.values()) {
            int bleed = data.getBleed(limb);
            BleedingTier tier = BleedingTier.fromBleedValue(bleed);
            if (tier == null) continue;

            // Effect icon on the far right
            gfx.blit(tier.icon, iconX, y, 0, 0, ICON_SIZE, ICON_SIZE);

            // "Limb Name (bleed%)" label, right-aligned against the icon
            String label = limb.getDisplayName().getString() + " (" + bleed + "%)";
            int labelX = iconX - mc.font.width(label) - 3;
            int labelY = y + (ICON_SIZE - mc.font.lineHeight) / 2;
            gfx.drawString(mc.font, label, labelX, labelY, tier.getColor(), true);

            y += ROW_H;
        }
    }
}
