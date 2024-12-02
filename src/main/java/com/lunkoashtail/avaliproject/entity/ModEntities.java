package com.lunkoashtail.avaliproject.entity;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.SkacikkjrrkbwcakEntity;
import com.lunkoashtail.avaliproject.entity.custom.SksceegehkjaEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, AvaliProject.MOD_ID);
    public static final Supplier<EntityType<SksceegehkjaEntity>> SKSCEEGEHKJA =
            ENTITY_TYPES.register("sksceegehkja", () -> EntityType.Builder.of(SksceegehkjaEntity::new, MobCategory.CREATURE)
                    .sized(2f, 2f).build("sksceegehkja"));

    public static final Supplier<EntityType<SkacikkjrrkbwcakEntity>> SKACIKKJRRKBWCAK =
            ENTITY_TYPES.register("skacikkjrrkbwcak", () -> EntityType.Builder.of(SkacikkjrrkbwcakEntity::new, MobCategory.CREATURE)
                    .sized(0.95f, 1.15f).build("skacikkjrrkbwcak"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}