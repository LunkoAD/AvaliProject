package com.lunkoashtail.avaliproject.block.entity;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.WoodSet;
import com.lunkoashtail.avaliproject.block.entity.custom.ContainerCrateBlockEntity;
import com.lunkoashtail.avaliproject.block.entity.custom.GrowthVatBlockEntity;
import com.lunkoashtail.avaliproject.block.entity.custom.MoonlitOfferingAltarBlockEntity;
import com.lunkoashtail.avaliproject.block.entity.custom.NanoloomBlockEntity;
import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenStationBlockEntity;
import com.lunkoashtail.avaliproject.block.entity.custom.SynchPodBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AvaliProject.MOD_ID);

        public static final Supplier<BlockEntityType<NanoloomBlockEntity>> NANOLOOM_BE =
            BLOCK_ENTITIES.register("nanoloom_be", () -> BlockEntityType.Builder.of(
                    NanoloomBlockEntity::new, ModBlocks.NANOLOOM.get()).build(null));

        public static final Supplier<BlockEntityType<ContainerCrateBlockEntity>> CONTAINER_CRATE_BE =
            BLOCK_ENTITIES.register("container_crate_be", () -> BlockEntityType.Builder.of(
                    ContainerCrateBlockEntity::new, ModBlocks.CONTAINER_CRATE.get()).build(null));

        public static final Supplier<BlockEntityType<MoonlitOfferingAltarBlockEntity>> MOONLIT_OFFERING_ALTAR_BE =
            BLOCK_ENTITIES.register("moonlit_offering_altar_be", () -> BlockEntityType.Builder.of(
                    MoonlitOfferingAltarBlockEntity::new, ModBlocks.MOONLIT_OFFERING_ALTAR.get()).build(null));

        public static final Supplier<BlockEntityType<SynchPodBlockEntity>> SYNCH_POD_BE =
            BLOCK_ENTITIES.register("synch_pod_be", () -> BlockEntityType.Builder.of(
                    SynchPodBlockEntity::new, ModBlocks.SYNCH_POD.get()).build(null));

        public static final Supplier<BlockEntityType<GrowthVatBlockEntity>> GROWTH_VAT_BE =
            BLOCK_ENTITIES.register("growth_vat_be", () -> BlockEntityType.Builder.of(
                    GrowthVatBlockEntity::new, ModBlocks.GROWTH_VAT.get()).build(null));

        public static final Supplier<BlockEntityType<ProtogenStationBlockEntity>> PROTOGEN_STATION_BE =
            BLOCK_ENTITIES.register("protogen_station_be", () -> BlockEntityType.Builder.of(
                    ProtogenStationBlockEntity::new, ModBlocks.PROTOGEN_RESEARCH_STATION.get(),
                    ModBlocks.PROTOGEN_MINING_STATION.get(), ModBlocks.PROTOGEN_FARMING_STATION.get(),
                    ModBlocks.PROTOGEN_GENERATOR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
        eventBus.addListener(ModBlockEntities::addSignBlocks);
    }

    private static void addSignBlocks(BlockEntityTypeAddBlocksEvent event) {
        for (WoodSet set : ModBlocks.WOOD_SETS) {
            event.modify(BlockEntityType.SIGN, set.sign.get(), set.wallSign.get());
            event.modify(BlockEntityType.HANGING_SIGN, set.hangingSign.get(), set.wallHangingSign.get());
        }
    }
}
