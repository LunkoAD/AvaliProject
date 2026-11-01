package com.lunkoashtail.avaliproject.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public enum FluidType {
    WATER,
    BIOCHEM,
    OPIUM,
    MORPHINE,
    SALINE,
    BOILED_WATER,
    IRRADIATED_WATER;

    public static final Codec<FluidType> CODEC = Codec.STRING.xmap(FluidType::valueOf, FluidType::name);

    public static final StreamCodec<ByteBuf, FluidType> STREAM_CODEC =
            ByteBufCodecs.VAR_INT.map(id -> FluidType.values()[id], FluidType::ordinal);

    public Component displayName() {
        return switch (this) {
            case WATER -> Component.literal("Water");
            case BIOCHEM -> Component.literal("Bio-chem");
            case OPIUM -> Component.literal("Opium");
            case MORPHINE -> Component.literal("Morphine");
            case SALINE -> Component.literal("Saline");
            case BOILED_WATER -> Component.literal("Boiled Water");
            case IRRADIATED_WATER -> Component.literal("Irradiated Water");
        };
    }
}
