package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
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
                .add(ModItems.AVALI_SPEAR.get())
                .add(ModItems.SERGAL_SWORD.get())
                .add(ModItems.SERGAL_GREATSWORD.get())
                .add(ModItems.SERGAL_LANCE.get())
                .add(ModItems.SERGAL_MACE.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.AVALI_PICKAXE.get());
        tag(ItemTags.AXES)
                .add(ModItems.PROTOGEN_AXE.get())
                .add(ModItems.AVALI_AXE.get());
        tag(ItemTags.HOES)
                .add(ModItems.AVALI_HOE.get());
        tag(ItemTags.MUSIC_DISCS) //can be used to make blank disks from etched and other things.
                .add(ModItems.MERP_MUSIC_DISC.get())
                .add(ModItems.AVALI_DANCE_MUSIC_DISC.get())
                .add(ModItems.CYBERNETIC_HEART_MUSIC_DISC.get());
        tag(ModTags.Items.CHEESE).add(ModItems.SERGAL_CHEESE.get()); //useable to make cheeseburgers and other stuff.
//        tag(ItemTags.BOW_ENCHANTABLE)
//                .add(ModItems.SERGAL_SLINGSHOT.get());

//        tag(ItemTags.ARROWS)
//                .add(ModItems.VILOUS_CLAY.get());
    }
}
