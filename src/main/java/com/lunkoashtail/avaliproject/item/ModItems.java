package com.lunkoashtail.avaliproject.item;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.item.custom.*;
import com.lunkoashtail.avaliproject.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AvaliProject.MOD_ID);

    public static final RegistryObject<Item> LUME = ITEMS.register("lume",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NOVULITE = ITEMS.register("novulite",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AGATE = ITEMS.register("agate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LUME_BIT = ITEMS.register("lume_bit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AVALI_ICON = ITEMS.register("avali_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PROTOGEN_ICON = ITEMS.register("protogen_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SERGAL_ICON = ITEMS.register("sergal_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_CRYSTAL = ITEMS.register("thermal_crystal",
            () -> new FuelItem(new Item.Properties(), 2600));
    public static final RegistryObject<Item> KIRIKIRI_PIE = ITEMS.register("kirikiri_pie",
            () -> new Item(new Item.Properties().food(ModFoodProperties.KIRIKIRI_PIE)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.kirikiri_pie.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> AVALI_MUFFIN = ITEMS.register("avali_muffin",
            () -> new Item(new Item.Properties().food(ModFoodProperties.AVALI_MUFFIN)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_muffin.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> AVALON_TACO = ITEMS.register("avalon_taco",
            () -> new Item(new Item.Properties().food(ModFoodProperties.AVALON_TACO)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avalon_taco.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });


    public static final RegistryObject<Item> GROOU = ITEMS.register("groou",
            () -> new ItemNameBlockItem(ModBlocks.GROOU_CROP_BLOCK.get() , new Item.Properties().food(ModFoodProperties.GROOU)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.groou.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> NAKATI_OVOID = ITEMS.register("nakati_ovoid",
            () -> new ItemNameBlockItem(ModBlocks.NAKATI_CROP_BLOCK.get(), new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.nakati_ovoid.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> KIRI_FRUIT = ITEMS.register("kiri_fruit",
            () -> new ItemNameBlockItem(ModBlocks.KIRI_CROP_BLOCK.get(), new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.kiri_fruit.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> PIRU_COLONY = ITEMS.register("piru_colony",
            () -> new ItemNameBlockItem(ModBlocks.PIRU_CROP_BLOCK.get(), new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.piru_colony.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final RegistryObject<Item> PIRUZA = ITEMS.register("piruza",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PIRUZA)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.piruza.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final  RegistryObject<Item> GROOU_JUICE = ITEMS.register("groou_juice",
            () -> new SimpleDrinkableItem(new Item.Properties().food(ModFoodProperties.GROOU_JUICE)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.groou_juice.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> KIRI_CURRY = ITEMS.register("kiri_curry",
            () -> new Item(new Item.Properties().food(ModFoodProperties.KIRI_CURRY)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.kiri_curry.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> KIRI_JAM = ITEMS.register("kiri_jam",
            () -> new SimpleDrinkableItem(new Item.Properties().food(ModFoodProperties.KIRI_JAM)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.kiri_jam.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> PIRU_FLOUR = ITEMS.register("piru_flour",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.piru_flour.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> PIRU_NOODLE = ITEMS.register("piru_noodle",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PIRU_NOODLE)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.piru_noodle.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> AEROGEL = ITEMS.register("aerogel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AERO_CRYSTAL = ITEMS.register("aero_crystal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCAITES_CRYSTAL = ITEMS.register("arcaites_crystal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SYNC_CRYSTAL = ITEMS.register("sync_crystal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_DURASTEEL = ITEMS.register("raw_durasteel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_AEGISALT = ITEMS.register("raw_aegisalt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VILOUS_CLAY = ITEMS.register("vilous_clay",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REFINED_AEGISALT = ITEMS.register("refined_aegisalt",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.refined_aegisalt.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> DURASTEEL_INGOT = ITEMS.register("durasteel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AEROMER = ITEMS.register("aeromer",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.aeromer.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> AVALI_BOTTLE = ITEMS.register("avali_bottle",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_bottle.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> FIBER = ITEMS.register("fiber",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.fiber.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> NAKATI_BARK = ITEMS.register("nakati_bark",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.nakati_bark.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> NANITE_INJECTOR = ITEMS.register("nanite_injector",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.nanite_injector.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> PROTOSTEEL_INGOT = ITEMS.register("protosteel_ingot",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.protosteel_ingot.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> WOVEN_FABRIC = ITEMS.register("woven_fabric",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.woven_fabric.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> WOVEN_GRAPHENE = ITEMS.register("woven_graphene",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.woven_graphene.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> TUCKER = ITEMS.register("tucker",
            () -> new Item(new Item.Properties().food(ModFoodProperties.TUCKER)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.tucker.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> KIRI_CIDER = ITEMS.register("kiri_cider",
            () -> new SimpleDrinkableItem(new Item.Properties().food(ModFoodProperties.KIRI_CIDER)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.kiri_cider.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> SPICY_JERKY = ITEMS.register("spicy_jerky",
            () -> new Item(new Item.Properties().food(ModFoodProperties.SPICY_JERKY)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.spicy_jerky.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> AVALI_BBQ = ITEMS.register("avali_bbq",
            () -> new Item(new Item.Properties().food(ModFoodProperties.AVALI_BBQ)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_bbq.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final RegistryObject<Item> FIRELANCE_HILT = ITEMS.register("firelance_hilt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRELANCE_MAIN = ITEMS.register("firelance_main",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRELANCE_MUZZLE = ITEMS.register("firelance_muzzle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRELANCE_SCOPE = ITEMS.register("firelance_scope",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MLSERIES_HILT = ITEMS.register("mlseries_hilt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MLSERIES_MUZZLE = ITEMS.register("mlseries_muzzle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MLSERIES_MAIN = ITEMS.register("mlseries_main",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NOVA_HILT = ITEMS.register("nova_hilt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> QRC_HILT = ITEMS.register("qrc_hilt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NOVA_MAIN = ITEMS.register("nova_main",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> QRC_MAIN = ITEMS.register("qrc_main",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NOVA_SCOPE = ITEMS.register("nova_scope",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NOVA_MUZZLE = ITEMS.register("nova_muzzle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> QRC_MUZZLE = ITEMS.register("qrc_muzzle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AVALI_DRONE_CORE = ITEMS.register("avali_drone_core",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AVALI_DRONE_ROTORS = ITEMS.register("avali_drone_rotors",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AVALI_DATA_CHIT = ITEMS.register("avali_data_chit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AVALI_DRONE = ITEMS.register("avali_drone", AvaliDroneItem::new);

    public static final RegistryObject<Item> STORM_CORE = ITEMS.register("storm_core",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STORM_MUZZLE = ITEMS.register("storm_muzzle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STORM_HILT = ITEMS.register("storm_hilt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLIZZARD_HILT = ITEMS.register("blizzard_hilt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLIZZARD_CORE = ITEMS.register("blizzard_core",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLIZZARD_MUZZLE = ITEMS.register("blizzard_muzzle",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> QRC = ITEMS.register("qrc",
            QrcItem::new);
    public static final RegistryObject<Item> FIRELANCE = ITEMS.register("firelance",
            FirelanceItem::new);
    public static final RegistryObject<Item> MLSERIES = ITEMS.register("mlseries",
            MlseriesItem::new);
    public static final RegistryObject<Item> NOVA = ITEMS.register("nova",
            NovaItem::new);
    public static final RegistryObject<Item> STORM = ITEMS.register("storm",
            StormItem::new);
    public static final RegistryObject<Item> BLIZZARD = ITEMS.register("blizzard",
            BlizzardItem::new);
    public static final RegistryObject<Item> AVALI_PROJECTILE_ITEM = ITEMS.register("avali_projectile_item",
            AvaliProjectileItemItem::new);

    public static final RegistryObject<Item> SERGAL_CHEESE = ITEMS.register("sergal_cheese",
            () -> new Item(new Item.Properties().food(ModFoodProperties.SERGAL_CHEESE)));
    public static final RegistryObject<Item> PROTOGEN_RAM = ITEMS.register("protogen_ram",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PROTOGEN_RAM)));
    public static final RegistryObject<Item> VILOUS_CERAMIC_INGOT = ITEMS.register("vilous_ceramic_ingot",
            () -> new Item(new Item.Properties()));

// Tools

//    public static final DeferredItem<SwordItem> AVALI_SWORD = ITEMS.register("avali_sword",
//            () -> new SwordItem(ModToolTiers.AEROGEL, new Item.Properties()
//                    .attributes(SwordItem.createAttributes(ModToolTiers.AEROGEL, 5, -2.4f))){
//                @Override
//                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_sword.tooltip"));
//                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//                }
//            });

    public static final RegistryObject<Item> AVALI_SWORD = ITEMS.register("avali_sword", AvaliswordItem::new);

    public static final RegistryObject<SwordItem> AVALI_SPEAR = ITEMS.register("avali_spear",
            () -> new ModEffectSwordItem(ModToolTiers.AEROGEL, 8, -2.7f, new Item.Properties(),MobEffects.MOVEMENT_SLOWDOWN){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_spear.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<SwordItem> PROTOGEN_SWORD = ITEMS.register("protogen_sword",
            () -> new SwordItem(ModToolTiers.HARDLIGHT,6, -1.4f, new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.protogen_sword.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });



    public static final RegistryObject<PickaxeItem> AVALI_PICKAXE = ITEMS.register("avali_pickaxe",
            () -> new PickaxeItem(ModToolTiers.AEROGEL, 1, -2.8f, new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_pickaxe.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
//    public static final DeferredItem<Item> AVALI_AXE = ITEMS.register("avali_axe", AvaliAxeItem::new );
    public static final RegistryObject<AxeItem> AVALI_AXE = ITEMS.register("avali_axe",
        () -> new AxeItem(ModToolTiers.AEROGEL, 4, -2.8f, new Item.Properties()){
            @Override
            public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_axe.tooltip"));
                super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            }
        });

    public static final RegistryObject<AxeItem> PROTOGEN_AXE = ITEMS.register("protogen_axe",
            () -> new AxeItem(ModToolTiers.HARDLIGHT, 6, -2.8f, new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.protogen_axe.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });


    public static final RegistryObject<HoeItem> AVALI_HOE = ITEMS.register("avali_hoe",
            () -> new HoeItem(ModToolTiers.AEROGEL, 0, -3.0f, new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.avali_hoe.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final RegistryObject<Item> PIRU_FROND = ITEMS.register("piru_frond",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PIRU_FROND)){
                @Override
                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.piru_frond.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final RegistryObject<Item> SKSCEEGEHKJA_SPAWN_EGG = ITEMS.register("sksceegehkja_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SKSCEEGEHKJA, 0x5d5d5d, 0xc9c9c9,
                    new Item.Properties()));
    public static final RegistryObject<Item> SKACIKKJRRBWCAK_SPAWN_EGG = ITEMS.register("skacikkjrrbwcak_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SKACIKKJRRKBWCAK, 0x623928, 0x91776d,
                    new Item.Properties()));
    public static final RegistryObject<Item> EEPUOR_SPAWN_EGG = ITEMS.register("eepuor_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.EEPUOR, 0x2d2f31, 0x3effff,
                    new Item.Properties()));

    public static final RegistryObject<Item> PROTOGEN_SPAWN_EGG = ITEMS.register("protogen_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.PROTOGEN, 0xdbdbdb, 0x68ffff,
                    new Item.Properties()));
    public static final RegistryObject<Item> PRIMAGEN_SPAWN_EGG = ITEMS.register("primagen_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.PRIMAGEN, 0xeaeaea, 0x68ff55,
                    new Item.Properties()));
    public static final RegistryObject<Item> AVALI_SPAWN_EGG = ITEMS.register("avali_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.AVALI, 0xff8432, 0xfff7d6,
                    new Item.Properties()));
    public static final RegistryObject<Item> SERGAL_SPAWN_EGG = ITEMS.register("sergal_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SERGAL, 0xf6d6c9, 0x84121e,
                    new Item.Properties()));
    public static final RegistryObject<Item> CHRGAKBZ_SPAWN_EGG = ITEMS.register("chrgakbz_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CHRGAKBZ, 0x3a757d, 0x3d1c5e,
                    new Item.Properties()));
    public static final RegistryObject<Item> CAKLERAH_SPAWN_EGG = ITEMS.register("caklerah_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CAKLERAH, 0xeae6da, 0xf19751,
                    new Item.Properties()));
    public static final RegistryObject<Item> MAMAGEN_SPAWN_EGG = ITEMS.register("mamagen_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MAMAGEN, 0x1b1c2b, 0x9fa1b7,
                    new Item.Properties()));
    public static final RegistryObject<Item> TALXLEECH_SPAWN_EGG = ITEMS.register("talxleech_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TALXLEECH, 0x783c1d, 0x939393,
                    new Item.Properties()));
    public static final RegistryObject<Item> TALXWEASEL_SPAWN_EGG = ITEMS.register("talxweasel_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TALXWEASEL, 0x0d0d0c, 0x484b47,
                    new Item.Properties()));
    public static final RegistryObject<Item> STALKER_SPAWN_EGG = ITEMS.register("stalker_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.STALKER, 0x9d9d9d, 0x9f3d00,
                    new Item.Properties()));
    public static final RegistryObject<Item> GOHUNTAKI_SPAWN_EGG = ITEMS.register("gohuntaki_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GOHUNTAKI, 0x564d4c, 0x8d381e,
                    new Item.Properties()));
    public static final RegistryObject<Item> TALXDOG_SPAWN_EGG = ITEMS.register("talxdog_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TALXDOG, 0x7e7e6f, 0x261e20,
                    new Item.Properties()));
    public static final RegistryObject<Item> TALXWOLF_SPAWN_EGG = ITEMS.register("talxwolf_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TALXWOLF, 0xa7a7a7, 0x303736,
                    new Item.Properties()));

    public static final RegistryObject<Item> MIZOLE_SPAWN_EGG = ITEMS.register("mizole_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MIZOLE, 0x8f9596, 0x4f879a,
                    new Item.Properties()));
    public static final RegistryObject<Item> SPORE_SPAWN_EGG = ITEMS.register("spore_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SPORE, 0xcacaca, 0x757d5b,
                    new Item.Properties()));
    public static final RegistryObject<Item> FEMALE_NEVREAN_SPAWN_EGG = ITEMS.register("female_nevrean_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.FEMALE_NEVREAN, 0x554d40, 0xcfac69,
                    new Item.Properties()));
    public static final RegistryObject<Item> MALE_NEVREAN_SPAWN_EGG = ITEMS.register("male_nevrean_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MALE_NEVREAN, 0xfbdd67, 0xccee0e,
                    new Item.Properties()));

    public static final RegistryObject<SwordItem> SERGAL_GREATSWORD = ITEMS.register("sergal_greatsword",
            () -> new SwordItem(ModToolTiers.CERAMIC, 8, -2.4f, new Item.Properties()));
    public static final RegistryObject<SwordItem> SERGAL_LANCE = ITEMS.register("sergal_lance",
            () -> new SwordItem(ModToolTiers.CERAMIC, 6, -1.5f, new Item.Properties()));
    public static final RegistryObject<SwordItem> SERGAL_MACE = ITEMS.register("sergal_mace",
            () -> new SwordItem(ModToolTiers.CERAMIC, 9, -3.2f, new Item.Properties()));
//    public static final DeferredItem<Item> SERGAL_SLINGSHOT = ITEMS.register("sergal_slingshot",
//            () -> new BowItem(new Item.Properties().durability(996)){
//                @Override
//                public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//                    tooltipComponents.add(Component.translatable("tooltip.avaliproject.sergal_slingshot.tooltip"));
//                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//                }
//            });

    public static final RegistryObject<SwordItem> SERGAL_SWORD = ITEMS.register("sergal_sword",
            () -> new SwordItem(ModToolTiers.CERAMIC, 7, -2.2f, new Item.Properties()));
    public static final RegistryObject<Item> MERP_MUSIC_DISC = ITEMS.register("merp_music_disc",
            () -> new RecordItem(8, ModSounds.MERP, new Item.Properties().stacksTo(1), 120));
    public static final RegistryObject<Item> AVALI_DANCE_MUSIC_DISC = ITEMS.register("avali_dance_music_disc",
            () -> new RecordItem(8, ModSounds.AVALI_DANCE, new Item.Properties().stacksTo(1), 65));
    public static final RegistryObject<Item> CYBERNETIC_HEART_MUSIC_DISC = ITEMS.register("cybernetic_heart_music_disc",
            () -> new RecordItem(8, ModSounds.CYBERNETIC_HEART, new Item.Properties().stacksTo(1), 187));


 //   public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(AvaliProject.MOD_ID);
 //   public static final DeferredItem<Item> AVALI_SWORD = REGISTRY.register("avali_sword", AvaliswordItem::new);


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
