package com.lunkoashtail.avaliproject.compat.curios;

import com.google.common.collect.Multimap;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.item.custom.TrinketEffects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public final class CuriosCompat {
    private CuriosCompat() {
    }

    public static void init() {
        TrinketCurio trinket = new TrinketCurio();
        CuriosApi.registerCurio(ModItems.PENDANT.get(), trinket);
        CuriosApi.registerCurio(ModItems.MANOKIT_TRINKET.get(), trinket);
        CuriosApi.registerCurio(ModItems.CRYSTALITHIUM_TRINKET.get(), trinket);
    }

    private static final class TrinketCurio implements ICurioItem {
        @Override
        public void curioTick(SlotContext slotContext, ItemStack stack) {
            TrinketEffects.tick(slotContext.entity(), stack);
        }

        @Override
        public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
            return TrinketEffects.modifiers(id, stack);
        }
    }
}
