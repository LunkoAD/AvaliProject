package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpeciesSyncPayload(int speciesOrdinal) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SpeciesSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "species_sync"));

    public static final StreamCodec<ByteBuf, SpeciesSyncPayload> STREAM_CODEC = StreamCodec.of(
            (buf, p) -> buf.writeInt(p.speciesOrdinal()),
            buf -> new SpeciesSyncPayload(buf.readInt())
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SpeciesSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player == null) return;
            Species[] values = Species.values();
            int ord = payload.speciesOrdinal();
            Species species = (ord >= 0 && ord < values.length) ? values[ord] : Species.HUMAN;
            player.setData(ModAttachments.SPECIES, species);
        });
    }
}
