package com.github.matyassladek.ac_wgp.ac_wgp.controller;

import com.github.matyassladek.ac_wgp.ac_wgp.model.*;
import lombok.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NonNullByDefault
public class Game {

    private int season = 0;
    private final Track[][] allSeasons = new Track[][]{
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
            Calendar.SEASON_16.getTracks()};

    private Championship gameInit() {
        Championship championship = new Championship(allSeasons[season]);
        List<Team> teams = teamsInit();
        //TODO: finish playerInit
        championship.setDriversStandings(driverStandingsInit(teams));
        championship.setConstructorsStandings(constructorsStandingsInit(teams));
        return championship;
    }

    private HashMap<Driver, Team> playerInit(String firstName, String lastName, String abbreviation, Country country, Team team) {
        HashMap<Driver, Team> player = new HashMap<>();
        player.put(new Driver(firstName, lastName, abbreviation, country), team);
        return player;
    }

    private List<HashMap<Driver, Integer>> driverStandingsInit(List<Team> teams) {
        List<HashMap<Driver, Integer>> driverStandings = new ArrayList<>(12);
        for (Team team : teams) {
            HashMap<Driver, Integer> driver1 = new HashMap<>(1);
            driver1.put(team.getDriver1(), 0);
            driverStandings.add(driver1);

            HashMap<Driver, Integer> driver2 = new HashMap<>(1);
            driver2.put(team.getDriver2(), 0);
            driverStandings.add(driver2);

        }
        return driverStandings;
    }

    private List<HashMap<Team, Integer>> constructorsStandingsInit(List<Team> teams) {
        List<HashMap<Team, Integer>> constructorsStandings = new ArrayList<>(12);
        for (Team team : teams) {
            HashMap<Team, Integer> constructor = new HashMap<>(1);
            constructor.put(team, 0);
            constructorsStandings.add(constructor);
        }
        return constructorsStandings;
    }

    private List<Team> teamsInit() {
        List<Team> teams = new ArrayList<>();

        teams.add(new Team("Scuderia Ferrari", "Ferrari", Country.ITA, 1,
                new Driver("Romeo", "Morbidelli", "MOR", Country.ITA),
                new Driver("Ethan", "Simon", "SIM", Country.BEL)));

        teams.add(new Team("Gulf McLaren GP Team", "McLaren", Country.GBR, 2,
                new Driver("Brent", "Harris", "HAR", Country.AUS),
                new Driver("Arthur", "Hardwick", "HRW", Country.GBR)));

        teams.add(new Team("NewMan Team Renault Elf", "Renault", Country.FRA, 3,
                new Driver("Emilio", "Cevallos", "CEV", Country.ESP),
                new Driver("Antoine", "Lavoie", "LAV", Country.FRA)));

        teams.add(new Team("Mercedes AMG Petronas", "Mercedes", Country.GBR, 4,
                new Driver("Gerhard", "Drager", "DRA", Country.DEU),
                new Driver("Noah", "Tanner", "TAN", Country.GBR)));

        teams.add(new Team("Team Lotus JPS", "Lotus", Country.BRA, 5,
                new Driver("Jack", "Campbell", "CAM", Country.NZL),
                new Driver("Arthur", "Ferreira", "FER", Country.BRA)));

        teams.add(new Team("Red Bull Honda Racing", "Honda", Country.NLD, 6,
                new Driver("David", "Stark", "STA", Country.USA),
                new Driver("Jozef", "Kloosterman", "KLO", Country.NLD)));

        teams.add(new Team("Aitalia Alfa Romeo", "Alfa Romeo", Country.FIN, 7,
                new Driver("Bruno", "Campioni", "CAM", Country.ITA),
                new Driver("Mikko", "Vanhala", "VAN", Country.FIN)));

        teams.add(new Team("Chevrolet", "Chevrolet", Country.BRA, 8,
                new Driver("Naveen", "Daggubati", "DAG", Country.IND),
                new Driver("Felipe", "Pinto", "PIN", Country.BRA)));

        teams.add(new Team("Castrol Jaguar Racing", "Jaguar", Country.ZAF, 9,
                new Driver("Ruairi", "Ferriter", "FRT", Country.IRL),
                new Driver("Rick", "Swart", "SWA", Country.ZAF)));

        teams.add(new Team("Warsteiner Porsche", "Porsche", Country.PRT, 10,
                new Driver("Lucas", "Pereyra", "PER", Country.ARG),
                new Driver("Jaime", "Oliveira", "OLI", Country.PRT)));

        teams.add(new Team("Jägermeister Toyota Racing", "Toyota", Country.MEX, 11,
                new Driver("Hiro", "Iwata", "IWA", Country.JPN),
                new Driver("Marcos", "Peralta", "PER", Country.MEX)));

        teams.add(new Team("BMW Martini Racing", "BMW", Country.AUT, 12,
                new Driver("Sebastian", "Duda", "DUD", Country.POL),
                new Driver("Samuel", "Schmid", "SCH", Country.AUT)));

        return teams;
    }
}
