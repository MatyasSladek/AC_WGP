package com.github.matyassladek.ac_wgp.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.matyassladek.ac_wgp.factory.CalendarFactory;
import com.github.matyassladek.ac_wgp.factory.ChampionshipFactory;
import com.github.matyassladek.ac_wgp.factory.TeamFactory;
import com.github.matyassladek.ac_wgp.model.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@NonNullByDefault
@Setter
@Getter
@ToString
public class Game {


    @JsonProperty("allSeasons")
    private final List<List<Track>> allSeasons;

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
        this.teams = teamsInit(playerTeam);
        this.currentChampionship = championshipInit();
        this.currentSeason = 0;
    }

    @JsonIgnore
    public List<List<Track>> getAllSeasons() {
        return allSeasons;
    }

    @JsonIgnore
    public boolean newSeason() {
        if (currentSeason < allSeasons.size()) {
            setCurrentSeason(currentSeason + 1);
            setCurrentChampionship(championshipInit());
            return true;
        } else {
            return false;
        }
    }

    @JsonIgnore
    private Championship championshipInit() {
        ChampionshipFactory championshipFactory = new ChampionshipFactory();
        return championshipFactory.createChampionship(teams, allSeasons.get(currentSeason));
    }

    @JsonIgnore
    private List<Team> teamsInit(Manufacture playerTeam){
        TeamFactory teamFactory = new TeamFactory();
        return teamFactory.crateTeamList(player, playerTeam);
    }

    @JsonIgnore
    private List<List<Track>> allSeasonsInit() {
        CalendarFactory calendarFactory = new CalendarFactory();
        return calendarFactory.createAllSeasons();
    }
}
