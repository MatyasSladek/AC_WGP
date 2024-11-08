package com.github.matyassladek.ac_wgp.ac_wgp.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Race {

    private final int round;
    private final Track track;
    private List<Driver> result;

    public Race(int round, Track track) {
        this.round = round;
        this.track = track;
    }
}
