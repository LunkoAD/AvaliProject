package com.lunkoashtail.avaliproject.entity.client;

import java.util.Arrays;
import java.util.Comparator;


public enum PrimagenVariant {
    BLUE(0),
    BROWN(1),
    PINK(2);
    private static final PrimagenVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(PrimagenVariant::getId)).toArray(PrimagenVariant[]::new);
    private final int id;
    PrimagenVariant(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public static PrimagenVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}