package com.lunkoashtail.avaliproject.species;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.compat.origins.OriginsCompat;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.network.SpeciesSyncPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Locale;

public final class SpeciesUtil {
    private SpeciesUtil() {
    }

    public static void setSpecies(ServerPlayer player, Species species) {
        applyInternal(player, species);
        OriginsCompat.applySpecies(player, species);
    }

    public static void setSubspecies(ServerPlayer player, ManokitVariant subspecies) {
        applyInternalSubspecies(player, subspecies);
        if (player.getData(ModAttachments.SPECIES) == Species.MANOKIT) {
            OriginsCompat.applySubspecies(player, subspecies);
        }
    }

    public static void applyInternal(ServerPlayer player, Species species) {
        player.setData(ModAttachments.SPECIES, species);
        sync(player);
    }

    public static void applyInternalSubspecies(ServerPlayer player, ManokitVariant subspecies) {
        player.setData(ModAttachments.MANOKIT_SUBSPECIES, subspecies);
        sync(player);
    }

    public static void sync(ServerPlayer player) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(player, SpeciesSyncPayload.of(player));
    }

    public static String id(Species species) {
        return species.name().toLowerCase(Locale.ROOT);
    }

    public static Component displayName(Species species) {
        return Component.translatable("species." + AvaliProject.MOD_ID + "." + id(species));
    }

    public static Component displayName(ManokitVariant subspecies) {
        return Component.translatable("subspecies." + AvaliProject.MOD_ID + ".manokit_" + subspecies.id());
    }
}
