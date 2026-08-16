package com.lunkoashtail.avaliproject.client;

import com.lunkoashtail.avaliproject.component.SyringeContents;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.network.AvaliRecruitProposalPayload;
import com.lunkoashtail.avaliproject.network.AvaliTrustSyncPayload;
import com.lunkoashtail.avaliproject.network.CarryCandidatesSyncPayload;
import com.lunkoashtail.avaliproject.network.CarryConsentRequestPayload;
import com.lunkoashtail.avaliproject.network.DressingDepletedPayload;
import com.lunkoashtail.avaliproject.network.PackDataSyncPayload;
import com.lunkoashtail.avaliproject.screen.custom.AvaliInteractionScreen;
import com.lunkoashtail.avaliproject.screen.custom.AvaliRecruitProposalScreen;
import com.lunkoashtail.avaliproject.screen.custom.BloodDrawScreen;
import com.lunkoashtail.avaliproject.screen.custom.CarryConsentScreen;
import com.lunkoashtail.avaliproject.screen.custom.CarrySelectionScreen;
import com.lunkoashtail.avaliproject.screen.custom.DressingMinigameScreen;
import com.lunkoashtail.avaliproject.screen.custom.LimbSelectionScreen;
import com.lunkoashtail.avaliproject.screen.custom.PackScreen;
import com.lunkoashtail.avaliproject.screen.custom.SyringeDrawScreen;
import com.lunkoashtail.avaliproject.screen.custom.SyringeMinigameScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ClientPayloadHandlers {

    private ClientPayloadHandlers() {}

    public static void handleDressingDepleted(DressingDepletedPayload payload) {
        if (Minecraft.getInstance().screen instanceof DressingMinigameScreen screen) {
            screen.onDressingDepleted();
        }
    }

    public static void handleTrustSync(AvaliTrustSyncPayload payload) {
        AvaliInteractionScreen.onTrustSync(payload);
    }

    public static void handlePackDataSync(PackDataSyncPayload payload) {
        PackScreen.onDataSync(payload);
    }

    public static void handleCarryCandidatesSync(CarryCandidatesSyncPayload payload) {
        Minecraft.getInstance().setScreen(new CarrySelectionScreen(payload.candidates()));
    }

    public static void handleCarryConsentRequest(CarryConsentRequestPayload payload) {
        Minecraft.getInstance().setScreen(new CarryConsentScreen(payload.requesterEntityId()));
    }

    public static void handleRecruitProposal(AvaliRecruitProposalPayload payload) {
        Minecraft.getInstance().setScreen(new AvaliRecruitProposalScreen(payload.entityId()));
    }

    public static void openExpieInteractionScreen(int entityId) {
        Minecraft.getInstance().setScreen(new com.lunkoashtail.avaliproject.screen.custom.ExpieInteractionScreen(entityId));
    }

    public static void openAvaliInteractionScreen(int entityId, boolean tame, boolean ownedByPlayer) {
        Minecraft.getInstance().setScreen(new AvaliInteractionScreen(entityId, tame, ownedByPlayer));
    }

    public static void openDressingLimbSelection(Player player, InteractionHand hand) {
        openDressingLimbSelection(player, hand, player.getId());
    }

    public static void openDressingLimbSelection(Player player, InteractionHand hand, int targetEntityId) {
        Minecraft.getInstance().setScreen(new LimbSelectionScreen(selectedLimb -> {
            int bleed = (targetEntityId == player.getId())
                    ? player.getData(ModAttachments.LIMB_DATA).getBleed(selectedLimb)
                    : TargetDataCache.getBleed(targetEntityId, selectedLimb);
            Minecraft.getInstance().setScreen(new DressingMinigameScreen(selectedLimb, bleed, hand, targetEntityId));
        }, targetEntityId));
    }

    public static void openBloodDrawScreen() {
        Minecraft.getInstance().setScreen(new BloodDrawScreen());
    }

    public static void openSyringeDrawScreen(InteractionHand hand) {
        Minecraft.getInstance().setScreen(new SyringeDrawScreen(hand));
    }

    public static void openSyringeLimbSelection(SyringeContents contents, InteractionHand hand) {
        Player player = Minecraft.getInstance().player;
        openSyringeLimbSelection(contents, hand, player != null ? player.getId() : -1);
    }

    public static void openSyringeLimbSelection(SyringeContents contents, InteractionHand hand, int targetEntityId) {
        Minecraft.getInstance().setScreen(new LimbSelectionScreen(selectedLimb ->
                Minecraft.getInstance().setScreen(
                        new SyringeMinigameScreen(contents.drugType(), contents.dosage(), selectedLimb, hand, targetEntityId)),
                targetEntityId));
    }
}