package com.lunkoashtail.avaliproject.vitals;

import com.lunkoashtail.avaliproject.limb.Limb;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LimbHealth {

    public static final float MAX = 100f;
    private static final int COUNT = Limb.values().length;

    private final float[] skin = new float[COUNT];
    private final float[] muscle = new float[COUNT];
    private final float[] infection = new float[COUNT];
    private final float[] clean = new float[COUNT];
    private final boolean[] tourniquet = new boolean[COUNT];

    public LimbHealth() {
        clear();
    }

    public void clear() {
        Arrays.fill(skin, MAX);
        Arrays.fill(muscle, MAX);
        Arrays.fill(infection, 0f);
        Arrays.fill(clean, 0f);
        Arrays.fill(tourniquet, false);
    }

    public float getSkin(Limb limb) { return skin[limb.ordinal()]; }
    public float getMuscle(Limb limb) { return muscle[limb.ordinal()]; }
    public float getInfection(Limb limb) { return infection[limb.ordinal()]; }
    public float getClean(Limb limb) { return clean[limb.ordinal()]; }
    public boolean hasTourniquet(Limb limb) { return tourniquet[limb.ordinal()]; }

    public void setSkin(Limb limb, float value) { skin[limb.ordinal()] = clamp(value); }
    public void setMuscle(Limb limb, float value) { muscle[limb.ordinal()] = clamp(value); }
    public void setInfection(Limb limb, float value) { infection[limb.ordinal()] = clamp(value); }
    public void setClean(Limb limb, float seconds) { clean[limb.ordinal()] = Math.max(0f, seconds); }
    public void setTourniquet(Limb limb, boolean value) { tourniquet[limb.ordinal()] = value; }

    public void addSkin(Limb limb, float amount) { setSkin(limb, getSkin(limb) + amount); }
    public void addMuscle(Limb limb, float amount) { setMuscle(limb, getMuscle(limb) + amount); }
    public void addInfection(Limb limb, float amount) { setInfection(limb, getInfection(limb) + amount); }

    public float maxInfection() {
        float max = 0f;
        for (float value : infection) max = Math.max(max, value);
        return max;
    }

    public void copyFrom(LimbHealth other) {
        System.arraycopy(other.skin, 0, skin, 0, COUNT);
        System.arraycopy(other.muscle, 0, muscle, 0, COUNT);
        System.arraycopy(other.infection, 0, infection, 0, COUNT);
        System.arraycopy(other.clean, 0, clean, 0, COUNT);
        System.arraycopy(other.tourniquet, 0, tourniquet, 0, COUNT);
    }

    private static float clamp(float value) {
        return Math.max(0f, Math.min(MAX, value));
    }

    private static List<Float> toList(float[] array) {
        List<Float> list = new ArrayList<>(array.length);
        for (float value : array) list.add(value);
        return list;
    }

    private static void fill(float[] target, List<Float> source) {
        for (int i = 0; i < Math.min(target.length, source.size()); i++) target[i] = source.get(i);
    }

    private static List<Boolean> toBoolList(boolean[] array) {
        List<Boolean> list = new ArrayList<>(array.length);
        for (boolean value : array) list.add(value);
        return list;
    }

    private static LimbHealth of(List<Float> skin, List<Float> muscle, List<Float> infection, List<Float> clean, List<Boolean> tourniquet) {
        LimbHealth health = new LimbHealth();
        fill(health.skin, skin);
        fill(health.muscle, muscle);
        fill(health.infection, infection);
        fill(health.clean, clean);
        for (int i = 0; i < Math.min(COUNT, tourniquet.size()); i++) health.tourniquet[i] = tourniquet.get(i);
        return health;
    }

    public static final Codec<LimbHealth> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.listOf().optionalFieldOf("skin", List.of()).forGetter(h -> toList(h.skin)),
            Codec.FLOAT.listOf().optionalFieldOf("muscle", List.of()).forGetter(h -> toList(h.muscle)),
            Codec.FLOAT.listOf().optionalFieldOf("infection", List.of()).forGetter(h -> toList(h.infection)),
            Codec.FLOAT.listOf().optionalFieldOf("clean", List.of()).forGetter(h -> toList(h.clean)),
            Codec.BOOL.listOf().optionalFieldOf("tourniquet", List.of()).forGetter(h -> toBoolList(h.tourniquet))
    ).apply(instance, LimbHealth::of));

    public static final StreamCodec<ByteBuf, LimbHealth> STREAM_CODEC = StreamCodec.of(
            (buf, h) -> {
                for (int i = 0; i < COUNT; i++) {
                    buf.writeFloat(h.skin[i]);
                    buf.writeFloat(h.muscle[i]);
                    buf.writeFloat(h.infection[i]);
                    buf.writeFloat(h.clean[i]);
                    buf.writeBoolean(h.tourniquet[i]);
                }
            },
            buf -> {
                LimbHealth h = new LimbHealth();
                for (int i = 0; i < COUNT; i++) {
                    h.skin[i] = buf.readFloat();
                    h.muscle[i] = buf.readFloat();
                    h.infection[i] = buf.readFloat();
                    h.clean[i] = buf.readFloat();
                    h.tourniquet[i] = buf.readBoolean();
                }
                return h;
            }
    );
}
