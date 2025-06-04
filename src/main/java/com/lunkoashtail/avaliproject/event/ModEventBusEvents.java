package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.custom.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AvaliProject.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SKSCEEGEHKJA.get(), SksceegehkjaEntity.createAttributes().build());
        event.put(ModEntities.SKACIKKJRRKBWCAK.get(), SkacikkjrrkbwcakEntity.createAttributes().build());
        event.put(ModEntities.PROTOGEN.get(), ProtogenEntity.createAttributes().build());
        event.put(ModEntities.PRIMAGEN.get(), PrimagenEntity.createAttributes().build());
        event.put(ModEntities.AVALI.get(), AvaliEntity.createAttributes().build());
        event.put(ModEntities.SERGAL.get(), SergalEntity.createAttributes().build());
        event.put(ModEntities.EEPUOR.get(), EepuorEntity.createAttributes().build());
        event.put(ModEntities.STALKER.get(), StalkerEntity.createAttributes().build());
        event.put(ModEntities.MAMAGEN.get(), MamagenEntity.createAttributes().build());
        event.put(ModEntities.CAKLERAH.get(), CaklerahEntity.createAttributes().build());
        event.put(ModEntities.TALXLEECH.get(), TalxleechEntity.createAttributes().build());
        event.put(ModEntities.TALXWEASEL.get(), TalxweaselEntity.createAttributes().build());
        event.put(ModEntities.GOHUNTAKI.get(), GohuntakiEntity.createAttributes().build());
        event.put(ModEntities.TALXDOG.get(), TalxdogEntity.createAttributes().build());
        event.put(ModEntities.TALXWOLF.get(), TalxwolfEntity.createAttributes().build());
        event.put(ModEntities.SPORE.get(), SporeEntity.createAttributes().build());
        event.put(ModEntities.MIZOLE.get(), MizoleEntity.createAttributes().build());
        event.put(ModEntities.FEMALE_NEVREAN.get(), FemaleNevreanEntity.createAttributes().build());
        event.put(ModEntities.MALE_NEVREAN.get(), MaleNevreanEntity.createAttributes().build());
        event.put(ModEntities.CHRGAKBZ.get(), ChrgakbzEntity.createAttributes().build());
        event.put(ModEntities.AVALI_DRONE.get(), AvaliDroneEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.SKSCEEGEHKJA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.SKACIKKJRRKBWCAK.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.FEMALE_NEVREAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.MALE_NEVREAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.PROTOGEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.PRIMAGEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.AVALI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.SERGAL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.EEPUOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.STALKER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.MAMAGEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.CAKLERAH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.TALXLEECH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.TALXWEASEL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.GOHUNTAKI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.TALXDOG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.TALXWOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.SPORE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.MIZOLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.CHRGAKBZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}