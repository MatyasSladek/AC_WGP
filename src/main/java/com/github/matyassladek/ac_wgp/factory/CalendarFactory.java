package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.Calendar;
import com.github.matyassladek.ac_wgp.enums.Track;

import java.util.List;

public class CalendarFactory {

    public List<List<Track>> createAllSeasons() {
        return List.of(
                Calendar.SEASON_1.getTracks()
//                Calendar.SEASON_2.getTracks(),
//                Calendar.SEASON_3.getTracks(),
//                Calendar.SEASON_4.getTracks(),
//                Calendar.SEASON_5.getTracks(),
//                Calendar.SEASON_6.getTracks(),
//                Calendar.SEASON_7.getTracks(),
//                Calendar.SEASON_8.getTracks(),
//                Calendar.SEASON_9.getTracks(),
//                Calendar.SEASON_10.getTracks(),
//                Calendar.SEASON_11.getTracks(),
//                Calendar.SEASON_12.getTracks(),
//                Calendar.SEASON_13.getTracks(),
//                Calendar.SEASON_14.getTracks(),
//                Calendar.SEASON_15.getTracks(),
//                Calendar.SEASON_16.getTracks(),
//                Calendar.SEASON_17.getTracks()
        );
    }
}
