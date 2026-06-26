package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Server → Client sync packet for LimbData.
 *
 * Sent whenever bleed values change on the server so the client's local
 * LimbSelectionScreen and HUD always show up-to-date information.
 *
 * Encodes all six bleed values as raw ints (ordinal order matches Limb enum).
 */
public record LimbDataSyncPayload(
        int head, int leftArm, int rightArm, int back, int leftLeg, int rightLeg
) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<LimbDataSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "limb_data_sync"));

    // Manual encoder/decoder to avoid any composite() arity issues.
    public static final StreamCodec<ByteBuf, LimbDataSyncPayload> STREAM_CODEC = StreamCodec.of(
            (buf, p) -> {
                buf.writeInt(p.head());
                buf.writeInt(p.leftArm());
                buf.writeInt(p.rightArm());
                buf.writeInt(p.back());
                buf.writeInt(p.leftLeg());
                buf.writeInt(p.rightLeg());
            },
            buf -> new LimbDataSyncPayload(
                    buf.readInt(), buf.readInt(), buf.readInt(),
                    buf.readInt(), buf.readInt(), buf.readInt()
            )
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /** Convenience factory: snapshot a LimbData into a payload ready to send. */
    public static LimbDataSyncPayload from(LimbData data) {
        return new LimbDataSyncPayload(
                data.getBleed(Limb.HEAD),
                data.getBleed(Limb.LEFT_ARM),
                data.getBleed(Limb.RIGHT_ARM),
                data.getBleed(Limb.BACK),
                data.getBleed(Limb.LEFT_LEG),
                data.getBleed(Limb.RIGHT_LEG)
        );
    }

    /**
     * Client-side handler: overwrites the local player's attachment with the
     * values received from the server.
     */
    public static void handle(LimbDataSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player == null) return;
            LimbData data = player.getData(ModAttachments.LIMB_DATA);
            data.setBleed(Limb.HEAD,      payload.head());
            data.setBleed(Limb.LEFT_ARM,  payload.leftArm());
            data.setBleed(Limb.RIGHT_ARM, payload.rightArm());
            data.setBleed(Limb.BACK,      payload.back());
            data.setBleed(Limb.LEFT_LEG,  payload.leftLeg());
            data.setBleed(Limb.RIGHT_LEG, payload.rightLeg());
        });
    }
}
