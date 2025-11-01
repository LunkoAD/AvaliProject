package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagProvider extends EntityTypeTagsProvider {
    //ad_astra:lives_without_oxygen
    //ad_astra:can_survive_extreme_cold
    public static final TagKey<EntityType<?>> NO_OXYGEN = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("ad_astra","lives_without_oxygen"));
    public static final TagKey<EntityType<?>> EXTREME_COLD = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("ad_astra","can_survive_extreme_cold"));

    public ModEntityTypeTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider) {
        super(pOutput, pProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(NO_OXYGEN)
                .add(ModEntities.AVALI.get())
                .add(ModEntities.SKSCEEGEHKJA.get());

        this.tag(EXTREME_COLD)
                .add(ModEntities.AVALI.get())
                .add(ModEntities.SKSCEEGEHKJA.get());


    }
}
