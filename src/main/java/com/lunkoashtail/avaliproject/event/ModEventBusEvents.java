package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.client.ModModelLayers;
import com.lunkoashtail.avaliproject.entity.client.PenguinModel;
import com.lunkoashtail.avaliproject.entity.custom.PenguinEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.PENGUIN, PenguinModel::createBodyLayer);
//        event.registerLayerDefinition(ModModelLayers.PROTOGEN, ProtogenModel::createBodyLayer);
//        event.registerLayerDefinition(ModModelLayers.AVALI, AvaliModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.PENGUIN.get(), PenguinEntity.createAttributes().build());
//      event.put(ModEntities.PROTOGEN.get(), ProtogenEntity.createAttributes().build());
//      event.put(ModEntities.AVALI.get(), AvaliEntity.createAttributes().build());
    }
}