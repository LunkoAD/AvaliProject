package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.LUME_BLOCK.get());

        add(ModBlocks.AEGISALT_ORE.get(),
                block -> createOreDrop(ModBlocks.AEGISALT_ORE.get(), ModItems.RAW_AEGISALT.get()));
        add(ModBlocks.AEGISALT_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.AEGISALT_DEEPSLATE_ORE.get(), ModItems.RAW_AEGISALT.get(), 2, 3));
        add(ModBlocks.LUME_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.LUME_ORE.get(), ModItems.LUME_BIT.get(), 2, 3));
        add(ModBlocks.LUME_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.LUME_DEEPSLATE_ORE.get(), ModItems.LUME_BIT.get(), 4, 6));
        add(ModBlocks.AERO_CRYSTAL_ORE.get(),
                block -> createOreDrop(ModBlocks.AERO_CRYSTAL_ORE.get(), ModItems.AERO_CRYSTAL.get()));
        add(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.AERO_CRYSTAL_DEEPSLATE_ORE.get(), ModItems.AERO_CRYSTAL.get(), 2, 3));
        add(ModBlocks.SYNC_CRYSTAL_ORE.get(),
                block -> createOreDrop(ModBlocks.SYNC_CRYSTAL_ORE.get(), ModItems.SYNC_CRYSTAL.get()));
        add(ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.SYNC_CRYSTAL_DEEPSLATE_ORE.get(), ModItems.SYNC_CRYSTAL.get(), 2, 3));
        add(ModBlocks.THERMAL_CRYSTAL_ORE.get(),
                block -> createOreDrop(ModBlocks.THERMAL_CRYSTAL_ORE.get(), ModItems.THERMAL_CRYSTAL.get()));
        add(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.THERMAL_CRYSTAL_DEEPSLATE_ORE.get(), ModItems.THERMAL_CRYSTAL.get(), 2, 3));
        add(ModBlocks.DURASTEEL_ORE.get(),
                block -> createOreDrop(ModBlocks.DURASTEEL_ORE.get(), ModItems.RAW_DURASTEEL.get()));
        add(ModBlocks.DURASTEEL_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.DURASTEEL_DEEPSLATE_ORE.get(), ModItems.RAW_DURASTEEL.get(), 2, 3));
        add(ModBlocks.TITANIUM_ORE.get(),
                block -> createOreDrop(ModBlocks.TITANIUM_ORE.get(), ModItems.RAW_TITANIUM.get()));
        add(ModBlocks.TITANIUM_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.TITANIUM_DEEPSLATE_ORE.get(), ModItems.RAW_TITANIUM.get(), 2, 3));


    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
