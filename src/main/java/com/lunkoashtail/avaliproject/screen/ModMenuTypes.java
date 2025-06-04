package com.lunkoashtail.avaliproject.screen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.screen.custom.NanoloomMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, AvaliProject.MOD_ID);

    public static final RegistryObject<MenuType<NanoloomMenu>> NANOLOOM_MENU =
            MENUS.register("nanoloom_menu", () -> new MenuType<>(NanoloomMenu::new,
                    FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}