package com.lunkoashtail.avaliproject.entity;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.custom.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, AvaliProject.MOD_ID);
    public static final Supplier<EntityType<SksceegehkjaEntity>> SKSCEEGEHKJA =
            ENTITY_TYPES.register("sksceegehkja", () -> EntityType.Builder.of(SksceegehkjaEntity::new, MobCategory.CREATURE)
                    .sized(2f, 2f).build("sksceegehkja"));

    public static final Supplier<EntityType<SkacikkjrrkbwcakEntity>> SKACIKKJRRKBWCAK =
            ENTITY_TYPES.register("skacikkjrrkbwcak", () -> EntityType.Builder.of(SkacikkjrrkbwcakEntity::new, MobCategory.CREATURE)
                    .sized(0.95f, 1.15f).build("skacikkjrrkbwcak"));

    public static final Supplier<EntityType<PrimagenEntity>> PRIMAGEN =
            ENTITY_TYPES.register("primagen", () -> EntityType.Builder.of(PrimagenEntity::new, MobCategory.CREATURE)
                    .sized(0.95f, 1.15f).build("primagen"));

    public static final Supplier<EntityType<ProtogenEntity>> PROTOGEN =
            ENTITY_TYPES.register("protogen", () -> EntityType.Builder.of(ProtogenEntity::new, MobCategory.CREATURE)
                    .sized(0.95f, 1.15f).build("protogen"));

    public static final Supplier<EntityType<AvaliEntity>> AVALI =
            ENTITY_TYPES.register("avali", () -> EntityType.Builder.of(AvaliEntity::new, MobCategory.CREATURE)
                    .sized(0.95f, 1.75f).build("avali"));

    public static final Supplier<EntityType<SergalEntity>> SERGAL =
            ENTITY_TYPES.register("sergal", () -> EntityType.Builder.of(SergalEntity::new, MobCategory.CREATURE)
                    .sized(0.95f, 1.75f).build("sergal"));

    public static final Supplier<EntityType<EepuorEntity>> EEPUOR =
            ENTITY_TYPES.register("eepuor", () -> EntityType.Builder.of(EepuorEntity::new, MobCategory.CREATURE)
                    .sized(0.95f, 1.75f).build("eepuor"));

    public static final Supplier<EntityType<MamagenEntity>> MAMAGEN =
            ENTITY_TYPES.register("mamagen", () -> EntityType.Builder.of(MamagenEntity::new, MobCategory.CREATURE)
                    .sized(0.95f, 1.75f).build("mamagen"));

    public static final Supplier<EntityType<StalkerEntity>> STALKER =
            ENTITY_TYPES.register("stalker", () -> EntityType.Builder.of(StalkerEntity::new, MobCategory.CREATURE)
                    .sized(0.95f, 1.75f).build("stalker"));

    public static final Supplier<EntityType<CaklerahEntity>> CAKLERAH =
            ENTITY_TYPES.register("caklerah", () -> EntityType.Builder.of(CaklerahEntity::new, MobCategory.CREATURE)
                    .sized(0.95f, 1.75f).build("caklerah"));

    public static final Supplier<EntityType<TalxleechEntity>> TALXLEECH =
            ENTITY_TYPES.register("talxleech", () -> EntityType.Builder.of(TalxleechEntity::new, MobCategory.CREATURE)
                    .sized(6f, 2f).build("talxleech"));
    public static final Supplier<EntityType<TalxweaselEntity>> TALXWEASEL =
            ENTITY_TYPES.register("talxweasel", () -> EntityType.Builder.of(TalxweaselEntity::new, MobCategory.CREATURE)
                    .sized(1.25f, 1.75f).build("talxweasel"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}