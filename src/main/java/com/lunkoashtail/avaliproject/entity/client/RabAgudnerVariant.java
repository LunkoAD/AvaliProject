package com.lunkoashtail.avaliproject.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum RabAgudnerVariant {
    FIDDLE(0),
    MAROON(1);
    private static final RabAgudnerVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(RabAgudnerVariant::getId)).toArray(RabAgudnerVariant[]::new);
    private final int id;
    RabAgudnerVariant(int id) { this.id = id; }
    public int getId() { return id; }
    public static RabAgudnerVariant byId(int id) { return BY_ID[id % BY_ID.length]; }
}
