package com.lunkoashtail.avaliproject.entity.ai;

import com.lunkoashtail.avaliproject.block.custom.StationKind;
import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenWorkStationBlockEntity;
import com.lunkoashtail.avaliproject.entity.custom.ProtogenEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ProtogenStationWorkGoal extends Goal {
    private static final int SEARCH_COOLDOWN = 40;
    private static final int STUCK_TICKS = 200;
    private static final double STAY_DISTANCE = 5.0;
    private static final double MINING_REACH = 4.5;
    private static final double FARMING_REACH = 2.5;

    private final ProtogenEntity protogen;
    @Nullable
    private ProtogenWorkStationBlockEntity station;
    @Nullable
    private BlockPos target;
    private int workTicks;
    private int requiredTicks;
    private int travelTicks;
    private int returnTicks;
    private int searchCooldown;

    public ProtogenStationWorkGoal(ProtogenEntity protogen) {
        this.protogen = protogen;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (protogen.getTarget() != null) return false;
        station = protogen.resolveWorkStation();
        return station != null;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        clearTarget();
        protogen.getNavigation().stop();
        protogen.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        station = null;
    }

    @Override
    public void tick() {
        if (station == null) return;
        if (!station.isOperational() || station.kind() == StationKind.RESEARCH) {
            clearTarget();
            protogen.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            stayNear(station.getBlockPos());
            return;
        }
        if (target == null && !acquireTarget()) {
            stayNear(station.getBlockPos());
            return;
        }
        if (!station.isValidTarget(target)) {
            clearTarget();
            return;
        }
        protogen.getLookControl().setLookAt(Vec3.atCenterOf(target));
        if (inReach(target)) {
            work();
        } else {
            travel();
        }
    }

    private boolean acquireTarget() {
        if (searchCooldown-- > 0) return false;
        searchCooldown = SEARCH_COOLDOWN;
        target = station.findTarget(protogen);
        if (target == null) return false;
        searchCooldown = 0;
        workTicks = 0;
        travelTicks = 0;
        requiredTicks = station.workTicks(target);
        protogen.setItemSlot(EquipmentSlot.MAINHAND, station.toolFor(target).copy());
        return true;
    }

    private boolean isMining() {
        return station != null && station.kind() == StationKind.MINING;
    }

    private boolean inReach(BlockPos pos) {
        Vec3 center = Vec3.atCenterOf(pos);
        return isMining()
                ? protogen.getEyePosition().distanceToSqr(center) <= MINING_REACH * MINING_REACH
                : protogen.position().distanceToSqr(center) <= FARMING_REACH * FARMING_REACH;
    }

    private void work() {
        protogen.getNavigation().stop();
        workTicks++;
        if (isMining()) {
            protogen.level().destroyBlockProgress(protogen.getId(), target, workTicks * 10 / Math.max(1, requiredTicks));
        }
        if (workTicks % 8 == 1) protogen.swing(InteractionHand.MAIN_HAND);
        if (workTicks >= requiredTicks) {
            station.performWork(target, protogen);
            clearTarget();
        }
    }

    private void travel() {
        travelTicks++;
        if (travelTicks % 20 == 1) {
            protogen.getNavigation().moveTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5, 1.0);
        }
        if (travelTicks > STUCK_TICKS) {
            travelTicks = 0;
            if (isMining()) {
                teleportNear(target);
            } else {
                clearTarget();
                searchCooldown = SEARCH_COOLDOWN;
            }
        }
    }

    private void stayNear(BlockPos home) {
        if (protogen.distanceToSqr(Vec3.atBottomCenterOf(home)) <= STAY_DISTANCE * STAY_DISTANCE) {
            returnTicks = 0;
            if (protogen.getNavigation().isDone() && protogen.getRandom().nextInt(120) == 0) {
                BlockPos spot = home.offset(protogen.getRandom().nextInt(7) - 3, 0, protogen.getRandom().nextInt(7) - 3);
                protogen.getNavigation().moveTo(spot.getX() + 0.5, spot.getY(), spot.getZ() + 0.5, 0.6);
            }
            return;
        }
        returnTicks++;
        if (returnTicks % 20 == 1) {
            protogen.getNavigation().moveTo(home.getX() + 0.5, home.getY(), home.getZ() + 0.5, 1.0);
        }
        if (returnTicks > STUCK_TICKS) {
            returnTicks = 0;
            teleportNear(home);
        }
    }

    private void teleportNear(BlockPos pos) {
        Level level = protogen.level();
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (BlockPos candidate : BlockPos.betweenClosed(pos.offset(-2, -2, -2), pos.offset(2, 3, 2))) {
            if (!isStandable(level, candidate)) continue;
            double distance = candidate.distSqr(pos);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate.immutable();
            }
        }
        if (best == null) return;
        protogen.getNavigation().stop();
        protogen.teleportTo(best.getX() + 0.5, best.getY(), best.getZ() + 0.5);
    }

    private static boolean isStandable(Level level, BlockPos pos) {
        BlockPos below = pos.below();
        return level.getBlockState(pos).getCollisionShape(level, pos).isEmpty()
                && level.getBlockState(pos.above()).getCollisionShape(level, pos.above()).isEmpty()
                && level.getFluidState(pos).isEmpty()
                && level.getBlockState(below).isFaceSturdy(level, below, Direction.UP);
    }

    private void clearTarget() {
        if (target != null && isMining()) {
            protogen.level().destroyBlockProgress(protogen.getId(), target, -1);
        }
        target = null;
        workTicks = 0;
        travelTicks = 0;
    }
}
