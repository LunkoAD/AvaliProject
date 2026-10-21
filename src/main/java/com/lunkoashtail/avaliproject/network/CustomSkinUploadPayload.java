package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.CustomSkins;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record CustomSkinUploadPayload(String folder, String name, int index, int total, byte[] chunk)
        implements CustomPacketPayload {

    private static final int MAX_CHUNKS = CustomSkins.MAX_BYTES / CustomSkins.CHUNK_SIZE + 1;
    private static final Map<UUID, Upload> UPLOADS = new HashMap<>();

    private static final class Upload {
        private final String folder;
        private final String name;
        private final int total;
        private final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        private int next;

        private Upload(String folder, String name, int total) {
            this.folder = folder;
            this.name = name;
            this.total = total;
        }
    }

    public static final CustomPacketPayload.Type<CustomSkinUploadPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "custom_skin_upload"));

    public static final StreamCodec<ByteBuf, CustomSkinUploadPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, CustomSkinUploadPayload::folder,
            ByteBufCodecs.STRING_UTF8, CustomSkinUploadPayload::name,
            ByteBufCodecs.VAR_INT, CustomSkinUploadPayload::index,
            ByteBufCodecs.VAR_INT, CustomSkinUploadPayload::total,
            ByteBufCodecs.byteArray(CustomSkins.CHUNK_SIZE), CustomSkinUploadPayload::chunk,
            CustomSkinUploadPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void sendToServer(String folder, String name, byte[] png) {
        int total = Math.max(1, (png.length + CustomSkins.CHUNK_SIZE - 1) / CustomSkins.CHUNK_SIZE);
        for (int i = 0; i < total; i++) {
            int from = i * CustomSkins.CHUNK_SIZE;
            int to = Math.min(png.length, from + CustomSkins.CHUNK_SIZE);
            PacketDistributor.sendToServer(new CustomSkinUploadPayload(folder, name, i, total, Arrays.copyOfRange(png, from, to)));
        }
    }

    public static void forget(UUID player) {
        UPLOADS.remove(player);
    }

    public static void handle(CustomSkinUploadPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            UUID id = player.getUUID();
            if (payload.total() <= 0 || payload.total() > MAX_CHUNKS || !CustomSkins.isValidName(payload.name())) {
                UPLOADS.remove(id);
                return;
            }
            if (payload.index() == 0) {
                UPLOADS.put(id, new Upload(payload.folder(), payload.name(), payload.total()));
            }
            Upload upload = UPLOADS.get(id);
            if (upload == null || upload.next != payload.index() || upload.total != payload.total()
                    || !upload.folder.equals(payload.folder()) || !upload.name.equals(payload.name())) {
                UPLOADS.remove(id);
                return;
            }
            upload.bytes.writeBytes(payload.chunk());
            upload.next++;
            if (upload.bytes.size() > CustomSkins.MAX_BYTES) {
                UPLOADS.remove(id);
                return;
            }
            if (upload.next < upload.total) return;
            UPLOADS.remove(id);
            finish(player, upload.folder, upload.name, upload.bytes.toByteArray());
        });
    }

    private static void finish(ServerPlayer player, String folder, String name, byte[] png) {
        Species species = player.getData(ModAttachments.SPECIES);
        if (species == Species.HUMAN) return;
        if (!CustomSkins.folder(species, player.getData(ModAttachments.MANOKIT_SUBSPECIES)).equals(folder)) return;
        if (!CustomSkins.isValidPng(png)) return;
        player.setData(ModAttachments.CUSTOM_SKINS,
                player.getData(ModAttachments.CUSTOM_SKINS).with(folder, new CustomSkins.SkinFile(name, png)));
        player.setData(ModAttachments.SPECIES_COSMETICS,
                player.getData(ModAttachments.SPECIES_COSMETICS).with(species, CustomSkins.choiceFor(name)));
        CustomSkinSyncPayload.broadcast(player, folder);
        SpeciesUtil.sync(player);
    }
}
