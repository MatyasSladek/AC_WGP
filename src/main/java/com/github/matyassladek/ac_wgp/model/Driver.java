package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.matyassladek.ac_wgp.enums.Country;
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

    private List<Optional<Team>> teams;
    private List<Optional<Integer>> results;
    private int wins;
    private int podiums;
    private int top10;
    private int points;
    private int starts;

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
        this.wins = 0;
        this.podiums = 0;
        this.top10 = 0;
        this.points = 0;
        this.starts = 0;
    }

    @JsonIgnore
    public String getName() {
        return firstName + " " + lastName;
    }
}
