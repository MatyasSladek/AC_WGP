package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.Scoring;
import com.github.matyassladek.ac_wgp.model.Championship;
import com.github.matyassladek.ac_wgp.model.Team;
import com.github.matyassladek.ac_wgp.enums.Track;

import java.util.ArrayList;
import java.util.List;

public class ChampionshipFactory {

    public Championship createChampionship(List<Team> teams, List<Track> season) {
        Championship championship = new Championship(season, Scoring.TOP15_B.getPoints());
        championship.setDriversStandings(createDriverStandings(teams));
        championship.setConstructorsStandings(createConstructorsStandings(teams));
        return championship;
    }

    private List<Championship.DriverSlot> createDriverStandings(List<Team> teams) {
        List<Championship.DriverSlot> driverStandings = new ArrayList<>(12);
        for (Team team : teams) {
            driverStandings.add(new Championship.DriverSlot(team.getDriver1(), team));
            driverStandings.add(new Championship.DriverSlot(team.getDriver2(), team));
        }
        return driverStandings;
    }

    private List<Championship.TeamSlot> createConstructorsStandings(List<Team> teams) {
        List<Championship.TeamSlot> constructorsStandings = new ArrayList<>(12);
        for (Team team : teams) {
            constructorsStandings.add(new Championship.TeamSlot(team));
        }
        return constructorsStandings;
    }
}
