package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    private static final float FRUIT_CHANCE = 0.65F;
    private static final float LEAVES_CHANCE = 0.3F;

    public ModDataMapProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        Builder<Compostable, Item> compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
        compostables.add(ModItems.COCONUT, new Compostable(FRUIT_CHANCE), false);
        compostables.add(ModItems.PASSION_FRUIT, new Compostable(FRUIT_CHANCE), false);
        compostables.add(ModItems.PAPAYA, new Compostable(FRUIT_CHANCE), false);
        compostables.add(ModItems.DRAGON_FRUIT, new Compostable(FRUIT_CHANCE), false);
        compostables.add(ModItems.OPEN_DRAGON_FRUIT, new Compostable(FRUIT_CHANCE), false);
        compostables.add(ModItems.OPEN_COCONUT, new Compostable(FRUIT_CHANCE), false);
        compostables.add(ModBlocks.PALM_LEAVES.getId(), new Compostable(LEAVES_CHANCE), false);
        compostables.add(ModBlocks.PASSION_FRUIT_LEAVES.getId(), new Compostable(LEAVES_CHANCE), false);
        compostables.add(ModBlocks.PAPAYA_LEAVES.getId(), new Compostable(LEAVES_CHANCE), false);
    }
}
