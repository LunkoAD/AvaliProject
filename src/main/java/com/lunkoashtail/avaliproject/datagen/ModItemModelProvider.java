package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.WoodSet;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AvaliProject.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        
        basicItem(ModItems.AERO_CRYSTAL.get());
        basicItem(ModItems.THERMAL_CRYSTAL.get());
        basicItem(ModItems.SYNC_CRYSTAL.get());
        basicItem(ModItems.AEROGEL.get());
        basicItem(ModItems.AEROGEL_BATTERY.get());
        basicItem(ModItems.AEROMER.get());
        basicItem(ModItems.AEROMER.get());
        basicItem(ModItems.AVALI_BOTTLE.get());
        basicItem(ModItems.DURASTEEL_INGOT.get());
        basicItem(ModItems.RAW_DURASTEEL.get());
        basicItem(ModItems.REFINED_AEGISALT.get());
        basicItem(ModItems.RAW_AEGISALT.get());
        basicItem(ModItems.TITANIUM_INGOT.get());
        basicItem(ModItems.RAW_TITANIUM.get());
        basicItem(ModItems.FIBER.get());
        basicItem(ModItems.LUME.get());
        basicItem(ModItems.LUME_BIT.get());
        basicItem(ModItems.NAKATI_BARK.get());
        basicItem(ModItems.NANITE_INJECTOR.get());
        basicItem(ModItems.PIRU_FLOUR.get());
        basicItem(ModItems.PROTOSTEEL_INGOT.get());
        basicItem(ModItems.WOVEN_FABRIC.get());
        basicItem(ModItems.WOVEN_GRAPHENE.get());
        basicItem(ModItems.VILOUS_CLAY.get());
        basicItem(ModItems.SERGAL_CHEESE.get());
        basicItem(ModItems.VILOUS_CERAMIC_INGOT.get());
        basicItem(ModItems.PROTOGEN_RAM.get());
        basicItem(ModItems.ARCAITES_CRYSTAL.get());
        basicItem(ModItems.NOVULITE.get());
        basicItem(ModItems.AGATE.get());
        basicItem(ModItems.MERP_MUSIC_DISC.get());
        basicItem(ModItems.AVALI_DANCE_MUSIC_DISC.get());
        basicItem(ModItems.CYBERNETIC_HEART_MUSIC_DISC.get());
        basicItem(ModItems.FIRELANCE_HILT.get());
        basicItem(ModItems.FIRELANCE_MAIN.get());
        basicItem(ModItems.FIRELANCE_MUZZLE.get());
        basicItem(ModItems.FIRELANCE_SCOPE.get());
        basicItem(ModItems.MLSERIES_MAIN.get());
        basicItem(ModItems.MLSERIES_HILT.get());
        basicItem(ModItems.MLSERIES_MUZZLE.get());
        basicItem(ModItems.QRC_HILT.get());
        basicItem(ModItems.QRC_MAIN.get());
        basicItem(ModItems.QRC_MUZZLE.get());
        basicItem(ModItems.NOVA_HILT.get());
        basicItem(ModItems.NOVA_MAIN.get());
        basicItem(ModItems.NOVA_SCOPE.get());
        basicItem(ModItems.NOVA_MUZZLE.get());
        basicItem(ModItems.AVALI_DRONE_CORE.get());
        basicItem(ModItems.AVALI_DRONE_ROTORS.get());
        basicItem(ModItems.AVALI_DRONE.get());
        basicItem(ModItems.AVALI_DATA_CHIT.get());
        basicItem(ModItems.STORM_HILT.get());
        basicItem(ModItems.STORM_MUZZLE.get());
        basicItem(ModItems.STORM_CORE.get());
        basicItem(ModItems.BLIZZARD_HILT.get());
        basicItem(ModItems.BLIZZARD_MUZZLE.get());
        basicItem(ModItems.BLIZZARD_CORE.get());
        basicItem(ModItems.RAILGUN.get());
        getBuilder(ModItems.EXPIE_PLUSH.getId().getPath())
                .customLoader(SeparateTransformsModelBuilder::begin)
                .base(nested().parent(getExistingFile(modLoc("block/expie_plush"))))
                .perspective(ItemDisplayContext.GUI, nested().parent(getExistingFile(mcLoc("item/generated")))
                        .texture("layer0", modLoc("item/expie_plush")))
                .end();
        basicItem(ModItems.NUTRIENT_PASTE.get());
        basicItem(ModItems.EMBRYO.get());
        basicItem(ModItems.DNA.get());
        basicItem(ModItems.PROTOGEN_AUGMENT.get());
        basicItem(ModItems.AVALI_AUGMENT.get());
        basicItem(ModItems.EXPIE_AUGMENT.get());
        protogenMachineItem(ModItems.SYNCH_POD, "synch_pod/synch_pod_classic", 0.18F, 4.0F, 0.08F);
        protogenMachineItem(ModItems.GROWTH_VAT, "growth_vat/growth_vat_classic", 0.16F, 6.0F, 0.07F);
        protogenMachineItem(ModItems.PROTOGEN_RESEARCH_STATION, "protogen_research_station", 0.4F, 4.8F, 0.25F);
        protogenMachineItem(ModItems.PROTOGEN_MINING_STATION, "protogen_mining_station", 0.45F, 5.4F, 0.3F);
        protogenMachineItem(ModItems.PROTOGEN_FARMING_STATION, "protogen_farming_station", 0.45F, 5.4F, 0.3F);
        protogenMachineItem(ModItems.PROTOGEN_GENERATOR, "protogen_generator", 0.3F, 4.8F, 0.2F);
        basicItem(ModItems.DRESSING.get());
        basicItem(ModItems.RIPPED_DRESSING.get());

        
        basicItem(ModItems.AKITU_PLUSHIE.get());
        basicItem(ModItems.AMS_PLUSHIE.get());
        basicItem(ModItems.BOT_PLUSHIE.get());
        basicItem(ModItems.BROWN_BUNNY_PLUSHIE.get());
        basicItem(ModItems.BUNNY_PLUSHIE.get());
        basicItem(ModItems.DAS_PLUSHIE.get());
        basicItem(ModItems.FRIEND_PLUSH.get());
        basicItem(ModItems.GORT_PLUSHIE.get());
        basicItem(ModItems.JIMMY_PLUSHIE.get());
        basicItem(ModItems.MOFFEE_PLUSHIE.get());
        basicItem(ModItems.ORANGE_PLUSHIE.get());
        basicItem(ModItems.PENTER_PLUSHIE.get());
        basicItem(ModItems.RED_FOX_PLUSHIE.get());
        basicItem(ModItems.ROBOT_PLUSHIE.get());
        basicItem(ModItems.SHARK_PLUSHIE.get());
        basicItem(ModItems.TACO_PLUSHIE.get());
        basicItem(ModItems.WEH_PLUSHIE.get());
        basicItem(ModItems.WICK_PLUSHIE.get());

        
        basicItem(ModItems.ALCOHOL.get());
        basicItem(ModItems.ANTI_DEPRESSANTS.get());
        basicItem(ModItems.ANTIBIOTICS.get());
        basicItem(ModItems.ANTIRAD.get());
        basicItem(ModItems.ANTISEPTIC.get());
        basicItem(ModItems.ANTISERUM.get());
        basicItem(ModItems.APPLE_JUICE.get());
        basicItem(ModItems.BLOOD_BAG.get());
        basicItem(ModItems.BRUISE_KIT.get());
        basicItem(ModItems.CANTEEN.get());
        basicItem(ModItems.CHEST_DRAIN.get());
        basicItem(ModItems.EMPTY_BLOOD_BAG.get());
        basicItem(ModItems.FENTANYL.get());
        basicItem(ModItems.FOLIAGE.get());
        basicItem(ModItems.GEOFRUIT.get());
        basicItem(ModItems.HEROIN.get());
        basicItem(ModItems.LOCKPICKING_KIT.get());
        basicItem(ModItems.MANUAL_DEFIBRILLATOR.get());
        basicItem(ModItems.MED_KIT.get());
        basicItem(ModItems.MINDWIPE.get());
        basicItem(ModItems.MORPHINE.get());
        basicItem(ModItems.NALOXONE.get());
        basicItem(ModItems.OPIUM.get());
        basicItem(ModItems.PAINKILLERS.get());
        basicItem(ModItems.RELIEF_CREAM.get());
        basicItem(ModItems.SALINE.get());
        basicItem(ModItems.SLEEPING_PILLS.get());
        basicItem(ModItems.SODIUM_NITROPRUSSIDE.get());
        basicItem(ModItems.STERILIZED_DRESSING.get());
        basicItem(ModItems.STREPTOKINASE.get());
        basicItem(ModItems.SYRINGE.get());
        basicItem(ModItems.TOURNIQUET.get());
        basicItem(ModItems.TRASH_BAG.get());
        basicItem(ModItems.WATER_BOTTLE.get());
        basicItem(ModItems.WOUND_GLUE.get());

        basicItem(ModItems.CHUNK_OF_PLASTIC.get());
        basicItem(ModItems.FLEXIGLASS.get());
        basicItem(ModItems.SCRAP_CUBE.get());
        basicItem(ModItems.SCRAP_TUBE.get());
        basicItem(ModItems.BUNDLE_OF_WIRES.get());
        basicItem(ModItems.CIRCUIT_BOARD.get());
        basicItem(ModItems.AUTO_INJECTOR.get());
        basicItem(ModItems.SCRAP_METAL.get());
        basicItem(ModItems.SCRAP_PANEL.get());
        basicItem(ModItems.ROPE.get());
        basicItem(ModItems.TEMPORARY_BOTTLE.get());
        basicItem(ModItems.NUMBERRY.get());
        basicItem(ModItems.GLOWPLANT_FRUIT.get());

        
        basicItem(ModItems.GALAXY_ICON.get());
        basicItem(ModItems.AVALI_ICON.get());
        basicItem(ModItems.PROTOGEN_ICON.get());
        basicItem(ModItems.SERGAL_ICON.get());

        

        basicItem(ModItems.AVALI_PICKAXE.get());
        basicItem(ModItems.AVALI_HOE.get());

        basicItem(ModItems.PROTOGEN_SWORD.get());
        basicItem(ModItems.PROTOGEN_AXE.get());

        basicItem(ModItems.SERGAL_GREATSWORD.get());
        basicItem(ModItems.SERGAL_LANCE.get());
        basicItem(ModItems.SERGAL_SWORD.get());

        basicItem(ModItems.SERGAL_MACE.get());

        
        basicItem(ModItems.AVALI_BBQ.get());
        basicItem(ModItems.AVALON_TACO.get());
        basicItem(ModItems.AVALI_MUFFIN.get());
        basicItem(ModItems.KIRI_CIDER.get());
        basicItem(ModItems.KIRI_CURRY.get());
        basicItem(ModItems.KIRI_FRUIT.get());
        basicItem(ModItems.KIRI_JAM.get());
        basicItem(ModItems.KIRIKIRI_PIE.get());
        basicItem(ModItems.GROOU.get());
        basicItem(ModItems.GROOU_JUICE.get());
        basicItem(ModItems.NAKATI_OVOID.get());
        basicItem(ModItems.PIRU_COLONY.get());
        basicItem(ModItems.PIRU_NOODLE.get());
        basicItem(ModItems.PIRUZA.get());
        basicItem(ModItems.SPICY_JERKY.get());
        basicItem(ModItems.TUCKER.get());
        basicItem(ModItems.PIRU_FROND.get());


        flowerItem(ModBlocks.GROOU_NODULE);
        flowerItem(ModBlocks.PIRU_NODULE);
        flowerItem(ModBlocks.NAKATI_NODULE);
        flowerItem(ModBlocks.KIRI_NODULE);

        withExistingParent(ModItems.SKSCEEGEHKJA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SKACIKKJRRBWCAK_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.PRIMAGEN_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.PROTOGEN_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.AVALI_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SERGAL_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.EEPUOR_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MAMAGEN_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.STALKER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CAKLERAH_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TALXLEECH_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TALXWEASEL_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GOHUNTAKI_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TALXDOG_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TALXWOLF_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MIZOLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SPORE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.FEMALE_NEVREAN_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MALE_NEVREAN_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CHRGAKBZ_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.AVALI_DRONE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.EXPIE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BOS_AGUDNER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GOAT_AGUDNER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.RAB_AGUDNER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        basicItem(ModItems.CRYSTALITHIUM_CRYSTAL.get());
        basicItem(ModItems.WICKER_HORN_FRAGMENT.get());
        basicItem(ModItems.CLAW_SHARD.get());
        basicItem(ModItems.MANE_TUFT.get());
        basicItem(ModItems.CRYSTALITHIUM_TRINKET.get());
        basicItem(ModItems.FELKIN_HIDE.get());
        basicItem(ModItems.COCONUT.get());
        basicItem(ModItems.PASSION_FRUIT.get());
        basicItem(ModItems.PAPAYA.get());
        basicItem(ModItems.OPEN_COCONUT.get());
        basicItem(ModItems.DRAGON_FRUIT.get());
        basicItem(ModItems.OPEN_DRAGON_FRUIT.get());
        for (WoodSet set : ModBlocks.WOOD_SETS) {
            fenceItem(set.fence, set.planks);
            buttonItem(set.button, set.planks);
            basicItem(set.signItem.get());
            basicItem(set.hangingSignItem.get());
        }
        basicItem(ModItems.SHELL.get());
        basicItem(ModItems.CORAL_PIECE.get());
        basicItem(ModItems.PEARL.get());
        basicItem(ModItems.BLACK_PEARL.get());
        basicItem(ModItems.PENDANT.get());
        basicItem(ModItems.MANOKIT_TRINKET.get());
        basicItem(ModItems.BONE_JEWELRY.get());
        basicItem(ModItems.MOONSTONE.get());
        basicItem(ModItems.SUNSTONE.get());
        basicItem(ModItems.LARIMAR.get());
        basicItem(ModItems.ABALONE_SHARD.get());
        basicItem(ModItems.SEA_OPAL.get());
        basicItem(ModItems.JADEITE.get());
        basicItem(ModItems.CARNELIAN.get());
        basicItem(ModItems.AMMOLITE.get());

        withExistingParent(ModItems.WICKERBEAST_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.FELKIN_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.NORTHERN_FELKIN_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ZORGOIA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DUTCH_ANGEL_DRAGON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GREATER_YINGLET_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BEACHDWELLER_MANOKIT_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TRIBAL_MANOKIT_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ARCANE_MANOKIT_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        basicItem(ModItems.HUMAN_SERUM.get());
        basicItem(ModItems.EXPIE_SERUM.get());
        basicItem(ModItems.AVALI_SERUM.get());
        basicItem(ModItems.SERGAL_SERUM.get());
        basicItem(ModItems.PROTOGEN_SERUM.get());
        basicItem(ModItems.WICKERBEAST_SERUM.get());
        basicItem(ModItems.MANOKIT_SERUM.get());

        for (DeferredItem<Item> icon : ModItems.ORIGIN_ICONS) {
            String path = icon.getId().getPath();
            withExistingParent(path, mcLoc("item/generated"))
                    .texture("layer0", modLoc("origin/" + path.substring(0, path.length() - "_origin_icon".length())));
        }
    }

    private void protogenMachineItem(DeferredItem<Item> item, String particle, float guiScale, float guiDrop, float handScale) {
        String name = item.getId().getPath();
        getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", modLoc("block/" + particle))
                .transforms()
                .transform(ItemDisplayContext.GUI).rotation(30, 225, 0).translation(0, -guiDrop, 0).scale(guiScale).end()
                .transform(ItemDisplayContext.FIXED).rotation(0, 180, 0).translation(0, -guiDrop, 0).scale(guiScale).end()
                .transform(ItemDisplayContext.GROUND).translation(0, 3, 0).scale(handScale).end()
                .transform(ItemDisplayContext.HEAD).translation(0, 13, 0).scale(handScale * 2).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(75, 45, 0).translation(0, 2.5F, 0).scale(handScale).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(75, 45, 0).translation(0, 2.5F, 0).scale(handScale).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, 45, 0).translation(0, 0, 0).scale(handScale * 1.5F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 225, 0).translation(0, 0, 0).scale(handScale * 1.5F).end()
                .end();
    }

    private void trimmedArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MOD_ID = AvaliProject.MOD_ID; 

        if(itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); 
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                
                
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                
                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()));

            });

        }
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID,"item/" + item.getId().getPath()));
    }
    public void flowerItem(DeferredBlock<Block> block) {
        this.withExistingParent(block.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0",  ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID,
                        "block/" + block.getId().getPath()));

    }

}
