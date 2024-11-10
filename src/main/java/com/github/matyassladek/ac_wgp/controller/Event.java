package com.github.matyassladek.ac_wgp.controller;

import com.github.matyassladek.ac_wgp.model.Championship;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.model.Race;
import com.github.matyassladek.ac_wgp.model.Track;
import lombok.Getter;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@NonNullByDefault
@Getter
public class Event {

    private final Game game;
    private final Race race;
    private final int round;
    private final int championshipLength;
    private final Track track;
    private final int[] scoring;

    public Event(Game game) {
        this.game = game;
        this.race = new Race();
        this.round = game.getCurrentChampionship().getCurrentRound();
        this.championshipLength = game.getCurrentChampionship().getCalendar().length;
        this.track = game.getCurrentChampionship().getCalendar()[this.round];
        this.scoring = game.getCurrentChampionship().getScoring();
    }

    public boolean finishRace(List<Driver> result) {
        return writeResult(result) && updateStandings(result) && nextEvent();
    }

    private boolean writeResult(List<Driver> result) {
        race.setResult(result);
        List<Race> results = game.getCurrentChampionship().getResults();
        game.getCurrentChampionship().setResults(results);
        return true;
    }

    private boolean updateStandings(List<Driver> result) {
        List<Championship.DriverSlot> driverStandings = game.getCurrentChampionship().getDriversStandings();
        for (int i = 0; i < 10; i++) {
            for (Championship.DriverSlot driver : driverStandings) {
                if (driver.getDriver().equals(result.get(i))) {
                    driver.setPoints(driver.getPoints() + scoring[i]);
                    break;
                }
            }
        }
        return true;
    }

    private boolean nextEvent() {
        if ((round + 1) < championshipLength) {
            game.getCurrentChampionship().setCurrentRound(round + 1);
            return true;
        }
        if (game.getCurrentSeason() + 1 < game.getAllSeasons().length) {
            game.setCurrentSeason(game.getCurrentSeason() + 1);
            return true;
        }
        return false;
    }
}