package com.lunkoashtail.avaliproject.limb;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.diplomacy.DiplomacyData;
import com.lunkoashtail.avaliproject.entity.client.ManokitVariant;
import com.lunkoashtail.avaliproject.entity.ExpieMoodData;
import com.lunkoashtail.avaliproject.pack.AugmentInventoryData;
import com.lunkoashtail.avaliproject.pack.AvaliTrustMemory;
import com.lunkoashtail.avaliproject.pack.PackMembership;
import com.lunkoashtail.avaliproject.pack.PackRankData;
import com.lunkoashtail.avaliproject.species.CustomSkins;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.species.SpeciesCosmeticData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;














public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AvaliProject.MOD_ID);

    



    public static final Supplier<AttachmentType<LimbData>> LIMB_DATA = ATTACHMENT_TYPES.register(
            "limb_data",
            () -> AttachmentType.<LimbData>builder(LimbData::new)
                    .serialize(LimbData.CODEC)
                    .build()
    );

    public static final Supplier<AttachmentType<Species>> SPECIES = ATTACHMENT_TYPES.register(
            "species",
            () -> AttachmentType.<Species>builder(() -> Species.HUMAN)
                    .serialize(Species.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<ManokitVariant>> MANOKIT_SUBSPECIES = ATTACHMENT_TYPES.register(
            "manokit_subspecies",
            () -> AttachmentType.<ManokitVariant>builder(() -> ManokitVariant.DEFAULT)
                    .serialize(ManokitVariant.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<SpeciesCosmeticData>> SPECIES_COSMETICS = ATTACHMENT_TYPES.register(
            "species_cosmetics",
            () -> AttachmentType.<SpeciesCosmeticData>builder(() -> SpeciesCosmeticData.EMPTY)
                    .serialize(SpeciesCosmeticData.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<CustomSkins.Data>> CUSTOM_SKINS = ATTACHMENT_TYPES.register(
            "custom_skins",
            () -> AttachmentType.<CustomSkins.Data>builder(() -> CustomSkins.Data.EMPTY)
                    .serialize(CustomSkins.Data.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<LimbConditions>> LIMB_CONDITIONS =ATTACHMENT_TYPES.register(
            "limb_conditions",
            () -> AttachmentType.<LimbConditions>builder(LimbConditions::new)
                    .serialize(LimbConditions.CODEC)
                    .build()
    );

    public static final Supplier<AttachmentType<AvaliTrustMemory>> AVALI_TRUST = ATTACHMENT_TYPES.register(
            "avali_trust",
            () -> AttachmentType.<AvaliTrustMemory>builder(AvaliTrustMemory::new)
                    .serialize(AvaliTrustMemory.CODEC)
                    .build()
    );

    public static final Supplier<AttachmentType<DiplomacyData>> DIPLOMACY_DATA = ATTACHMENT_TYPES.register(
            "diplomacy_data",
            () -> AttachmentType.<DiplomacyData>builder(() -> DiplomacyData.INITIAL)
                    .serialize(DiplomacyData.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<PackRankData>> PACK_RANK_DATA = ATTACHMENT_TYPES.register(
            "pack_rank_data",
            () -> AttachmentType.<PackRankData>builder(() -> PackRankData.INITIAL)
                    .serialize(PackRankData.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<PackMembership>> PACK_MEMBERSHIP = ATTACHMENT_TYPES.register(
            "pack_membership",
            () -> AttachmentType.<PackMembership>builder(() -> PackMembership.NONE)
                    .serialize(PackMembership.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<AugmentInventoryData>> AUGMENT_INVENTORY = ATTACHMENT_TYPES.register(
            "augment_inventory",
            () -> AttachmentType.<AugmentInventoryData>builder(() -> AugmentInventoryData.EMPTY)
                    .serialize(AugmentInventoryData.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<PainData>> PAIN_DATA = ATTACHMENT_TYPES.register(
            "pain_data",
            () -> AttachmentType.<PainData>builder(() -> new PainData())
                    .serialize(PainData.CODEC)
                    .build()
    );

    public static final Supplier<AttachmentType<ExpieMoodData>> EXPIE_MOOD = ATTACHMENT_TYPES.register(
            "expie_mood",
            () -> AttachmentType.<ExpieMoodData>builder(() -> new ExpieMoodData())
                    .serialize(ExpieMoodData.CODEC)
                    .build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
