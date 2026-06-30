package com.lunkoashtail.avaliproject.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum TalxleechVariant {
    BROWN(0),
    REDSOIL(1),
    SICKBROWN(2);
    private static final TalxleechVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(TalxleechVariant::getId)).toArray(TalxleechVariant[]::new);
    private final int id;
    TalxleechVariant(int id) { this.id = id; }
    public int getId() { return id; }
    public static TalxleechVariant byId(int id) { return BY_ID[id % BY_ID.length]; }
}
