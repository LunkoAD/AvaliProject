package com.lunkoashtail.avaliproject.datagen.avalon;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class AvalonTags extends BiomeTagsProvider {


    public AvalonTags(PackOutput output,CompletableFuture<HolderLookup.Provider> p_256205_, ExistingFileHelper exFileHelper) {
        super(output, p_256205_, AvaliProject.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider){
        this.tag(ModTags.AVALON).add(AvalonBiomes.AVALON_CITIES).add(AvalonBiomes.SNOWY_PLAINS).add(AvalonBiomes.PERMAFROST_FORESTS).add(AvalonBiomes.SHIFTING_ICE);
    }
}
