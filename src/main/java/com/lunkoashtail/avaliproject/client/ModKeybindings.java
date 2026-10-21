package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;







@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public class ModKeybindings {

     
    public static final KeyMapping OPEN_LIMB_WHEEL = new KeyMapping(
            "key.avaliproject.open_limb_wheel",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_H,
            "key.categories.avaliproject"
    );

    public static final KeyMapping OPEN_PACK_GUI = new KeyMapping(
            "key.avaliproject.open_pack_gui",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_K,
            "key.categories.avaliproject"
    );

    public static final KeyMapping OPEN_CARRY_GUI = new KeyMapping(
            "key.avaliproject.open_carry_gui",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_J,
            "key.categories.avaliproject"
    );

    public static final KeyMapping OPEN_SPECIES_WARDROBE = new KeyMapping(
            "key.avaliproject.open_species_wardrobe",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_G,
            "key.categories.avaliproject"
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_LIMB_WHEEL);
        event.register(OPEN_PACK_GUI);
        event.register(OPEN_CARRY_GUI);
        event.register(OPEN_SPECIES_WARDROBE);
    }
}
