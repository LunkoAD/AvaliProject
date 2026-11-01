package com.lunkoashtail.avaliproject.block.entity.custom;

import com.lunkoashtail.avaliproject.entity.custom.ProtogenEntity;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface ProtogenRegistrar {
    int ROSTER_RADIUS = 16;

    @Nullable
    UUID registeredProtogen();

    void registerProtogen(ServerPlayer player, ProtogenEntity protogen);

    void unregisterProtogen(ServerPlayer player);
}
