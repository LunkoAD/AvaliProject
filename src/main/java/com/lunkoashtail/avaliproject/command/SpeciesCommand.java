package com.lunkoashtail.avaliproject.command;

import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.network.SpeciesSyncPayload;
import com.lunkoashtail.avaliproject.species.Species;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SpeciesCommand {

    private static final SuggestionProvider<CommandSourceStack> SPECIES_SUGGESTIONS =
            (ctx, builder) -> SharedSuggestionProvider.suggest(
                    Arrays.stream(Species.values())
                            .map(s -> s.name().toLowerCase())
                            .collect(Collectors.toList()),
                    builder);

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("species")
                .then(Commands.literal("set")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .suggests(SPECIES_SUGGESTIONS)
                                .executes(ctx -> {
                                    String input = StringArgumentType.getString(ctx, "name");
                                    Species species = Species.fromName(input);
                                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                                    applySpecies(player, species);
                                    ctx.getSource().sendSuccess(
                                            () -> Component.literal("Species set to " + species.name().toLowerCase() + "."),
                                            false);
                                    return 1;
                                })))
                .then(Commands.literal("reset")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            applySpecies(player, Species.HUMAN);
                            ctx.getSource().sendSuccess(
                                    () -> Component.literal("Species reset to human."),
                                    false);
                            return 1;
                        }));
    }

    private static void applySpecies(ServerPlayer player, Species species) {
        player.setData(ModAttachments.SPECIES, species);
        PacketDistributor.sendToPlayer(player, new SpeciesSyncPayload(species.ordinal()));
    }
}
