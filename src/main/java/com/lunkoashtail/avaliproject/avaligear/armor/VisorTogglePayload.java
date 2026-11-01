package com.lunkoashtail.avaliproject.avaligear.armor;

import com.lunkoashtail.avaliproject.avaligear.AvaliGear;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record VisorTogglePayload() implements CustomPacketPayload {
    public static final Type<VisorTogglePayload> TYPE = new Type<>(AvaliGear.id("visor_toggle"));
    public static final StreamCodec<ByteBuf, VisorTogglePayload> STREAM_CODEC = StreamCodec.unit(new VisorTogglePayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(VisorTogglePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
            if (!(head.getItem() instanceof AvaliArmorItem armor) || !armor.hasVisor()) return;
            boolean open = !head.getOrDefault(AvaliGear.VISOR_OPEN, false);
            head.set(AvaliGear.VISOR_OPEN, open);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    open ? SoundEvents.IRON_TRAPDOOR_OPEN : SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.PLAYERS, 0.6F, 1.4F);
        });
    }
}
