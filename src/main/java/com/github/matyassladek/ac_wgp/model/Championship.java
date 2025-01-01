package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.matyassladek.ac_wgp.enums.Track;
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
    public Championship(@JsonProperty("calendar") List<Track> calendar) {
        this.calendar = calendar;
        this.scoring = List.of(50, 30, 20, 15, 10, 5, 4, 3, 2, 1);
        this.currentRound = 0;
    }

    @JsonIgnore
    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();
        for (DriverSlot driver : driversStandings) {
            drivers.add(driver.getDriver());
        }
        return drivers;
    }

    @Setter
    @Getter
    @ToString
    @NonNullByDefault
    public static class DriverSlot {

        @JsonProperty("driver")
        private final Driver driver;

        @JsonProperty("team")
        private final Team team;

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
        private final Team team;

        @JsonProperty("points")
        private int points;

        @JsonCreator
        public TeamSlot(@JsonProperty("team") Team team) {
            this.team = team;
            this.points = 0;
        }
    }
}
