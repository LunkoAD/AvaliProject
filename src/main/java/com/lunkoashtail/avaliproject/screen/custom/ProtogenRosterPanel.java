package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.block.entity.custom.ProtogenRegistrar;
import com.lunkoashtail.avaliproject.client.PackHeadRenderer;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.custom.ProtogenEntity;
import com.lunkoashtail.avaliproject.network.ProtogenAssignPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class ProtogenRosterPanel {
    public static final int WIDTH = 112;
    public static final int GAP = 4;
    private static final int HEADER = 20;
    private static final int ROW_HEIGHT = 32;
    private static final int HEAD_SIZE = 28;
    private static final Map<String, LivingEntity> DUMMIES = new HashMap<>();

    private final BlockPos pos;
    private final Supplier<UUID> registered;
    private List<ProtogenEntity> protogens = List.of();
    private int scroll;
    private int refreshCooldown;
    private int x;
    private int y;
    private int height;

    public ProtogenRosterPanel(BlockPos pos, Supplier<UUID> registered) {
        this.pos = pos;
        this.registered = registered;
    }

    public void setBounds(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    private int visibleRows() {
        return Math.max(1, (height - HEADER - 6) / ROW_HEIGHT);
    }

    public void tick() {
        if (--refreshCooldown > 0) return;
        refreshCooldown = 10;
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.level == null) return;
        Vec3 center = Vec3.atCenterOf(pos);
        double radius = ProtogenRegistrar.ROSTER_RADIUS;
        protogens = minecraft.level.getEntitiesOfClass(ProtogenEntity.class, new AABB(pos).inflate(radius),
                        protogen -> protogen.isAlive() && protogen.isOwnedBy(player) && protogen.distanceToSqr(center) <= radius * radius)
                .stream()
                .sorted(Comparator.comparingDouble(protogen -> protogen.distanceToSqr(center)))
                .toList();
        scroll = Math.max(0, Math.min(scroll, protogens.size() - visibleRows()));
    }

    public void render(GuiGraphics gfx, Font font, int mouseX, int mouseY) {
        ProtogenMachineScreen.drawPanel(gfx, x, y, WIDTH, height);
        gfx.drawString(font, Component.translatable("gui.avaliproject.protogen_roster.title"), x + 8, y + 7, ProtogenMachineScreen.ACCENT, false);
        if (protogens.isEmpty()) {
            gfx.drawWordWrap(font, Component.translatable("gui.avaliproject.protogen_roster.empty"), x + 7, y + HEADER + 4, WIDTH - 14, ProtogenMachineScreen.TEXT);
            return;
        }
        UUID selected = registered.get();
        int rows = Math.min(visibleRows(), protogens.size() - scroll);
        for (int row = 0; row < rows; row++) {
            ProtogenEntity protogen = protogens.get(scroll + row);
            int rowX = x + 5;
            int rowY = y + HEADER + row * ROW_HEIGHT;
            boolean isSelected = protogen.getUUID().equals(selected);
            boolean hovered = mouseX >= rowX && mouseX < rowX + WIDTH - 10 && mouseY >= rowY && mouseY < rowY + ROW_HEIGHT - 2;
            int face = isSelected ? ProtogenMachineScreen.ROW_SELECTED : hovered ? ProtogenMachineScreen.ROW_HOVER : ProtogenMachineScreen.ROW;
            ProtogenMachineScreen.drawInset(gfx, rowX, rowY, WIDTH - 10, ROW_HEIGHT - 2, face);
            LivingEntity dummy = dummyFor(protogen.getSkin());
            if (dummy != null) {
                PackHeadRenderer.renderHead(gfx, dummy, rowX + 1, rowY + 1, HEAD_SIZE);
            }
            int textX = rowX + HEAD_SIZE + 4;
            gfx.drawString(font, font.plainSubstrByWidth(protogen.getDisplayName().getString(), WIDTH - HEAD_SIZE - 22), textX, rowY + 5, 0xE6FCFF, false);
            Component detail = isSelected
                    ? Component.translatable("gui.avaliproject.protogen_roster.registered")
                    : Component.literal(prettySkin(protogen.getSkin()));
            gfx.drawString(font, detail, textX, rowY + 17, isSelected ? ProtogenMachineScreen.GOOD_TEXT : ProtogenMachineScreen.MUTED, false);
        }
        if (protogens.size() > visibleRows()) {
            String more = (scroll + 1) + "-" + (scroll + rows) + "/" + protogens.size();
            gfx.drawString(font, more, x + WIDTH - 7 - font.width(more), y + 7, ProtogenMachineScreen.MUTED, false);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY) {
        int rows = Math.min(visibleRows(), protogens.size() - scroll);
        for (int row = 0; row < rows; row++) {
            int rowX = x + 5;
            int rowY = y + HEADER + row * ROW_HEIGHT;
            if (mouseX < rowX || mouseX >= rowX + WIDTH - 10 || mouseY < rowY || mouseY >= rowY + ROW_HEIGHT - 2) continue;
            ProtogenEntity protogen = protogens.get(scroll + row);
            boolean isSelected = protogen.getUUID().equals(registered.get());
            PacketDistributor.sendToServer(new ProtogenAssignPayload(pos, isSelected ? ProtogenAssignPayload.UNASSIGN : protogen.getId()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            refreshCooldown = 5;
            return true;
        }
        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (mouseX < x || mouseX >= x + WIDTH || mouseY < y || mouseY >= y + height) return false;
        scroll = Math.max(0, Math.min(scroll - (int) Math.signum(delta), protogens.size() - visibleRows()));
        return true;
    }

    @Nullable
    public static LivingEntity dummyFor(String skin) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return null;
        LivingEntity dummy = DUMMIES.get(skin);
        if (dummy == null || dummy.level() != level) {
            ProtogenEntity created = ModEntities.PROTOGEN.get().create(level);
            if (created == null) return null;
            created.setSkin(skin);
            DUMMIES.put(skin, created);
            dummy = created;
        }
        return dummy;
    }

    public static String prettySkin(String skin) {
        if (skin.isEmpty()) return "Protogen";
        return skin.substring(0, 1).toUpperCase(Locale.ROOT) + skin.substring(1).replace('_', ' ');
    }
}
