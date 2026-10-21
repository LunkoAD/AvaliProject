package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = AvaliProject.MOD_ID)
public class ExpieDialogueEventHandler {
    private static final double NEARBY_RADIUS = 10.0;

    @SubscribeEvent
    public static void onBlockMined(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (event.getPlayer() == null) return;

        AABB area = new AABB(event.getPos()).inflate(NEARBY_RADIUS);
        for (ExpieEntity expie : level.getEntitiesOfClass(ExpieEntity.class, area)) {
            expie.getDialogue().onNearbyBlockMined(event.getPlayer());
        }
    }
}
