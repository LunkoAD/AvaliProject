package com.lunkoashtail.avaliproject.entity.client;

import java.util.Arrays;
import java.util.Comparator;


public enum ProtogenVariant {
    BLUE(0),
    PURPLE(1),
    GREEN(2);
    private static final ProtogenVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ProtogenVariant::getId)).toArray(ProtogenVariant[]::new);
    private final int id;
    ProtogenVariant(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public static ProtogenVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}