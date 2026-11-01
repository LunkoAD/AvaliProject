package com.lunkoashtail.avaliproject.block.custom;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum StationKind implements StringRepresentable {
    GENERATOR(0),
    RESEARCH(9),
    MINING(21),
    FARMING(18);

    public static final Codec<StationKind> CODEC = StringRepresentable.fromEnum(StationKind::values);

    private final String id = name().toLowerCase(Locale.ROOT);
    private final int inventorySize;

    StationKind(int inventorySize) {
        this.inventorySize = inventorySize;
    }

    public int inventorySize() {
        return inventorySize;
    }

    public boolean usesProtogen() {
        return this != GENERATOR;
    }

    @Override
    public String getSerializedName() {
        return id;
    }
}
