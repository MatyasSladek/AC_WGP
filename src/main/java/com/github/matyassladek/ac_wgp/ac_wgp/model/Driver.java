package com.github.matyassladek.ac_wgp.ac_wgp.model;

public class Driver {

    private String firstName;
    private String lastName;
    private String abbreviation;
    Country country;

    public Driver(String firstName, String lastName, String abbreviation, Country country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.abbreviation = abbreviation;
        this.country = country;
    }
}
