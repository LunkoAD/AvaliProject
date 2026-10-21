package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.species.CustomSkins;
import com.lunkoashtail.avaliproject.species.Species;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public final class CustomSkinFolder {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String PNG = ".png";
    private static final String README = """
            Avali Project custom skins
            ==========================

            Drop any .png file into the folder of the species it is made for.
            The file name does not matter, it is used as the skin's name in the wardrobe.
            Manokit skins go into the folder of their subspecies (manokit/tribal, manokit/arcane, manokit/beachdweller).

            Open the Species Wardrobe in game, pick the skin and press Apply.
            Other players on the server will see it too.

            Limits: 512 KB per file, at most 1024x1024 pixels.
            Use the species' original texture as a template so the UV layout matches.
            """;

    private CustomSkinFolder() {
    }

    public static Path root() {
        return FMLPaths.GAMEDIR.get().resolve(CustomSkins.FOLDER_NAME);
    }

    public static Path folder(Species species, ManokitVariant subspecies) {
        return root().resolve(CustomSkins.folder(species, subspecies));
    }

    public static void ensureCreated() {
        try {
            for (Species species : Species.values()) {
                if (species == Species.HUMAN) continue;
                if (species == Species.MANOKIT) {
                    for (ManokitVariant variant : ManokitVariant.values()) {
                        Files.createDirectories(folder(species, variant));
                    }
                } else {
                    Files.createDirectories(folder(species, ManokitVariant.DEFAULT));
                }
            }
            Path readme = root().resolve("README.txt");
            if (!Files.exists(readme)) {
                Files.writeString(readme, README, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to create custom skin folders in {}", root(), e);
        }
    }

    public static List<Path> list(Species species, ManokitVariant subspecies) {
        Path dir = folder(species, subspecies);
        if (!Files.isDirectory(dir)) return List.of();
        try (Stream<Path> files = Files.list(dir)) {
            return files.filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(PNG))
                    .filter(file -> CustomSkins.isValidName(nameOf(file)))
                    .sorted()
                    .toList();
        } catch (IOException e) {
            LOGGER.warn("Failed to list custom skins in {}", dir, e);
            return List.of();
        }
    }

    public static String nameOf(Path file) {
        String fileName = file.getFileName().toString();
        return fileName.substring(0, fileName.length() - PNG.length());
    }
}
