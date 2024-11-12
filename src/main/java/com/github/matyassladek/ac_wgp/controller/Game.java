package com.github.matyassladek.ac_wgp.controller;

import com.github.matyassladek.ac_wgp.model.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.ArrayList;
import java.util.List;

@NonNullByDefault
@Setter
@Getter
@ToString
public class Game {

    private final Track[][] allSeasons;
    private final Driver player;
    private final List<Team> teams;
    private Championship currentChampionship;
    private int currentSeason = 0;

    public Game(String playerFirstName, String playerLastName, Country playerCountry, Manufacture playerTeam) {
        this.allSeasons = allSeasonsInit();
        this.player = new Driver(playerFirstName, playerLastName, playerCountry);
        this.teams = playerInit(player, playerTeam);
        this.currentChampionship = championshipInit();
    }

    private Championship championshipInit() {
        Championship championship = new Championship(allSeasons[currentSeason]);
        championship.setDriversStandings(driverStandingsInit(teams));
        championship.setConstructorsStandings(constructorsStandingsInit(teams));
        return championship;
    }

    private List<Team> playerInit(Driver player, Manufacture playerTeam) {
        List<Team> teams = teamsInit();
        for (Team team : teams) {
            if (team.getManufacture().equals(playerTeam)) {
                team.setDriver2(player);
                return teams;
            }
        }
        return teams;
    }

    private List<Championship.DriverSlot> driverStandingsInit(List<Team> teams) {
        List<Championship.DriverSlot> driverStandings = new ArrayList<>(12);
        for (Team team : teams) {
            driverStandings.add(new Championship.DriverSlot(team.getDriver1(), team));
            driverStandings.add(new Championship.DriverSlot(team.getDriver2(), team));
        }
        return driverStandings;
    }

    private List<Championship.TeamSlot> constructorsStandingsInit(List<Team> teams) {
        List<Championship.TeamSlot> constructorsStandings = new ArrayList<>(12);
        for (Team team : teams) {
            constructorsStandings.add(new Championship.TeamSlot(team));
        }
        return constructorsStandings;
    }

    private List<Team> teamsInit() {
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

    private Track[][] allSeasonsInit() {
        return new Track[][] {
            Calendar.SEASON_1.getTracks(),
            Calendar.SEASON_2.getTracks(),
            Calendar.SEASON_3.getTracks(),
            Calendar.SEASON_4.getTracks(),
            Calendar.SEASON_5.getTracks(),
            Calendar.SEASON_6.getTracks(),
            Calendar.SEASON_7.getTracks(),
            Calendar.SEASON_8.getTracks(),
            Calendar.SEASON_9.getTracks(),
            Calendar.SEASON_10.getTracks(),
            Calendar.SEASON_11.getTracks(),
            Calendar.SEASON_12.getTracks(),
            Calendar.SEASON_13.getTracks(),
            Calendar.SEASON_14.getTracks(),
            Calendar.SEASON_15.getTracks(),
            Calendar.SEASON_16.getTracks()
        };
    }
}
