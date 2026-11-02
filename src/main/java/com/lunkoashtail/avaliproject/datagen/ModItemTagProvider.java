package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.WoodSet;
import com.lunkoashtail.avaliproject.creativetab.GalaxyCategory;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AvaliProject.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ItemTags.SWORDS)
                .add(ModItems.AVALI_SWORD.get())
                .add(ModItems.PROTOGEN_SWORD.get())
                .add(ModItems.AVALI_SPEAR.get())
                .add(ModItems.SERGAL_SWORD.get())
                .add(ModItems.SERGAL_GREATSWORD.get())
                .add(ModItems.SERGAL_LANCE.get())
                .add(ModItems.TITANIUM_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.AVALI_PICKAXE.get())
                .add(ModItems.TITANIUM_PICKAXE.get());
        tag(ItemTags.AXES)
                .add(ModItems.PROTOGEN_AXE.get())
                .add(ModItems.AVALI_AXE.get())
                .add(ModItems.TITANIUM_AXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.TITANIUM_SHOVEL.get());
        tag(ItemTags.HOES)
                .add(ModItems.AVALI_HOE.get())
                .add(ModItems.TITANIUM_HOE.get());
        tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.TITANIUM_HELMET.get());
        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.TITANIUM_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.TITANIUM_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.TITANIUM_BOOTS.get());
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots")))
                .add(ModItems.TITANIUM_INGOT.get())
                .add(ModItems.AEROGEL.get())
                .add(ModItems.VILOUS_CERAMIC_INGOT.get())
                .add(ModItems.DURASTEEL_INGOT.get())
                .add(ModItems.PROTOSTEEL_INGOT.get())
                .add(ModItems.REFINED_AEGISALT.get());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/titanium")))
                .add(ModItems.TITANIUM_INGOT.get());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/steel")))
                .add(ModItems.DURASTEEL_INGOT.get())
                .add(ModItems.PROTOSTEEL_INGOT.get());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ore/titanium")))
                .add(ModBlocks.TITANIUM_ORE.asItem())
                .add(ModBlocks.TITANIUM_DEEPSLATE_ORE.asItem());


        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.TITANIUM_HELMET.get())
                .add(ModItems.TITANIUM_CHESTPLATE.get())
                .add(ModItems.TITANIUM_LEGGINGS.get())
                .add(ModItems.TITANIUM_BOOTS.get());


        tag(ItemTags.MACE_ENCHANTABLE)
                .add(ModItems.SERGAL_MACE.get());



        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("pamhc", "cheesemild")))
                .add(ModItems.SERGAL_CHEESE.get());

        for (WoodSet set : ModBlocks.WOOD_SETS) {
            set.logs().forEach(block -> tag(set.logsItemTag).add(block.asItem()));
            tag(ItemTags.LOGS_THAT_BURN).addTag(set.logsItemTag);
            tag(ItemTags.PLANKS).add(set.planks.asItem());
            tag(ItemTags.WOODEN_STAIRS).add(set.stairs.asItem());
            tag(ItemTags.WOODEN_SLABS).add(set.slab.asItem());
            tag(ItemTags.WOODEN_FENCES).add(set.fence.asItem());
            tag(ItemTags.FENCE_GATES).add(set.fenceGate.asItem());
            tag(ItemTags.WOODEN_BUTTONS).add(set.button.asItem());
            tag(ItemTags.WOODEN_PRESSURE_PLATES).add(set.pressurePlate.asItem());
            tag(ItemTags.SIGNS).add(set.signItem.get());
            tag(ItemTags.HANGING_SIGNS).add(set.hangingSignItem.get());
            set.all().stream()
                    .filter(block -> block != set.sign && block != set.wallSign && block != set.hangingSign && block != set.wallHangingSign)
                    .forEach(block -> tag(GalaxyCategory.MANOKIT.tag()).add(block.asItem()));
            tag(GalaxyCategory.MANOKIT.tag()).add(set.signItem.get()).add(set.hangingSignItem.get());
        }
        tag(ItemTags.LEAVES)
                .add(ModBlocks.PALM_LEAVES.asItem())
                .add(ModBlocks.PASSION_FRUIT_LEAVES.asItem())
                .add(ModBlocks.PAPAYA_LEAVES.asItem());

        tag(ModTags.Items.TROPICAL_FRUITS)
                .add(ModItems.COCONUT.get())
                .add(ModItems.PASSION_FRUIT.get())
                .add(ModItems.PAPAYA.get())
                .add(ModItems.OPEN_COCONUT.get())
                .add(ModItems.DRAGON_FRUIT.get())
                .add(ModItems.OPEN_DRAGON_FRUIT.get());
        tag(ModTags.Items.MANOKIT_GEMS)
                .add(ModItems.MOONSTONE.get())
                .add(ModItems.SUNSTONE.get())
                .add(ModItems.LARIMAR.get())
                .add(ModItems.SEA_OPAL.get())
                .add(ModItems.JADEITE.get())
                .add(ModItems.CARNELIAN.get())
                .add(ModItems.AMMOLITE.get())
                .add(ModItems.ABALONE_SHARD.get())
                .add(ModItems.PEARL.get())
                .add(ModItems.BLACK_PEARL.get());
        tag(ModTags.Items.FELKIN_FOOD)
                .add(Items.WHEAT)
                .add(Items.HAY_BLOCK)
                .add(Items.APPLE)
                .add(Items.CARROT);

        tag(ModTags.Items.ZORGOIA_FOOD)
                .add(Items.BEEF)
                .add(Items.PORKCHOP)
                .add(Items.MUTTON)
                .add(Items.CHICKEN)
                .add(Items.RABBIT);

        tag(ModTags.Items.DUTCH_ANGEL_DRAGON_FOOD)
                .add(Items.APPLE)
                .add(Items.SWEET_BERRIES)
                .add(Items.GLOW_BERRIES)
                .add(Items.MELON_SLICE);

        tag(CURIOS_NECKLACE)
                .add(ModItems.PENDANT.get())
                .add(ModItems.MANOKIT_TRINKET.get());
        tag(CURIOS_CHARM).add(ModItems.CRYSTALITHIUM_TRINKET.get());

        addGalaxyCategoryTags();
    }

    private static TagKey<Item> curiosSlot(String slot) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", slot));
    }

    public static final TagKey<Item> CURIOS_NECKLACE = curiosSlot("necklace");
    public static final TagKey<Item> CURIOS_CHARM = curiosSlot("charm");

    private void addGalaxyCategoryTags() {
        tag(GalaxyCategory.AVALI.tag())
                .add(ModItems.LUME.get())
                .add(ModItems.LUME_BIT.get())
                .add(ModItems.KIRIKIRI_PIE.get())
                .add(ModItems.AVALI_MUFFIN.get())
                .add(ModItems.AVALON_TACO.get())
                .add(ModItems.GROOU.get())
                .add(ModItems.GROOU_JUICE.get())
                .add(ModItems.KIRI_JAM.get())
                .add(ModItems.KIRI_CURRY.get())
                .add(ModItems.PIRU_NOODLE.get())
                .add(ModItems.PIRUZA.get())
                .add(ModItems.AEROGEL.get())
                .add(ModItems.AEROGEL_BATTERY.get())
                .add(ModItems.PIRU_FLOUR.get())
                .add(ModItems.SYNC_CRYSTAL.get())
                .add(ModItems.AERO_CRYSTAL.get())
                .add(ModItems.THERMAL_CRYSTAL.get())
                .add(ModItems.REFINED_AEGISALT.get())
                .add(ModItems.RAW_AEGISALT.get())
                .add(ModItems.WOVEN_FABRIC.get())
                .add(ModItems.WOVEN_GRAPHENE.get())
                .add(ModItems.AEROMER.get())
                .add(ModItems.AVALI_BOTTLE.get())
                .add(ModItems.FIBER.get())
                .add(ModItems.NAKATI_BARK.get())
                .add(ModItems.AVALI_BBQ.get())
                .add(ModItems.KIRI_CIDER.get())
                .add(ModItems.TUCKER.get())
                .add(ModItems.KIRI_FRUIT.get())
                .add(ModItems.SPICY_JERKY.get())
                .add(ModItems.NAKATI_OVOID.get())
                .add(ModItems.PIRU_COLONY.get())
                .add(ModItems.AVALI_AXE.get())
                .add(ModItems.AVALI_SWORD.get())
                .add(ModItems.AVALI_SPEAR.get())
                .add(ModItems.AVALI_HOE.get())
                .add(ModItems.AVALI_PICKAXE.get())
                .add(ModItems.PIRU_FROND.get())
                .add(ModItems.AVALI_DANCE_MUSIC_DISC.get())
                .add(ModItems.MLSERIES_MAIN.get())
                .add(ModItems.MLSERIES_HILT.get())
                .add(ModItems.MLSERIES_MUZZLE.get())
                .add(ModItems.FIRELANCE_HILT.get())
                .add(ModItems.FIRELANCE_MAIN.get())
                .add(ModItems.FIRELANCE_MUZZLE.get())
                .add(ModItems.FIRELANCE_SCOPE.get())
                .add(ModItems.QRC_MUZZLE.get())
                .add(ModItems.QRC_MAIN.get())
                .add(ModItems.QRC_HILT.get())
                .add(ModItems.NOVA_MUZZLE.get())
                .add(ModItems.NOVA_MAIN.get())
                .add(ModItems.NOVA_HILT.get())
                .add(ModItems.NOVA_SCOPE.get())
                .add(ModItems.AVALI_DRONE_CORE.get())
                .add(ModItems.AVALI_DRONE_ROTORS.get())
                .add(ModItems.AVALI_DRONE.get())
                .add(ModItems.AVALI_DATA_CHIT.get())
                .add(ModItems.STORM_CORE.get())
                .add(ModItems.STORM_HILT.get())
                .add(ModItems.STORM_MUZZLE.get())
                .add(ModItems.BLIZZARD_HILT.get())
                .add(ModItems.BLIZZARD_CORE.get())
                .add(ModItems.BLIZZARD_MUZZLE.get())
                .add(ModItems.QRC.get())
                .add(ModItems.NOVA.get())
                .add(ModItems.MLSERIES.get())
                .add(ModItems.FIRELANCE.get())
                .add(ModItems.STORM.get())
                .add(ModItems.BLIZZARD.get())
                .add(ModItems.RAILGUN.get())
                .add(ModItems.SKSCEEGEHKJA_SPAWN_EGG.get())
                .add(ModItems.SKACIKKJRRBWCAK_SPAWN_EGG.get())
                .add(ModItems.EEPUOR_SPAWN_EGG.get())
                .add(ModItems.AVALI_SPAWN_EGG.get())
                .add(ModItems.AVALI_DRONE_SPAWN_EGG.get())
                .add(ModItems.CAKLERAH_SPAWN_EGG.get())
                .add(ModItems.CHRGAKBZ_SPAWN_EGG.get())
                .add(ModBlocks.LUME_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.THERMAL_CRYSTAL_ORE.asItem())
                .add(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.AERO_CRYSTAL_ORE.asItem())
                .add(ModBlocks.AEGISALT_ORE.asItem())
                .add(ModBlocks.AEGISALT_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.LUME_BLOCK.asItem())
                .add(ModBlocks.LUME_ORE.asItem())
                .add(ModBlocks.AVALI_PATTERN_BLOCK_1.asItem())
                .add(ModBlocks.AVALI_PATTERN_BLOCK_2.asItem())
                .add(ModBlocks.AVALI_PATTERN_BLOCK_3.asItem())
                .add(ModBlocks.AVALI_PATTERN_BLOCK_4.asItem())
                .add(ModBlocks.AVALI_WALL_PATTERN_BLOCK_1.asItem())
                .add(ModBlocks.AVALI_WALL_PATTERN_BLOCK_2.asItem())
                .add(ModBlocks.AVALI_WALL_PATTERN_BLOCK_3.asItem())
                .add(ModBlocks.AVALI_WALL_PATTERN_BLOCK_4.asItem())
                .add(ModBlocks.AVALI_FABRIC_BLOCK.asItem())
                .add(ModBlocks.ALT_AVALI_FABRIC_BLOCK.asItem())
                .add(ModBlocks.AVALI_CARVING_1.asItem())
                .add(ModBlocks.AVALI_CARVING_2.asItem())
                .add(ModBlocks.AVALI_CARVING_3.asItem())
                .add(ModBlocks.AVALI_CARVING_4.asItem())
                .add(ModBlocks.AVALI_CARVING_5.asItem())
                .add(ModBlocks.AVALI_NIGHTLY_FABRIC_1.asItem())
                .add(ModBlocks.AVALI_NIGHTLY_FABRIC_2.asItem())
                .add(ModBlocks.AVALI_NIGHTLY_FABRIC_3.asItem())
                .add(ModBlocks.AVALI_NIGHTLY_FABRIC_4.asItem())
                .add(ModBlocks.AVALI_NIGHTLY_FABRIC_5.asItem())
                .add(ModBlocks.AVALI_NIGHTLY_FABRIC_6.asItem())
                .add(ModBlocks.SOFT_TAPESTRY.asItem())
                .add(ModBlocks.GROOU_NODULE.asItem())
                .add(ModBlocks.KIRI_NODULE.asItem())
                .add(ModBlocks.NAKATI_NODULE.asItem())
                .add(ModBlocks.PIRU_NODULE.asItem())
                .add(ModItems.AVALI_SERUM.get())
                .add(ModItems.AVALI_AUGMENT.get());

        tag(GalaxyCategory.PROTOGEN.tag())
                .add(ModItems.ARCAITES_CRYSTAL.get())
                .add(ModItems.RAW_DURASTEEL.get())
                .add(ModItems.RAW_TITANIUM.get())
                .add(ModItems.TITANIUM_INGOT.get())
                .add(ModItems.TITANIUM_SWORD.get())
                .add(ModItems.TITANIUM_PICKAXE.get())
                .add(ModItems.TITANIUM_AXE.get())
                .add(ModItems.TITANIUM_SHOVEL.get())
                .add(ModItems.TITANIUM_HOE.get())
                .add(ModItems.TITANIUM_HELMET.get())
                .add(ModItems.TITANIUM_CHESTPLATE.get())
                .add(ModItems.TITANIUM_LEGGINGS.get())
                .add(ModItems.TITANIUM_BOOTS.get())
                .add(ModItems.WOVEN_FABRIC.get())
                .add(ModItems.WOVEN_GRAPHENE.get())
                .add(ModItems.NANITE_INJECTOR.get())
                .add(ModItems.PROTOSTEEL_INGOT.get())
                .add(ModItems.DURASTEEL_INGOT.get())
                .add(ModItems.FIBER.get())
                .add(ModItems.PROTOGEN_RAM.get())
                .add(ModItems.PROTOGEN_AXE.get())
                .add(ModItems.PROTOGEN_SWORD.get())
                .add(ModItems.NOVULITE.get())
                .add(ModItems.PROTOGEN_SPAWN_EGG.get())
                .add(ModItems.PRIMAGEN_SPAWN_EGG.get())
                .add(ModItems.MAMAGEN_SPAWN_EGG.get())
                .add(ModItems.CYBERNETIC_HEART_MUSIC_DISC.get())
                .add(ModBlocks.TITANIUM_ORE.asItem())
                .add(ModBlocks.TITANIUM_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.DURASTEEL_ORE.asItem())
                .add(ModBlocks.DURASTEEL_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.ARCAITES_CRYSTAL_ORE.asItem())
                .add(ModBlocks.ALT_DETAILED_PROTOGEN_BLOCK.asItem())
                .add(ModBlocks.DETAILED_PROTOGEN_BLOCK.asItem())
                .add(ModBlocks.ALT_PROTOGEN_SUPPORT_BLOCK.asItem())
                .add(ModBlocks.PROTOGEN_SUPPORT_BLOCK.asItem())
                .add(ModItems.SYNCH_POD.get())
                .add(ModItems.GROWTH_VAT.get())
                .add(ModItems.PROTOGEN_RESEARCH_STATION.get())
                .add(ModItems.PROTOGEN_MINING_STATION.get())
                .add(ModItems.PROTOGEN_FARMING_STATION.get())
                .add(ModItems.PROTOGEN_GENERATOR.get())
                .add(ModItems.NUTRIENT_PASTE.get())
                .add(ModItems.EMBRYO.get())
                .add(ModItems.DNA.get())
                .add(ModItems.PROTOGEN_AUGMENT.get())
                .add(ModBlocks.NOVULITE_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.NOVULITE_BLOCK.asItem())
                .add(ModBlocks.NOVULITE_ORE.asItem())
                .add(ModItems.PROTOGEN_SERUM.get());

        tag(GalaxyCategory.SERGAL.tag())
                .add(ModItems.AGATE.get())
                .add(ModItems.VILOUS_CLAY.get())
                .add(ModItems.SERGAL_CHEESE.get())
                .add(ModItems.VILOUS_CERAMIC_INGOT.get())
                .add(ModItems.SERGAL_LANCE.get())
                .add(ModItems.SERGAL_SWORD.get())
                .add(ModItems.SERGAL_GREATSWORD.get())
                .add(ModItems.SERGAL_MACE.get())
                .add(ModItems.MERP_MUSIC_DISC.get())
                .add(ModItems.SERGAL_SPAWN_EGG.get())
                .add(ModItems.TALXLEECH_SPAWN_EGG.get())
                .add(ModItems.TALXWEASEL_SPAWN_EGG.get())
                .add(ModItems.STALKER_SPAWN_EGG.get())
                .add(ModItems.GOHUNTAKI_SPAWN_EGG.get())
                .add(ModItems.TALXDOG_SPAWN_EGG.get())
                .add(ModItems.TALXWOLF_SPAWN_EGG.get())
                .add(ModItems.MIZOLE_SPAWN_EGG.get())
                .add(ModItems.SPORE_SPAWN_EGG.get())
                .add(ModItems.MALE_NEVREAN_SPAWN_EGG.get())
                .add(ModItems.FEMALE_NEVREAN_SPAWN_EGG.get())
                .add(ModItems.BOS_AGUDNER_SPAWN_EGG.get())
                .add(ModItems.GOAT_AGUDNER_SPAWN_EGG.get())
                .add(ModItems.RAB_AGUDNER_SPAWN_EGG.get())
                .add(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.VILOUS_CERAMIC_ORE.asItem())
                .add(ModBlocks.AGATE_ORE.asItem())
                .add(ModBlocks.AGATE_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.AGATE_BLOCK.asItem())
                .add(ModItems.SERGAL_SERUM.get());

        tag(GalaxyCategory.EXPIE.tag())
                .add(ModItems.EXPIE_PLUSH.get())
                .add(ModItems.DRESSING.get())
                .add(ModItems.RIPPED_DRESSING.get())
                .add(ModItems.AKITU_PLUSHIE.get())
                .add(ModItems.AMS_PLUSHIE.get())
                .add(ModItems.BOT_PLUSHIE.get())
                .add(ModItems.BROWN_BUNNY_PLUSHIE.get())
                .add(ModItems.BUNNY_PLUSHIE.get())
                .add(ModItems.DAS_PLUSHIE.get())
                .add(ModItems.FRIEND_PLUSH.get())
                .add(ModItems.GORT_PLUSHIE.get())
                .add(ModItems.JIMMY_PLUSHIE.get())
                .add(ModItems.MOFFEE_PLUSHIE.get())
                .add(ModItems.ORANGE_PLUSHIE.get())
                .add(ModItems.PENTER_PLUSHIE.get())
                .add(ModItems.RED_FOX_PLUSHIE.get())
                .add(ModItems.ROBOT_PLUSHIE.get())
                .add(ModItems.SHARK_PLUSHIE.get())
                .add(ModItems.TACO_PLUSHIE.get())
                .add(ModItems.WEH_PLUSHIE.get())
                .add(ModItems.WICK_PLUSHIE.get())
                .add(ModItems.ALCOHOL.get())
                .add(ModItems.ANTI_DEPRESSANTS.get())
                .add(ModItems.ANTIBIOTICS.get())
                .add(ModItems.ANTIRAD.get())
                .add(ModItems.ANTISEPTIC.get())
                .add(ModItems.ANTISERUM.get())
                .add(ModItems.APPLE_JUICE.get())
                .add(ModItems.BLOOD_BAG.get())
                .add(ModItems.BRUISE_KIT.get())
                .add(ModItems.CANTEEN.get())
                .add(ModItems.CHEST_DRAIN.get())
                .add(ModItems.EMPTY_BLOOD_BAG.get())
                .add(ModItems.FENTANYL.get())
                .add(ModItems.FOLIAGE.get())
                .add(ModItems.GEOFRUIT.get())
                .add(ModItems.HEROIN.get())
                .add(ModItems.LOCKPICKING_KIT.get())
                .add(ModItems.MANUAL_DEFIBRILLATOR.get())
                .add(ModItems.MED_KIT.get())
                .add(ModItems.MINDWIPE.get())
                .add(ModItems.MORPHINE.get())
                .add(ModItems.NALOXONE.get())
                .add(ModItems.OPIUM.get())
                .add(ModItems.PAINKILLERS.get())
                .add(ModItems.RELIEF_CREAM.get())
                .add(ModItems.SALINE.get())
                .add(ModItems.SLEEPING_PILLS.get())
                .add(ModItems.SODIUM_NITROPRUSSIDE.get())
                .add(ModItems.STERILIZED_DRESSING.get())
                .add(ModItems.STREPTOKINASE.get())
                .add(ModItems.SYRINGE.get())
                .add(ModItems.TOURNIQUET.get())
                .add(ModItems.TRASH_BAG.get())
                .add(ModItems.WATER_BOTTLE.get())
                .add(ModItems.WOUND_GLUE.get())
                .add(ModItems.COMBAT_PEN.get())
                .add(ModItems.CHUNK_OF_PLASTIC.get())
                .add(ModItems.FLEXIGLASS.get())
                .add(ModItems.BUNDLE_OF_WIRES.get())
                .add(ModItems.CIRCUIT_BOARD.get())
                .add(ModItems.AUTO_INJECTOR.get())
                .add(ModItems.SCRAP_METAL.get())
                .add(ModItems.SCRAP_CUBE.get())
                .add(ModItems.SCRAP_TUBE.get())
                .add(ModItems.SCRAP_PANEL.get())
                .add(ModItems.ROPE.get())
                .add(ModItems.TEMPORARY_BOTTLE.get())
                .add(ModItems.NUMBERRY.get())
                .add(ModItems.GLOWPLANT_FRUIT.get())
                .add(ModBlocks.CONTAINER_CRATE.asItem())
                .add(ModItems.EXPIE_SPAWN_EGG.get())
                .add(ModItems.EXPIE_SERUM.get())
                .add(ModItems.EXPIE_AUGMENT.get());

        tag(GalaxyCategory.WICKERBEASTS.tag())
                .add(ModBlocks.CRYSTALITHIUM_ORE.asItem())
                .add(ModBlocks.CRYSTALITHIUM_DEEPSLATE_ORE.asItem())
                .add(ModItems.CRYSTALITHIUM_CRYSTAL.get())
                .add(ModItems.WICKER_HORN_FRAGMENT.get())
                .add(ModItems.CLAW_SHARD.get())
                .add(ModItems.MANE_TUFT.get())
                .add(ModItems.WICKER_EGG.get())
                .add(ModItems.CRYSTALITHIUM_TRINKET.get())
                .add(ModItems.WICKERBEAST_SPAWN_EGG.get())
                .add(ModItems.WICKERBEAST_SERUM.get());

        tag(GalaxyCategory.FELKIN.tag())
                .add(ModItems.FELKIN_HIDE.get())
                .add(ModItems.NORTHERN_FELKIN_SPAWN_EGG.get());

        tag(GalaxyCategory.ZORGOIA.tag())
                .add(ModItems.ZORGOIA_SPAWN_EGG.get());

        tag(GalaxyCategory.DUTCH_ANGEL_DRAGON.tag())
                .add(ModItems.DUTCH_ANGEL_DRAGON_SPAWN_EGG.get());

        tag(GalaxyCategory.YINGLET.tag())
                .add(ModItems.GREATER_YINGLET_SPAWN_EGG.get());

        tag(GalaxyCategory.MANOKIT.tag())
                .add(ModBlocks.CORAL_CULTIVATION_BLOCK.asItem())
                .add(ModBlocks.MOONLIT_OFFERING_ALTAR.asItem())
                .add(ModBlocks.SEA_GLASS.asItem())
                .add(ModBlocks.CARNELIAN_BLOCK.asItem())
                .add(ModBlocks.JADEITE_BLOCK.asItem())
                .add(ModBlocks.PALM_LEAVES.asItem())
                .add(ModBlocks.PASSION_FRUIT_LEAVES.asItem())
                .add(ModBlocks.PAPAYA_LEAVES.asItem())
                .add(ModBlocks.MOONSTONE_ORE.asItem())
                .add(ModBlocks.MOONSTONE_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.SUNSTONE_ORE.asItem())
                .add(ModBlocks.SUNSTONE_DEEPSLATE_ORE.asItem())
                .add(ModBlocks.LARIMAR_ORE.asItem())
                .add(ModBlocks.LARIMAR_DEEPSLATE_ORE.asItem())
                .add(ModItems.COCONUT.get())
                .add(ModItems.PASSION_FRUIT.get())
                .add(ModItems.PAPAYA.get())
                .add(ModItems.OPEN_COCONUT.get())
                .add(ModItems.DRAGON_FRUIT.get())
                .add(ModItems.OPEN_DRAGON_FRUIT.get())
                .add(ModItems.SHELL.get())
                .add(ModItems.CORAL_PIECE.get())
                .add(ModBlocks.DRIFTWOOD.asItem())
                .add(ModItems.PEARL.get())
                .add(ModItems.BLACK_PEARL.get())
                .add(ModItems.BONE_JEWELRY.get())
                .add(ModItems.PENDANT.get())
                .add(ModItems.MANOKIT_TRINKET.get())
                .add(ModItems.MOONSTONE.get())
                .add(ModItems.SUNSTONE.get())
                .add(ModItems.LARIMAR.get())
                .add(ModItems.ABALONE_SHARD.get())
                .add(ModItems.SEA_OPAL.get())
                .add(ModItems.JADEITE.get())
                .add(ModItems.CARNELIAN.get())
                .add(ModItems.AMMOLITE.get())
                .add(ModItems.BEACHDWELLER_MANOKIT_SPAWN_EGG.get())
                .add(ModItems.TRIBAL_MANOKIT_SPAWN_EGG.get())
                .add(ModItems.ARCANE_MANOKIT_SPAWN_EGG.get())
                .add(ModItems.MANOKIT_SERUM.get());

        tag(EXPIE_MEDICAL_TAG)
                .add(ModItems.ALCOHOL.get())
                .add(ModItems.ANTI_DEPRESSANTS.get())
                .add(ModItems.ANTIBIOTICS.get())
                .add(ModItems.ANTIRAD.get())
                .add(ModItems.ANTISEPTIC.get())
                .add(ModItems.ANTISERUM.get())
                .add(ModItems.BLOOD_BAG.get())
                .add(ModItems.BRUISE_KIT.get())
                .add(ModItems.EMPTY_BLOOD_BAG.get())
                .add(ModItems.FENTANYL.get())
                .add(ModItems.HEROIN.get())
                .add(ModItems.MED_KIT.get())
                .add(ModItems.MINDWIPE.get())
                .add(ModItems.MORPHINE.get())
                .add(ModItems.NALOXONE.get())
                .add(ModItems.OPIUM.get())
                .add(ModItems.PAINKILLERS.get())
                .add(ModItems.RELIEF_CREAM.get())
                .add(ModItems.SALINE.get())
                .add(ModItems.SLEEPING_PILLS.get())
                .add(ModItems.SODIUM_NITROPRUSSIDE.get())
                .add(ModItems.DRESSING.get())
                .add(ModItems.RIPPED_DRESSING.get())
                .add(ModItems.STERILIZED_DRESSING.get())
                .add(ModItems.STREPTOKINASE.get())
                .add(ModItems.WOUND_GLUE.get());

        tag(EXPIE_TOOLS_TAG)
                .add(ModItems.CHEST_DRAIN.get())
                .add(ModItems.LOCKPICKING_KIT.get())
                .add(ModItems.MANUAL_DEFIBRILLATOR.get())
                .add(ModItems.SYRINGE.get())
                .add(ModItems.AUTO_INJECTOR.get())
                .add(ModItems.TOURNIQUET.get());

        tag(EXPIE_UTILITY_TAG)
                .add(ModItems.APPLE_JUICE.get())
                .add(ModItems.CANTEEN.get())
                .add(ModItems.WATER_BOTTLE.get())
                .add(ModItems.TRASH_BAG.get())
                .add(ModItems.CHUNK_OF_PLASTIC.get())
                .add(ModItems.FLEXIGLASS.get())
                .add(ModItems.BUNDLE_OF_WIRES.get())
                .add(ModItems.CIRCUIT_BOARD.get())
                .add(ModItems.SCRAP_METAL.get())
                .add(ModItems.SCRAP_CUBE.get())
                .add(ModItems.SCRAP_TUBE.get())
                .add(ModItems.SCRAP_PANEL.get())
                .add(ModItems.ROPE.get())
                .add(ModItems.TEMPORARY_BOTTLE.get())
                .add(ModItems.NUMBERRY.get())
                .add(ModItems.GLOWPLANT_FRUIT.get())
                .add(ModItems.GEOFRUIT.get())
                .add(ModItems.FOLIAGE.get());
    }

    public static final TagKey<net.minecraft.world.item.Item> EXPIE_MEDICAL_TAG =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "expie_medical"));
    public static final TagKey<net.minecraft.world.item.Item> EXPIE_TOOLS_TAG =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "expie_tools"));
    public static final TagKey<net.minecraft.world.item.Item> EXPIE_UTILITY_TAG =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "expie_utility"));
}
