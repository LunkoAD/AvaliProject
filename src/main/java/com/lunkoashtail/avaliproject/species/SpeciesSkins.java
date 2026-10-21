package com.lunkoashtail.avaliproject.species;

import net.minecraft.util.RandomSource;

import java.util.List;

public final class SpeciesSkins {
    private SpeciesSkins() {
    }

    public static String random(Species species, RandomSource random) {
        List<SpeciesCosmetics.Entry> skins = SpeciesCosmetics.forSpecies(species);
        return skins.isEmpty() ? "" : skins.get(random.nextInt(skins.size())).id();
    }

    public static boolean isKnown(Species species, String id) {
        return SpeciesCosmetics.forSpecies(species).stream().anyMatch(entry -> entry.id().equals(id));
    }

    public static String knownOrRandom(Species species, String id, RandomSource random) {
        return isKnown(species, id) ? id : random(species, random);
    }
}
