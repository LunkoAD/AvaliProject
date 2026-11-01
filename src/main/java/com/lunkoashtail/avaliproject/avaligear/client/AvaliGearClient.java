package com.lunkoashtail.avaliproject.avaligear.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.avaligear.AvaliGear;
import com.lunkoashtail.avaliproject.avaligear.ship.CatalinaEntity;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.client.AvaliRenderer;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class AvaliGearClient {
    private static final String CATEGORY = "key.categories.avaliproject";
    public static final KeyMapping TOGGLE_VISOR = new KeyMapping("key.avaliproject.toggle_visor",
            InputConstants.Type.KEYSYM, InputConstants.KEY_V, CATEGORY);
    public static final KeyMapping SHIP_TAKEOFF_LAND = new KeyMapping("key.avaliproject.ship_takeoff_land",
            InputConstants.Type.KEYSYM, InputConstants.KEY_R, CATEGORY);
    public static final KeyMapping SHIP_DESCEND = new KeyMapping("key.avaliproject.ship_descend",
            InputConstants.Type.KEYSYM, InputConstants.KEY_X, CATEGORY);
    public static final KeyMapping SHIP_HOVER = new KeyMapping("key.avaliproject.ship_hover",
            InputConstants.Type.KEYSYM, InputConstants.KEY_N, CATEGORY);

    private AvaliGearClient() {
    }

    @SubscribeEvent
    public static void keys(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_VISOR);
        event.register(SHIP_TAKEOFF_LAND);
        event.register(SHIP_DESCEND);
        event.register(SHIP_HOVER);
    }

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        CatalinaEntity.clientVerticalInput = () -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.screen != null) return 0;
            if (mc.options.keyJump.isDown()) return 1;
            return SHIP_DESCEND.isDown() ? -1 : 0;
        };
    }

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AvaliGear.CATALINA, CatalinaRenderer::new);
    }

    @SubscribeEvent
    public static void layers(EntityRenderersEvent.AddLayers event) {
        AvaliRenderer renderer = event.getRenderer(ModEntities.AVALI.get());
        if (renderer != null) renderer.addLayer(new AvaliArmorLayer(renderer));
    }

    @SubscribeEvent
    public static void reload(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((ResourceManagerReloadListener) manager -> AvaliArmorLayer.clearCache());
    }
}
