package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModEntityLootTableProvider extends EntityLootSubProvider {

    public ModEntityLootTableProvider() {
        super(FeatureFlagSet.of());
    }

    @Override
    public void generate() {
        //TODO: WHY ARENT YOU WORKING!?! - 989onan
        /*add(ModEntities.AVALI.get(),
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(
                                        UniformGenerator.between(2.0F, 4.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(
                                        UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(4.0F))
                                .add(LootItem.lootTableItem(Items.CHICKEN)
                                        .apply(SmeltItemFunction.smelted()
                                                .when(LootItemEntityPropertyCondition.hasProperties(
                                                        LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                        .apply(LootingEnchantFunction.lootingMultiplier(
                                                UniformGenerator.between(0.0F, 1.0F))))));*/
    }
}
