package com.github.matyassladek.ac_wgp.ac_wgp.model;

public class Team {

    private final String name;
    private final Country country;
    private int budget;
    private Engine engine;
    private Chassis chassis;
    private Driver driver1;
    private Driver driver2;

    public Team(String name, Country country, Driver driver1, Driver driver2) {
        this.name = name;
        this.country = country;
        this.budget = 1_000_000;
        this.engine = new Engine();
        this.chassis = new Chassis();
        this.driver1 = driver1;
        this.driver2 = driver2;
    }
}
