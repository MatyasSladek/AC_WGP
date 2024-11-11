package com.github.matyassladek.ac_wgp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DriverStandings {

    private final IntegerProperty position;
    private final StringProperty driver;
    private final StringProperty team;
    private final IntegerProperty points;

    // Constructor
    public DriverStandings(int position, String driver, String team, int points) {
        this.position = new SimpleIntegerProperty(position);
        this.driver = new SimpleStringProperty(driver);
        this.team = new SimpleStringProperty(team);
        this.points = new SimpleIntegerProperty(points);
    }

    // Getter and Setter methods for position
    public int getPosition() {
        return position.get();
    }

    public void setPosition(int position) {
        this.position.set(position);
    }

    public IntegerProperty positionProperty() {
        return position;
    }

    // Getter and Setter methods for driver
    public String getDriver() {
        return driver.get();
    }

    public void setDriver(String driver) {
        this.driver.set(driver);
    }

    public StringProperty driverProperty() {
        return driver;
    }

    // Getter and Setter methods for team
    public String getTeam() {
        return team.get();
    }

    public void setTeam(String team) {
        this.team.set(team);
    }

    public StringProperty teamProperty() {
        return team;
    }

    // Getter and Setter methods for points
    public int getPoints() {
        return points.get();
    }

    public void setPoints(int points) {
        this.points.set(points);
    }

    public IntegerProperty pointsProperty() {
        return points;
    }
}
