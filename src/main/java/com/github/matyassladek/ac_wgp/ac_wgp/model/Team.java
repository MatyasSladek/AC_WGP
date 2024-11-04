package com.github.matyassladek.ac_wgp.ac_wgp.model;

public class Team {

    private final String name;
    private final Country country;
    private int budget;
    private Engine engine;
    private Chassis chassis;

    public Team(String name, Country country, Engine engine, Chassis chassis) {
        this.name = name;
        this.country = country;
        this.budget = 1_000_000;
        this.engine = new Engine();
        this.chassis = new Chassis();
    }
}
