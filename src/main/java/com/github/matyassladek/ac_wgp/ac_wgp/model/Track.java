package com.github.matyassladek.ac_wgp.ac_wgp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Track {
    ALGARVE("Algarve International Circuit", Country.PRT, calculateLaps(4653)),
    BARCELONA("Circuit de Barcelona-Catalunya", Country.ESP, calculateLaps(4675)),
    BRNO("Brno Circuit", Country.CZE, calculateLaps(5403)),
    CHANG("Chang International Circuit", Country.THA, calculateLaps(4554)),
    FUJI("Fuji Speedway", Country.JPN, calculateLaps(4563)),
    HERMANOS_RODRIGUEZ("Autódromo Hermanos Rodríguez", Country.MEX, calculateLaps(4304)),
    HUNGARORING("Hungaroring", Country.HUN, calculateLaps(4381)),
    IMOLA("Autodromo Internazionale Enzo e Dino Ferrari", Country.ITA, calculateLaps(4909)),
    INDIANAPOLIS("Indianapolis Grand Prix Course", Country.USA, calculateLaps(4023)),
    INTERLAGOS("Autódromo José Carlos Pace", Country.BRA, calculateLaps(4309)),
    KYALAMI("Kyalami Grand Prix Circuit", Country.ZAF, calculateLaps(4529)),
    LAS_VEGAS("Las Vegas Street Circuit", Country.USA, calculateLaps(6120)),
    LE_MANS_BUGATTI("Circuit Bugatti", Country.FRA, calculateLaps(4185)),
    MELBOURNE("Albert Park Circuit", Country.AUS, calculateLaps(5303)),
    MONACO("Circuit de Monaco", Country.MCO, calculateLaps(3337)),
    MONTREAL("Circuit Gilles Villeneuve", Country.CAN, calculateLaps(4361)),
    MONZA("Autodromo Nazionale Monza", Country.ITA, calculateLaps(5793)),
    MOST("Autodrom Most", Country.CZE, calculateLaps(4219)),
    MUGELLO("Mugello Circuit", Country.ITA, calculateLaps(5245)),
    NURBURGRING("Nürburgring", Country.DEU, calculateLaps(5148)),
    ROAD_AMERICA("Road America", Country.USA, calculateLaps(6515)),
    SAKHIR("Bahrain International Circuit", Country.BHR, calculateLaps(5412)),
    SAKHIR_OUTER("Bahrain Outer Circuit", Country.BHR, calculateLaps(3543)),
    SHANGHAI("Shanghai International Circuit", Country.CHN, calculateLaps(5451)),
    SHANGHAI_TAINMA("Shanghai Tainma Circuit", Country.CHN, calculateLaps(2063)),
    SILVERSTONE("Silverstone Circuit", Country.GBR, calculateLaps(5891)),
    SPA_FRANCORCHAMPS("Circuit de Spa-Francorchamps", Country.BEL, calculateLaps(7004)),
    SEPANG("Sepang International Circuit", Country.MYS, calculateLaps(5543)),
    SPIELBERG("Red Bull Ring", Country.AUT, calculateLaps(4318)),
    SURFERS_PARADISE("Surfers Paradise Circuit", Country.AUS, calculateLaps(3940)),
    SUZUKA("Suzuka International Racing Course", Country.JPN, calculateLaps(5807)),
    WATKINS_GLEN("Watkins Glen International", Country.USA, calculateLaps(5472)),
    ZANDVOORT("Circuit Zandvoort", Country.NLD, calculateLaps(4259)),
    ZHUHAI("Zhuhai International Circuit", Country.CHN, calculateLaps(4319));

    private final String name;
    private final Country country;
    private final int laps;

    private static final int RACE_DISTANCE = 140000;

    private static int calculateLaps(int lapLength) {
        return RACE_DISTANCE / lapLength;
    }
}
