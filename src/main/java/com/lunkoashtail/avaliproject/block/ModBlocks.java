package com.lunkoashtail.avaliproject.block;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.custom.*;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AvaliProject.MOD_ID);

    public static final DeferredBlock<Block> LUME_BLOCK = registerBlock("lume_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> ARCAITES_CRYSTAL_ORE = registerBlock("arcaites_crystal_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ARCAITES_CRYSTAL_DEEPSLATE_ORE = registerBlock("arcaites_crystal_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> NOVULITE_ORE = registerBlock("novulite_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> NOVULITE_DEEPSLATE_ORE = registerBlock("novulite_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AGATE_ORE = registerBlock("agate_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AGATE_DEEPSLATE_ORE = registerBlock("agate_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> LUME_ORE = registerBlock("lume_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AERO_CRYSTAL_ORE = registerBlock("aero_crystal_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> THERMAL_CRYSTAL_ORE = registerBlock("thermal_crystal_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> LUME_DEEPSLATE_ORE = registerBlock("lume_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> THERMAL_CRYSTAL_DEEPSLATE_ORE = registerBlock("thermal_crystal_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> AERO_CRYSTAL_DEEPSLATE_ORE = registerBlock("aero_crystal_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> SYNC_CRYSTAL_DEEPSLATE_ORE = registerBlock("sync_crystal_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> SYNC_CRYSTAL_ORE = registerBlock("sync_crystal_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> DURASTEEL_DEEPSLATE_ORE = registerBlock("durasteel_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> DURASTEEL_ORE = registerBlock("durasteel_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AEGISALT_DEEPSLATE_ORE = registerBlock("aegisalt_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> AEGISALT_ORE = registerBlock("aegisalt_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> TITANIUM_DEEPSLATE_ORE = registerBlock("titanium_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> TITANIUM_ORE = registerBlock("titanium_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> VILOUS_CERAMIC_ORE = registerBlock("vilous_ceramic_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> VILOUS_CERAMIC_DEEPSLATE_ORE = registerBlock("vilous_ceramic_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> GROOU_NODULE = registerBlock("groou_nodule",
            () -> new FlowerBlock(MobEffects.ABSORPTION, 4, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
    public static final DeferredBlock<Block> POTTED_GROOU_NODULE = BLOCKS.register("potted_groou_nodule",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), GROOU_NODULE, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)));

    public static final DeferredBlock<Block> KIRI_NODULE = registerBlock("kiri_nodule",
            () -> new FlowerBlock(MobEffects.SATURATION, 4, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
    public static final DeferredBlock<Block> POTTED_KIRI_NODULE = BLOCKS.register("potted_kiri_nodule",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), KIRI_NODULE, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)));

    public static final DeferredBlock<Block> NAKATI_NODULE = registerBlock("nakati_nodule",
            () -> new FlowerBlock(MobEffects.LUCK, 4, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
    public static final DeferredBlock<Block> POTTED_NAKATI_NODULE = BLOCKS.register("potted_nakati_nodule",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NAKATI_NODULE, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)));

    public static final DeferredBlock<Block> PIRU_NODULE = registerBlock("piru_nodule",
            () -> new FlowerBlock(MobEffects.SATURATION, 4, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
    public static final DeferredBlock<Block> POTTED_PIRU_NODULE = BLOCKS.register("potted_piru_nodule",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), PIRU_NODULE, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)));


    public static final DeferredBlock<Block> AVALI_WALL_PATTERN_BLOCK_1 = registerBlock("avali_wall_pattern_block_1",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_WALL_PATTERN_BLOCK_2 = registerBlock("avali_wall_pattern_block_2",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_WALL_PATTERN_BLOCK_3 = registerBlock("avali_wall_pattern_block_3",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_WALL_PATTERN_BLOCK_4 = registerBlock("avali_wall_pattern_block_4",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_PATTERN_BLOCK_1 = registerBlock("avali_pattern_block_1",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_PATTERN_BLOCK_2 = registerBlock("avali_pattern_block_2",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_PATTERN_BLOCK_3 = registerBlock("avali_pattern_block_3",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_PATTERN_BLOCK_4 = registerBlock("avali_pattern_block_4",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_FABRIC_BLOCK = registerBlock("avali_fabric_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> ALT_AVALI_FABRIC_BLOCK = registerBlock("alt_avali_fabric_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> SOFT_TAPESTRY = registerBlock("soft_tapestry",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> DETAILED_PROTOGEN_BLOCK = registerBlock("detailed_protogen_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final DeferredBlock<Block> PROTOGEN_SUPPORT_BLOCK = registerBlock("protogen_support_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final DeferredBlock<Block> ALT_DETAILED_PROTOGEN_BLOCK = registerBlock("alt_detailed_protogen_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final DeferredBlock<Block> ALT_PROTOGEN_SUPPORT_BLOCK = registerBlock("alt_protogen_support_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final DeferredBlock<Block> AGATE_BLOCK = registerBlock("agate_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> NOVULITE_BLOCK = registerBlock("novulite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));


    public static final DeferredBlock<Block> NANOLOOM = registerBlock("nanoloom",
            () -> new NanoloomBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));


// Crops
    public static final DeferredBlock<Block> NAKATI_CROP_BLOCK = BLOCKS.register("nakati_crop_block",
            () -> new NakatiCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> GROOU_CROP_BLOCK = BLOCKS.register("groou_crop_block",
            () -> new GroouCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> KIRI_CROP_BLOCK = BLOCKS.register("kiri_crop_block",
            () -> new KiriCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> PIRU_CROP_BLOCK = BLOCKS.register("piru_crop_block",
            () -> new PiruCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
