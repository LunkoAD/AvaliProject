package com.lunkoashtail.avaliproject.block.entity;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.entity.custom.NanoloomBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AvaliProject.MOD_ID);

        public static final Supplier<BlockEntityType<NanoloomBlockEntity>> NANOLOOM_BE =
            BLOCK_ENTITIES.register("nanoloom_be", () -> BlockEntityType.Builder.of(
                    NanoloomBlockEntity::new, ModBlocks.NANOLOOM.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}