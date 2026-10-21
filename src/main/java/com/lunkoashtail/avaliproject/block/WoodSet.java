package com.lunkoashtail.avaliproject.block;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.function.Supplier;

public final class WoodSet {
    public final String name;
    public final BlockSetType blockSetType;
    public final WoodType woodType;
    public final TagKey<Block> logsBlockTag;
    public final TagKey<Item> logsItemTag;

    public final DeferredBlock<Block> log;
    public final DeferredBlock<Block> wood;
    public final DeferredBlock<Block> planks;
    public final DeferredBlock<Block> stairs;
    public final DeferredBlock<Block> slab;
    public final DeferredBlock<Block> fence;
    public final DeferredBlock<Block> fenceGate;
    public final DeferredBlock<Block> button;
    public final DeferredBlock<Block> pressurePlate;
    public final DeferredBlock<Block> sign;
    public final DeferredBlock<Block> wallSign;
    public final DeferredBlock<Block> hangingSign;
    public final DeferredBlock<Block> wallHangingSign;
    public final DeferredItem<Item> signItem;
    public final DeferredItem<Item> hangingSignItem;

    public WoodSet(String name, MapColor planksColor, MapColor barkColor) {
        this.name = name;
        this.blockSetType = BlockSetType.register(new BlockSetType(AvaliProject.MOD_ID + ":" + name));
        this.woodType = WoodType.register(new WoodType(AvaliProject.MOD_ID + ":" + name, blockSetType));
        ResourceLocation logsTag = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name + "_logs");
        this.logsBlockTag = BlockTags.create(logsTag);
        this.logsItemTag = ItemTags.create(logsTag);

        log = block(name + "_log",
                () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_LOG).mapColor(barkColor)));
        wood = block(name + "_wood",
                () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_WOOD).mapColor(barkColor)));
        planks = block(name + "_planks",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS).mapColor(planksColor)));
        stairs = block(name + "_stairs",
                () -> new StairBlock(planks.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_STAIRS).mapColor(planksColor)));
        slab = block(name + "_slab",
                () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_SLAB).mapColor(planksColor)));
        fence = block(name + "_fence",
                () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_FENCE).mapColor(planksColor)));
        fenceGate = block(name + "_fence_gate",
                () -> new FenceGateBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_FENCE_GATE).mapColor(planksColor)));
        button = block(name + "_button",
                () -> new ButtonBlock(blockSetType, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_BUTTON)));
        pressurePlate = block(name + "_pressure_plate",
                () -> new PressurePlateBlock(blockSetType, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PRESSURE_PLATE).mapColor(planksColor)));

        sign = ModBlocks.BLOCKS.register(name + "_sign",
                () -> new StandingSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_SIGN).mapColor(planksColor)));
        wallSign = ModBlocks.BLOCKS.register(name + "_wall_sign",
                () -> new WallSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_WALL_SIGN).mapColor(planksColor).dropsLike(sign.get())));
        hangingSign = ModBlocks.BLOCKS.register(name + "_hanging_sign",
                () -> new CeilingHangingSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_HANGING_SIGN).mapColor(planksColor)));
        wallHangingSign = ModBlocks.BLOCKS.register(name + "_wall_hanging_sign",
                () -> new WallHangingSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_WALL_HANGING_SIGN).mapColor(planksColor).dropsLike(hangingSign.get())));
        signItem = ModItems.ITEMS.register(name + "_sign",
                () -> new SignItem(new Item.Properties().stacksTo(16), sign.get(), wallSign.get()));
        hangingSignItem = ModItems.ITEMS.register(name + "_hanging_sign",
                () -> new HangingSignItem(hangingSign.get(), wallHangingSign.get(), new Item.Properties().stacksTo(16)));
    }

    public List<DeferredBlock<Block>> logs() {
        return List.of(log, wood);
    }

    public List<DeferredBlock<Block>> all() {
        return List.of(log, wood, planks, stairs, slab, fence, fenceGate,
                button, pressurePlate, sign, wallSign, hangingSign, wallHangingSign);
    }

    private static DeferredBlock<Block> block(String id, Supplier<? extends Block> supplier) {
        DeferredBlock<Block> block = ModBlocks.BLOCKS.register(id, supplier);
        ModItems.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }
}
