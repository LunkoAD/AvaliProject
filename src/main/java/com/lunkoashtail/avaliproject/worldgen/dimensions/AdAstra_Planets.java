package com.lunkoashtail.avaliproject.worldgen.dimensions;
import com.lunkoashtail.avaliproject.AvaliProject;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static com.lunkoashtail.avaliproject.worldgen.dimensions.ModDimensions.AVALON_LEVEL;

public class AdAstra_Planets {//extends ModCodecProvider<Planet>{
    public static final ResourceLocation Avol = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "Avol");

    public AdAstra_Planets(){

    }


    public static void bootstrapPlanets(BiConsumer<ResourceLocation, Planet> consumer){

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avalon"),
                new Planet(
                        AVALON_LEVEL,
                        false,
                        (short) 45, //temperature is in Celcius. I took the middle here.
                        2.45f,
                        4,
                        Avol,
                        Optional.empty(),
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
                        Optional.empty(), tier,
                        List.of()
                )
        );
    }
}
