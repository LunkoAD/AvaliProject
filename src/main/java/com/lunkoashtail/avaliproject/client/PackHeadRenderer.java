package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.client.event.SpeciesRenderHandler;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.lunkoashtail.avaliproject.pack.PackRosterEntry;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesCosmetics;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.UUID;

public final class PackHeadRenderer {
    private static final float HEAD_SPAN_OF_HEIGHT = 0.5f;
    private static final float HEAD_CENTER_ABOVE_EYE = 0.08f;
    private static final float FACING_YAW = 200.0f;

    private PackHeadRenderer() {
    }

    @Nullable
    public static LivingEntity createDummy(PackRosterEntry entry) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return null;
        if (entry.isAvali()) {
            AvaliEntity avali = new AvaliEntity(ModEntities.AVALI.get(), level);
            avali.setMale(entry.male());
            avali.setTexture(entry.skin());
            if (entry.baby()) avali.setAge(-24000);
            avali.refreshDimensions();
            return avali;
        }

        Species species = entry.species();
        if (species == Species.HUMAN) return null;
        ManokitVariant subspecies = entry.subspecies();
        LivingEntity dummy = SpeciesRenderHandler.createDummy(species, subspecies, level);
        if (dummy == null) return null;
        SpeciesCosmetics.Entry cosmetic = SpeciesCosmetics.find(species, subspecies, entry.skin())
                .or(() -> SpeciesCosmetics.defaultFor(species, subspecies)).orElse(null);
        SpeciesRenderHandler.applyCosmetic(dummy, species, subspecies, cosmetic);
        CustomSkinTextures.find(entry.id(), species, subspecies, entry.skin())
                .ifPresent(texture -> SpeciesCosmeticTextures.set(dummy, texture));
        dummy.refreshDimensions();
        return dummy;
    }

    public static void render(GuiGraphics gfx, PackRosterEntry entry, @Nullable LivingEntity dummy, int x, int y, int size) {
        if (dummy == null) {
            PlayerFaceRenderer.draw(gfx, skinOf(entry.id()), x, y, size);
            return;
        }
        renderHead(gfx, dummy, x, y, size);
    }

    public static void renderHead(GuiGraphics gfx, LivingEntity dummy, int x, int y, int size) {
        dummy.yBodyRot = FACING_YAW;
        dummy.yBodyRotO = FACING_YAW;
        dummy.yHeadRot = FACING_YAW;
        dummy.yHeadRotO = FACING_YAW;
        dummy.setYRot(FACING_YAW);
        dummy.yRotO = FACING_YAW;
        dummy.setXRot(0.0f);
        dummy.xRotO = 0.0f;

        float scale = size / Math.max(0.1f, dummy.getBbHeight() * HEAD_SPAN_OF_HEIGHT);
        gfx.enableScissor(x, y, x + size, y + size);
        InventoryScreen.renderEntityInInventory(gfx, x + size / 2.0f, y + size / 2.0f, scale,
                new Vector3f(0.0f, dummy.getEyeHeight() + dummy.getBbHeight() * HEAD_CENTER_ABOVE_EYE, 0.0f), Axis.ZP.rotationDegrees(180.0f), null, dummy);
        gfx.disableScissor();
    }

    private static PlayerSkin skinOf(UUID id) {
        var connection = Minecraft.getInstance().getConnection();
        PlayerInfo info = connection != null ? connection.getPlayerInfo(id) : null;
        return info != null ? info.getSkin() : DefaultPlayerSkin.get(id);
    }
}
