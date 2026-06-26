package com.lunkoashtail.avaliproject.command;

import com.lunkoashtail.avaliproject.limb.BleedingTier;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.network.LimbDataSyncPayload;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Arrays;

/**
 * /avalibleed — operator command for inspecting and modifying per-limb bleed values.
 *
 * Sub-commands:
 *   add    <player> <limb> <tier>    Set a limb's bleed to the chosen tier's midpoint.
 *   remove <player> <limb>           Clear bleeding on one limb (set to 0).
 *   set    <player> <limb> <0–100>   Set an exact raw bleed value.
 *   clear  <player>                  Clear all limbs at once.
 *   status <player>                  Print every limb's current tier and value.
 *
 * Requires operator permission level 2.
 *
 * Limb keys:   head | left_arm | right_arm | back | left_leg | right_leg
 * Tier names:  minor_bleeding | bleeding | heavy_bleeding | catastrophic_bleeding
 */
public class BleedingCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("avalibleed")
                .requires(src -> src.hasPermission(2))

                // ── add <player> <limb> <tier> ────────────────────────────────────────
                .then(Commands.literal("add")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("limb", StringArgumentType.word())
                                        .suggests((ctx, b) -> suggestLimbs(b))
                                        .then(Commands.argument("tier", StringArgumentType.word())
                                                .suggests((ctx, b) -> suggestTiers(b))
                                                .executes(ctx -> {
                                                    ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
                                                    Limb limb = resolveLimb(ctx.getSource(),
                                                            StringArgumentType.getString(ctx, "limb"));
                                                    if (limb == null) return 0;
                                                    BleedingTier tier = resolveTier(ctx.getSource(),
                                                            StringArgumentType.getString(ctx, "tier"));
                                                    if (tier == null) return 0;

                                                    // Use the tier midpoint so the limb lands squarely inside that tier
                                                    int value = (tier.minBleed + tier.maxBleed) / 2;
                                                    applyAndSync(player, limb, value);

                                                    String msg = "Set " + limb.getDisplayName().getString()
                                                            + " → " + tier.getDisplayName().getString()
                                                            + " (" + value + ") on " + player.getName().getString();
                                                    ctx.getSource().sendSuccess(() -> Component.literal(msg), true);
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )

                // ── remove <player> <limb> ────────────────────────────────────────────
                .then(Commands.literal("remove")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("limb", StringArgumentType.word())
                                        .suggests((ctx, b) -> suggestLimbs(b))
                                        .executes(ctx -> {
                                            ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
                                            Limb limb = resolveLimb(ctx.getSource(),
                                                    StringArgumentType.getString(ctx, "limb"));
                                            if (limb == null) return 0;

                                            applyAndSync(player, limb, 0);

                                            String msg = "Cleared bleeding on " + limb.getDisplayName().getString()
                                                    + " for " + player.getName().getString();
                                            ctx.getSource().sendSuccess(() -> Component.literal(msg), true);
                                            return 1;
                                        })
                                )
                        )
                )

                // ── set <player> <limb> <value 0-100> ────────────────────────────────
                .then(Commands.literal("set")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("limb", StringArgumentType.word())
                                        .suggests((ctx, b) -> suggestLimbs(b))
                                        .then(Commands.argument("value",
                                                IntegerArgumentType.integer(0, LimbData.MAX_BLEED))
                                                .executes(ctx -> {
                                                    ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
                                                    Limb limb = resolveLimb(ctx.getSource(),
                                                            StringArgumentType.getString(ctx, "limb"));
                                                    if (limb == null) return 0;
                                                    int value = IntegerArgumentType.getInteger(ctx, "value");

                                                    applyAndSync(player, limb, value);

                                                    BleedingTier tier = BleedingTier.fromBleedValue(value);
                                                    String tierStr = (tier == null) ? "Healthy"
                                                            : tier.getDisplayName().getString();
                                                    String msg = "Set " + limb.getDisplayName().getString()
                                                            + " to " + value + " (" + tierStr + ")"
                                                            + " for " + player.getName().getString();
                                                    ctx.getSource().sendSuccess(() -> Component.literal(msg), true);
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )

                // ── clear <player> ────────────────────────────────────────────────────
                .then(Commands.literal("clear")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(ctx -> {
                                    ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
                                    LimbData data = player.getData(ModAttachments.LIMB_DATA);
                                    for (Limb l : Limb.values()) data.setBleed(l, 0);
                                    PacketDistributor.sendToPlayer(player, LimbDataSyncPayload.from(data));

                                    String msg = "Cleared all bleeding for " + player.getName().getString();
                                    ctx.getSource().sendSuccess(() -> Component.literal(msg), true);
                                    return 1;
                                })
                        )
                )

                // ── status <player> ───────────────────────────────────────────────────
                .then(Commands.literal("status")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(ctx -> {
                                    ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
                                    LimbData data = player.getData(ModAttachments.LIMB_DATA);

                                    StringBuilder sb = new StringBuilder(
                                            player.getName().getString() + "'s limb status:\n");
                                    for (Limb l : Limb.values()) {
                                        int bleed = data.getBleed(l);
                                        BleedingTier tier = BleedingTier.fromBleedValue(bleed);
                                        String status = (tier == null) ? "Healthy"
                                                : tier.getDisplayName().getString() + " (" + bleed + ")";
                                        sb.append("  ").append(l.getDisplayName().getString())
                                                .append(": ").append(status).append("\n");
                                    }

                                    String report = sb.toString().stripTrailing();
                                    ctx.getSource().sendSuccess(() -> Component.literal(report), false);
                                    return 1;
                                })
                        )
                );
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private static void applyAndSync(ServerPlayer player, Limb limb, int value) {
        LimbData data = player.getData(ModAttachments.LIMB_DATA);
        data.setBleed(limb, value);
        PacketDistributor.sendToPlayer(player, LimbDataSyncPayload.from(data));
    }

    private static Limb resolveLimb(CommandSourceStack src, String key) {
        Limb limb = Limb.fromKey(key);
        if (limb == null) {
            src.sendFailure(Component.literal("Unknown limb '" + key
                    + "'. Valid: " + validLimbs()));
        }
        return limb;
    }

    private static BleedingTier resolveTier(CommandSourceStack src, String name) {
        for (BleedingTier t : BleedingTier.values()) {
            if (t.name().equalsIgnoreCase(name)) return t;
        }
        src.sendFailure(Component.literal("Unknown tier '" + name
                + "'. Valid: " + validTiers()));
        return null;
    }

    private static String validLimbs() {
        StringBuilder sb = new StringBuilder();
        for (Limb l : Limb.values()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(l.key);
        }
        return sb.toString();
    }

    private static String validTiers() {
        StringBuilder sb = new StringBuilder();
        for (BleedingTier t : BleedingTier.values()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(t.name().toLowerCase());
        }
        return sb.toString();
    }

    private static java.util.concurrent.CompletableFuture<com.mojang.brigadier.suggestion.Suggestions>
    suggestLimbs(com.mojang.brigadier.suggestion.SuggestionsBuilder builder) {
        for (Limb l : Limb.values()) builder.suggest(l.key);
        return builder.buildFuture();
    }

    private static java.util.concurrent.CompletableFuture<com.mojang.brigadier.suggestion.Suggestions>
    suggestTiers(com.mojang.brigadier.suggestion.SuggestionsBuilder builder) {
        for (BleedingTier t : BleedingTier.values()) builder.suggest(t.name().toLowerCase());
        return builder.buildFuture();
    }
}
