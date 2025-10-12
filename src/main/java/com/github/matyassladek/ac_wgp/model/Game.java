package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.matyassladek.ac_wgp.enums.Country;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.factory.CalendarFactory;
import com.github.matyassladek.ac_wgp.factory.ChampionshipFactory;
import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.helpers.GameConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.ArrayList;
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
        this.allSeasons = new ArrayList<>();
        this.player = new Driver(playerFirstName, playerLastName, playerCountry);
        this.teams = new com.github.matyassladek.ac_wgp.factory.TeamFactory().createTeamList(player, playerTeam);
        this.currentChampionship = null; // Will be initialized after calendar is set
        this.currentSeason = 0;
        this.fxmlScreen = FXMLFile.DRIVERS_STANDINGS.getFileName();
        this.configuration = new GameConfiguration();
    }

    // Constructor with AC paths
    public Game(String playerFirstName, String playerLastName, Country playerCountry, Manufacture playerTeam,
                String jsonResultsPath, String acGamePath) {
        this.allSeasons = new ArrayList<>();
        this.player = new Driver(playerFirstName, playerLastName, playerCountry);
        this.teams = new com.github.matyassladek.ac_wgp.factory.TeamFactory().createTeamList(player, playerTeam);
        this.currentChampionship = null; // Will be initialized after calendar is set
        this.currentSeason = 0;
        this.fxmlScreen = FXMLFile.DRIVERS_STANDINGS.getFileName();
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
        this.allSeasons = allSeasons != null ? allSeasons : new ArrayList<>();
        this.player = player;
        this.teams = teams;
        this.currentChampionship = currentChampionship;
        this.currentSeason = currentSeason;
        this.fxmlScreen = fxmlScreen;
        this.configuration = configuration != null ? configuration : new GameConfiguration();
    }

    /**
     * Set the custom calendar for the current season
     * This replaces the enum-based calendar with Track objects loaded from AC
     *
     * @param calendar List of Track objects for the season
     */
    @JsonIgnore
    public void setCustomCalendar(List<Track> calendar) {
        if (calendar == null || calendar.isEmpty()) {
            throw new IllegalArgumentException("Calendar cannot be null or empty");
        }

        if (currentSeason < allSeasons.size()) {
            allSeasons.set(currentSeason, new ArrayList<>(calendar));
        } else {
            allSeasons.add(new ArrayList<>(calendar));
        }
    }

    /**
     * Get the calendar for the current season
     *
     * @return List of Track objects for current season, or empty list if not set
     */
    @JsonIgnore
    public List<Track> getCurrentCalendar() {
        if (currentSeason >= 0 && currentSeason < allSeasons.size()) {
            return allSeasons.get(currentSeason);
        }
        return new ArrayList<>();
    }

    /**
     * Advance to a new season
     * Upgrades team components, sorts standings, and initializes new championship
     *
     * @return true if advanced to new season, false if no more seasons available
     */
    @JsonIgnore
    public boolean newSeason() {
        if (currentSeason < allSeasons.size() - 1) {
            setCurrentSeason(currentSeason + 1);

            // Sort constructor standings by points
            this.currentChampionship.getConstructorsStandings().sort(
                    (teamSlot1, teamSlot2) -> Integer.compare(teamSlot2.getPoints(), teamSlot1.getPoints())
            );

            // Update team garages and upgrade components
            int garage = 0;
            for (Championship.TeamSlot teamSlot : this.currentChampionship.getConstructorsStandings()) {
                Team team = teamSlot.getTeam();
                team.setGarage(garage);
                garage++;

                team.getEngine().upgrade(team.getFactoryLevel(), currentSeason, random);
                team.getChassis().upgrade(team.getFactoryLevel(), currentSeason, random);

                // Random chance to upgrade factory level
                if (random.nextInt(5) == 0) {
                    team.setFactoryLevel(team.getFactoryLevel() + 1);
                }
            }

            // Initialize championship for new season
            setCurrentChampionship(championshipInit());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Initialize championship with the current season's calendar
     *
     * @return Championship object or null if calendar not set
     */
    @JsonIgnore
    private Championship championshipInit() {
        if (currentSeason >= allSeasons.size() || allSeasons.get(currentSeason).isEmpty()) {
            return null;
        }

        ChampionshipFactory championshipFactory = new ChampionshipFactory();
        return championshipFactory.createChampionship(teams, allSeasons.get(currentSeason));
    }

    /**
     * Get the current race from the championship
     *
     * @return Current Track or null if no championship or invalid race index
     */
    @JsonIgnore
    public Track getCurrentRace() {
        if (currentChampionship == null) {
            return null;
        }

        List<Track> calendar = getCurrentCalendar();
        int raceIndex = currentChampionship.getCurrentRaceIndex();

        if (raceIndex >= 0 && raceIndex < calendar.size()) {
            return calendar.get(raceIndex);
        }

        return null;
    }

    /**
     * Check if there are more races in the current season
     *
     * @return true if more races available
     */
    @JsonIgnore
    public boolean hasMoreRaces() {
        if (currentChampionship == null) {
            return false;
        }

        List<Track> calendar = getCurrentCalendar();
        return currentChampionship.getCurrentRaceIndex() < calendar.size() - 1;
    }

    /**
     * Check if the current season's calendar is set
     *
     * @return true if calendar exists and is not empty
     */
    @JsonIgnore
    public boolean hasCalendar() {
        return currentSeason < allSeasons.size() && !allSeasons.get(currentSeason).isEmpty();
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