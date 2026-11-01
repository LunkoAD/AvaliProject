package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RequestVitalsPayload(int targetEntityId) implements CustomPacketPayload {

    private static final double MAX_VIEW_DISTANCE_SQ = 100;

    public static final CustomPacketPayload.Type<RequestVitalsPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "request_vitals"));

    public static final StreamCodec<ByteBuf, RequestVitalsPayload> STREAM_CODEC =
            ByteBufCodecs.INT.map(RequestVitalsPayload::new, RequestVitalsPayload::targetEntityId);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RequestVitalsPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer viewer)) return;
            if (!(viewer.level().getEntity(payload.targetEntityId()) instanceof ServerPlayer target)) return;
            if (target != viewer && target.distanceToSqr(viewer) > MAX_VIEW_DISTANCE_SQ) return;
            if (target.getData(ModAttachments.SPECIES) != Species.EXPIE) return;
            PacketDistributor.sendToPlayer(viewer, VitalsSyncPayload.from(target));
        });
    }
}
