package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AvaliProject.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.LUME_BLOCK);

        blockWithItem(ModBlocks.LUME_ORE);
        blockWithItem(ModBlocks.LUME_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.SYNC_CRYSTAL_ORE);
        blockWithItem(ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.AERO_CRYSTAL_ORE);
        blockWithItem(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.AEGISALT_ORE);
        blockWithItem(ModBlocks.AEGISALT_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.THERMAL_CRYSTAL_ORE);
        blockWithItem(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.DURASTEEL_ORE);
        blockWithItem(ModBlocks.DURASTEEL_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.TITANIUM_ORE);
        blockWithItem(ModBlocks.TITANIUM_DEEPSLATE_ORE);
    }

     private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("avaliproject:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("avaliproject:block/" + deferredBlock.getId().getPath() + appendix));
    }
}
