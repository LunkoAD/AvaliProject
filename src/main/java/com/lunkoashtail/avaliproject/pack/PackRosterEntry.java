package com.lunkoashtail.avaliproject.pack;

import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.species.Species;

import java.util.UUID;

public record PackRosterEntry(UUID id, String name, boolean isAvali, boolean online,
                               boolean male, boolean baby, int health, int maxHealth, int trust,
                               Species species, ManokitVariant subspecies, String skin) {

    public static PackRosterEntry forPlayer(UUID id, String name, boolean online, Species species, ManokitVariant subspecies,
                                            String skin) {
        return new PackRosterEntry(id, name, false, online, true, false, 0, 0, 0, species, subspecies, skin);
    }

    public static PackRosterEntry forAvali(UUID id, String name, boolean male, boolean baby, int health, int maxHealth, int trust,
                                           String skin) {
        return new PackRosterEntry(id, name, true, true, male, baby, health, maxHealth, trust,
                Species.AVALI, ManokitVariant.DEFAULT, skin);
    }
}
