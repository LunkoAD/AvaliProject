package com.lunkoashtail.avaliproject.vitals;

import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbConditions;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.limb.PainData;
import com.lunkoashtail.avaliproject.network.LimbDataSyncPayload;
import com.lunkoashtail.avaliproject.vitals.Vitals.Stat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.network.PacketDistributor;

public final class VitalsLogic {

    public static final float NORMAL_BLOOD = 5000f;
    public static final float LITERS_PER_MIN_PER_BLEED = 0.018f;
    public static final float INCAPACITATED = 30f;

    private static final int EFFECT_TICKS = 30;

    private VitalsLogic() {}

    public static float effectivePain(ServerPlayer player, Vitals vitals) {
        return Math.max(0f, player.getData(ModAttachments.PAIN_DATA).get() - vitals.get(Stat.ANALGESIA));
    }

    public static float limbBleedRate(LimbData data, LimbHealth limbs, Vitals vitals, Limb limb) {
        if (limbs.hasTourniquet(limb)) return 0f;
        float viscosityFactor = Mth.clamp(1f - vitals.get(Stat.VISCOSITY) / 100f, 0.25f, 3f);
        return data.getBleed(limb) * LITERS_PER_MIN_PER_BLEED * viscosityFactor;
    }

    public static void tick(ServerPlayer player) {
        Vitals v = player.getData(ModAttachments.VITALS);
        LimbHealth limbs = player.getData(ModAttachments.LIMB_HEALTH);
        LimbData bleed = player.getData(ModAttachments.LIMB_DATA);
        LimbConditions conditions = player.getData(ModAttachments.LIMB_CONDITIONS);
        PainData painData = player.getData(ModAttachments.PAIN_DATA);
        RandomSource random = player.getRandom();

        if (v.get(Stat.DYING_TIME) > 0f) {
            tickDying(player, v);
            return;
        }

        tickDrugs(v);
        boolean bleedChanged = tickLimbs(player, v, limbs, bleed, conditions, random);
        tickBlood(player, v, limbs, bleed);
        tickNeeds(player, v);
        tickRadiation(player, v, limbs, random);
        tickCirculation(player, v, limbs, effectivePain(player, v), random);
        tickConsciousness(player, v, effectivePain(player, v));
        tickBrain(v, random);
        applyEffects(player, v, limbs, random);

        if (limbs.maxInfection() > 25f) {
            painData.add(0.1f);
        }
        if (bleedChanged) {
            PacketDistributor.sendToPlayer(player, LimbDataSyncPayload.from(bleed));
        }
        if (v.get(Stat.BRAIN) <= 0f) {
            startDying(v);
        }
    }

    public static final float FLATLINE_SECONDS = 10f;

    private static void startDying(Vitals v) {
        v.set(Stat.DEATH_CAUSE, VitalsDamage.indexOf(VitalsDamage.causeOf(v)));
        v.set(Stat.DYING_TIME, FLATLINE_SECONDS);
        holdFlatline(v);
    }

    private static void holdFlatline(Vitals v) {
        v.set(Stat.BRAIN, 0f);
        v.set(Stat.FIBRILLATION, Stat.FIBRILLATION.max);
        v.set(Stat.HEART_RATE, 0f);
        v.set(Stat.SYSTOLIC, 0f);
        v.set(Stat.DIASTOLIC, 0f);
        v.set(Stat.RESP_RATE, 0f);
        v.set(Stat.CONSCIOUSNESS, 0f);
        v.add(Stat.OXYGEN, -3.5f);
    }

    private static void tickDying(ServerPlayer player, Vitals v) {
        holdFlatline(v);
        v.add(Stat.DYING_TIME, -1f);
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, EFFECT_TICKS, 6, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, EFFECT_TICKS, 4, false, false));
        if (v.get(Stat.DYING_TIME) <= 0f) {
            VitalsDamage.kill(player, VitalsDamage.byIndex(v.get(Stat.DEATH_CAUSE)));
        }
    }

    public static void resuscitate(Vitals v) {
        v.set(Stat.DYING_TIME, 0f);
        v.set(Stat.FIBRILLATION, 0f);
        v.set(Stat.HEART_RATE, 50f);
        v.set(Stat.BRAIN, Math.max(v.get(Stat.BRAIN), 20f));
        v.set(Stat.OXYGEN, Math.max(v.get(Stat.OXYGEN), 60f));
    }

    private static void tickDrugs(Vitals v) {
        v.tickDown(Stat.OPIOID, 0.15f);
        v.tickDown(Stat.ANALGESIA, 0.2f);
        v.tickDown(Stat.STIMULANT, 0.4f);
        v.tickDown(Stat.VASODILATOR, 0.5f);
        float viscosity = v.get(Stat.VISCOSITY);
        v.set(Stat.VISCOSITY, viscosity > 0 ? Math.max(0, viscosity - 0.05f) : Math.min(0, viscosity + 0.05f));
        v.tickDown(Stat.ANTIRAD_TIME, 1f);
        v.tickDown(Stat.ANTIBIOTIC_TIME, 1f);
        v.tickDown(Stat.ANTIDEPRESSANT_TIME, 1f);
        v.tickDown(Stat.ANTIDEPRESSANT_COOLDOWN, 1f);
        v.tickDown(Stat.MINDWIPE_TIME, 1f);
        v.tickDown(Stat.OXYLINE_TIME, 1f);
    }

    private static boolean tickLimbs(ServerPlayer player, Vitals v, LimbHealth limbs, LimbData bleed,
                                     LimbConditions conditions, RandomSource random) {
        boolean bleedChanged = false;
        boolean antibiotics = v.get(Stat.ANTIBIOTIC_TIME) > 0;
        for (Limb limb : Limb.values()) {
            int amount = bleed.getBleed(limb);
            float clot = 0.03f * (1f + v.get(Stat.VISCOSITY) / 100f);
            if (limbs.hasTourniquet(limb)) clot *= 3f;
            if (amount > 0 && amount <= 30 && random.nextFloat() < clot) {
                bleed.reduceBleed(limb, 1);
                bleedChanged = true;
            }

            if (limbs.hasTourniquet(limb)) {
                limbs.addMuscle(limb, -0.12f);
            } else if (!conditions.hasShrapnel(limb) && limbs.getInfection(limb) < 25f) {
                limbs.addMuscle(limb, limbs.getMuscle(limb) < 10f ? 0.02f : 0.1f);
            }
            if (amount == 0) {
                limbs.addSkin(limb, limbs.getSkin(limb) < 10f ? 0.014f : 0.07f);
            }

            float clean = limbs.getClean(limb);
            if (clean > 0) limbs.setClean(limb, clean - 1f);

            float infection = limbs.getInfection(limb);
            if (infection > 0) {
                limbs.addInfection(limb, antibiotics ? -0.4f : 0.03f);
            } else if (amount > 0 && limbs.getSkin(limb) < 80f && clean <= 0
                    && random.nextFloat() < 0.002f * (1f + (80f - limbs.getSkin(limb)) / 40f)) {
                limbs.addInfection(limb, 5f);
            }
        }
        return bleedChanged;
    }

    private static void tickBlood(ServerPlayer player, Vitals v, LimbHealth limbs, LimbData bleed) {
        float litersPerMin = 0f;
        for (Limb limb : Limb.values()) litersPerMin += limbBleedRate(bleed, limbs, v, limb);
        if (limbs.getMuscle(Limb.BACK) < 10f) litersPerMin += 0.05f;
        float radiation = v.get(Stat.RADIATION);
        if (radiation > 3f) litersPerMin += 0.01f * (radiation - 3f);
        v.set(Stat.BLEED_RATE, litersPerMin);

        float mlPerSecond = litersPerMin * 1000f / 60f;
        v.add(Stat.BLOOD, -mlPerSecond);

        float blood = v.get(Stat.BLOOD);
        if (litersPerMin < 0.01f && player.getFoodData().getFoodLevel() >= 6 && v.get(Stat.THIRST) > 20f && blood < NORMAL_BLOOD) {
            v.set(Stat.BLOOD, Math.min(NORMAL_BLOOD, blood + 1.33f));
        } else if (blood > NORMAL_BLOOD) {
            v.add(Stat.BLOOD, -1.33f);
        }

        float hemothorax = v.get(Stat.HEMOTHORAX);
        if (hemothorax > 0) v.add(Stat.HEMOTHORAX, bleed.getBleed(Limb.BACK) > 40 ? 0.04f : -0.036f);
    }

    private static void tickNeeds(ServerPlayer player, Vitals v) {
        if (!player.isCreative()) {
            v.add(Stat.THIRST, player.isSprinting() ? -0.06f : -0.028f);
        }
        if (v.get(Stat.THIRST) <= 0f) {
            v.add(Stat.BLOOD, -0.4f);
            v.add(Stat.VISCOSITY, 0.1f);
        } else if (v.get(Stat.THIRST) < 20f) {
            v.add(Stat.VISCOSITY, 0.06f);
        }

        if (v.get(Stat.STIMULANT) > 0f) v.add(Stat.ENERGY, -0.02f);
        if (player.isSleeping()) {
            v.add(Stat.ENERGY, 0.5f);
        } else {
            v.add(Stat.ENERGY, -0.009f);
        }
    }

    private static void tickRadiation(ServerPlayer player, Vitals v, LimbHealth limbs, RandomSource random) {
        float current = v.get(Stat.RADIATION);
        float exposure = Radiation.exposurePerSecond(player.level(), player.blockPosition(), current);
        v.set(Stat.RADIATION_EXPOSURE, exposure);
        v.add(Stat.RADIATION, exposure - Radiation.NATURAL_DECAY_PER_SECOND);
        if (v.get(Stat.ANTIRAD_TIME) > 0) v.add(Stat.RADIATION, -0.0675f);

        float rad = v.get(Stat.RADIATION);
        if (rad > 9f && random.nextFloat() < 0.003f) {
            Limb[] all = Limb.values();
            limbs.addInfection(all[random.nextInt(all.length)], 10f);
        }
        if (rad > 9f && random.nextFloat() < 0.01f) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 0, false, false));
        }
        if (rad > 15f && random.nextFloat() < 0.004f) {
            v.add(Stat.BRAIN, -10f);
            v.add(Stat.VISCOSITY, 15f);
        }
    }

    private static void tickCirculation(ServerPlayer player, Vitals v, LimbHealth limbs, float pain, RandomSource random) {
        float bloodDeficit = Math.max(0f, NORMAL_BLOOD - v.get(Stat.BLOOD));
        float opioid = v.get(Stat.OPIOID);
        float stimulant = v.get(Stat.STIMULANT);
        float sepsis = Math.max(0f, limbs.maxInfection() - 60f);

        float drift = v.get(Stat.HR_DRIFT);
        drift += (random.nextFloat() - 0.5f) * 1.2f - drift * 0.15f;
        v.set(Stat.HR_DRIFT, drift);
        float fibrillation = v.get(Stat.FIBRILLATION);

        float oxygenNow = v.get(Stat.OXYGEN);
        float hypoxia = 0f;
        if (oxygenNow < 55f) hypoxia = 45f - (55f - oxygenNow) * 3f;
        else if (oxygenNow < 92f) hypoxia = Math.min(45f, (92f - oxygenNow) * 1.5f);
        float targetHr = 70f + bloodDeficit / 20f + pain * 0.4f + stimulant * 0.9f - opioid * 0.35f + sepsis * 0.5f
                + hypoxia + drift;
        targetHr = Mth.clamp(targetHr, 30f, 220f);
        float hr = v.get(Stat.HEART_RATE);
        if (v.isCardiacArrest()) {
            v.set(Stat.HEART_RATE, Math.max(0f, hr - 30f));
        } else if (fibrillation > 30f) {
            float erratic = (random.nextFloat() - 0.5f) * fibrillation * 0.8f;
            v.set(Stat.HEART_RATE, Mth.clamp(targetHr + fibrillation * 0.8f + erratic, 20f, 240f));
        } else {
            v.set(Stat.HEART_RATE, hr + Mth.clamp(targetHr - hr, -4f, 4f));
        }

        float systolic = 120f - bloodDeficit / 25f * 0.66f
                + (v.get(Stat.HEART_RATE) - 70f) * 0.25f
                - opioid * 0.25f
                - v.get(Stat.VASODILATOR) * 0.4f
                + v.get(Stat.VISCOSITY) * 0.3f
                + stimulant * 0.3f
                - sepsis * 0.8f
                - fibrillation * 0.35f
                + drift * 0.6f;
        if (v.isCardiacArrest()) systolic = 0f;
        v.set(Stat.SYSTOLIC, systolic);
        v.set(Stat.DIASTOLIC, systolic * 0.66f);

        float fibGrowth = 0f;
        if (systolic < 78f) fibGrowth += (78f - systolic) * 0.05f;
        if (stimulant > 70f) fibGrowth += (stimulant - 70f) * 0.06f;
        float currentHr = v.get(Stat.HEART_RATE);
        if (currentHr > 180f) fibGrowth += (currentHr - 180f) * 0.04f;
        if (v.get(Stat.VISCOSITY) > 90f) fibGrowth += 0.5f;
        if (fibGrowth > 0) v.add(Stat.FIBRILLATION, fibGrowth);
        else if (fibrillation < 40f) v.add(Stat.FIBRILLATION, -0.5f);

        float resp = 16f - opioid * 0.13f - v.get(Stat.HEMOTHORAX) * 0.1f + Math.max(0f, v.get(Stat.HEART_RATE) - 100f) * 0.05f;
        if (opioid >= 110f || limbs.getMuscle(Limb.BACK) < 4f || v.isCardiacArrest()) resp = 0f;
        v.set(Stat.RESP_RATE, resp);

        float maxOxygen = 100f - Math.max(0f, 4200f - v.get(Stat.BLOOD)) / 25f - v.get(Stat.HEMOTHORAX) * 0.3f;
        if (resp > 0 && resp < 10f) maxOxygen = Math.min(maxOxygen, 100f - (10f - resp) * 4f);
        boolean suffocating = resp <= 0f || v.isCardiacArrest() || player.getAirSupply() <= 0;
        float oxygen = v.get(Stat.OXYGEN);
        if (suffocating) {
            v.add(Stat.OXYGEN, -3.5f);
        } else {
            v.set(Stat.OXYGEN, oxygen + Mth.clamp(maxOxygen - oxygen, -2f, 2f));
        }
        if (v.get(Stat.OXYLINE_TIME) > 0f) {
            v.add(Stat.OXYGEN, 2.5f);
            if (v.get(Stat.STIMULANT) < 70f) v.add(Stat.FIBRILLATION, -0.5f);
            if (v.get(Stat.BLOOD) < NORMAL_BLOOD) v.add(Stat.BLOOD, 0.3f);
        }
    }

    private static void tickConsciousness(ServerPlayer player, Vitals v, float pain) {
        float cap = 100f;
        cap = Math.min(cap, (v.get(Stat.OXYGEN) - 50f) * 2f);
        cap = Math.min(cap, 150f - pain * 1.5f);
        float systolic = v.get(Stat.SYSTOLIC);
        if (systolic < 110f) cap = Math.min(cap, 100f - (110f - systolic) * 1.4f);
        else if (systolic > 150f) cap = Math.min(cap, 100f - (systolic - 150f) * 1.17f);
        cap = Math.min(cap, 100f - v.get(Stat.RADIATION) * 7f / 3f);
        float opioid = v.get(Stat.OPIOID);
        if (opioid > 50f) cap = Math.min(cap, 100f - (opioid - 50f) * 1.2f);
        if (v.get(Stat.THIRST) < 10f) cap = Math.min(cap, 60f);
        float energy = v.get(Stat.ENERGY);
        if (energy < 10f) cap = Math.min(cap, 40f + energy * 3f);
        cap += v.get(Stat.STIMULANT) * 0.3f;
        cap = Math.min(cap, v.get(Stat.BRAIN) * 2.1f);
        if (v.get(Stat.MINDWIPE_TIME) > 0 || v.isCardiacArrest() || systolic < 60f || v.get(Stat.OXYGEN) < 50f) cap = 0f;
        cap = Mth.clamp(cap, 0f, 100f);

        float current = v.get(Stat.CONSCIOUSNESS);
        v.set(Stat.CONSCIOUSNESS, current + Mth.clamp(cap - current, cap <= 0f ? -25f : -8f, 3f));
    }

    private static void tickBrain(Vitals v, RandomSource random) {
        if (v.get(Stat.SYSTOLIC) > 180f) {
            v.add(Stat.BRAIN, -0.3f);
            if (random.nextFloat() < 0.01f) v.add(Stat.CONSCIOUSNESS, -50f);
        }
        if (v.get(Stat.STIMULANT) > 100f && random.nextFloat() < 0.05f) v.add(Stat.CONSCIOUSNESS, -50f);
        boolean collapse = v.isCardiacArrest() || v.get(Stat.SYSTOLIC) < 60f || v.get(Stat.OXYGEN) < 50f;
        if (collapse) v.add(Stat.BRAIN, -1.5f);
        else if (v.get(Stat.OXYGEN) < 70f) v.add(Stat.BRAIN, -0.3f);
        if (v.get(Stat.OPIOID) > 120f) v.add(Stat.BRAIN, -0.4f);
        if (v.get(Stat.THIRST) <= 0f && v.get(Stat.BLOOD) < 3500f) v.add(Stat.BRAIN, -0.2f);
        if (v.get(Stat.BRAIN) < 100f && v.get(Stat.CONSCIOUSNESS) > 50f && v.get(Stat.OXYGEN) > 90f) {
            v.add(Stat.BRAIN, 0.03f);
        }
    }

    private static void applyEffects(ServerPlayer player, Vitals v, LimbHealth limbs, RandomSource random) {
        float consciousness = v.get(Stat.CONSCIOUSNESS);
        int slowness = -1;
        if (consciousness < INCAPACITATED) {
            slowness = 6;
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, EFFECT_TICKS, 4, false, false));
        } else if (consciousness < 55f) {
            slowness = 2;
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, EFFECT_TICKS, 1, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, EFFECT_TICKS, 0, false, false));
        } else if (consciousness < 72f) {
            slowness = 1;
        } else if (consciousness < 90f) {
            slowness = 0;
        }

        float legs = (limbs.getMuscle(Limb.LEFT_LEG) + limbs.getMuscle(Limb.RIGHT_LEG)) / 2f;
        if (legs < 25f) slowness = Math.max(slowness, 1);
        else if (legs < 50f) slowness = Math.max(slowness, 0);
        if (v.get(Stat.ENERGY) < 10f) slowness = Math.max(slowness, 0);
        if (slowness >= 0) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, EFFECT_TICKS, slowness, false, false));
        }

        float arms = Math.min(limbs.getMuscle(Limb.LEFT_ARM), limbs.getMuscle(Limb.RIGHT_ARM));
        if (arms < 30f) player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, EFFECT_TICKS, 0, false, false));

        if (v.get(Stat.STIMULANT) > 0f && consciousness >= INCAPACITATED) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, EFFECT_TICKS, 0, false, false));
        }
        if (v.get(Stat.THIRST) < 15f && random.nextFloat() < 0.05f) {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 0, false, false));
        }
    }
}
