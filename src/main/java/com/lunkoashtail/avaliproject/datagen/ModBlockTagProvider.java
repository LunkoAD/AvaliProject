package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static java.awt.AWTEventMulticaster.add;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AvaliProject.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.LUME_BLOCK.get())
                .add(ModBlocks.LUME_ORE.get())
                .add(ModBlocks.LUME_DEEPSLATE_ORE.get())
                .add(ModBlocks.AERO_CRYSTAL_ORE.get())
                .add(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.THERMAL_CRYSTAL_ORE.get())
                .add(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.SYNC_CRYSTAL_ORE.get())
                .add(ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.DURASTEEL_ORE.get())
                .add(ModBlocks.DURASTEEL_DEEPSLATE_ORE.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.TITANIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.AEGISALT_ORE.get())
                .add(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.get())
                .add(ModBlocks.VILOUS_CERAMIC_ORE.get())
                .add(ModBlocks.AEGISALT_DEEPSLATE_ORE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.LUME_ORE.get())
                .add(ModBlocks.AERO_CRYSTAL_ORE.get())
                .add(ModBlocks.THERMAL_CRYSTAL_ORE.get())
                .add(ModBlocks.SYNC_CRYSTAL_ORE.get())
                .add(ModBlocks.DURASTEEL_ORE.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.VILOUS_CERAMIC_ORE.get())
                .add(ModBlocks.AEGISALT_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.LUME_DEEPSLATE_ORE.get())
                .add(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.DURASTEEL_DEEPSLATE_ORE.get())
                .add(ModBlocks.TITANIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.get())
                .add(ModBlocks.AEGISALT_DEEPSLATE_ORE.get());


        tag(ModTags.Blocks.NEEDS_AEROGEL_TOOL)
                .add(ModBlocks.DURASTEEL_DEEPSLATE_ORE.get())
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_AEROGEL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL);
    }
}
