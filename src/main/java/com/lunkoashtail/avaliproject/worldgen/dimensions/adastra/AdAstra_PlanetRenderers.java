package com.lunkoashtail.avaliproject.worldgen.dimensions.adastra;

import com.lunkoashtail.avaliproject.datagen.ModDimensions;
import com.lunkoashtail.avaliproject.worldgen.dimensions.adastra.emulator.ModCodecProvider;
import earth.terrarium.adastra.client.dimension.MovementType;
import earth.terrarium.adastra.client.dimension.PlanetRenderer;
import earth.terrarium.adastra.client.dimension.SkyRenderable;
import earth.terrarium.adastra.client.utils.DimensionRenderingUtils;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

/// MOSTLY BELONGS TO: https://github.com/terrarium-earth/Ad-Astra
///
/// this is to fix issues with data gen due to them not existing in source files.

public class AdAstra_PlanetRenderers extends ModCodecProvider<PlanetRenderer> {

    public static final ResourceKey<Registry<PlanetRenderer>> PLANET_REGISTRY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("adastra", "planet_renderers"));

    public static final int DEFAULT_SUNRISE_COLOR = 0xd85f33;

    public static final SimpleWeightedRandomList<Integer> COLORED_STARS = SimpleWeightedRandomList.<Integer>builder()
            .add(0xA9BCDFFF, 3)   // Blue
            .add(0xBBD7FFFF, 5)   // Blue-White,
            .add(0xFFF4E8FF, 100) // Yellow-White
            .add(0xFFD1A0FF, 80)  // Orange
            .add(0xFF8A8AFF, 150) // Red
            .add(0xFF4500FF, 10)  // Orange-Red
            .add(0xFFFFF4FF, 60)  // White
            .add(0xFFF8E7FF, 40)  // Pale Yellow
            .add(0xFFFFFF00, 20)  // Very Pale Yellow
            .add(0xFFFF0000, 1)   // Bright Red
            .build();

    public static final SimpleWeightedRandomList<Integer> DEFAULT_STARS = SimpleWeightedRandomList.<Integer>builder()
            .add(0xffffffff, 1)
            .build();

    public AdAstra_PlanetRenderers(PackOutput packOutput) {
        super(packOutput, PlanetRenderer.CODEC, PLANET_REGISTRY, PackOutput.Target.RESOURCE_PACK);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, PlanetRenderer> consumer) {

        List<SkyRenderable> Avalon_sky = List.of(
                new SkyRenderable(AdAstra_Planets.AVOL_SUN, 2, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xffffffd9),
                new SkyRenderable(AdAstra_Planets.AVOL_GAS_GIANT, 120, new Vec3(40, 40, 0), new Vec3(0, 20, 0), MovementType.STATIC, true, 0xffffffd9)
        );

        consumer.accept(ModDimensions.AVALON.location(), new PlanetRenderer(
                ModDimensions.AVALON_LEVEL,
                true,
                true,
                false,
                false,
                true,
                0xd4b284,
                1500,
                Optional.empty(),
                20,
                false,
                DEFAULT_STARS,
                Avalon_sky
        ));

        orbit(consumer, ModDimensions.AVALON_ORBIT_LEVEL, AdAstra_Planets.AVALON_PLANET_LOOK, 0x000000, 9,
                new SkyRenderable(AdAstra_Planets.AVOL_GAS_GIANT, 120, new Vec3(40, 40, 0), new Vec3(0, 20, 0), MovementType.STATIC, true, 0x00000000));
    }

    private static void orbit(BiConsumer<ResourceLocation, PlanetRenderer> consumer, ResourceKey<Level> planet, ResourceLocation planetTexture, int backlightColor, int sunScale, SkyRenderable... additionalRenderables) {
        List<SkyRenderable> renderables = new ArrayList<>();
        renderables.add(new SkyRenderable(DimensionRenderingUtils.SUN, sunScale, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, 0xffffffd9));
        renderables.add(new SkyRenderable(planetTexture, 80, new Vec3(180, 0, 0), Vec3.ZERO, MovementType.STATIC, backlightColor));
        renderables.addAll(List.of(additionalRenderables));
        consumer.accept(
                planet.location(),
                new PlanetRenderer(
                        planet,
                        true,
                        true,
                        false,
                        false,
                        false,
                        DEFAULT_SUNRISE_COLOR,
                        13000,
                        Optional.of(0.6f),
                        0,
                        true,
                        COLORED_STARS,
                        renderables
                ));
    }

    @Override
    public @NotNull String getName() {
        return "Planet Renderers";
    }
}
