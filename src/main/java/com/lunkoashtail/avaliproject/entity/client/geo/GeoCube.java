package com.lunkoashtail.avaliproject.entity.client.geo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Set;

public class GeoCube extends ModelPart.Cube {
    public GeoCube(float minX, float minY, float minZ, float sizeX, float sizeY, float sizeZ, float inflate,
                   float textureWidth, float textureHeight, Set<Direction> visibleFaces) {
        super(0, 0, minX, minY, minZ, sizeX, sizeY, sizeZ, inflate, inflate, inflate, false,
                textureWidth, textureHeight, visibleFaces);
    }

    @Override
    public void compile(PoseStack.Pose pose, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        Matrix4f matrix = pose.pose();
        Vector3f scratch = new Vector3f();
        for (ModelPart.Polygon polygon : this.polygons) {
            Vector3f normal = pose.transformNormal(polygon.normal, scratch);
            float nx = normal.x();
            float ny = normal.y();
            float nz = normal.z();
            for (ModelPart.Vertex vertex : polygon.vertices) {
                Vector3f position = matrix.transformPosition(
                        vertex.pos.x() / 16.0F, vertex.pos.y() / 16.0F, vertex.pos.z() / 16.0F, scratch);
                buffer.addVertex(position.x(), position.y(), position.z(), color,
                        vertex.u, vertex.v, packedOverlay, packedLight, nx, ny, nz);
            }
        }
    }
}
