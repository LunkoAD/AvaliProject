package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.entity.client.geo.GeoModelLoader;
import com.lunkoashtail.avaliproject.entity.client.geo.GeoModelParts;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesCosmetics;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public final class SpeciesFirstPersonArm {
    private static final float PIXEL = 1.0F / 16.0F;
    private static final float VANILLA_SHOULDER_X = 5.0F * PIXEL;
    private static final float VANILLA_SHOULDER_Y = 2.0F * PIXEL;
    private static final float VANILLA_ARM_CENTER_X = 1.0F * PIXEL;
    private static final float VANILLA_HAND_BOTTOM = 10.0F * PIXEL;
    private static final List<String> RIGHT_ARM_BONES = List.of("rightarm", "RightArm", "ArmR");
    private static final List<String> LEFT_ARM_BONES = List.of("leftarm", "LeftArm", "ArmL");

    private record ArmModel(@Nullable ModelPart arm, List<ModelPart> bones, float twist,
                            float centerX, float centerZ, float bottomY) {
    }

    private static final Map<String, ArmModel> CACHE = new HashMap<>();

    private SpeciesFirstPersonArm() {
    }

    @SubscribeEvent
    public static void onRenderArm(RenderArmEvent event) {
        AbstractClientPlayer player = event.getPlayer();
        Species species = player.getData(ModAttachments.SPECIES);
        if (species == Species.HUMAN) return;
        ManokitVariant subspecies = player.getData(ModAttachments.MANOKIT_SUBSPECIES);
        String geo = geoPath(species, subspecies);
        if (geo == null) return;

        HumanoidArm side = event.getArm();
        ArmModel model = CACHE.computeIfAbsent(geo + "#" + side, key -> build(geo, side, species, subspecies));
        if (model.arm() == null) return;
        ResourceLocation texture = texture(player, species, subspecies);
        if (texture == null) return;

        event.setCanceled(true);
        float sign = side == HumanoidArm.RIGHT ? -1.0F : 1.0F;
        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();
        poseStack.translate(sign * VANILLA_SHOULDER_X + sign * VANILLA_ARM_CENTER_X - model.centerX(),
                VANILLA_SHOULDER_Y + VANILLA_HAND_BOTTOM - model.bottomY(),
                -model.centerZ());
        straighten(model);
        model.arm().render(poseStack, event.getMultiBufferSource().getBuffer(RenderType.entityTranslucent(texture)),
                event.getPackedLight(), OverlayTexture.NO_OVERLAY);
        model.bones().forEach(ModelPart::resetPose);
        poseStack.popPose();
    }

    @Nullable
    private static String geoPath(Species species, ManokitVariant subspecies) {
        return switch (species) {
            case EXPIE -> "geo/expie.geo.json";
            case AVALI -> "geo/avali.geo.json";
            case SERGAL -> "geo/sergal.geo.json";
            case PROTOGEN -> "geo/protogen.geo.json";
            case WICKERBEAST -> "geo/wickerbeast.geo.json";
            case MANOKIT -> subspecies.geoPath();
            default -> null;
        };
    }

    @Nullable
    private static ResourceLocation texture(AbstractClientPlayer player, Species species, ManokitVariant subspecies) {
        SpeciesCosmetics.Entry fallback = SpeciesCosmetics.defaultFor(species, subspecies).orElse(null);
        if (fallback == null) return null;
        Optional<ResourceLocation> custom = CustomSkinTextures.forPlayer(player);
        if (custom.isPresent()) return custom.get();
        SpeciesCosmetics.Entry chosen = SpeciesCosmetics.resolve(species, subspecies,
                player.getData(ModAttachments.SPECIES_COSMETICS)).orElse(fallback);
        return SpeciesCosmeticTextures.existingOr(chosen.texture(), fallback.texture());
    }

    private static ArmModel build(String geo, HumanoidArm side, Species species, ManokitVariant subspecies) {
        GeoModelParts parts = GeoModelLoader.load(Minecraft.getInstance().getResourceManager(),
                ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, geo));
        String boneName = armBone(parts, side, species, subspecies);
        ModelPart arm = boneName == null ? null : parts.part(boneName);
        if (arm == null) {
            return new ArmModel(null, List.of(), 0.0F, 0.0F, 0.0F, 0.0F);
        }
        List<ModelPart> bones = parts.parts().keySet().stream()
                .filter(name -> descendsFrom(parts, name, boneName))
                .map(parts::part)
                .toList();
        float twist = species == Species.MANOKIT
                ? (side == HumanoidArm.RIGHT ? -1.0F : 1.0F) * subspecies.firstPersonArmTwist() * Mth.DEG_TO_RAD
                : 0.0F;
        ArmModel unmeasured = new ArmModel(arm, bones, twist, 0.0F, 0.0F, 0.0F);
        straighten(unmeasured);
        Bounds bounds = new Bounds();
        arm.render(new PoseStack(), bounds, 0, OverlayTexture.NO_OVERLAY);
        bones.forEach(ModelPart::resetPose);
        if (!bounds.valid()) {
            return new ArmModel(null, List.of(), 0.0F, 0.0F, 0.0F, 0.0F);
        }
        return new ArmModel(arm, bones, twist, (bounds.minX + bounds.maxX) / 2.0F,
                (bounds.minZ + bounds.maxZ) / 2.0F, bounds.maxY);
    }

    @Nullable
    private static String armBone(GeoModelParts parts, HumanoidArm side, Species species, ManokitVariant subspecies) {
        if (species == Species.MANOKIT) {
            return side == HumanoidArm.RIGHT ? subspecies.rightArmBone() : subspecies.leftArmBone();
        }
        for (String name : side == HumanoidArm.RIGHT ? RIGHT_ARM_BONES : LEFT_ARM_BONES) {
            if (parts.part(name) != null) return name;
        }
        return null;
    }

    private static boolean descendsFrom(GeoModelParts parts, String name, String ancestor) {
        for (String current = name; current != null; current = parts.parents().get(current)) {
            if (current.equals(ancestor)) return true;
        }
        return false;
    }

    private static void straighten(ArmModel model) {
        for (ModelPart bone : model.bones()) {
            bone.setRotation(0.0F, 0.0F, 0.0F);
        }
        ModelPart arm = model.arm();
        arm.yRot = model.twist();
        arm.x = 0.0F;
        arm.y = 0.0F;
        arm.z = 0.0F;
    }

    public static void clearCache() {
        CACHE.clear();
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((ResourceManagerReloadListener) resourceManager -> {
            clearCache();
            SpeciesCosmeticTextures.clearCache();
        });
    }

    private static final class Bounds implements VertexConsumer {
        private float minX = Float.MAX_VALUE;
        private float minZ = Float.MAX_VALUE;
        private float maxX = -Float.MAX_VALUE;
        private float maxY = -Float.MAX_VALUE;
        private float maxZ = -Float.MAX_VALUE;

        private boolean valid() {
            return maxX >= minX;
        }

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            minX = Math.min(minX, x);
            minZ = Math.min(minZ, z);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            maxZ = Math.max(maxZ, z);
            return this;
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            return this;
        }

        @Override
        public VertexConsumer setUv(float u, float v) {
            return this;
        }

        @Override
        public VertexConsumer setUv1(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer setUv2(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer setNormal(float normalX, float normalY, float normalZ) {
            return this;
        }
    }
}
