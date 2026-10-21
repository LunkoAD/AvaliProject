package com.lunkoashtail.avaliproject.worldgen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.datagen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower PALM = new TreeGrower(AvaliProject.MOD_ID + ":palm",
            Optional.empty(), Optional.of(ModConfiguredFeatures.PALM_TREE_KEY), Optional.empty());
    public static final TreeGrower PASSION_FRUIT = new TreeGrower(AvaliProject.MOD_ID + ":passion_fruit",
            Optional.empty(), Optional.of(ModConfiguredFeatures.PASSION_FRUIT_TREE_KEY), Optional.empty());
    public static final TreeGrower PAPAYA = new TreeGrower(AvaliProject.MOD_ID + ":papaya",
            Optional.empty(), Optional.of(ModConfiguredFeatures.PAPAYA_TREE_KEY), Optional.empty());
}
