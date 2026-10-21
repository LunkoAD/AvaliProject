package com.lunkoashtail.avaliproject.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

public abstract class SpeciesCreatureEntity extends TamableAnimal {
    private static final float TAME_CHANCE = 1.0F / 3.0F;
    private static final float FEED_HEAL = 4.0F;

    protected SpeciesCreatureEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    protected abstract boolean isTameItem(ItemStack stack);

    @Override
    public boolean isFood(ItemStack stack) {
        return isTameItem(stack);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        registerCombatGoals();
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.1, this::isTameItem, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
    }

    protected void registerCombatGoals() {
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean client = level().isClientSide();

        if (!isTame() && isTameItem(stack)) {
            if (!client) {
                stack.consume(1, player);
                tryTame(player);
            }
            return InteractionResult.sidedSuccess(client);
        }

        if (isTame() && isOwnedBy(player)) {
            if (isFood(stack) && getHealth() < getMaxHealth()) {
                if (!client) {
                    stack.consume(1, player);
                    heal(FEED_HEAL);
                }
                return InteractionResult.sidedSuccess(client);
            }
            InteractionResult ownerResult = ownerInteract(player, hand, stack);
            if (ownerResult != InteractionResult.PASS) {
                return ownerResult;
            }
            if (stack.isEmpty()) {
                if (!client) {
                    setOrderedToSit(!isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    setTarget(null);
                }
                return InteractionResult.sidedSuccess(client);
            }
        }

        return super.mobInteract(player, hand);
    }

    protected InteractionResult ownerInteract(Player player, InteractionHand hand, ItemStack stack) {
        return InteractionResult.PASS;
    }

    private void tryTame(Player player) {
        if (this.random.nextFloat() < TAME_CHANCE && !EventHooks.onAnimalTame(this, player)) {
            tame(player);
            this.navigation.stop();
            setTarget(null);
            setOrderedToSit(true);
            level().broadcastEntityEvent(this, (byte) 7);
        } else {
            level().broadcastEntityEvent(this, (byte) 6);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return getType().create(level) instanceof AgeableMob child ? child : null;
    }
}
