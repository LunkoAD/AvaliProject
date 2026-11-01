package com.lunkoashtail.avaliproject.screen.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class ProtogenMachineMenu<T extends BlockEntity> extends AbstractContainerMenu {
    public static final int PLAYER_SLOT_COUNT = 36;
    public static final int WIDTH = 196;
    public static final int INVENTORY_LEFT = (WIDTH - 162) / 2;

    public final T blockEntity;
    protected final ContainerData data;
    private final ContainerLevelAccess access;
    private final Block block;
    private int playerInventoryLeft = INVENTORY_LEFT;
    private int playerInventoryTop = -1;

    protected ProtogenMachineMenu(MenuType<?> type, int containerId, Inventory inventory, T blockEntity, ContainerData data) {
        super(type, containerId);
        this.blockEntity = blockEntity;
        this.data = data;
        this.access = ContainerLevelAccess.create(inventory.player.level(), blockEntity.getBlockPos());
        this.block = blockEntity.getBlockState().getBlock();
        addDataSlots(data);
    }

    public BlockPos getBlockPos() {
        return blockEntity.getBlockPos();
    }

    public int getPlayerInventoryLeft() {
        return playerInventoryLeft;
    }

    public boolean hasPlayerInventory() {
        return playerInventoryTop >= 0;
    }

    protected void addPlayerSlots(Inventory inventory, int top) {
        addPlayerSlots(inventory, INVENTORY_LEFT, top);
    }

    protected void addPlayerSlots(Inventory inventory, int left, int top) {
        playerInventoryLeft = left;
        playerInventoryTop = top;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, column + row * 9 + 9, left + column * 18, top + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, left + column * 18, top + 58));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot source = slots.get(index);
        if (!source.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = source.getItem();
        ItemStack copy = stack.copy();
        if (index < PLAYER_SLOT_COUNT) {
            if (!moveItemStackTo(stack, PLAYER_SLOT_COUNT, slots.size(), false)) return ItemStack.EMPTY;
        } else if (!moveItemStackTo(stack, 0, PLAYER_SLOT_COUNT, true)) {
            return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            source.set(ItemStack.EMPTY);
        } else {
            source.setChanged();
        }
        source.onTake(player, stack);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, block);
    }
}
