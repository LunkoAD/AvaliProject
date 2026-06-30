package com.lunkoashtail.avaliproject.client.event;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.custom.AvaliEntity;
import com.lunkoashtail.avaliproject.entity.custom.ExpieEntity;
import com.lunkoashtail.avaliproject.entity.custom.ProtogenEntity;
import com.lunkoashtail.avaliproject.entity.custom.SergalEntity;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.species.Species;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@EventBusSubscriber(modid = AvaliProject.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class SpeciesRenderHandler {

    private static ExpieEntity expieDummy = null;
    private static AvaliEntity avaliDummy = null;
    private static SergalEntity sergalDummy = null;
    private static ProtogenEntity protogenDummy = null;

    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        if (player != Minecraft.getInstance().player) return;

        Species species = player.getData(ModAttachments.SPECIES);
        if (species == Species.HUMAN) return;

        event.setCanceled(true);

        LivingEntity dummy = getDummy(species, player);
        if (dummy == null) return;
        syncState(player, dummy);
        renderDummy(dummy, player, event);
    }

    private static LivingEntity getDummy(Species species, Player player) {
        return switch (species) {
            case EXPIE -> {
                if (expieDummy == null) expieDummy = new ExpieEntity(ModEntities.EXPIE.get(), player.level());
                yield expieDummy;
            }
            case AVALI -> {
                if (avaliDummy == null) avaliDummy = new AvaliEntity(ModEntities.AVALI.get(), player.level());
                yield avaliDummy;
            }
            case SERGAL -> {
                if (sergalDummy == null) sergalDummy = new SergalEntity(ModEntities.SERGAL.get(), player.level());
                yield sergalDummy;
            }
            case PROTOGEN -> {
                if (protogenDummy == null) protogenDummy = new ProtogenEntity(ModEntities.PROTOGEN.get(), player.level());
                yield protogenDummy;
            }
            default -> null;
        };
    }

    private static void syncState(Player player, LivingEntity dummy) {
        // moveTo sets xRotO = xRot and yRotO = yRot, preventing jitter from 0→value interpolation
        dummy.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
        dummy.yHeadRot = player.yHeadRot;
        dummy.yHeadRotO = player.yHeadRotO;
        dummy.yBodyRot = player.yBodyRot;
        dummy.yBodyRotO = player.yBodyRotO;
        dummy.tickCount = player.tickCount;
        dummy.walkDist = player.walkDist;
        dummy.walkDistO = player.walkDistO;
        dummy.setPose(player.getPose());
        dummy.setSprinting(player.isSprinting());
        dummy.setDeltaMovement(player.getDeltaMovement());
        dummy.setSwimming(player.isSwimming());
        // Copy walk speed so event.isMoving() / getLimbSwingAmount() work correctly
        dummy.walkAnimation.update(player.walkAnimation.speed(), 1.0f);
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
