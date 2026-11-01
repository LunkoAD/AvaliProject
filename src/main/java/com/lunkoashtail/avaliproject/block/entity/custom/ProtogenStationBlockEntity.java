package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.block.custom.ProtogenStationBlock;
import com.lunkoashtail.avaliproject.block.custom.StationKind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class ProtogenStationBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private RawAnimation idle;
    private RawAnimation active;

    protected ProtogenStationBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public String getStationName() {
        return BuiltInRegistries.BLOCK.getKey(getBlockState().getBlock()).getPath();
    }

    public StationKind kind() {
        return kindOf(getBlockState());
    }

    protected static StationKind kindOf(BlockState state) {
        return state.getBlock() instanceof ProtogenStationBlock station ? station.kind() : StationKind.RESEARCH;
    }

    public abstract void serverTick();

    public void onBroken() {
    }

    protected void dropAll(ItemStackHandler handler) {
        if (level == null) return;
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            ItemStack stack = handler.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), stack.copy());
                handler.setStackInSlot(slot, ItemStack.EMPTY);
            }
        }
    }

    protected void setActive(boolean running) {
        BlockState state = getBlockState();
        if (level != null && state.hasProperty(ProtogenStationBlock.ACTIVE) && state.getValue(ProtogenStationBlock.ACTIVE) != running) {
            level.setBlock(worldPosition, state.setValue(ProtogenStationBlock.ACTIVE, running), Block.UPDATE_ALL);
        }
    }

    protected void sync() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(getBlockState().getBlock().getDescriptionId());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "station", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<ProtogenStationBlockEntity> state) {
        BlockState block = getBlockState();
        if (idle == null && block.getBlock() instanceof ProtogenStationBlock station) {
            String prefix = "animation." + getStationName() + ".";
            idle = RawAnimation.begin().thenLoop(prefix + station.getIdleAnimation());
            active = RawAnimation.begin().thenLoop(prefix + station.getActiveAnimation());
        }
        if (idle == null) return PlayState.STOP;
        return state.setAndContinue(block.getValue(ProtogenStationBlock.ACTIVE) ? active : idle);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
