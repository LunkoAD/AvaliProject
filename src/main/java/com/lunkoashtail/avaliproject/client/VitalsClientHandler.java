package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.limb.BleedingTier;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.vitals.LimbHealth;
import com.lunkoashtail.avaliproject.screen.custom.HealthPanelScreen;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.vitals.Vitals;
import com.lunkoashtail.avaliproject.vitals.Vitals.Stat;
import com.lunkoashtail.avaliproject.vitals.VitalsClientCache;
import com.lunkoashtail.avaliproject.vitals.VitalsEventHandler;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public class VitalsClientHandler {

    public static void openOwnPanel(Minecraft mc) {
        if (mc.player == null) return;
        if (mc.player.getData(ModAttachments.SPECIES) != Species.EXPIE) {
            mc.player.displayClientMessage(net.minecraft.network.chat.Component.literal("Only expies carry a health monitor."), true);
            return;
        }
        mc.setScreen(new HealthPanelScreen(mc.player.getId()));
    }

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        if (!VitalsEventHandler.isIncapacitated(event.getEntity())) return;
        var input = event.getInput();
        input.forwardImpulse = 0f;
        input.leftImpulse = 0f;
        input.up = false;
        input.down = false;
        input.left = false;
        input.right = false;
        input.jumping = false;
        input.shiftKeyDown = false;
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getLevel().isClientSide()) return;
        Player player = event.getEntity();
        if (!player.isShiftKeyDown() || event.getHand() != InteractionHand.MAIN_HAND) return;
        if (!player.getMainHandItem().isEmpty()) return;
        if (!(event.getTarget() instanceof Player target)) return;
        if (target.getData(ModAttachments.SPECIES) != Species.EXPIE) return;
        Minecraft.getInstance().setScreen(new HealthPanelScreen(target.getId()));
    }

    private static boolean wasDown;

    @SubscribeEvent
    public static void onClone(ClientPlayerNetworkEvent.Clone event) {
        Player player = event.getNewPlayer();
        player.setForcedPose(null);
        com.lunkoashtail.avaliproject.client.event.SpeciesRenderHandler.forget(player.getUUID());
        wasDown = false;
    }

    @SubscribeEvent
    public static void onClientTick(net.neoforged.neoforge.client.event.ClientTickEvent.Post event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        boolean down = VitalsEventHandler.isIncapacitated(player);
        if (wasDown && !down) {
            com.lunkoashtail.avaliproject.client.event.SpeciesRenderHandler.forget(player.getUUID());
        }
        wasDown = down;
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        VitalsClientCache.clear();
    }

    @EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class Layers {
        @SubscribeEvent
        public static void register(RegisterGuiLayersEvent event) {
            event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS,
                    ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "vitals_overlay"),
                    VitalsClientHandler::renderOverlay);
        }
    }

    private static void renderOverlay(GuiGraphics gfx, DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || !VitalsEventHandler.isActive(player)) return;
        Vitals v = player.getData(ModAttachments.VITALS);
        int w = gfx.guiWidth();
        int h = gfx.guiHeight();

        float consciousness = v.get(Stat.CONSCIOUSNESS);
        if (consciousness < 70f) {
            float darkness = Math.min(1f, (70f - consciousness) * 0.021f / 0.98f);
            int alpha = Math.round(darkness * 255f);
            gfx.fill(0, 0, w, h, alpha << 24);
            float dying = v.get(Stat.DYING_TIME);
            if (dying > 0f) {
                gfx.drawCenteredString(mc.font, "Your heart has stopped...", w / 2, h / 2 - 6, 0xFFAA3333);
                gfx.drawCenteredString(mc.font, (int) Math.ceil(dying) + "s", w / 2, h / 2 + 6, 0xFFAA3333);
            } else if (consciousness < 23f) {
                gfx.drawCenteredString(mc.font, "You are unconscious...", w / 2, h / 2, 0xFF884444);
            }
        }

        boolean worse = v.get(Stat.BRAIN) < 50 || v.get(Stat.BLEED_RATE) > 0.134f
                || (v.get(Stat.BLOOD) < 3500 && v.get(Stat.BLEED_RATE) > 0.02f)
                || v.get(Stat.THIRST) < 10 || v.get(Stat.RADIATION) > 18
                || v.get(Stat.SYSTOLIC) < 80 || v.get(Stat.SYSTOLIC) > 170
                || v.get(Stat.OXYGEN) < 65 || v.get(Stat.FIBRILLATION) > 20
                || v.get(Stat.RESP_RATE) <= 0 || v.get(Stat.OPIOID) >= 100;
        if (mc.screen instanceof HealthPanelScreen) return;

        int x = 6;
        int y = h / 2 - 30;
        if (worse) {
            boolean blink = (System.currentTimeMillis() / 400) % 2 == 0;
            gfx.fill(x, y, x + 16, y + 16, blink ? 0xCCB01010 : 0x88400808);
            gfx.fill(x + 6, y + 2, x + 10, y + 14, 0xFFFFFFFF);
            gfx.fill(x + 2, y + 6, x + 14, y + 10, 0xFFFFFFFF);
        }
        drawBleedingIcons(gfx, mc, player, v, x, y + 20);
    }

    private static void drawBleedingIcons(GuiGraphics gfx, Minecraft mc, Player player, Vitals v, int x, int y) {
        LimbData bleed = player.getData(ModAttachments.LIMB_DATA);
        LimbHealth limbs = player.getData(ModAttachments.LIMB_HEALTH);
        int[] counts = new int[BleedingTier.values().length];
        boolean any = false;
        for (Limb limb : Limb.values()) {
            if (limbs.hasTourniquet(limb)) continue;
            BleedingTier tier = BleedingTier.fromBleedValue(bleed.getBleed(limb));
            if (tier == null) continue;
            counts[tier.ordinal()]++;
            any = true;
        }
        if (!any) return;

        BleedingTier[] tiers = BleedingTier.values();
        for (int i = tiers.length - 1; i >= 0; i--) {
            if (counts[i] == 0) continue;
            BleedingTier tier = tiers[i];
            gfx.fill(x - 1, y - 1, x + 17, y + 17, 0x66000000);
            gfx.blit(tier.icon, x, y, 16, 16, 0f, 0f, 256, 256, 256, 256);
            if (counts[i] > 1) gfx.drawString(mc.font, "x" + counts[i], x + 19, y + 4, tier.getColor(), true);
            y += 19;
        }
        gfx.drawString(mc.font, String.format("%.2f L/m", v.get(Stat.BLEED_RATE)), x, y + 1, 0xFFE05050, true);
    }
}
