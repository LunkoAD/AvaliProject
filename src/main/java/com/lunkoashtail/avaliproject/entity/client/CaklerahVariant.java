package com.lunkoashtail.avaliproject.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum CaklerahVariant {
    DEFAULT(0),
    CHOCOMILK(1),
    MILK(2);
    private static final CaklerahVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CaklerahVariant::getId)).toArray(CaklerahVariant[]::new);
    private final int id;
    CaklerahVariant(int id) { this.id = id; }
    public int getId() { return id; }
    public static CaklerahVariant byId(int id) { return BY_ID[id % BY_ID.length]; }
}
