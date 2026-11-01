package com.lunkoashtail.avaliproject.block.custom;

import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Locale;

public enum ProtogenColor implements StringRepresentable {
    CLASSIC,
    ARCTIC,
    BUBBLEGUM,
    CRIMSON,
    EMBER,
    GALAXY,
    LAVENDER,
    MIDNIGHT,
    MINT,
    OCEAN,
    ROYAL,
    SUNSET,
    TOXIC;

    public static final EnumProperty<ProtogenColor> PROPERTY = EnumProperty.create("color", ProtogenColor.class);

    private final String id = name().toLowerCase(Locale.ROOT);

    @Override
    public String getSerializedName() {
        return id;
    }

    public static ProtogenColor random(RandomSource random) {
        ProtogenColor[] colors = values();
        return colors[random.nextInt(colors.length)];
    }

    public static ProtogenColor fromDye(DyeColor dye) {
        return switch (dye) {
            case WHITE, LIGHT_GRAY -> CLASSIC;
            case GRAY, BLACK -> MIDNIGHT;
            case BROWN -> EMBER;
            case RED -> CRIMSON;
            case ORANGE -> SUNSET;
            case YELLOW -> ROYAL;
            case LIME, GREEN -> TOXIC;
            case CYAN -> MINT;
            case LIGHT_BLUE -> ARCTIC;
            case BLUE -> OCEAN;
            case PURPLE -> GALAXY;
            case MAGENTA, PINK -> BUBBLEGUM;
        };
    }

    public static ProtogenColor fromSkin(String skin) {
        for (ProtogenColor color : values()) {
            if (color.id.equals(skin)) return color;
        }
        return "blue".equals(skin) ? OCEAN : CLASSIC;
    }
}
