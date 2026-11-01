package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.block.entity.ModBlockEntities;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.screen.custom.ProtogenGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ProtogenGeneratorBlockEntity extends ProtogenStationBlockEntity {
    public static final int BURN_TICKS = 24000;

    public final ItemStackHandler fuel = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return isFuel(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private boolean switchedOn;
    private int burnTime;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> burnTime;
                case 1 -> switchedOn ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> burnTime = value;
                case 1 -> switchedOn = value != 0;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public ProtogenGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PROTOGEN_GENERATOR_BE.get(), pos, state);
    }

    public static boolean isFuel(ItemStack stack) {
        return stack.is(ModItems.ARCAITES_CRYSTAL.get());
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide()) {
            ProtogenPower.add(level, worldPosition);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (level != null && !level.isClientSide()) {
            ProtogenPower.remove(level, worldPosition);
        }
    }

    @Override
    public void serverTick() {
        if (switchedOn && burnTime <= 0 && !fuel.getStackInSlot(0).isEmpty()) {
            fuel.extractItem(0, 1, false);
            burnTime = BURN_TICKS;
        }
        boolean running = switchedOn && burnTime > 0;
        if (running) {
            burnTime--;
            if (burnTime % 100 == 0) setChanged();
        }
        setActive(running);
    }

    public void toggle() {
        switchedOn = !switchedOn;
        if (level != null) {
            level.playSound(null, worldPosition, switchedOn ? SoundEvents.BEACON_ACTIVATE : SoundEvents.BEACON_DEACTIVATE,
                    SoundSource.BLOCKS, 0.6F, 1.4F);
        }
        sync();
    }

    @Override
    public void onBroken() {
        dropAll(fuel);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ProtogenGeneratorMenu(containerId, inventory, this, data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("fuel", fuel.serializeNBT(registries));
        tag.putBoolean("on", switchedOn);
        tag.putInt("burn_time", burnTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        fuel.deserializeNBT(registries, tag.getCompound("fuel"));
        switchedOn = tag.getBoolean("on");
        burnTime = tag.getInt("burn_time");
    }
}
