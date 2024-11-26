package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

@Getter
@Setter
@ToString
@NonNullByDefault
public class Team {

    @JsonProperty("manufacture")
    private Manufacture manufacture;

    @JsonProperty("budget")
    private int budget;

    @JsonIgnore
    private Engine engine;

    @JsonIgnore
    private Chassis chassis;

    @JsonProperty("garage")
    private int garage;

    @JsonProperty("driver1")
    private Driver driver1;

    @JsonProperty("driver2")
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
