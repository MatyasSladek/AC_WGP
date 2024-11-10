package com.github.matyassladek.ac_wgp.model;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.jdt.annotation.NonNullByDefault;

@Getter
@Setter
@NonNullByDefault
public class Team {
    private final Manufacture manufacture;
    private int budget;
    private Engine engine;
    private Chassis chassis;
    private int garage;
    private Driver driver1;
    private Driver driver2;

    public Team(Manufacture manufacture, int garage, Driver driver1, Driver driver2) {
        this.manufacture = manufacture;
        this.budget = 1_000_000;
        this.engine = new Engine();
        this.chassis = new Chassis();
        this.garage = garage;
        this.driver1 = driver1;
        this.driver2 = driver2;
    }
}