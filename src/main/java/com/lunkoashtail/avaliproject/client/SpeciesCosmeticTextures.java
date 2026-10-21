package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesCosmetics;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public final class SpeciesCosmeticTextures {
    private static final Map<Entity, ResourceLocation> OVERRIDES = new WeakHashMap<>();
    private static final Map<ResourceLocation, Boolean> EXISTS = new HashMap<>();
    private static final Map<String, Optional<ResourceLocation>> SKINS = new HashMap<>();

    private SpeciesCosmeticTextures() {
    }

    public static void set(Entity entity, ResourceLocation texture) {
        OVERRIDES.put(entity, texture);
    }

    public static ResourceLocation resolve(Entity entity, ResourceLocation fallback) {
        ResourceLocation override = OVERRIDES.get(entity);
        return override != null ? override : fallback;
    }

    public static ResourceLocation skinOr(Species species, String skin, ResourceLocation fallback) {
        return SKINS.computeIfAbsent(species.name() + ":" + skin, key -> SpeciesCosmetics.forSpecies(species).stream()
                        .filter(entry -> entry.id().equals(skin))
                        .findFirst()
                        .map(SpeciesCosmetics.Entry::texture)
                        .filter(SpeciesCosmeticTextures::exists))
                .orElse(fallback);
    }

    public static ResourceLocation existingOr(ResourceLocation texture, ResourceLocation fallback) {
        return exists(texture) ? texture : fallback;
    }

    public static boolean exists(ResourceLocation texture) {
        if (CustomSkinTextures.isRegistered(texture)) return true;
        return EXISTS.computeIfAbsent(texture,
                key -> Minecraft.getInstance().getResourceManager().getResource(key).isPresent());
    }

    public static void clearCache() {
        EXISTS.clear();
        SKINS.clear();
    }
}
