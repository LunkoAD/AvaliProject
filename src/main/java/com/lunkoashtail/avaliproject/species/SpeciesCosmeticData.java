package com.lunkoashtail.avaliproject.species;

import com.mojang.serialization.Codec;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record SpeciesCosmeticData(Map<String, String> choices) {
    public static final SpeciesCosmeticData EMPTY = new SpeciesCosmeticData(Map.of());
    public static final Codec<SpeciesCosmeticData> CODEC =
            Codec.unboundedMap(Codec.STRING, Codec.STRING).xmap(SpeciesCosmeticData::new, SpeciesCosmeticData::choices);

    public SpeciesCosmeticData {
        choices = Map.copyOf(choices);
    }

    public Optional<String> choice(Species species) {
        return Optional.ofNullable(choices.get(SpeciesUtil.id(species)));
    }

    public SpeciesCosmeticData with(Species species, String variantId) {
        Map<String, String> updated = new HashMap<>(choices);
        updated.put(SpeciesUtil.id(species), variantId);
        return new SpeciesCosmeticData(updated);
    }

    public SpeciesCosmeticData without(Species species) {
        Map<String, String> updated = new HashMap<>(choices);
        updated.remove(SpeciesUtil.id(species));
        return new SpeciesCosmeticData(updated);
    }
}
