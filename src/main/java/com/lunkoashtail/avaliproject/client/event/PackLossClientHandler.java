package com.lunkoashtail.avaliproject.client.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.effect.ModEffects;
import com.lunkoashtail.avaliproject.effect.PackLossEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.GatherEffectScreenTooltipsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public final class PackLossClientHandler {
    private static final float NEAREST_VISIBLE = 4.0F;
    private static final float FARTHEST_VISIBLE = 14.0F;
    private static final float PULSE_PERIOD_TICKS = 60.0F;
    private static final float FADE_STEP = 0.05F;
    private static final int EFFECT_COLUMN_GAP = 2;
    private static final int WIDE_EFFECT_WIDTH = 120;
    private static final int EFFECT_ROW_HEIGHT = 33;
    private static final int EFFECT_LIST_HEIGHT = 132;
    private static final int MAX_UNSQUEEZED_EFFECTS = 5;

    private static float prevProgress;
    private static float progress;

    private PackLossClientHandler() {
    }

    public static boolean isDarkened(LivingEntity entity) {
        MobEffectInstance packLoss = entity.getEffect(ModEffects.PACK_LOSS);
        if (packLoss == null) return false;
        int effective = entity.hasEffect(ModEffects.ANTIDEPRESSANT) ? packLoss.getAmplifier() - 1 : packLoss.getAmplifier();
        return effective >= PackLossEffect.DARKNESS_LEVEL;
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        prevProgress = progress;
        Player player = Minecraft.getInstance().player;
        boolean darkened = player != null && isDarkened(player);
        progress = Mth.clamp(progress + (darkened ? FADE_STEP : -FADE_STEP), 0.0F, 1.0F);
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        float amount = amount(event.getPartialTick());
        if (amount <= 0.0F) return;
        Player player = Minecraft.getInstance().player;
        float time = (player == null ? 0 : player.tickCount) + (float) event.getPartialTick();
        float pulse = 0.5F + 0.5F * Mth.sin(time * Mth.TWO_PI / PULSE_PERIOD_TICKS);
        float visible = Mth.lerp(pulse, NEAREST_VISIBLE, FARTHEST_VISIBLE);
        boolean sky = event.getMode() == FogRenderer.FogMode.FOG_SKY;
        float targetNear = sky ? 0.0F : visible * 0.25F;
        event.setNearPlaneDistance(Mth.lerp(amount, event.getNearPlaneDistance(), Math.min(event.getNearPlaneDistance(), targetNear)));
        event.setFarPlaneDistance(Mth.lerp(amount, event.getFarPlaneDistance(), Math.min(event.getFarPlaneDistance(), visible)));
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        float amount = amount(event.getPartialTick());
        if (amount <= 0.0F) return;
        float keep = 1.0F - amount;
        event.setRed(event.getRed() * keep);
        event.setGreen(event.getGreen() * keep);
        event.setBlue(event.getBlue() * keep);
    }

    private static float amount(double partialTick) {
        return Mth.lerp((float) partialTick, prevProgress, progress);
    }

    @SubscribeEvent
    public static void onGatherEffectTooltip(GatherEffectScreenTooltipsEvent event) {
        MobEffectInstance instance = event.getEffectInstance();
        if (instance.is(ModEffects.PACK_LOSS)) {
            event.getTooltip().addAll(PackLossEffect.describe(instance.getAmplifier()));
        }
    }

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event) {
        if (!(event.getScreen() instanceof EffectRenderingInventoryScreen<?> screen)) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null) return;
        int left = screen.getGuiLeft() + screen.getXSize() + EFFECT_COLUMN_GAP;
        if (screen.width - left < WIDE_EFFECT_WIDTH) return;
        int mouseX = event.getMouseX();
        int mouseY = event.getMouseY();
        if (mouseX < left || mouseX > left + WIDE_EFFECT_WIDTH) return;

        List<MobEffectInstance> effects = minecraft.player.getActiveEffects().stream()
                .filter(ClientHooks::shouldRenderEffect).sorted().toList();
        if (effects.isEmpty()) return;
        int rowHeight = effects.size() > MAX_UNSQUEEZED_EFFECTS
                ? EFFECT_LIST_HEIGHT / (effects.size() - 1) : EFFECT_ROW_HEIGHT;
        MobEffectInstance hovered = null;
        int rowTop = screen.getGuiTop();
        for (MobEffectInstance effect : effects) {
            if (mouseY >= rowTop && mouseY <= rowTop + rowHeight) hovered = effect;
            rowTop += rowHeight;
        }
        if (hovered == null || !hovered.is(ModEffects.PACK_LOSS)) return;

        List<Component> lines = new ArrayList<>();
        lines.add(Component.translatable(hovered.getDescriptionId()).append(" ")
                .append(Component.translatable("enchantment.level." + (hovered.getAmplifier() + 1))));
        lines.add(MobEffectUtil.formatDuration(hovered, 1.0F, minecraft.level.tickRateManager().tickrate()));
        lines.addAll(PackLossEffect.describe(hovered.getAmplifier()));
        event.getGuiGraphics().renderComponentTooltip(minecraft.font, lines, mouseX, mouseY);
    }
}
