package com.lunkoashtail.avaliproject.init;

import com.lunkoashtail.avaliproject.entity.client.ModelCustomModel;
import com.lunkoashtail.avaliproject.entity.client.Modelavali_projectile_Converted;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelCustomModel.LAYER_LOCATION, ModelCustomModel::createBodyLayer);
        event.registerLayerDefinition(Modelavali_projectile_Converted.LAYER_LOCATION, Modelavali_projectile_Converted::createBodyLayer);
    }
}