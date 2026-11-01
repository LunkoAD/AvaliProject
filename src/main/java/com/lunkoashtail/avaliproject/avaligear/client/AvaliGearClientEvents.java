package com.lunkoashtail.avaliproject.avaligear.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.avaligear.armor.AvaliArmorItem;
import com.lunkoashtail.avaliproject.avaligear.armor.VisorTogglePayload;
import com.lunkoashtail.avaliproject.avaligear.ship.CatalinaEntity;
import com.lunkoashtail.avaliproject.avaligear.ship.ShipControlPayload;
import com.lunkoashtail.avaliproject.entity.client.AvaliModel;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public final class AvaliGearClientEvents {
    private static final EquipmentSlot[] SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private static final List<ModelPart> HIDDEN = new ArrayList<>();

    private AvaliGearClientEvents() {
    }

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        while (AvaliGearClient.TOGGLE_VISOR.consumeClick()) {
            ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
            if (head.getItem() instanceof AvaliArmorItem armor && armor.hasVisor()) {
                PacketDistributor.sendToServer(new VisorTogglePayload());
            }
        }
        boolean piloting = player.getVehicle() instanceof CatalinaEntity ship && ship.getControllingPassenger() == player;
        while (AvaliGearClient.SHIP_TAKEOFF_LAND.consumeClick()) {
            if (piloting) PacketDistributor.sendToServer(new ShipControlPayload(ShipControlPayload.TAKEOFF_OR_LAND));
        }
        while (AvaliGearClient.SHIP_HOVER.consumeClick()) {
            if (piloting) PacketDistributor.sendToServer(new ShipControlPayload(ShipControlPayload.TOGGLE_HOVER));
        }
    }

    @SubscribeEvent
    public static void beforeRender(RenderLivingEvent.Pre<?, ?> event) {
        if (!(event.getEntity() instanceof AvaliEntity avali) || !(event.getRenderer().getModel() instanceof AvaliModel model)) return;
        LivingEntity source = AvaliArmorLayer.source(avali);
        for (EquipmentSlot slot : SLOTS) {
            if (!(source.getItemBySlot(slot).getItem() instanceof AvaliArmorItem armor) || armor.getEquipmentSlot() != slot) continue;
            for (String name : armor.hiddenParts()) {
                ModelPart part = model.parts().part(name);
                if (part != null && part.visible) {
                    part.visible = false;
                    HIDDEN.add(part);
                }
            }
        }
    }

    @SubscribeEvent
    public static void afterRender(RenderLivingEvent.Post<?, ?> event) {
        for (ModelPart part : HIDDEN) part.visible = true;
        HIDDEN.clear();
    }
}
