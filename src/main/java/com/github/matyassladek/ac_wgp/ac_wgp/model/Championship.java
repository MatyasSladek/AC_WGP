package com.github.matyassladek.ac_wgp.ac_wgp.model;

import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Setter
public class Championship {

    private int season = 0;
    private final Track[] calendar;
    private final int[] scoring;
    private int currentRound;
    private List<HashMap<Driver, Integer>> driversStandings;
    private List<HashMap<Team, Integer>> constructorsStandings;
    private List<Race> results;

    public Championship(Track[] calendar) {
        this.calendar = calendar;
        this.scoring = new int[]{50, 30, 20, 15, 10, 5, 4, 3, 2, 1};
        this.currentRound = 0;
    }
}
