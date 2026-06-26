package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

/**
 * Declares and registers mod keybindings.
 *
 * OPEN_LIMB_WHEEL defaults to H (re-assignable in Options → Controls).
 * Key consumption (opening the screen) happens in ClientEventHandler.
 */
@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModKeybindings {

    /** Opens the standalone limb-status / selection wheel. */
    public static final KeyMapping OPEN_LIMB_WHEEL = new KeyMapping(
            "key.avaliproject.open_limb_wheel",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_H,
            "key.categories.avaliproject"
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_LIMB_WHEEL);
    }
}
