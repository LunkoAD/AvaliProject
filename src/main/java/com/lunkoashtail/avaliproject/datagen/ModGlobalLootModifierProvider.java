package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.loot.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    private static final float CACTUS_DRAGON_FRUIT_CHANCE = 0.1F;

    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AvaliProject.MOD_ID);
    }

    @Override
    protected void start() {
        add("dragon_fruit_from_cactus", new AddItemModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("blocks/cactus")).build(),
                LootItemRandomChanceCondition.randomChance(CACTUS_DRAGON_FRUIT_CHANCE).build()
        }, ModItems.DRAGON_FRUIT.get()));
    }
}
