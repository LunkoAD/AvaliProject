package com.lunkoashtail.avaliproject.item.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.custom.ProtogenColor;
import com.lunkoashtail.avaliproject.block.entity.client.ProtogenMachineModel;
import com.lunkoashtail.avaliproject.block.entity.client.ProtogenStationRenderer;
import com.lunkoashtail.avaliproject.item.custom.ProtogenMachineItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ProtogenMachineItemRenderer extends GeoItemRenderer<ProtogenMachineItem> {
    public ProtogenMachineItemRenderer(String name, boolean colored) {
        super(new GeoModel<>() {
            private final ResourceLocation model = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "geo/" + name + ".geo.json");
            private final ResourceLocation animation = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "animations/" + name + ".animation.json");

            @Override
            public ResourceLocation getModelResource(ProtogenMachineItem animatable) {
                return model;
            }

            @Override
            public ResourceLocation getTextureResource(ProtogenMachineItem animatable) {
                return colored ? ProtogenMachineModel.texture(name, ProtogenColor.CLASSIC) : ProtogenStationRenderer.texture(name);
            }

            @Override
            public ResourceLocation getAnimationResource(ProtogenMachineItem animatable) {
                return animation;
            }
        });
        if (!colored) {
            addRenderLayer(new AutoGlowingGeoLayer<>(this));
        }
    }

    @Override
    public RenderType getRenderType(ProtogenMachineItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
