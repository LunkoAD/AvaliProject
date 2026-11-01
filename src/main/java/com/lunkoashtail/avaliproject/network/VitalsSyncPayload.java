package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbConditions;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.vitals.LimbHealth;
import com.lunkoashtail.avaliproject.vitals.Vitals;
import com.lunkoashtail.avaliproject.vitals.VitalsClientCache;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record VitalsSyncPayload(int entityId, Vitals vitals, LimbHealth limbs, float pain,
                                int[] bleed, int[] shrapnel, boolean[] dislocated) implements CustomPacketPayload {

    private static final int LIMBS = Limb.values().length;

    public static final CustomPacketPayload.Type<VitalsSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "vitals_sync"));

    public static final StreamCodec<ByteBuf, VitalsSyncPayload> STREAM_CODEC = StreamCodec.of(
            (buf, p) -> {
                buf.writeInt(p.entityId());
                Vitals.STREAM_CODEC.encode(buf, p.vitals());
                LimbHealth.STREAM_CODEC.encode(buf, p.limbs());
                buf.writeFloat(p.pain());
                for (int i = 0; i < LIMBS; i++) {
                    buf.writeByte(p.bleed()[i]);
                    buf.writeByte(p.shrapnel()[i]);
                    buf.writeBoolean(p.dislocated()[i]);
                }
            },
            buf -> {
                int id = buf.readInt();
                Vitals vitals = Vitals.STREAM_CODEC.decode(buf);
                LimbHealth limbs = LimbHealth.STREAM_CODEC.decode(buf);
                float pain = buf.readFloat();
                int[] bleed = new int[LIMBS];
                int[] shrapnel = new int[LIMBS];
                boolean[] dislocated = new boolean[LIMBS];
                for (int i = 0; i < LIMBS; i++) {
                    bleed[i] = buf.readUnsignedByte();
                    shrapnel[i] = buf.readUnsignedByte();
                    dislocated[i] = buf.readBoolean();
                }
                return new VitalsSyncPayload(id, vitals, limbs, pain, bleed, shrapnel, dislocated);
            }
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static VitalsSyncPayload from(Player player) {
        LimbData data = player.getData(ModAttachments.LIMB_DATA);
        LimbConditions conditions = player.getData(ModAttachments.LIMB_CONDITIONS);
        int[] bleed = new int[LIMBS];
        int[] shrapnel = new int[LIMBS];
        boolean[] dislocated = new boolean[LIMBS];
        for (Limb limb : Limb.values()) {
            bleed[limb.ordinal()] = data.getBleed(limb);
            shrapnel[limb.ordinal()] = conditions.getShrapnel(limb);
            dislocated[limb.ordinal()] = conditions.isDislocated(limb);
        }
        return new VitalsSyncPayload(player.getId(), player.getData(ModAttachments.VITALS),
                player.getData(ModAttachments.LIMB_HEALTH), player.getData(ModAttachments.PAIN_DATA).get(),
                bleed, shrapnel, dislocated);
    }

    public static void handle(VitalsSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            VitalsClientCache.put(payload);
            Player player = context.player();
            if (player != null && player.getId() == payload.entityId()) {
                player.getData(ModAttachments.VITALS).copyFrom(payload.vitals());
                player.getData(ModAttachments.LIMB_HEALTH).copyFrom(payload.limbs());
            }
        });
    }
}
