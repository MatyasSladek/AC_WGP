package com.github.matyassladek.ac_wgp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eclipse.jdt.annotation.NonNullByDefault;

@AllArgsConstructor
@Getter
@NonNullByDefault
public enum Track {
    ALGARVE("Algarve International Circuit", Country.PRT, calculateLaps(4653), "algarve"),
    BARCELONA("Circuit de Barcelona-Catalunya", Country.ESP, calculateLaps(4675), "barcelona"),
    BRNO("Brno Circuit", Country.CZE, calculateLaps(5403), "brno"),
    BUDDH("Buddh International Circuit", Country.IND, calculateLaps(5073), "buddh"),
    CHANG("Chang International Circuit", Country.THA, calculateLaps(4554), "chang"),
    IMOLA("Autodromo Enzo e Dino Ferrari", Country.ITA, calculateLaps(4909), "imola"),
    INTERLAGOS("Autódromo José Carlos Pace", Country.BRA, calculateLaps(4309), "interlagos"),
    KYALAMI("Kyalami Grand Prix Circuit", Country.ZAF, calculateLaps(4529), "kyalami"),
    MELBOURNE("Albert Park Circuit", Country.AUS, calculateLaps(5303), "melbourne"),
    MONT_TREMBLANT("Circuit Mont-Tremblant", Country.CAN, calculateLaps(4266), "mont_tremblant"),
    MONTREAL("Circuit Gilles Villeneuve", Country.CAN, calculateLaps(4361), "montreal"),
    MONZA("Autodromo Nazionale Monza", Country.ITA, calculateLaps(5793), "monza"),
    MUGELLO("Mugello Circuit", Country.ITA, calculateLaps(5245), "mugello"),
    NURBURGRING("Nürburgring", Country.DEU, calculateLaps(5148), "nurburgring"),
    ROAD_AMERICA("Road America", Country.USA, calculateLaps(6515), "road_america"),
    SAKHIR("Bahrain International Circuit", Country.BHR, calculateLaps(5412), "sakhir"),
    SILVERSTONE("Silverstone Circuit", Country.GBR, calculateLaps(5891), "silverstone"),
    SPA_FRANCORCHAMPS("Circuit de Spa-Francorchamps", Country.BEL, calculateLaps(7004), "spa"),
    SEPANG("Sepang International Circuit", Country.MYS, calculateLaps(5543), "sepang"),
    SOKOL("Sokol International Racetrack", Country.KAZ, calculateLaps(4765), "sokol"),
    SPIELBERG("Spielberg Circuit", Country.AUT, calculateLaps(4326), "spielberg"),
    SUZUKA("Suzuka International Racing Course", Country.JPN, calculateLaps(5807), "suzuka"),
    SVEG("Sveg Raceway", Country.SWE, calculateLaps(4700), "sveg"),
    WATKINS_GLEN("Watkins Glen International", Country.USA, calculateLaps(5472), "watkins_glen"),
    ZANDVOORT("Circuit Zandvoort", Country.NLD, calculateLaps(4259), "zandvoort");

    private final String name;
    private final Country country;
    private final int laps;
    private final String acTrackId;

    private static final int RACE_DISTANCE = 170000;

    private static int calculateLaps(int lapLength) {
        return RACE_DISTANCE / lapLength + 1;
    }
}