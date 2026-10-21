package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.client.ClientPayloadHandlers;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.pack.PackRosterEntry;
import com.lunkoashtail.avaliproject.species.Species;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public record PackDataSyncPayload(String packName, String leaderName, boolean viewerIsLeader,
                                   int rankXp, int lumeBits, List<PackRosterEntry> roster) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PackDataSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "pack_data_sync"));

    private static final StreamCodec<ByteBuf, PackRosterEntry> ENTRY_CODEC = StreamCodec.of(
            (buf, entry) -> {
                UUIDUtil.STREAM_CODEC.encode(buf, entry.id());
                ByteBufCodecs.STRING_UTF8.encode(buf, entry.name());
                ByteBufCodecs.BOOL.encode(buf, entry.isAvali());
                ByteBufCodecs.BOOL.encode(buf, entry.online());
                ByteBufCodecs.BOOL.encode(buf, entry.male());
                ByteBufCodecs.BOOL.encode(buf, entry.baby());
                ByteBufCodecs.INT.encode(buf, entry.health());
                ByteBufCodecs.INT.encode(buf, entry.maxHealth());
                ByteBufCodecs.INT.encode(buf, entry.trust());
                ByteBufCodecs.VAR_INT.encode(buf, entry.species().ordinal());
                ByteBufCodecs.VAR_INT.encode(buf, entry.subspecies().ordinal());
                ByteBufCodecs.STRING_UTF8.encode(buf, entry.skin());
            },
            buf -> new PackRosterEntry(
                    UUIDUtil.STREAM_CODEC.decode(buf),
                    ByteBufCodecs.STRING_UTF8.decode(buf),
                    ByteBufCodecs.BOOL.decode(buf),
                    ByteBufCodecs.BOOL.decode(buf),
                    ByteBufCodecs.BOOL.decode(buf),
                    ByteBufCodecs.BOOL.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    Species.values()[ByteBufCodecs.VAR_INT.decode(buf)],
                    ManokitVariant.values()[ByteBufCodecs.VAR_INT.decode(buf)],
                    ByteBufCodecs.STRING_UTF8.decode(buf))
    );

    public static final StreamCodec<ByteBuf, PackDataSyncPayload> STREAM_CODEC = StreamCodec.of(
            (buf, payload) -> {
                ByteBufCodecs.STRING_UTF8.encode(buf, payload.packName());
                ByteBufCodecs.STRING_UTF8.encode(buf, payload.leaderName());
                ByteBufCodecs.BOOL.encode(buf, payload.viewerIsLeader());
                ByteBufCodecs.INT.encode(buf, payload.rankXp());
                ByteBufCodecs.INT.encode(buf, payload.lumeBits());
                ByteBufCodecs.INT.encode(buf, payload.roster().size());
                for (PackRosterEntry entry : payload.roster()) {
                    ENTRY_CODEC.encode(buf, entry);
                }
            },
            buf -> {
                String packName = ByteBufCodecs.STRING_UTF8.decode(buf);
                String leaderName = ByteBufCodecs.STRING_UTF8.decode(buf);
                boolean viewerIsLeader = ByteBufCodecs.BOOL.decode(buf);
                int rankXp = ByteBufCodecs.INT.decode(buf);
                int lumeBits = ByteBufCodecs.INT.decode(buf);
                int count = ByteBufCodecs.INT.decode(buf);
                List<PackRosterEntry> roster = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    roster.add(ENTRY_CODEC.decode(buf));
                }
                return new PackDataSyncPayload(packName, leaderName, viewerIsLeader, rankXp, lumeBits, roster);
            }
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PackDataSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientPayloadHandlers.handlePackDataSync(payload));
    }
}
