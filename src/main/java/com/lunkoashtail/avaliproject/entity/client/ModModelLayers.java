package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation PENGUIN = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "penguin"), "main");
//    public static final ModelLayerLocation AVALI = new ModelLayerLocation(
//            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali"), "main");
//    public static final ModelLayerLocation PROTOGEN = new ModelLayerLocation(
//            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "protogen"), "main");
}