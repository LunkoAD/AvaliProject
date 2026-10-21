package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.diplomacy.DiplomacyData;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.pack.PackProgression;
import com.lunkoashtail.avaliproject.pack.PackRecord;
import com.lunkoashtail.avaliproject.pack.PackSavedData;
import com.lunkoashtail.avaliproject.species.Species;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

@EventBusSubscriber(modid = AvaliProject.MOD_ID)
public class PackRankEventHandler {
    private static final int COMPANIONSHIP_INTERVAL_TICKS = 6000;
    private static final double COMPANION_RADIUS = 16.0;
    private static final double COMBAT_RADIUS = 24.0;

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (!(victim.level() instanceof ServerLevel level)) return;
        Entity attacker = event.getSource().getEntity();

        ServerPlayer bereavedOwner = null;
        if (victim instanceof AvaliEntity avali && avali.isTame() && avali.getOwnerUUID() != null) {
            bereavedOwner = level.getServer().getPlayerList().getPlayer(avali.getOwnerUUID());
        }

        if (attacker instanceof ServerPlayer killer && isAvali(victim)) {
            boolean packmate = isPackmate(level, killer, victim);
            PackProgression.demote(killer, packmate ? PackProgression.DEMOTE_KILLED_PACKMATE : PackProgression.DEMOTE_KILLED_AVALI,
                    packmate ? "message.avaliproject.pack.rank.killed_packmate" : "message.avaliproject.pack.rank.killed_avali");
            DiplomacyData diplomacy = killer.getData(ModAttachments.DIPLOMACY_DATA);
            killer.setData(ModAttachments.DIPLOMACY_DATA, diplomacy.addRenown(-PackProgression.RENOWN_LOSS_KILLED_AVALI));
            if (bereavedOwner == killer) bereavedOwner = null;
        }

        if (bereavedOwner != null) {
            PackProgression.demote(bereavedOwner, PackProgression.DEMOTE_PACKMATE_LOST, "message.avaliproject.pack.rank.packmate_lost");
        }

        if (victim instanceof Enemy) {
            ServerPlayer credited = creditedPlayer(level, attacker);
            if (credited != null && hasCompanionNearby(credited, COMBAT_RADIUS)) {
                PackProgression.award(credited, PackProgression.XP_HOSTILE_KILL);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % COMPANIONSHIP_INTERVAL_TICKS != 0 || !player.isAlive()) return;
        if (hasCompanionNearby(player, COMPANION_RADIUS)) {
            PackProgression.award(player, PackProgression.XP_COMPANIONSHIP);
        }
    }

    @Nullable
    private static ServerPlayer creditedPlayer(ServerLevel level, @Nullable Entity attacker) {
        if (attacker instanceof ServerPlayer player) return player;
        if (attacker instanceof AvaliEntity avali && avali.isTame() && avali.getOwnerUUID() != null) {
            return level.getServer().getPlayerList().getPlayer(avali.getOwnerUUID());
        }
        return null;
    }

    private static boolean hasCompanionNearby(ServerPlayer player, double radius) {
        return !player.level().getEntitiesOfClass(AvaliEntity.class, player.getBoundingBox().inflate(radius),
                avali -> avali.isAlive() && avali.isOwnedBy(player)).isEmpty();
    }

    private static boolean isAvali(LivingEntity entity) {
        if (entity instanceof AvaliEntity) return true;
        return entity instanceof ServerPlayer player && player.getData(ModAttachments.SPECIES) == Species.AVALI;
    }

    private static boolean isPackmate(ServerLevel level, ServerPlayer killer, LivingEntity victim) {
        Optional<UUID> leader = killer.getData(ModAttachments.PACK_MEMBERSHIP).leaderUUID();
        if (leader.isEmpty()) return false;
        if (victim instanceof ServerPlayer victimPlayer) {
            return leader.equals(victimPlayer.getData(ModAttachments.PACK_MEMBERSHIP).leaderUUID());
        }
        if (victim instanceof AvaliEntity avali && avali.isTame() && avali.getOwnerUUID() != null) {
            UUID owner = avali.getOwnerUUID();
            if (owner.equals(leader.get())) return true;
            PackRecord record = PackSavedData.get(level).get(leader.get());
            return record != null && record.getMemberUUIDs().contains(owner);
        }
        return false;
    }
}
