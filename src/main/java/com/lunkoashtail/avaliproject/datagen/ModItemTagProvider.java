package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AvaliProject.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ItemTags.SWORDS)
                .add(ModItems.AVALI_SWORD.get())
                .add(ModItems.PROTOGEN_SWORD.get())
                .add(ModItems.AVALI_SPEAR.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.AVALI_PICKAXE.get());
        tag(ItemTags.AXES)
                .add(ModItems.PROTOGEN_AXE.get())
                .add(ModItems.AVALI_AXE.get());
        tag(ItemTags.HOES)
                .add(ModItems.AVALI_HOE.get());

    }
}
