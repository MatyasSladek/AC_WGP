package com.github.matyassladek.ac_wgp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@Getter
@AllArgsConstructor
@NonNullByDefault
public enum Calendar {
    SEASON_1(List.of(
            Track.MELBOURNE,
            Track.SEPANG,
            Track.KYALAMI,
            Track.ALGARVE,
            Track.NURBURGRING,
            Track.BRNO,
            Track.SPA_FRANCORCHAMPS,
            Track.MUGELLO,
            Track.SUZUKA,
            Track.ROAD_AMERICA,
            Track.INTERLAGOS
    )),
    SEASON_2(List.of(
            Track.MELBOURNE,
            Track.SEPANG,
            Track.KYALAMI,
            Track.ALGARVE,
            Track.NURBURGRING,
            Track.BRNO,
            Track.SPA_FRANCORCHAMPS,
            Track.MUGELLO,
            Track.SUZUKA,
            Track.ROAD_AMERICA,
            Track.INTERLAGOS
    ));

    private final List<Track> tracks;
}