package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@Setter
@Getter
@NonNullByDefault
public class Championship {

    @JsonProperty("calendar")
    private Track[] calendar;

    @JsonProperty("scoring")
    private final int[] scoring;

    @JsonProperty("currentRound")
    private int currentRound;

    @JsonProperty("driversStandings")
    private List<DriverSlot> driversStandings;

    @JsonProperty("constructorsStandings")
    private List<TeamSlot> constructorsStandings;

    @JsonProperty("results")
    private List<Race> results;

    public Championship(Track[] calendar) {
        this.calendar = calendar;
        this.scoring = new int[]{50, 30, 20, 15, 10, 5, 4, 3, 2, 1};
        this.currentRound = 0;
    }

    @Setter
    @Getter
    @NonNullByDefault
    public static class DriverSlot {

        @JsonProperty("driver")
        private final Driver driver;

        @JsonProperty("team")
        private final Team team;

        @JsonProperty("points")
        private int points;

        public DriverSlot(Driver driver, Team team) {
            this.driver = driver;
            this.team = team;
            this.points = 0;
        }
    }

    @Setter
    @Getter
    @NonNullByDefault
    public static class TeamSlot {

        @JsonProperty("team")
        private final Team team;

        @JsonProperty("points")
        private int points;

        public TeamSlot(Team team) {
            this.team = team;
            this.points = 0;
        }
    }
}
