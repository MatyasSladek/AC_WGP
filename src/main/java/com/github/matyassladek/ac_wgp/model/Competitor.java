package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public abstract class Competitor {

    @JsonProperty("championshipPoints")
    private List<Integer> championshipPoints;

    protected Competitor() {
        initializeChampionshipPoints();
    }

    protected void initializeChampionshipPoints() {
        this.championshipPoints = new ArrayList<>(14);
        for (int i = 0; i < 14; i++) {
            this.championshipPoints.add(0);
        }
    }
}
