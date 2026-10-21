package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.command.TestMinigameCommand;
import com.lunkoashtail.avaliproject.creativetab.GalaxyCreativeScreen;
import com.lunkoashtail.avaliproject.network.CarryOpenPayload;
import com.lunkoashtail.avaliproject.network.PackOpenPayload;
import com.lunkoashtail.avaliproject.screen.custom.LimbSelectionScreen;
import com.lunkoashtail.avaliproject.screen.custom.SpeciesWardrobeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;








@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        
        if (event.getAction() != GLFW.GLFW_PRESS) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;

        if (ModKeybindings.OPEN_LIMB_WHEEL.consumeClick()) {
            mc.setScreen(new LimbSelectionScreen(null));
        }
        if (ModKeybindings.OPEN_PACK_GUI.consumeClick()) {
            PacketDistributor.sendToServer(new PackOpenPayload());
        }
        if (ModKeybindings.OPEN_CARRY_GUI.consumeClick()) {
            PacketDistributor.sendToServer(new CarryOpenPayload());
        }
        if (ModKeybindings.OPEN_SPECIES_WARDROBE.consumeClick()) {
            mc.setScreen(new SpeciesWardrobeScreen());
        }
    }

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(TestMinigameCommand.build());
    }

    @SubscribeEvent
    public static void onScreenOpening(ScreenEvent.Opening event) {
        if (!(event.getNewScreen() instanceof CreativeModeInventoryScreen) || event.getNewScreen() instanceof GalaxyCreativeScreen) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        event.setNewScreen(new GalaxyCreativeScreen(
                player,
                player.connection.enabledFeatures(),
                mc.options.operatorItemsTab().get()
        ));
    }
}
