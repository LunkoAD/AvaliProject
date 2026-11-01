package com.lunkoashtail.avaliproject.compat;

import com.lunkoashtail.avaliproject.screen.custom.ProtogenMachineScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.client.renderer.Rect2i;

import java.util.List;

final class JEIProtogenScreens {
    private JEIProtogenScreens() {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static void register(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler((Class) ProtogenMachineScreen.class, new IGuiContainerHandler<ProtogenMachineScreen<?>>() {
            @Override
            public List<Rect2i> getGuiExtraAreas(ProtogenMachineScreen<?> screen) {
                return screen.getExtraAreas();
            }
        });
    }
}
