package com.lunkoashtail.avaliproject.component;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AvaliProject.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DrugDosage>> DRUG_DOSAGE =
            DATA_COMPONENTS.register("drug_dosage", () -> DataComponentType.<DrugDosage>builder()
                    .persistent(DrugDosage.CODEC)
                    .networkSynchronized(DrugDosage.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SyringeContents>> SYRINGE_CONTENTS =
            DATA_COMPONENTS.register("syringe_contents", () -> DataComponentType.<SyringeContents>builder()
                    .persistent(SyringeContents.CODEC)
                    .networkSynchronized(SyringeContents.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SwordCondition>> SWORD_CONDITION =
            DATA_COMPONENTS.register("sword_condition", () -> DataComponentType.<SwordCondition>builder()
                    .persistent(SwordCondition.CODEC)
                    .networkSynchronized(SwordCondition.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AerogelToolState>> AEROGEL_TOOL_STATE =
            DATA_COMPONENTS.register("aerogel_tool_state", () -> DataComponentType.<AerogelToolState>builder()
                    .persistent(AerogelToolState.CODEC)
                    .networkSynchronized(AerogelToolState.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CanteenContents>> CANTEEN_CONTENTS =
            DATA_COMPONENTS.register("canteen_contents", () -> DataComponentType.<CanteenContents>builder()
                    .persistent(CanteenContents.CODEC)
                    .networkSynchronized(CanteenContents.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BloodContents>> BLOOD_CONTENTS =
            DATA_COMPONENTS.register("blood_contents", () -> DataComponentType.<BloodContents>builder()
                    .persistent(BloodContents.CODEC)
                    .networkSynchronized(BloodContents.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FluidAmount>> FLUID_AMOUNT =
            DATA_COMPONENTS.register("fluid_amount", () -> DataComponentType.<FluidAmount>builder()
                    .persistent(FluidAmount.CODEC)
                    .networkSynchronized(FluidAmount.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> TRINKET_GEM =
            DATA_COMPONENTS.register("trinket_gem", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                    .build());

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
