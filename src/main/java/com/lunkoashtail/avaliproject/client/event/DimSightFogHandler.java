package com.lunkoashtail.avaliproject.client.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public final class DimSightFogHandler {
    private static final float VISIBLE_DISTANCE = 8.0F;
    private static final float TERRAIN_FOG_START_SHARE = 0.25F;
    private static final float SKY_FOG_END_SHARE = 0.8F;
    private static final float FADE_STEP = 0.1F;

    private static float prevProgress;
    private static float progress;

    private DimSightFogHandler() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        prevProgress = progress;
        Player player = Minecraft.getInstance().player;
        boolean dimmed = player != null && player.hasEffect(ModEffects.DIM_SIGHT);
        progress = Mth.clamp(progress + (dimmed ? FADE_STEP : -FADE_STEP), 0.0F, 1.0F);
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        float amount = amount(event.getPartialTick());
        if (amount <= 0.0F) return;
        boolean sky = event.getMode() == FogRenderer.FogMode.FOG_SKY;
        float targetNear = sky ? 0.0F : VISIBLE_DISTANCE * TERRAIN_FOG_START_SHARE;
        float targetFar = sky ? VISIBLE_DISTANCE * SKY_FOG_END_SHARE : VISIBLE_DISTANCE;
        event.setNearPlaneDistance(Mth.lerp(amount, event.getNearPlaneDistance(), Math.min(event.getNearPlaneDistance(), targetNear)));
        event.setFarPlaneDistance(Mth.lerp(amount, event.getFarPlaneDistance(), Math.min(event.getFarPlaneDistance(), targetFar)));
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        float amount = amount(event.getPartialTick());
        if (amount <= 0.0F) return;
        float keep = 1.0F - amount;
        event.setRed(event.getRed() * keep);
        event.setGreen(event.getGreen() * keep);
        event.setBlue(event.getBlue() * keep);
    }

    private static float amount(double partialTick) {
        return Mth.lerp((float) partialTick, prevProgress, progress);
    }
}
