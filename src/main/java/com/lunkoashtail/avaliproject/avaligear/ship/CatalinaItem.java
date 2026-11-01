package com.lunkoashtail.avaliproject.avaligear.ship;

import com.lunkoashtail.avaliproject.avaligear.AvaliGear;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class CatalinaItem extends Item {
    public CatalinaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel server)) return InteractionResult.SUCCESS;
        BlockPos ground = context.getClickedPos();
        Player player = context.getPlayer();
        float yaw = player != null ? player.getYRot() : 0.0F;
        CatalinaEntity ship = new CatalinaEntity(AvaliGear.CATALINA, server);
        ship.moveTo(ground.getX() + 0.5, ground.getY() + 1.0, ground.getZ() + 0.5, yaw, 0.0F);
        if (!ship.landHere()) {
            if (player != null) {
                player.displayClientMessage(Component.translatable("message.avaliproject.catalina.no_room"), true);
            }
            return InteractionResult.FAIL;
        }
        server.addFreshEntity(ship);
        ItemStack stack = context.getItemInHand();
        if (player == null || !player.getAbilities().instabuild) stack.shrink(1);
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.avaliproject.catalina"));
    }
}
