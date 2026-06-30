package com.lunkoashtail.avaliproject.species;

import com.mojang.serialization.Codec;

public enum Species {
    HUMAN,
    EXPIE,
    AVALI,
    SERGAL,
    PROTOGEN;

    public static final Codec<Species> CODEC = Codec.STRING.xmap(Species::fromName, Species::name);

    public static Species fromName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return HUMAN;
        }
    }
}
