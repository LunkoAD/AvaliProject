package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.diplomacy.DiplomacyData;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.pack.PerPlayerTrust;
import com.lunkoashtail.avaliproject.screen.custom.AvaliSocialLines;
import com.lunkoashtail.avaliproject.sound.ModSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record AvaliSocializeInteractionPayload(int entityId, int actionOrdinal) implements CustomPacketPayload {
    public static final int ACTION_TALK = 0;
    public static final int ACTION_GOSSIP = 1;
    public static final int ACTION_BE_RUDE = 2;
    public static final int ACTION_FLIRT = 3;
    public static final int ACTION_PLAY = 4;
    public static final int ACTION_JOKE = 5;
    public static final int ACTION_HUG = 6;

    private static final int FLIRT_TRUST_THRESHOLD = 60;
    private static final int SLOW_RAISE_SUCCESS_CHANCE = 75;

    public static final CustomPacketPayload.Type<AvaliSocializeInteractionPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali_socialize_interaction"));

    public static final StreamCodec<ByteBuf, AvaliSocializeInteractionPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, AvaliSocializeInteractionPayload::entityId,
            ByteBufCodecs.INT, AvaliSocializeInteractionPayload::actionOrdinal,
            AvaliSocializeInteractionPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private static int cooldownFor(int actionOrdinal) {
        return switch (actionOrdinal) {
            case ACTION_FLIRT, ACTION_PLAY -> 100;
            case ACTION_JOKE -> 80;
            case ACTION_HUG -> 60;
            default -> 40;
        };
    }

    public static void handle(AvaliSocializeInteractionPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            Entity entity = player.level().getEntity(payload.entityId());
            if (!(entity instanceof AvaliEntity avali)) return;
            if (avali.distanceToSqr(player) > 64) return;
            avali.touchInteraction();

            int cooldownTicks = cooldownFor(payload.actionOrdinal());
            long now = player.level().getGameTime();
            PerPlayerTrust current = avali.getTrustMemory().get(player.getUUID());
            if (current.lastInteractionTick() != 0 && now - current.lastInteractionTick() < cooldownTicks) {
                player.displayClientMessage(Component.translatable("message.avaliproject.avali.on_cooldown"), true);
                return;
            }

            int rawDelta;
            List<String> lines;
            boolean bad_interaction = false;
            switch (payload.actionOrdinal()) {
                case ACTION_BE_RUDE -> {
                    rawDelta = -8;
                    lines = AvaliSocialLines.BE_RUDE;
                    bad_interaction = true;
                }
                case ACTION_FLIRT -> {
                    boolean welcome = current.trust() >= FLIRT_TRUST_THRESHOLD;
                    rawDelta = welcome ? 6 : -6;
                    lines = welcome ? AvaliSocialLines.FLIRT : AvaliSocialLines.FLIRT_TOO_SOON;
                    bad_interaction = !welcome;
                }
                case ACTION_HUG -> {
                    rawDelta = 10;
                    lines = AvaliSocialLines.HUG;
                }
                case ACTION_TALK, ACTION_GOSSIP, ACTION_PLAY, ACTION_JOKE -> {
                    boolean landed = avali.getRandom().nextInt(100) < SLOW_RAISE_SUCCESS_CHANCE;
                    rawDelta = landed ? 2 : 0;
                    lines = landed ? linesFor(payload.actionOrdinal()) : AvaliSocialLines.FAILED;

                }
                default -> {
                    rawDelta = 0;
                    lines = List.of("...");
                }
            }

            DiplomacyData diplomacy = player.getData(ModAttachments.DIPLOMACY_DATA);
            double multiplier = rawDelta > 0 ? diplomacy.rank().trustGainMultiplier() : 1.0;
            int scaledDelta = (int) Math.round(rawDelta * multiplier);
            avali.getTrustMemory().put(player.getUUID(), current.withTrustDelta(scaledDelta, now));

            avali.playHugOrSocializeFeedback(bad_interaction ? ACTION_BE_RUDE : payload.actionOrdinal());
            if (player.level() instanceof ServerLevel serverLevel && payload.actionOrdinal() == ACTION_HUG) {
                serverLevel.sendParticles(ParticleTypes.HEART, avali.getX(), avali.getY() + avali.getBbHeight() + 0.2, avali.getZ(),
                        6, 0.3, 0.2, 0.3, 0.02);
            }

            String line = AvaliSocialLines.random(lines, avali.getRandom());
            player.displayClientMessage(Component.literal(avali.getDisplayName().getString() + " " + line), false);

            AvaliTrustSyncPayload.sendTo(player, avali);
        });
    }

    private static List<String> linesFor(int actionOrdinal) {
        return switch (actionOrdinal) {
            case ACTION_TALK -> AvaliSocialLines.TALK;
            case ACTION_GOSSIP -> AvaliSocialLines.GOSSIP;
            case ACTION_PLAY -> AvaliSocialLines.PLAY;
            case ACTION_JOKE -> AvaliSocialLines.JOKE;
            default -> List.of("...");
        };
    }
}
