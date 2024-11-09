package com.github.matyassladek.ac_wgp.ac_wgp.model;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@NonNullByDefault
public class Championship {

    private final Track[] calendar;
    private final int[] scoring;
    private int currentRound;
    private List<DriverSlot> driversStandings;
    private List<TeamSlot> constructorsStandings;
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

        private final Driver driver;
        private final Team team;
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

        private final Team team;
        private int points;

        public TeamSlot(Team team) {
            this.team = team;
            this.points = 0;
        }
    }
}
