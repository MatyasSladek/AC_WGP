package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NonNullByDefault
public class Championship {

    @JsonProperty("calendar")
    private List<Track> calendar;

    @JsonProperty("scoring")
    private final List<Integer> scoring;

    @JsonProperty("currentRound")
    private int currentRound;

    @JsonProperty("driversStandings")
    private List<DriverSlot> driversStandings;

    @JsonProperty("constructorsStandings")
    private List<TeamSlot> constructorsStandings;

    @JsonProperty("results")
    private List<Race> results;

    @JsonCreator
    public Championship(
            @JsonProperty("calendar") List<Track> calendar,
            @JsonProperty("scoring") List<Integer> scoring) {
        this.calendar = calendar;
        this.scoring = scoring;
        this.currentRound = 0;
        this.results = new ArrayList<>();
    }

    @JsonIgnore
    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();
        for (DriverSlot driver : driversStandings) {
            drivers.add(driver.getDriver());
        }
        return drivers;
    }

    /**
     * Get the current race index (same as currentRound)
     * @return current race index
     */
    @JsonIgnore
    public int getCurrentRaceIndex() {
        return currentRound;
    }

    /**
     * Advance to the next race
     * @return true if advanced, false if no more races
     */
    @JsonIgnore
    public boolean advanceRound() {
        if (currentRound < calendar.size() - 1) {
            currentRound++;
            return true;
        }
        return false;
    }

    /**
     * Check if championship is complete
     * @return true if all races completed
     */
    @JsonIgnore
    public boolean isComplete() {
        return currentRound >= calendar.size();
    }

    /**
     * Get the current track
     * @return current Track or null if invalid index
     */
    @JsonIgnore
    public Track getCurrentTrack() {
        if (currentRound >= 0 && currentRound < calendar.size()) {
            return calendar.get(currentRound);
        }
        return null;
    }

    @Setter
    @Getter
    @ToString
    @NonNullByDefault
    public static class DriverSlot {

        @JsonProperty("driver")
        private final Driver driver;

        @JsonProperty("team")
        private Team team;  // Not final - allows reference reconciliation

        @JsonProperty("points")
        private int points;

        @JsonCreator
        public DriverSlot(
                @JsonProperty("driver") Driver driver,
                @JsonProperty("team") Team team
        ) {
            this.driver = driver;
            this.team = team;
            this.points = 0;
        }
    }

    @Setter
    @Getter
    @ToString
    @NonNullByDefault
    public static class TeamSlot {

        @JsonProperty("team")
        private Team team;  // Not final - allows reference reconciliation

        @JsonProperty("points")
        private int points;

        @JsonCreator
        public TeamSlot(@JsonProperty("team") Team team) {
            this.team = team;
            this.points = 0;
        }
    }
}