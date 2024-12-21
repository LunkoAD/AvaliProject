package com.lunkoashtail.avaliproject.entity.client;

import java.util.Arrays;
import java.util.Comparator;


public enum SergalVariant {
    BLACK(0),
    GREY(1),
    BLUE(2),
    BROWN(3),
    BROWN_ALT(4),
    CRIMSON(5),
    FROST(6),
    GREEN(7),
    ORANGE(8),
    PEACH(9),
    PINK(10),
    PURPLE(11);
    private static final SergalVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(SergalVariant::getId)).toArray(SergalVariant[]::new);
    private final int id;
    SergalVariant(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public static SergalVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}