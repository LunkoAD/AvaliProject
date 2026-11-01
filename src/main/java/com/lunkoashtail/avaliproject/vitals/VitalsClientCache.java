package com.lunkoashtail.avaliproject.vitals;

import com.lunkoashtail.avaliproject.network.VitalsSyncPayload;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class VitalsClientCache {

    private static final Map<Integer, VitalsSyncPayload> SNAPSHOTS = new HashMap<>();

    private VitalsClientCache() {}

    public static void put(VitalsSyncPayload payload) {
        SNAPSHOTS.put(payload.entityId(), payload);
    }

    @Nullable
    public static VitalsSyncPayload get(int entityId) {
        return SNAPSHOTS.get(entityId);
    }

    public static void clear() {
        SNAPSHOTS.clear();
    }
}
