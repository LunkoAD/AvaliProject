package com.lunkoashtail.avaliproject.block;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.custom.*;
import com.lunkoashtail.avaliproject.block.fluid.ModFluids;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.worldgen.ModTreeGrowers;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

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


    public static final DeferredBlock<Block> AVALI_NIGHTLY_FABRIC_1 = registerBlock("avali_nightly_fabric_1",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_NIGHTLY_FABRIC_2 = registerBlock("avali_nightly_fabric_2",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_NIGHTLY_FABRIC_3 = registerBlock("avali_nightly_fabric_3",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_NIGHTLY_FABRIC_4 = registerBlock("avali_nightly_fabric_4",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_NIGHTLY_FABRIC_5 = registerBlock("avali_nightly_fabric_5",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_NIGHTLY_FABRIC_6 = registerBlock("avali_nightly_fabric_6",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AVALI_CARVING_1 = registerBlock("avali_carving_1",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> AVALI_CARVING_2 = registerBlock("avali_carving_2",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> AVALI_CARVING_3 = registerBlock("avali_carving_3",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> AVALI_CARVING_4 = registerBlock("avali_carving_4",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> AVALI_CARVING_5 = registerBlock("avali_carving_5",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));


    public static final DeferredBlock<Block> AGATE_BLOCK = registerBlock("agate_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> CARNELIAN_BLOCK = registerBlock("carnelian_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE)
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> JADEITE_BLOCK = registerBlock("jadeite_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> NOVULITE_BLOCK = registerBlock("novulite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));


    public static final DeferredBlock<Block> NANOLOOM = registerBlock("nanoloom",
            () -> new NanoloomBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> VILOUS_CERAMIC_BLOCK = registerBlock("vilous_ceramic_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));


    public static final DeferredBlock<Block> NAKATI_CROP_BLOCK = BLOCKS.register("nakati_crop_block",
            () -> new NakatiCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> GROOU_CROP_BLOCK = BLOCKS.register("groou_crop_block",
            () -> new GroouCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> KIRI_CROP_BLOCK = BLOCKS.register("kiri_crop_block",
            () -> new KiriCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> PIRU_CROP_BLOCK = BLOCKS.register("piru_crop_block",
            () -> new PiruCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));

    public static final DeferredBlock<Block> GEOTREE = BLOCKS.register("geotree",
            () -> new GeotreeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).strength(1.0f)));
    public static final DeferredBlock<Block> HYDREED = BLOCKS.register("hydreed",
            () -> new HydreedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).strength(1.0f)));
    public static final DeferredBlock<Block> GLOWPLANT = BLOCKS.register("glowplant",
            () -> new GlowplantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).strength(1.0f).lightLevel(state -> 10)));

    public static final DeferredBlock<Block> CONTAINER_CRATE = registerBlock("container_crate",
            () -> new ContainerCrateBlock(BlockBehaviour.Properties.of().strength(2.5f).sound(SoundType.WOOD)));

    public static final DeferredBlock<Block> CRYSTALITHIUM_ORE = registerBlock("crystalithium_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 5),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> CRYSTALITHIUM_DEEPSLATE_ORE = registerBlock("crystalithium_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4.5f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> MOONSTONE_ORE = registerBlock("moonstone_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> MOONSTONE_DEEPSLATE_ORE = registerBlock("moonstone_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4.5f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> SUNSTONE_ORE = registerBlock("sunstone_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SUNSTONE_DEEPSLATE_ORE = registerBlock("sunstone_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4.5f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> LARIMAR_ORE = registerBlock("larimar_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> LARIMAR_DEEPSLATE_ORE = registerBlock("larimar_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(4, 7),
                    BlockBehaviour.Properties.of().strength(4.5f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> SEA_GLASS = registerBlock("sea_glass",
            () -> new SeaGlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.3F)
                    .sound(SoundType.GLASS).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> CORAL_CULTIVATION_BLOCK = registerBlock("coral_cultivation_block",
            () -> new CoralCultivationBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.CORAL_BLOCK).lightLevel(state -> 6).noOcclusion()));
    public static final DeferredBlock<Block> MOONLIT_OFFERING_ALTAR = registerBlock("moonlit_offering_altar",
            () -> new MoonlitOfferingAltarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ)
                    .strength(2.0f).requiresCorrectToolForDrops().sound(SoundType.CALCITE).lightLevel(state -> 4).noOcclusion()));
    public static final DeferredBlock<Block> SHELL_COVER = BLOCKS.register("shell_cover",
            () -> new ShellCoverBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.2f)
                    .sound(SoundType.BONE_BLOCK).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final WoodSet PALM = new WoodSet("palm", MapColor.SAND, MapColor.COLOR_BROWN);
    public static final WoodSet PASSION_FRUIT = new WoodSet("passion_fruit", MapColor.COLOR_PURPLE, MapColor.TERRACOTTA_BROWN);
    public static final WoodSet PAPAYA = new WoodSet("papaya", MapColor.COLOR_ORANGE, MapColor.TERRACOTTA_LIGHT_GRAY);
    public static final List<WoodSet> WOOD_SETS = List.of(PALM, PASSION_FRUIT, PAPAYA);

    public static final DeferredBlock<Block> PALM_LEAVES = registerBlock("palm_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LEAVES)));
    public static final DeferredBlock<Block> COCONUT = BLOCKS.register("coconut",
            () -> new CoconutBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(1.0F)
                    .sound(SoundType.WOOD).noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> PASSION_FRUIT_LEAVES = registerBlock("passion_fruit_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LEAVES)));
    public static final DeferredBlock<Block> PASSION_FRUIT_SAPLING = BLOCKS.register("passion_fruit_sapling",
            () -> new FruitSaplingBlock(ModTreeGrowers.PASSION_FRUIT, false, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_SAPLING)));

    public static final DeferredBlock<Block> PAPAYA_LEAVES = registerBlock("papaya_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LEAVES)));
    public static final DeferredBlock<Block> PAPAYA_SAPLING = BLOCKS.register("papaya_sapling",
            () -> new FruitSaplingBlock(ModTreeGrowers.PAPAYA, false, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_SAPLING)));

    public static final DeferredBlock<Block> DRIFTWOOD = registerBlock("driftwood",
            () -> new MangroveRootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_ROOTS)));

    public static final DeferredBlock<Block> EXPIE_PLUSH = BLOCKS.register("expie_plush",
            () -> new ExpiePlushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(0.3F)
                    .sound(SoundType.WOOL).noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> WICKER_EGG = BLOCKS.register("wicker_egg",
            () -> new WickerEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.5F)
                    .sound(SoundType.DECORATED_POT).noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> SYNCH_POD = BLOCKS.register("synch_pod",
            () -> new SynchPodBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5F)
                    .requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().lightLevel(state -> 7)));

    public static final DeferredBlock<Block> GROWTH_VAT = BLOCKS.register("growth_vat",
            () -> new GrowthVatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5F)
                    .requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().lightLevel(state -> 7)));

    public static final DeferredBlock<Block> PROTOGEN_RESEARCH_STATION = BLOCKS.register("protogen_research_station",
            () -> new ProtogenStationBlock("idle", "active", 32, 24, 16, protogenStationProperties()));

    public static final DeferredBlock<Block> PROTOGEN_MINING_STATION = BLOCKS.register("protogen_mining_station",
            () -> new ProtogenStationBlock("idle", "active", 16, 24, 16, protogenStationProperties()));

    public static final DeferredBlock<Block> PROTOGEN_FARMING_STATION = BLOCKS.register("protogen_farming_station",
            () -> new ProtogenStationBlock("idle", "growing", 16, 24, 16, protogenStationProperties()));

    public static final DeferredBlock<Block> PROTOGEN_GENERATOR = BLOCKS.register("protogen_generator",
            () -> new ProtogenStationBlock("off", "on", 48, 32, 32, protogenStationProperties()));


    private static BlockBehaviour.Properties protogenStationProperties() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5F).requiresCorrectToolForDrops()
                .sound(SoundType.METAL).noOcclusion().lightLevel(state -> state.getValue(ProtogenStationBlock.ACTIVE) ? 10 : 5);
    }

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
