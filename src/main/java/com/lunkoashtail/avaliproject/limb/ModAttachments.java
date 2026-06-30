package com.lunkoashtail.avaliproject.limb;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.species.Species;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

/**
 * Registers NeoForge AttachmentTypes that persist data on player entities.
 *
 * LIMB_DATA:
 *   – Attached to both ServerPlayer (server) and LocalPlayer (client).
 *   – Serialized via LimbData.CODEC — survives world reloads and server restarts.
 *   – copyOnDeath() — bleed persists through death (player keeps wounds).
 *   – The client copy is kept in sync by LimbDataSyncPayload (S→C packet).
 *
 * To add new per-player attachment data in the future, register a new entry here
 * and call register(modEventBus) once in AvaliProject's constructor.
 */
public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AvaliProject.MOD_ID);

    /**
     * Per-limb bleeding tracker.  Default value is a freshly constructed LimbData
     * with all limbs at 0 bleed.
     */
    public static final Supplier<AttachmentType<LimbData>> LIMB_DATA = ATTACHMENT_TYPES.register(
            "limb_data",
            () -> AttachmentType.<LimbData>builder(LimbData::new)
                    .serialize(LimbData.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<Species>> SPECIES = ATTACHMENT_TYPES.register(
            "species",
            () -> AttachmentType.<Species>builder(() -> Species.HUMAN)
                    .serialize(Species.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
