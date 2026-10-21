package com.lunkoashtail.avaliproject.compat.origins;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesUtil;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.Optional;

public final class OriginsCompat {
    public static final String ORIGINS_MOD_ID = "origins";
    public static final String NEO_ORIGINS_MOD_ID = "neoorigins";

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int COMMAND_PERMISSION = 4;
    private static final String SUBSPECIES_PREFIX = "manokit_";
    private static final ResourceLocation SUBSPECIES_LAYER =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "manokit_subtype");

    private static final ResourceLocation ORIGIN_DATA =
            ResourceLocation.fromNamespaceAndPath(NEO_ORIGINS_MOD_ID, "origin_data");

    private static Port port = Port.NONE;
    private static boolean pushing;

    private OriginsCompat() {
    }

    public enum Port {
        NONE(null, null, null, null, null, null),
        IAF_ORIGINS("origin", "compat_packs/origins_iaf", "origins:origin", "origins:human", null,
                "com.iafenvoy.origins.event.GrantOriginEvent"),
        NEO_ORIGINS("neoorigins", "compat_packs/neoorigins", "neoorigins:origin", "neoorigins:human", "neoorigins:class",
                "com.cyberday1.neoorigins.api.event.OriginChangedEvent");

        private final String command;
        private final String packPath;
        private final ResourceLocation raceLayer;
        private final ResourceLocation humanOrigin;
        private final ResourceLocation classLayer;
        private final String changeEvent;

        Port(String command, String packPath, String raceLayer, String humanOrigin, String classLayer, String changeEvent) {
            this.command = command;
            this.packPath = packPath;
            this.raceLayer = raceLayer == null ? null : ResourceLocation.parse(raceLayer);
            this.humanOrigin = humanOrigin == null ? null : ResourceLocation.parse(humanOrigin);
            this.classLayer = classLayer == null ? null : ResourceLocation.parse(classLayer);
            this.changeEvent = changeEvent;
        }
    }

    public static void init() {
        boolean iaf = ModList.get().isLoaded(ORIGINS_MOD_ID);
        boolean neo = ModList.get().isLoaded(NEO_ORIGINS_MOD_ID);
        if (iaf) {
            port = Port.IAF_ORIGINS;
            if (neo) {
                LOGGER.info("Both origins and neoorigins are loaded; using origins and skipping neoorigins species compat");
            }
        } else if (neo) {
            port = Port.NEO_ORIGINS;
        }
        if (port != Port.NONE) {
            LOGGER.info("Species origins compat enabled for {}", port);
            listenForOriginChanges();
        }
    }

    public static boolean isActive() {
        return port != Port.NONE;
    }

    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (port == Port.NONE || event.getPackType() != PackType.SERVER_DATA) return;
        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, port.packPath),
                PackType.SERVER_DATA,
                Component.literal("Galaxy species origins"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP);
    }

    public static void applySpecies(ServerPlayer player, Species species) {
        if (port == Port.NONE || pushing) return;
        ResourceLocation origin = species == Species.HUMAN
                ? port.humanOrigin
                : ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, SpeciesUtil.id(species));
        setOrigin(player, port.raceLayer, origin);
        if (species == Species.MANOKIT) {
            applySubspecies(player, player.getData(ModAttachments.MANOKIT_SUBSPECIES));
        }
        if (species != Species.HUMAN) {
            promptMissingClass(player);
        }
    }

    private static void promptMissingClass(ServerPlayer player) {
        if (port.classLayer == null || hasOriginForLayer(player, port.classLayer)) return;
        runCommand(player, port.command + " gui " + player.getGameProfile().getName() + " " + port.classLayer,
                "open the class picker");
    }

    private static boolean hasOriginForLayer(ServerPlayer player, ResourceLocation layer) {
        try {
            AttachmentType<?> type = NeoForgeRegistries.ATTACHMENT_TYPES.get(ORIGIN_DATA);
            if (type == null || !player.hasData(type)) return false;
            Object data = player.getData(type);
            Object result = data.getClass().getMethod("hasOriginForLayer", ResourceLocation.class).invoke(data, layer);
            return Boolean.TRUE.equals(result);
        } catch (ReflectiveOperationException | RuntimeException e) {
            LOGGER.debug("Origins compat could not read {} for {}", layer, player.getName().getString(), e);
            return true;
        }
    }

    public static void applySubspecies(ServerPlayer player, ManokitVariant subspecies) {
        if (port == Port.NONE || pushing) return;
        setOrigin(player, SUBSPECIES_LAYER, subspeciesOrigin(subspecies));
    }

    private static ResourceLocation subspeciesOrigin(ManokitVariant subspecies) {
        return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, SUBSPECIES_PREFIX + subspecies.id());
    }

    private static void setOrigin(ServerPlayer player, ResourceLocation layer, ResourceLocation origin) {
        runCommand(player, port.command + " set " + player.getGameProfile().getName() + " " + layer + " " + origin,
                "set origin " + origin);
    }

    private static void runCommand(ServerPlayer player, String command, String action) {
        MinecraftServer server = player.getServer();
        if (server == null) return;
        CommandSourceStack source = server.createCommandSourceStack().withSuppressedOutput().withPermission(COMMAND_PERMISSION);
        pushing = true;
        try {
            int result = server.getCommands().getDispatcher().execute(command, source);
            if (result <= 0) {
                LOGGER.warn("Origins compat could not {} for {} (command returned {})", action, player.getName().getString(), result);
            }
        } catch (CommandSyntaxException | RuntimeException e) {
            LOGGER.warn("Origins compat failed to {} for {}: {}", action, player.getName().getString(), e.getMessage());
        } finally {
            pushing = false;
        }
    }

    private static void listenForOriginChanges() {
        try {
            Class<? extends Event> type = Class.forName(port.changeEvent).asSubclass(Event.class);
            NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, false, type, OriginsCompat::onOriginEvent);
        } catch (ClassNotFoundException | ClassCastException e) {
            LOGGER.warn("Origins compat could not listen for origin changes ({}); serums still work", e.getMessage());
        }
    }

    private static void onOriginEvent(Event event) {
        if (pushing || !(event instanceof EntityEvent entityEvent)) return;
        Entity entity = entityEvent.getEntity();
        if (!(entity instanceof ServerPlayer player)) return;
        Optional<ResourceLocation> layer = readId(event, "getLayer");
        Optional<ResourceLocation> origin = readId(event, port == Port.IAF_ORIGINS ? "getOrigin" : "getNewOrigin");
        if (layer.isEmpty() || origin.isEmpty()) return;
        if (SUBSPECIES_LAYER.equals(layer.get())) {
            String path = origin.get().getPath();
            if (AvaliProject.MOD_ID.equals(origin.get().getNamespace()) && path.startsWith(SUBSPECIES_PREFIX)) {
                ManokitVariant subspecies = ManokitVariant.byId(path.substring(SUBSPECIES_PREFIX.length()));
                if (player.getData(ModAttachments.MANOKIT_SUBSPECIES) != subspecies) {
                    SpeciesUtil.applyInternalSubspecies(player, subspecies);
                }
            }
            return;
        }
        if (!port.raceLayer.equals(layer.get())) return;
        Species species = AvaliProject.MOD_ID.equals(origin.get().getNamespace())
                ? Species.fromName(origin.get().getPath())
                : Species.HUMAN;
        if (player.getData(ModAttachments.SPECIES) != species) {
            SpeciesUtil.applyInternal(player, species);
        }
    }

    private static Optional<ResourceLocation> readId(Event event, String getter) {
        try {
            Method method = event.getClass().getMethod(getter);
            Object value = method.invoke(event);
            if (value instanceof ResourceLocation id) return Optional.of(id);
            if (value instanceof Holder<?> holder) return holder.unwrapKey().map(key -> key.location());
        } catch (ReflectiveOperationException | RuntimeException e) {
            LOGGER.debug("Origins compat could not read {} from {}", getter, event.getClass().getName(), e);
        }
        return Optional.empty();
    }
}
