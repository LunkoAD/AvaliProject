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
        System.out.println("getting into this dreaded junk for loading data gen tags");
        this.tag(ModTags.Biomes.AVALON_AVALON_CITIES).add(AvalonBiomes.SNOWY_PLAINS);
        this.tag(ModTags.Biomes.AVALON_SHIFTING_ICE).add(AvalonBiomes.SHIFTING_ICE);
        this.tag(ModTags.Biomes.AVALON_PERMAFROST_FORESTS).add(AvalonBiomes.PERMAFROST_FORESTS);
        this.tag(ModTags.Biomes.AVALON_SNOWY_PLAINS).add(AvalonBiomes.SNOWY_PLAINS);

        System.out.println(pProvider.lookup(Registries.BIOME).get());

        System.out.println(pProvider.lookup(Registries.BIOME).get().get(ModTags.Biomes.AVALON_SNOWY_PLAINS));
        this.tag(ModTags.Biomes.AVALON).add(ModTags.Biomes.AVALON_SNOWY_PLAINS).addTag(ModTags.Biomes.AVALON_AVALON_CITIES).addTag(ModTags.Biomes.AVALON_PERMAFROST_FORESTS).addTag(ModTags.Biomes.AVALON_SHIFTING_ICE);
        System.out.println("Success!!!!!!!!!!!");
    }
}
