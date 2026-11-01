package com.lunkoashtail.avaliproject.worldgen;

import com.lunkoashtail.avaliproject.block.ModBlocks;
import com.lunkoashtail.avaliproject.block.entity.custom.ContainerCrateBlockEntity;
import com.mojang.serialization.Codec;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ContainerCrateFeature extends Feature<NoneFeatureConfiguration> {
    private static final float EXPIE_CHANCE = 0.4f;

    public ContainerCrateFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();

        if (level.getBlockState(pos.below()).isAir()) return false;
        if (!level.getBlockState(pos).canBeReplaced()) return false;

        level.setBlock(pos, ModBlocks.CONTAINER_CRATE.get().defaultBlockState(), 3);
        if (level.getBlockEntity(pos) instanceof ContainerCrateBlockEntity crate) {
            crate.setLootTable(ModLootTables.EXPIE_CONTAINER_CRATE, context.random().nextLong());
        }
        if (context.random().nextFloat() < EXPIE_CHANCE) {
            spawnExpieNear(level, pos, context.random());
        }
        return true;
    }

    private static void spawnExpieNear(WorldGenLevel level, BlockPos crate, RandomSource random) {
        for (int attempt = 0; attempt < 6; attempt++) {
            BlockPos pos = crate.offset(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
            if (pos.equals(crate)) continue;
            if (!level.getBlockState(pos).isAir() || !level.getBlockState(pos.above()).isAir()) continue;
            if (!level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP)) continue;
            ExpieEntity expie = ModEntities.EXPIE.get().create(level.getLevel());
            if (expie == null) return;
            expie.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, random.nextFloat() * 360f, 0f);
            expie.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null);
            expie.setPersistenceRequired();
            level.addFreshEntityWithPassengers(expie);
            return;
        }
    }
}
