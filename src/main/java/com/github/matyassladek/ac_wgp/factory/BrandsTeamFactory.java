package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.DriverEnum;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.model.Team;

import java.util.ArrayList;
import java.util.List;

public class BrandsTeamFactory extends TeamFactory {

    @Override
    protected List<Team> teamInit() {

        List<Team> defaultTeams = new ArrayList<>();

        defaultTeams.add(new Team(Manufacture.BWT, ++garage,
                DriverEnum.SAMUEL_SCHMID.getDriver(),
                DriverEnum.MARCOS_PERALTA.getDriver()));

        defaultTeams.add(new Team(Manufacture.CASTROL, ++garage,
                DriverEnum.FREDERIC_GASCOIGNE.getDriver(),
                DriverEnum.BRUNO_CAMPIONI.getDriver()));

        defaultTeams.add(new Team(Manufacture.EM, ++garage,
                DriverEnum.FELIPE_PINTO.getDriver(),
                DriverEnum.JAN_VALENTA.getDriver()));

        defaultTeams.add(new Team(Manufacture.FALKEN, ++garage,
                DriverEnum.HAKARU_KOJIMA.getDriver(),
                DriverEnum.JOHNATHAN_KAANDORP.getDriver()));

        defaultTeams.add(new Team(Manufacture.GULF, ++garage,
                DriverEnum.LUCAS_PEREYRA.getDriver(),
                DriverEnum.DAVID_STARK.getDriver()));

        defaultTeams.add(new Team(Manufacture.JPS, ++garage,
                DriverEnum.RUAIRI_FERRITER.getDriver(),
                DriverEnum.ANTOINE_LAVOIE.getDriver()));

        defaultTeams.add(new Team(Manufacture.MARTINI, ++garage,
                DriverEnum.ARTHUR_HARDWICK.getDriver(),
                DriverEnum.MIKKO_VANHALA.getDriver()));

        defaultTeams.add(new Team(Manufacture.MOBIL1, ++garage,
                DriverEnum.RICK_SWART.getDriver(),
                DriverEnum.EMILIO_CEVALLOS.getDriver()));

        defaultTeams.add(new Team(Manufacture.RBR, ++garage,
                DriverEnum.GERHARD_DRAGER.getDriver(),
                DriverEnum.BRENT_HARRIS.getDriver()));

        defaultTeams.add(new Team(Manufacture.TARGET, ++garage,
                DriverEnum.RYAN_DAYSON.getDriver(),
                DriverEnum.JACK_CAMPBELL.getDriver()));

        defaultTeams.add(new Team(Manufacture.VODAFONE, ++garage,
                DriverEnum.JAIME_OLIVEIRA.getDriver(),
                DriverEnum.NOAH_TANNER.getDriver()));

        return defaultTeams;
    }
}
