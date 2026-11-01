package com.lunkoashtail.avaliproject.avaligear.armor;

import net.minecraft.world.item.ArmorItem;

import java.util.List;

public final class AvaliArmorTable {
    private AvaliArmorTable() {
    }

    public record Row(String id, String model, String texture, ArmorItem.Type type, String material, boolean visor,
                      List<String> hide) {
    }

    public static final List<Row> ROWS = List.of(
            new Row("specialty_head", "specialty", "specialty", ArmorItem.Type.HELMET, "SPECIALTY", true, List.of("Feathers Crest Top", "Feathers Crest Back", "Feathers Crest Side")),
            new Row("specialty_chest", "specialty", "specialty", ArmorItem.Type.CHESTPLATE, "SPECIALTY", false, List.of("Feathers Wing Left", "Feathers Wing Right", "Feathers Chest")),
            new Row("specialty_legs", "specialty", "specialty", ArmorItem.Type.LEGGINGS, "SPECIALTY", false, List.of()),
            new Row("specialty_feet", "specialty", "specialty", ArmorItem.Type.BOOTS, "SPECIALTY", false, List.of()),
            new Row("hatchery_guard_head", "hatchery_guard", "hatchery_guard", ArmorItem.Type.HELMET, "PLATE", false, List.of("Feathers Crest Top", "Feathers Crest Back", "Feathers Crest Side")),
            new Row("hatchery_guard_chest", "hatchery_guard", "hatchery_guard", ArmorItem.Type.CHESTPLATE, "PLATE", false, List.of("Feathers Chest")),
            new Row("hatchery_guard_legs", "hatchery_guard", "hatchery_guard", ArmorItem.Type.LEGGINGS, "PLATE", false, List.of()),
            new Row("hatchery_guard_feet", "hatchery_guard", "hatchery_guard", ArmorItem.Type.BOOTS, "PLATE", false, List.of()),
            new Row("standard_issue_head", "standard_issue", "standard_issue", ArmorItem.Type.HELMET, "PLATE", false, List.of("Feathers Crest Top", "Feathers Crest Back", "Feathers Crest Side")),
            new Row("standard_issue_chest", "standard_issue", "standard_issue", ArmorItem.Type.CHESTPLATE, "PLATE", false, List.of("Feathers Chest")),
            new Row("standard_issue_legs", "standard_issue", "standard_issue", ArmorItem.Type.LEGGINGS, "PLATE", false, List.of()),
            new Row("standard_issue_feet", "standard_issue", "standard_issue", ArmorItem.Type.BOOTS, "PLATE", false, List.of()),
            new Row("hunter_garb_head", "hunter_garb", "hunter_garb", ArmorItem.Type.HELMET, "GARMENT", false, List.of()),
            new Row("hunter_garb_chest", "hunter_garb", "hunter_garb", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of("Feathers Chest")),
            new Row("hunter_garb_legs", "hunter_garb", "hunter_garb", ArmorItem.Type.LEGGINGS, "GARMENT", false, List.of()),
            new Row("hunter_garb_feet", "hunter_garb", "hunter_garb", ArmorItem.Type.BOOTS, "GARMENT", false, List.of()),
            new Row("tribal_wrap_head", "tribal_wrap", "tribal_wrap", ArmorItem.Type.HELMET, "GARMENT", false, List.of()),
            new Row("tribal_wrap_chest", "tribal_wrap", "tribal_wrap", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of("Feathers Chest")),
            new Row("tribal_wrap_legs", "tribal_wrap", "tribal_wrap", ArmorItem.Type.LEGGINGS, "GARMENT", false, List.of()),
            new Row("tribal_wrap_feet", "tribal_wrap", "tribal_wrap", ArmorItem.Type.BOOTS, "GARMENT", false, List.of()),
            new Row("ground_utility_head", "utility_uniform", "utility_uniform", ArmorItem.Type.HELMET, "UNIFORM", false, List.of()),
            new Row("ground_utility_chest", "utility_uniform", "utility_uniform", ArmorItem.Type.CHESTPLATE, "UNIFORM", false, List.of("Feathers Chest")),
            new Row("ground_utility_legs", "utility_uniform", "utility_uniform", ArmorItem.Type.LEGGINGS, "UNIFORM", false, List.of()),
            new Row("ground_utility_feet", "utility_uniform", "utility_uniform", ArmorItem.Type.BOOTS, "UNIFORM", false, List.of()),
            new Row("space_utility_head", "utility_uniform", "utility_uniform_space", ArmorItem.Type.HELMET, "UNIFORM", false, List.of()),
            new Row("space_utility_chest", "utility_uniform", "utility_uniform_space", ArmorItem.Type.CHESTPLATE, "UNIFORM", false, List.of("Feathers Chest")),
            new Row("space_utility_legs", "utility_uniform", "utility_uniform_space", ArmorItem.Type.LEGGINGS, "UNIFORM", false, List.of()),
            new Row("space_utility_feet", "utility_uniform", "utility_uniform_space", ArmorItem.Type.BOOTS, "UNIFORM", false, List.of()),
            new Row("naval_utility_head", "utility_uniform", "utility_uniform_naval", ArmorItem.Type.HELMET, "UNIFORM", false, List.of()),
            new Row("naval_utility_chest", "utility_uniform", "utility_uniform_naval", ArmorItem.Type.CHESTPLATE, "UNIFORM", false, List.of("Feathers Chest")),
            new Row("naval_utility_legs", "utility_uniform", "utility_uniform_naval", ArmorItem.Type.LEGGINGS, "UNIFORM", false, List.of()),
            new Row("naval_utility_feet", "utility_uniform", "utility_uniform_naval", ArmorItem.Type.BOOTS, "UNIFORM", false, List.of()),
            new Row("snow_cloak_head", "snow_cloak", "snow_cloak", ArmorItem.Type.HELMET, "UNIFORM", false, List.of("Feathers Crest Top", "Feathers Crest Back", "Feathers Crest Side")),
            new Row("snow_cloak_chest", "snow_cloak", "snow_cloak", ArmorItem.Type.CHESTPLATE, "UNIFORM", false, List.of("Feathers Wing Left", "Feathers Wing Right", "Feathers Chest")),
            new Row("snow_cloak_legs", "snow_cloak", "snow_cloak", ArmorItem.Type.LEGGINGS, "UNIFORM", false, List.of()),
            new Row("snow_cloak_feet", "snow_cloak", "snow_cloak", ArmorItem.Type.BOOTS, "UNIFORM", false, List.of()),
            new Row("grey_snow_cloak_head", "snow_cloak", "snow_cloak_grey", ArmorItem.Type.HELMET, "UNIFORM", false, List.of("Feathers Crest Top", "Feathers Crest Back", "Feathers Crest Side")),
            new Row("grey_snow_cloak_chest", "snow_cloak", "snow_cloak_grey", ArmorItem.Type.CHESTPLATE, "UNIFORM", false, List.of("Feathers Wing Left", "Feathers Wing Right", "Feathers Chest")),
            new Row("grey_snow_cloak_legs", "snow_cloak", "snow_cloak_grey", ArmorItem.Type.LEGGINGS, "UNIFORM", false, List.of()),
            new Row("grey_snow_cloak_feet", "snow_cloak", "snow_cloak_grey", ArmorItem.Type.BOOTS, "UNIFORM", false, List.of()),
            new Row("scarf_violet", "scarf_violet", "scarf_violet", ArmorItem.Type.HELMET, "GARMENT", false, List.of("Feathers Chest")),
            new Row("hood_black", "hood_black", "hood_black", ArmorItem.Type.HELMET, "GARMENT", false, List.of()),
            new Row("collar_ring", "collar_ring", "collar_ring", ArmorItem.Type.HELMET, "GARMENT", false, List.of()),
            new Row("vest_black", "vest_black", "vest_black", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of("Feathers Chest")),
            new Row("shirt_grey", "shirt_grey", "shirt_grey", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of("Feathers Chest")),
            new Row("poncho_orange", "poncho_orange", "poncho_orange", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of("Feathers Chest")),
            new Row("jacket_black", "jacket_black", "jacket_black", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of("Feathers Chest")),
            new Row("shawl_cream", "shawl_cream", "shawl_cream", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of("Feathers Chest")),
            new Row("tunic_orange", "tunic_orange", "tunic_orange", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of("Feathers Chest")),
            new Row("coat_orange", "coat_orange", "coat_orange", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of("Feathers Wing Left", "Feathers Wing Right", "Feathers Chest")),
            new Row("cloak_black", "cloak_black", "cloak_black", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of("Feathers Wing Left", "Feathers Wing Right")),
            new Row("sleeves_striped", "sleeves_striped", "sleeves_striped", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of()),
            new Row("armbands_grey", "armbands_grey", "armbands_grey", ArmorItem.Type.CHESTPLATE, "GARMENT", false, List.of()),
            new Row("pants_cream", "pants_cream", "pants_cream", ArmorItem.Type.LEGGINGS, "GARMENT", false, List.of()),
            new Row("pants_black", "pants_black", "pants_black", ArmorItem.Type.LEGGINGS, "GARMENT", false, List.of()),
            new Row("belt_pouches", "belt_pouches", "belt_pouches", ArmorItem.Type.LEGGINGS, "GARMENT", false, List.of()),
            new Row("wraps_black", "wraps_black", "wraps_black", ArmorItem.Type.BOOTS, "GARMENT", false, List.of()),
            new Row("boots_orange", "boots_orange", "boots_orange", ArmorItem.Type.BOOTS, "GARMENT", false, List.of()));
}
