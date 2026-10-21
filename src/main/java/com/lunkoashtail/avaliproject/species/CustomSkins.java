package com.lunkoashtail.avaliproject.species;

import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CustomSkins {
    public static final String PREFIX = "custom:";
    public static final String FOLDER_NAME = "avaliproject_skins";
    public static final int MAX_BYTES = 512 * 1024;
    public static final int MAX_DIMENSION = 1024;
    public static final int CHUNK_SIZE = 30000;
    public static final int MAX_NAME_LENGTH = 64;

    private static final byte[] PNG_SIGNATURE = {(byte) 0x89, 'P', 'N', 'G', '\r', '\n', 0x1A, '\n'};

    public record SkinFile(String name, byte[] png) {
        public static final Codec<SkinFile> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("name").forGetter(SkinFile::name),
                Codec.BYTE_BUFFER.xmap(CustomSkins::toArray, ByteBuffer::wrap).fieldOf("png").forGetter(SkinFile::png)
        ).apply(instance, SkinFile::new));
    }

    public record Data(Map<String, SkinFile> byFolder) {
        public static final Data EMPTY = new Data(Map.of());
        public static final Codec<Data> CODEC = Codec.unboundedMap(Codec.STRING, SkinFile.CODEC)
                .xmap(Data::new, Data::byFolder);

        public Data {
            byFolder = Map.copyOf(byFolder);
        }

        public Data with(String folder, SkinFile file) {
            Map<String, SkinFile> updated = new HashMap<>(byFolder);
            updated.put(folder, file);
            return new Data(updated);
        }

        public Data without(String folder) {
            Map<String, SkinFile> updated = new HashMap<>(byFolder);
            updated.remove(folder);
            return new Data(updated);
        }
    }

    private CustomSkins() {
    }

    public static String folder(Species species, ManokitVariant subspecies) {
        String id = SpeciesUtil.id(species);
        return species == Species.MANOKIT ? id + "/" + subspecies.id() : id;
    }

    public static boolean isCustom(String choice) {
        return choice.startsWith(PREFIX);
    }

    public static String choiceFor(String name) {
        return PREFIX + name;
    }

    public static Optional<String> nameOf(SpeciesCosmeticData data, Species species) {
        return data.choice(species).filter(CustomSkins::isCustom).map(choice -> choice.substring(PREFIX.length()));
    }

    public static boolean isValidName(String name) {
        return !name.isEmpty() && name.length() <= MAX_NAME_LENGTH && name.chars().noneMatch(c -> c < 0x20);
    }

    public static boolean isValidPng(byte[] bytes) {
        if (bytes.length < 24 || bytes.length > MAX_BYTES) return false;
        for (int i = 0; i < PNG_SIGNATURE.length; i++) {
            if (bytes[i] != PNG_SIGNATURE[i]) return false;
        }
        ByteBuffer header = ByteBuffer.wrap(bytes, 16, 8);
        int width = header.getInt();
        int height = header.getInt();
        return width > 0 && height > 0 && width <= MAX_DIMENSION && height <= MAX_DIMENSION;
    }

    private static byte[] toArray(ByteBuffer buffer) {
        ByteBuffer copy = buffer.duplicate();
        byte[] bytes = new byte[copy.remaining()];
        copy.get(bytes);
        return bytes;
    }
}
