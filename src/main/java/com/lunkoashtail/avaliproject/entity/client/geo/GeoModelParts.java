package com.lunkoashtail.avaliproject.entity.client.geo;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public record GeoModelParts(ModelPart root, Map<String, ModelPart> parts, Map<String, String> parents,
                            Map<String, float[]> geoPivots) {

    public static GeoModelParts empty() {
        return new GeoModelParts(new ModelPart(List.of(), Map.of()), Map.of(), Map.of(), Map.of());
    }

    @Nullable
    public ModelPart part(String name) {
        return parts.get(name);
    }

    public float geoPivotY(String name) {
        float[] pivot = geoPivots.get(name);
        return pivot == null ? 0.0F : pivot[1];
    }

    public void resetPose() {
        root.getAllParts().forEach(ModelPart::resetPose);
    }

    public boolean translateTo(String name, PoseStack poseStack) {
        if (!parts.containsKey(name)) return false;
        List<String> chain = new ArrayList<>();
        for (String current = name; current != null; current = parents.get(current)) {
            chain.add(current);
        }
        Collections.reverse(chain);
        root.translateAndRotate(poseStack);
        for (String bone : chain) {
            parts.get(bone).translateAndRotate(poseStack);
        }
        return true;
    }
}
