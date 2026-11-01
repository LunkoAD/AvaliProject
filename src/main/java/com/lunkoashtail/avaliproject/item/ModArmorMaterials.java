package com.lunkoashtail.avaliproject.item;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, AvaliProject.MOD_ID);

    public static final int TITANIUM_DURABILITY_MULTIPLIER = 25;

    public static final Holder<ArmorMaterial> TITANIUM = ARMOR_MATERIALS.register("titanium", () -> new ArmorMaterial(
            Util.defense(2, 5, 7, 2, 5),
            16,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(ModItems.TITANIUM_INGOT.get()),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "titanium"))),
            1.0f,
            0.0f));

    private static final class Util {
        private static EnumMap<ArmorItem.Type, Integer> defense(int boots, int leggings, int chestplate, int helmet, int body) {
            EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
            map.put(ArmorItem.Type.BOOTS, boots);
            map.put(ArmorItem.Type.LEGGINGS, leggings);
            map.put(ArmorItem.Type.CHESTPLATE, chestplate);
            map.put(ArmorItem.Type.HELMET, helmet);
            map.put(ArmorItem.Type.BODY, body);
            return map;
        }
    }

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}
