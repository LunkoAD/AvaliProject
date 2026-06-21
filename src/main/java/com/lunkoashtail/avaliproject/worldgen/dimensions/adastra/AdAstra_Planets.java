package com.lunkoashtail.avaliproject.worldgen.dimensions.adastra;
import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.worldgen.dimensions.adastra.emulator.ModCodecProvider;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static com.lunkoashtail.avaliproject.datagen.ModDimensions.*;

/// PARTLY BELONGS TO: https://github.com/terrarium-earth/Ad-Astra
///
/// this is to fix issues with data gen due to them not existing in source files.

public class AdAstra_Planets extends ModCodecProvider<Planet> {//extends ModCodecProvider<Planet>{

    public static final ResourceKey<Registry<Planet>> PLANET_REGISTRY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("adastra", "planets"));

    //## solar systems.
    //Avol, the home of the Avali.
    public static final ResourceLocation Avol = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avol");


    //## textures in sky
    //avalon
    public static final ResourceLocation AVOL_SUN = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/environment/avalon/sun.png");
    public static final ResourceLocation AVOL_GAS_GIANT = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/environment/avalon/gas_giant.png");
    public static final ResourceLocation AVALON_PLANET_LOOK = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/environment/avalon/planet.png");





    public AdAstra_Planets(PackOutput packOutput) {
        super(packOutput, Planet.CODEC, PLANET_REGISTRY);
    }


    @Override
    public void build(BiConsumer<ResourceLocation, Planet> consumer){

        consumer.accept(
                AVALON.location(),new Planet(
                        AVALON_LEVEL,
                        false,
                        (short) -45, //temperature is in Celcius. I took the middle here.
                        2.45f,
                        4,
                        Avol,
                        Optional.of(AVALON_ORBIT_LEVEL),
                        4,
                        List.of()
                )

        );
    }

    private static void orbit(BiConsumer<ResourceLocation, Planet> consumer, ResourceKey<Level> planet, int solarPower, ResourceLocation galaxy, int tier) {
        consumer.accept(
                planet.location(),
                new Planet(planet,
                        false,
                        PlanetConstants.SPACE_TEMPERATURE,
                        PlanetConstants.SPACE_GRAVITY,
                        solarPower,
                        galaxy,
                        Optional.empty(),
                        tier,
                        List.of()
                )
        );
    }

    @Override
    public @NotNull String getName() {
        return "Planets";
    }
}
