package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.WoodSet;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
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
                .add(ModBlocks.DURASTEEL_ORE.get())
                .add(ModBlocks.DURASTEEL_DEEPSLATE_ORE.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.TITANIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.AEGISALT_ORE.get())
                .add(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.get())
                .add(ModBlocks.VILOUS_CERAMIC_ORE.get())
                .add(ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.ARCAITES_CRYSTAL_ORE.get())
                .add(ModBlocks.AEGISALT_DEEPSLATE_ORE.get())
                .add(ModBlocks.AVALI_PATTERN_BLOCK_1.get())
                .add(ModBlocks.AVALI_PATTERN_BLOCK_2.get())
                .add(ModBlocks.AVALI_PATTERN_BLOCK_3.get())
                .add(ModBlocks.AVALI_PATTERN_BLOCK_4.get())
                .add(ModBlocks.AVALI_WALL_PATTERN_BLOCK_1.get())
                .add(ModBlocks.AVALI_WALL_PATTERN_BLOCK_2.get())
                .add(ModBlocks.AVALI_WALL_PATTERN_BLOCK_3.get())
                .add(ModBlocks.AVALI_WALL_PATTERN_BLOCK_4.get())
                .add(ModBlocks.AVALI_FABRIC_BLOCK.get())
                .add(ModBlocks.SOFT_TAPESTRY.get())
                .add(ModBlocks.PROTOGEN_SUPPORT_BLOCK.get())
                .add(ModBlocks.DETAILED_PROTOGEN_BLOCK.get())
                .add(ModBlocks.AGATE_ORE.get())
                .add(ModBlocks.AGATE_DEEPSLATE_ORE.get())
                .add(ModBlocks.NOVULITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.NOVULITE_ORE.get())
                .add(ModBlocks.NOVULITE_BLOCK.get())
                .add(ModBlocks.AGATE_BLOCK.get())
                .add(ModBlocks.CARNELIAN_BLOCK.get())
                .add(ModBlocks.JADEITE_BLOCK.get())
                .add(ModBlocks.ALT_PROTOGEN_SUPPORT_BLOCK.get())
                .add(ModBlocks.ALT_DETAILED_PROTOGEN_BLOCK.get())
                .add(ModBlocks.ALT_AVALI_FABRIC_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.LUME_ORE.get())
                .add(ModBlocks.AERO_CRYSTAL_ORE.get())
                .add(ModBlocks.THERMAL_CRYSTAL_ORE.get())
                .add(ModBlocks.DURASTEEL_ORE.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.VILOUS_CERAMIC_ORE.get())
                .add(ModBlocks.ARCAITES_CRYSTAL_ORE.get())
                .add(ModBlocks.NOVULITE_ORE.get())
                .add(ModBlocks.AGATE_ORE.get())
                .add(ModBlocks.AEGISALT_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.LUME_DEEPSLATE_ORE.get())
                .add(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.DURASTEEL_DEEPSLATE_ORE.get())
                .add(ModBlocks.TITANIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.get())
                .add(ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.NOVULITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.AGATE_DEEPSLATE_ORE.get())
                .add(ModBlocks.AEGISALT_DEEPSLATE_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/aegisalt")))
                .add(ModBlocks.AEGISALT_ORE.get())
                .add(ModBlocks.AEGISALT_DEEPSLATE_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/vilousclay")))
                .add(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.get())
                .add(ModBlocks.VILOUS_CERAMIC_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/durasteel")))
                .add(ModBlocks.DURASTEEL_DEEPSLATE_ORE.get())
                .add(ModBlocks.DURASTEEL_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/titanium")))
                .add(ModBlocks.TITANIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.TITANIUM_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/agate")))
                .add(ModBlocks.AGATE_DEEPSLATE_ORE.get())
                .add(ModBlocks.AGATE_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/novulite")))
                .add(ModBlocks.NOVULITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.NOVULITE_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/lume")))
                .add(ModBlocks.LUME_DEEPSLATE_ORE.get())
                .add(ModBlocks.LUME_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/arcites")))
                .add(ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.ARCAITES_CRYSTAL_ORE.get());
        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/aerocrystal")))
                .add(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.AERO_CRYSTAL_ORE.get());
        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/thermalcrystal")))
                .add(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.THERMAL_CRYSTAL_ORE.get());
        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.AGATE_BLOCK.get())
                .add(ModBlocks.CARNELIAN_BLOCK.get())
                .add(ModBlocks.JADEITE_BLOCK.get())
                .add(ModBlocks.LUME_BLOCK.get())
                .add(ModBlocks.NOVULITE_BLOCK.get());


        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.CRYSTALITHIUM_ORE.get())
                .add(ModBlocks.CRYSTALITHIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.MOONSTONE_ORE.get())
                .add(ModBlocks.MOONSTONE_DEEPSLATE_ORE.get())
                .add(ModBlocks.SUNSTONE_ORE.get())
                .add(ModBlocks.SUNSTONE_DEEPSLATE_ORE.get())
                .add(ModBlocks.LARIMAR_ORE.get())
                .add(ModBlocks.LARIMAR_DEEPSLATE_ORE.get())
                .add(ModBlocks.CORAL_CULTIVATION_BLOCK.get())
                .add(ModBlocks.MOONLIT_OFFERING_ALTAR.get())
                .add(ModBlocks.SYNCH_POD.get())
                .add(ModBlocks.GROWTH_VAT.get())
                .add(ModBlocks.PROTOGEN_RESEARCH_STATION.get())
                .add(ModBlocks.PROTOGEN_MINING_STATION.get())
                .add(ModBlocks.PROTOGEN_FARMING_STATION.get())
                .add(ModBlocks.PROTOGEN_GENERATOR.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.CRYSTALITHIUM_ORE.get())
                .add(ModBlocks.CRYSTALITHIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.MOONSTONE_ORE.get())
                .add(ModBlocks.MOONSTONE_DEEPSLATE_ORE.get())
                .add(ModBlocks.SUNSTONE_ORE.get())
                .add(ModBlocks.SUNSTONE_DEEPSLATE_ORE.get())
                .add(ModBlocks.LARIMAR_ORE.get())
                .add(ModBlocks.LARIMAR_DEEPSLATE_ORE.get());

        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.COCONUT.get()).add(ModBlocks.DRIFTWOOD.get());
        for (WoodSet set : ModBlocks.WOOD_SETS) {
            set.all().forEach(block -> tag(BlockTags.MINEABLE_WITH_AXE).add(block.get()));
            set.logs().forEach(block -> tag(set.logsBlockTag).add(block.get()));
            tag(BlockTags.LOGS_THAT_BURN).addTag(set.logsBlockTag);
            tag(BlockTags.PLANKS).add(set.planks.get());
            tag(BlockTags.WOODEN_STAIRS).add(set.stairs.get());
            tag(BlockTags.WOODEN_SLABS).add(set.slab.get());
            tag(BlockTags.WOODEN_FENCES).add(set.fence.get());
            tag(BlockTags.FENCE_GATES).add(set.fenceGate.get());
            tag(BlockTags.WOODEN_BUTTONS).add(set.button.get());
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(set.pressurePlate.get());
            tag(BlockTags.STANDING_SIGNS).add(set.sign.get());
            tag(BlockTags.WALL_SIGNS).add(set.wallSign.get());
            tag(BlockTags.CEILING_HANGING_SIGNS).add(set.hangingSign.get());
            tag(BlockTags.WALL_HANGING_SIGNS).add(set.wallHangingSign.get());
        }

        tag(BlockTags.LEAVES)
                .add(ModBlocks.PALM_LEAVES.get())
                .add(ModBlocks.PASSION_FRUIT_LEAVES.get())
                .add(ModBlocks.PAPAYA_LEAVES.get());

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.PALM_LEAVES.get())
                .add(ModBlocks.PASSION_FRUIT_LEAVES.get())
                .add(ModBlocks.PAPAYA_LEAVES.get());

        tag(BlockTags.SAPLINGS)
                .add(ModBlocks.PASSION_FRUIT_SAPLING.get())
                .add(ModBlocks.PAPAYA_SAPLING.get());



        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/crystalithium")))
                .add(ModBlocks.CRYSTALITHIUM_ORE.get())
                .add(ModBlocks.CRYSTALITHIUM_DEEPSLATE_ORE.get());
        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/moonstone")))
                .add(ModBlocks.MOONSTONE_ORE.get())
                .add(ModBlocks.MOONSTONE_DEEPSLATE_ORE.get());
        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/sunstone")))
                .add(ModBlocks.SUNSTONE_ORE.get())
                .add(ModBlocks.SUNSTONE_DEEPSLATE_ORE.get());
        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/larimar")))
                .add(ModBlocks.LARIMAR_ORE.get())
                .add(ModBlocks.LARIMAR_DEEPSLATE_ORE.get());

        tag(ModTags.Blocks.NEEDS_AEROGEL_TOOL)
                .addTag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/durasteel")))
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_AEROGEL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL);

        tag(ModTags.Blocks.NEEDS_HARDLIGHT_TOOL)
                .addTag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/durasteel")))
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_HARDLIGHT_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL);

        tag(ModTags.Blocks.NEEDS_CERAMIC_TOOL)
                .addTag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores/durasteel")))
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_CERAMIC_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL);

        tag(com.lunkoashtail.avaliproject.vitals.Radiation.RADIOACTIVE_BLOCKS)
                .add(ModBlocks.THERMAL_CRYSTAL_ORE.get())
                .add(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.AERO_CRYSTAL_ORE.get())
                .add(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.ARCAITES_CRYSTAL_ORE.get())
                .add(ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.CRYSTALITHIUM_ORE.get())
                .add(ModBlocks.CRYSTALITHIUM_DEEPSLATE_ORE.get());

        tag(ModTags.Blocks.EXPIE_COMFORT)
                .addTag(BlockTags.BEDS)
                .addTag(BlockTags.FLOWERS)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.WOOL)
                .addTag(BlockTags.WOOL_CARPETS)
                .addTag(BlockTags.DOORS)
                .addTag(BlockTags.CAMPFIRES)
                .addTag(BlockTags.CANDLES)
                .addTag(Tags.Blocks.GLASS_BLOCKS)
                .addTag(Tags.Blocks.GLASS_PANES)
                .add(Blocks.LANTERN)
                .add(Blocks.SOUL_LANTERN)
                .add(ModBlocks.EXPIE_PLUSH.get());
    }
}
