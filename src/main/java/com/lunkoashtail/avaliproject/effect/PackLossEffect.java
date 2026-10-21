package com.lunkoashtail.avaliproject.effect;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.EffectCure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PackLossEffect extends MobEffect {
    public static final int MAX_AMPLIFIER = 4;
    public static final int MIN_DURATION_TICKS = 20 * 60 * 10;
    public static final int HUNGER_LEVEL = 1;
    public static final int SLOWNESS_LEVEL = 2;
    public static final int FATIGUE_LEVEL = 3;
    public static final int DARKNESS_LEVEL = 4;
    private static final int TICK_INTERVAL = 20;
    private static final float HUNGER_EXHAUSTION_PER_TICK = 0.005F;
    private static final int WITHER_INTERVAL_TICKS = 20 * 30;
    private static final int WITHER_DAMAGE_SPACING_TICKS = 40;
    private static final float WITHER_DAMAGE = 1.0F;

    public PackLossEffect() {
        super(MobEffectCategory.HARMFUL, 0x4B3F6B);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, id("weakness"), AttributeModifier.Operation.ADD_VALUE,
                amplifier -> -4.0);
        addAttributeModifier(Attributes.LUCK, id("bad_luck"), AttributeModifier.Operation.ADD_VALUE,
                amplifier -> -1.0);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, id("slowness"), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                amplifier -> amplifier >= SLOWNESS_LEVEL ? -0.15 : 0.0);
        addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, id("mining_fatigue"), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                amplifier -> amplifier >= FATIGUE_LEVEL ? -0.7 : 0.0);
        addAttributeModifier(Attributes.ATTACK_SPEED, id("mining_fatigue_attack"), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                amplifier -> amplifier >= FATIGUE_LEVEL ? -0.1 : 0.0);
    }

    private static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "pack_loss_" + name);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % TICK_INTERVAL == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide()) return true;
        if (amplifier >= HUNGER_LEVEL && entity instanceof Player player) {
            player.causeFoodExhaustion(HUNGER_EXHAUSTION_PER_TICK * TICK_INTERVAL);
        }
        if (amplifier >= DARKNESS_LEVEL) {
            MobEffectInstance packLoss = entity.getEffect(ModEffects.PACK_LOSS);
            int phase = packLoss == null ? -1 : packLoss.getDuration() % WITHER_INTERVAL_TICKS;
            if (phase == 0 || phase == WITHER_INTERVAL_TICKS - WITHER_DAMAGE_SPACING_TICKS) {
                entity.hurt(entity.damageSources().wither(), WITHER_DAMAGE);
            }
        }
        return true;
    }

    public static List<Component> describe(int amplifier) {
        List<Component> lines = new ArrayList<>();
        lines.add(line("weakness"));
        lines.add(line("bad_luck"));
        if (amplifier >= HUNGER_LEVEL) lines.add(line("hunger"));
        if (amplifier >= SLOWNESS_LEVEL) lines.add(line("slowness"));
        if (amplifier >= FATIGUE_LEVEL) lines.add(line("mining_fatigue"));
        if (amplifier >= DARKNESS_LEVEL) {
            lines.add(line("darkness"));
            lines.add(line("wither"));
        }
        lines.add(Component.translatable("effect.avaliproject.pack_loss.incurable").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        return lines;
    }

    private static Component line(String key) {
        return Component.translatable("effect.avaliproject.pack_loss." + key).withStyle(ChatFormatting.RED);
    }

    public static void intensify(LivingEntity entity) {
        MobEffectInstance current = entity.getEffect(ModEffects.PACK_LOSS);
        int amplifier = current == null ? 0 : Math.min(MAX_AMPLIFIER, current.getAmplifier() + 1);
        int duration = current == null ? MIN_DURATION_TICKS : Math.max(MIN_DURATION_TICKS, current.getDuration());
        restore(entity, amplifier, duration);
    }

    public static void restore(LivingEntity entity, int amplifier, int duration) {
        entity.removeEffect(ModEffects.PACK_LOSS);
        entity.addEffect(new MobEffectInstance(ModEffects.PACK_LOSS, duration, amplifier, false, true, true));
    }
}
