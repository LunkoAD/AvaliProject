package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.WoodSet;
import com.lunkoashtail.avaliproject.block.custom.GroouCropBlock;
import com.lunkoashtail.avaliproject.block.custom.ProtogenColor;
import com.lunkoashtail.avaliproject.block.custom.ShellCoverBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.core.Direction;
import com.lunkoashtail.avaliproject.block.custom.KiriCropBlock;
import com.lunkoashtail.avaliproject.block.custom.NakatiCropBlock;
import com.lunkoashtail.avaliproject.block.custom.PiruCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
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
        blockWithItem(ModBlocks.ALT_PROTOGEN_SUPPORT_BLOCK);
        blockWithItem(ModBlocks.ALT_DETAILED_PROTOGEN_BLOCK);
        blockWithItem(ModBlocks.ALT_AVALI_FABRIC_BLOCK);
        blockWithItem(ModBlocks.AVALI_CARVING_1);
        blockWithItem(ModBlocks.AVALI_CARVING_2);
        blockWithItem(ModBlocks.AVALI_CARVING_3);
        blockWithItem(ModBlocks.AVALI_CARVING_4);
        blockWithItem(ModBlocks.AVALI_CARVING_5);
        blockWithItem(ModBlocks.AVALI_NIGHTLY_FABRIC_1);
        blockWithItem(ModBlocks.AVALI_NIGHTLY_FABRIC_2);
        blockWithItem(ModBlocks.AVALI_NIGHTLY_FABRIC_3);
        blockWithItem(ModBlocks.AVALI_NIGHTLY_FABRIC_4);
        blockWithItem(ModBlocks.AVALI_NIGHTLY_FABRIC_5);
        blockWithItem(ModBlocks.AVALI_NIGHTLY_FABRIC_6);
        blockWithItem(ModBlocks.VILOUS_CERAMIC_BLOCK);

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

        blockWithItem(ModBlocks.CONTAINER_CRATE);

        simpleBlock(ModBlocks.HYDREED.get(),
                models().cross("hydreed", ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "block/hydreed")).renderType("cutout"));
        simpleBlock(ModBlocks.GLOWPLANT.get(),
                models().cross("glowplant", ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "block/glowplant")).renderType("cutout"));

        ModelFile geotreeBottom = models().cross("geotree_bottom",
                ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "block/geotree_bottom")).renderType("cutout");
        ModelFile geotreeTop = models().cross("geotree_top",
                ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "block/geotree_top")).renderType("cutout");
        getVariantBuilder(ModBlocks.GEOTREE.get()).forAllStates(state ->
                new ConfiguredModel[]{new ConfiguredModel(
                        state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER ? geotreeBottom : geotreeTop)});

        blockWithItem(ModBlocks.CRYSTALITHIUM_ORE);
        blockWithItem(ModBlocks.CRYSTALITHIUM_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.MOONSTONE_ORE);
        blockWithItem(ModBlocks.MOONSTONE_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.SUNSTONE_ORE);
        blockWithItem(ModBlocks.SUNSTONE_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.LARIMAR_ORE);
        blockWithItem(ModBlocks.LARIMAR_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.CORAL_CULTIVATION_BLOCK);
        blockWithItem(ModBlocks.MOONLIT_OFFERING_ALTAR);

        simpleBlockWithItem(ModBlocks.SEA_GLASS.get(), models().withExistingParent("sea_glass", mcLoc("block/carpet"))
                .texture("wool", modLoc("block/sea_glass")).renderType("translucent"));
        blockWithItem(ModBlocks.CARNELIAN_BLOCK);
        blockWithItem(ModBlocks.JADEITE_BLOCK);

        getVariantBuilder(ModBlocks.SHELL_COVER.get()).forAllStatesExcept(state -> {
            String type = state.getValue(ShellCoverBlock.TYPE).getSerializedName();
            return new ConfiguredModel[]{new ConfiguredModel(models()
                    .withExistingParent("shell_cover_" + type, mcLoc("block/carpet"))
                    .texture("wool", modLoc("block/shell_cover_" + type))
                    .renderType("cutout"))};
        }, ShellCoverBlock.WATERLOGGED);

        ModBlocks.WOOD_SETS.forEach(this::woodSet);

        leaves(ModBlocks.PALM_LEAVES);
        leaves(ModBlocks.PASSION_FRUIT_LEAVES);
        leaves(ModBlocks.PAPAYA_LEAVES);

        fruitSapling(ModBlocks.PASSION_FRUIT_SAPLING, "jungle_sapling");
        fruitSapling(ModBlocks.PAPAYA_SAPLING, "acacia_sapling");

        simpleBlockWithItem(ModBlocks.DRIFTWOOD.get(), models().withExistingParent("driftwood", mcLoc("block/mangrove_roots"))
                .renderType("cutout"));

        simpleBlock(ModBlocks.COCONUT.get(), models().withExistingParent("coconut", mcLoc("block/block"))
                .texture("top", modLoc("block/coconut_top"))
                .texture("side", modLoc("block/coconut_side"))
                .texture("particle", modLoc("block/coconut_side"))
                .element().from(4, 0, 4).to(12, 8, 12)
                .allFaces((direction, face) -> face.texture(direction == Direction.UP ? "#top" : "#side")
                        .uvs(0, 0, 16, 16).cullface(direction == Direction.DOWN ? Direction.DOWN : null))
                .end());

        simpleBlock(ModBlocks.WICKER_EGG.get(), models().getExistingFile(modLoc("block/wicker_egg")));
        horizontalBlock(ModBlocks.EXPIE_PLUSH.get(), models().getExistingFile(modLoc("block/expie_plush")));
        protogenMachine(ModBlocks.SYNCH_POD, "synch_pod");
        protogenMachine(ModBlocks.GROWTH_VAT, "growth_vat");
        protogenStation(ModBlocks.PROTOGEN_RESEARCH_STATION);
        protogenStation(ModBlocks.PROTOGEN_MINING_STATION);
        protogenStation(ModBlocks.PROTOGEN_FARMING_STATION);
        protogenStation(ModBlocks.PROTOGEN_GENERATOR);
        simpleBlock(ModBlocks.MACHINE_PART.get(), models().getBuilder("protogen_machine_part")
                .texture("particle", modLoc("block/protogen_support_block")));
    }

    private void protogenStation(DeferredBlock<Block> block) {
        String name = block.getId().getPath();
        simpleBlock(block.get(), models().getBuilder(name).texture("particle", modLoc("block/" + name)));
    }

    private void protogenMachine(DeferredBlock<Block> block, String name) {
        VariantBlockStateBuilder builder = getVariantBuilder(block.get());
        for (ProtogenColor color : ProtogenColor.values()) {
            String variant = name + "_" + color.getSerializedName();
            builder.partialState().with(ProtogenColor.PROPERTY, color)
                    .modelForState().modelFile(models().getBuilder(variant)
                            .texture("particle", modLoc("block/" + name + "/" + variant)))
                    .addModel();
        }
    }

    private void woodSet(WoodSet set) {
        ResourceLocation planks = modLoc("block/" + set.name + "_planks");
        ResourceLocation log = modLoc("block/" + set.name + "_log");

        logBlock((RotatedPillarBlock) set.log.get());
        blockItem(set.log);
        axisBlock((RotatedPillarBlock) set.wood.get(), log, log);
        blockItem(set.wood);

        blockWithItem(set.planks);
        stairsBlock((StairBlock) set.stairs.get(), planks);
        blockItem(set.stairs);
        slabBlock((SlabBlock) set.slab.get(), planks, planks);
        blockItem(set.slab);
        fenceBlock((FenceBlock) set.fence.get(), planks);
        fenceGateBlock((FenceGateBlock) set.fenceGate.get(), planks);
        blockItem(set.fenceGate);
        buttonBlock((ButtonBlock) set.button.get(), planks);
        pressurePlateBlock((PressurePlateBlock) set.pressurePlate.get(), planks);
        blockItem(set.pressurePlate);
        signBlock((StandingSignBlock) set.sign.get(), (WallSignBlock) set.wallSign.get(), planks);
        hangingSignBlock((CeilingHangingSignBlock) set.hangingSign.get(), (WallHangingSignBlock) set.wallHangingSign.get(), log);
    }

    private void leaves(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), models().withExistingParent(block.getId().getPath(), mcLoc("block/leaves"))
                .texture("all", mcLoc("block/dark_oak_leaves")).renderType("cutout_mipped"));
    }

    private void fruitSapling(DeferredBlock<?> block, String vanillaSapling) {
        simpleBlock(block.get(), models().cross(block.getId().getPath(),
                mcLoc("block/" + vanillaSapling)).renderType("cutout"));
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

        
        int age = state.getValue(ageProperty);

        
        models[0] = new ConfiguredModel(models().crop(
                modelName + age,
                ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "block/" + textureName + age)
        ).renderType("cutout"));

        return models;
    }
}