package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenRegistrar;
import com.lunkoashtail.avaliproject.entity.custom.ProtogenEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ProtogenAssignPayload(BlockPos pos, int entityId) implements CustomPacketPayload {
    public static final int UNASSIGN = -1;

    public static final CustomPacketPayload.Type<ProtogenAssignPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "protogen_assign"));

    public static final StreamCodec<ByteBuf, ProtogenAssignPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, ProtogenAssignPayload::pos,
            ByteBufCodecs.VAR_INT, ProtogenAssignPayload::entityId,
            ProtogenAssignPayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ProtogenAssignPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            Vec3 center = Vec3.atCenterOf(payload.pos());
            if (player.distanceToSqr(center) > 64 || !player.level().isLoaded(payload.pos())) return;
            if (!(player.level().getBlockEntity(payload.pos()) instanceof ProtogenRegistrar registrar)) return;
            if (payload.entityId() == UNASSIGN) {
                registrar.unregisterProtogen(player);
                return;
            }
            if (!(player.level().getEntity(payload.entityId()) instanceof ProtogenEntity protogen)) return;
            if (!protogen.isAlive() || !protogen.isOwnedBy(player)) return;
            double radius = ProtogenRegistrar.ROSTER_RADIUS + 1;
            if (protogen.distanceToSqr(center) > radius * radius) return;
            registrar.registerProtogen(player, protogen);
        });
    }
}
