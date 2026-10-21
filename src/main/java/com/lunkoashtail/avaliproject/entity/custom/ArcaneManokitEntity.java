package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class ArcaneManokitEntity extends AbstractManokitEntity {
    private static final int SHEAR_COOLDOWN = 6000;
    private static final float RARE_GEM_CHANCE = 0.2F;
    private static final float MANOKIT_RARE_GEM_CHANCE = 0.35F;
    private static final float SEA_OPAL_CHANCE = 0.6F;
    private static final float MANOKIT_SEA_OPAL_CHANCE = 0.8F;
    private static final float SEA_OPAL_DROP_CHANCE = 0.25F;

    private int shearCooldown;

    public ArcaneManokitEntity(EntityType<? extends ArcaneManokitEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public ManokitVariant getVariant() {
        return ManokitVariant.ARCANE;
    }

    public boolean canBeSheared() {
        return !isBaby() && shearCooldown <= 0;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (shearCooldown > 0) {
            shearCooldown--;
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof ShearsItem) {
            if (!canBeSheared()) {
                return InteractionResult.PASS;
            }
            if (!level().isClientSide()) {
                shearGems(player);
                stack.hurtAndBreak(1, player, getSlotForHand(hand));
            }
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    private void shearGems(Player player) {
        boolean manokit = player.getData(ModAttachments.SPECIES) == Species.MANOKIT;
        int rolls = manokit ? 2 : 1;
        for (int i = 0; i < rolls; i++) {
            spawnAtLocation(new ItemStack(rollGem(manokit)), 1.0F);
        }
        shearCooldown = SHEAR_COOLDOWN;
        level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.2F);
        gameEvent(GameEvent.SHEAR, player);
    }

    private Item rollGem(boolean manokit) {
        float roll = this.random.nextFloat();
        if (roll < (manokit ? MANOKIT_RARE_GEM_CHANCE : RARE_GEM_CHANCE)) {
            List<Item> rare = List.of(ModItems.MOONSTONE.get(), ModItems.SUNSTONE.get(),
                    ModItems.LARIMAR.get(), ModItems.CRYSTALITHIUM_CRYSTAL.get());
            return rare.get(this.random.nextInt(rare.size()));
        }
        if (roll < (manokit ? MANOKIT_SEA_OPAL_CHANCE : SEA_OPAL_CHANCE)) {
            return ModItems.SEA_OPAL.get();
        }
        return ModItems.PEARL.get();
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        if (!isBaby() && this.random.nextFloat() < SEA_OPAL_DROP_CHANCE) {
            spawnAtLocation(new ItemStack(ModItems.SEA_OPAL.get()));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ShearCooldown", shearCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        shearCooldown = tag.getInt("ShearCooldown");
    }
}
