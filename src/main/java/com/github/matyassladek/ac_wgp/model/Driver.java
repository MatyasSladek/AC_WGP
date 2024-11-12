package com.github.matyassladek.ac_wgp.model;

import lombok.ToString;
import org.eclipse.jdt.annotation.NonNullByDefault;

@NonNullByDefault
@ToString
public class Driver {

    private String firstName;
    private String lastName;
    Country country;

    public Driver(String firstName, String lastName, Country country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
    }
}
