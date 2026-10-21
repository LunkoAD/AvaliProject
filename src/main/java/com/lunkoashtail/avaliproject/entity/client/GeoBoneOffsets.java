package com.lunkoashtail.avaliproject.entity.client;

import software.bernie.geckolib.cache.object.GeoBone;

import java.util.Map;
import java.util.WeakHashMap;

public final class GeoBoneOffsets {
    private static final Map<GeoBone, float[]> STATES = new WeakHashMap<>();

    private GeoBoneOffsets() {
    }

    public static void apply(GeoBone bone, float x, float y, float z) {
        float[] state = STATES.computeIfAbsent(bone, key -> new float[]{0.0F, 0.0F, 0.0F, Float.NaN, Float.NaN, Float.NaN});
        bone.setRotX(axis(state, 0, bone.getRotX(), x));
        bone.setRotY(axis(state, 1, bone.getRotY(), y));
        bone.setRotZ(axis(state, 2, bone.getRotZ(), z));
    }

    private static float axis(float[] state, int axis, float current, float offset) {
        if (current != state[axis + 3]) {
            state[axis] = current;
        }
        state[axis + 3] = state[axis] + offset;
        return state[axis + 3];
    }
}
