package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record CarryDropPayload() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<CarryDropPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "carry_drop"));

    public static final StreamCodec<ByteBuf, CarryDropPayload> STREAM_CODEC =
            StreamCodec.unit(new CarryDropPayload());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(CarryDropPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            for (Entity passenger : List.copyOf(player.getPassengers())) {
                passenger.stopRiding();
            }
        });
    }
}
