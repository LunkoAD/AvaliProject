package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.diplomacy.DiplomacyData;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.pack.PackProgression;
import com.lunkoashtail.avaliproject.pack.PackRecord;
import com.lunkoashtail.avaliproject.pack.PackSavedData;
import com.lunkoashtail.avaliproject.pack.PackUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public record AvaliRecruitPayload(int entityId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<AvaliRecruitPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali_recruit"));

    public static final StreamCodec<ByteBuf, AvaliRecruitPayload> STREAM_CODEC =
            ByteBufCodecs.INT.map(AvaliRecruitPayload::new, AvaliRecruitPayload::entityId);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(AvaliRecruitPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            Entity entity = player.level().getEntity(payload.entityId());
            if (!(entity instanceof AvaliEntity avali)) return;
            if (avali.distanceToSqr(player) > 64 || avali.isTame()) return;
            avali.touchInteraction();

            ServerLevel level = (ServerLevel) player.level();
            UUID leaderUUID = PackUtil.getOrCreateLeaderUUID(player);
            PackRecord record = PackSavedData.get(level).getOrCreate(leaderUUID, player.getName().getString() + "'s Pack");
            if (PackUtil.totalPackSize(level, leaderUUID, record) >= PackRecord.MAX_TOTAL_MEMBERS) {
                player.displayClientMessage(Component.translatable("message.avaliproject.pack.full"), true);
                return;
            }

            DiplomacyData diplomacy = player.getData(ModAttachments.DIPLOMACY_DATA);
            int cost = avali.getScaledRecruitCost(diplomacy);
            if (countLumeBits(player) < cost) {
                player.displayClientMessage(Component.translatable("message.avaliproject.avali.recruit_needs_bits"), true);
                return;
            }

            removeLumeBits(player, cost);
            avali.recruit(player);
            player.setData(ModAttachments.DIPLOMACY_DATA, diplomacy.addRenown(5));
            PackProgression.awardUncapped(player, PackProgression.XP_RECRUIT);
            player.displayClientMessage(Component.translatable("message.avaliproject.avali.recruited"), true);
        });
    }

    private static int countLumeBits(ServerPlayer player) {
        int total = 0;
        for (net.minecraft.world.item.ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.LUME_BIT.get())) total += stack.getCount();
        }
        return total;
    }

    private static void removeLumeBits(ServerPlayer player, int amount) {
        int remaining = amount;
        for (net.minecraft.world.item.ItemStack stack : player.getInventory().items) {
            if (remaining <= 0) break;
            if (stack.is(ModItems.LUME_BIT.get())) {
                int take = Math.min(remaining, stack.getCount());
                stack.shrink(take);
                remaining -= take;
            }
        }
    }
}
