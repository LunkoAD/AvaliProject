package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.custom.GroouCropBlock;
import com.lunkoashtail.avaliproject.block.custom.KiriCropBlock;
import com.lunkoashtail.avaliproject.block.custom.NakatiCropBlock;
import com.lunkoashtail.avaliproject.block.custom.PiruCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

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
        blockWithItem(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.VILOUS_CERAMIC_ORE);
        blockWithItem(ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.AVALI_FABRIC_BLOCK);
        blockWithItem(ModBlocks.SOFT_TAPESTRY);
        blockWithItem(ModBlocks.DETAILED_PROTOGEN_BLOCK);
        blockWithItem(ModBlocks.PROTOGEN_SUPPORT_BLOCK);
        blockWithItem(ModBlocks.AVALI_PATTERN_BLOCK_1);
        blockWithItem(ModBlocks.AVALI_PATTERN_BLOCK_2);
        blockWithItem(ModBlocks.AVALI_PATTERN_BLOCK_3);
        blockWithItem(ModBlocks.AVALI_PATTERN_BLOCK_4);
        blockWithItem(ModBlocks.AVALI_WALL_PATTERN_BLOCK_1);
        blockWithItem(ModBlocks.AVALI_WALL_PATTERN_BLOCK_3);
        blockWithItem(ModBlocks.AVALI_WALL_PATTERN_BLOCK_2);
        blockWithItem(ModBlocks.AVALI_WALL_PATTERN_BLOCK_4);
        blockWithItem(ModBlocks.ARCAITES_CRYSTAL_ORE);
        blockWithItem(ModBlocks.NOVULITE_ORE);
        blockWithItem(ModBlocks.AGATE_ORE);
        blockWithItem(ModBlocks.NOVULITE_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.AGATE_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.NOVULITE_BLOCK);
        blockWithItem(ModBlocks.AGATE_BLOCK);

        blockWithItem(ModBlocks.NANOLOOM);

        makeCrop(((GroouCropBlock) ModBlocks.GROOU_CROP_BLOCK.get()), "groou_crop_block_stage","groou_crop_block_stage");
        makeCrop(((KiriCropBlock) ModBlocks.KIRI_CROP_BLOCK.get()), "kiri_crop_block_stage","kiri_crop_block_stage");
        makeCrop(((NakatiCropBlock) ModBlocks.NAKATI_CROP_BLOCK.get()), "nakati_crop_block_stage","nakati_crop_block_stage");
        makeCrop(((PiruCropBlock) ModBlocks.PIRU_CROP_BLOCK.get()), "piru_crop_block_stage","piru_crop_block_stage");


        simpleBlock(ModBlocks.GROOU_NODULE.get(),
                models().cross(blockTexture(ModBlocks.GROOU_NODULE.get()).getPath(), blockTexture(ModBlocks.GROOU_NODULE.get())).renderType("cutout"));
        simpleBlock(ModBlocks.POTTED_GROOU_NODULE.get(), models().singleTexture("potted_groou_nodule", ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.GROOU_NODULE.get())).renderType("cutout"));

        simpleBlock(ModBlocks.PIRU_NODULE.get(),
                models().cross(blockTexture(ModBlocks.PIRU_NODULE.get()).getPath(), blockTexture(ModBlocks.PIRU_NODULE.get())).renderType("cutout"));
        simpleBlock(ModBlocks.POTTED_PIRU_NODULE.get(), models().singleTexture("potted_piru_nodule", ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.PIRU_NODULE.get())).renderType("cutout"));

        simpleBlock(ModBlocks.NAKATI_NODULE.get(),
                models().cross(blockTexture(ModBlocks.NAKATI_NODULE.get()).getPath(), blockTexture(ModBlocks.NAKATI_NODULE.get())).renderType("cutout"));
        simpleBlock(ModBlocks.POTTED_NAKATI_NODULE.get(), models().singleTexture("potted_nakati_nodule", ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.NAKATI_NODULE.get())).renderType("cutout"));

        simpleBlock(ModBlocks.KIRI_NODULE.get(),
                models().cross(blockTexture(ModBlocks.KIRI_NODULE.get()).getPath(), blockTexture(ModBlocks.KIRI_NODULE.get())).renderType("cutout"));
        simpleBlock(ModBlocks.POTTED_KIRI_NODULE.get(), models().singleTexture("potted_kiri_nodule", ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.KIRI_NODULE.get())).renderType("cutout"));

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

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }
    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];

        // Dynamically get the age property for various crop types
        IntegerProperty ageProperty;
        if (block instanceof GroouCropBlock) {
            ageProperty = ((GroouCropBlock) block).getAgeProperty();
        } else if (block instanceof PiruCropBlock) {
            ageProperty = ((PiruCropBlock) block).getAgeProperty();
        } else if (block instanceof NakatiCropBlock) {
            ageProperty = ((NakatiCropBlock) block).getAgeProperty();
        } else if (block instanceof KiriCropBlock) {
            ageProperty = ((KiriCropBlock) block).getAgeProperty();
        } else {
            throw new IllegalArgumentException("Unsupported crop block: " + block.getClass().getName());
        }

        // Get the age value from the block state
        int age = state.getValue(ageProperty);

        // Define the model for this age
        models[0] = new ConfiguredModel(models().crop(
                modelName + age,
                ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "block/" + textureName + age)
        ).renderType("cutout"));

        return models;
    }
}