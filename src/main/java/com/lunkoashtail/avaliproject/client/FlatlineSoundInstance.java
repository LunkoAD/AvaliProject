package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.screen.custom.HealthPanelScreen;
import com.lunkoashtail.avaliproject.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class FlatlineSoundInstance extends AbstractTickableSoundInstance {

    private final HealthPanelScreen panel;

    public FlatlineSoundInstance(HealthPanelScreen panel) {
        super(ModSounds.ECG_FLATLINE.get(), SoundSource.MASTER, RandomSource.create());
        this.panel = panel;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.45f;
        this.relative = true;
        this.attenuation = SoundInstance.Attenuation.NONE;
    }

    @Override
    public void tick() {
        if (Minecraft.getInstance().screen != panel || !panel.isFlatlining()) stop();
    }
}
