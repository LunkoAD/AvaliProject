package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.CustomSkins;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesCosmeticData;
import com.lunkoashtail.avaliproject.species.SpeciesCosmetics;
import com.lunkoashtail.avaliproject.species.SpeciesUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpeciesWardrobeSelectPayload(String speciesId, String variantId) implements CustomPacketPayload {

    public static final String RESET = "";

    public static final CustomPacketPayload.Type<SpeciesWardrobeSelectPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "species_wardrobe_select"));

    public static final StreamCodec<ByteBuf, SpeciesWardrobeSelectPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SpeciesWardrobeSelectPayload::speciesId,
            ByteBufCodecs.STRING_UTF8, SpeciesWardrobeSelectPayload::variantId,
            SpeciesWardrobeSelectPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SpeciesWardrobeSelectPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            Species species = player.getData(ModAttachments.SPECIES);
            if (species == Species.HUMAN || !SpeciesUtil.id(species).equals(payload.speciesId())) return;

            SpeciesCosmeticData data = player.getData(ModAttachments.SPECIES_COSMETICS);
            if (payload.variantId().equals(RESET)) {
                player.setData(ModAttachments.SPECIES_COSMETICS, data.without(species));
            } else {
                if (SpeciesCosmetics.find(species, player.getData(ModAttachments.MANOKIT_SUBSPECIES), payload.variantId()).isEmpty()) {
                    return;
                }
                player.setData(ModAttachments.SPECIES_COSMETICS, data.with(species, payload.variantId()));
            }
            String folder = CustomSkins.folder(species, player.getData(ModAttachments.MANOKIT_SUBSPECIES));
            CustomSkins.Data custom = player.getData(ModAttachments.CUSTOM_SKINS);
            if (custom.byFolder().containsKey(folder)) {
                player.setData(ModAttachments.CUSTOM_SKINS, custom.without(folder));
                CustomSkinSyncPayload.broadcast(player, folder);
            }
            SpeciesUtil.sync(player);
        });
    }
}
