package com.lunkoashtail.avaliproject.entity.client.geo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import org.slf4j.Logger;

import java.io.Reader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class GeoModelLoader {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final float GROUND_Y = 24.0F;
    private static final Direction[] VANILLA_FACE_ORDER = {
            Direction.DOWN, Direction.UP, Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH};

    private GeoModelLoader() {
    }

    public static GeoModelParts load(ResourceManager resourceManager, ResourceLocation location) {
        try (Reader reader = resourceManager.openAsReader(location)) {
            JsonObject geometry = JsonParser.parseReader(reader).getAsJsonObject()
                    .getAsJsonArray("minecraft:geometry").get(0).getAsJsonObject();
            return new GeoModelLoader.Builder(geometry).build();
        } catch (Exception e) {
            LOGGER.error("Could not load geo model {}; rendering nothing for it", location, e);
            return GeoModelParts.empty();
        }
    }

    private static final class Builder {
        private final float textureWidth;
        private final float textureHeight;
        private final Map<String, JsonObject> bones = new LinkedHashMap<>();
        private final Map<String, List<String>> children = new HashMap<>();
        private final List<String> topLevel = new ArrayList<>();
        private final Map<String, ModelPart> parts = new HashMap<>();
        private final Map<String, String> parents = new HashMap<>();
        private final Map<String, float[]> pivots = new HashMap<>();

        private Builder(JsonObject geometry) {
            JsonObject description = geometry.getAsJsonObject("description");
            this.textureWidth = description.get("texture_width").getAsFloat();
            this.textureHeight = description.get("texture_height").getAsFloat();
            for (JsonElement element : geometry.getAsJsonArray("bones")) {
                JsonObject bone = element.getAsJsonObject();
                bones.put(bone.get("name").getAsString(), bone);
            }
            for (Map.Entry<String, JsonObject> entry : bones.entrySet()) {
                String parent = entry.getValue().has("parent") ? entry.getValue().get("parent").getAsString() : null;
                if (parent != null && bones.containsKey(parent)) {
                    children.computeIfAbsent(parent, key -> new ArrayList<>()).add(entry.getKey());
                    parents.put(entry.getKey(), parent);
                } else {
                    topLevel.add(entry.getKey());
                }
            }
        }

        private GeoModelParts build() {
            Map<String, ModelPart> rootChildren = new LinkedHashMap<>();
            for (String name : topLevel) {
                rootChildren.put(name, buildBone(name, null));
            }
            ModelPart root = new ModelPart(List.of(), rootChildren);
            return new GeoModelParts(root, parts, parents, pivots);
        }

        private ModelPart buildBone(String name, float[] parentPivot) {
            JsonObject bone = bones.get(name);
            float[] geoPivot = vec(bone, "pivot");
            float[] pivot = toModelSpace(geoPivot);
            pivots.put(name, geoPivot);
            float[] rotation = radians(vec(bone, "rotation"));
            float boneInflate = bone.has("inflate") ? bone.get("inflate").getAsFloat() : 0.0F;

            List<ModelPart.Cube> cubes = new ArrayList<>();
            Map<String, ModelPart> partChildren = new LinkedHashMap<>();
            JsonArray cubeArray = bone.has("cubes") ? bone.getAsJsonArray("cubes") : new JsonArray();
            for (int i = 0; i < cubeArray.size(); i++) {
                JsonObject cube = cubeArray.get(i).getAsJsonObject();
                float[] cubeRotation = radians(vec(cube, "rotation"));
                float inflate = cube.has("inflate") ? cube.get("inflate").getAsFloat() : boneInflate;
                if (isZero(cubeRotation)) {
                    cubes.add(buildCube(cube, pivot, inflate));
                } else {
                    float[] cubePivot = toModelSpace(vec(cube, "pivot"));
                    ModelPart rotated = new ModelPart(List.of(buildCube(cube, cubePivot, inflate)), Map.of());
                    pose(rotated, subtract(cubePivot, pivot), cubeRotation);
                    partChildren.put(name + "#" + i, rotated);
                }
            }
            for (String child : children.getOrDefault(name, List.of())) {
                partChildren.put(child, buildBone(child, pivot));
            }

            ModelPart part = new ModelPart(cubes, partChildren);
            pose(part, parentPivot == null ? pivot : subtract(pivot, parentPivot), rotation);
            parts.put(name, part);
            return part;
        }

        private ModelPart.Cube buildCube(JsonObject cube, float[] framePivot, float inflate) {
            float[] origin = vec(cube, "origin");
            float[] size = vec(cube, "size");
            float minX = origin[0] - framePivot[0];
            float minY = GROUND_Y - origin[1] - size[1] - framePivot[1];
            float minZ = origin[2] - framePivot[2];

            Map<Direction, float[]> faces = faceUvs(cube, size);
            ModelPart.Cube built = new GeoCube(minX, minY, minZ, size[0], size[1], size[2],
                    inflate, textureWidth, textureHeight,
                    faces.isEmpty() ? EnumSet.noneOf(Direction.class) : EnumSet.copyOf(faces.keySet()));
            int index = 0;
            for (Direction direction : VANILLA_FACE_ORDER) {
                float[] uv = faces.get(direction);
                if (uv == null) continue;
                ModelPart.Polygon original = built.polygons[index];
                ModelPart.Polygon polygon = new ModelPart.Polygon(original.vertices.clone(),
                        uv[0], uv[1], uv[0] + uv[2], uv[1] + uv[3], textureWidth, textureHeight, false, direction);
                if (uv.length > 4) rotateUv(polygon, uv[4]);
                built.polygons[index] = polygon;
                index++;
            }
            return built;
        }

        private static void rotateUv(ModelPart.Polygon polygon, float degrees) {
            int steps = Math.floorMod(Math.round(degrees / 90.0F), 4);
            if (steps == 0) return;
            ModelPart.Vertex[] vertices = polygon.vertices;
            int count = vertices.length;
            float[] us = new float[count];
            float[] vs = new float[count];
            for (int i = 0; i < count; i++) {
                us[i] = vertices[i].u;
                vs[i] = vertices[i].v;
            }
            for (int i = 0; i < count; i++) {
                int from = Math.floorMod(i - steps, count);
                vertices[i] = vertices[i].remap(us[from], vs[from]);
            }
        }

        private Map<Direction, float[]> faceUvs(JsonObject cube, float[] size) {
            Map<Direction, float[]> faces = new EnumMap<>(Direction.class);
            if (!cube.has("uv")) {
                return faces;
            }
            JsonElement uv = cube.get("uv");
            if (uv.isJsonArray()) {
                float u = uv.getAsJsonArray().get(0).getAsFloat();
                float v = uv.getAsJsonArray().get(1).getAsFloat();
                float x = Mth.floor(size[0]);
                float y = Mth.floor(size[1]);
                float z = Mth.floor(size[2]);
                faces.put(Direction.EAST, new float[]{u + z + x, v + z, z, y});
                faces.put(Direction.WEST, new float[]{u, v + z, z, y});
                faces.put(Direction.NORTH, new float[]{u + z, v + z, x, y});
                faces.put(Direction.SOUTH, new float[]{u + z + x + z, v + z, x, y});
                faces.put(Direction.DOWN, new float[]{u + z, v, x, z});
                faces.put(Direction.UP, new float[]{u + z + x, v + z, x, -z});
                return faces;
            }
            JsonObject faceObject = uv.getAsJsonObject();
            putFace(faces, faceObject, "west", Direction.EAST);
            putFace(faces, faceObject, "east", Direction.WEST);
            putFace(faces, faceObject, "north", Direction.NORTH);
            putFace(faces, faceObject, "south", Direction.SOUTH);
            putFace(faces, faceObject, "up", Direction.DOWN);
            putFace(faces, faceObject, "down", Direction.UP);
            return faces;
        }

        private static void putFace(Map<Direction, float[]> faces, JsonObject faceObject, String geoFace, Direction modelFace) {
            if (!faceObject.has(geoFace)) return;
            JsonObject face = faceObject.getAsJsonObject(geoFace);
            float[] uv = vec2(face, "uv");
            float[] uvSize = vec2(face, "uv_size");
            float rotation = face.has("uv_rotation") ? face.get("uv_rotation").getAsFloat() : 0.0F;
            faces.put(modelFace, new float[]{uv[0], uv[1], uvSize[0], uvSize[1], rotation});
        }

        private static void pose(ModelPart part, float[] offset, float[] rotation) {
            PartPose pose = PartPose.offsetAndRotation(offset[0], offset[1], offset[2], rotation[0], rotation[1], rotation[2]);
            part.setInitialPose(pose);
            part.loadPose(pose);
        }

        private static float[] toModelSpace(float[] geo) {
            return new float[]{geo[0], GROUND_Y - geo[1], geo[2]};
        }

        private static float[] subtract(float[] a, float[] b) {
            return new float[]{a[0] - b[0], a[1] - b[1], a[2] - b[2]};
        }

        private static float[] radians(float[] degrees) {
            return new float[]{degrees[0] * Mth.DEG_TO_RAD, degrees[1] * Mth.DEG_TO_RAD, degrees[2] * Mth.DEG_TO_RAD};
        }

        private static boolean isZero(float[] values) {
            return values[0] == 0.0F && values[1] == 0.0F && values[2] == 0.0F;
        }

        private static float[] vec(JsonObject object, String key) {
            if (!object.has(key)) return new float[3];
            JsonArray array = object.getAsJsonArray(key);
            return new float[]{array.get(0).getAsFloat(), array.get(1).getAsFloat(), array.get(2).getAsFloat()};
        }

        private static float[] vec2(JsonObject object, String key) {
            if (!object.has(key)) return new float[2];
            JsonArray array = object.getAsJsonArray(key);
            return new float[]{array.get(0).getAsFloat(), array.get(1).getAsFloat()};
        }
    }
}
