package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.GlassShardEntity;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class GlassBreakEventHandler {
    private static final float SPAWN_CHANCE = 0.9f;
    private static final int MIN_SHARDS = 1;
    private static final int MAX_SHARDS = 3;
    private static final double SCATTER_RADIUS = 0.6;

    @SubscribeEvent
    public static void onGlassBreak(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if(event.getPlayer() == null) return;
        if (event.getPlayer().getData(ModAttachments.SPECIES) != Species.EXPIE) return; //we only want glass shards if the player is playing as an Expie.
        if (!isGlass(event.getState())) return;
        if (level.random.nextFloat() >= SPAWN_CHANCE) return;

        int count = MIN_SHARDS + level.random.nextInt(MAX_SHARDS - MIN_SHARDS + 1);
        double x = event.getPos().getX() + 0.5;
        double y = event.getPos().getY() + 0.2;
        double z = event.getPos().getZ() + 0.5;

        for (int i = 0; i < count; i++) {
            double ox = (level.random.nextDouble() - 0.5) * SCATTER_RADIUS;
            double oz = (level.random.nextDouble() - 0.5) * SCATTER_RADIUS;
            level.addFreshEntity(new GlassShardEntity(level, x + ox, y, z + oz, event.getState()));
        }
    }

    private static boolean isGlass(BlockState state) {
        return BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath().contains("glass");
    }
}
