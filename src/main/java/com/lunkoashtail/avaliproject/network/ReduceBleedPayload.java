package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Client → Server: request to reduce bleed on a specific limb.
 *
 * Sent by the client when a bandage minigame completes successfully.
 * The server validates, applies the reduction, then echoes a LimbDataSyncPayload
 * back to the client.
 *
 * limbOrdinal: Limb.values()[limbOrdinal] — the target limb.
 * amount:      how many bleed points to remove (e.g. 30).
 */
public record ReduceBleedPayload(int limbOrdinal, int amount) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ReduceBleedPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "reduce_bleed"));

    public static final StreamCodec<ByteBuf, ReduceBleedPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, ReduceBleedPayload::limbOrdinal,
                    ByteBufCodecs.INT, ReduceBleedPayload::amount,
                    ReduceBleedPayload::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /** Server-side handler: apply reduction and sync updated data to client. */
    public static void handle(ReduceBleedPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer serverPlayer)) return;

            Limb[] limbs = Limb.values();
            if (payload.limbOrdinal() < 0 || payload.limbOrdinal() >= limbs.length) return;

            Limb limb = limbs[payload.limbOrdinal()];
            LimbData data = serverPlayer.getData(ModAttachments.LIMB_DATA);
            data.reduceBleed(limb, payload.amount());

            // Send the authoritative state back to the client.
            PacketDistributor.sendToPlayer(serverPlayer, LimbDataSyncPayload.from(data));
        });
    }
}
