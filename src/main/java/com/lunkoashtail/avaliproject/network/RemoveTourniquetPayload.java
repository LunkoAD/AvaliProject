package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.vitals.LimbHealth;
import com.lunkoashtail.avaliproject.vitals.VitalsEventHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RemoveTourniquetPayload(int targetEntityId, int limbOrdinal) implements CustomPacketPayload {

    private static final double MAX_TREAT_DISTANCE_SQ = 64;

    public static final CustomPacketPayload.Type<RemoveTourniquetPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "remove_tourniquet"));

    public static final StreamCodec<ByteBuf, RemoveTourniquetPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, RemoveTourniquetPayload::targetEntityId,
            ByteBufCodecs.VAR_INT, RemoveTourniquetPayload::limbOrdinal,
            RemoveTourniquetPayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RemoveTourniquetPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer user)) return;
            Limb[] limbs = Limb.values();
            if (payload.limbOrdinal() < 0 || payload.limbOrdinal() >= limbs.length) return;
            if (!(user.level().getEntity(payload.targetEntityId()) instanceof ServerPlayer patient)) return;
            if (patient != user && patient.distanceToSqr(user) > MAX_TREAT_DISTANCE_SQ) return;
            if (patient == user && VitalsEventHandler.isIncapacitated(user)) return;

            Limb limb = limbs[payload.limbOrdinal()];
            LimbHealth health = patient.getData(ModAttachments.LIMB_HEALTH);
            if (!health.hasTourniquet(limb)) return;
            health.setTourniquet(limb, false);
            ItemStack returned = new ItemStack(ModItems.TOURNIQUET.get());
            if (!user.getInventory().add(returned)) user.drop(returned, false);
            VitalsEventHandler.sync(patient);
            if (patient != user) PacketDistributor.sendToPlayer(user, VitalsSyncPayload.from(patient));
        });
    }
}
