package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.matyassladek.ac_wgp.enums.Country;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.factory.TeamFactory;
import com.github.matyassladek.ac_wgp.utils.GameConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.ArrayList;
import java.util.List;

/**
 * Pure domain model representing the game state.
 * Contains only data and simple getters/setters.
 * Business logic has been moved to service classes.
 */
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
    private String fxmlScreen;

    @JsonProperty("configuration")
    private GameConfiguration configuration;

    // Private constructor - use GameFactory to create instances
    private Game(Driver player, List<Team> teams, GameConfiguration configuration) {
        this.allSeasons = new ArrayList<>();
        this.player = player;
        this.teams = teams;
        this.currentChampionship = null;
        this.currentSeason = 0;
        this.fxmlScreen = null; // Will be resolved by ScreenResolver
        this.configuration = configuration;
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

    // Factory method for creating new games
    public static Game create(String playerFirstName, String playerLastName,
                              Country playerCountry, Manufacture playerTeam,
                              String jsonResultsPath, String acGamePath,
                              TeamFactory teamFactory) {
        Driver player = new Driver(playerFirstName, playerLastName, playerCountry);
        List<Team> teams = teamFactory.createTeamList(player, playerTeam);
        GameConfiguration configuration = new GameConfiguration(jsonResultsPath, acGamePath);

        return new Game(player, teams, configuration);
    }

    /**
     * Get the calendar for the current season.
     *
     * @return List of Track objects for current season, or empty list if not set
     */
    @JsonIgnore
    public List<Track> getCurrentCalendar() {
        if (currentSeason >= 0 && currentSeason < allSeasons.size()) {
            return new ArrayList<>(allSeasons.get(currentSeason));
        }
        return new ArrayList<>();
    }

    /**
     * Get the calendar for a specific season.
     *
     * @param seasonIndex The season index
     * @return List of Track objects for the season, or empty list if invalid
     */
    @JsonIgnore
    public List<Track> getSeasonCalendar(int seasonIndex) {
        if (seasonIndex >= 0 && seasonIndex < allSeasons.size()) {
            return new ArrayList<>(allSeasons.get(seasonIndex));
        }
        return new ArrayList<>();
    }

    /**
     * Set the calendar for a specific season.
     *
     * @param seasonIndex The season index
     * @param calendar List of Track objects
     */
    public void setSeasonCalendar(int seasonIndex, List<Track> calendar) {
        if (calendar == null || calendar.isEmpty()) {
            throw new IllegalArgumentException("Calendar cannot be null or empty");
        }

        while (allSeasons.size() <= seasonIndex) {
            allSeasons.add(new ArrayList<>());
        }

        allSeasons.set(seasonIndex, new ArrayList<>(calendar));
    }

    /**
     * Check if a calendar exists for the current season.
     *
     * @return true if calendar exists and is not empty
     */
    @JsonIgnore
    public boolean hasCurrentCalendar() {
        return currentSeason < allSeasons.size()
                && allSeasons.get(currentSeason) != null
                && !allSeasons.get(currentSeason).isEmpty();
    }

    /**
     * Check if more seasons are available.
     *
     * @return true if there are more seasons
     */
    @JsonIgnore
    public boolean hasMoreSeasons() {
        final int max_seasons = 5;
        return currentSeason < max_seasons;
    }

    /**
     * Get the total number of seasons.
     *
     * @return number of seasons
     */
    @JsonIgnore
    public int getTotalSeasons() {
        return allSeasons.size();
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