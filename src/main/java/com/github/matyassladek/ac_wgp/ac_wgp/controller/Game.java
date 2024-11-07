package com.github.matyassladek.ac_wgp.ac_wgp.controller;

import com.github.matyassladek.ac_wgp.ac_wgp.model.Country;
import com.github.matyassladek.ac_wgp.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.ac_wgp.model.Team;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public List<Team> teamsInit() {
        List<Team> teams = new ArrayList<Team>();
        teams.add(new Team("Scuderia Ferrari", "Ferrari", Country.ITA,
                new Driver("Romeo", "Morbidelli", "MOR", Country.ITA),
                new Driver("Ethan", "Simon", "SIM", Country.BEL)));
        //TODO: add remaining teams
        return teams;
    }
}
