package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.model.Country;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.model.Manufacture;
import com.github.matyassladek.ac_wgp.model.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamFactory {

    private List<Team> teams = new ArrayList<>();
    private int garage = 0;

    public List<Team> crateTeamList(Driver player, Manufacture playerTeam) {

        basicTeamList();
        for (Team team : this.teams) {
            if (team.getManufacture().equals(playerTeam)) {
                team.setDriver2(player);
                return this.teams;
            }
        }
        return this.teams;
    }

    private void basicTeamList() {

        this.teams.add(new Team(Manufacture.FERRARI, ++garage,
                new Driver("Romeo", "Morbidelli", Country.ITA),
                new Driver("Ethan", "Simon", Country.BEL)));

        this.teams.add(new Team(Manufacture.MCLAREN, ++garage,
                new Driver("Brent", "Harris", Country.AUS),
                new Driver("Arthur", "Hardwick", Country.GBR)));

        this.teams.add(new Team(Manufacture.RENAULT, ++garage,
                new Driver("Emilio", "Cevallos", Country.ESP),
                new Driver("Antoine", "Lavoie", Country.FRA)));

        this.teams.add(new Team(Manufacture.MERCEDES, ++garage,
                new Driver("Gerhard", "Drager",Country.DEU),
                new Driver("Noah", "Tanner", Country.GBR)));

        this.teams.add(new Team(Manufacture.LOTUS, ++garage,
                new Driver("Jack", "Campbell", Country.NZL),
                new Driver("Arthur", "Ferreira", Country.BRA)));

        this.teams.add(new Team(Manufacture.HONDA, ++garage,
                new Driver("David", "Stark", Country.USA),
                new Driver("Jozef", "Kloosterman", Country.NLD)));

        this.teams.add(new Team(Manufacture.ALFA_ROMEO, ++garage,
                new Driver("Bruno", "Campioni", Country.ITA),
                new Driver("Mikko", "Vanhala", Country.FIN)));

        this.teams.add(new Team(Manufacture.CHEVROLET, ++garage,
                new Driver("Felipe", "Pinto", Country.BRA),
                new Driver("Naveen", "Daggubati", Country.IND)));

        this.teams.add(new Team(Manufacture.JAGUAR, ++garage,
                new Driver("Ruairi", "Ferriter", Country.IRL),
                new Driver("Rick", "Swart", Country.ZAF)));

        this.teams.add(new Team(Manufacture.PORSCHE, ++garage,
                new Driver("Lucas", "Pereyra", Country.ARG),
                new Driver("Jaime", "Oliveira", Country.PRT)));

        this.teams.add(new Team(Manufacture.TOYOTA, ++garage,
                new Driver("Hiro", "Iwata", Country.JPN),
                new Driver("Marcos", "Peralta", Country.MEX)));

        this.teams.add(new Team(Manufacture.BMW, ++garage,
                new Driver("Sebastian", "Duda", Country.POL),
                new Driver("Samuel", "Schmid", Country.AUT)));
    }
}
