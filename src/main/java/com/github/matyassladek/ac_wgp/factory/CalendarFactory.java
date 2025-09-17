package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.Calendar;
import com.github.matyassladek.ac_wgp.enums.Track;

import java.util.List;

public class CalendarFactory {

    public List<List<Track>> createAllSeasons() {
        return List.of(
                Calendar.TEST.getTracks(),
                Calendar.TEST.getTracks(),
                Calendar.TEST.getTracks(),
                Calendar.TEST.getTracks(),
                Calendar.TEST.getTracks(),
                Calendar.TEST.getTracks(),
                Calendar.TEST.getTracks()
        );
    }
}
