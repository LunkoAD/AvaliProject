package com.lunkoashtail.avaliproject.entity.ai;

import com.lunkoashtail.avaliproject.entity.ExpieContextLines;
import com.lunkoashtail.avaliproject.entity.ExpieContextLines.Context;
import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.phys.AABB;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExpieDialogueController {
    private static final int CONTEXT_COOLDOWN_MIN_TICKS = 300;
    private static final int CONTEXT_COOLDOWN_MAX_TICKS = 900;

    private static final int GLOBAL_COOLDOWN_MIN_TICKS = 160;
    private static final int GLOBAL_COOLDOWN_MAX_TICKS = 260;

    private static final int POLL_INTERVAL_TICKS = 40;
    private static final double MONSTER_SCAN_RADIUS = 16.0;
    private static final double DIALOGUE_RANGE = 10.0;

    private final ExpieEntity expie;
    private final Map<Context, Long> nextContextTick = new EnumMap<>(Context.class);
    private long nextGlobalTick;

    public ExpieDialogueController(ExpieEntity expie) {
        this.expie = expie;
    }

    public void tick() {
        if (expie.level().isClientSide()) return;
        if (expie.level().getGameTime() % POLL_INTERVAL_TICKS != 0) return;

        Player player = bondedOrNearestPlayer();
        if (player == null || player.distanceToSqr(expie) > DIALOGUE_RANGE * DIALOGUE_RANGE) return;

        if (hasNearbyMonster() && trySay(Context.NEARBY_MONSTER, player)) return;
        if (player.isSleeping()) { trySay(Context.SLEEPING, player); return; }

        AbstractContainerMenu menu = player.containerMenu;
        if (menu instanceof FurnaceMenu) { trySay(Context.SMELTING, player); return; }
        if (menu instanceof CraftingMenu) { trySay(Context.CRAFTING, player); }
    }

    public void onNearbyBlockMined(Player player) {
        if (player.distanceToSqr(expie) > DIALOGUE_RANGE * DIALOGUE_RANGE) return;
        trySay(Context.MINING, player);
    }

    private boolean hasNearbyMonster() {
        AABB area = expie.getBoundingBox().inflate(MONSTER_SCAN_RADIUS);
        return expie.level().getEntitiesOfClass(Monster.class, area).size() > 0;
    }

    private boolean trySay(Context context, Player player) {
        long now = expie.level().getGameTime();
        if (now < nextGlobalTick) return false;
        Long contextReady = nextContextTick.get(context);
        if (contextReady != null && now < contextReady) return false;
        if (!(player instanceof ServerPlayer serverPlayer)) return false;

        boolean anxious = expie.getMoodValue() < 0f;
        List<String> lines = ExpieContextLines.linesFor(context, anxious);
        String line = lines.get(expie.getRandom().nextInt(lines.size()));
        serverPlayer.displayClientMessage(Component.literal(expie.getDisplayName().getString() + " " + line), false);

        nextGlobalTick = now + GLOBAL_COOLDOWN_MIN_TICKS
                + expie.getRandom().nextInt(GLOBAL_COOLDOWN_MAX_TICKS - GLOBAL_COOLDOWN_MIN_TICKS);
        nextContextTick.put(context, now + CONTEXT_COOLDOWN_MIN_TICKS
                + expie.getRandom().nextInt(CONTEXT_COOLDOWN_MAX_TICKS - CONTEXT_COOLDOWN_MIN_TICKS));
        return true;
    }

    private Player bondedOrNearestPlayer() {
        UUID clingyTo = expie.getClingyTarget();
        if (clingyTo != null && expie.level().getPlayerByUUID(clingyTo) instanceof Player bonded) {
            return bonded;
        }
        return expie.level().getNearestPlayer(expie, DIALOGUE_RANGE);
    }
}
