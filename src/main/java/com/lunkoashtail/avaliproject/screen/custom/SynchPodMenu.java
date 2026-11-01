package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.block.entity.custom.SynchPodBlockEntity;
import com.lunkoashtail.avaliproject.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class SynchPodMenu extends ProtogenMachineMenu<SynchPodBlockEntity> {
    public SynchPodMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, (SynchPodBlockEntity) inventory.player.level().getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(1));
    }

    public SynchPodMenu(int containerId, Inventory inventory, SynchPodBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.SYNCH_POD_MENU.get(), containerId, inventory, blockEntity, data);
    }

    public boolean isPowered() {
        return data.get(0) != 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
