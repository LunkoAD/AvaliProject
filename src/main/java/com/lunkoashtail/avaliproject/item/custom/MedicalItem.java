package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.client.ClientPayloadHandlers;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.vitals.MedicalAction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MedicalItem extends Item {

    private final MedicalAction action;
    @Nullable private final MedicalAction sneakAction;

    public MedicalItem(MedicalAction action, Properties properties) {
        this(action, null, properties);
    }

    public MedicalItem(MedicalAction action, @Nullable MedicalAction sneakAction, Properties properties) {
        super(properties);
        this.action = action;
        this.sneakAction = sneakAction;
    }

    public MedicalAction actionFor(Player player) {
        return sneakAction != null && player.isShiftKeyDown() ? sneakAction : action;
    }

    public boolean supports(MedicalAction candidate) {
        return candidate == action || candidate == sneakAction;
    }

    private static MedicalAction selfActionOf(ItemStack stack, @Nullable LivingEntity entity) {
        MedicalItem item = (MedicalItem) stack.getItem();
        if (entity instanceof Player player) return item.actionFor(player);
        return item.action;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        MedicalAction chosen = actionFor(player);
        if (!chosen.speciesAllowed(player)) {
            if (level.isClientSide()) player.displayClientMessage(Component.literal("This won't do anything for you."), true);
            return InteractionResultHolder.fail(stack);
        }
        if (chosen.target == MedicalAction.Target.LIMB) {
            if (level.isClientSide()) ClientPayloadHandlers.openMedicalLimbSelection(player, hand, player.getId(), chosen);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        if (chosen.useTicks <= 0) {
            if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                finish(serverPlayer, serverPlayer, null, hand, chosen);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide() && entity instanceof ServerPlayer player) {
            InteractionHand hand = player.getUsedItemHand();
            finish(player, player, null, hand, selfActionOf(stack, player));
            return player.getItemInHand(hand);
        }
        return stack;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof Player patient) || patient == player) return InteractionResult.PASS;
        MedicalAction chosen = actionFor(player);
        if (patient.getData(ModAttachments.SPECIES) != Species.EXPIE && chosen != MedicalAction.ANTI_DEPRESSANTS) {
            if (player.level().isClientSide()) player.displayClientMessage(Component.literal("They're not an expie."), true);
            return InteractionResult.FAIL;
        }
        if (chosen.target == MedicalAction.Target.LIMB) {
            if (player.level().isClientSide()) ClientPayloadHandlers.openMedicalLimbSelection(player, hand, patient.getId(), chosen);
            return InteractionResult.SUCCESS;
        }
        if (!player.level().isClientSide() && player instanceof ServerPlayer user && patient instanceof ServerPlayer serverPatient) {
            finish(user, serverPatient, null, hand, chosen);
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide());
    }

    public static void finish(ServerPlayer user, ServerPlayer patient, @Nullable Limb limb, InteractionHand hand, MedicalAction action) {
        ItemStack stack = user.getItemInHand(hand);
        if (!(stack.getItem() instanceof MedicalItem)) return;
        if (!action.speciesAllowed(patient)) return;
        if (action.target == MedicalAction.Target.LIMB && (limb == null || !action.limbs.allowed.contains(limb))) return;

        MedicalAction.Result result = action.apply(user, patient, limb, stack);
        switch (result) {
            case CONSUME -> {
                boolean bloodBag = action == MedicalAction.BLOOD_BAG;
                if (!user.getAbilities().instabuild) stack.shrink(1);
                if (bloodBag) {
                    ItemStack empty = new ItemStack(ModItems.EMPTY_BLOOD_BAG.get());
                    if (stack.isEmpty()) user.setItemInHand(hand, empty);
                    else if (!user.getInventory().add(empty)) user.drop(empty, false);
                }
            }
            case DAMAGE -> stack.hurtAndBreak(1, user, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            case KEEP, FAIL -> {}
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        if (action.target == MedicalAction.Target.SELF) return action.animation;
        return sneakAction != null ? sneakAction.animation : UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return selfActionOf(stack, entity).useTicks;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.avaliproject.medical." + action.name().toLowerCase()));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
