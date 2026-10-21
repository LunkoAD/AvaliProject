package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.worldgen.ModLootTables;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModChestLootTableProvider implements LootTableSubProvider {

    protected ModChestLootTableProvider(HolderLookup.Provider registries) {
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> writer) {
        writer.accept(ModLootTables.EXPIE_CONTAINER_CRATE, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 3))
                        .when(LootItemRandomChanceCondition.randomChance(0.16f))
                        .add(TagEntry.expandTag(ModItemTagProvider.EXPIE_MEDICAL_TAG)))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 3))
                        .when(LootItemRandomChanceCondition.randomChance(0.16f))
                        .add(TagEntry.expandTag(ModItemTagProvider.EXPIE_TOOLS_TAG)))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 3))
                        .when(LootItemRandomChanceCondition.randomChance(0.33f))
                        .add(TagEntry.expandTag(ModItemTagProvider.EXPIE_UTILITY_TAG))));

        writer.accept(ModLootTables.WICKER_ENCLAVE_NEST, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(3, 6))
                        .add(entry(ModItems.MANE_TUFT.get(), 20, 1, 3))
                        .add(entry(ModItems.CLAW_SHARD.get(), 15, 1, 2))
                        .add(entry(ModItems.WICKER_HORN_FRAGMENT.get(), 10, 1, 1))
                        .add(entry(ModItems.CRYSTALITHIUM_CRYSTAL.get(), 15, 1, 4))
                        .add(entry(Items.GOLD_NUGGET, 20, 2, 8))
                        .add(entry(Items.GOLD_INGOT, 6, 1, 2))
                        .add(entry(Items.AMETHYST_SHARD, 10, 1, 3)))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.3f))
                        .add(LootItem.lootTableItem(ModItems.WICKER_EGG.get()))));

        writer.accept(ModLootTables.BEACH_BURROW, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(3, 5))
                        .add(entry(ModItems.SHELL.get(), 20, 1, 4))
                        .add(entry(ModBlocks.SEA_GLASS.get().asItem(), 15, 1, 3))
                        .add(entry(ModItems.COCONUT.get(), 15, 1, 3))
                        .add(entry(ModBlocks.DRIFTWOOD.asItem(), 15, 1, 3))
                        .add(entry(ModItems.CORAL_PIECE.get(), 10, 1, 3))
                        .add(entry(ModItems.PEARL.get(), 8, 1, 2))
                        .add(entry(ModItems.ABALONE_SHARD.get(), 5, 1, 1)))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.08f))
                        .add(LootItem.lootTableItem(ModItems.BLACK_PEARL.get()))));

        writer.accept(ModLootTables.TROPICAL_DEN, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(3, 6))
                        .add(entry(ModItems.PAPAYA.get(), 15, 1, 3))
                        .add(entry(ModItems.DRAGON_FRUIT.get(), 12, 1, 3))
                        .add(entry(ModItems.PASSION_FRUIT.get(), 15, 1, 3))
                        .add(entry(ModItems.COCONUT.get(), 10, 1, 2))
                        .add(entry(ModItems.BONE_JEWELRY.get(), 10, 1, 2))
                        .add(entry(ModItems.JADEITE.get(), 6, 1, 2))
                        .add(entry(ModItems.CARNELIAN.get(), 6, 1, 2))
                        .add(entry(ModItems.AMMOLITE.get(), 4, 1, 1))
                        .add(entry(ModItems.MOONSTONE.get(), 4, 1, 1))
                        .add(entry(ModItems.SUNSTONE.get(), 4, 1, 1))
                        .add(entry(ModItems.PEARL.get(), 5, 1, 2)))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.15f))
                        .add(LootItem.lootTableItem(ModItems.MANOKIT_TRINKET.get())
                                .apply(SetComponentsFunction.setComponent(ModDataComponents.TRINKET_GEM.get(), "moonstone")))));
    }

    private static LootPoolEntryContainer.Builder<?> entry(ItemLike item, int weight, int min, int max) {
        return LootItem.lootTableItem(item).setWeight(weight)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }
}
