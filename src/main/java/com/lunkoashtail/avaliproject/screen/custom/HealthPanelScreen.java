package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.network.RemoveTourniquetPayload;
import com.lunkoashtail.avaliproject.network.RequestVitalsPayload;
import com.lunkoashtail.avaliproject.network.VitalsSyncPayload;
import com.lunkoashtail.avaliproject.vitals.LimbHealth;
import com.lunkoashtail.avaliproject.vitals.Vitals;
import com.lunkoashtail.avaliproject.vitals.Vitals.Stat;
import com.lunkoashtail.avaliproject.vitals.VitalsClientCache;
import com.lunkoashtail.avaliproject.vitals.VitalsLogic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HealthPanelScreen extends Screen {

    private static final int W = 200;
    private static final int H = 228;
    private static final int LIMB_BTN_W = 64;
    private static final int LIMB_BTN_H = 24;
    private static final Limb[] RING = {Limb.HEAD, Limb.LEFT_ARM, Limb.LEFT_LEG, Limb.BACK, Limb.RIGHT_LEG, Limb.RIGHT_ARM};

    private static final int COL_BG = 0xF00A140E;
    private static final int COL_PANEL = 0xFF0E1D14;
    private static final int COL_BORDER = 0xFF2F6B44;
    private static final int COL_GRID = 0xFF13261A;
    private static final int COL_TEXT = 0xFF46F08A;
    private static final int COL_DIM = 0xFF1F7A45;
    private static final int COL_BAR_BG = 0xFF06100A;
    private static final int COL_BAD = 0xFFFF4A4A;
    private static final int COL_WARN = 0xFFFFC23A;

    private static final int ECG_SAMPLES = W - 16;
    private static final double ECG_SAMPLES_PER_SECOND = 100.0;

    private final int targetEntityId;
    private Limb selected = Limb.HEAD;
    private final int left = 0;
    private final int top = 0;
    private int panelX, panelY;
    private float scale = 1f;
    private final float[] ecg = new float[ECG_SAMPLES];
    private int ecgHead;
    private double ecgPhase;
    private long lastFrameNanos;
    private double ecgStepDebt;
    private boolean flatlining;
    @Nullable private com.lunkoashtail.avaliproject.client.FlatlineSoundInstance flatlineSound;
    private int ticks;
    private final List<ClickZone> zones = new ArrayList<>();
    private final List<ClickZone> screenZones = new ArrayList<>();

    private record ClickZone(int x1, int y1, int x2, int y2, Runnable action) {
        boolean contains(double mx, double my) {
            return mx >= x1 && mx < x2 && my >= y1 && my < y2;
        }
    }

    public HealthPanelScreen(int targetEntityId) {
        super(Component.translatable("screen.avaliproject.health_panel"));
        this.targetEntityId = targetEntityId;
    }

    private boolean isSelf() {
        Player local = Minecraft.getInstance().player;
        return local != null && local.getId() == targetEntityId;
    }

    @Override
    protected void init() {
        super.init();
        scale = Math.max(0.5f, Math.min((height - 16f) / H, width * 0.3f / W));
        panelX = 8;
        panelY = Math.round((height - H * scale) / 2f);
        PacketDistributor.sendToServer(new RequestVitalsPayload(targetEntityId));
    }

    @Override
    public void tick() {
        super.tick();
        ticks++;
        if (!isSelf() && ticks % 20 == 0) {
            PacketDistributor.sendToServer(new RequestVitalsPayload(targetEntityId));
        }
    }

    @Override
    public void renderBackground(GuiGraphics gfx, int mx, int my, float partial) {
    }

    @Override
    public void render(GuiGraphics gfx, int mx, int my, float partial) {
        gfx.fill(0, 0, width, height, 0x88000000);
        zones.clear();
        screenZones.clear();
        VitalsSyncPayload data = VitalsClientCache.get(targetEntityId);
        int localX = Math.round((mx - panelX) / scale);
        int localY = Math.round((my - panelY) / scale);
        boolean blink = (System.currentTimeMillis() / 400) % 2 == 0;

        gfx.pose().pushPose();
        gfx.pose().translate(panelX, panelY, 0);
        gfx.pose().scale(scale, scale, 1f);
        frame(gfx, left, top, W, H);
        if (data == null) {
            gfx.drawCenteredString(font, "NO SIGNAL", left + W / 2, top + H / 2, COL_DIM);
            gfx.pose().popPose();
            return;
        }
        Vitals v = data.vitals();
        drawHeader(gfx, v, blink);
        drawVitals(gfx, data, v, blink);
        drawEcg(gfx, v);
        drawLimb(gfx, data, blink, localX, localY);
        gfx.pose().popPose();

        drawBody(gfx, data, mx, my);
        super.render(gfx, mx, my, partial);
    }

    private void frame(GuiGraphics gfx, int x, int y, int w, int h) {
        gfx.fill(x - 1, y - 1, x + w + 1, y + h + 1, COL_BORDER);
        gfx.fill(x, y, x + w, y + h, COL_PANEL);
        for (int gx = x + 8; gx < x + w; gx += 8) gfx.fill(gx, y, gx + 1, y + h, COL_GRID);
        for (int gy = y + 8; gy < y + h; gy += 8) gfx.fill(x, gy, x + w, gy + 1, COL_GRID);
    }

    private void divider(GuiGraphics gfx, int y) {
        for (int x = left + 4; x < left + W - 4; x += 3) gfx.fill(x, y, x + 2, y + 1, COL_DIM);
    }

    private void drawHeader(GuiGraphics gfx, Vitals v, boolean blink) {
        String critical = criticalWarning(v);
        int headerColor = critical != null && blink ? 0xFF5A1010 : 0xFF132A1C;
        gfx.fill(left, top, left + W, top + 20, headerColor);
        Entity entity = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getEntity(targetEntityId) : null;
        String name = entity != null ? entity.getName().getString().toUpperCase() : "PATIENT";
        gfx.drawString(font, name, left + 4, top + 2, COL_TEXT, false);
        String sub = critical != null ? "! " + critical : "EXPIE | #" + targetEntityId;
        gfx.drawString(font, sub, left + 4, top + 11, critical != null ? COL_BAD : COL_DIM, false);
        gfx.drawString(font, "EXPIE-MED v1.0", left + W - 4 - font.width("EXPIE-MED v1.0"), top + 2, COL_DIM, false);
    }

    private void drawVitals(GuiGraphics gfx, VitalsSyncPayload data, Vitals v, boolean blink) {
        int x = left + 6;
        int r = left + W - 6;
        int y = top + 24;

        float brain = v.get(Stat.BRAIN);
        label(gfx, "BRAIN", x, y, COL_DIM);
        bar(gfx, x + 34, y + 1, W - 74, 6, brain / 100f, flag(brain < 50, blink));
        right(gfx, fmt0(brain), r, y, flag(brain < 50, blink));
        y += 11;

        float cons = v.get(Stat.CONSCIOUSNESS);
        float pain = Math.max(0, data.pain() - v.get(Stat.ANALGESIA));
        gfx.drawString(font, "CONSC " + fmt0(cons) + "%", x, y, flag(cons < 40, blink), false);
        right(gfx, "PAIN " + fmt0(pain) + "%", r, y, flag(pain > 75, blink));
        y += 12;
        divider(gfx, y);
        y += 4;

        float blood = v.get(Stat.BLOOD);
        float bleed = v.get(Stat.BLEED_RATE);
        gfx.drawString(font, "BLOOD " + String.format("%.2f", blood / 1000f) + "L", x, y, flag(blood < 3000, blink), false);
        right(gfx, "THIRST " + fmt0(v.get(Stat.THIRST)), r, y, flag(v.get(Stat.THIRST) < 10, blink));
        y += 10;
        gfx.drawString(font, "BLEED " + String.format("%.3f", bleed) + "L/m", x, y,
                bleed > 0.09f ? flag(true, blink) : bleed > 0f ? COL_WARN : COL_DIM, false);
        right(gfx, "ENERGY " + fmt0(v.get(Stat.ENERGY)) + "%", r, y, flag(v.get(Stat.ENERGY) < 5, blink));
        y += 10;
        bar(gfx, x, y, 88, 4, blood / VitalsLogic.NORMAL_BLOOD, flag(blood < 3000, blink));
        bar(gfx, r - 88, y, 88, 4, v.get(Stat.THIRST) / 100f, flag(v.get(Stat.THIRST) < 10, blink));
        y += 8;
        divider(gfx, y);
        y += 4;

        float resp = v.get(Stat.RESP_RATE);
        float rad = v.get(Stat.RADIATION);
        gfx.drawString(font, "RESP " + fmt0(resp) + "/m", x, y, flag(resp < 8, blink), false);
        right(gfx, String.format("RAD %.1fGy", rad), r, y,
                rad > 7.5f ? flag(true, blink) : v.get(Stat.RADIATION_EXPOSURE) > 0f ? COL_WARN : COL_TEXT);
        y += 10;
        float hemo = v.get(Stat.HEMOTHORAX);
        float opioid = v.get(Stat.OPIOID);
        gfx.drawString(font, "OPIOID " + fmt0(opioid), x, y, opioid >= 100 ? flag(true, blink) : opioid > 50 ? COL_WARN : COL_DIM, false);
        right(gfx, "HEMOTHORAX " + fmt0(hemo), r, y, hemo >= 70 ? flag(true, blink) : hemo >= 40 ? COL_WARN : COL_DIM);
        y += 12;
        divider(gfx, y);
        y += 4;

        float hr = v.get(Stat.HEART_RATE);
        float sys = v.get(Stat.SYSTOLIC);
        float dia = v.get(Stat.DIASTOLIC);
        boolean heartBad = hr > 200 || hr < 20 || sys < 88 || sys > 160 || v.get(Stat.FIBRILLATION) > 30;
        gfx.drawString(font, "♥ " + fmt0(hr) + " | " + fmt0(sys) + "/" + fmt0(dia), x, y, flag(heartBad, blink), false);
        float oxygen = v.get(Stat.OXYGEN);
        right(gfx, "O2 " + fmt0(oxygen) + "%", r, y, flag(oxygen < 70, blink));
    }

    private void drawEcg(GuiGraphics gfx, Vitals v) {
        int x = left + 8;
        int y = top + 122;
        int h = 28;
        gfx.fill(x - 1, y - 1, x + ECG_SAMPLES + 1, y + h + 1, COL_DIM);
        gfx.fill(x, y, x + ECG_SAMPLES, y + h, COL_BAR_BG);

        long now = System.nanoTime();
        double dt = lastFrameNanos == 0 ? 0 : Math.min(0.25, (now - lastFrameNanos) / 1e9);
        lastFrameNanos = now;
        ecgStepDebt += dt * ECG_SAMPLES_PER_SECOND;
        int steps = (int) ecgStepDebt;
        ecgStepDebt -= steps;

        float hr = v.get(Stat.HEART_RATE);
        float fib = v.get(Stat.FIBRILLATION);
        boolean flatline = hr < 1f;
        boolean chaotic = !flatline && fib > 30f;
        for (int i = 0; i < steps; i++) {
            double before = ecgPhase;
            double rate = hr / 60.0 / ECG_SAMPLES_PER_SECOND;
            if (chaotic) rate *= 0.6 + Math.random() * 0.8;
            ecgPhase += rate;
            float sample = 0f;
            if (!flatline) {
                boolean rPeak = Math.floor(before) != Math.floor(ecgPhase);
                double period = 60.0 / Math.max(hr, 1f);
                double sinceBeat = (ecgPhase - Math.floor(ecgPhase)) * period;
                sample = rPeak ? 1f : waveform(sinceBeat, period);
                if (chaotic) {
                    sample = sample * (float) (0.5 + Math.random() * 0.6) + (float) ((Math.random() - 0.5) * fib / 200.0);
                }
                if (rPeak) beep(v);
            }
            ecg[ecgHead] = sample;
            ecgHead = (ecgHead + 1) % ECG_SAMPLES;
        }
        flatlining = flatline;
        if (flatline) flatlineTone();

        int mid = y + h / 2 + 4;
        int color = hr < 1 || fib > 60 ? COL_BAD : COL_TEXT;
        int prevY = mid;
        for (int i = 0; i < ECG_SAMPLES; i++) {
            float sample = ecg[(ecgHead + i) % ECG_SAMPLES];
            int py = Mth.clamp(mid - Math.round(sample * (h / 2f + 2)), y + 1, y + h - 1);
            int lo = Math.min(prevY, py);
            int hi = Math.max(prevY, py);
            gfx.fill(x + i, lo, x + i + 1, hi + 1, color);
            prevY = py;
        }
    }

    private static float waveform(double t, double period) {
        if (t < 0.03) return -0.35f;
        double untilNext = period - t;
        if (untilNext < 0.03) return -0.2f;
        if (untilNext < 0.08) return 0f;
        if (untilNext < 0.16) return (float) (0.15 * Math.sin((0.16 - untilNext) / 0.08 * Math.PI));
        if (t >= 0.14 && t < 0.30) return (float) (0.25 * Math.sin((t - 0.14) / 0.16 * Math.PI));
        return 0f;
    }

    private void drawLimb(GuiGraphics gfx, VitalsSyncPayload data, boolean blink, int mx, int my) {
        int y = top + 156;
        divider(gfx, y - 2);
        LimbHealth limbs = data.limbs();
        Vitals v = data.vitals();
        int i = selected.ordinal();
        String title = selected.getDisplayName().getString().toUpperCase();
        gfx.drawCenteredString(font, title, left + W / 2, y + 2, COL_TEXT);
        y += 13;

        int x = left + 6;
        int r = left + W - 6;
        float skin = limbs.getSkin(selected);
        float muscle = limbs.getMuscle(selected);
        label(gfx, "SKIN", x, y, COL_DIM);
        bar(gfx, x + 40, y + 1, W - 80, 6, skin / 100f, flag(skin < 10, blink));
        right(gfx, fmt0(skin), r, y, flag(skin < 10, blink));
        y += 10;
        label(gfx, "MUSCLE", x, y, COL_DIM);
        bar(gfx, x + 40, y + 1, W - 80, 6, muscle / 100f, flag(muscle < 10, blink));
        right(gfx, fmt0(muscle), r, y, flag(muscle < 10, blink));
        y += 11;

        float limbBleed = limbs.hasTourniquet(selected) ? 0f : data.bleed()[i] * VitalsLogic.LITERS_PER_MIN_PER_BLEED;
        float infection = limbs.getInfection(selected);
        gfx.drawString(font, "BLEED " + String.format("%.2f", limbBleed) + "L/m", x, y,
                limbBleed > 0.12f ? flag(true, blink) : limbBleed > 0 ? COL_WARN : COL_DIM, false);
        right(gfx, "INFECT " + (infection > 0 ? fmt0(infection) : "-"), r, y,
                infection > 25 ? flag(true, blink) : infection > 0 ? COL_WARN : COL_DIM);
        y += 11;

        int bx = x;
        boolean self = isSelf();
        if (data.shrapnel()[i] > 0) {
            bx = button(gfx, bx, y, "SHRAPNEL", self, mx, my, () -> {
                Minecraft.getInstance().setScreen(new ShrapnelMinigameScreen(selected, data.shrapnel()[selected.ordinal()]));
            });
        }
        if (data.dislocated()[i]) {
            bx = button(gfx, bx, y, "DISLOCATED", self, mx, my, () ->
                    Minecraft.getInstance().setScreen(new DislocationMinigameScreen(selected)));
        }
        if (limbs.hasTourniquet(selected)) {
            bx = button(gfx, bx, y, "TOURNIQUET", true, mx, my, () ->
                    PacketDistributor.sendToServer(new RemoveTourniquetPayload(targetEntityId, selected.ordinal())));
        }
        if (bx == x) gfx.drawString(font, "NO COMPLICATIONS", x, y + 2, COL_DIM, false);

        if (v.get(Stat.CONSCIOUSNESS) < VitalsLogic.INCAPACITATED) {
            gfx.drawCenteredString(font, blink ? "PATIENT UNRESPONSIVE" : "", left + W / 2, top + H - 10, COL_BAD);
        }
    }

    private int button(GuiGraphics gfx, int x, int y, String text, boolean clickable, int mx, int my, Runnable action) {
        int w = font.width(text) + 6;
        boolean hover = clickable && mx >= x && mx < x + w && my >= y && my < y + 12;
        gfx.fill(x, y, x + w, y + 12, hover ? 0xFF3A1A1A : 0xFF2A1010);
        gfx.fill(x, y + 11, x + w, y + 12, COL_BAD);
        gfx.drawString(font, text, x + 3, y + 2, COL_BAD, false);
        if (clickable) zones.add(new ClickZone(x, y, x + w, y + 12, action));
        return x + w + 3;
    }

    private void drawBody(GuiGraphics gfx, VitalsSyncPayload data, int mx, int my) {
        int panelRight = panelX + Math.round(W * scale);
        int cx = width / 2;
        int cy = height / 2;
        int size = Math.max(20, height / 7);
        int available = cx - panelRight - LIMB_BTN_W / 2 - 6;
        int radiusX = Math.max(LIMB_BTN_W / 2 + 20, Math.min(Math.round(size * 2.6f), available));
        int radiusY = Math.round(size * 1.9f);
        if (radiusX < size * 1.4f + LIMB_BTN_W / 2f + 4) {
            size = Math.max(16, Math.round((radiusX - LIMB_BTN_W / 2f - 4) / 1.4f));
        }

        LivingEntity model = resolveModelEntity();
        if (model != null) {
            int halfW = Math.round(size * 1.4f);
            int halfH = Math.round(size * 1.6f);
            InventoryScreen.renderEntityInInventoryFollowsMouse(gfx, cx - halfW, cy - halfH, cx + halfW, cy + halfH,
                    size, 0.0625f, mx, my, model);
        }

        for (int i = 0; i < RING.length; i++) {
            double angle = Math.toRadians(-90 + i * 60);
            int bx = cx + (int) Math.round(Math.cos(angle) * radiusX);
            int by = cy + (int) Math.round(Math.sin(angle) * radiusY);
            limbButton(gfx, data, RING[i], bx, by, mx, my);
        }
        gfx.drawCenteredString(font, "Click a limb to inspect it", cx, height - 14, COL_DIM);
    }

    @Nullable
    private LivingEntity resolveModelEntity() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return null;
        return mc.level.getEntity(targetEntityId) instanceof LivingEntity living ? living : mc.player;
    }

    private void limbButton(GuiGraphics gfx, VitalsSyncPayload data, Limb limb, int cx, int cy, int mx, int my) {
        int x = cx - LIMB_BTN_W / 2;
        int y = cy - LIMB_BTN_H / 2;
        int i = limb.ordinal();
        LimbHealth limbs = data.limbs();
        float health = Math.min(limbs.getSkin(limb), limbs.getMuscle(limb));
        int status;
        if (data.bleed()[i] > 0 && !limbs.hasTourniquet(limb)) status = 0xFFC23030;
        else if (limbs.getInfection(limb) > 25 || data.shrapnel()[i] > 0 || data.dislocated()[i] || limbs.hasTourniquet(limb)) status = 0xFFC28A20;
        else if (health < 50) status = 0xFF8AA030;
        else status = 0xFF2FA85A;

        boolean hover = mx >= x && mx < x + LIMB_BTN_W && my >= y && my < y + LIMB_BTN_H;
        boolean active = limb == selected;
        gfx.fill(x - 1, y - 1, x + LIMB_BTN_W + 1, y + LIMB_BTN_H + 1, active || hover ? COL_TEXT : COL_BORDER);
        gfx.fill(x, y, x + LIMB_BTN_W, y + LIMB_BTN_H, COL_PANEL);
        gfx.fill(x, y, x + 3, y + LIMB_BTN_H, status);
        gfx.drawCenteredString(font, limb.getDisplayName().getString(), cx + 1, y + 3, active ? COL_TEXT : COL_DIM);
        int barW = LIMB_BTN_W - 10;
        bar(gfx, x + 6, y + LIMB_BTN_H - 8, barW, 5, health / 100f, status);
        screenZones.add(new ClickZone(x, y, x + LIMB_BTN_W, y + LIMB_BTN_H, () -> selected = limb));
    }

    @Nullable
    private static String criticalWarning(Vitals v) {
        if (v.get(Stat.DYING_TIME) > 0) return "FLATLINE " + (int) Math.ceil(v.get(Stat.DYING_TIME)) + "s";
        if (v.isCardiacArrest() || v.get(Stat.HEART_RATE) < 1) return "CARDIAC ARREST";
        if (v.get(Stat.RESP_RATE) <= 0) return "RESPIRATORY ARREST";
        if (v.get(Stat.OPIOID) >= 100) return "OVERDOSE";
        if (v.get(Stat.BLOOD) < 3250 && v.get(Stat.BLEED_RATE) > 0.02f) return "HEMORRHAGE";
        if (v.get(Stat.SYSTOLIC) < 70) return "HYPOTENSION";
        if (v.get(Stat.OXYGEN) < 50) return "HYPOXIA";
        if (v.get(Stat.FIBRILLATION) > 60) return "FIBRILLATION";
        if (v.get(Stat.RADIATION) > 18) return "RADIATION";
        if (v.get(Stat.BRAIN) < 50) return "BRAIN DAMAGE";
        return null;
    }

    public boolean isFlatlining() {
        return flatlining;
    }

    private void beep(Vitals v) {
        float pitch = criticalWarning(v) != null ? 1.15f : 1.0f;
        Minecraft.getInstance().getSoundManager().play(
                net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(com.lunkoashtail.avaliproject.sound.ModSounds.ECG_BEEP.get(), pitch, 0.5f));
    }

    private void flatlineTone() {
        var sounds = Minecraft.getInstance().getSoundManager();
        if (flatlineSound != null && sounds.isActive(flatlineSound)) return;
        flatlineSound = new com.lunkoashtail.avaliproject.client.FlatlineSoundInstance(this);
        sounds.play(flatlineSound);
    }

    private int flag(boolean bad, boolean blink) {
        return bad ? (blink ? COL_BAD : 0xFF8A2020) : COL_TEXT;
    }

    private void label(GuiGraphics gfx, String text, int x, int y, int color) {
        gfx.drawString(font, text, x, y, color, false);
    }

    private void right(GuiGraphics gfx, String text, int r, int y, int color) {
        gfx.drawString(font, text, r - font.width(text), y, color, false);
    }

    private void bar(GuiGraphics gfx, int x, int y, int w, int h, float fraction, int color) {
        gfx.fill(x, y, x + w, y + h, COL_BAR_BG);
        gfx.fill(x, y, x + w, y + 1, COL_DIM);
        gfx.fill(x, y + h - 1, x + w, y + h, COL_DIM);
        int fill = Math.round(Mth.clamp(fraction, 0f, 1f) * (w - 2));
        if (fill > 0) gfx.fill(x + 1, y + 1, x + 1 + fill, y + h - 1, color);
    }

    private static String fmt0(float value) {
        return Integer.toString(Math.round(value));
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 1) {
            onClose();
            return true;
        }
        if (button == 0) {
            for (ClickZone zone : List.copyOf(screenZones)) {
                if (zone.contains(mx, my)) {
                    zone.action().run();
                    return true;
                }
            }
            double localX = (mx - panelX) / scale;
            double localY = (my - panelY) / scale;
            for (ClickZone zone : List.copyOf(zones)) {
                if (zone.contains(localX, localY)) {
                    zone.action().run();
                    return true;
                }
            }
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
