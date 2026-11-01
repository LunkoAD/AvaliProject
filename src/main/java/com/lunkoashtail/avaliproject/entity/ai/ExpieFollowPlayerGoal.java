package com.lunkoashtail.avaliproject.entity.ai;

import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class ExpieFollowPlayerGoal extends Goal {
    private static final float START_DISTANCE = 5f;
    private static final float STOP_DISTANCE = 2f;
    private static final double SPEED_MODIFIER = 1.4;

    private static final List<String> CHASE_LINES = List.of(
            "P-please don't leave me...!",
            "W-wait, don't go!",
            "Don't leave me alone, please!",
            "I- I don't want to be by myself again...",
            "W-wait for me, please!",
            "Please, just... stay close...?"
    );
    private static final int CHASE_LINE_COOLDOWN_MIN_TICKS = 1200;
    private static final int CHASE_LINE_COOLDOWN_MAX_TICKS = 2000;
    private static final double CHASE_LINE_MAX_DISTANCE = 16.0;

    private final ExpieEntity expie;
    private final PathNavigation navigation;
    private Player target;
    private int timeToRecalcPath;
    private long nextChaseLineTick;

    public ExpieFollowPlayerGoal(ExpieEntity expie) {
        this.expie = expie;
        this.navigation = expie.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        UUID clingyTo = expie.getClingyTarget();
        if (clingyTo == null) return false;
        if (!(expie.level().getPlayerByUUID(clingyTo) instanceof Player player)) return false;
        if (expie.distanceToSqr(player) < (double) (START_DISTANCE * START_DISTANCE)) return false;

        this.target = player;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (target == null || !target.isAlive()) return false;
        if (expie.getClingyTarget() == null) return false;
        return !navigation.isDone() && expie.distanceToSqr(target) > (double) (STOP_DISTANCE * STOP_DISTANCE);
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
        maybeSayChaseLine();
    }

    @Override
    public void stop() {
        target = null;
        navigation.stop();
    }

    @Override
    public void tick() {
        if (target == null) return;
        expie.getLookControl().setLookAt(target, 10f, (float) expie.getMaxHeadXRot());
        if (--timeToRecalcPath <= 0) {
            timeToRecalcPath = adjustedTickDelay(5);
            navigation.moveTo(target, SPEED_MODIFIER);
        }
        maybeSayChaseLine();
    }

    private void maybeSayChaseLine() {
        if (expie.level().getGameTime() < nextChaseLineTick) return;
        if (target == null || expie.distanceToSqr(target) > CHASE_LINE_MAX_DISTANCE * CHASE_LINE_MAX_DISTANCE) return;
        nextChaseLineTick = expie.level().getGameTime()
                + CHASE_LINE_COOLDOWN_MIN_TICKS
                + expie.getRandom().nextInt(CHASE_LINE_COOLDOWN_MAX_TICKS - CHASE_LINE_COOLDOWN_MIN_TICKS);
        if (!(target instanceof ServerPlayer serverPlayer)) return;
        String line = CHASE_LINES.get(expie.getRandom().nextInt(CHASE_LINES.size()));
        serverPlayer.displayClientMessage(Component.literal(expie.getDisplayName().getString() + " " + line), false);
    }
}
