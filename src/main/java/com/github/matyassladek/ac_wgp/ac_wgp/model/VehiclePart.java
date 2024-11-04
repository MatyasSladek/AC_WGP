package com.github.matyassladek.ac_wgp.ac_wgp.model;

public abstract class VehiclePart {

    protected final int initialPerformance;
    protected final int[] upgradeSpeed;
    protected final int upgradeValue;
    protected final int basicUpgradeCost;
    protected final int upgradeCostIncrease;
    protected int performance;
    protected int upgradeCost;

    protected VehiclePart(int initialPerformance, int[] upgradeSpeed, int upgradeValue, int basicUpgradeCost, int upgradeCostIncrease) {
        this.initialPerformance = initialPerformance;
        this.upgradeSpeed = upgradeSpeed;
        this.upgradeValue = upgradeValue;
        this.basicUpgradeCost = basicUpgradeCost;
        this.upgradeCostIncrease = upgradeCostIncrease;
        this.performance = initialPerformance;
        this.upgradeCost = basicUpgradeCost;
    }

    public void upgrade() {
        this.performance -= this.upgradeValue;
        this.upgradeCost += this.upgradeCostIncrease;
    }
}
