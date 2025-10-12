package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.Calendar;
import com.github.matyassladek.ac_wgp.enums.TrackEnum;

import java.util.List;

public class CalendarFactory {

    public List<List<TrackEnum>> createAllSeasons() {
        return List.of(
                Calendar.TEST.getTrackEnums(),
                Calendar.TEST.getTrackEnums(),
                Calendar.TEST.getTrackEnums(),
                Calendar.TEST.getTrackEnums(),
                Calendar.TEST.getTrackEnums(),
                Calendar.TEST.getTrackEnums(),
                Calendar.TEST.getTrackEnums()
        );
    }
}
