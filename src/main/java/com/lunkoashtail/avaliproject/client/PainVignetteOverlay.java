package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.limb.PainData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public class PainVignetteOverlay {

    private static final float FADE_IN_START = 30f;
    private static final int MAX_ALPHA = 70;

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        PainData pain = mc.player.getData(ModAttachments.PAIN_DATA);
        if (pain.get() <= FADE_IN_START) return;

        float fraction = (pain.get() - FADE_IN_START) / (PainData.MAX_PAIN - FADE_IN_START);
        int alpha = Math.round(fraction * MAX_ALPHA);
        int color = (alpha << 24) | 0x660000;

        GuiGraphics gfx = event.getGuiGraphics();
        gfx.fill(0, 0, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), color);
    }
}
