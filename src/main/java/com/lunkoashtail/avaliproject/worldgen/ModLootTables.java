package com.lunkoashtail.avaliproject.worldgen;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModLootTables {
    public static final ResourceKey<LootTable> EXPIE_CONTAINER_CRATE = ResourceKey.create(Registries.LOOT_TABLE,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "chests/expie_container_crate"));
    public static final ResourceKey<LootTable> WICKER_ENCLAVE_NEST = ResourceKey.create(Registries.LOOT_TABLE,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "chests/wicker_enclave_nest"));
    public static final ResourceKey<LootTable> BEACH_BURROW = ResourceKey.create(Registries.LOOT_TABLE,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "chests/beach_burrow"));
    public static final ResourceKey<LootTable> TROPICAL_DEN = ResourceKey.create(Registries.LOOT_TABLE,
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "chests/tropical_den"));
}
