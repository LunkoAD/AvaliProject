package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = AvaliProject.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper  = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK))));
        generator.addProvider(event.includeServer(), new com.lunkoashtail.avaliproject.datagen.ModRecipeProvider(packOutput));

        BlockTagsProvider blockTagsProvider = new com.lunkoashtail.avaliproject.datagen.ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new com.lunkoashtail.avaliproject.datagen.ModItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeClient(), new com.lunkoashtail.avaliproject.datagen.ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new com.lunkoashtail.avaliproject.datagen.ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
    }
}
