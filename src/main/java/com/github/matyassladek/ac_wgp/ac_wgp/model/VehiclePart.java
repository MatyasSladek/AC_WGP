package com.github.matyassladek.ac_wgp.ac_wgp.model;

public abstract class VehiclePart {

    protected final int initialPerformance;
    protected final int[] upgradeSpeed;
    protected final int upgradeValue;
    protected final int basicUpgradeCost;
    protected final int upgradeCostIncrease;

    public VehiclePart(int initialPerformance, int[] upgradeSpeed, int upgradeValue, int basicUpgradeCost, int upgradeCostIncrease) {
        this.initialPerformance = initialPerformance;
        this.upgradeSpeed = upgradeSpeed;
        this.upgradeValue = upgradeValue;
        this.basicUpgradeCost = basicUpgradeCost;
        this.upgradeCostIncrease = upgradeCostIncrease;
    }
}
