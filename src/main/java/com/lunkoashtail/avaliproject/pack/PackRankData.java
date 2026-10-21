package com.lunkoashtail.avaliproject.pack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record PackRankData(int xp, long day, int earnedToday) {

    public static final PackRankData INITIAL = new PackRankData(0, 0L, 0);

    private static final Codec<PackRankData> FULL_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("xp").forGetter(PackRankData::xp),
            Codec.LONG.optionalFieldOf("day", 0L).forGetter(PackRankData::day),
            Codec.INT.optionalFieldOf("earned_today", 0).forGetter(PackRankData::earnedToday)
    ).apply(instance, PackRankData::new));

    public static final Codec<PackRankData> CODEC = Codec.withAlternative(FULL_CODEC,
            Codec.INT.xmap(xp -> new PackRankData(xp, 0L, 0), PackRankData::xp));

    public static final StreamCodec<ByteBuf, PackRankData> STREAM_CODEC =
            ByteBufCodecs.INT.map(xp -> new PackRankData(xp, 0L, 0), PackRankData::xp);

    public PackRank rank() {
        return PackRank.forXp(xp);
    }

    public int remainingToday(long currentDay, int dailyCap) {
        return currentDay == day ? Math.max(0, dailyCap - earnedToday) : dailyCap;
    }

    public PackRankData addCappedXp(int amount, long currentDay, int dailyCap) {
        int granted = Math.min(amount, remainingToday(currentDay, dailyCap));
        int earned = (currentDay == day ? earnedToday : 0) + granted;
        return new PackRankData(xp + granted, currentDay, earned);
    }

    public PackRankData addXp(int amount) {
        return new PackRankData(Math.max(0, xp + amount), day, earnedToday);
    }

    public PackRankData demote(int ranks) {
        PackRank target = PackRank.values()[Math.max(0, rank().ordinal() - ranks)];
        return new PackRankData(target.xpThreshold(), day, earnedToday);
    }
}
