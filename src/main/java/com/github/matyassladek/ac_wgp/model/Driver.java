package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;
import java.util.Optional;

@NonNullByDefault
@ToString
@Getter
@Setter
public class Driver extends Competitor {

    private final String firstName;
    private final String lastName;
    private final Country country;
    private List<Integer> results;
    private int wins;
    private int podiums;
    private int top10;
    private int points;
    private int starts;
    private double averagePoints;
    private double averagePosition;
    // private int poles;

    @JsonCreator
    public Driver(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("country") Country country
    ) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
    }

    @JsonIgnore
    public String getName() {
        return firstName + " " + lastName;
    }
}
