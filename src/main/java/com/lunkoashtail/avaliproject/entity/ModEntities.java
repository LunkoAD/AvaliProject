package com.lunkoashtail.avaliproject.entity;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.PenguinEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, AvaliProject.MOD_ID);
    public static final Supplier<EntityType<PenguinEntity>> PENGUIN =
            ENTITY_TYPES.register("penguin", () -> EntityType.Builder.of(PenguinEntity::new, MobCategory.CREATURE)
                    .sized(0.75f, 0.95f).build("penguin"));

//    public static final Supplier<EntityType<ProtogenEntity>> PROTOGEN =
//            ENTITY_TYPES.register("protogen", () -> EntityType.Builder.of(ProtogenEntity::new, MobCategory.CREATURE)
//                    .sized(0.75f, 0.95f).build("protogen"));
//    public static final Supplier<EntityType<AvaliEntity>> PROTOGEN =
//            ENTITY_TYPES.register("avali", () -> EntityType.Builder.of(AVALIEntity::new, MobCategory.CREATURE)
//                    .sized(0.75f, 0.95f).build("avali"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}