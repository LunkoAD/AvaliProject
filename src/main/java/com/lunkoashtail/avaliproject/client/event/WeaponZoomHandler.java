package com.lunkoashtail.avaliproject.client.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.item.custom.HitscanWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public class WeaponZoomHandler {
    private static final double ZOOM_DIVISOR = 3.0;
    private static final float ZOOM_STEP = 0.18f;

    private static float prevZoomProgress = 0f;
    private static float zoomProgress = 0f;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        prevZoomProgress = zoomProgress;
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        boolean wantsZoom = player != null && minecraft.options != null && minecraft.options.keyUse.isDown()
                && player.getMainHandItem().getItem() instanceof HitscanWeapon;
        zoomProgress = Mth.clamp(zoomProgress + (wantsZoom ? ZOOM_STEP : -ZOOM_STEP), 0f, 1f);
    }

    @SubscribeEvent
    public static void onComputeFov(ViewportEvent.ComputeFov event) {
        float smoothedProgress = Mth.lerp((float) event.getPartialTick(), prevZoomProgress, zoomProgress);
        if (smoothedProgress <= 0f)
            return;
        double zoomedFov = event.getFOV() / ZOOM_DIVISOR;
        event.setFOV(Mth.lerp(smoothedProgress, event.getFOV(), zoomedFov));
    }
}
