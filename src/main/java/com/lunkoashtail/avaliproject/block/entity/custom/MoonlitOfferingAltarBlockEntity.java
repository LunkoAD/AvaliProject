package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.entity.ModBlockEntities;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class MoonlitOfferingAltarBlockEntity extends BlockEntity {
    public static final int SLOTS = 3;
    private static final int OFFERING_TIME = 600;
    private static final int FULL_MOON_PHASE = 0;

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
    private int progress;

    public MoonlitOfferingAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MOONLIT_OFFERING_ALTAR_BE.get(), pos, state);
    }

    public boolean insert(ItemStack stack) {
        for (int i = 0; i < SLOTS; i++) {
            if (items.get(i).isEmpty()) {
                items.set(i, stack.copyWithCount(1));
                progress = 0;
                setChanged();
                return true;
            }
        }
        return false;
    }

    public ItemStack takeLast() {
        for (int i = SLOTS - 1; i >= 0; i--) {
            if (!items.get(i).isEmpty()) {
                ItemStack taken = items.get(i);
                items.set(i, ItemStack.EMPTY);
                progress = 0;
                setChanged();
                return taken;
            }
        }
        return ItemStack.EMPTY;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MoonlitOfferingAltarBlockEntity altar) {
        if (!level.isNight() || !level.canSeeSky(pos.above())) return;
        Offering offering = altar.findOffering();
        if (offering == Offering.NONE) {
            altar.progress = 0;
            return;
        }
        boolean fullMoon = level.getMoonPhase() == FULL_MOON_PHASE;
        altar.progress += fullMoon ? 2 : 1;
        if (level instanceof ServerLevel serverLevel && level.getGameTime() % 20 == 0) {
            serverLevel.sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5,
                    2, 0.25, 0.1, 0.25, 0.01);
        }
        if (altar.progress >= OFFERING_TIME) {
            altar.progress = 0;
            altar.complete(level, pos, offering, fullMoon);
        }
    }

    private Offering findOffering() {
        if (has(s -> s.is(ModItems.PEARL.get())) && has(s -> s.is(ModBlocks.SEA_GLASS.get().asItem())) && has(s -> s.is(ModItems.MOONSTONE.get()))) {
            return Offering.PENDANT;
        }
        if (has(s -> s.is(ModItems.SHELL.get())) && has(s -> s.is(Items.BONE))) {
            return Offering.BONE_JEWELRY;
        }
        if (has(s -> s.is(ModTags.Items.TROPICAL_FRUITS)) && has(s -> s.is(ModTags.Items.MANOKIT_GEMS))) {
            return Offering.FRUIT_AND_GEM;
        }
        return Offering.NONE;
    }

    private boolean has(Predicate<ItemStack> predicate) {
        for (ItemStack stack : items) {
            if (predicate.test(stack)) return true;
        }
        return false;
    }

    private ItemStack consume(Predicate<ItemStack> predicate) {
        for (int i = 0; i < SLOTS; i++) {
            if (predicate.test(items.get(i))) {
                ItemStack taken = items.get(i);
                items.set(i, ItemStack.EMPTY);
                return taken;
            }
        }
        return ItemStack.EMPTY;
    }

    private void complete(Level level, BlockPos pos, Offering offering, boolean fullMoon) {
        switch (offering) {
            case PENDANT -> {
                consume(s -> s.is(ModItems.PEARL.get()));
                consume(s -> s.is(ModBlocks.SEA_GLASS.get().asItem()));
                consume(s -> s.is(ModItems.MOONSTONE.get()));
                emit(level, pos, new ItemStack(ModItems.PENDANT.get()));
                if (fullMoon) dropExperience(level, pos, 5);
            }
            case BONE_JEWELRY -> {
                consume(s -> s.is(ModItems.SHELL.get()));
                consume(s -> s.is(Items.BONE));
                emit(level, pos, new ItemStack(ModItems.BONE_JEWELRY.get(), fullMoon ? 2 : 1));
            }
            case FRUIT_AND_GEM -> {
                consume(s -> s.is(ModTags.Items.TROPICAL_FRUITS));
                dropExperience(level, pos, fullMoon ? 10 : 5);
                if (fullMoon && level.getRandom().nextFloat() < 0.25F) {
                    for (ItemStack stack : items) {
                        if (stack.is(ModTags.Items.MANOKIT_GEMS)) {
                            emit(level, pos, stack.copyWithCount(1));
                            break;
                        }
                    }
                }
            }
            default -> {
            }
        }
        level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, fullMoon ? 1.4F : 1.0F);
        setChanged();
    }

    private static void emit(Level level, BlockPos pos, ItemStack stack) {
        ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, stack);
        entity.setDeltaMovement(0.0, 0.15, 0.0);
        level.addFreshEntity(entity);
    }

    private static void dropExperience(Level level, BlockPos pos, int amount) {
        if (level instanceof ServerLevel serverLevel) {
            ExperienceOrb.award(serverLevel, pos.getCenter().add(0.0, 0.6, 0.0), amount);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("Progress", progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items.clear();
        ContainerHelper.loadAllItems(tag, items, registries);
        progress = tag.getInt("Progress");
    }

    private enum Offering {
        NONE,
        PENDANT,
        BONE_JEWELRY,
        FRUIT_AND_GEM
    }
}
