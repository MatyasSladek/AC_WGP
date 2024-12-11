package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NonNullByDefault
public class Team extends Competitor {

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

    @JsonCreator
    public Team(
            @JsonProperty("manufacture") Manufacture manufacture,
            @JsonProperty("garage") int garage,
            @JsonProperty("driver1") Driver driver1,
            @JsonProperty("driver2") Driver driver2
    ) {
        super();
        this.manufacture = manufacture;
        this.budget = 1_000_000; // Default budget
        this.engine = new Engine(); // Default engine
        this.chassis = new Chassis(); // Default chassis
        this.garage = garage;
        this.driver1 = driver1;
        this.driver2 = driver2;
    }
}

