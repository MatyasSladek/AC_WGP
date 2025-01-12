package com.github.matyassladek.ac_wgp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Manufacture {

    // Car manufactures
    FERRARI("Scuderia Ferrari", "Ferrari", Country.ITA),
    MCLAREN("Gulf McLaren GP Team", "McLaren", Country.GBR),
    RENAULT("NewMan Team Renault Elf", "Renault", Country.FRA),
    MERCEDES("Mercedes AMG Petronas", "Mercedes", Country.DEU),
    LOTUS("Team Lotus JPS", "Lotus", Country.GBR),
    HONDA("Red Bull Honda Racing", "Honda", Country.JPN),
    ALFA_ROMEO("Aitalia Alfa Romeo", "Alfa Romeo", Country.ITA),
    CHEVROLET("Chevrolet", "Chevrolet", Country.USA),
    JAGUAR("Castrol Jaguar Racing", "Jaguar", Country.GBR),
    PORSCHE("Warsteiner Porsche", "Porsche", Country.DEU),
    TOYOTA("Jägermeister Toyota Racing", "Toyota", Country.JPN),
    BMW("BMW Martini Racing", "BMW", Country.DEU),

    // Brands
    BWT("BWT Racing", "BWT", Country.AUT),
    CASTROL("Castrol Racing", "Castrol", Country.GBR),
    EM("Edl Motorsport", "EM", Country.CZE),
    FALKEN("Falken Tyres Racing", "Falken", Country.JPN),
    GULF("Gulf Oil Motorsports", "Gulf", Country.USA),
    JPS("Team John Player Special", "JPS", Country.GBR),
    MARTINI("Martini Racing", "Martini", Country.ITA),
    MOBIL1("Mobil 1 Racing", "Mobil 1", Country.USA),
    RBR("Oracle Red Bull Racing", "Red Bull", Country.AUT),
    TARGET("Target Racing", "Target", Country.USA),
    VODAFONE("Vodafone Racing", "Vodafone", Country.GBR);

    private final String nameLong;
    private final String nameShort;
    private final Country country;
}

