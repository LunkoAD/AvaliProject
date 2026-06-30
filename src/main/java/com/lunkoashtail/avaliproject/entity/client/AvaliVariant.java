package com.lunkoashtail.avaliproject.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum AvaliVariant {
    PENGUIN(0),
    BLANK(1);
    private static final AvaliVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(AvaliVariant::getId)).toArray(AvaliVariant[]::new);
    private final int id;
    AvaliVariant(int id) { this.id = id; }
    public int getId() { return id; }
    public static AvaliVariant byId(int id) { return BY_ID[id % BY_ID.length]; }
}
