package com.lunkoashtail.avaliproject.client.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.item.custom.HitscanWeapon;
import com.lunkoashtail.avaliproject.network.HitscanFirePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public class WeaponFireHandler {
    @SubscribeEvent
    public static void onInteractionKey(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack())
            return;
        Player player = Minecraft.getInstance().player;
        if (player == null || !(player.getMainHandItem().getItem() instanceof HitscanWeapon))
            return;
        event.setCanceled(true);
        event.setSwingHand(false);
        PacketDistributor.sendToServer(new HitscanFirePayload(player.getXRot(), player.getYRot()));
    }
}
