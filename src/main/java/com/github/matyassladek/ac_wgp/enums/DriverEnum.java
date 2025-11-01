package com.github.matyassladek.ac_wgp.enums;

import com.github.matyassladek.ac_wgp.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DriverEnum {

    ANTOINE_LAVOIE(new Driver("Antoine", "Lavoie", Country.FRA)),
    ARTHUR_FERREIRA(new Driver("Arthur", "Ferreira", Country.BRA)),
    ARTHUR_HARDWICK(new Driver("Arthur", "Hardwick", Country.GBR)),

    BRENT_HARRIS(new Driver("Brent", "Harris", Country.AUS)),
    BRUNO_CAMPIONI(new Driver("Bruno", "Campioni", Country.ITA)),

    DAVID_STARK(new Driver("David", "Stark", Country.USA)),
    DIEGO_CABRERA(new Driver("Diego", "Cabrera", Country.COL)),

    EMILIO_CEVALLOS(new Driver("Emilio", "Cevallos", Country.ESP)),
    ETHAN_SIMON(new Driver("Ethan", "Simon", Country.BEL)),

    FELIPE_PINTO(new Driver("Felipe", "Pinto", Country.BRA)),
    FRANZ_HERMANN(new Driver("Franz", "Hermann", Country.DEU)),
    FREDERIC_GASCOIGNE(new Driver("Frederic", "Gascoigne", Country.CAN)),

    GERHARD_DRAGER(new Driver("Gerhard", "Drager", Country.DEU)),

    HAKARU_KOJIMA(new Driver("Hakaru", "Kojima", Country.JPN)),
    HIRO_IWATA(new Driver("Hiro", "Iwata", Country.JPN)),

    JACK_CAMPBELL(new Driver("Jack", "Campbell", Country.NZL)),
    JAIME_OLIVEIRA(new Driver("Jaime", "Oliveira", Country.PRT)),
    JAN_VALENTA(new Driver("Jan", "Valenta", Country.CZE)),
    JOERI_KAANDORP(new Driver("Joeri", "Kaandorp", Country.NLD)),
    JOZEF_KLOOSTERMAN(new Driver("Jozef", "Kloosterman", Country.NLD)),

    LUCAS_PEREYRA(new Driver("Lucas", "Pereyra", Country.ARG)),

    MARCOS_PERALTA(new Driver("Marcos", "Peralta", Country.MEX)),
    MIKKO_VANHALA(new Driver("Mikko", "Vanhala", Country.FIN)),

    NAVEEN_DAGGUBATI(new Driver("Naveen", "Daggubati", Country.IND)),
    NOAH_TANNER(new Driver("Noah", "Tanner", Country.GBR)),

    RICK_SWART(new Driver("Rick", "Swart", Country.ZAF)),
    ROMEO_MORBIDELLI(new Driver("Romeo", "Morbidelli", Country.ITA)),
    RUAIRI_FERRITER(new Driver("Ruairi", "Ferriter", Country.IRL)),

    SAMUEL_SCHMID(new Driver("Samuel", "Schmid", Country.AUT)),
    SONNY_KELLY(new Driver("Sonny", "Kelly", Country.AUS)),
    SEBASTIAN_DUDA(new Driver("Sebastian", "Duda", Country.POL));

    private final Driver driver;
}
