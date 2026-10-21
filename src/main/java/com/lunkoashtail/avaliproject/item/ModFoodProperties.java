package com.lunkoashtail.avaliproject.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties KIRIKIRI_PIE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 400), 0.35f).build();
    public static final FoodProperties AVALON_TACO = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties AVALI_MUFFIN = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties GROOU = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties GROOU_JUICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400), 0.35f).build();
    public static final FoodProperties KIRI_CURRY = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties KIRI_JAM = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties PIRU_NOODLE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties PIRUZA = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties AVALI_BBQ = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties KIRI_CIDER = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties TUCKER = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties SPICY_JERKY = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties PIRU_FROND = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties SERGAL_CHEESE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties PROTOGEN_RAM = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties NUTRIENT_PASTE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.6f).build();
    public static final FoodProperties GEOFRUIT = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties COCONUT = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 2), 1.0f).build();
    public static final FoodProperties PAPAYA = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 1.0f).build();
    public static final FoodProperties PASSION_FRUIT = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 1.0f).build();
    public static final FoodProperties DRAGON_FRUIT = new FoodProperties.Builder().nutrition(3).saturationModifier(0.35f)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200), 1.0f).build();
}
