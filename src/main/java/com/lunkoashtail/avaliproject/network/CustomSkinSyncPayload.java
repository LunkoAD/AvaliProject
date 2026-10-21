package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.client.CustomSkinTextures;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.CustomSkins;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Map;
import java.util.UUID;

public record CustomSkinSyncPayload(UUID owner, String folder, String name, byte[] png) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<CustomSkinSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "custom_skin_sync"));

    public static final StreamCodec<ByteBuf, CustomSkinSyncPayload> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, CustomSkinSyncPayload::owner,
            ByteBufCodecs.STRING_UTF8, CustomSkinSyncPayload::folder,
            ByteBufCodecs.STRING_UTF8, CustomSkinSyncPayload::name,
            ByteBufCodecs.byteArray(CustomSkins.MAX_BYTES), CustomSkinSyncPayload::png,
            CustomSkinSyncPayload::new
    );

    public static CustomSkinSyncPayload cleared(UUID owner, String folder) {
        return new CustomSkinSyncPayload(owner, folder, "", new byte[0]);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void broadcast(ServerPlayer owner, String folder) {
        CustomSkins.SkinFile file = owner.getData(ModAttachments.CUSTOM_SKINS).byFolder().get(folder);
        PacketDistributor.sendToAllPlayers(file == null
                ? cleared(owner.getUUID(), folder)
                : new CustomSkinSyncPayload(owner.getUUID(), folder, file.name(), file.png()));
    }

    public static void sendAll(ServerPlayer owner, ServerPlayer receiver) {
        for (Map.Entry<String, CustomSkins.SkinFile> entry : owner.getData(ModAttachments.CUSTOM_SKINS).byFolder().entrySet()) {
            PacketDistributor.sendToPlayer(receiver, new CustomSkinSyncPayload(owner.getUUID(), entry.getKey(),
                    entry.getValue().name(), entry.getValue().png()));
        }
    }

    public static void handle(CustomSkinSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> CustomSkinTextures.accept(payload.owner(), payload.folder(), payload.name(), payload.png()));
    }
}
