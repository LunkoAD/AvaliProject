package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Sent from the client when the syringe minigame succeeds.
 * The server applies the drug effects so they tick down properly
 * and their HUD icons disappear when they expire.
 *
 * drugOrdinal matches SyringeMinigameScreen.DrugType ordinal:
 *   0 = FENTANYL, 1 = HEROIN, 2 = SYRINGE
 */
public record SyringeEffectPayload(int drugOrdinal) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SyringeEffectPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "syringe_effect"));

    public static final StreamCodec<ByteBuf, SyringeEffectPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, SyringeEffectPayload::drugOrdinal,
                    SyringeEffectPayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyringeEffectPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player == null) return;
            switch (payload.drugOrdinal()) {
                case 0 -> { // FENTANYL
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,      600, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1200, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.DARKNESS,          100, 0));
                }
                case 1 -> { // HEROIN
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,      400, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION,         600, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 800, 0));
                }
                case 2 -> { // SYRINGE
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,      200, 0));
                }
            }
        });
    }
}
