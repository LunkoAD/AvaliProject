package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import com.lunkoashtail.avaliproject.component.SyringeContents;
import com.lunkoashtail.avaliproject.item.custom.DrugType;
import com.lunkoashtail.avaliproject.item.custom.SyringeItem;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;













public record SyringeEffectPayload(int handOrdinal, int drugTypeOrdinal, float injectedAmount, int targetEntityId)
        implements CustomPacketPayload {

    private static final double MAX_TREAT_DISTANCE_SQ = 64;

    public static final CustomPacketPayload.Type<SyringeEffectPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "syringe_effect"));

    public static final StreamCodec<ByteBuf, SyringeEffectPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, SyringeEffectPayload::handOrdinal,
                    ByteBufCodecs.INT, SyringeEffectPayload::drugTypeOrdinal,
                    ByteBufCodecs.FLOAT, SyringeEffectPayload::injectedAmount,
                    ByteBufCodecs.INT, SyringeEffectPayload::targetEntityId,
                    SyringeEffectPayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyringeEffectPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer wielder)) return;

            Entity targetEntity = wielder.level().getEntity(payload.targetEntityId());
            if (!(targetEntity instanceof ServerPlayer targetPlayer)) return;
            boolean self = targetPlayer == wielder;
            if (!self && targetPlayer.distanceToSqr(wielder) > MAX_TREAT_DISTANCE_SQ) return;
            if (!targetPlayer.isAlive()) return;
            if (targetPlayer.getData(ModAttachments.SPECIES) != Species.EXPIE) return;

            InteractionHand hand = payload.handOrdinal() == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            ItemStack syringeStack = wielder.getItemInHand(hand);
            if (!(syringeStack.getItem() instanceof SyringeItem)) return;

            SyringeContents contents = syringeStack.get(ModDataComponents.SYRINGE_CONTENTS);
            if (contents == null) return;

            DrugType[] types = DrugType.values();
            if (payload.drugTypeOrdinal() < 0 || payload.drugTypeOrdinal() >= types.length) return;
            if (contents.drugType() != types[payload.drugTypeOrdinal()]) return;

            float amount = Math.max(0f, Math.min(payload.injectedAmount(), contents.dosage()));
            if (amount <= 0f) return;

            applyEffects(targetPlayer, contents.drugType(), amount);

            float leftover = contents.dosage() - amount;
            if (leftover <= 0.5f) {
                syringeStack.remove(ModDataComponents.SYRINGE_CONTENTS);
            } else {
                syringeStack.set(ModDataComponents.SYRINGE_CONTENTS, contents.withDosage(leftover));
            }
        });
    }

    private static void applyEffects(Player player, DrugType drugType, float amount) {
        int amplifier = amount >= 300f ? 1 : 0;
        float scale = amount / 250f;

        com.lunkoashtail.avaliproject.vitals.Vitals vitals = player.getData(ModAttachments.VITALS);
        switch (drugType) {
            case FENTANYL -> {
                vitals.add(com.lunkoashtail.avaliproject.vitals.Vitals.Stat.OPIOID, amount * 0.3f);
                vitals.add(com.lunkoashtail.avaliproject.vitals.Vitals.Stat.ANALGESIA, amount * 0.3f);
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (400 * scale), amplifier));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (900 * scale), amplifier));
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, (int) (80 * scale), 0));
            }
            case HEROIN -> {
                vitals.add(com.lunkoashtail.avaliproject.vitals.Vitals.Stat.OPIOID, amount * 0.2f);
                vitals.add(com.lunkoashtail.avaliproject.vitals.Vitals.Stat.ANALGESIA, amount * 0.25f);
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (300 * scale), amplifier));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) (450 * scale), amplifier));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (600 * scale), amplifier));
            }
        }
    }
}
