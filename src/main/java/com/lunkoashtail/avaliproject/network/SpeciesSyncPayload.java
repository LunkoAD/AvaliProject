package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesCosmeticData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;

public record SpeciesSyncPayload(int entityId, int speciesOrdinal, int subspeciesOrdinal,
                                 Map<String, String> cosmetics) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SpeciesSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "species_sync"));

    private static final StreamCodec<ByteBuf, Map<String, String>> COSMETICS_CODEC =
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.STRING_UTF8);

    public static final StreamCodec<ByteBuf, SpeciesSyncPayload> STREAM_CODEC = StreamCodec.of(
            (buf, p) -> {
                ByteBufCodecs.VAR_INT.encode(buf, p.entityId());
                buf.writeInt(p.speciesOrdinal());
                buf.writeInt(p.subspeciesOrdinal());
                COSMETICS_CODEC.encode(buf, p.cosmetics());
            },
            buf -> new SpeciesSyncPayload(ByteBufCodecs.VAR_INT.decode(buf), buf.readInt(), buf.readInt(),
                    COSMETICS_CODEC.decode(buf))
    );

    public static SpeciesSyncPayload of(Player player) {
        return new SpeciesSyncPayload(player.getId(),
                player.getData(ModAttachments.SPECIES).ordinal(),
                player.getData(ModAttachments.MANOKIT_SUBSPECIES).ordinal(),
                player.getData(ModAttachments.SPECIES_COSMETICS).choices());
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SpeciesSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player receiver = context.player();
            if (receiver == null) return;
            Entity entity = receiver.getId() == payload.entityId() ? receiver : receiver.level().getEntity(payload.entityId());
            if (!(entity instanceof Player player)) return;
            Species[] species = Species.values();
            int ord = payload.speciesOrdinal();
            player.setData(ModAttachments.SPECIES, ord >= 0 && ord < species.length ? species[ord] : Species.HUMAN);
            ManokitVariant[] variants = ManokitVariant.values();
            int sub = payload.subspeciesOrdinal();
            player.setData(ModAttachments.MANOKIT_SUBSPECIES, sub >= 0 && sub < variants.length ? variants[sub] : ManokitVariant.DEFAULT);
            player.setData(ModAttachments.SPECIES_COSMETICS, new SpeciesCosmeticData(payload.cosmetics()));
        });
    }
}
