package com.github.matyassladek.ac_wgp.ac_wgp.model;

public class Chassis extends VehiclePart {
    public Chassis() {
        super(new int[]{20, 25, 25, 20, 20, 15, 15, 10, 10, 10, 10, 5, 5, 5, 5},
                5, 50_000);
        this.performance = 200;
        this.upgradeCost = 2_000_000;
    }
}