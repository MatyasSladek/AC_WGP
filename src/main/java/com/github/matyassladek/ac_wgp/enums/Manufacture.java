package com.github.matyassladek.ac_wgp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Manufacture {

    BWT("BWT Racing", "BWT", Country.AUT, "ferrari_f2002", "30_bwt", "31_bwt"),
    CASTROL("Castrol Racing", "Castrol", Country.GBR, "ferrari_f2002", "36_castrol", "37_castrol"),
    EM("Edl Motorsport", "EM", Country.CZE, "ferrari_f2002", "38_em", "39_em"),
    GULF("Gulf Oil Motorsports", "Gulf", Country.USA, "ferrari_f2002", "34_gulf", "35_gulf"),
    JPS("Team John Player Special", "JPS", Country.GBR, "ferrari_f2002", "26_jps", "27_jps"),
    MARTINI("Martini Racing", "Martini", Country.ITA, "ferrari_f2002", "40_martini", "41_martini"),
    NEWMAN("Newman Motorsport", "Newman", Country.USA, "ferrari_f2002", "28_newman", "29_newman"),
    RBR("Oracle Red Bull Racing", "Red Bull", Country.AUT, "ferrari_f2002", "32_rbr", "33_rbr"),
    TARGET("Target Racing", "Target", Country.USA, "ferrari_f2002", "46_target", "47_target"),
    VODAFONE("Vodafone Racing", "Vodafone", Country.GBR, "ferrari_f2002", "42_vodafone", "43_vodafone");

    private final String nameLong;
    private final String nameShort;
    private final Country country;
    private final String car;
    private final String skinDriver1;
    private final String skinDriver2;
}