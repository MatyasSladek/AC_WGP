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

        for (int i = 0; i < this.championship.getScoring().size(); i++) {
            updateDriverAndConstructorPoints(raceResult.get(i), i, driversStandings, constructorsStandings);
        }

        driversStandings.sort(Comparator.comparingInt(Championship.DriverSlot::getPoints).reversed());
        constructorsStandings.sort(Comparator.comparingInt(Championship.TeamSlot::getPoints).reversed());
    }

    private void updateDriverAndConstructorPoints(String driverName, int raceIndex,
                                                  List<Championship.DriverSlot> driversStandings,
                                                  List<Championship.TeamSlot> constructorsStandings) {
        Championship.DriverSlot driverSlot = findDriverSlot(driverName, driversStandings);
        if (driverSlot != null) {
            driverSlot.setPoints(driverSlot.getPoints() + championship.getScoring().get(raceIndex));
        }

        Championship.TeamSlot teamSlot = findTeamSlotForDriver(driverName, constructorsStandings);
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

    private Championship.TeamSlot findTeamSlotForDriver(String driverName,
                                                        List<Championship.TeamSlot> constructorsStandings) {
        return constructorsStandings.stream()
                .filter(team -> team.getTeam().getDriver1().getName().equals(driverName) ||
                        team.getTeam().getDriver2().getName().equals(driverName))
                .findFirst()
                .orElse(null);
    }
}
