package com.lunkoashtail.avaliproject.client.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.client.CustomSkinTextures;
import com.lunkoashtail.avaliproject.client.SpeciesCosmeticTextures;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.lunkoashtail.avaliproject.entity.custom.CosmeticVariantTarget;
import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.entity.custom.AbstractManokitEntity;
import com.lunkoashtail.avaliproject.entity.custom.ArcaneManokitEntity;
import com.lunkoashtail.avaliproject.entity.custom.BeachdwellerManokitEntity;
import com.lunkoashtail.avaliproject.entity.custom.TribalManokitEntity;
import com.lunkoashtail.avaliproject.entity.custom.ProtogenEntity;
import com.lunkoashtail.avaliproject.entity.custom.SergalEntity;
import com.lunkoashtail.avaliproject.entity.custom.WickerbeastEntity;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesCosmetics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, value = Dist.CLIENT)
public class SpeciesRenderHandler {

    private record DummySlot(Species species, ManokitVariant subspecies, LivingEntity dummy) {
    }

    private static final Map<UUID, DummySlot> DUMMIES = new HashMap<>();
    private static final Map<Entity, Player> OWNERS = new WeakHashMap<>();
    @Nullable
    private static Level dummyLevel;

    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        Species species = player.getData(ModAttachments.SPECIES);
        if (species == Species.HUMAN) return;

        event.setCanceled(true);

        LivingEntity dummy = getDummy(species, player);
        if (dummy == null) return;
        syncState(player, dummy);
        applyCosmetic(dummy, species, player.getData(ModAttachments.MANOKIT_SUBSPECIES),
                SpeciesCosmetics.resolve(species, player.getData(ModAttachments.MANOKIT_SUBSPECIES),
                        player.getData(ModAttachments.SPECIES_COSMETICS)).orElse(null));
        CustomSkinTextures.forPlayer(player).ifPresent(texture -> SpeciesCosmeticTextures.set(dummy, texture));
        renderDummy(dummy, player, event);
    }

    private static LivingEntity getDummy(Species species, Player player) {
        if (dummyLevel != player.level()) {
            DUMMIES.clear();
            OWNERS.clear();
            dummyLevel = player.level();
        }
        ManokitVariant subspecies = player.getData(ModAttachments.MANOKIT_SUBSPECIES);
        DummySlot slot = DUMMIES.get(player.getUUID());
        if (slot == null || slot.species() != species || (species == Species.MANOKIT && slot.subspecies() != subspecies)) {
            LivingEntity dummy = createDummy(species, subspecies, player.level());
            if (dummy == null) return null;
            slot = new DummySlot(species, subspecies, dummy);
            DUMMIES.put(player.getUUID(), slot);
        }
        OWNERS.put(slot.dummy(), player);
        return slot.dummy();
    }

    public static void forget(UUID playerId) {
        DummySlot slot = DUMMIES.remove(playerId);
        if (slot != null) OWNERS.remove(slot.dummy());
    }

    @Nullable
    public static Player ownerOf(Entity dummy) {
        return OWNERS.get(dummy);
    }

    @Nullable
    public static LivingEntity createDummy(Species species, ManokitVariant subspecies, Level level) {
        return switch (species) {
            case EXPIE -> new ExpieEntity(ModEntities.EXPIE.get(), level);
            case AVALI -> new AvaliEntity(ModEntities.AVALI.get(), level);
            case SERGAL -> new SergalEntity(ModEntities.SERGAL.get(), level);
            case PROTOGEN -> new ProtogenEntity(ModEntities.PROTOGEN.get(), level);
            case WICKERBEAST -> new WickerbeastEntity(ModEntities.WICKERBEAST.get(), level);
            case MANOKIT -> createManokit(subspecies, level);
            default -> null;
        };
    }

    private static AbstractManokitEntity createManokit(ManokitVariant variant, Level level) {
        return switch (variant) {
            case BEACHDWELLER -> new BeachdwellerManokitEntity(ModEntities.BEACHDWELLER_MANOKIT.get(), level);
            case TRIBAL -> new TribalManokitEntity(ModEntities.TRIBAL_MANOKIT.get(), level);
            case ARCANE -> new ArcaneManokitEntity(ModEntities.ARCANE_MANOKIT.get(), level);
        };
    }

    public static void applyCosmetic(LivingEntity dummy, Species species, ManokitVariant subspecies,
                                     @Nullable SpeciesCosmetics.Entry entry) {
        SpeciesCosmetics.Entry fallback = SpeciesCosmetics.defaultFor(species, subspecies).orElse(null);
        if (entry == null || fallback == null) return;
        if (entry.variantId() != SpeciesCosmetics.NO_VARIANT && dummy instanceof CosmeticVariantTarget target) {
            target.setCosmeticVariant(entry.variantId());
        }
        SpeciesCosmeticTextures.set(dummy, SpeciesCosmeticTextures.existingOr(entry.texture(), fallback.texture()));
    }

    private static void syncState(Player player, LivingEntity dummy) {

        dummy.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
        dummy.xo = player.xo;
        dummy.yo = player.yo;
        dummy.zo = player.zo;
        dummy.xRotO = player.xRotO;
        dummy.yRotO = player.yRotO;
        dummy.yHeadRot = player.yHeadRot;
        dummy.yHeadRotO = player.yHeadRotO;
        dummy.yBodyRot = player.yBodyRot;
        dummy.yBodyRotO = player.yBodyRotO;
        dummy.tickCount = player.tickCount;
        dummy.walkDist = player.walkDist;
        dummy.walkDistO = player.walkDistO;
        dummy.setPose(player.getPose());
        dummy.setSprinting(player.isSprinting());
        dummy.setShiftKeyDown(player.isShiftKeyDown());
        dummy.setDeltaMovement(new Vec3(player.getX() - player.xo, player.getY() - player.yo, player.getZ() - player.zo));
        dummy.setSwimming(player.isSwimming());
        dummy.setOnGround(player.onGround());

        copyScale(player, dummy);
        copyWalkAnimation(player.walkAnimation, dummy.walkAnimation);
        dummy.attackAnim = player.attackAnim;
        dummy.oAttackAnim = player.oAttackAnim;
        dummy.swinging = player.swinging;
        dummy.swingingArm = player.swingingArm;
        dummy.swingTime = player.swingTime;
        if (dummy instanceof AbstractManokitEntity || dummy instanceof AvaliEntity) {
            Mob mob = (Mob) dummy;
            mob.setItemSlot(EquipmentSlot.MAINHAND, player.getMainHandItem());
            mob.setItemSlot(EquipmentSlot.OFFHAND, player.getOffhandItem());
            mob.setLeftHanded(player.getMainArm() == HumanoidArm.LEFT);
        }
        if (player != Minecraft.getInstance().player) {
            dummy.setCustomName(player.getDisplayName());
            dummy.setCustomNameVisible(true);
        }
    }

    private static void copyScale(Player player, LivingEntity dummy) {
        AttributeInstance scale = dummy.getAttribute(Attributes.SCALE);
        if (scale != null && scale.getBaseValue() != player.getScale()) {
            scale.setBaseValue(player.getScale());
        }
    }

    private static void copyWalkAnimation(WalkAnimationState from, WalkAnimationState to) {
        float speedOld = from.speed(0.0F);
        float speed = from.speed();
        to.update(from.position() - speed - to.position(), 1.0F);
        to.setSpeed(speedOld);
        to.update(speed, 1.0F);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void renderDummy(LivingEntity dummy, Player player, RenderPlayerEvent.Pre event) {
        var dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer renderer = dispatcher.getRenderer(dummy);
        if (renderer == null) return;
        float partialTick = event.getPartialTick();
        float entityYaw = Mth.rotLerp(partialTick, player.yBodyRotO, player.yBodyRot);
        renderer.render(dummy, entityYaw, partialTick,
                event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
    }
}
