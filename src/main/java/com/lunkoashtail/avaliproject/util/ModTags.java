package com.lunkoashtail.avaliproject.util;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_AEROGEL_TOOL = createTag("needs_aerogel_tool");
        public static final TagKey<Block> INCORRECT_FOR_AEROGEL_TOOL = createTag("incorrect_for_aerogel_tool");
        public static final TagKey<Block> NEEDS_HARDLIGHT_TOOL = createTag("needs_hardlight_tool");
        public static final TagKey<Block> INCORRECT_FOR_HARDLIGHT_TOOL = createTag("incorrect_for_hardlight_tool");
        public static final TagKey<Block> NEEDS_CERAMIC_TOOL = createTag("needs_ceramic_tool");
        public static final TagKey<Block> INCORRECT_FOR_CERAMIC_TOOL = createTag("incorrect_for_ceramic_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name));
        }
    }
}