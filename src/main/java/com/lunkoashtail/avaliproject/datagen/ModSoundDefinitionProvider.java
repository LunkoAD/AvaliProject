package com.lunkoashtail.avaliproject.datagen;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.teamresourceful.resourcefullib.common.collections.CycleableList;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import static com.lunkoashtail.avaliproject.sound.ModSounds.*;

public class ModSoundDefinitionProvider extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    protected ModSoundDefinitionProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, AvaliProject.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(AVALI_CHIRP_1.get(), definition()
                .subtitle("sound.avaliproject.avali_chirp") // Set translation key
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali/audio_track_1")) // Set first sound
                                .volume(0.5), // Scales all volumes called on this sound by half
                        sound(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali/audio_track_2")) // Set first sound
                                .volume(0.5), // Scales all volumes called on this sound by half
                        sound(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali/audio_track_3")) // Set first sound
                                .volume(0.5), // Scales all volumes called on this sound by half
                        sound(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali/audio_track_4")) // Set first sound
                                .volume(0.5), // Scales all volumes called on this sound by half
                        sound(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali/audio_track_5")) // Set first sound
                                .volume(0.5), // Scales all volumes called on this sound by half
                        sound(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali/audio_track_6")) // Set first sound
                                .volume(0.5), // Scales all volumes called on this sound by half
                        sound(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali/audio_track_7")) // Set first sound
                                .volume(0.5) // Scales all volumes called on this sound by half
                )
        );

        this.add(MERP.get(), definition()
                .subtitle("sound.avaliproject.merp") // Set translation key
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "merp")) // Set first sound
                                .volume(0.5)
                                .stream()
                )
        );

        this.add(AVALI_DANCE.get(), definition()
                .subtitle("sound.avaliproject.avali_dance") // Set translation key
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "avali_dance")) // Set first sound
                                .volume(0.5)
                                .stream()
                )
        );

        this.add(CYBERNETIC_HEART.get(), definition()
                .subtitle("sound.avaliproject.cybernetic_heart") // Set translation key
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "cybernetic_heart")) // Set first sound
                                .volume(0.5)
                                .stream()
                )
        );
    }
}
