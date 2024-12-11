package com.lunkoashtail.avaliproject;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.entity.ModBlockEntities;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.client.*;
import com.lunkoashtail.avaliproject.item.ModCreativeModeTabs;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.screen.ModMenuTypes;
import com.lunkoashtail.avaliproject.screen.custom.NanoloomScreen;
import com.lunkoashtail.avaliproject.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AvaliProject.MOD_ID)
public class AvaliProject {
    public static final String MOD_ID = "avaliproject";
    private static final Logger LOGGER = LogUtils.getLogger();

    public AvaliProject(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);

        if (!ModList.get().isLoaded("geckolib")) {
            throw new IllegalStateException("Geckolib is not loaded! This mod requires Geckolib to function.");
        }

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.GROOU_NODULE.getId(), ModBlocks.POTTED_GROOU_NODULE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.NAKATI_NODULE.getId(), ModBlocks.POTTED_NAKATI_NODULE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.PIRU_NODULE.getId(), ModBlocks.POTTED_PIRU_NODULE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.KIRI_NODULE.getId(), ModBlocks.POTTED_KIRI_NODULE);
            System.out.println("Common setup for Avali Project.");
        });
        LOGGER.info("HELLO FROM COMMON SETUP");
        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            if (!ModList.get().isLoaded("geckolib")) {
                throw new IllegalStateException("Geckolib is not loaded! This mod requires Geckolib to function.");
            }
            EntityRenderers.register(ModEntities.SKSCEEGEHKJA.get(), SksceegehkjaRenderer::new);
            EntityRenderers.register(ModEntities.SKACIKKJRRKBWCAK.get(), SkacikkjrrkbwcakRenderer::new);
            EntityRenderers.register(ModEntities.PROTOGEN.get(), ProtogenRenderer::new);
            EntityRenderers.register(ModEntities.PRIMAGEN.get(), PrimagenRenderer::new);
            EntityRenderers.register(ModEntities.AVALI.get(), AvaliRenderer::new);
        }
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.NANOLOOM_MENU.get(), NanoloomScreen::new);
        }
    }



    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
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
            event.accept(ModItems.VILOUS_CLAY);
            event.accept(ModItems.VILOUS_CERAMIC_INGOT);
            event.accept(ModItems.ARCAITES_CRYSTAL);
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.LUME_BLOCK);
            event.accept(ModBlocks.AGATE_BLOCK);
            event.accept(ModBlocks.NOVULITE_BLOCK);
        }

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.LUME_ORE);
            event.accept(ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE);
            event.accept(ModBlocks.ARCAITES_CRYSTAL_ORE);
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
            event.accept(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE);
            event.accept(ModBlocks.VILOUS_CERAMIC_ORE);
            event.accept(ModBlocks.NOVULITE_DEEPSLATE_ORE);
            event.accept(ModBlocks.NOVULITE_ORE);
            event.accept(ModBlocks.AGATE_ORE);
            event.accept(ModBlocks.AGATE_DEEPSLATE_ORE);
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
        }

        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            event.accept(ModBlocks.AVALI_PATTERN_BLOCK_1);
            event.accept(ModBlocks.AVALI_PATTERN_BLOCK_2);
            event.accept(ModBlocks.AVALI_PATTERN_BLOCK_3);
            event.accept(ModBlocks.AVALI_PATTERN_BLOCK_4);
            event.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_1);
            event.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_2);
            event.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_3);
            event.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_4);
            event.accept(ModBlocks.AVALI_FABRIC_BLOCK);
            event.accept(ModBlocks.ALT_AVALI_FABRIC_BLOCK);
            event.accept(ModBlocks.SOFT_TAPESTRY);
            event.accept(ModBlocks.DETAILED_PROTOGEN_BLOCK);
            event.accept(ModBlocks.PROTOGEN_SUPPORT_BLOCK);
            event.accept(ModBlocks.ALT_DETAILED_PROTOGEN_BLOCK);
            event.accept(ModBlocks.ALT_PROTOGEN_SUPPORT_BLOCK);
        }

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.SKSCEEGEHKJA_SPAWN_EGG);
            event.accept(ModItems.SKACIKKJRRBWCAK_SPAWN_EGG);
            event.accept(ModItems.PROTOGEN_SPAWN_EGG);
            event.accept(ModItems.PRIMAGEN_SPAWN_EGG);
            event.accept(ModItems.AVALI_SPAWN_EGG);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.AVALI_AXE);
            event.accept(ModItems.AVALI_PICKAXE);
            event.accept(ModItems.AVALI_HOE);
            event.accept(ModItems.PROTOGEN_AXE);
        }

        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.AVALI_SWORD);
            event.accept(ModItems.PROTOGEN_SWORD);
            event.accept(ModItems.SERGAL_SWORD);
            event.accept(ModItems.SERGAL_GREATSWORD);
            event.accept(ModItems.SERGAL_LANCE);
//            event.accept(ModItems.SERGAL_SLINGSHOT);
            event.accept(ModItems.SERGAL_MACE);
            event.accept(ModItems.AVALI_SPEAR);
        }

        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
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
            event.accept(ModItems.PROTOGEN_RAM);
            event.accept(ModItems.SERGAL_CHEESE);
        }
    }

    private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tick, Runnable action) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            workQueue.add(new Tuple<>(action, tick));
    }

    @SubscribeEvent
    public void tick(ServerTickEvent.Post event) {
        List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
        workQueue.forEach(work -> {
            work.setB(work.getB() - 1);
            if (work.getB() == 0)
                actions.add(work);
        });
        actions.forEach(e -> e.getA().run());
        workQueue.removeAll(actions);
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}