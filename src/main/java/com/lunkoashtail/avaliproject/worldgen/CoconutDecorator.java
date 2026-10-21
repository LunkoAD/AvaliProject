package com.lunkoashtail.avaliproject.worldgen;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.custom.CoconutBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class CoconutDecorator extends TreeDecorator {
    public static final MapCodec<CoconutDecorator> CODEC = Codec.floatRange(0.0F, 1.0F)
            .fieldOf("probability").xmap(CoconutDecorator::new, decorator -> decorator.probability);
    private static final int ATTEMPTS = 3;
    private static final int RADIUS = 2;
    private static final int MAX_DROP = 16;

    private final float probability;

    public CoconutDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.COCONUT.get();
    }

    @Override
    public void place(Context context) {
        List<BlockPos> logs = context.logs();
        if (logs.isEmpty()) return;
        RandomSource random = context.random();
        BlockPos top = logs.get(logs.size() - 1);
        for (int i = 0; i < ATTEMPTS; i++) {
            if (random.nextFloat() >= probability) continue;
            int dx = random.nextIntBetweenInclusive(-RADIUS, RADIUS);
            int dz = random.nextIntBetweenInclusive(-RADIUS, RADIUS);
            if (dx == 0 && dz == 0) continue;
            BlockPos.MutableBlockPos cursor = top.offset(dx, 0, dz).mutable();
            for (int drop = 0; drop < MAX_DROP; drop++, cursor.move(0, -1, 0)) {
                if (!context.isAir(cursor)) continue;
                BlockPos below = cursor.below();
                if (context.level().isStateAtPosition(below, state -> state.is(BlockTags.SAND) || state.is(BlockTags.DIRT))) {
                    context.setBlock(cursor.immutable(), ModBlocks.COCONUT.get().defaultBlockState().setValue(CoconutBlock.NATURAL, true));
                    break;
                }
                if (!context.isAir(below) && !context.level().isStateAtPosition(below, state -> state.is(BlockTags.LEAVES))) {
                    break;
                }
            }
        }
    }
}
