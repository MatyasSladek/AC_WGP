package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.matyassladek.ac_wgp.enums.Country;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.factory.CalendarFactory;
import com.github.matyassladek.ac_wgp.factory.ChampionshipFactory;
import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.enums.Track;
import com.github.matyassladek.ac_wgp.factory.TeamFactory;
import com.github.matyassladek.ac_wgp.helpers.GameConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;
import java.util.Random;

@NonNullByDefault
@Setter
@Getter
@ToString
public class Game {

    @JsonIgnore
    private static final Random random = new Random();

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

    @JsonProperty("configuration")
    private GameConfiguration configuration;

    // Constructor used for game initialization
    public Game(String playerFirstName, String playerLastName, Country playerCountry, Manufacture playerTeam) {
        this.allSeasons = allSeasonsInit();
        this.player = new Driver(playerFirstName, playerLastName, playerCountry);
        this.teams = new TeamFactory().createTeamList(player, playerTeam);
        this.currentChampionship = championshipInit();
        this.currentSeason = 0;
        this.fxmlScreen = FXMLFile.DRIVERS_STANDINGS.getFileName();
        this.configuration = new GameConfiguration();
    }

    // Constructor with AC paths
    public Game(String playerFirstName, String playerLastName, Country playerCountry, Manufacture playerTeam,
                String jsonResultsPath, String acGamePath) {
        this(playerFirstName, playerLastName, playerCountry, playerTeam);
        this.configuration = new GameConfiguration(jsonResultsPath, acGamePath);
    }

    // Constructor used by Jackson for deserialization
    @JsonCreator
    public Game(
            @JsonProperty("allSeasons") List<List<Track>> allSeasons,
            @JsonProperty("player") Driver player,
            @JsonProperty("teams") List<Team> teams,
            @JsonProperty("currentChampionship") Championship currentChampionship,
            @JsonProperty("currentSeason") int currentSeason,
            @JsonProperty("fxmlScreen") String fxmlScreen,
            @JsonProperty("configuration") GameConfiguration configuration) {
        this.allSeasons = allSeasons;
        this.player = player;
        this.teams = teams;
        this.currentChampionship = currentChampionship;
        this.currentSeason = currentSeason;
        this.fxmlScreen = fxmlScreen;
        this.configuration = configuration != null ? configuration : new GameConfiguration();
    }

    @JsonIgnore
    public boolean newSeason() {
        if (currentSeason < allSeasons.size()) {
            setCurrentSeason(currentSeason + 1);

            this.currentChampionship.getConstructorsStandings().sort(
                    (teamSlot1, teamSlot2) -> Integer.compare(teamSlot2.getPoints(), teamSlot1.getPoints())
            );

            int garage = 0;
            for (Championship.TeamSlot teamSlot : this.currentChampionship.getConstructorsStandings()) {
                Team team = teamSlot.getTeam();
                team.setGarage(garage);
                garage++;

                team.getEngine().upgrade(team.getFactoryLevel(), currentSeason, random);
                team.getChassis().upgrade(team.getFactoryLevel(), currentSeason, random);

                if (random.nextInt(5) == 0) {
                    team.setFactoryLevel(team.getFactoryLevel() + 1);
                }
            }

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

    // Convenience methods for configuration access
    @JsonIgnore
    public String getJsonResultsPath() {
        return configuration.getJsonResultsPath();
    }

    @JsonIgnore
    public void setJsonResultsPath(String jsonResultsPath) {
        configuration.setJsonResultsPath(jsonResultsPath);
    }

    @JsonIgnore
    public String getAcGamePath() {
        return configuration.getAcGamePath();
    }

    @JsonIgnore
    public void setAcGamePath(String acGamePath) {
        configuration.setAcGamePath(acGamePath);
    }

    @JsonIgnore
    public boolean isAutoLoadResults() {
        return configuration.isAutoLoadResults();
    }

    @JsonIgnore
    public void setAutoLoadResults(boolean autoLoadResults) {
        configuration.setAutoLoadResults(autoLoadResults);
    }

    @JsonIgnore
    public boolean isValidateTracks() {
        return configuration.isValidateTracks();
    }

    @JsonIgnore
    public void setValidateTracks(boolean validateTracks) {
        configuration.setValidateTracks(validateTracks);
    }
}