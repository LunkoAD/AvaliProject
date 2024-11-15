package com.lunkoashtail.avaliproject.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SimpleDrinkableItem extends Item {

    public SimpleDrinkableItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        // Set the item to use the drinking animation
        return UseAnim.DRINK;
    }

    public int getUseDuration(ItemStack stack) {
        // Duration for drinking animation (32 is typical for drinking)
        return 32;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        // Play drink sound when used by a player
        if (entity instanceof Player player && !level.isClientSide) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return super.finishUsingItem(stack, level, entity);
    }
}