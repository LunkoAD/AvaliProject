package com.lunkoashtail.avaliproject.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum EepuorVariant {
    GLOW(0),
    CYAN(1),
    SWAMP_GREEN(2),
    PALE_DEEP(3);
    private static final EepuorVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(EepuorVariant::getId)).toArray(EepuorVariant[]::new);
    private final int id;
    EepuorVariant(int id) { this.id = id; }
    public int getId() { return id; }
    public static EepuorVariant byId(int id) { return BY_ID[id % BY_ID.length]; }
}
