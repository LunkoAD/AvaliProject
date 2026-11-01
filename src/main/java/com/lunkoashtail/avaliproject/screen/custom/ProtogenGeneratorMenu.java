package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenGeneratorBlockEntity;
import com.lunkoashtail.avaliproject.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ProtogenGeneratorMenu extends ProtogenMachineMenu<ProtogenGeneratorBlockEntity> {
    public static final int BUTTON_TOGGLE = 0;
    public static final int FUEL_X = 30;
    public static final int FUEL_Y = 54;
    public static final int INVENTORY_TOP = 94;

    public ProtogenGeneratorMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, (ProtogenGeneratorBlockEntity) inventory.player.level().getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(2));
    }

    public ProtogenGeneratorMenu(int containerId, Inventory inventory, ProtogenGeneratorBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.PROTOGEN_GENERATOR_MENU.get(), containerId, inventory, blockEntity, data);
        addPlayerSlots(inventory, INVENTORY_TOP);
        addSlot(new SlotItemHandler(blockEntity.fuel, 0, FUEL_X, FUEL_Y));
    }

    public int getBurnTime() {
        return data.get(0);
    }

    public boolean isSwitchedOn() {
        return data.get(1) != 0;
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id != BUTTON_TOGGLE) return false;
        blockEntity.toggle();
        return true;
    }
}
