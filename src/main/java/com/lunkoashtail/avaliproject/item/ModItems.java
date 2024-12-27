package com.lunkoashtail.avaliproject.item;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.item.custom.AvaliswordItem;
import com.lunkoashtail.avaliproject.item.custom.FuelItem;
import com.lunkoashtail.avaliproject.item.custom.ModEffectSwordItem;
import com.lunkoashtail.avaliproject.item.custom.SimpleDrinkableItem;
import com.lunkoashtail.avaliproject.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
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
    public static final DeferredItem<Item> NOVULITE = ITEMS.register("novulite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AGATE = ITEMS.register("agate",
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
            () -> new ItemNameBlockItem(ModBlocks.GROOU_CROP_BLOCK.get() , new Item.Properties().food(ModFoodProperties.GROOU)){
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
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new Item(new Item.Properties()));
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

//    public static final DeferredItem<SwordItem> AVALI_SWORD = ITEMS.register("avali_sword",
//            () -> new SwordItem(ModToolTiers.AEROGEL, new Item.Properties()
//                    .attributes(SwordItem.createAttributes(ModToolTiers.AEROGEL, 5, -2.4f))){
//                @Override
//                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_sword.tooltip"));
//                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//                }
//            });

    public static final DeferredItem<Item> AVALI_SWORD = ITEMS.register("avali_sword", AvaliswordItem::new);

    public static final DeferredItem<SwordItem> AVALI_SPEAR = ITEMS.register("avali_spear",
            () -> new ModEffectSwordItem(ModToolTiers.AEROGEL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.AEROGEL, 8, -2.7f)), MobEffects.MOVEMENT_SLOWDOWN){
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
//    public static final DeferredItem<Item> AVALI_AXE = ITEMS.register("avali_axe", AvaliAxeItem::new );
    public static final DeferredItem<AxeItem> AVALI_AXE = ITEMS.register("avali_axe",
        () -> new AxeItem(ModToolTiers.AEROGEL, new Item.Properties()
                .attributes(AxeItem.createAttributes(ModToolTiers.AEROGEL, 4.0F, -2.8f))){
            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_axe.tooltip"));
                super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            }
        });

    public static final DeferredItem<AxeItem> PROTOGEN_AXE = ITEMS.register("protogen_axe",
            () -> new AxeItem(ModToolTiers.HARDLIGHT, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.HARDLIGHT, 6.0F, -2.8f))){
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
    public static final DeferredItem<Item> SKSCEEGEHKJA_SPAWN_EGG = ITEMS.register("sksceegehkja_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SKSCEEGEHKJA, 0x5d5d5d, 0xc9c9c9,
                    new Item.Properties()));
    public static final DeferredItem<Item> SKACIKKJRRBWCAK_SPAWN_EGG = ITEMS.register("skacikkjrrbwcak_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SKACIKKJRRKBWCAK, 0x623928, 0x91776d,
                    new Item.Properties()));
    public static final DeferredItem<Item> EEPUOR_SPAWN_EGG = ITEMS.register("eepuor_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.EEPUOR, 0x2d2f31, 0x3effff,
                    new Item.Properties()));

    public static final DeferredItem<Item> PROTOGEN_SPAWN_EGG = ITEMS.register("protogen_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.PROTOGEN, 0xdbdbdb, 0x68ffff,
                    new Item.Properties()));
    public static final DeferredItem<Item> PRIMAGEN_SPAWN_EGG = ITEMS.register("primagen_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.PRIMAGEN, 0xeaeaea, 0x68ff55,
                    new Item.Properties()));
    public static final DeferredItem<Item> AVALI_SPAWN_EGG = ITEMS.register("avali_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.AVALI, 0xff8432, 0xfff7d6,
                    new Item.Properties()));
    public static final DeferredItem<Item> SERGAL_SPAWN_EGG = ITEMS.register("sergal_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SERGAL, 0xf6d6c9, 0x84121e,
                    new Item.Properties()));

    public static final DeferredItem<Item> CAKLERAH_SPAWN_EGG = ITEMS.register("caklerah_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.CAKLERAH, 0xeae6da, 0xf19751,
                    new Item.Properties()));
    public static final DeferredItem<Item> MAMAGEN_SPAWN_EGG = ITEMS.register("mamagen_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.MAMAGEN, 0x1b1c2b, 0x9fa1b7,
                    new Item.Properties()));
    public static final DeferredItem<Item> TALXLEECH_SPAWN_EGG = ITEMS.register("talxleech_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.TALXLEECH, 0x783c1d, 0x939393,
                    new Item.Properties()));
    public static final DeferredItem<Item> TALXWEASEL_SPAWN_EGG = ITEMS.register("talxweasel_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.TALXWEASEL, 0x0d0d0c, 0x484b47,
                    new Item.Properties()));
    public static final DeferredItem<Item> STALKER_SPAWN_EGG = ITEMS.register("stalker_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.STALKER, 0x9d9d9d, 0x9f3d00,
                    new Item.Properties()));
    public static final DeferredItem<Item> GOHUNTAKI_SPAWN_EGG = ITEMS.register("gohuntaki_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.GOHUNTAKI, 0x564d4c, 0x8d381e,
                    new Item.Properties()));
    public static final DeferredItem<Item> TALXDOG_SPAWN_EGG = ITEMS.register("talxdog_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.TALXDOG, 0x7e7e6f, 0x261e20,
                    new Item.Properties()));
    public static final DeferredItem<Item> TALXWOLF_SPAWN_EGG = ITEMS.register("talxwolf_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.TALXWOLF, 0xa7a7a7, 0x303736,
                    new Item.Properties()));

    public static final DeferredItem<Item> MIZOLE_SPAWN_EGG = ITEMS.register("mizole_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.MIZOLE, 0x8f9596, 0x4f879a,
                    new Item.Properties()));
    public static final DeferredItem<Item> SPORE_SPAWN_EGG = ITEMS.register("spore_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SPORE, 0xcacaca, 0x757d5b,
                    new Item.Properties()));
    public static final DeferredItem<Item> FEMALE_NEVREAN_SPAWN_EGG = ITEMS.register("female_nevrean_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.FEMALE_NEVREAN, 0x554d40, 0xcfac69,
                    new Item.Properties()));
    public static final DeferredItem<Item> MALE_NEVREAN_SPAWN_EGG = ITEMS.register("male_nevrean_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.MALE_NEVREAN, 0xfbdd67, 0xccee0e,
                    new Item.Properties()));

    public static final DeferredItem<SwordItem> SERGAL_GREATSWORD = ITEMS.register("sergal_greatsword",
            () -> new SwordItem(ModToolTiers.CERAMIC, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CERAMIC, 8.0F, -2.4f))));
    public static final DeferredItem<SwordItem> SERGAL_LANCE = ITEMS.register("sergal_lance",
            () -> new SwordItem(ModToolTiers.CERAMIC, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CERAMIC, 6.0F, -1.5f))));
    public static final DeferredItem<SwordItem> SERGAL_MACE = ITEMS.register("sergal_mace",
            () -> new SwordItem(ModToolTiers.CERAMIC, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CERAMIC, 9.0F, -3.2f))));
//    public static final DeferredItem<Item> SERGAL_SLINGSHOT = ITEMS.register("sergal_slingshot",
//            () -> new BowItem(new Item.Properties().durability(996)){
//                @Override
//                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.sergal_slingshot.tooltip"));
//                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//                }
//            });

    public static final DeferredItem<SwordItem> SERGAL_SWORD = ITEMS.register("sergal_sword",
            () -> new SwordItem(ModToolTiers.CERAMIC, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CERAMIC, 6.5F, -2.2f))));
    public static final DeferredItem<Item> MERP_MUSIC_DISC = ITEMS.registerItem("merp_music_disc",
            properties -> new Item(properties.jukeboxPlayable(ModSounds.MERP_KEY).stacksTo(1)));
    public static final DeferredItem<Item> AVALI_DANCE_MUSIC_DISC = ITEMS.registerItem("avali_dance_music_disc",
            properties -> new Item(properties.jukeboxPlayable(ModSounds.AVALI_DANCE_KEY).stacksTo(1)));
    public static final DeferredItem<Item> CYBERNETIC_HEART_MUSIC_DISC = ITEMS.registerItem("cybernetic_heart_music_disc",
            properties -> new Item(properties.jukeboxPlayable(ModSounds.CYBERNETIC_HEART_KEY).stacksTo(1)));


 //   public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(AvaliProject.MOD_ID);
 //   public static final DeferredItem<Item> AVALI_SWORD = REGISTRY.register("avali_sword", AvaliswordItem::new);


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
