package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SubspeciesSelectPayload(String subspeciesId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SubspeciesSelectPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "subspecies_select"));

    public static final StreamCodec<ByteBuf, SubspeciesSelectPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SubspeciesSelectPayload::subspeciesId,
            SubspeciesSelectPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SubspeciesSelectPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            if (player.getData(ModAttachments.SPECIES) != Species.MANOKIT) return;
            ManokitVariant subspecies = ManokitVariant.byId(payload.subspeciesId());
            if (!subspecies.id().equals(payload.subspeciesId())) return;
            if (player.getData(ModAttachments.MANOKIT_SUBSPECIES) == subspecies) return;
            SpeciesUtil.setSubspecies(player, subspecies);
        });
    }
}
