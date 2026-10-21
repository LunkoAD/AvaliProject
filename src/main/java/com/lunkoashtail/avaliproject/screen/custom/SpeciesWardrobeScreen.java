package com.lunkoashtail.avaliproject.screen.custom;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.client.CustomSkinFolder;
import com.lunkoashtail.avaliproject.client.CustomSkinTextures;
import com.lunkoashtail.avaliproject.client.SpeciesCosmeticTextures;
import com.lunkoashtail.avaliproject.client.event.SpeciesRenderHandler;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.network.CustomSkinUploadPayload;
import com.lunkoashtail.avaliproject.network.SpeciesWardrobeSelectPayload;
import com.lunkoashtail.avaliproject.network.SubspeciesSelectPayload;
import com.lunkoashtail.avaliproject.species.CustomSkins;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesCosmeticData;
import com.lunkoashtail.avaliproject.species.SpeciesCosmetics;
import com.lunkoashtail.avaliproject.species.SpeciesUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpeciesWardrobeScreen extends Screen {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/gui/wardrobe/wardrobe_panel.png");
    private static final int PANEL_WIDTH = 380;
    private static final int PANEL_HEIGHT = 230;
    private static final int PREVIEW_WIDTH = 130;
    private static final int PREVIEW_X = 6;
    private static final int ROW_HEIGHT = 20;
    private static final int HEADER_HEIGHT = 24;
    private static final int LIST_BOX_BOTTOM = 188;
    private static final int BOX_TOP = 22;
    private static final int LIST_BOX_LEFT = 138;
    private static final int LIST_BOX_RIGHT = 373;
    private static final long DOUBLE_CLICK_MS = 300L;
    private static final int SUBSPECIES_BUTTON_WIDTH = 130;
    private static final float REFERENCE_WIDTH = 400f;
    private static final float REFERENCE_HEIGHT = 320f;
    private static final float MIN_SCALE = 0.5f;
    private static final float MAX_SCALE = 2.0f;
    private static final int RESCAN_TICKS = 40;
    private static final ResourceLocation INVALID_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/entity/invalid_custom_skin.png");

    private static final int SCROLLBAR_COLOR = 0xFFC6C6C6;
    private static final int TITLE_COLOR = 0xFF404040;
    private static final int HIGHLIGHT_COLOR = 0x60FFFFFF;
    private static final int HOVER_COLOR = 0x30FFFFFF;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int APPLIED_COLOR = 0xFF7CFC9A;
    private static final int MUTED_COLOR = 0xFFDDDDDD;

    private Species species = Species.HUMAN;
    private ManokitVariant subspecies = ManokitVariant.DEFAULT;
    private List<SpeciesCosmetics.Entry> entries = List.of();
    private final Map<String, Path> localFiles = new HashMap<>();
    private List<Path> scannedFiles = List.of();
    private int rescanTimer;
    @Nullable
    private LivingEntity previewDummy;
    private int highlighted = -1;
    private int scroll;
    private int lastClickedRow = -1;
    private long lastClickTime;

    private int left;
    private int top;

    private Button applyButton;
    private Button resetButton;
    private Button subspeciesButton;

    public SpeciesWardrobeScreen() {
        super(Component.translatable("screen.avaliproject.wardrobe"));
    }

    private float scaleFactor() {
        var window = Minecraft.getInstance().getWindow();
        float scale = Math.min(window.getGuiScaledWidth() / REFERENCE_WIDTH, window.getGuiScaledHeight() / REFERENCE_HEIGHT);
        return Mth.clamp(scale, MIN_SCALE, MAX_SCALE);
    }

    @Override
    protected void init() {
        super.init();
        float scale = scaleFactor();
        left = (int) ((width / scale - PANEL_WIDTH) / 2);
        top = (int) ((height / scale - PANEL_HEIGHT) / 2);

        int buttonY = top + LIST_BOX_BOTTOM + 8;
        int listX = listLeft();
        int buttonWidth = (listWidth() - 12) / 4;
        applyButton = addRenderableWidget(Button.builder(Component.translatable("screen.avaliproject.wardrobe.apply"),
                b -> applyHighlighted()).bounds(listX, buttonY, buttonWidth, 20).build());
        resetButton = addRenderableWidget(Button.builder(Component.translatable("screen.avaliproject.wardrobe.reset"),
                b -> sendSelection(SpeciesWardrobeSelectPayload.RESET)).bounds(listX + buttonWidth + 4, buttonY, buttonWidth, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("screen.avaliproject.wardrobe.folder"), b -> openSkinFolder())
                .bounds(listX + (buttonWidth + 4) * 2, buttonY, buttonWidth, 20)
                .tooltip(Tooltip.create(Component.translatable("screen.avaliproject.wardrobe.folder.tooltip")))
                .build());
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), b -> onClose())
                .bounds(listX + (buttonWidth + 4) * 3, buttonY, buttonWidth, 20).build());
        subspeciesButton = addRenderableWidget(Button.builder(Component.empty(), b -> cycleSubspecies())
                .bounds(left + LIST_BOX_RIGHT - SUBSPECIES_BUTTON_WIDTH, top + 2, SUBSPECIES_BUTTON_WIDTH, 18).build());

        rebuildForCurrentSpecies();
    }

    private void rebuildForCurrentSpecies() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        species = player.getData(ModAttachments.SPECIES);
        subspecies = player.getData(ModAttachments.MANOKIT_SUBSPECIES);
        rebuildEntries();
        previewDummy = SpeciesRenderHandler.createDummy(species, subspecies, player.level());
        scroll = 0;
        highlighted = appliedIndex();
        if (highlighted < 0 && !entries.isEmpty()) highlighted = 0;
        lastClickedRow = -1;
        updateButtons();
    }

    private void rebuildEntries() {
        localFiles.clear();
        if (species == Species.HUMAN) {
            entries = List.of();
            scannedFiles = List.of();
            return;
        }
        List<SpeciesCosmetics.Entry> built = new ArrayList<>(SpeciesCosmetics.forModel(species, subspecies));
        scannedFiles = CustomSkinFolder.list(species, subspecies);
        ManokitVariant model = species == Species.MANOKIT ? subspecies : null;
        for (Path file : scannedFiles) {
            String name = CustomSkinFolder.nameOf(file);
            String id = CustomSkins.choiceFor(name);
            ResourceLocation texture = CustomSkinTextures.local(file);
            built.add(new SpeciesCosmetics.Entry(species, model, id, "wardrobe." + AvaliProject.MOD_ID + ".custom",
                    name, texture != null ? texture : INVALID_TEXTURE, SpeciesCosmetics.NO_VARIANT));
            localFiles.put(id, file);
        }
        entries = List.copyOf(built);
    }

    private void rescanLocalSkins() {
        if (species == Species.HUMAN) return;
        List<Path> current = CustomSkinFolder.list(species, subspecies);
        boolean changed = !current.equals(scannedFiles)
                || current.stream().anyMatch(file -> CustomSkinTextures.local(file) != textureOf(file));
        if (!changed) return;
        String highlightedId = highlighted >= 0 && highlighted < entries.size() ? entries.get(highlighted).id() : null;
        rebuildEntries();
        highlighted = indexOf(highlightedId);
        if (highlighted < 0 && !entries.isEmpty()) highlighted = 0;
        scroll = Mth.clamp(scroll, 0, maxScroll());
        lastClickedRow = -1;
    }

    @Nullable
    private ResourceLocation textureOf(Path file) {
        for (SpeciesCosmetics.Entry entry : entries) {
            if (file.equals(localFiles.get(entry.id()))) {
                return entry.texture().equals(INVALID_TEXTURE) ? null : entry.texture();
            }
        }
        return null;
    }

    private int indexOf(@Nullable String id) {
        if (id == null) return -1;
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).id().equals(id)) return i;
        }
        return -1;
    }

    private int appliedIndex() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || entries.isEmpty()) return -1;
        SpeciesCosmeticData data = player.getData(ModAttachments.SPECIES_COSMETICS);
        Optional<String> choice = data.choice(species);
        if (choice.isPresent() && CustomSkins.isCustom(choice.get())) {
            return CustomSkinTextures.forPlayer(player).isPresent() ? indexOf(choice.get()) : 0;
        }
        return SpeciesCosmetics.resolve(species, subspecies, data).map(entries::indexOf).orElse(-1);
    }

    private boolean isUsable(int index) {
        if (index < 0 || index >= entries.size()) return false;
        Path file = localFiles.get(entries.get(index).id());
        return file == null || CustomSkinTextures.isLocalValid(file);
    }

    private void openSkinFolder() {
        CustomSkinFolder.ensureCreated();
        Path folder = species == Species.HUMAN ? CustomSkinFolder.root() : CustomSkinFolder.folder(species, subspecies);
        Util.getPlatform().openFile(folder.toFile());
    }

    private void updateButtons() {
        applyButton.active = isUsable(highlighted) && highlighted != appliedIndex();
        resetButton.active = !entries.isEmpty();
        subspeciesButton.visible = species == Species.MANOKIT;
        subspeciesButton.setMessage(Component.translatable("screen.avaliproject.wardrobe.subspecies",
                SpeciesUtil.displayName(subspecies)));
    }

    private void cycleSubspecies() {
        if (species != Species.MANOKIT) return;
        ManokitVariant[] variants = ManokitVariant.values();
        ManokitVariant next = variants[(subspecies.ordinal() + 1) % variants.length];
        PacketDistributor.sendToServer(new SubspeciesSelectPayload(next.id()));
    }

    @Override
    public void tick() {
        super.tick();
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            onClose();
            return;
        }
        if (player.getData(ModAttachments.SPECIES) != species
                || (species == Species.MANOKIT && player.getData(ModAttachments.MANOKIT_SUBSPECIES) != subspecies)) {
            rebuildForCurrentSpecies();
        } else if (++rescanTimer >= RESCAN_TICKS) {
            rescanTimer = 0;
            rescanLocalSkins();
        }
        updateButtons();
    }

    private void applyHighlighted() {
        if (!isUsable(highlighted)) return;
        SpeciesCosmetics.Entry entry = entries.get(highlighted);
        Path file = localFiles.get(entry.id());
        if (file == null) {
            sendSelection(entry.id());
            return;
        }
        byte[] png = CustomSkinTextures.readLocal(file);
        if (png == null) return;
        CustomSkinUploadPayload.sendToServer(CustomSkins.folder(species, subspecies), entry.fallbackName(), png);
    }

    private void sendSelection(String variantId) {
        if (species == Species.HUMAN) return;
        PacketDistributor.sendToServer(new SpeciesWardrobeSelectPayload(SpeciesUtil.id(species), variantId));
    }

    private int listLeft() {
        return left + LIST_BOX_LEFT + 2;
    }

    private int listTop() {
        return top + HEADER_HEIGHT;
    }

    private int listWidth() {
        return LIST_BOX_RIGHT - LIST_BOX_LEFT - 7;
    }

    private int listHeight() {
        return LIST_BOX_BOTTOM - 2 - HEADER_HEIGHT;
    }

    private int visibleRows() {
        return Math.max(1, listHeight() / ROW_HEIGHT);
    }

    private int maxScroll() {
        return Math.max(0, entries.size() - visibleRows());
    }

    private int rowAt(double mouseX, double mouseY) {
        if (mouseX < listLeft() || mouseX >= listLeft() + listWidth()) return -1;
        if (mouseY < listTop() || mouseY >= listTop() + visibleRows() * ROW_HEIGHT) return -1;
        int row = scroll + (int) ((mouseY - listTop()) / ROW_HEIGHT);
        return row < entries.size() ? row : -1;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float scale = scaleFactor();
        double x = mouseX / scale;
        double y = mouseY / scale;
        if (super.mouseClicked(x, y, button)) return true;
        int row = rowAt(x, y);
        if (button != 0 || row < 0) return false;
        long now = Util.getMillis();
        boolean doubleClick = row == lastClickedRow && now - lastClickTime < DOUBLE_CLICK_MS;
        highlighted = row;
        lastClickedRow = row;
        lastClickTime = now;
        if (doubleClick) applyHighlighted();
        updateButtons();
        return true;
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
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        float scale = scaleFactor();
        double x = mouseX / scale;
        double y = mouseY / scale;
        if (x >= listLeft() && x < listLeft() + listWidth() && y >= listTop() && y < listTop() + listHeight()) {
            scroll = Mth.clamp(scroll - (int) Math.signum(scrollY), 0, maxScroll());
            return true;
        }
        return super.mouseScrolled(x, y, scrollX, scrollY);
    }

    @Override
    public void renderBackground(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
    }

    @Override
    public void renderTransparentBackground(GuiGraphics gfx) {
    }

    @Override
    protected void renderMenuBackground(GuiGraphics gfx) {
    }

    @Override
    protected void renderBlurredBackground(float partialTick) {
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        float scale = scaleFactor();
        int scaledMouseX = (int) (mouseX / scale);
        int scaledMouseY = (int) (mouseY / scale);

        gfx.pose().pushPose();
        gfx.pose().scale(scale, scale, 1.0F);
        gfx.blit(GUI_TEXTURE, left, top, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, PANEL_WIDTH, PANEL_HEIGHT);
        super.render(gfx, scaledMouseX, scaledMouseY, partialTick);
        Component heading = Component.translatable("screen.avaliproject.wardrobe.title", SpeciesUtil.displayName(species));
        gfx.drawString(font, heading, left + 8, top + 8, TITLE_COLOR, false);
        renderList(gfx, scaledMouseX, scaledMouseY);
        gfx.pose().popPose();

        renderPreview(gfx, scale, mouseX, mouseY);
    }

    private void renderPreview(GuiGraphics gfx, float guiScale, int mouseX, int mouseY) {
        LocalPlayer player = Minecraft.getInstance().player;
        LivingEntity shown = player;
        if (species != Species.HUMAN && previewDummy != null) {
            SpeciesCosmetics.Entry entry = highlighted >= 0 && highlighted < entries.size() ? entries.get(highlighted) : null;
            SpeciesRenderHandler.applyCosmetic(previewDummy, species, subspecies, entry);
            shown = previewDummy;
        }
        if (shown == null) return;
        int x1 = Math.round((left + PREVIEW_X) * guiScale);
        int y1 = Math.round((top + BOX_TOP) * guiScale);
        int x2 = Math.round((left + PREVIEW_WIDTH) * guiScale);
        int y2 = Math.round((top + PANEL_HEIGHT - PREVIEW_X) * guiScale);
        int scale = Math.round((y2 - y1) * 0.7F / Math.max(0.6F, shown.getBbHeight()));
        InventoryScreen.renderEntityInInventoryFollowsMouse(gfx, x1, y1, x2, y2, scale, 0.0625F,
                (float) mouseX, (float) mouseY, shown);
    }

    private void renderList(GuiGraphics gfx, int mouseX, int mouseY) {
        int x = listLeft();
        int y = listTop();
        int w = listWidth();
        if (entries.isEmpty()) {
            gfx.drawCenteredString(font, Component.translatable("screen.avaliproject.wardrobe.empty"),
                    x + w / 2, y + listHeight() / 2 - 4, MUTED_COLOR);
            return;
        }
        int applied = appliedIndex();
        int hovered = rowAt(mouseX, mouseY);
        int end = Math.min(entries.size(), scroll + visibleRows());
        for (int i = scroll; i < end; i++) {
            SpeciesCosmetics.Entry entry = entries.get(i);
            int rowY = y + (i - scroll) * ROW_HEIGHT;
            if (i == highlighted) {
                gfx.fill(x, rowY, x + w, rowY + ROW_HEIGHT - 2, HIGHLIGHT_COLOR);
            } else if (i == hovered) {
                gfx.fill(x, rowY, x + w, rowY + ROW_HEIGHT - 2, HOVER_COLOR);
            }
            Component label = Component.translatableWithFallback(entry.translationKey(), entry.fallbackName());
            if (i == 0) {
                label = label.copy().append(" ").append(Component.translatable("screen.avaliproject.wardrobe.default"));
            }
            boolean custom = localFiles.containsKey(entry.id());
            if (custom) {
                label = label.copy().append(" ").append(Component.translatable("screen.avaliproject.wardrobe.custom"));
            }
            if (custom && !isUsable(i)) {
                label = label.copy().append(" ").append(Component.translatable("screen.avaliproject.wardrobe.invalid"));
            } else if (!SpeciesCosmeticTextures.exists(entry.texture())) {
                label = label.copy().append(" ").append(Component.translatable("screen.avaliproject.wardrobe.missing"));
            }
            gfx.drawString(font, label, x + 6, rowY + 5, i == applied ? APPLIED_COLOR : TEXT_COLOR);
        }
        if (maxScroll() > 0) {
            int trackHeight = visibleRows() * ROW_HEIGHT;
            int thumbHeight = Math.max(10, trackHeight * visibleRows() / entries.size());
            int thumbY = y + (trackHeight - thumbHeight) * scroll / maxScroll();
            gfx.fill(x + w + 2, thumbY, x + w + 5, thumbY + thumbHeight, SCROLLBAR_COLOR);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
