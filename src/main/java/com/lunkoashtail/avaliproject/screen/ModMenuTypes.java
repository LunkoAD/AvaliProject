package com.lunkoashtail.avaliproject.screen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.screen.custom.AugmentMenu;
import com.lunkoashtail.avaliproject.screen.custom.AvaliEquipMenu;
import com.lunkoashtail.avaliproject.screen.custom.GrowthVatMenu;
import com.lunkoashtail.avaliproject.screen.custom.NanoloomMenu;
import com.lunkoashtail.avaliproject.screen.custom.ProtogenGeneratorMenu;
import com.lunkoashtail.avaliproject.screen.custom.ProtogenStationMenu;
import com.lunkoashtail.avaliproject.screen.custom.SynchPodMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, AvaliProject.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<NanoloomMenu>> NANOLOOM_MENU =
            registerMenuType("nanoloom_menu", NanoloomMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<AvaliEquipMenu>> AVALI_EQUIP_MENU =
            registerMenuType("avali_equip_menu", AvaliEquipMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<AugmentMenu>> AUGMENT_MENU =
            registerMenuType("augment_menu", AugmentMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<ProtogenGeneratorMenu>> PROTOGEN_GENERATOR_MENU =
            registerMenuType("protogen_generator_menu", ProtogenGeneratorMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<ProtogenStationMenu>> PROTOGEN_STATION_MENU =
            registerMenuType("protogen_station_menu", ProtogenStationMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<GrowthVatMenu>> GROWTH_VAT_MENU =
            registerMenuType("growth_vat_menu", GrowthVatMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<SynchPodMenu>> SYNCH_POD_MENU =
            registerMenuType("synch_pod_menu", SynchPodMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,
                                                                                                              IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}