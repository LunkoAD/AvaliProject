package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.screen.custom.LimbSelectionScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

/**
 * Client-side game event handler.
 *
 * Listens for key presses and opens the limb wheel when the bound key fires.
 * Using InputEvent.Key is correct here: it fires exactly once per physical
 * key-down event, so the screen opens once per press without needing a tick loop.
 */
@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        // Only act on key-down (not repeat or release)
        if (event.getAction() != GLFW.GLFW_PRESS) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;

        if (ModKeybindings.OPEN_LIMB_WHEEL.consumeClick()) {
            mc.setScreen(new LimbSelectionScreen(null));
        }
    }
}
