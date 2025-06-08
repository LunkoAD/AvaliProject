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

        //TODO: This is dumb. Why do biome tags not exist during this stage of the game?
        //TODO: I'm gonna crash out. - @989omnan
        //System.out.println("getting into this dreaded junk for loading data gen tags");

        //System.out.println(pProvider.lookup(Registries.BIOME).get());

        //System.out.println(pProvider.lookup(Registries.BIOME).get().get(ModTags.Biomes.AVALON_SNOWY_PLAINS));
        //System.out.println("Success!!!!!!!!!!!");
    }
}
