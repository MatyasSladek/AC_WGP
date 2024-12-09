package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

@NonNullByDefault
@ToString
@Getter
@Setter
public class Driver {

    private final String firstName;
    private final String lastName;
    private final Country country;

    @JsonCreator
    public Driver(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("country") Country country
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
    }

    @JsonIgnore
    public String getName() {
        return firstName + " " + lastName;
    }
}
