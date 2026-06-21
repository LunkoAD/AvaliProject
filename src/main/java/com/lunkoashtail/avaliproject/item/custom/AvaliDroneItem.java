package com.lunkoashtail.avaliproject.item.custom;

import com.lunkoashtail.avaliproject.event.AvaliDroneSpawn;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;

public class AvaliDroneItem extends Item {
    public AvaliDroneItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        AvaliDroneSpawn.execute(world, entity.getX(), entity.getY(), entity.getZ());
        return ar;
    }
}