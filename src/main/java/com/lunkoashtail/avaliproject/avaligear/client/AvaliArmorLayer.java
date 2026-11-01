package com.lunkoashtail.avaliproject.avaligear.client;

import com.lunkoashtail.avaliproject.avaligear.AvaliGear;
import com.lunkoashtail.avaliproject.avaligear.armor.AvaliArmorItem;
import com.lunkoashtail.avaliproject.client.event.SpeciesRenderHandler;
import com.lunkoashtail.avaliproject.entity.client.AvaliModel;
import com.lunkoashtail.avaliproject.entity.client.geo.GeoModelLoader;
import com.lunkoashtail.avaliproject.entity.client.geo.GeoModelParts;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class AvaliArmorLayer extends RenderLayer<AvaliEntity, AvaliModel> {
    private static final EquipmentSlot[] SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private static final Map<String, GeoModelParts> MODELS = new HashMap<>();
    private static final Map<LivingEntity, float[]> VISOR = new WeakHashMap<>();
    private static final float VISOR_OPEN_RAD = 100.0F * Mth.DEG_TO_RAD;

    public AvaliArmorLayer(RenderLayerParent<AvaliEntity, AvaliModel> parent) {
        super(parent);
    }

    public static void clearCache() {
        MODELS.clear();
    }

    public static LivingEntity source(AvaliEntity entity) {
        Player owner = SpeciesRenderHandler.ownerOf(entity);
        return owner != null ? owner : entity;
    }

    private static String slotName(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> "head";
            case CHEST -> "chest";
            case LEGS -> "legs";
            default -> "feet";
        };
    }

    private static GeoModelParts model(String name) {
        return MODELS.computeIfAbsent(name, n -> GeoModelLoader.load(Minecraft.getInstance().getResourceManager(),
                AvaliGear.id("geo/avali_armor/" + n + ".geo.json")));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int packedLight, AvaliEntity entity, float limbSwing,
                       float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity source = source(entity);
        GeoModelParts body = getParentModel().parts();
        for (EquipmentSlot slot : SLOTS) {
            ItemStack stack = source.getItemBySlot(slot);
            if (!(stack.getItem() instanceof AvaliArmorItem armor) || armor.getEquipmentSlot() != slot) continue;
            GeoModelParts parts = model(armor.model());
            String prefix = slotName(slot) + "|";
            float visor = armor.hasVisor() ? visorProgress(source, stack, ageInTicks) : 0.0F;
            for (Map.Entry<String, ModelPart> e : parts.parts().entrySet()) {
                String name = e.getKey();
                ModelPart part = e.getValue();
                if (name.indexOf('|') >= 0) {
                    part.resetPose();
                    part.visible = name.startsWith(prefix);
                } else if (name.equals("visor")) {
                    part.resetPose();
                    part.xRot += visor * VISOR_OPEN_RAD;
                } else {
                    ModelPart src = body.part(name);
                    if (src != null) part.loadPose(src.storePose());
                }
            }
            ResourceLocation texture = AvaliGear.id("textures/models/avali_armor/" + armor.texture() + ".png");
            VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffers, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
            parts.root().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }

    private static float visorProgress(LivingEntity source, ItemStack helmet, float ageInTicks) {
        float target = Boolean.TRUE.equals(helmet.get(AvaliGear.VISOR_OPEN)) ? 1.0F : 0.0F;
        float[] st = VISOR.computeIfAbsent(source, k -> new float[]{target, ageInTicks});
        float dt = Mth.clamp(ageInTicks - st[1], 0.0F, 5.0F);
        st[1] = ageInTicks;
        float step = dt * 0.12F;
        st[0] = st[0] < target ? Math.min(target, st[0] + step) : Math.max(target, st[0] - step);
        float t = st[0];
        return t * t * (3 - 2 * t);
    }
}
