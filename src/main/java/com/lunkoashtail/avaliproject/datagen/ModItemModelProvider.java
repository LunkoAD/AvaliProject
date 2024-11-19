package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
        //Items
        basicItem(ModItems.AERO_CRYSTAL.get());
        basicItem(ModItems.THERMAL_CRYSTAL.get());
        basicItem(ModItems.SYNC_CRYSTAL.get());
        basicItem(ModItems.AEROGEL.get());
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


        //Icons
        basicItem(ModItems.AVALI_ICON.get());
        basicItem(ModItems.PROTOGEN_ICON.get());
        basicItem(ModItems.SERGAL_ICON.get());

        //Tools
        basicItem(ModItems.AVALI_AXE.get());
        basicItem(ModItems.AVALI_PICKAXE.get());
        basicItem(ModItems.AVALI_HOE.get());
        basicItem(ModItems.AVALI_SWORD.get());

        //Foods
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

    }

    private void trimmedArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MOD_ID = AvaliProject.MOD_ID; // Change this to your mod id

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
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
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
