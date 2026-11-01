package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.block.custom.StationKind;
import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenWorkStationBlockEntity;
import com.lunkoashtail.avaliproject.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ProtogenStationMenu extends ProtogenMachineMenu<ProtogenWorkStationBlockEntity> {
    public static final int TOOL_ROW_Y = 56;
    public static final int MINING_OUTPUT_Y = 80;
    public static final int FARMING_ROW_Y = 56;
    public static final int RESEARCH_BAR_Y = 56;
    public static final int RESEARCH_ROW_Y = 76;

    private final StationKind kind;

    public ProtogenStationMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, (ProtogenWorkStationBlockEntity) inventory.player.level().getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(4));
    }

    public ProtogenStationMenu(int containerId, Inventory inventory, ProtogenWorkStationBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.PROTOGEN_STATION_MENU.get(), containerId, inventory, blockEntity, data);
        this.kind = blockEntity.kind();
        addPlayerSlots(inventory, inventoryTop(kind));
        IItemHandler handler = blockEntity.inventory;
        switch (kind) {
            case MINING -> {
                for (int slot = 0; slot < ProtogenWorkStationBlockEntity.TOOL_SLOTS; slot++) {
                    addSlot(new SlotItemHandler(handler, slot, INVENTORY_LEFT + slot * 18, TOOL_ROW_Y));
                }
                addGrid(handler, ProtogenWorkStationBlockEntity.TOOL_SLOTS, 2, MINING_OUTPUT_Y, true);
            }
            case FARMING -> addGrid(handler, 0, 2, FARMING_ROW_Y, false);
            default -> addGrid(handler, 0, 1, RESEARCH_ROW_Y, true);
        }
    }

    public static int inventoryTop(StationKind kind) {
        return switch (kind) {
            case MINING -> MINING_OUTPUT_Y + 2 * 18 + 16;
            case FARMING -> FARMING_ROW_Y + 2 * 18 + 16;
            default -> RESEARCH_ROW_Y + 18 + 16;
        };
    }

    private void addGrid(IItemHandler handler, int firstSlot, int rows, int top, boolean outputOnly) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < 9; column++) {
                int slot = firstSlot + row * 9 + column;
                int x = INVENTORY_LEFT + column * 18;
                int y = top + row * 18;
                addSlot(outputOnly ? new SlotItemHandler(handler, slot, x, y) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                } : new SlotItemHandler(handler, slot, x, y));
            }
        }
    }

    public StationKind getKind() {
        return kind;
    }

    public int getStatus() {
        return data.get(0);
    }

    public boolean hasStatus(int flag) {
        return (getStatus() & flag) != 0;
    }

    public int getProgress() {
        return data.get(1);
    }

    public int getMaxProgress() {
        return data.get(2);
    }

    public int getBottlesMade() {
        return data.get(3);
    }
}
