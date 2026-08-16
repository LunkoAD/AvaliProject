package com.lunkoashtail.avaliproject.sound;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, AvaliProject.MOD_ID);

    public static final Supplier<SoundEvent> MERP = registerSoundEvent("merp");
    public static final ResourceKey<JukeboxSong> MERP_KEY = createSong("merp");

    public static final Supplier<SoundEvent> AVALI_DANCE = registerSoundEvent("avali_dance");
    public static final ResourceKey<JukeboxSong> AVALI_DANCE_KEY = createSong("avali_dance");

    public static final Supplier<SoundEvent> CYBERNETIC_HEART = registerSoundEvent("cybernetic_heart");
    public static final ResourceKey<JukeboxSong> CYBERNETIC_HEART_KEY = createSong("cybernetic_heart");

    
    public static final Supplier<SoundEvent> BANDAGE_WRAP    = registerSoundEvent("bandage_wrap");
    public static final Supplier<SoundEvent> BANDAGE_SUCCESS = registerSoundEvent("bandage_success");

    
    public static final Supplier<SoundEvent> SYRINGE_STAB   = registerSoundEvent("syringe_stab");
    public static final Supplier<SoundEvent> SYRINGE_INJECT = registerSoundEvent("syringe_inject");

    public static final Supplier<SoundEvent> SHRAPNEL_PULL     = registerSoundEvent("shrapnel_pull");
    public static final Supplier<SoundEvent> SHRAPNEL_SLIP     = registerSoundEvent("shrapnel_slip");
    public static final Supplier<SoundEvent> SHRAPNEL_COMPLETE = registerSoundEvent("shrapnel_complete");

    public static final Supplier<SoundEvent> DISLOCATION_STRAIN = registerSoundEvent("dislocation_strain");
    public static final Supplier<SoundEvent> DISLOCATION_POP    = registerSoundEvent("dislocation_pop");

    public static final Supplier<SoundEvent> EXPIE_HURT     = registerSoundEvent("expie_hurt");
    public static final Supplier<SoundEvent> EXPIE_CALL     = registerSoundEvent("expie_call");
    public static final Supplier<SoundEvent> EXPIE_INTERACT = registerSoundEvent("expie_interact");

    //give avalis more life, and not parrots - @989onan
    public static final Supplier<SoundEvent> AVALI_IDLE = registerSoundEvent("avali.idle");
    public static final Supplier<SoundEvent> AVALI_HAPPY = registerSoundEvent("avali.happy");
    public static final Supplier<SoundEvent> AVALI_SURPISE = registerSoundEvent("avali.surprise");
    public static final Supplier<SoundEvent> AVALI_TALK = registerSoundEvent("avali.talk");

    private static ResourceKey<JukeboxSong> createSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name));
    }

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }


    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}