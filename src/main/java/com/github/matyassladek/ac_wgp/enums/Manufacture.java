package com.github.matyassladek.ac_wgp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Manufacture {

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
    BMW("BMW Martini Racing", "BMW", Country.DEU);

    private final String nameLong;
    private final String nameShort;
    private final Country country;
}

