package com.lunkoashtail.avaliproject.compat;

import com.lunkoashtail.avaliproject.AvaliProject;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIAvaliProject implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "jei_plugin");
    }
}