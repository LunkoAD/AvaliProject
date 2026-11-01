package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.block.custom.MachineFootprint;
import com.lunkoashtail.avaliproject.block.custom.ProtogenColor;
import com.lunkoashtail.avaliproject.block.custom.SynchPodBlock;
import com.lunkoashtail.avaliproject.block.entity.ModBlockEntities;
import com.lunkoashtail.avaliproject.entity.custom.ProtogenEntity;
import com.lunkoashtail.avaliproject.screen.custom.SynchPodMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class SynchPodBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider, ProtogenRegistrar {
    private static final String EMPTY_NAME = "animation.synch_pod.empty";
    private static final String OCCUPIED_NAME = "animation.synch_pod.occupied";
    private static final String CLOSE_NAME = "animation.synch_pod.close";
    private static final RawAnimation EMPTY = RawAnimation.begin().thenLoop(EMPTY_NAME);
    private static final RawAnimation OCCUPIED = RawAnimation.begin().thenLoop(OCCUPIED_NAME);
    private static final RawAnimation OPEN = RawAnimation.begin().thenPlayAndHold("animation.synch_pod.open");
    private static final RawAnimation CLOSE_EMPTY = RawAnimation.begin().thenPlay(CLOSE_NAME).thenLoop(EMPTY_NAME);
    private static final RawAnimation CLOSE_OCCUPIED = RawAnimation.begin().thenPlay(CLOSE_NAME).thenLoop(OCCUPIED_NAME);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean wasOpen;
    private boolean closing;
    private boolean closingOccupied;

    @Nullable
    private CompoundTag storedProtogen;
    @Nullable
    private UUID storedId;
    private String storedSkin = "";

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return index == 0 && level != null && ProtogenPower.isPowered(level, worldPosition) ? 1 : 0;
        }

        @Override
        public void set(int index, int value) {
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public SynchPodBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SYNCH_POD_BE.get(), pos, state);
    }

    public boolean isOccupied() {
        return storedProtogen != null;
    }

    public String getStoredSkin() {
        return storedSkin;
    }

    @Nullable
    @Override
    public UUID registeredProtogen() {
        return storedId;
    }

    @Override
    public void registerProtogen(ServerPlayer player, ProtogenEntity protogen) {
        if (level == null) return;
        if (!ProtogenPower.isPowered(level, worldPosition)) {
            player.displayClientMessage(Component.translatable("message.avaliproject.protogen_machine.no_power"), true);
            return;
        }
        if (isOccupied()) {
            player.displayClientMessage(Component.translatable("message.avaliproject.synch_pod.occupied"), true);
            return;
        }
        protogen.leaveWorkStation();
        CompoundTag tag = new CompoundTag();
        if (!protogen.saveAsPassenger(tag)) return;
        storedProtogen = tag;
        storedId = protogen.getUUID();
        storedSkin = protogen.getSkin();
        protogen.discard();
        level.setBlock(worldPosition, getBlockState()
                .setValue(SynchPodBlock.OCCUPIED, true)
                .setValue(SynchPodBlock.OPEN, false)
                .setValue(ProtogenColor.PROPERTY, ProtogenColor.fromSkin(storedSkin)), Block.UPDATE_ALL);
        level.playSound(null, worldPosition, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 0.6F, 1.4F);
        sync();
    }

    @Override
    public void unregisterProtogen(ServerPlayer player) {
        release();
    }

    public void release() {
        if (!(level instanceof ServerLevel serverLevel) || storedProtogen == null) return;
        Direction facing = getBlockState().getValue(SynchPodBlock.FACING);
        BlockPos found = MachineFootprint.SYNCH_POD.findExit(serverLevel, worldPosition, facing);
        BlockPos exit = found != null ? found : worldPosition.above(MachineFootprint.SYNCH_POD.height());
        EntityType.create(storedProtogen, serverLevel).ifPresent(entity -> {
            entity.moveTo(exit.getX() + 0.5, exit.getY(), exit.getZ() + 0.5, facing.toYRot(), 0.0F);
            serverLevel.addFreshEntity(entity);
        });
        storedProtogen = null;
        storedId = null;
        storedSkin = "";
        BlockState state = level.getBlockState(worldPosition);
        if (state.hasProperty(SynchPodBlock.OCCUPIED)) {
            level.setBlock(worldPosition, state.setValue(SynchPodBlock.OCCUPIED, false), Block.UPDATE_ALL);
        }
        sync();
    }

    private void sync() {
        setChanged();
        if (level != null) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SynchPodMenu(containerId, playerInventory, this, data);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "pod", 5, this::predicate));
    }

    private PlayState predicate(AnimationState<SynchPodBlockEntity> state) {
        BlockState block = getBlockState();
        boolean occupied = block.getValue(SynchPodBlock.OCCUPIED);
        if (block.getValue(SynchPodBlock.OPEN)) {
            wasOpen = true;
            closing = false;
            return state.setAndContinue(OPEN);
        }
        if (wasOpen) {
            wasOpen = false;
            closing = true;
            closingOccupied = occupied;
        }
        if (closing && closingOccupied != occupied) {
            closing = false;
        }
        if (closing) {
            return state.setAndContinue(occupied ? CLOSE_OCCUPIED : CLOSE_EMPTY);
        }
        return state.setAndContinue(occupied ? OCCUPIED : EMPTY);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (storedProtogen != null) tag.put("protogen", storedProtogen);
        if (storedId != null) tag.putUUID("protogen_id", storedId);
        tag.putString("protogen_skin", storedSkin);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        storedProtogen = tag.contains("protogen") ? tag.getCompound("protogen") : null;
        storedId = tag.hasUUID("protogen_id") ? tag.getUUID("protogen_id") : null;
        storedSkin = tag.getString("protogen_skin");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        if (storedId != null) tag.putUUID("protogen_id", storedId);
        tag.putString("protogen_skin", storedSkin);
        if (storedProtogen != null) tag.put("protogen", new CompoundTag());
        return tag;
    }
}
