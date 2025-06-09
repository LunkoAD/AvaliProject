package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.worldgen.dimensions.adastra.AdAstra_PlanetRenderers;
import com.lunkoashtail.avaliproject.worldgen.dimensions.adastra.AdAstra_Planets;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBiomeTagsProvider;
import net.minecraft.data.registries.VanillaRegistries;
import com.lunkoashtail.avaliproject.datagen.avalon.AvalonTags;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.DoubleStream;

@Mod.EventBusSubscriber(modid = AvaliProject.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper  = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK),new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new, LootContextParamSets.ENTITY))));
        generator.addProvider(event.includeServer(), new com.lunkoashtail.avaliproject.datagen.ModRecipeProvider(packOutput));

        BlockTagsProvider blockTagsProvider = new com.lunkoashtail.avaliproject.datagen.ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new com.lunkoashtail.avaliproject.datagen.ModItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new AdAstra_PlanetRenderers(packOutput));
        generator.addProvider(event.includeServer(), new AdAstra_Planets(packOutput));

        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));


        generator.addProvider(
                // Tell generator to run only when client assets are generating
                event.includeClient(),
                new ModSoundDefinitionProvider(packOutput, event.getExistingFileHelper())
        );
        generator.addProvider(event.includeServer(), new AvalonTags(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagProvider(packOutput,lookupProvider));
        generator.addProvider(event.includeClient(), new com.lunkoashtail.avaliproject.datagen.ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new com.lunkoashtail.avaliproject.datagen.ModBlockStateProvider(packOutput, existingFileHelper));

        //var registrySet = VanillaRegistries.createLookup();



        /*generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(), registrySet, Set.of(AvaliProject.MOD_ID)) {
            @Override
            public String getName() {
                return "Data Registries: " + AvaliProject.MOD_ID;
            }

            @Override
            protected boolean shouldDump(ResourceKey<?> key) {
                return false;
            }
        });*/

    }
}
