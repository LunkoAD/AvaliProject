package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.effect.ModEffects;
import com.lunkoashtail.avaliproject.effect.PackLossEffect;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.lunkoashtail.avaliproject.pack.PackRecord;
import com.lunkoashtail.avaliproject.pack.PackSavedData;
import com.lunkoashtail.avaliproject.pack.PackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = AvaliProject.MOD_ID)
public class PackLossEventHandler {
    private record Carried(int amplifier, int duration) {
    }

    private static final Map<UUID, Carried> CARRIED_OVER_DEATH = new HashMap<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.isCanceled()) return;
        if (event.getEntity() instanceof ServerPlayer player) {
            MobEffectInstance packLoss = player.getEffect(ModEffects.PACK_LOSS);
            if (packLoss != null) {
                CARRIED_OVER_DEATH.put(player.getUUID(), new Carried(packLoss.getAmplifier(), packLoss.getDuration()));
            }
            return;
        }
        if (!(event.getEntity() instanceof AvaliEntity victim) || !(victim.level() instanceof ServerLevel level)) return;
        if (!victim.isTame() || victim.getOwnerUUID() == null) return;
        afflictPack(level.getServer(), victim, packOf(level, victim.getOwnerUUID()));
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        Carried carried = CARRIED_OVER_DEATH.remove(player.getUUID());
        if (carried != null) PackLossEffect.restore(player, carried.amplifier(), carried.duration());
    }

    private static Set<UUID> packOf(ServerLevel level, UUID owner) {
        for (Map.Entry<UUID, PackRecord> pack : PackSavedData.get(level).getPacksByLeader().entrySet()) {
            if (pack.getKey().equals(owner) || pack.getValue().getMemberUUIDs().contains(owner)) {
                return PackUtil.allMemberUUIDs(pack.getKey(), pack.getValue());
            }
        }
        return Set.of(owner);
    }

    private static void afflictPack(MinecraftServer server, AvaliEntity victim, Set<UUID> members) {
        for (UUID member : members) {
            ServerPlayer player = server.getPlayerList().getPlayer(member);
            if (player == null) continue;
            PackLossEffect.intensify(player);
            int level = player.getEffect(ModEffects.PACK_LOSS).getAmplifier() + 1;
            player.sendSystemMessage(Component.translatable("message.avaliproject.pack.loss",
                    victim.getDisplayName(), Component.translatable("enchantment.level." + level))
                    .withStyle(ChatFormatting.DARK_PURPLE));
        }
        for (ServerLevel level : server.getAllLevels()) {
            for (AvaliEntity packmate : PackUtil.ownedAvali(level, members)) {
                if (packmate != victim && packmate.isAlive()) PackLossEffect.intensify(packmate);
            }
        }
    }
}
