package com.lunkoashtail.avaliproject;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.components.ModDataComponents;
import com.lunkoashtail.avaliproject.item.ModCreativeModeTabs;
import com.lunkoashtail.avaliproject.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AvaliProject.MOD_ID)
public class AvaliProject {
    public static final String MOD_ID = "avaliproject";
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public AvaliProject(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.LUME);
            event.accept(ModItems.LUME_BIT);
            event.accept(ModItems.THERMAL_CRYSTAL);
            event.accept(ModItems.SYNC_CRYSTAL);
            event.accept(ModItems.AERO_CRYSTAL);
            event.accept(ModItems.AEROGEL);
            event.accept(ModItems.REFINED_AEGISALT);
            event.accept(ModItems.RAW_AEGISALT);
            event.accept(ModItems.RAW_DURASTEEL);
            event.accept(ModItems.RAW_TITANIUM);
            event.accept(ModItems.TITANIUM_INGOT);
            event.accept(ModItems.DURASTEEL_INGOT);
            event.accept(ModItems.AEROMER);
            event.accept(ModItems.AVALI_BOTTLE);
            event.accept(ModItems.FIBER);
            event.accept(ModItems.NAKATI_BARK);
            event.accept(ModItems.NANITE_INJECTOR);
            event.accept(ModItems.PROTOSTEEL_INGOT);
            event.accept(ModItems.WOVEN_FABRIC);
            event.accept(ModItems.WOVEN_GRAPHENE);
        }

        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.LUME_BLOCK);
            event.accept(ModBlocks.LUME_ORE);
            event.accept(ModBlocks.LUME_DEEPSLATE_ORE);
            event.accept(ModBlocks.AERO_CRYSTAL_ORE);
            event.accept(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE);
            event.accept(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE);
            event.accept(ModBlocks.THERMAL_CRYSTAL_ORE);
            event.accept(ModBlocks.SYNC_CRYSTAL_ORE);
            event.accept(ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE);
            event.accept(ModBlocks.AEGISALT_ORE);
            event.accept(ModBlocks.AEGISALT_DEEPSLATE_ORE);
            event.accept(ModBlocks.TITANIUM_ORE);
            event.accept(ModBlocks.TITANIUM_DEEPSLATE_ORE);
            event.accept(ModBlocks.DURASTEEL_ORE);
            event.accept(ModBlocks.DURASTEEL_DEEPSLATE_ORE);
        }

        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
        }

        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(ModItems.AVALI_AXE);
            event.accept(ModItems.AVALI_PICKAXE);
            event.accept(ModItems.AVALI_HOE);
        }

        if(event.getTabKey() == CreativeModeTabs.COMBAT){
            event.accept(ModItems.AVALI_SWORD);
        }

        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.KIRIKIRI_PIE);
            event.accept(ModItems.AVALI_MUFFIN);
            event.accept(ModItems.AVALON_TACO);
            event.accept(ModItems.GROOU);
            event.accept(ModItems.GROOU_JUICE);
            event.accept(ModItems.KIRI_JAM);
            event.accept(ModItems.KIRI_CURRY);
            event.accept(ModItems.PIRU_FLOUR);
            event.accept(ModItems.PIRU_NOODLE);
            event.accept(ModItems.PIRUZA);
            event.accept(ModItems.AVALI_BBQ);
            event.accept(ModItems.KIRI_CIDER);
            event.accept(ModItems.TUCKER);
            event.accept(ModItems.KIRI_FRUIT);
            event.accept(ModItems.SPICY_JERKY);
            event.accept(ModItems.NAKATI_OVOID);
            event.accept(ModItems.PIRU_COLONY);
        }
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}