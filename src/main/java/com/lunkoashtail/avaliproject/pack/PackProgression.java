package com.lunkoashtail.avaliproject.pack;

import com.lunkoashtail.avaliproject.limb.ModAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public final class PackProgression {
    public static final int DAILY_XP_CAP = 100;
    private static final long TICKS_PER_DAY = 24000L;

    public static final int XP_RECRUIT = 25;
    public static final int XP_HOSTILE_KILL = 2;
    public static final int XP_COMPANIONSHIP = 4;
    public static final int XP_TRADE = 1;

    public static final int DEMOTE_PACKMATE_LOST = 1;
    public static final int DEMOTE_KILLED_AVALI = 2;
    public static final int DEMOTE_KILLED_PACKMATE = 3;
    public static final int RENOWN_LOSS_KILLED_AVALI = 100;

    private PackProgression() {
    }

    private static long currentDay(ServerPlayer player) {
        return player.serverLevel().getServer().overworld().getGameTime() / TICKS_PER_DAY;
    }

    public static void award(ServerPlayer player, int amount) {
        PackRankData before = player.getData(ModAttachments.PACK_RANK_DATA);
        long day = currentDay(player);
        if (before.remainingToday(day, DAILY_XP_CAP) <= 0) return;
        PackRankData after = before.addCappedXp(amount, day, DAILY_XP_CAP);
        apply(player, before, after);
        if (after.remainingToday(day, DAILY_XP_CAP) <= 0) {
            player.displayClientMessage(Component.translatable("message.avaliproject.pack.rank.daily_cap")
                    .withStyle(ChatFormatting.GRAY), true);
        }
    }

    public static void awardUncapped(ServerPlayer player, int amount) {
        PackRankData before = player.getData(ModAttachments.PACK_RANK_DATA);
        apply(player, before, before.addXp(amount));
    }

    public static void demote(ServerPlayer player, int ranks, String reasonKey) {
        PackRankData before = player.getData(ModAttachments.PACK_RANK_DATA);
        PackRankData after = before.demote(ranks);
        player.setData(ModAttachments.PACK_RANK_DATA, after);
        player.sendSystemMessage(Component.translatable(reasonKey).withStyle(ChatFormatting.DARK_RED));
        player.sendSystemMessage(Component.translatable("message.avaliproject.pack.rank.demoted",
                after.rank().displayName(), before.xp() - after.xp()).withStyle(ChatFormatting.RED));
        player.playNotifySound(SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.0f, 0.6f);
    }

    private static void apply(ServerPlayer player, PackRankData before, PackRankData after) {
        player.setData(ModAttachments.PACK_RANK_DATA, after);
        if (after.rank().ordinal() > before.rank().ordinal()) {
            player.sendSystemMessage(Component.translatable("message.avaliproject.pack.rank.promoted",
                    after.rank().displayName()).withStyle(ChatFormatting.GOLD));
            player.playNotifySound(SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }
}
