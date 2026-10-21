package com.lunkoashtail.avaliproject.pack;

import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.network.PackDataSyncPayload;
import com.lunkoashtail.avaliproject.species.Species;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class PackUtil {
    private static final AABB WHOLE_WORLD = new AABB(-3.0E7, -2048, -3.0E7, 3.0E7, 2048, 3.0E7);

    public static UUID getOrCreateLeaderUUID(ServerPlayer player) {
        PackMembership membership = player.getData(ModAttachments.PACK_MEMBERSHIP);
        if (membership.leaderUUID().isPresent())
            return membership.leaderUUID().get();

        UUID self = player.getUUID();
        player.setData(ModAttachments.PACK_MEMBERSHIP, new PackMembership(Optional.of(self)));
        PackSavedData.get((ServerLevel) player.level()).getOrCreate(self, player.getName().getString() + "'s Pack");
        return self;
    }

    public static Set<UUID> allMemberUUIDs(UUID leaderUUID, PackRecord record) {
        Set<UUID> all = new HashSet<>(record.getMemberUUIDs());
        all.add(leaderUUID);
        return all;
    }

    public static List<AvaliEntity> ownedAvali(ServerLevel level, Set<UUID> memberUUIDs) {
        return level.getEntities(EntityTypeTest.forExactClass(AvaliEntity.class), WHOLE_WORLD,
                avali -> avali.isTame() && avali.getOwnerUUID() != null && memberUUIDs.contains(avali.getOwnerUUID()));
    }

    public static int totalPackSize(ServerLevel level, UUID leaderUUID, PackRecord record) {
        Set<UUID> all = allMemberUUIDs(leaderUUID, record);
        return all.size() + ownedAvali(level, all).size();
    }

    public static String resolvePlayerName(MinecraftServer server, UUID uuid) {
        ServerPlayer online = server.getPlayerList().getPlayer(uuid);
        if (online != null)
            return online.getName().getString();
        Optional<GameProfile> cached = server.getProfileCache() != null ? server.getProfileCache().get(uuid) : Optional.empty();
        return cached.map(GameProfile::getName).orElse(uuid.toString().substring(0, 8));
    }

    public static boolean isOnline(MinecraftServer server, UUID uuid) {
        return server.getPlayerList().getPlayer(uuid) != null;
    }

    public static void sendSync(ServerPlayer viewer) {
        ServerLevel level = (ServerLevel) viewer.level();
        UUID leaderUUID = getOrCreateLeaderUUID(viewer);
        PackRecord record = PackSavedData.get(level).getOrCreate(leaderUUID, viewer.getName().getString() + "'s Pack");
        Set<UUID> allMembers = allMemberUUIDs(leaderUUID, record);

        MinecraftServer server = level.getServer();
        List<PackRosterEntry> roster = new ArrayList<>();
        for (UUID member : record.getMemberUUIDs()) {
            ServerPlayer memberPlayer = server.getPlayerList().getPlayer(member);
            Species species = memberPlayer != null ? memberPlayer.getData(ModAttachments.SPECIES) : Species.HUMAN;
            roster.add(PackRosterEntry.forPlayer(member, resolvePlayerName(server, member), memberPlayer != null, species,
                    memberPlayer != null ? memberPlayer.getData(ModAttachments.MANOKIT_SUBSPECIES) : ManokitVariant.DEFAULT,
                    memberPlayer != null ? memberPlayer.getData(ModAttachments.SPECIES_COSMETICS).choice(species).orElse("") : ""));
        }
        for (AvaliEntity avali : ownedAvali(level, allMembers)) {
            int trust = avali.getOwnerUUID() != null ? avali.getTrustMemory().get(avali.getOwnerUUID()).trust() : 0;
            roster.add(PackRosterEntry.forAvali(avali.getUUID(), avali.getDisplayName().getString(),
                    avali.isMale(), avali.isBaby(), (int) avali.getHealth(), (int) avali.getMaxHealth(), trust,
                    avali.getTexture()));
        }

        int lumeBits = 0;
        for (ItemStack stack : viewer.getInventory().items) {
            if (stack.is(ModItems.LUME_BIT.get())) lumeBits += stack.getCount();
        }

        var rankData = viewer.getData(ModAttachments.PACK_RANK_DATA);
        PacketDistributor.sendToPlayer(viewer, new PackDataSyncPayload(
                record.getName(), resolvePlayerName(server, leaderUUID), leaderUUID.equals(viewer.getUUID()),
                rankData.xp(), lumeBits, roster));
    }
}
