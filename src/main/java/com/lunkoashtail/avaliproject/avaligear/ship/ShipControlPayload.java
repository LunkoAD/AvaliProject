package com.lunkoashtail.avaliproject.avaligear.ship;

import com.lunkoashtail.avaliproject.avaligear.AvaliGear;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ShipControlPayload(int action) implements CustomPacketPayload {
    public static final int TAKEOFF_OR_LAND = 0;
    public static final int TOGGLE_HOVER = 1;

    public static final Type<ShipControlPayload> TYPE = new Type<>(AvaliGear.id("ship_control"));
    public static final StreamCodec<ByteBuf, ShipControlPayload> STREAM_CODEC =
            ByteBufCodecs.VAR_INT.map(ShipControlPayload::new, ShipControlPayload::action);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ShipControlPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            if (!(player.getVehicle() instanceof CatalinaEntity ship) || ship.getControllingPassenger() != player) return;
            switch (payload.action()) {
                case TAKEOFF_OR_LAND -> ship.toggleFlight(player);
                case TOGGLE_HOVER -> ship.toggleHover();
                default -> {
                }
            }
        });
    }
}
