package com.lunkoashtail.avaliproject.mixin;

import com.lunkoashtail.avaliproject.client.event.PackLossClientHandler;
import com.lunkoashtail.avaliproject.effect.ModEffects;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Inject(method = "doesMobEffectBlockSky", at = @At("RETURN"), cancellable = true)
    private void avaliproject$dimSightBlocksSky(Camera camera, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() && camera.getEntity() instanceof LivingEntity living
                && (living.hasEffect(ModEffects.DIM_SIGHT) || PackLossClientHandler.isDarkened(living))) {
            cir.setReturnValue(true);
        }
    }
}
