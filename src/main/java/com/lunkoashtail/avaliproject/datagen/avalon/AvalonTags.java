package com.lunkoashtail.avaliproject.datagen.avalon;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class AvalonTags extends BiomeTagsProvider {


    public AvalonTags(PackOutput output,CompletableFuture<HolderLookup.Provider> p_256205_, ExistingFileHelper exFileHelper) {
        super(output, p_256205_, AvaliProject.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider){
        this.tag(ModTags.Biomes.AVALON)
                .addOptional(AvalonBiomes.AVALON_CITIES.location())
                .addOptional(AvalonBiomes.SNOWY_PLAINS.location())
                .addOptional(AvalonBiomes.PERMAFROST_FORESTS.location())
                .addOptional(AvalonBiomes.SHIFTING_ICE.location());
    }
}
