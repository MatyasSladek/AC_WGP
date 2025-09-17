package com.github.matyassladek.ac_wgp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Manufacture {

    BWT("BWT Racing", "BWT", Country.AUT),
    CASTROL("Castrol Racing", "Castrol", Country.GBR),
    EM("Edl Motorsport", "EM", Country.CZE),
    GULF("Gulf Oil Motorsports", "Gulf", Country.USA),
    JPS("Team John Player Special", "JPS", Country.GBR),
    MARTINI("Martini Racing", "Martini", Country.ITA),
    NEWMAN("Newman Motorsport", "Newman", Country.USA),
    RBR("Oracle Red Bull Racing", "Red Bull", Country.AUT),
    TARGET("Target Racing", "Target", Country.USA),
    VODAFONE("Vodafone Racing", "Vodafone", Country.GBR);

    private final String nameLong;
    private final String nameShort;
    private final Country country;
}
