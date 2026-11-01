package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.block.entity.custom.GrowthVatBlockEntity;
import com.lunkoashtail.avaliproject.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class GrowthVatMenu extends ProtogenMachineMenu<GrowthVatBlockEntity> {
    public static final int BUTTON_FINISH = 0;
    public static final int WIDTH = 216;
    public static final int SLOT_Y = 40;
    public static final int[] SLOT_X = {35, 78, 122, 165};
    public static final int INVENTORY_TOP = 136;

    public GrowthVatMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, (GrowthVatBlockEntity) inventory.player.level().getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(3));
    }

    public GrowthVatMenu(int containerId, Inventory inventory, GrowthVatBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.GROWTH_VAT_MENU.get(), containerId, inventory, blockEntity, data);
        addPlayerSlots(inventory, (WIDTH - 162) / 2, INVENTORY_TOP);
        for (int slot = 0; slot < SLOT_X.length; slot++) {
            addSlot(new SlotItemHandler(blockEntity.inventory, slot, SLOT_X[slot], SLOT_Y) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return !isGrowing() && super.mayPlace(stack);
                }

                @Override
                public boolean mayPickup(Player player) {
                    return !isGrowing() && super.mayPickup(player);
                }
            });
        }
    }

    public int getProgressSeconds() {
        return data.get(0);
    }

    public float getProgressFraction() {
        return Math.min(1.0F, getProgressSeconds() * 20.0F / GrowthVatBlockEntity.GROWTH_TICKS);
    }

    public boolean isGrowing() {
        return data.get(1) != 0;
    }

    public boolean isPowered() {
        return data.get(2) != 0;
    }

    public boolean isComplete() {
        return isGrowing() && getProgressSeconds() * 20 >= GrowthVatBlockEntity.GROWTH_TICKS;
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id != BUTTON_FINISH || !(player instanceof ServerPlayer serverPlayer)) return false;
        blockEntity.finish(serverPlayer);
        return true;
    }
}
