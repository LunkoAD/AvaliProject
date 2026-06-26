package com.lunkoashtail.avaliproject.limb;

import net.minecraft.network.chat.Component;

/**
 * The six body parts that can sustain wounds.
 *
 * Ordinal values are used for network serialization — do NOT reorder or remove entries.
 * The key string is used for codec serialization and must remain stable across versions.
 *
 * Expandable: future entries (e.g. NECK, CHEST) can be appended at the end without
 * breaking existing save data.
 */
public enum Limb {
    HEAD      ("head",      "limb.avaliproject.head"),
    LEFT_ARM  ("left_arm",  "limb.avaliproject.left_arm"),
    RIGHT_ARM ("right_arm", "limb.avaliproject.right_arm"),
    BACK      ("back",      "limb.avaliproject.back"),
    LEFT_LEG  ("left_leg",  "limb.avaliproject.left_leg"),
    RIGHT_LEG ("right_leg", "limb.avaliproject.right_leg");

    /** Stable string key used in NBT/codec serialization. */
    public final String key;
    /** Translation key for display in GUIs. */
    public final String langKey;

    Limb(String key, String langKey) {
        this.key     = key;
        this.langKey = langKey;
    }

    public Component getDisplayName() {
        return Component.translatable(langKey);
    }

    /** Returns the Limb whose {@code key} matches, or null if unknown. */
    public static Limb fromKey(String key) {
        for (Limb l : values()) {
            if (l.key.equals(key)) return l;
        }
        return null;
    }
}
