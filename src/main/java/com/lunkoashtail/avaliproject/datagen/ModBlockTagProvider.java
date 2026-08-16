package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
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

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/aegisalt")))
                .add(ModBlocks.AEGISALT_ORE.get())
                .add(ModBlocks.AEGISALT_DEEPSLATE_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/vilousclay")))
                .add(ModBlocks.VILOUS_CERAMIC_DEEPSLATE_ORE.get())
                .add(ModBlocks.VILOUS_CERAMIC_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/durasteel")))
                .add(ModBlocks.DURASTEEL_DEEPSLATE_ORE.get())
                .add(ModBlocks.DURASTEEL_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/titanium")))
                .add(ModBlocks.TITANIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.TITANIUM_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/agate")))
                .add(ModBlocks.AGATE_DEEPSLATE_ORE.get())
                .add(ModBlocks.AGATE_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/novulite")))
                .add(ModBlocks.NOVULITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.NOVULITE_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/lume")))
                .add(ModBlocks.LUME_DEEPSLATE_ORE.get())
                .add(ModBlocks.LUME_ORE.get());

        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/arcites")))
                .add(ModBlocks.ARCAITES_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.ARCAITES_CRYSTAL_ORE.get());
        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/aerocrystal")))
                .add(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.AERO_CRYSTAL_ORE.get());
        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/thermalcrystal")))
                .add(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.THERMAL_CRYSTAL_ORE.get());
        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.AGATE_BLOCK.get())
                .add(ModBlocks.LUME_BLOCK.get())
                .add(ModBlocks.NOVULITE_BLOCK.get());


        tag(ModTags.Blocks.NEEDS_AEROGEL_TOOL)
                .addTag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/durasteel")))
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_AEROGEL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL);

        tag(ModTags.Blocks.NEEDS_HARDLIGHT_TOOL)
                .addTag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/durasteel")))
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_HARDLIGHT_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL);

        tag(ModTags.Blocks.NEEDS_CERAMIC_TOOL)
                .addTag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "ores/durasteel")))
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_CERAMIC_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL);
    }
}
