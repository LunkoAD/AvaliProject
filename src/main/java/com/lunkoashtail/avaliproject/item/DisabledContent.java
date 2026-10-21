package com.lunkoashtail.avaliproject.item;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.List;
import java.util.function.Supplier;

public final class DisabledContent {
    private static final List<Supplier<? extends ItemLike>> ENTRIES = List.of(
            ModItems.RAILGUN,
            ModBlocks.MOONLIT_OFFERING_ALTAR,
            ModBlocks.CORAL_CULTIVATION_BLOCK);

    private DisabledContent() {
    }

    public static boolean isDisabled(ItemLike item) {
        return ENTRIES.stream().anyMatch(entry -> entry.get().asItem() == item.asItem());
    }

    public static List<ItemStack> stacks() {
        return ENTRIES.stream().map(entry -> new ItemStack(entry.get())).toList();
    }

    public static void hideFromCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        for (ItemStack stack : stacks()) {
            if (event.getParentEntries().contains(stack) || event.getSearchEntries().contains(stack)) {
                event.remove(stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }
}
