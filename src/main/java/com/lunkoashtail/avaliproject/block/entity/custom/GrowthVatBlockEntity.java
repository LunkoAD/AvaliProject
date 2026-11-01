package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.block.custom.GrowthVatBlock;
import com.lunkoashtail.avaliproject.block.custom.MachineFootprint;
import com.lunkoashtail.avaliproject.block.custom.ProtogenColor;
import com.lunkoashtail.avaliproject.block.entity.ModBlockEntities;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.custom.ProtogenEntity;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.screen.custom.GrowthVatMenu;
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
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
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

public class GrowthVatBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider {
    public static final int GROWTH_DAYS = 21;
    public static final int GROWTH_TICKS = GROWTH_DAYS * 24000;
    public static final int SLOT_DNA = 0;
    public static final int SLOT_NUTRIENT = 1;
    public static final int SLOT_EMBRYO = 2;
    public static final int SLOT_DYE = 3;

    private static final RawAnimation[] STAGES = {
            RawAnimation.begin().thenLoop("animation.growth_vat.empty"),
            RawAnimation.begin().thenLoop("animation.growth_vat.stage_1"),
            RawAnimation.begin().thenLoop("animation.growth_vat.stage_2"),
            RawAnimation.begin().thenLoop("animation.growth_vat.stage_3")
    };

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final ItemStackHandler inventory = new ItemStackHandler(4) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case SLOT_DNA -> stack.is(ModItems.DNA.get());
                case SLOT_NUTRIENT -> stack.is(ModItems.NUTRIENT_PASTE.get());
                case SLOT_EMBRYO -> stack.is(ModItems.EMBRYO.get());
                default -> stack.getItem() instanceof DyeItem;
            };
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (slot == SLOT_DYE && !growing) updateColor();
        }
    };
    private int progress;
    private boolean growing;
    private boolean powered;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress / 20;
                case 1 -> growing ? 1 : 0;
                case 2 -> powered ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    public GrowthVatBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GROWTH_VAT_BE.get(), pos, state);
    }

    public boolean isGrowing() {
        return growing;
    }

    public boolean isComplete() {
        return growing && progress >= GROWTH_TICKS;
    }

    private boolean hasIngredients() {
        return !inventory.getStackInSlot(SLOT_DNA).isEmpty()
                && !inventory.getStackInSlot(SLOT_NUTRIENT).isEmpty()
                && !inventory.getStackInSlot(SLOT_EMBRYO).isEmpty()
                && !inventory.getStackInSlot(SLOT_DYE).isEmpty();
    }

    private ProtogenColor chosenColor() {
        return inventory.getStackInSlot(SLOT_DYE).getItem() instanceof DyeItem dye
                ? ProtogenColor.fromDye(dye.getDyeColor()) : ProtogenColor.CLASSIC;
    }

    public void serverTick() {
        if (level == null) return;
        if (level.getGameTime() % 20 == 0) {
            powered = ProtogenPower.isPowered(level, worldPosition);
        }
        if (!growing) {
            if (powered && hasIngredients()) start();
            return;
        }
        if (!powered || progress >= GROWTH_TICKS) return;
        progress++;
        if (progress % 200 == 0 || progress == GROWTH_TICKS) {
            setChanged();
            updateStage();
        }
    }

    private void start() {
        growing = true;
        progress = 0;
        updateColor();
        updateStage();
        level.playSound(null, worldPosition, SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, SoundSource.BLOCKS, 1.0F, 1.0F);
        sync();
    }

    private void updateStage() {
        int stage = growing ? 1 + Math.min(GrowthVatBlock.MAX_STAGE - 1, (int) ((long) progress * GrowthVatBlock.MAX_STAGE / GROWTH_TICKS)) : 0;
        BlockState state = getBlockState();
        if (state.getValue(GrowthVatBlock.STAGE) != stage) {
            level.setBlock(worldPosition, state.setValue(GrowthVatBlock.STAGE, stage), Block.UPDATE_ALL);
        }
    }

    private void updateColor() {
        if (level == null || level.isClientSide()) return;
        BlockState state = getBlockState();
        ProtogenColor color = chosenColor();
        if (state.getValue(ProtogenColor.PROPERTY) != color) {
            level.setBlock(worldPosition, state.setValue(ProtogenColor.PROPERTY, color), Block.UPDATE_ALL);
        }
    }

    public void finish(ServerPlayer player) {
        if (!isComplete() || !(level instanceof ServerLevel serverLevel)) return;
        ProtogenEntity protogen = ModEntities.PROTOGEN.get().create(serverLevel);
        if (protogen == null) return;
        Direction facing = getBlockState().getValue(GrowthVatBlock.FACING);
        BlockPos spawn = MachineFootprint.GROWTH_VAT.findExit(serverLevel, worldPosition, facing);
        if (spawn == null) spawn = worldPosition.above(MachineFootprint.GROWTH_VAT.height());
        protogen.moveTo(spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5, facing.toYRot(), 0.0F);
        protogen.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(spawn), MobSpawnType.MOB_SUMMONED, null);
        protogen.setSkin(getBlockState().getValue(ProtogenColor.PROPERTY).getSerializedName());
        protogen.tameBy(player);
        serverLevel.addFreshEntity(protogen);

        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            inventory.extractItem(slot, 1, false);
        }
        growing = false;
        progress = 0;
        updateStage();
        updateColor();
        serverLevel.playSound(null, worldPosition, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 0.8F);
        player.displayClientMessage(Component.translatable("message.avaliproject.growth_vat.finished"), true);
        sync();
    }

    public void drops() {
        if (level == null) return;
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory.getStackInSlot(slot).copy());
            inventory.setStackInSlot(slot, ItemStack.EMPTY);
        }
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
        return new GrowthVatMenu(containerId, playerInventory, this, data);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "vat", 10, this::predicate));
    }

    private PlayState predicate(AnimationState<GrowthVatBlockEntity> state) {
        return state.setAndContinue(STAGES[getBlockState().getValue(GrowthVatBlock.STAGE)]);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putBoolean("growing", growing);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        growing = tag.getBoolean("growing");
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
