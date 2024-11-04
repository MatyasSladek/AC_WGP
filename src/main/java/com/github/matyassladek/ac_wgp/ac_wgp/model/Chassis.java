package com.github.matyassladek.ac_wgp.ac_wgp.model;

public class Chassis extends VehiclePart {
    public Chassis() {
        super(200, new int[]{20, 25, 25, 20, 20, 15, 15, 10, 10, 10, 10, 5, 5, 5, 5},
                5, 2_000_000, 50_000);
    }
}