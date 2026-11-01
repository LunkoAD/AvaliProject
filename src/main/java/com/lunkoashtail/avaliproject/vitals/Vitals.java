package com.lunkoashtail.avaliproject.vitals;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;

public class Vitals {

    public enum Stat {
        BLOOD(5000f, 0f, 7500f),
        HEART_RATE(70f, 0f, 240f),
        SYSTOLIC(120f, 0f, 260f),
        DIASTOLIC(80f, 0f, 180f),
        OXYGEN(100f, 0f, 100f),
        RESP_RATE(16f, 0f, 40f),
        CONSCIOUSNESS(100f, 0f, 100f),
        BRAIN(100f, 0f, 100f),
        THIRST(100f, 0f, 100f),
        ENERGY(100f, 0f, 100f),
        RADIATION(0f, 0f, 30f),
        OPIOID(0f, 0f, 200f),
        ANALGESIA(0f, 0f, 100f),
        STIMULANT(0f, 0f, 150f),
        VASODILATOR(0f, 0f, 100f),
        VISCOSITY(0f, -100f, 100f),
        FIBRILLATION(0f, 0f, 100f),
        HEMOTHORAX(0f, 0f, 100f),
        BLEED_RATE(0f, 0f, 20f),
        ANTIRAD_TIME(0f, 0f, 3600f),
        ANTIBIOTIC_TIME(0f, 0f, 3600f),
        ANTIDEPRESSANT_TIME(0f, 0f, 3600f),
        ANTIDEPRESSANT_COOLDOWN(0f, 0f, 3600f),
        MINDWIPE_TIME(0f, 0f, 3600f),
        RADIATION_EXPOSURE(0f, 0f, 10f),
        HR_DRIFT(0f, -4f, 4f),
        OXYLINE_TIME(0f, 0f, 3600f),
        DYING_TIME(0f, 0f, 60f),
        DEATH_CAUSE(0f, 0f, 16f);

        public final float initial;
        public final float min;
        public final float max;
        public final String key;

        Stat(float initial, float min, float max) {
            this.initial = initial;
            this.min = min;
            this.max = max;
            this.key = name().toLowerCase();
        }
    }

    private static final Stat[] STATS = Stat.values();

    private final float[] values = new float[STATS.length];

    public Vitals() {
        reset();
    }

    public void reset() {
        for (Stat stat : STATS) values[stat.ordinal()] = stat.initial;
    }

    public float get(Stat stat) {
        return values[stat.ordinal()];
    }

    public void set(Stat stat, float value) {
        values[stat.ordinal()] = Math.max(stat.min, Math.min(stat.max, value));
    }

    public void add(Stat stat, float amount) {
        set(stat, get(stat) + amount);
    }

    public void tickDown(Stat stat, float amount) {
        if (get(stat) > 0) add(stat, -amount);
    }

    public boolean isCardiacArrest() {
        return get(Stat.FIBRILLATION) >= Stat.FIBRILLATION.max;
    }

    public void copyFrom(Vitals other) {
        System.arraycopy(other.values, 0, values, 0, values.length);
    }

    private Map<String, Float> toMap() {
        Map<String, Float> map = new HashMap<>();
        for (Stat stat : STATS) map.put(stat.key, values[stat.ordinal()]);
        return map;
    }

    private static Vitals fromMap(Map<String, Float> map) {
        Vitals vitals = new Vitals();
        for (Stat stat : STATS) {
            Float value = map.get(stat.key);
            if (value != null) vitals.set(stat, value);
        }
        return vitals;
    }

    public static final Codec<Vitals> CODEC =
            Codec.unboundedMap(Codec.STRING, Codec.FLOAT).xmap(Vitals::fromMap, Vitals::toMap);

    public static final StreamCodec<ByteBuf, Vitals> STREAM_CODEC = StreamCodec.of(
            (buf, vitals) -> {
                for (float value : vitals.values) buf.writeFloat(value);
            },
            buf -> {
                Vitals vitals = new Vitals();
                for (int i = 0; i < STATS.length; i++) vitals.values[i] = buf.readFloat();
                return vitals;
            }
    );
}
