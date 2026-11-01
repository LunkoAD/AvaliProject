package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.block.custom.ProtogenStationBlock;
import com.lunkoashtail.avaliproject.block.custom.StationKind;
import com.lunkoashtail.avaliproject.block.entity.ModBlockEntities;
import com.lunkoashtail.avaliproject.entity.custom.ProtogenEntity;
import com.lunkoashtail.avaliproject.screen.custom.ProtogenStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ProtogenWorkStationBlockEntity extends ProtogenStationBlockEntity implements ProtogenRegistrar {
    public static final int WORK_RADIUS = 16;
    public static final int BED_RADIUS = 16;
    public static final int RESEARCH_TICKS = 6000;
    public static final int TOOL_SLOTS = 3;
    public static final int PIT_SIZE = 16;
    public static final int PIT_OFFSET = 2;
    private static final int PIT_PERIMETER = 4 * (PIT_SIZE - 1);
    private static final int FARM_HEIGHT = 3;
    private static final int RESEARCH_DISTANCE = 8;

    public static final int STATUS_POWERED = 1;
    public static final int STATUS_PROTOGEN = 2;
    public static final int STATUS_BED = 4;
    public static final int STATUS_FULL = 8;
    public static final int STATUS_NO_TOOL = 16;
    public static final int STATUS_DONE = 32;
    private static final int STATUS_REQUIREMENTS = STATUS_POWERED | STATUS_PROTOGEN | STATUS_BED;
    private static final int STATUS_HALTED = STATUS_FULL | STATUS_NO_TOOL | STATUS_DONE;

    public final ItemStackHandler inventory;
    @Nullable
    private UUID protogenId;
    private int status;
    private boolean outputFull;
    private boolean missingTool;
    private boolean bedNearby;
    private int bedCheckCooldown;
    private int researchProgress;
    private int bottlesMade;
    private int mineDepth;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> status;
                case 1 -> kind() == StationKind.MINING ? mineDepth : researchProgress / 20;
                case 2 -> kind() == StationKind.MINING ? totalLayers() : RESEARCH_TICKS / 20;
                case 3 -> Math.min(bottlesMade, Short.MAX_VALUE);
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public ProtogenWorkStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PROTOGEN_STATION_BE.get(), pos, state);
        StationKind kind = kindOf(state);
        this.inventory = new ItemStackHandler(kind.inventorySize()) {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return kind != StationKind.MINING || slot >= TOOL_SLOTS || isTool(stack);
            }

            @Override
            protected void onContentsChanged(int slot) {
                outputFull = false;
                missingTool = false;
                setChanged();
            }
        };
    }

    public static boolean isTool(ItemStack stack) {
        return stack.getItem() instanceof DiggerItem;
    }

    public int firstOutputSlot() {
        return kind() == StationKind.MINING ? TOOL_SLOTS : 0;
    }

    public boolean isOperational() {
        return (status & STATUS_REQUIREMENTS) == STATUS_REQUIREMENTS && (status & STATUS_HALTED) == 0;
    }

    @Override
    public void serverTick() {
        if (!(level instanceof ServerLevel serverLevel) || serverLevel.getGameTime() % 20 != 0) return;
        if (--bedCheckCooldown <= 0) {
            bedCheckCooldown = 5;
            bedNearby = serverLevel.getPoiManager().findClosest(holder -> holder.is(PoiTypes.HOME), worldPosition,
                    BED_RADIUS, PoiManager.Occupancy.ANY).isPresent();
        }
        boolean powered = ProtogenPower.isPowered(serverLevel, worldPosition);
        ProtogenEntity protogen = findProtogen(serverLevel);
        boolean ready = powered && protogen != null && bedNearby;

        if (kind() == StationKind.RESEARCH && ready && !outputFull
                && protogen.distanceToSqr(Vec3.atCenterOf(worldPosition)) <= RESEARCH_DISTANCE * RESEARCH_DISTANCE) {
            research();
        }

        int next = 0;
        if (powered) next |= STATUS_POWERED;
        if (protogen != null) next |= STATUS_PROTOGEN;
        if (bedNearby) next |= STATUS_BED;
        if (outputFull) next |= STATUS_FULL;
        if (missingTool) next |= STATUS_NO_TOOL;
        if (kind() == StationKind.MINING && pitFinished()) next |= STATUS_DONE;
        status = next;
        setActive(isOperational());
    }

    private void research() {
        researchProgress += 20;
        if (researchProgress < RESEARCH_TICKS) return;
        List<ItemStack> bottle = List.of(new ItemStack(Items.EXPERIENCE_BOTTLE));
        if (canStore(bottle)) {
            store(bottle);
            researchProgress = 0;
            bottlesMade++;
            level.playSound(null, worldPosition, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.5F, 1.2F);
        } else {
            researchProgress = RESEARCH_TICKS;
            outputFull = true;
        }
        setChanged();
    }

    @Nullable
    private ProtogenEntity findProtogen(ServerLevel serverLevel) {
        if (protogenId == null) return null;
        return serverLevel.getEntity(protogenId) instanceof ProtogenEntity protogen && protogen.isAlive() ? protogen : null;
    }

    @Nullable
    @Override
    public UUID registeredProtogen() {
        return protogenId;
    }

    public boolean isRegistered(ProtogenEntity protogen) {
        return protogen.getUUID().equals(protogenId);
    }

    @Override
    public void registerProtogen(ServerPlayer player, ProtogenEntity protogen) {
        if (isRegistered(protogen)) return;
        protogen.leaveWorkStation();
        releaseProtogen();
        protogenId = protogen.getUUID();
        protogen.setWorkStation(worldPosition);
        sync();
    }

    @Override
    public void unregisterProtogen(ServerPlayer player) {
        releaseProtogen();
        sync();
    }

    public void forgetProtogen(UUID id) {
        if (id.equals(protogenId)) {
            protogenId = null;
            sync();
        }
    }

    private void releaseProtogen() {
        if (protogenId != null && level instanceof ServerLevel serverLevel
                && serverLevel.getEntity(protogenId) instanceof ProtogenEntity protogen
                && worldPosition.equals(protogen.getWorkStation())) {
            protogen.setWorkStation(null);
        }
        protogenId = null;
    }

    @Override
    public void onBroken() {
        releaseProtogen();
        dropAll(inventory);
    }

    private boolean canStore(List<ItemStack> stacks) {
        ItemStackHandler copy = new ItemStackHandler(inventory.getSlots());
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            copy.setStackInSlot(slot, inventory.getStackInSlot(slot).copy());
        }
        for (ItemStack stack : stacks) {
            if (!insert(copy, stack.copy()).isEmpty()) return false;
        }
        return true;
    }

    private void store(List<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            ItemStack rest = insert(inventory, stack);
            if (!rest.isEmpty()) {
                Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ(), rest);
            }
        }
    }

    private ItemStack insert(ItemStackHandler handler, ItemStack stack) {
        ItemStack rest = stack;
        for (int slot = firstOutputSlot(); slot < handler.getSlots() && !rest.isEmpty(); slot++) {
            rest = handler.insertItem(slot, rest, false);
        }
        return rest;
    }

    @Nullable
    public BlockPos findTarget(ProtogenEntity protogen) {
        return switch (kind()) {
            case FARMING -> findFarmTarget(protogen);
            case MINING -> findMineTarget(protogen);
            default -> null;
        };
    }

    public boolean isValidTarget(BlockPos pos) {
        return switch (kind()) {
            case FARMING -> isFarmTarget(pos, true);
            case MINING -> isMinable(pos);
            default -> false;
        };
    }

    public int workTicks(BlockPos pos) {
        return kind() == StationKind.MINING ? mineTicks(pos) : 10;
    }

    public ItemStack toolFor(BlockPos pos) {
        if (kind() != StationKind.MINING || level == null) return ItemStack.EMPTY;
        int slot = toolSlotFor(level.getBlockState(pos));
        return slot < 0 ? ItemStack.EMPTY : inventory.getStackInSlot(slot);
    }

    public void performWork(BlockPos pos, ProtogenEntity protogen) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        switch (kind()) {
            case FARMING -> farm(serverLevel, pos, protogen);
            case MINING -> mine(serverLevel, pos, protogen);
            default -> {
            }
        }
    }

    @Nullable
    private BlockPos findFarmTarget(ProtogenEntity protogen) {
        boolean hasSeeds = seedSlot(null, null) >= 0;
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed(worldPosition.offset(-WORK_RADIUS, -FARM_HEIGHT, -WORK_RADIUS),
                worldPosition.offset(WORK_RADIUS, FARM_HEIGHT, WORK_RADIUS))) {
            if (!isFarmTarget(pos, hasSeeds)) continue;
            double distance = pos.distSqr(protogen.blockPosition());
            if (distance < bestDistance) {
                bestDistance = distance;
                best = pos.immutable();
            }
        }
        return best;
    }

    private boolean isFarmTarget(BlockPos pos, boolean hasSeeds) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof CropBlock crop) return crop.isMaxAge(state);
        return hasSeeds && state.isAir() && level.getBlockState(pos.below()).getBlock() instanceof FarmBlock
                && seedSlot(pos, null) >= 0;
    }

    private int seedSlot(@Nullable BlockPos pos, @Nullable Block preferred) {
        int fallback = -1;
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            if (!(inventory.getStackInSlot(slot).getItem() instanceof BlockItem item) || !(item.getBlock() instanceof CropBlock crop)) continue;
            if (pos != null && !crop.defaultBlockState().canSurvive(level, pos)) continue;
            if (crop == preferred) return slot;
            if (fallback < 0) fallback = slot;
        }
        return fallback;
    }

    private void farm(ServerLevel serverLevel, BlockPos pos, ProtogenEntity protogen) {
        BlockState state = serverLevel.getBlockState(pos);
        if (state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state)) {
            List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, protogen, ItemStack.EMPTY);
            if (!canStore(drops)) {
                outputFull = true;
                return;
            }
            serverLevel.destroyBlock(pos, false, protogen);
            store(drops);
            plant(serverLevel, pos, crop, protogen);
        } else if (state.isAir()) {
            plant(serverLevel, pos, null, protogen);
        }
    }

    private void plant(ServerLevel serverLevel, BlockPos pos, @Nullable Block preferred, ProtogenEntity protogen) {
        int slot = seedSlot(pos, preferred);
        if (slot < 0 || !(inventory.getStackInSlot(slot).getItem() instanceof BlockItem item)) return;
        serverLevel.setBlock(pos, item.getBlock().defaultBlockState(), Block.UPDATE_ALL);
        serverLevel.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F);
        serverLevel.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(protogen, item.getBlock().defaultBlockState()));
        inventory.extractItem(slot, 1, false);
    }

    private int topY() {
        return worldPosition.getY();
    }

    private int totalLayers() {
        return level == null ? 0 : topY() - level.getMinBuildHeight() + 1;
    }

    private boolean pitFinished() {
        return topY() - mineDepth < level.getMinBuildHeight();
    }

    private BlockPos pitCell(int across, int forward, int y) {
        Direction facing = getBlockState().getValue(ProtogenStationBlock.FACING);
        Direction side = facing.getClockWise();
        BlockPos origin = worldPosition.relative(facing, PIT_OFFSET).relative(side, -PIT_SIZE / 2);
        return new BlockPos(origin.getX() + side.getStepX() * across + facing.getStepX() * forward, y,
                origin.getZ() + side.getStepZ() * across + facing.getStepZ() * forward);
    }

    private static boolean isStairStep(int across, int forward, int depth) {
        int index = depth % PIT_PERIMETER;
        int edge = PIT_SIZE - 1;
        int side = index / edge;
        int offset = index % edge;
        return switch (side) {
            case 0 -> across == offset && forward == 0;
            case 1 -> across == edge && forward == offset;
            case 2 -> across == edge - offset && forward == edge;
            default -> across == 0 && forward == edge - offset;
        };
    }

    @Nullable
    private BlockPos findMineTarget(ProtogenEntity protogen) {
        for (int scanned = 0; scanned < 8 && !pitFinished(); scanned++) {
            BlockPos target = nearestInLayer(mineDepth, protogen);
            if (target != null) return target;
            mineDepth++;
            setChanged();
        }
        return null;
    }

    @Nullable
    private BlockPos nearestInLayer(int depth, ProtogenEntity protogen) {
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (int across = 0; across < PIT_SIZE; across++) {
            for (int forward = 0; forward < PIT_SIZE; forward++) {
                if (isStairStep(across, forward, depth)) continue;
                BlockPos pos = pitCell(across, forward, topY() - depth);
                if (!isMinable(pos)) continue;
                double distance = pos.distSqr(protogen.blockPosition());
                if (distance < bestDistance) {
                    bestDistance = distance;
                    best = pos;
                }
            }
        }
        return best;
    }

    private boolean isMinable(BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.isAir() || state.hasBlockEntity() || state.getBlock() instanceof LiquidBlock
                || state.getBlock() instanceof BedBlock || state.is(Blocks.BUBBLE_COLUMN)) {
            return false;
        }
        return state.getDestroySpeed(level, pos) >= 0;
    }

    private int toolSlotFor(BlockState state) {
        int best = -1;
        float bestSpeed = 0;
        for (int slot = 0; slot < TOOL_SLOTS; slot++) {
            ItemStack tool = inventory.getStackInSlot(slot);
            if (tool.isEmpty()) continue;
            if (state.requiresCorrectToolForDrops() && !tool.isCorrectToolForDrops(state)) continue;
            float speed = tool.getDestroySpeed(state);
            if (speed > bestSpeed) {
                best = slot;
                bestSpeed = speed;
            }
        }
        return best;
    }

    private int mineTicks(BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        ItemStack tool = toolFor(pos);
        float speed = tool.isEmpty() ? 1.0F : Math.max(1.0F, tool.getDestroySpeed(state));
        return Mth.clamp(Mth.ceil(state.getDestroySpeed(level, pos) * 60.0F / speed), 6, 200);
    }

    private void mine(ServerLevel serverLevel, BlockPos pos, ProtogenEntity protogen) {
        if (!isMinable(pos)) return;
        BlockState state = serverLevel.getBlockState(pos);
        int slot = toolSlotFor(state);
        if (slot < 0 && state.requiresCorrectToolForDrops()) {
            missingTool = true;
            return;
        }
        ItemStack tool = slot < 0 ? ItemStack.EMPTY : inventory.getStackInSlot(slot).copy();
        List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, protogen, tool);
        if (!canStore(drops)) {
            outputFull = true;
            return;
        }
        serverLevel.destroyBlock(pos, false, protogen);
        store(drops);
        if (!tool.isEmpty()) {
            tool.hurtAndBreak(1, serverLevel, null, item -> {
            });
            inventory.setStackInSlot(slot, tool);
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ProtogenStationMenu(containerId, playerInventory, this, data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        if (protogenId != null) tag.putUUID("protogen", protogenId);
        tag.putInt("research_progress", researchProgress);
        tag.putInt("bottles_made", bottlesMade);
        tag.putInt("mine_depth", mineDepth);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        protogenId = tag.hasUUID("protogen") ? tag.getUUID("protogen") : null;
        researchProgress = tag.getInt("research_progress");
        bottlesMade = tag.getInt("bottles_made");
        mineDepth = tag.getInt("mine_depth");
    }
}
