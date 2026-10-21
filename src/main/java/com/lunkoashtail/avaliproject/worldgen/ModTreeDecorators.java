package com.lunkoashtail.avaliproject.worldgen;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTreeDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS =
            DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, AvaliProject.MOD_ID);

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<CoconutDecorator>> COCONUT =
            TREE_DECORATORS.register("coconut", () -> new TreeDecoratorType<>(CoconutDecorator.CODEC));

    public static void register(IEventBus eventBus) {
        TREE_DECORATORS.register(eventBus);
    }
}
