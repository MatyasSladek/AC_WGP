package com.github.matyassladek.ac_wgp.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.matyassladek.ac_wgp.factory.CalendarFactory;
import com.github.matyassladek.ac_wgp.factory.TeamFactory;
import com.github.matyassladek.ac_wgp.model.*;
import com.github.matyassladek.ac_wgp.view.DriverStandingsController;
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


    @JsonProperty("allSeasons")
    private final Track[][] allSeasons;

    @JsonProperty("player")
    private final Driver player;

    @JsonProperty("teams")
    private final List<Team> teams;

    @JsonProperty("currentChampionship")
    private Championship currentChampionship;

    @JsonProperty("currentSeason")
    private int currentSeason;

    public Game(String playerFirstName, String playerLastName, Country playerCountry, Manufacture playerTeam) {
        this.allSeasons = allSeasonsInit();
        this.player = new Driver(playerFirstName, playerLastName, playerCountry);
        this.teams = playerInit(player, playerTeam);
        this.currentChampionship = championshipInit();
        this.currentSeason = 0;
    }

    // Here is an example of how to set the championship from Game to the controller
    public void initializeDriverStandingsController(DriverStandingsController controller) {
        controller.setChampionship(currentChampionship); // Pass the current championship
    }

    @JsonIgnore
    public Track[][] getAllSeasons() {
        return allSeasons;
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
        return TeamFactory.teamsInit();
    }

    private Track[][] allSeasonsInit() {
        return CalendarFactory.allSeasonsInit();
    }
}
