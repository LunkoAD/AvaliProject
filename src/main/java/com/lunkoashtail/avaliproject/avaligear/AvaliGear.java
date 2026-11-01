package com.lunkoashtail.avaliproject.avaligear;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.avaligear.armor.AvaliArmorItem;
import com.lunkoashtail.avaliproject.avaligear.armor.AvaliArmorTable;
import com.lunkoashtail.avaliproject.avaligear.armor.VisorTogglePayload;
import com.lunkoashtail.avaliproject.avaligear.block.AvaliEggBlock;
import com.lunkoashtail.avaliproject.avaligear.ship.CatalinaConsoleBlock;
import com.lunkoashtail.avaliproject.avaligear.ship.CatalinaEntity;
import com.lunkoashtail.avaliproject.avaligear.ship.CatalinaHullBlock;
import com.lunkoashtail.avaliproject.avaligear.ship.CatalinaItem;
import com.lunkoashtail.avaliproject.avaligear.ship.ShipControlPayload;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class AvaliGear {
    private AvaliGear() {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, path);
    }

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GARMENT = material("avali_garment");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> UNIFORM = material("avali_uniform");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PLATE = material("avali_plate");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SPECIALTY = material("avali_specialty");

    public static DataComponentType<Boolean> VISOR_OPEN;
    public static Block EGG_FERTILIZED;
    public static Block EGG_INERT;
    public static Block CATALINA_HULL;
    public static Block CATALINA_CONSOLE;
    public static EntityType<CatalinaEntity> CATALINA;
    public static Item CATALINA_ITEM;
    public static final List<Item> GEAR_ITEMS = new ArrayList<>();

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> material(String name) {
        return DeferredHolder.create(Registries.ARMOR_MATERIAL, id(name));
    }

    public static Holder<ArmorMaterial> materialByName(String name) {
        return switch (name) {
            case "PLATE" -> PLATE;
            case "SPECIALTY" -> SPECIALTY;
            case "UNIFORM" -> UNIFORM;
            default -> GARMENT;
        };
    }

    private static ArmorMaterial makeMaterial(int boots, int legs, int chest, int helmet, int enchant,
                                              Holder<SoundEvent> sound, float toughness, Supplier<Ingredient> repair) {
        Map<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        defense.put(ArmorItem.Type.BOOTS, boots);
        defense.put(ArmorItem.Type.LEGGINGS, legs);
        defense.put(ArmorItem.Type.CHESTPLATE, chest);
        defense.put(ArmorItem.Type.HELMET, helmet);
        defense.put(ArmorItem.Type.BODY, chest);

        return new ArmorMaterial(defense, enchant, sound, repair,
                List.of(new ArmorMaterial.Layer(id("avali_garment"))), toughness, 0.0F);
    }

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(Registries.ARMOR_MATERIAL, helper -> {
            helper.register(id("avali_garment"), makeMaterial(1, 2, 3, 1, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F,
                    () -> Ingredient.of(ItemTags.WOOL)));
            helper.register(id("avali_uniform"), makeMaterial(1, 4, 5, 2, 12, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F,
                    () -> Ingredient.of(ItemTags.WOOL)));
            helper.register(id("avali_plate"), makeMaterial(2, 5, 6, 2, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.5F,
                    () -> Ingredient.of(Items.IRON_INGOT)));
            helper.register(id("avali_specialty"), makeMaterial(3, 6, 8, 3, 10, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.0F,
                    () -> Ingredient.of(Items.DIAMOND)));
        });

        event.register(Registries.DATA_COMPONENT_TYPE, helper -> {
            VISOR_OPEN = DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build();
            helper.register(id("visor_open"), VISOR_OPEN);
        });

        event.register(Registries.BLOCK, helper -> {
            EGG_FERTILIZED = new AvaliEggBlock(eggProps(), true);
            EGG_INERT = new AvaliEggBlock(eggProps(), false);
            helper.register(id("avali_egg_fertilized"), EGG_FERTILIZED);
            helper.register(id("avali_egg_inert"), EGG_INERT);
            CATALINA_HULL = new CatalinaHullBlock(hullProps());
            CATALINA_CONSOLE = new CatalinaConsoleBlock(hullProps());
            helper.register(id("catalina_hull"), CATALINA_HULL);
            helper.register(id("catalina_console"), CATALINA_CONSOLE);
        });

        event.register(Registries.ENTITY_TYPE, helper -> {
            CATALINA = EntityType.Builder.<CatalinaEntity>of(CatalinaEntity::new, MobCategory.MISC)
                    .sized(6.0F, 4.0F)
                    .clientTrackingRange(16)
                    .updateInterval(1)
                    .fireImmune()
                    .build("catalina");
            helper.register(id("catalina"), CATALINA);
        });

        event.register(Registries.ITEM, helper -> {
            for (AvaliArmorTable.Row row : AvaliArmorTable.ROWS) {
                Item item = new AvaliArmorItem(materialByName(row.material()), row.type(),
                        new Item.Properties().durability(row.type().getDurability(durabilityFactor(row.material()))),
                        row.model(), row.texture(), row.visor(), row.hide());
                helper.register(id(row.id()), item);
                GEAR_ITEMS.add(item);
            }
            Item fert = new BlockItem(EGG_FERTILIZED, new Item.Properties().stacksTo(16));
            Item inert = new BlockItem(EGG_INERT, new Item.Properties().stacksTo(16));
            helper.register(id("avali_egg_fertilized"), fert);
            helper.register(id("avali_egg_inert"), inert);
            GEAR_ITEMS.add(fert);
            GEAR_ITEMS.add(inert);
            CATALINA_ITEM = new CatalinaItem(new Item.Properties().stacksTo(1).fireResistant());
            helper.register(id("catalina"), CATALINA_ITEM);
            GEAR_ITEMS.add(CATALINA_ITEM);
        });

        event.register(Registries.CREATIVE_MODE_TAB, helper -> helper.register(id("avali_gear"),
                CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.avaliproject.avali_gear"))
                        .icon(() -> new ItemStack(CATALINA_ITEM))
                        .displayItems((params, output) -> GEAR_ITEMS.forEach(output::accept))
                        .build()));
    }

    private static int durabilityFactor(String material) {
        return switch (material) {
            case "SPECIALTY" -> 33;
            case "PLATE" -> 20;
            case "UNIFORM" -> 15;
            default -> 7;
        };
    }

    private static BlockBehaviour.Properties eggProps() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.5F)
                .sound(SoundType.DECORATED_POT).noOcclusion().pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties hullProps() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.NONE).strength(-1.0F, 3600000.0F)
                .noLootTable().noOcclusion().pushReaction(PushReaction.BLOCK)
                .isValidSpawn((state, level, pos, type) -> false)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .isRedstoneConductor((state, level, pos) -> false)
                .randomTicks();
    }

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(VisorTogglePayload.TYPE, VisorTogglePayload.STREAM_CODEC, VisorTogglePayload::handle);
        registrar.playToServer(ShipControlPayload.TYPE, ShipControlPayload.STREAM_CODEC, ShipControlPayload::handle);
    }
}
