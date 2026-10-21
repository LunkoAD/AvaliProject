package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.CustomSkins;
import com.lunkoashtail.avaliproject.species.Species;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public final class CustomSkinTextures {
    private static final Logger LOGGER = LogUtils.getLogger();

    private record Loaded(String name, ResourceLocation texture) {
    }

    private record Local(long modified, long size, @Nullable ResourceLocation texture, boolean valid) {
    }

    private static final Map<UUID, Map<String, Loaded>> REMOTE = new HashMap<>();
    private static final Map<Path, Local> LOCAL = new HashMap<>();
    private static final Set<ResourceLocation> REGISTERED = new HashSet<>();
    private static int counter;

    private CustomSkinTextures() {
    }

    public static void accept(UUID owner, String folder, String name, byte[] png) {
        Map<String, Loaded> skins = REMOTE.computeIfAbsent(owner, key -> new HashMap<>());
        Loaded old = skins.remove(folder);
        if (old != null) release(old.texture());
        if (png.length > 0) {
            ResourceLocation texture = register(png);
            if (texture != null) skins.put(folder, new Loaded(name, texture));
        }
        if (skins.isEmpty()) REMOTE.remove(owner);
    }

    public static Optional<ResourceLocation> forPlayer(Player player) {
        Species species = player.getData(ModAttachments.SPECIES);
        if (species == Species.HUMAN) return Optional.empty();
        return find(player.getUUID(), species, player.getData(ModAttachments.MANOKIT_SUBSPECIES),
                player.getData(ModAttachments.SPECIES_COSMETICS).choice(species).orElse(""));
    }

    public static Optional<ResourceLocation> find(UUID owner, Species species, ManokitVariant subspecies, String choice) {
        if (!CustomSkins.isCustom(choice)) return Optional.empty();
        Map<String, Loaded> skins = REMOTE.get(owner);
        Loaded loaded = skins == null ? null : skins.get(CustomSkins.folder(species, subspecies));
        if (loaded == null || !CustomSkins.choiceFor(loaded.name()).equals(choice)) return Optional.empty();
        return Optional.of(loaded.texture());
    }

    @Nullable
    public static ResourceLocation local(Path file) {
        Local current = localEntry(file);
        return current.valid() ? current.texture() : null;
    }

    public static boolean isLocalValid(Path file) {
        return localEntry(file).valid();
    }

    private static Local localEntry(Path file) {
        long modified;
        long size;
        try {
            modified = Files.getLastModifiedTime(file).toMillis();
            size = Files.size(file);
        } catch (IOException e) {
            return new Local(0L, 0L, null, false);
        }
        Local cached = LOCAL.get(file);
        if (cached != null && cached.modified() == modified && cached.size() == size) return cached;
        if (cached != null && cached.texture() != null) release(cached.texture());
        byte[] png = readLocal(file);
        ResourceLocation texture = png == null ? null : register(png);
        Local loaded = new Local(modified, size, texture, texture != null);
        LOCAL.put(file, loaded);
        return loaded;
    }

    @Nullable
    public static byte[] readLocal(Path file) {
        try {
            if (Files.size(file) > CustomSkins.MAX_BYTES) return null;
            byte[] png = Files.readAllBytes(file);
            return CustomSkins.isValidPng(png) ? png : null;
        } catch (IOException e) {
            LOGGER.warn("Failed to read custom skin {}", file, e);
            return null;
        }
    }

    public static boolean isRegistered(ResourceLocation texture) {
        return REGISTERED.contains(texture);
    }

    @Nullable
    private static ResourceLocation register(byte[] png) {
        try {
            NativeImage image = NativeImage.read(new ByteArrayInputStream(png));
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "dynamic/custom_skin_" + counter++);
            Minecraft.getInstance().getTextureManager().register(id, new DynamicTexture(image));
            REGISTERED.add(id);
            return id;
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.warn("Failed to load custom skin texture", e);
            return null;
        }
    }

    private static void release(ResourceLocation texture) {
        REGISTERED.remove(texture);
        Minecraft.getInstance().getTextureManager().release(texture);
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        REMOTE.values().forEach(skins -> skins.values().forEach(loaded -> release(loaded.texture())));
        REMOTE.clear();
    }
}
