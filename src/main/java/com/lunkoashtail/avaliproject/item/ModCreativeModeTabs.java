package com.lunkoashtail.avaliproject.item;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AvaliProject.MOD_ID);

    public static final Supplier<CreativeModeTab> AVALI_ITEMS_TAB = CREATIVE_MODE_TAB.register("avali_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.AVALI_ICON.get()))
                    .title(Component.translatable("creativetab.avaliproject.avali_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.LUME_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.THERMAL_CRYSTAL_ORE.get());
                        output.accept(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.SYNC_CRYSTAL_ORE.get());
                        output.accept(ModBlocks.AERO_CRYSTAL_ORE.get());
                        output.accept(ModBlocks.AEGISALT_ORE.get());
                        output.accept(ModBlocks.AEGISALT_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.LUME_BLOCK.get());
                        output.accept(ModBlocks.LUME_ORE.get());
                        output.accept(ModBlocks.AVALI_PATTERN_BLOCK_1.get());
                        output.accept(ModBlocks.AVALI_PATTERN_BLOCK_2.get());
                        output.accept(ModBlocks.AVALI_PATTERN_BLOCK_3.get());
                        output.accept(ModBlocks.AVALI_PATTERN_BLOCK_4.get());
                        output.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_1.get());
                        output.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_2.get());
                        output.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_3.get());
                        output.accept(ModBlocks.AVALI_WALL_PATTERN_BLOCK_4.get());
                        output.accept(ModBlocks.AVALI_FABRIC_BLOCK.get());
                        output.accept(ModBlocks.ALT_AVALI_FABRIC_BLOCK.get());
                        /*output.accept(ModBlocks.AVALI_CARVING_1.get());
                        output.accept(ModBlocks.AVALI_CARVING_2.get());
                        output.accept(ModBlocks.AVALI_CARVING_3.get());
                        output.accept(ModBlocks.AVALI_CARVING_4.get());
                        output.accept(ModBlocks.AVALI_CARVING_5.get());
                        output.accept(ModBlocks.AVALI_NIGHTLY_FABRIC_1.get());
                        output.accept(ModBlocks.AVALI_NIGHTLY_FABRIC_2.get());
                        output.accept(ModBlocks.AVALI_NIGHTLY_FABRIC_3.get());
                        output.accept(ModBlocks.AVALI_NIGHTLY_FABRIC_4.get());
                        output.accept(ModBlocks.AVALI_NIGHTLY_FABRIC_5.get());
                        output.accept(ModBlocks.AVALI_NIGHTLY_FABRIC_6.get());*/
                        output.accept(ModBlocks.SOFT_TAPESTRY.get());
                        output.accept(ModItems.LUME.get());
                        output.accept(ModItems.LUME_BIT.get());
                        output.accept(ModItems.KIRIKIRI_PIE.get());
                        output.accept(ModItems.AVALI_MUFFIN.get());
                        output.accept(ModItems.AVALON_TACO.get());
                        output.accept(ModItems.GROOU.get());
                        output.accept(ModItems.GROOU_JUICE.get());
                        output.accept(ModItems.KIRI_JAM.get());
                        output.accept(ModItems.KIRI_CURRY.get());
                        output.accept(ModItems.PIRU_NOODLE.get());
                        output.accept(ModItems.PIRUZA.get());
                        output.accept(ModItems.AEROGEL.get());
                        output.accept(ModItems.PIRU_FLOUR.get());
                        output.accept(ModItems.SYNC_CRYSTAL.get());
                        output.accept(ModItems.AERO_CRYSTAL.get());
                        output.accept(ModItems.THERMAL_CRYSTAL.get());
                        output.accept(ModItems.REFINED_AEGISALT.get());
                        output.accept(ModItems.RAW_AEGISALT.get());
                        output.accept(ModItems.WOVEN_FABRIC.get());
                        output.accept(ModItems.WOVEN_GRAPHENE.get());
                        output.accept(ModItems.AEROMER.get());
                        output.accept(ModItems.AVALI_BOTTLE.get());
                        output.accept(ModItems.FIBER.get());
                        output.accept(ModItems.NAKATI_BARK.get());
                        output.accept(ModItems.AVALI_BBQ.get());
                        output.accept(ModItems.KIRI_CIDER.get());
                        output.accept(ModItems.TUCKER.get());
                        output.accept(ModItems.KIRI_FRUIT.get());
                        output.accept(ModItems.SPICY_JERKY.get());
                        output.accept(ModItems.NAKATI_OVOID.get());
                        output.accept(ModItems.PIRU_COLONY.get());
                        output.accept(ModItems.AVALI_AXE.get());
                        output.accept(ModItems.AVALI_SWORD.get());
                        output.accept(ModItems.AVALI_SPEAR.get());
                        output.accept(ModItems.AVALI_HOE.get());
                        output.accept(ModItems.AVALI_PICKAXE.get());
                        output.accept(ModItems.PIRU_FROND.get());
                        output.accept(ModItems.AVALI_DANCE_MUSIC_DISC.get());
                        output.accept(ModItems.MLSERIES_MAIN.get());
                        output.accept(ModItems.MLSERIES_HILT.get());
                        output.accept(ModItems.MLSERIES_MUZZLE.get());
                        output.accept(ModItems.FIRELANCE_HILT.get());
                        output.accept(ModItems.FIRELANCE_MAIN.get());
                        output.accept(ModItems.FIRELANCE_MUZZLE.get());
                        output.accept(ModItems.FIRELANCE_SCOPE.get());
                        output.accept(ModItems.QRC_MUZZLE.get());
                        output.accept(ModItems.QRC_MAIN.get());
                        output.accept(ModItems.QRC_HILT.get());
                        output.accept(ModItems.NOVA_MUZZLE.get());
                        output.accept(ModItems.NOVA_MAIN.get());
                        output.accept(ModItems.NOVA_HILT.get());
                        output.accept(ModItems.NOVA_SCOPE.get());
                        output.accept(ModItems.AVALI_DRONE_CORE.get());
                        output.accept(ModItems.AVALI_DRONE_ROTORS.get());
                        output.accept(ModItems.AVALI_DRONE.get());
                        output.accept(ModItems.AVALI_DATA_CHIT.get());
                        output.accept(ModItems.STORM_CORE.get());
                        output.accept(ModItems.STORM_HILT.get());
                        output.accept(ModItems.STORM_MUZZLE.get());
                        output.accept(ModItems.BLIZZARD_HILT.get());
                        output.accept(ModItems.BLIZZARD_CORE.get());
                        output.accept(ModItems.BLIZZARD_MUZZLE.get());

                        output.accept(ModItems.QRC.get());
                        output.accept(ModItems.NOVA.get());
                        output.accept(ModItems.MLSERIES.get());
                        output.accept(ModItems.FIRELANCE.get());
                        output.accept(ModItems.STORM.get());
                        output.accept(ModItems.BLIZZARD.get());

                        output.accept(ModBlocks.GROOU_NODULE.get());
                        output.accept(ModBlocks.KIRI_NODULE.get());
                        output.accept(ModBlocks.NAKATI_NODULE.get());
                        output.accept(ModBlocks.PIRU_NODULE.get());

                        output.accept(ModItems.SKSCEEGEHKJA_SPAWN_EGG.get());
                        output.accept(ModItems.SKACIKKJRRBWCAK_SPAWN_EGG.get());
                        output.accept(ModItems.EEPUOR_SPAWN_EGG.get());
                        output.accept(ModItems.AVALI_SPAWN_EGG.get());
                        output.accept(ModItems.CAKLERAH_SPAWN_EGG.get());
                        output.accept(ModItems.CHRGAKBZ_SPAWN_EGG.get());

//                        output.accept(ModBlocks.NANOLOOM.get().get());

                    }).build());

    public static final Supplier<CreativeModeTab> PROTOGEN_ITEM_TAB = CREATIVE_MODE_TAB.register("protogen_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PROTOGEN_ICON.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali_items_tab"))
                    .title(Component.translatable("creativetab.avaliproject.protogen_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.TITANIUM_ORE.get());
                        output.accept(ModBlocks.TITANIUM_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.DURASTEEL_ORE.get());
                        output.accept(ModBlocks.DURASTEEL_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.ARCAITES_CRYSTAL_ORE.get());
                        output.accept(ModBlocks.ALT_DETAILED_PROTOGEN_BLOCK.get());
                        output.accept(ModBlocks.DETAILED_PROTOGEN_BLOCK.get());
                        output.accept(ModBlocks.ALT_PROTOGEN_SUPPORT_BLOCK.get());
                        output.accept(ModBlocks.PROTOGEN_SUPPORT_BLOCK.get());
                        output.accept(ModBlocks.NOVULITE_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.NOVULITE_BLOCK.get());
                        output.accept(ModBlocks.NOVULITE_ORE.get());
                        output.accept(ModItems.ARCAITES_CRYSTAL.get());
                        output.accept(ModItems.RAW_DURASTEEL.get());
                        output.accept(ModItems.RAW_TITANIUM.get());
                        output.accept(ModItems.TITANIUM_INGOT.get());
                        output.accept(ModItems.WOVEN_FABRIC.get());
                        output.accept(ModItems.WOVEN_GRAPHENE.get());
                        output.accept(ModItems.NANITE_INJECTOR.get());
                        output.accept(ModItems.PROTOSTEEL_INGOT.get());
                        output.accept(ModItems.DURASTEEL_INGOT.get());
                        output.accept(ModItems.FIBER.get());
                        output.accept(ModItems.PROTOGEN_RAM.get());
                        output.accept(ModItems.PROTOGEN_AXE.get());
                        output.accept(ModItems.PROTOGEN_SWORD.get());
                        output.accept(ModItems.NOVULITE.get());
                        output.accept(ModItems.PROTOGEN_SPAWN_EGG.get());
                        output.accept(ModItems.PRIMAGEN_SPAWN_EGG.get());
                        output.accept(ModItems.MAMAGEN_SPAWN_EGG.get());
                        output.accept(ModItems.CYBERNETIC_HEART_MUSIC_DISC.get());
                    }).build());

    public static final Supplier<CreativeModeTab> SERGAL_ITEM_TAB = CREATIVE_MODE_TAB.register("sergal_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SERGAL_ICON.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "protogen_items_tab"))
                    .title(Component.translatable("creativetab.avaliproject.sergal_item"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.VILOUS_CERAMIC_ORE.get());
                        output.accept(ModBlocks.AGATE_ORE.get());
                        output.accept(ModBlocks.AGATE_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.AGATE_BLOCK.get());
                        output.accept(ModItems.AGATE.get());
                        output.accept(ModItems.VILOUS_CLAY.get());
                        output.accept(ModItems.SERGAL_CHEESE.get());
                        output.accept(ModItems.VILOUS_CERAMIC_INGOT.get());
                        output.accept(ModItems.SERGAL_LANCE.get());
                        output.accept(ModItems.SERGAL_SWORD.get());
                        output.accept(ModItems.SERGAL_GREATSWORD.get());
                        output.accept(ModItems.SERGAL_MACE.get());
//                        output.accept(ModItems.SERGAL_SLINGSHOT.get());
                        output.accept(ModItems.MERP_MUSIC_DISC.get());
                        output.accept(ModItems.SERGAL_SPAWN_EGG.get());
                        output.accept(ModItems.TALXLEECH_SPAWN_EGG.get());
                        output.accept(ModItems.TALXWEASEL_SPAWN_EGG.get());
                        output.accept(ModItems.STALKER_SPAWN_EGG.get());
                        output.accept(ModItems.GOHUNTAKI_SPAWN_EGG.get());
                        output.accept(ModItems.TALXDOG_SPAWN_EGG.get());
                        output.accept(ModItems.TALXWOLF_SPAWN_EGG.get());
                        output.accept(ModItems.MIZOLE_SPAWN_EGG.get());
                        output.accept(ModItems.SPORE_SPAWN_EGG.get());
                        output.accept(ModItems.MALE_NEVREAN_SPAWN_EGG.get());
                        output.accept(ModItems.FEMALE_NEVREAN_SPAWN_EGG.get());
                        output.accept(ModBlocks.VILOUS_CERAMIC_INGOT_BLOCK.get());
                    }).build());

    public static void register (IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}