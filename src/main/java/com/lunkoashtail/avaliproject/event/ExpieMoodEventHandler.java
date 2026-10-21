package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;

@EventBusSubscriber(modid = AvaliProject.MOD_ID)
public class ExpieMoodEventHandler {
    private static final double WITNESS_RADIUS = 20.0;
    private static final float WITNESS_DEATH_MOOD_LOSS = 15f;

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        AABB area = player.getBoundingBox().inflate(WITNESS_RADIUS);
        List<ExpieEntity> nearby = player.level().getEntitiesOfClass(ExpieEntity.class, area);
        for (ExpieEntity expie : nearby) {
            expie.getData(ModAttachments.EXPIE_MOOD).add(-WITNESS_DEATH_MOOD_LOSS);
        }
    }
}
