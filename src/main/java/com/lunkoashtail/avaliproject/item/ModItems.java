package com.lunkoashtail.avaliproject.item;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.item.custom.FuelItem;
import com.lunkoashtail.avaliproject.item.custom.SimpleDrinkableItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AvaliProject.MOD_ID);

    public static final DeferredItem<Item> LUME = ITEMS.register("lume",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LUME_BIT = ITEMS.register("lume_bit",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AVALI_ICON = ITEMS.register("avali_icon",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PROTOGEN_ICON = ITEMS.register("protogen_icon",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SERGAL_ICON = ITEMS.register("sergal_icon",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> THERMAL_CRYSTAL = ITEMS.register("thermal_crystal",
            () -> new FuelItem(new Item.Properties(), 2600));
    public static final DeferredItem<Item> KIRIKIRI_PIE = ITEMS.register("kirikiri_pie",
            () -> new Item(new Item.Properties().food(ModFoodProperties.KIRIKIRI_PIE)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.kirikiri_pie.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> AVALI_MUFFIN = ITEMS.register("avali_muffin",
            () -> new Item(new Item.Properties().food(ModFoodProperties.AVALI_MUFFIN)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_muffin.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> AVALON_TACO = ITEMS.register("avalon_taco",
            () -> new Item(new Item.Properties().food(ModFoodProperties.AVALON_TACO)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avalon_taco.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });


    public static final DeferredItem<Item> GROOU = ITEMS.register("groou",
            () -> new ItemNameBlockItem(ModBlocks.GROOU_CROP_BLOCK.get(), new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.groou.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> NAKATI_OVOID = ITEMS.register("nakati_ovoid",
            () -> new ItemNameBlockItem(ModBlocks.NAKATI_CROP_BLOCK.get(), new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.nakati_ovoid.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> KIRI_FRUIT = ITEMS.register("kiri_fruit",
            () -> new ItemNameBlockItem(ModBlocks.KIRI_CROP_BLOCK.get(), new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.kiri_fruit.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> PIRU_COLONY = ITEMS.register("piru_colony",
            () -> new ItemNameBlockItem(ModBlocks.PIRU_CROP_BLOCK.get(), new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.piru_colony.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> PIRUZA = ITEMS.register("piruza",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PIRUZA)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.piruza.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final  DeferredItem<Item> GROOU_JUICE = ITEMS.register("groou_juice",
            () -> new SimpleDrinkableItem(new Item.Properties().food(ModFoodProperties.GROOU_JUICE)){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.groou_juice.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> KIRI_CURRY = ITEMS.register("kiri_curry",
            () -> new Item(new Item.Properties().food(ModFoodProperties.KIRI_CURRY)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.kiri_curry.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> KIRI_JAM = ITEMS.register("kiri_jam",
            () -> new SimpleDrinkableItem(new Item.Properties().food(ModFoodProperties.KIRI_JAM)){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.kiri_jam.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> PIRU_FLOUR = ITEMS.register("piru_flour",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.piru_flour.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> PIRU_NOODLE = ITEMS.register("piru_noodle",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PIRU_NOODLE)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.piru_noodle.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> AEROGEL = ITEMS.register("aerogel",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AERO_CRYSTAL = ITEMS.register("aero_crystal",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ARCAITES_CRYSTAL = ITEMS.register("arcaites_crystal",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SYNC_CRYSTAL = ITEMS.register("sync_crystal",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_DURASTEEL = ITEMS.register("raw_durasteel",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_AEGISALT = ITEMS.register("raw_aegisalt",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_TITANIUM = ITEMS.register("raw_titanium",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VILOUS_CLAY = ITEMS.register("vilous_clay",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> REFINED_AEGISALT = ITEMS.register("refined_aegisalt",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.refined_aegisalt.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> DURASTEEL_INGOT = ITEMS.register("durasteel_ingot",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.durasteel_ingot.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.titanium_ingot.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> AEROMER = ITEMS.register("aeromer",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.aeromer.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> AVALI_BOTTLE = ITEMS.register("avali_bottle",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_bottle.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> FIBER = ITEMS.register("fiber",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.fiber.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> NAKATI_BARK = ITEMS.register("nakati_bark",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.nakati_bark.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> NANITE_INJECTOR = ITEMS.register("nanite_injector",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.nanite_injector.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> PROTOSTEEL_INGOT = ITEMS.register("protosteel_ingot",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.protosteel_ingot.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> WOVEN_FABRIC = ITEMS.register("woven_fabric",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.woven_fabric.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> WOVEN_GRAPHENE = ITEMS.register("woven_graphene",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.woven_graphene.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> TUCKER = ITEMS.register("tucker",
            () -> new Item(new Item.Properties().food(ModFoodProperties.TUCKER)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.tucker.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> KIRI_CIDER = ITEMS.register("kiri_cider",
            () -> new SimpleDrinkableItem(new Item.Properties().food(ModFoodProperties.KIRI_CIDER)){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.kiri_cider.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> SPICY_JERKY = ITEMS.register("spicy_jerky",
            () -> new Item(new Item.Properties().food(ModFoodProperties.SPICY_JERKY)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.spicy_jerky.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> AVALI_BBQ = ITEMS.register("avali_bbq",
            () -> new Item(new Item.Properties().food(ModFoodProperties.AVALI_BBQ)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_bbq.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> SERGAL_CHEESE = ITEMS.register("sergal_cheese",
            () -> new Item(new Item.Properties().food(ModFoodProperties.SERGAL_CHEESE)));
    public static final DeferredItem<Item> PROTOGEN_RAM = ITEMS.register("protogen_ram",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PROTOGEN_RAM)));
    public static final DeferredItem<Item> VILOUS_CERAMIC_INGOT = ITEMS.register("vilous_ceramic_ingot",
            () -> new Item(new Item.Properties()));

// Tools

    public static final DeferredItem<SwordItem> AVALI_SWORD = ITEMS.register("avali_sword",
            () -> new SwordItem(ModToolTiers.AEROGEL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.AEROGEL, 5, -2.4f))){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_sword.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<SwordItem> AVALI_SPEAR = ITEMS.register("avali_spear",
            () -> new SwordItem(ModToolTiers.AEROGEL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.AEROGEL, 8, -2.7f))){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_spear.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<SwordItem> PROTOGEN_SWORD = ITEMS.register("protogen_sword",
            () -> new SwordItem(ModToolTiers.HARDLIGHT, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.HARDLIGHT, 6, -1.4f))){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.protogen_sword.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });



    public static final DeferredItem<PickaxeItem> AVALI_PICKAXE = ITEMS.register("avali_pickaxe",
            () -> new PickaxeItem(ModToolTiers.AEROGEL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.AEROGEL, 1.0F, -2.8f))){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_pickaxe.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<AxeItem> AVALI_AXE = ITEMS.register("avali_axe",
            () -> new AxeItem(ModToolTiers.AEROGEL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.AEROGEL, 6.0F, -3.2f))){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_axe.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<AxeItem> PROTOGEN_AXE = ITEMS.register("protogen_axe",
            () -> new AxeItem(ModToolTiers.HARDLIGHT, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.HARDLIGHT, 6.0F, -3.2f))){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.protogen_axe.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });


    public static final DeferredItem<HoeItem> AVALI_HOE = ITEMS.register("avali_hoe",
            () -> new HoeItem(ModToolTiers.AEROGEL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.AEROGEL, 0F, -3.0f))){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_hoe.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> PIRU_FROND = ITEMS.register("piru_frond",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PIRU_FROND)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.piru_frond.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> SKSKCEEGEHKJA_SPAWN_EGG = ITEMS.register("skskceegehkja_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SKSKCEEGEHKJA, 0xdebd47, 0xccbfbe,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
