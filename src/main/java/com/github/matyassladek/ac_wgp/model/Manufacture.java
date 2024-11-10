package com.github.matyassladek.ac_wgp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Manufacture {

    FERRARI("Scuderia Ferrari", "Ferrari", Country.ITA),
    MCLAREN("Gulf McLaren GP Team", "McLaren", Country.GBR),
    RENAULT("NewMan Team Renault Elf", "Renault", Country.FRA),
    MERCEDES("Mercedes AMG Petronas", "Mercedes", Country.GBR),
    LOTUS("Team Lotus JPS", "Lotus", Country.BRA),
    HONDA("Red Bull Honda Racing", "Honda", Country.NLD),
    ALFA_ROMEO("Aitalia Alfa Romeo", "Alfa Romeo", Country.FIN),
    CHEVROLET("Chevrolet", "Chevrolet", Country.BRA),
    JAGUAR("Castrol Jaguar Racing", "Jaguar", Country.ZAF),
    PORSCHE("Warsteiner Porsche", "Porsche", Country.PRT),
    TOYOTA("Jägermeister Toyota Racing", "Toyota", Country.MEX),
    BMW("BMW Martini Racing", "BMW", Country.AUT);

    private final String nameLong;
    private final String nameShort;
    private final Country country;
}

