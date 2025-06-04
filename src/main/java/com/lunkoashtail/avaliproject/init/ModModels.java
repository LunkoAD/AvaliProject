package com.lunkoashtail.avaliproject.init;

import com.lunkoashtail.avaliproject.entity.client.ModelCustomModel;
import com.lunkoashtail.avaliproject.entity.client.Modelavali_projectile_Converted;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelCustomModel.LAYER_LOCATION, ModelCustomModel::createBodyLayer);
        event.registerLayerDefinition(Modelavali_projectile_Converted.LAYER_LOCATION, Modelavali_projectile_Converted::createBodyLayer);
    }
}