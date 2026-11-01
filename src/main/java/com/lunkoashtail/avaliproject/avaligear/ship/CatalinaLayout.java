package com.lunkoashtail.avaliproject.avaligear.ship;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CatalinaLayout {
    public static final int HULL = 0;
    public static final int RAMP = 1;
    public static final int CONSOLE = 2;

    public record Cell(int dx, int dy, int dz, int height, int kind) {
    }

    public static final List<Cell> CELLS;

    public static final double BAY_X0 = -40, BAY_X1 = 40, BAY_Y0 = 16, BAY_Y1 = 80, BAY_Z0 = -24, BAY_Z1 = 152;

    static {
        Map<Long, Cell> cells = new LinkedHashMap<>();
        box(cells, -40, 40, 0, 16, -24, 152, 16, HULL);
        box(cells, -56, -40, 0, 80, -24, 152, 16, HULL);
        box(cells, 40, 56, 0, 80, -24, 152, 16, HULL);
        box(cells, -56, 56, 80, 96, -24, 152, 16, HULL);
        box(cells, -56, 56, 0, 96, -168, -24, 16, HULL);
        box(cells, -56, -40, 16, 64, 152, 168, 16, HULL);
        box(cells, 40, 56, 16, 64, 152, 168, 16, HULL);
        box(cells, -56, 56, 64, 96, 152, 168, 16, HULL);
        int[][] ramp = {{152, 12}, {168, 8}, {184, 5}, {200, 2}};
        for (int[] step : ramp) {
            box(cells, -40, 40, 0, 16, step[0], step[0] + 16, step[1], RAMP);
        }
        box(cells, 8, 40, 16, 32, -24, -8, 16, CONSOLE);
        CELLS = Collections.unmodifiableList(new ArrayList<>(cells.values()));
    }

    private CatalinaLayout() {
    }

    private static void box(Map<Long, Cell> cells, int x0, int x1, int y0, int y1, int z0, int z1, int height, int kind) {
        for (int i = Math.floorDiv(x0 + 8, 16); i < Math.floorDiv(x1 + 8, 16); i++) {
            for (int j = Math.floorDiv(y0, 16); j < Math.floorDiv(y1, 16); j++) {
                for (int k = Math.floorDiv(z0 + 8, 16); k < Math.floorDiv(z1 + 8, 16); k++) {
                    cells.put(BlockPos.asLong(i, j, k), new Cell(i, j, k, height, kind));
                }
            }
        }
    }

    public static Vec3 toWorld(double lx, double ly, double lz, float yaw) {
        return new Vec3(lx / 16.0, ly / 16.0, lz / 16.0).yRot((float) Math.toRadians(180.0F - yaw));
    }

    public static Vec3 toLocalPx(Vec3 worldOffset, float yaw) {
        return worldOffset.yRot((float) -Math.toRadians(180.0F - yaw)).scale(16.0);
    }

    public static BlockPos cellToWorld(BlockPos origin, Cell c, float yaw) {
        Vec3 v = toWorld(c.dx() * 16.0, 0, c.dz() * 16.0, yaw);
        return origin.offset((int) Math.round(v.x), c.dy(), (int) Math.round(v.z));
    }

    public static boolean insideBay(Vec3 localPx) {
        return localPx.x > BAY_X0 && localPx.x < BAY_X1 && localPx.y > BAY_Y0 - 4 && localPx.y < BAY_Y1
                && localPx.z > BAY_Z0 && localPx.z < BAY_Z1 + 64;
    }
}
