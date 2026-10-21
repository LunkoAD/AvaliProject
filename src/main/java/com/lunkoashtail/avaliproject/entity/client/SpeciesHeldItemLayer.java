package com.lunkoashtail.avaliproject.entity.client;

import com.lunkoashtail.avaliproject.client.event.SpeciesRenderHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.cache.object.GeoQuad;
import software.bernie.geckolib.cache.object.GeoVertex;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import java.util.Map;
import java.util.WeakHashMap;

public class SpeciesHeldItemLayer<T extends LivingEntity & GeoAnimatable> extends BlockAndItemGeoLayer<T> {
    private static final String RIGHT_ARM = "rightarm";
    private static final String LEFT_ARM = "leftarm";
    private static final float ITEM_OUTWARD = 1.0F / 16.0F;
    private static final float ITEM_FORWARD = 0.125F;
    private static final float GRIP_ABOVE_ARM_END = 2.0F / 16.0F;

    private final Map<GeoBone, Float> gripHeights = new WeakHashMap<>();

    public SpeciesHeldItemLayer(GeoRenderer<T> renderer) {
        super(renderer);
    }

    private float gripHeight(GeoBone bone) {
        return gripHeights.computeIfAbsent(bone, key -> {
            float lowest = key.getPivotY() / 16.0F;
            for (GeoCube cube : key.getCubes()) {
                for (GeoQuad quad : cube.quads()) {
                    if (quad == null) continue;
                    for (GeoVertex vertex : quad.vertices()) {
                        lowest = Math.min(lowest, vertex.position().y());
                    }
                }
            }
            return lowest + GRIP_ABOVE_ARM_END;
        });
    }

    @Nullable
    private static HumanoidArm sideOf(GeoBone bone) {
        return switch (bone.getName()) {
            case RIGHT_ARM -> HumanoidArm.RIGHT;
            case LEFT_ARM -> HumanoidArm.LEFT;
            default -> null;
        };
    }

    @Nullable
    @Override
    protected ItemStack getStackForBone(GeoBone bone, T animatable) {
        HumanoidArm side = sideOf(bone);
        if (side == null) return null;
        Player owner = SpeciesRenderHandler.ownerOf(animatable);
        if (owner == null) return null;
        ItemStack stack = side == owner.getMainArm() ? owner.getMainHandItem() : owner.getOffhandItem();
        return stack.isEmpty() ? null : stack;
    }

    @Override
    protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
        return sideOf(bone) == HumanoidArm.LEFT ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    @Override
    protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, T animatable,
                                      MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
        boolean left = sideOf(bone) == HumanoidArm.LEFT;
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotation(-bone.getRotX()));
        poseStack.mulPose(Axis.YP.rotation(-bone.getRotY()));
        poseStack.mulPose(Axis.ZP.rotation(-bone.getRotZ()));
        poseStack.translate(0.0F, gripHeight(bone) - bone.getPivotY() / 16.0F, 0.0F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.translate(left ? -ITEM_OUTWARD : ITEM_OUTWARD, ITEM_FORWARD, 0.0F);
        Minecraft.getInstance().getItemRenderer().renderStatic(animatable, stack, getTransformTypeForStack(bone, stack, animatable),
                left, poseStack, bufferSource, animatable.level(), packedLight, packedOverlay, animatable.getId());
        poseStack.popPose();
    }
}
