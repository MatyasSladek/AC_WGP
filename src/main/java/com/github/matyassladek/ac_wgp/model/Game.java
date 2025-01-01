package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.matyassladek.ac_wgp.enums.Country;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.factory.CalendarFactory;
import com.github.matyassladek.ac_wgp.factory.ChampionshipFactory;
import com.github.matyassladek.ac_wgp.factory.TeamFactory;
import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.enums.Track;
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

    @JsonProperty("fxmlScreen")
    private final String fxmlScreen;

    // Constructor used for game initialization
    public Game(String playerFirstName, String playerLastName, Country playerCountry, Manufacture playerTeam) {
        this.allSeasons = allSeasonsInit();
        this.player = new Driver(playerFirstName, playerLastName, playerCountry);
        this.teams = new TeamFactory().createTeamList(player, playerTeam);
        this.currentChampionship = championshipInit();
        this.currentSeason = 0;
        this.fxmlScreen = FXMLFile.DRIVERS_STANDINGS.getFileName();
    }

    // Constructor used by Jackson for deserialization
    @JsonCreator
    public Game(
            @JsonProperty("allSeasons") List<List<Track>> allSeasons,
            @JsonProperty("player") Driver player,
            @JsonProperty("teams") List<Team> teams,
            @JsonProperty("currentChampionship") Championship currentChampionship,
            @JsonProperty("currentSeason") int currentSeason,
            @JsonProperty("fxmlScreen") String fxmlScreen) {
        this.allSeasons = allSeasons;
        this.player = player;
        this.teams = teams;
        this.currentChampionship = currentChampionship;
        this.currentSeason = currentSeason;
        this.fxmlScreen = fxmlScreen;
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
    private List<List<Track>> allSeasonsInit() {
        CalendarFactory calendarFactory = new CalendarFactory();
        return calendarFactory.createAllSeasons();
    }
}
