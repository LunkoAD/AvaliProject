package com.lunkoashtail.avaliproject.pack;

import net.minecraft.resources.ResourceLocation;

import com.lunkoashtail.avaliproject.AvaliProject;

public enum PackRank {
    RANK_01_E1_RECRUIT("rank_01_e1_recruit", 0),
    RANK_02_E2_TROOP("rank_02_e2_troop", 150),
    RANK_03_E3_SPECIALIST("rank_03_e3_specialist", 350),
    RANK_04_E4_CORPORAL("rank_04_e4_corporal", 600),
    RANK_05_E5_PACK_SGT("rank_05_e5_pack_sgt", 900),
    RANK_06_E6_FLIGHT_SGT("rank_06_e6_flight_sgt", 1250),
    RANK_07_E7_LEAD_FLIGHT_SGT("rank_07_e7_lead_flight_sgt", 1650),
    RANK_08_E8_MASTER_SGT("rank_08_e8_master_sgt", 2100),
    RANK_09_E9_CLUSTER_SGT("rank_09_e9_cluster_sgt", 2600),
    RANK_10_BASIC_TOOP_PACK("rank_10_basic_toop_pack", 3150),
    RANK_11_PACK_FIRST_CLASS("rank_11_pack_first_class", 3750),
    RANK_12_SPECIALIST_PACK("rank_12_specialist_pack", 4400),
    RANK_13_TECHNICAL_PACK("rank_13_technical_pack", 5100),
    RANK_14_COMMAND_PACK("rank_14_command_pack", 5850),
    RANK_15_CW_2_A("rank_15_cw-2_a", 6650),
    RANK_16_CW_2_B("rank_16_cw-2_b", 7500),
    RANK_17_CW_3("rank_17_cw-3", 8400),
    RANK_18_SW_1("rank_18_sw-1", 9350),
    RANK_19_SW_2("rank_19_sw-2", 10350),
    RANK_20_SW_3("rank_20_sw-3", 11400),
    RANK_21_WD_1("rank_21_wd-1", 12500),
    RANK_22_WD_2("rank_22_wd-2", 13650),
    RANK_23_WD_3("rank_23_wd-3", 14850),
    RANK_24_BASIC_WARRANT_PACK("rank_24_basic_warrant_pack", 16100),
    RANK_25_WARRANT_PACK_FIRST_CLASS("rank_25_warrant_pack_first_class", 17400),
    RANK_26_WARRANT_SPECIALIST_PACK("rank_26_warrant_specialist_pack", 18750),
    RANK_27_WARRANT_TECHNICAL_PACK("rank_27_warrant_technical_pack", 20150),
    RANK_28_COMMAND_WARRANT_PACK("rank_28_command_warrant_pack", 21600),
    RANK_29_D1_FLETCH("rank_29_d-1_fletch", 23100),
    RANK_30_D2_LIEUTENANT_PACK_LEAD("rank_30_d-2_lieutenant_pack_lead", 24650),
    RANK_31_D3_CAPTAIN("rank_31_d-3_captain", 26250),
    RANK_32_D4_FEATHER_LEADER("rank_32_d-4_feather_leader", 27900),
    RANK_33_D5_MAJOR("rank_33_d-5_major", 29600),
    RANK_34_D6_CORONEL("rank_34_d-6_coronel", 31350),
    RANK_35_MARSHALL_COMMANDER("rank_35_marshall_commander", 33150),
    RANK_36_ADMIRAL("rank_36_admiral", 35000),
    RANK_37_FLEET_ADMIRAL("rank_37_fleet_admiral", 36900),
    RANK_38_BASIC_OFFICER_PACK("rank_38_basic_officer_pack", 38850),
    RANK_39_OFFICER_PACK_FIRST_CLASS("rank_39_officer_pack_first_class", 40850),
    RANK_40_SPECIALIST_OFFICER_PACK("rank_40_specialist_officer_pack", 42900),
    RANK_41_TECHNICAL_OFFICER_PACK("rank_41_technical_officer_pack", 45000),
    RANK_42_COMMAND_OFFICER_PACK("rank_42_command_officer_pack", 47150);

    private final String id;
    private final int xpThreshold;

    PackRank(String id, int xpThreshold) {
        this.id = id;
        this.xpThreshold = xpThreshold;
    }

    public String id() {
        return id;
    }

    public int xpThreshold() {
        return xpThreshold;
    }

    public int xpForNextRank() {
        PackRank[] values = values();
        int next = ordinal() + 1;
        return next < values.length ? values[next].xpThreshold : -1;
    }

    public ResourceLocation iconLocation() {
        return ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "textures/gui/rank/" + id + ".png");
    }

    public String displayName() {
        String[] parts = id.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < parts.length; i++) {
            if (i > 2) sb.append(' ');
            sb.append(Character.toUpperCase(parts[i].charAt(0))).append(parts[i].substring(1));
        }
        return sb.toString();
    }

    public static PackRank forXp(int xp) {
        PackRank result = RANK_01_E1_RECRUIT;
        for (PackRank rank : values()) {
            if (xp >= rank.xpThreshold)
                result = rank;
        }
        return result;
    }
}
