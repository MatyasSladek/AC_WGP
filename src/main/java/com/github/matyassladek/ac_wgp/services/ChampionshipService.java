package com.github.matyassladek.ac_wgp.services;

import com.github.matyassladek.ac_wgp.model.Championship;

import java.util.Comparator;
import java.util.List;

public class ChampionshipService {
    private final Championship championship;

    public ChampionshipService(Championship championship) {
        this.championship = championship;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void updateChampionshipPoints(List<String> raceResult) {
        List<Championship.DriverSlot> driversStandings = championship.getDriversStandings();
        List<Championship.TeamSlot> constructorsStandings = championship.getConstructorsStandings();

        for (int i = 0; i < 10; i++) {
            updateDriverAndConstructorPoints(raceResult.get(i), i, driversStandings, constructorsStandings);
        }

        driversStandings.sort(Comparator.comparingInt(Championship.DriverSlot::getPoints).reversed());
        constructorsStandings.sort(Comparator.comparingInt(Championship.TeamSlot::getPoints).reversed());
    }

    private void updateDriverAndConstructorPoints(String driverName, int raceIndex,
                                                  List<Championship.DriverSlot> driversStandings,
                                                  List<Championship.TeamSlot> constructorsStandings) {
        Championship.DriverSlot driverSlot = findDriverSlot(driverName, driversStandings);
        if (driverSlot == null) return;

        driverSlot.setPoints(driverSlot.getPoints() + championship.getScoring().get(raceIndex));
        Championship.TeamSlot teamSlot = findTeamSlotForDriver(driverSlot, constructorsStandings);
        if (teamSlot != null) {
            teamSlot.setPoints(teamSlot.getPoints() + championship.getScoring().get(raceIndex));
        }
    }

    private Championship.DriverSlot findDriverSlot(String driverName, List<Championship.DriverSlot> driversStandings) {
        return driversStandings.stream()
                .filter(slot -> slot.getDriver().getName().equals(driverName))
                .findFirst()
                .orElse(null);
    }

    private Championship.TeamSlot findTeamSlotForDriver(Championship.DriverSlot driverSlot,
                                                        List<Championship.TeamSlot> constructorsStandings) {
        return constructorsStandings.stream()
                .filter(team -> team.getTeam().getDriver1() == driverSlot.getDriver() ||
                        team.getTeam().getDriver2() == driverSlot.getDriver())
                .findFirst()
                .orElse(null);
    }
}
