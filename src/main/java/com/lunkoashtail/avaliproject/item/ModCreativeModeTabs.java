package com.lunkoashtail.avaliproject.item;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AvaliProject.MOD_ID);

    public static final Supplier<CreativeModeTab> AVALI_ITEMS_TAB = CREATIVE_MODE_TAB.register("avali_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.AVALI_ICON.get()))
                    .title(Component.translatable("creativetab.avaliproject.avali_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.LUME_DEEPSLATE_ORE);
                        output.accept(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE);
                        output.accept(ModBlocks.THERMAL_CRYSTAL_ORE);
                        output.accept(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE);
                        output.accept(ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE);
                        output.accept(ModBlocks.SYNC_CRYSTAL_ORE);
                        output.accept(ModBlocks.AERO_CRYSTAL_ORE);
                        output.accept(ModBlocks.AEGISALT_ORE);
                        output.accept(ModBlocks.AEGISALT_DEEPSLATE_ORE);
                        output.accept(ModBlocks.LUME_BLOCK);
                        output.accept(ModBlocks.LUME_ORE);
                        output.accept(ModBlocks.AVALI_PATTERN_BLOCK_1);
                        output.accept(ModBlocks.AVALI_PATTERN_BLOCK_2);
                        output.accept(ModBlocks.AVALI_PATTERN_BLOCK_3);
                        output.accept(ModBlocks.AVALI_PATTERN_BLOCK_4);
                        output.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_1);
                        output.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_2);
                        output.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_3);
                        output.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_4);
                        output.accept(ModBlocks.AVALI_FABRIC_BLOCK);
                        output.accept(ModBlocks.ALT_AVALI_FABRIC_BLOCK);
                        output.accept(ModBlocks.SOFT_TAPESTRY);
                        output.accept(ModItems.LUME);
                        output.accept(ModItems.LUME_BIT);
                        output.accept(ModItems.KIRIKIRI_PIE);
                        output.accept(ModItems.AVALI_MUFFIN);
                        output.accept(ModItems.AVALON_TACO);
                        output.accept(ModItems.GROOU);
                        output.accept(ModItems.GROOU_JUICE);
                        output.accept(ModItems.KIRI_JAM);
                        output.accept(ModItems.KIRI_CURRY);
                        output.accept(ModItems.PIRU_NOODLE);
                        output.accept(ModItems.PIRUZA);
                        output.accept(ModItems.AEROGEL);
                        output.accept(ModItems.PIRU_FLOUR);
                        output.accept(ModItems.SYNC_CRYSTAL);
                        output.accept(ModItems.AERO_CRYSTAL);
                        output.accept(ModItems.THERMAL_CRYSTAL);
                        output.accept(ModItems.REFINED_AEGISALT);
                        output.accept(ModItems.RAW_AEGISALT);
                        output.accept(ModItems.WOVEN_FABRIC);
                        output.accept(ModItems.WOVEN_GRAPHENE);
                        output.accept(ModItems.AEROMER);
                        output.accept(ModItems.AVALI_BOTTLE);
                        output.accept(ModItems.FIBER);
                        output.accept(ModItems.NAKATI_BARK);
                        output.accept(ModItems.AVALI_BBQ);
                        output.accept(ModItems.KIRI_CIDER);
                        output.accept(ModItems.TUCKER);
                        output.accept(ModItems.KIRI_FRUIT);
                        output.accept(ModItems.SPICY_JERKY);
                        output.accept(ModItems.NAKATI_OVOID);
                        output.accept(ModItems.PIRU_COLONY);
                        output.accept(ModItems.AVALI_AXE);
                        output.accept(ModItems.AVALI_SWORD);
                        output.accept(ModItems.AVALI_SPEAR);
                        output.accept(ModItems.AVALI_HOE);
                        output.accept(ModItems.AVALI_PICKAXE);
                        output.accept(ModItems.PIRU_FROND);
                        output.accept(ModItems.AVALI_DANCE_MUSIC_DISC);

                        output.accept(ModBlocks.GROOU_NODULE);
                        output.accept(ModBlocks.KIRI_NODULE);
                        output.accept(ModBlocks.NAKATI_NODULE);
                        output.accept(ModBlocks.PIRU_NODULE);

                        output.accept(ModItems.SKSCEEGEHKJA_SPAWN_EGG);
                        output.accept(ModItems.SKACIKKJRRBWCAK_SPAWN_EGG);
                        output.accept(ModItems.EEPUOR_SPAWN_EGG);
                        output.accept(ModItems.AVALI_SPAWN_EGG);
                        output.accept(ModItems.CAKLERAH_SPAWN_EGG);

//                        output.accept(ModBlocks.NANOLOOM.get());

                    }).build());

    public static final Supplier<CreativeModeTab> PROTOGEN_ITEM_TAB = CREATIVE_MODE_TAB.register("protogen_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PROTOGEN_ICON.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali_items_tab"))
                    .title(Component.translatable("creativetab.avaliproject.protogen_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.TITANIUM_ORE);
                        output.accept(ModBlocks.TITANIUM_DEEPSLATE_ORE);
                        output.accept(ModBlocks.DURASTEEL_ORE);
                        output.accept(ModBlocks.DURASTEEL_DEEPSLATE_ORE);
                        output.accept(ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE);
                        output.accept(ModBlocks.ARCAITES_CRYSTAL_ORE);
                        output.accept(ModBlocks.ALT_DETAILED_PROTOGEN_BLOCK);
                        output.accept(ModBlocks.DETAILED_PROTOGEN_BLOCK);
                        output.accept(ModBlocks.ALT_PROTOGEN_SUPPORT_BLOCK);
                        output.accept(ModBlocks.PROTOGEN_SUPPORT_BLOCK);
                        output.accept(ModBlocks.NOVULITE_DEEPSLATE_ORE);
                        output.accept(ModBlocks.NOVULITE_BLOCK);
                        output.accept(ModBlocks.NOVULITE_ORE);
                        output.accept(ModItems.ARCAITES_CRYSTAL);
                        output.accept(ModItems.RAW_DURASTEEL);
                        output.accept(ModItems.RAW_TITANIUM);
                        output.accept(ModItems.TITANIUM_INGOT);
                        output.accept(ModItems.WOVEN_FABRIC);
                        output.accept(ModItems.WOVEN_GRAPHENE);
                        output.accept(ModItems.NANITE_INJECTOR);
                        output.accept(ModItems.PROTOSTEEL_INGOT);
                        output.accept(ModItems.DURASTEEL_INGOT);
                        output.accept(ModItems.FIBER);
                        output.accept(ModItems.PROTOGEN_RAM);
                        output.accept(ModItems.PROTOGEN_AXE);
                        output.accept(ModItems.PROTOGEN_SWORD);
                        output.accept(ModItems.NOVULITE);
                        output.accept(ModItems.PROTOGEN_SPAWN_EGG);
                        output.accept(ModItems.PRIMAGEN_SPAWN_EGG);
                        output.accept(ModItems.MAMAGEN_SPAWN_EGG);
                        output.accept(ModItems.CYBERNETIC_HEART_MUSIC_DISC);
                    }).build());

    public static final Supplier<CreativeModeTab> SERGAL_ITEM_TAB = CREATIVE_MODE_TAB.register("sergal_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SERGAL_ICON.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "protogen_items_tab"))
                    .title(Component.translatable("creativetab.avaliproject.sergal_item"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE);
                        output.accept(ModBlocks.VILOUS_CERAMIC_ORE);
                        output.accept(ModBlocks.AGATE_ORE);
                        output.accept(ModBlocks.AGATE_DEEPSLATE_ORE);
                        output.accept(ModBlocks.AGATE_BLOCK);
                        output.accept(ModItems.AGATE);
                        output.accept(ModItems.VILOUS_CLAY);
                        output.accept(ModItems.SERGAL_CHEESE);
                        output.accept(ModItems.VILOUS_CERAMIC_INGOT);
                        output.accept(ModItems.SERGAL_LANCE);
                        output.accept(ModItems.SERGAL_SWORD);
                        output.accept(ModItems.SERGAL_GREATSWORD);
                        output.accept(ModItems.SERGAL_MACE);
//                        output.accept(ModItems.SERGAL_SLINGSHOT);
                        output.accept(ModItems.MERP_MUSIC_DISC);
                        output.accept(ModItems.SERGAL_SPAWN_EGG);
                        output.accept(ModItems.TALXLEECH_SPAWN_EGG);
                        output.accept(ModItems.TALXWEASEL_SPAWN_EGG);
                        output.accept(ModItems.STALKER_SPAWN_EGG);
                        output.accept(ModItems.GOHUNTAKI_SPAWN_EGG);
                        output.accept(ModItems.TALXDOG_SPAWN_EGG);
                        output.accept(ModItems.TALXWOLF_SPAWN_EGG);
                        output.accept(ModItems.MIZOLE_SPAWN_EGG);
                        output.accept(ModItems.SPORE_SPAWN_EGG);
                        output.accept(ModItems.MALE_NEVREAN_SPAWN_EGG);
                        output.accept(ModItems.FEMALE_NEVREAN_SPAWN_EGG);
                    }).build());

    public static void register (IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}