package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@EventBusSubscriber(modid = AvaliProject.MOD_ID)
public class CarryEventHandler {

    private static final ResourceLocation CARRY_SPEED_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "carry_speed_penalty");

    private static final AttributeModifier CARRY_SPEED_MODIFIER = new AttributeModifier(
            CARRY_SPEED_MODIFIER_ID, -0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    private static final double BACK_OFFSET_UP = 1.1;
    private static final double BACK_OFFSET_BEHIND = 0.2;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        updateSpeedModifier(player);

        if (!player.getPassengers().isEmpty()) {
            for (Entity passenger : List.copyOf(player.getPassengers())) {
                if (!isStillValidPassenger(player, passenger)) {
                    passenger.stopRiding();
                    continue;
                }
                repositionOnBack(player, passenger);
            }
        }

        if (player.isPassenger() && player.getVehicle() instanceof Player carrier
                && (!carrier.isAlive() || carrier.level() != player.level())) {
            player.stopRiding();
        }
    }

    private static boolean isStillValidPassenger(ServerPlayer carrier, Entity passenger) {
        if (!passenger.isAlive() || passenger.level() != carrier.level()) return false;
        return !(passenger instanceof LivingEntity living) || !living.isDeadOrDying();
    }

    private static void updateSpeedModifier(ServerPlayer player) {
        AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) return;

        boolean carrying = !player.getPassengers().isEmpty();
        boolean hasModifier = speed.hasModifier(CARRY_SPEED_MODIFIER_ID);
        if (carrying && !hasModifier) {
            speed.addTransientModifier(CARRY_SPEED_MODIFIER);
        } else if (!carrying && hasModifier) {
            speed.removeModifier(CARRY_SPEED_MODIFIER_ID);
        }
    }

    private static void repositionOnBack(Player carrier, Entity passenger) {
        double yawRad = Math.toRadians(carrier.getYRot());
        double x = carrier.getX() - Math.sin(yawRad) * BACK_OFFSET_BEHIND;
        double z = carrier.getZ() + Math.cos(yawRad) * BACK_OFFSET_BEHIND;
        double y = carrier.getY() + BACK_OFFSET_UP;

        passenger.setPos(x, y, z);
        passenger.setYRot(carrier.getYRot());
        passenger.setYHeadRot(carrier.getYRot());
        passenger.yRotO = carrier.getYRot();
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        dismountEverything(entity);
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        dismountEverything(event.getEntity());
    }

    private static void dismountEverything(Entity entity) {
        for (Entity passenger : List.copyOf(entity.getPassengers())) {
            passenger.stopRiding();
        }
        if (entity.isPassenger()) {
            entity.stopRiding();
        }
    }
}
