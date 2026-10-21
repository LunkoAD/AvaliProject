package com.lunkoashtail.avaliproject.item.custom;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;

public final class TrinketEffects {
    private static final int REFRESH_INTERVAL = 20;
    private static final int NIGHT_VISION_DURATION = 300;
    private static final int SHORT_DURATION = 60;
    private static final double MINING_BONUS = 0.1;
    private static final double SWIM_BONUS = 0.25;
    private static final double LUCK_BONUS = 1.0;

    private TrinketEffects() {
    }

    public static void tick(LivingEntity wearer, ItemStack stack) {
        Level level = wearer.level();
        if (level.isClientSide() || wearer.tickCount % REFRESH_INTERVAL != 0) return;

        if (stack.is(ModItems.PENDANT.get())) {
            if (wearer.isEyeInFluidType(NeoForgeMod.WATER_TYPE.value())) refresh(wearer, MobEffects.WATER_BREATHING, SHORT_DURATION);
            return;
        }
        if (stack.is(ModItems.MANOKIT_TRINKET.get())) {
            switch (TrinketItem.getGem(stack)) {
                case "moonstone" -> {
                    if (level.isNight()) refresh(wearer, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION);
                }
                case "sunstone" -> {
                    if (level.isDay() && level.canSeeSky(wearer.blockPosition())) refresh(wearer, MobEffects.MOVEMENT_SPEED, SHORT_DURATION);
                }
                default -> {
                }
            }
        }
    }

    public static Multimap<Holder<Attribute>, AttributeModifier> modifiers(ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        if (stack.is(ModItems.CRYSTALITHIUM_TRINKET.get())) {
            modifiers.put(Attributes.BLOCK_BREAK_SPEED,
                    new AttributeModifier(id, MINING_BONUS, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        } else if (stack.is(ModItems.MANOKIT_TRINKET.get())) {
            switch (TrinketItem.getGem(stack)) {
                case "larimar", "sea_opal" -> modifiers.put(NeoForgeMod.SWIM_SPEED,
                        new AttributeModifier(id, SWIM_BONUS, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                case "jadeite", "carnelian", "ammolite", "abalone_shard" -> modifiers.put(Attributes.LUCK,
                        new AttributeModifier(id, LUCK_BONUS, AttributeModifier.Operation.ADD_VALUE));
                default -> {
                }
            }
        }
        return modifiers;
    }

    private static void refresh(LivingEntity wearer, Holder<MobEffect> effect, int duration) {
        wearer.addEffect(new MobEffectInstance(effect, duration, 0, true, false, true));
    }
}
