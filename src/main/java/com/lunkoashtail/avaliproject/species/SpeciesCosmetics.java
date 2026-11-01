package com.lunkoashtail.avaliproject.species;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.client.ExpieVariant;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.entity.client.ProtogenVariant;
import com.lunkoashtail.avaliproject.entity.client.SergalVariant;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public final class SpeciesCosmetics {
    public static final int NO_VARIANT = -1;

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String PNG = ".png";

    public record Entry(Species species, @Nullable ManokitVariant model, String id, String translationKey,
                        String fallbackName, ResourceLocation texture, int variantId) {
        public boolean fitsModel(ManokitVariant subspecies) {
            return model == null || model == subspecies;
        }
    }

    private static final Map<Species, List<Entry>> BY_SPECIES = new EnumMap<>(Species.class);

    static {
        add(Species.EXPIE, null, "normal", "expie/expie", ExpieVariant.NORMAL.getId());
        add(Species.EXPIE, null, "white", "expie/expie_white", ExpieVariant.WHITE.getId());

        add(Species.AVALI, null, "cynavali", "avali/avali_cynavali", NO_VARIANT);
        add(Species.AVALI, null, "kuro_swiftfeather", "avali/avali_kuro_swiftfeather", NO_VARIANT);
        add(Species.AVALI, null, "mack", "avali/avali_mack", NO_VARIANT);

        for (SergalVariant variant : SergalVariant.values()) {
            String id = lower(variant.name());
            add(Species.SERGAL, null, id, "sergal/sergal_" + id, variant.getId());
        }

        add(Species.PROTOGEN, null, "blue", "protogen/protogen", ProtogenVariant.BLUE.getId());

        add(Species.WICKERBEAST, null, "default", "wickerbeast/wickerbeast", NO_VARIANT);

        Path root = textureRoot();
        if (root != null) {
            discover(root);
            pruneMissing(root);
        }
    }

    private SpeciesCosmetics() {
    }

    @Nullable
    private static Path textureRoot() {
        ModList modList = ModList.get();
        IModFileInfo modFile = modList == null ? null : modList.getModFileById(AvaliProject.MOD_ID);
        if (modFile == null) return null;
        Path root = modFile.getFile().findResource("assets", AvaliProject.MOD_ID, "textures", "entity");
        return Files.isDirectory(root) ? root : null;
    }

    private static void pruneMissing(Path root) {
        String prefix = "textures/entity/";
        for (List<Entry> entries : BY_SPECIES.values()) {
            entries.removeIf(entry -> {
                String path = entry.texture().getPath();
                boolean missing = !path.startsWith(prefix)
                        || !Files.isRegularFile(root.resolve(path.substring(prefix.length())));
                if (missing) LOGGER.info("Skipping {} skin '{}': {} not found", entry.species(), entry.id(), path);
                return missing;
            });
        }
    }

    private static void add(Species species, @Nullable ManokitVariant model, String id, String texturePath, int variantId) {
        String translationKey = "wardrobe." + AvaliProject.MOD_ID + "." + SpeciesUtil.id(species) + "." + id;
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID,
                "textures/entity/" + texturePath + PNG);
        BY_SPECIES.computeIfAbsent(species, key -> new ArrayList<>())
                .add(new Entry(species, model, id, translationKey, prettify(id), texture, variantId));
    }

    private static void discover(Path root) {
        for (Species species : Species.values()) {
            if (species == Species.HUMAN) continue;
            String speciesId = SpeciesUtil.id(species);
            Path dir = root.resolve(speciesId);
            if (!Files.isDirectory(dir)) continue;
            List<String> paths;
            try (Stream<Path> files = Files.walk(dir)) {
                paths = files.filter(Files::isRegularFile)
                        .map(file -> root.relativize(file).toString().replace('\\', '/'))
                        .sorted()
                        .toList();
            } catch (IOException e) {
                LOGGER.warn("Failed to scan {} skins", speciesId, e);
                continue;
            }
            for (String path : paths) {
                addDiscovered(species, speciesId, path);
            }
        }
    }

    private static void addDiscovered(Species species, String speciesId, String path) {
        String[] parts = path.split("/");
        String fileName = parts[parts.length - 1];
        String prefix = speciesId + "_";
        if (!fileName.startsWith(prefix) || !fileName.endsWith(PNG)) return;
        String id = fileName.substring(prefix.length(), fileName.length() - PNG.length());
        if (id.isEmpty() || !ResourceLocation.isValidPath(id) || !ResourceLocation.isValidPath(path)) return;

        ManokitVariant model = null;
        if (species == Species.MANOKIT) {
            if (parts.length != 3) return;
            model = ManokitVariant.byIdOrNull(parts[1]);
            if (model == null) return;
        } else if (parts.length != 2) {
            return;
        }

        String texturePath = path.substring(0, path.length() - PNG.length());
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/" + path);
        ManokitVariant finalModel = model;
        boolean known = BY_SPECIES.getOrDefault(species, List.of()).stream()
                .anyMatch(entry -> entry.texture().equals(texture)
                        || (entry.id().equals(id) && (entry.model() == null || entry.model() == finalModel)));
        if (known) return;
        add(species, model, id, texturePath, NO_VARIANT);
    }

    private static String prettify(String id) {
        StringBuilder name = new StringBuilder();
        for (String word : id.split("_")) {
            if (word.isEmpty()) continue;
            if (!name.isEmpty()) name.append(' ');
            name.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return name.toString();
    }

    private static String lower(String name) {
        return name.toLowerCase(Locale.ROOT);
    }

    public static List<Entry> forSpecies(Species species) {
        return List.copyOf(BY_SPECIES.getOrDefault(species, List.of()));
    }

    public static List<Entry> forModel(Species species, ManokitVariant subspecies) {
        return forSpecies(species).stream().filter(entry -> entry.fitsModel(subspecies)).toList();
    }

    public static Optional<Entry> defaultFor(Species species, ManokitVariant subspecies) {
        List<Entry> entries = forModel(species, subspecies);
        return entries.isEmpty() ? Optional.empty() : Optional.of(entries.get(0));
    }

    public static Optional<Entry> find(Species species, ManokitVariant subspecies, String id) {
        return forModel(species, subspecies).stream().filter(entry -> entry.id().equals(id)).findFirst();
    }

    public static Optional<Entry> resolve(Species species, ManokitVariant subspecies, SpeciesCosmeticData data) {
        return data.choice(species)
                .flatMap(id -> find(species, subspecies, id))
                .or(() -> defaultFor(species, subspecies));
    }
}
