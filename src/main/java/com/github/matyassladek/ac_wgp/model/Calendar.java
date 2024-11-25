package com.github.matyassladek.ac_wgp.model;

import lombok.Getter;
import org.eclipse.jdt.annotation.NonNullByDefault;

@Getter
@NonNullByDefault
public enum Calendar {
    SEASON_1(new Track[]{
            Track.IMOLA,
            Track.SILVERSTONE,
            Track.NURBURGRING,
            Track.ZANDVOORT,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.MUGELLO
    }),
    SEASON_2(new Track[]{
            Track.SILVERSTONE,
            Track.NURBURGRING,
            Track.MUGELLO,
            Track.LE_MANS_BUGATTI,
            Track.ROAD_AMERICA,
            Track.SUZUKA
    }),
    SEASON_3(new Track[]{
            Track.MELBOURNE,
            Track.KYALAMI,
            Track.SILVERSTONE,
            Track.LE_MANS_BUGATTI,
            Track.NURBURGRING,
            Track.MUGELLO,
            Track.ROAD_AMERICA,
            Track.INTERLAGOS,
            Track.SUZUKA
    }),
    SEASON_4(new Track[]{
            Track.MELBOURNE,
            Track.KYALAMI,
            Track.SILVERSTONE,
            Track.LE_MANS_BUGATTI,
            Track.NURBURGRING,
            Track.MUGELLO,
            Track.ALGARVE,
            Track.ROAD_AMERICA,
            Track.SUZUKA,
            Track.INTERLAGOS
    }),
    SEASON_5(new Track[]{
            Track.MELBOURNE,
            Track.KYALAMI,
            Track.MONTE_CARLO,
            Track.NURBURGRING,
            Track.SILVERSTONE,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.SUZUKA,
            Track.ROAD_AMERICA,
            Track.INTERLAGOS
    }),
    SEASON_6(new Track[]{
            Track.MELBOURNE,
            Track.KYALAMI,
            Track.MONTE_CARLO,
            Track.NURBURGRING,
            Track.SILVERSTONE,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.ROAD_AMERICA,
            Track.INTERLAGOS,
            Track.SUZUKA
    }),
    SEASON_7(new Track[]{
            Track.MELBOURNE,
            Track.SEPANG,
            Track.KYALAMI,
            Track.SAKHIR,
            Track.SILVERSTONE,
            Track.MONTE_CARLO,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.INTERLAGOS,
            Track.ROAD_AMERICA,
            Track.SUZUKA
    }),
    SEASON_8(new Track[]{
            Track.MELBOURNE,
            Track.SEPANG,
            Track.KYALAMI,
            Track.MONTE_CARLO,
            Track.NURBURGRING,
            Track.SILVERSTONE,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.MONTREAL,
            Track.ROAD_AMERICA,
            Track.INTERLAGOS,
            Track.SUZUKA
    }),
    SEASON_9(new Track[]{
            Track.MELBOURNE,
            Track.SEPANG,
            Track.SAKHIR,
            Track.KYALAMI,
            Track.BARCELONA,
            Track.SILVERSTONE,
            Track.NURBURGRING,
            Track.LE_MANS_BUGATTI,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.ROAD_AMERICA,
            Track.INTERLAGOS,
            Track.SUZUKA
    }),
    SEASON_10(new Track[]{
            Track.MELBOURNE,
            Track.SEPANG,
            Track.SAKHIR,
            Track.ALGARVE,
            Track.BARCELONA,
            Track.MONTE_CARLO,
            Track.NURBURGRING,
            Track.SILVERSTONE,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.INDIANAPOLIS,
            Track.SUZUKA,
            Track.INTERLAGOS
    }),
    SEASON_11(new Track[]{
            Track.MELBOURNE,
            Track.SEPANG,
            Track.SAKHIR,
            Track.KYALAMI,
            Track.BARCELONA,
            Track.MONTE_CARLO,
            Track.SILVERSTONE,
            Track.NURBURGRING,
            Track.LE_MANS_BUGATTI,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.INDIANAPOLIS,
            Track.MONTREAL,
            Track.INTERLAGOS,
            Track.SUZUKA
    }),
    SEASON_12(new Track[]{
            Track.MELBOURNE,
            Track.SEPANG,
            Track.SAKHIR,
            Track.KYALAMI,
            Track.BARCELONA,
            Track.MONTE_CARLO,
            Track.MONTREAL,
            Track.SILVERSTONE,
            Track.NURBURGRING,
            Track.LE_MANS_BUGATTI,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.INDIANAPOLIS,
            Track.SUZUKA,
            Track.INTERLAGOS
    }),
    SEASON_13(new Track[]{
            Track.MELBOURNE,
            Track.SEPANG,
            Track.SAKHIR,
            Track.KYALAMI,
            Track.ALGARVE,
            Track.BARCELONA,
            Track.MONTE_CARLO,
            Track.SILVERSTONE,
            Track.NURBURGRING,
            Track.LE_MANS_BUGATTI,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.INDIANAPOLIS,
            Track.MONTREAL,
            Track.INTERLAGOS,
            Track.SUZUKA
    }),
    SEASON_14(new Track[]{
            Track.MELBOURNE,
            Track.SEPANG,
            Track.KYALAMI,
            Track.SAKHIR,
            Track.BARCELONA,
            Track.MONTE_CARLO,
            Track.SILVERSTONE,
            Track.NURBURGRING,
            Track.LE_MANS_BUGATTI,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.ALGARVE,
            Track.MONTREAL,
            Track.ROAD_AMERICA,
            Track.INTERLAGOS,
            Track.SUZUKA
    }),
    SEASON_15(new Track[]{
            Track.INTERLAGOS,
            Track.KYALAMI,
            Track.MELBOURNE,
            Track.IMOLA,
            Track.BARCELONA,
            Track.MONTE_CARLO,
            Track.SILVERSTONE,
            Track.NURBURGRING,
            Track.LE_MANS_BUGATTI,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.MONTREAL,
            Track.WATKINS_GLEN,
            Track.ROAD_AMERICA,
            Track.CHANG,
            Track.SUZUKA
    }),
    SEASON_16(new Track[]{
            Track.MELBOURNE,
            Track.SEPANG,
            Track.SAKHIR,
            Track.KYALAMI,
            Track.BARCELONA,
            Track.MONTE_CARLO,
            Track.SILVERSTONE,
            Track.NURBURGRING,
            Track.BRNO,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.LE_MANS_BUGATTI,
            Track.ALGARVE,
            Track.MUGELLO,
            Track.MONTREAL,
            Track.WATKINS_GLEN,
            Track.CHANG,
            Track.SUZUKA,
            Track.INTERLAGOS,
            Track.MOST
    }),
    SEASON_17(new Track[]{
            Track.MELBOURNE,
            Track.SEPANG,
            Track.SAKHIR,
            Track.KYALAMI,
            Track.MONTE_CARLO,
            Track.SILVERSTONE,
            Track.SPA_FRANCORCHAMPS,
            Track.MONZA,
            Track.MONTREAL,
            Track.WATKINS_GLEN,
            Track.ROAD_AMERICA,
            Track.INTERLAGOS,
            Track.CHANG,
            Track.SUZUKA
    });

    private final Track[] tracks;

    Calendar(Track[] tracks) {
        this.tracks = tracks;
    }
}
