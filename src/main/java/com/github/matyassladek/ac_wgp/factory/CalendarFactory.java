package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.Calendar;
import com.github.matyassladek.ac_wgp.enums.Track;

import java.util.List;

public class CalendarFactory {

    public List<List<Track>> createAllSeasons() {
        return List.of(
                Calendar.SEASON_1.getTracks(),
                Calendar.SEASON_2.getTracks()
        );
    }
}
