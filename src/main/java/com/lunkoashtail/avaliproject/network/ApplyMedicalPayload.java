package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.item.custom.MedicalItem;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.vitals.MedicalAction;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ApplyMedicalPayload(int actionOrdinal, int limbOrdinal, boolean mainHand, int targetEntityId)
        implements CustomPacketPayload {

    private static final double MAX_TREAT_DISTANCE_SQ = 64;

    public static final CustomPacketPayload.Type<ApplyMedicalPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "apply_medical"));

    public static final StreamCodec<ByteBuf, ApplyMedicalPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ApplyMedicalPayload::actionOrdinal,
            ByteBufCodecs.VAR_INT, ApplyMedicalPayload::limbOrdinal,
            ByteBufCodecs.BOOL, ApplyMedicalPayload::mainHand,
            ByteBufCodecs.INT, ApplyMedicalPayload::targetEntityId,
            ApplyMedicalPayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ApplyMedicalPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer user)) return;
            MedicalAction[] actions = MedicalAction.values();
            Limb[] limbs = Limb.values();
            if (payload.actionOrdinal() < 0 || payload.actionOrdinal() >= actions.length) return;
            if (payload.limbOrdinal() < 0 || payload.limbOrdinal() >= limbs.length) return;
            if (!(user.level().getEntity(payload.targetEntityId()) instanceof ServerPlayer patient)) return;
            if (patient != user && patient.distanceToSqr(user) > MAX_TREAT_DISTANCE_SQ) return;
            if (!patient.isAlive()) return;

            InteractionHand hand = payload.mainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            ItemStack stack = user.getItemInHand(hand);
            if (!(stack.getItem() instanceof MedicalItem item)) return;
            MedicalAction action = actions[payload.actionOrdinal()];
            if (!item.supports(action)) return;
            MedicalItem.finish(user, patient, limbs[payload.limbOrdinal()], hand, action);
        });
    }
}
