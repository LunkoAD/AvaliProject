package com.lunkoashtail.avaliproject.network;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.component.DrugDosage;
import com.lunkoashtail.avaliproject.component.FluidAmount;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import com.lunkoashtail.avaliproject.item.custom.AutoinjectorItem;
import com.lunkoashtail.avaliproject.item.custom.MorphineItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AutoinjectorLoadPayload(boolean mainHand, int injectorSlot, float amount) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<AutoinjectorLoadPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "autoinjector_load"));

    public static final StreamCodec<ByteBuf, AutoinjectorLoadPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, AutoinjectorLoadPayload::mainHand,
            ByteBufCodecs.VAR_INT, AutoinjectorLoadPayload::injectorSlot,
            ByteBufCodecs.FLOAT, AutoinjectorLoadPayload::amount,
            AutoinjectorLoadPayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(AutoinjectorLoadPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            ItemStack vial = player.getItemInHand(payload.mainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
            if (!(vial.getItem() instanceof MorphineItem)) return;
            if (payload.injectorSlot() < 0 || payload.injectorSlot() >= player.getInventory().getContainerSize()) return;
            ItemStack injector = player.getInventory().getItem(payload.injectorSlot());
            if (!(injector.getItem() instanceof AutoinjectorItem) || injector.getCount() != 1) return;

            float available = MorphineItem.dosageOf(vial);
            float room = AutoinjectorItem.CAPACITY_ML - AutoinjectorItem.contentsOf(injector);
            float moved = Math.max(0f, Math.min(payload.amount(), Math.min(available, room)));
            if (moved <= 0f) return;

            injector.set(ModDataComponents.FLUID_AMOUNT, new FluidAmount(AutoinjectorItem.contentsOf(injector) + moved));
            float left = available - moved;
            if (left <= 0.5f) vial.shrink(1);
            else vial.set(ModDataComponents.DRUG_DOSAGE, new DrugDosage(left));
        });
    }
}
