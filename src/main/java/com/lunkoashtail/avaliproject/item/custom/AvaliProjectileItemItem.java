package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.entity.custom.AvaliProjectileEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

public class AvaliProjectileItemItem extends Item {
    public AvaliProjectileItemItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = InteractionResultHolder.fail(entity.getItemInHand(hand));
        if (entity.getAbilities().instabuild || findAmmo(entity) != ItemStack.EMPTY) {
            ar = InteractionResultHolder.fail(entity.getItemInHand(hand));
            entity.startUsingItem(hand);
        }
        return ar;
    }

    @Override
    public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entity, int time) {
        if (!world.isClientSide() && entity instanceof ServerPlayer player) {
            ItemStack stack = findAmmo(player);
            if (player.getAbilities().instabuild || stack != ItemStack.EMPTY) {
                AvaliProjectileEntity projectile = AvaliProjectileEntity.shoot(world, entity, world.getRandom());
                if (player.getAbilities().instabuild) {
                    projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                } else {
                    if (stack.isDamageableItem()) {
                        if (world instanceof ServerLevel serverLevel)
                            stack.hurtAndBreak(1, serverLevel, player, _stkprov -> {
                            });
                    } else {
                        stack.shrink(1);
                    }
                }
            }
        }
    }

    private ItemStack findAmmo(Player player) {
        return new ItemStack(AvaliProjectileEntity.PROJECTILE_ITEM.getItem());
    }
}
