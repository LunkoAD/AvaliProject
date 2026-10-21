package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.client.PackHeadRenderer;
import com.lunkoashtail.avaliproject.network.AvaliOpenAugmentPayload;
import com.lunkoashtail.avaliproject.network.PackInvitePayload;
import com.lunkoashtail.avaliproject.network.PackDataSyncPayload;
import com.lunkoashtail.avaliproject.network.PackKickPayload;
import com.lunkoashtail.avaliproject.network.PackRenamePayload;
import com.lunkoashtail.avaliproject.pack.PackRank;
import com.lunkoashtail.avaliproject.pack.PackRecord;
import com.lunkoashtail.avaliproject.pack.PackRosterEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PackScreen extends Screen {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/gui/pack/pack_panel.png");
    private static final int PANEL_WIDTH = 320;
    private static final int PANEL_HEIGHT = 230;
    private static final float REFERENCE_WIDTH = 400f;
    private static final float REFERENCE_HEIGHT = 320f;
    private static final float MIN_SCALE = 0.5f;
    private static final float MAX_SCALE = 2.0f;

    private static final int HEAD_SIZE = 16;

    private record RosterHead(PackRosterEntry entry, @Nullable LivingEntity dummy, int x, int y) {
    }

    @Nullable
    private static PackScreen activeInstance;

    private final List<RosterHead> heads = new ArrayList<>();

    private PackDataSyncPayload data;
    private int leftPos;
    private int topPos;

    private EditBox nameBox;
    private EditBox inviteBox;

    public PackScreen(PackDataSyncPayload data) {
        super(Component.literal(data.leaderName() + "'s Pack"));
        this.data = data;
    }

    private float scaleFactor() {
        var window = Minecraft.getInstance().getWindow();
        float scale = Math.min(window.getGuiScaledWidth() / REFERENCE_WIDTH, window.getGuiScaledHeight() / REFERENCE_HEIGHT);
        return Mth.clamp(scale, MIN_SCALE, MAX_SCALE);
    }

    @Override
    protected void renderMenuBackground(GuiGraphics guiGraphics) {
    }

    @Override
    protected void init() {
        super.init();
        activeInstance = this;

        float scale = scaleFactor();
        leftPos = (int) ((width / scale - PANEL_WIDTH) / 2);
        topPos = (int) ((height / scale - PANEL_HEIGHT) / 2);

        int leftX = leftPos + 12;
        int rightX = leftPos + PANEL_WIDTH / 2 + 4;
        int topY = topPos + 24;

        int barY = topY + 36;
        nameBox = new EditBox(font, leftX, barY + 46, 144, 18, Component.translatable("screen.avaliproject.pack.rename"));
        nameBox.setValue(data.packName());
        nameBox.setMaxLength(32);
        nameBox.setEditable(data.viewerIsLeader());
        nameBox.setTextColor(0xFFFFAA00);
        addRenderableWidget(nameBox);

        addRenderableWidget(Button.builder(Component.translatable("screen.avaliproject.pack.save_name"),
                        b -> {
                            if (data.viewerIsLeader())
                                PacketDistributor.sendToServer(new PackRenamePayload(nameBox.getValue()));
                        })
                .bounds(leftX, barY + 68, 144, 20).build())
                .active = data.viewerIsLeader();

        addRenderableWidget(Button.builder(Component.translatable("screen.avaliproject.pack.augmentation"),
                        b -> PacketDistributor.sendToServer(new AvaliOpenAugmentPayload()))
                .bounds(leftX, barY + 92, 144, 20).build());

        int rowY = topY;
        if (data.viewerIsLeader() && data.roster().size() < PackRecord.MAX_TOTAL_MEMBERS - 1) {
            inviteBox = new EditBox(font, rightX, rowY, 90, 18, Component.translatable("screen.avaliproject.pack.invite_name"));
            addRenderableWidget(inviteBox);
            addRenderableWidget(Button.builder(Component.translatable("screen.avaliproject.pack.invite"),
                            b -> {
                                if (inviteBox.getValue() != null && !inviteBox.getValue().isBlank())
                                    PacketDistributor.sendToServer(new PackInvitePayload(inviteBox.getValue().trim()));
                            })
                    .bounds(rightX + 94, rowY, 50, 18).build());
            rowY += 24;
        }

        heads.clear();
        for (PackRosterEntry entry : data.roster()) {
            int y = rowY;
            heads.add(new RosterHead(entry, PackHeadRenderer.createDummy(entry), rightX, y));
            addRenderableWidget(Button.builder(Component.literal(entry.name()), b -> {})
                    .bounds(rightX + HEAD_SIZE + 2, y, 100 - HEAD_SIZE - 2, 16).build())
                    .active = false;
            if (!entry.isAvali() && data.viewerIsLeader()) {
                Button kick = Button.builder(Component.translatable("screen.avaliproject.pack.kick"),
                                b -> PacketDistributor.sendToServer(new PackKickPayload(entry.id())))
                        .bounds(rightX + 104, y, 40, 16).build();
                addRenderableWidget(kick);
            }
            rowY += 20;
        }
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        renderBackground(gfx, mouseX, mouseY, partialTick);
        float scale = scaleFactor();
        int scaledMouseX = (int) (mouseX / scale);
        int scaledMouseY = (int) (mouseY / scale);

        gfx.pose().pushPose();
        gfx.pose().scale(scale, scale, 1.0f);

        gfx.blit(GUI_TEXTURE, leftPos, topPos, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, PANEL_WIDTH, PANEL_HEIGHT);

        super.render(gfx, scaledMouseX, scaledMouseY, partialTick);

        gfx.drawCenteredString(font, title, leftPos + PANEL_WIDTH / 2, topPos + 8, 0xFFFFAA00);

        int leftX = leftPos + 12;
        int rightX = leftPos + PANEL_WIDTH / 2 + 4;
        int topY = topPos + 24;
        PackRank rank = PackRank.forXp(data.rankXp());

        gfx.blit(rank.iconLocation(), leftX + 56, topY, 0, 0, 32, 32, 32, 32);

        int barY = topY + 36;
        int barW = 144;
        int nextThreshold = rank.xpForNextRank();
        float progress = nextThreshold < 0 ? 1.0f
                : (float) (data.rankXp() - rank.xpThreshold()) / Math.max(1, nextThreshold - rank.xpThreshold());
        gfx.fill(leftX, barY, leftX + barW, barY + 8, 0xFF222222);
        gfx.fill(leftX, barY, leftX + (int) (barW * Math.min(1.0f, Math.max(0.0f, progress))), barY + 8, 0xFF00CFFF);

        gfx.drawCenteredString(font, rank.displayName(),leftX + barW / 2, barY + 12, 0xFFE0B080);
        gfx.drawCenteredString(font, Component.translatable("screen.avaliproject.pack.lume_bits", data.lumeBits()).getString(),
                leftX + barW / 2, barY + 26, 0xFF55FFFF);

        gfx.drawString(font, Component.translatable("screen.avaliproject.pack.roster"), rightX, topY - 12, 0xFFFFFFFF, false);

        gfx.pose().popPose();

        int headSize = Math.round(HEAD_SIZE * scale);
        for (RosterHead head : heads) {
            PackHeadRenderer.render(gfx, head.entry(), head.dummy(),
                    Math.round(head.x() * scale), Math.round(head.y() * scale), headSize);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float scale = scaleFactor();
        return super.mouseClicked(mouseX / scale, mouseY / scale, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        float scale = scaleFactor();
        return super.mouseReleased(mouseX / scale, mouseY / scale, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        float scale = scaleFactor();
        return super.mouseDragged(mouseX / scale, mouseY / scale, button, dragX / scale, dragY / scale);
    }

    @Override
    protected void renderBlurredBackground(float partialTick) {
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static void onDataSync(PackDataSyncPayload payload) {
        if (activeInstance == null) {
            Minecraft.getInstance().setScreen(new PackScreen(payload));
        } else {
            activeInstance.data = payload;
            activeInstance.refreshFromData();
        }
    }

    private void refreshFromData() {
        clearWidgets();
        init();
    }

    @Override
    public void removed() {
        if (activeInstance == this)
            activeInstance = null;
        super.removed();
    }
}
