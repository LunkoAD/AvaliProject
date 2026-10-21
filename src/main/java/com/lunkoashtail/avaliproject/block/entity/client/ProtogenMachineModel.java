package com.lunkoashtail.avaliproject.block.entity.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.custom.ProtogenColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class ProtogenMachineModel<T extends BlockEntity & GeoAnimatable> extends GeoModel<T> {
    private final String name;
    private final ResourceLocation model;
    private final ResourceLocation animation;

    public ProtogenMachineModel(String name) {
        this.name = name;
        this.model = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "geo/" + name + ".geo.json");
        this.animation = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "animations/" + name + ".animation.json");
    }

    public static ResourceLocation texture(String name, ProtogenColor color) {
        return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID,
                "textures/block/" + name + "/" + name + "_" + color.getSerializedName() + ".png");
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return texture(name, animatable.getBlockState().getValue(ProtogenColor.PROPERTY));
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animation;
    }
}
