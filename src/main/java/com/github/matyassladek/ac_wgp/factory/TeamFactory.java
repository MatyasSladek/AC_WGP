package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.model.Country;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.model.Manufacture;
import com.github.matyassladek.ac_wgp.model.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamFactory {

    public static List<Team> teamsInit() {
        List<Team> teams = new ArrayList<>();

        teams.add(new Team(Manufacture.FERRARI, 1,
                new Driver("Romeo", "Morbidelli", Country.ITA),
                new Driver("Ethan", "Simon", Country.BEL)));

        teams.add(new Team(Manufacture.MCLAREN, 2,
                new Driver("Brent", "Harris", Country.AUS),
                new Driver("Arthur", "Hardwick", Country.GBR)));

        teams.add(new Team(Manufacture.RENAULT, 3,
                new Driver("Emilio", "Cevallos", Country.ESP),
                new Driver("Antoine", "Lavoie", Country.FRA)));

        teams.add(new Team(Manufacture.MERCEDES, 4,
                new Driver("Gerhard", "Drager",Country.DEU),
                new Driver("Noah", "Tanner", Country.GBR)));

        teams.add(new Team(Manufacture.LOTUS, 5,
                new Driver("Jack", "Campbell", Country.NZL),
                new Driver("Arthur", "Ferreira", Country.BRA)));

        teams.add(new Team(Manufacture.HONDA, 6,
                new Driver("David", "Stark", Country.USA),
                new Driver("Jozef", "Kloosterman", Country.NLD)));

        teams.add(new Team(Manufacture.ALFA_ROMEO, 7,
                new Driver("Bruno", "Campioni", Country.ITA),
                new Driver("Mikko", "Vanhala", Country.FIN)));

        teams.add(new Team(Manufacture.CHEVROLET, 8,
                new Driver("Felipe", "Pinto", Country.BRA),
                new Driver("Naveen", "Daggubati", Country.IND)));

        teams.add(new Team(Manufacture.JAGUAR, 9,
                new Driver("Ruairi", "Ferriter", Country.IRL),
                new Driver("Rick", "Swart", Country.ZAF)));

        teams.add(new Team(Manufacture.PORSCHE, 10,
                new Driver("Lucas", "Pereyra", Country.ARG),
                new Driver("Jaime", "Oliveira", Country.PRT)));

        teams.add(new Team(Manufacture.TOYOTA, 11,
                new Driver("Hiro", "Iwata", Country.JPN),
                new Driver("Marcos", "Peralta", Country.MEX)));

        teams.add(new Team(Manufacture.BMW, 12,
                new Driver("Sebastian", "Duda", Country.POL),
                new Driver("Samuel", "Schmid", Country.AUT)));

        return teams;
    }
}
