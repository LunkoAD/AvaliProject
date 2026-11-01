package com.lunkoashtail.avaliproject.effect;

import com.lunkoashtail.avaliproject.AvaliProject;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, AvaliProject.MOD_ID);

    public static final Holder<MobEffect> DIM_SIGHT = MOB_EFFECTS.register("dim_sight", DimSightEffect::new);
    public static final Holder<MobEffect> PACK_LOSS = MOB_EFFECTS.register("pack_loss", PackLossEffect::new);
    public static final Holder<MobEffect> ANTIDEPRESSANT = MOB_EFFECTS.register("antidepressant", AntidepressantEffect::new);

    private ModEffects() {
    }

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
