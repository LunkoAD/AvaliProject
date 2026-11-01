package com.lunkoashtail.avaliproject.vitals;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public final class VitalsDamage {

    public static final ResourceKey<DamageType> BLED_OUT = key("bled_out");
    public static final ResourceKey<DamageType> OVERDOSE = key("overdose");
    public static final ResourceKey<DamageType> CARDIAC_ARREST = key("cardiac_arrest");
    public static final ResourceKey<DamageType> BRAIN_DEATH = key("brain_death");
    public static final ResourceKey<DamageType> RADIATION = key("radiation");
    public static final ResourceKey<DamageType> DEHYDRATION = key("dehydration");

    private static final java.util.List<ResourceKey<DamageType>> CAUSES =
            java.util.List.of(BLED_OUT, OVERDOSE, CARDIAC_ARREST, BRAIN_DEATH, RADIATION, DEHYDRATION);

    private VitalsDamage() {}

    public static int indexOf(ResourceKey<DamageType> cause) {
        return Math.max(0, CAUSES.indexOf(cause));
    }

    public static ResourceKey<DamageType> byIndex(float index) {
        int i = Math.round(index);
        return i >= 0 && i < CAUSES.size() ? CAUSES.get(i) : BRAIN_DEATH;
    }

    private static ResourceKey<DamageType> key(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name));
    }

    public static ResourceKey<DamageType> causeOf(Vitals vitals) {
        if (vitals.get(Vitals.Stat.OPIOID) >= 100f) return OVERDOSE;
        if (vitals.get(Vitals.Stat.BLOOD) < 3000f) return BLED_OUT;
        if (vitals.get(Vitals.Stat.RADIATION) >= 15f) return RADIATION;
        if (vitals.get(Vitals.Stat.THIRST) <= 0f) return DEHYDRATION;
        if (vitals.isCardiacArrest()) return CARDIAC_ARREST;
        return BRAIN_DEATH;
    }

    public static void kill(ServerPlayer player, ResourceKey<DamageType> type) {
        DamageSource source = new DamageSource(player.level().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type));
        player.hurt(source, Float.MAX_VALUE);
    }
}
