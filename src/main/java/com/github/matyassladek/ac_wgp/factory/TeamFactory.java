package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.DriverEnum;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.model.Manufacture;
import com.github.matyassladek.ac_wgp.model.Team;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TeamFactory {

    private final List<Team> teams;
    private int garage = 0;

    public TeamFactory(Driver player, Manufacture playerTeam) {
        this.teams = createTeamList(player, playerTeam);
    }

    private List<Team> createTeamList(Driver player, Manufacture playerTeam) {
        final List<Team> defaultTeams = teamInit();
        for (Team team : defaultTeams) {
            if (team.getManufacture().equals(playerTeam)) {
                team.setDriver2(player);
                return this.teams;
            }
        }
        return this.teams;
    }

    private List<Team> teamInit() {

        final List<Team> defaultTeams = new ArrayList<>();

        defaultTeams.add(new Team(Manufacture.FERRARI, ++garage,
                DriverEnum.ROMEO_MORBIDELLI.getDriver(),
                DriverEnum.ETHAN_SIMON.getDriver()));

        defaultTeams.add(new Team(Manufacture.MCLAREN, ++garage,
                DriverEnum.BRENT_HARRIS.getDriver(),
                DriverEnum.ARTHUR_HARDWICK.getDriver()));

        defaultTeams.add(new Team(Manufacture.RENAULT, ++garage,
                DriverEnum.EMILIO_CEVALLOS.getDriver(),
                DriverEnum.ANTOINE_LAVOIE.getDriver()));

        defaultTeams.add(new Team(Manufacture.MERCEDES, ++garage,
                DriverEnum.GERHARD_DRAGER.getDriver(),
                DriverEnum.NOAH_TANNER.getDriver()));

        defaultTeams.add(new Team(Manufacture.LOTUS, ++garage,
                DriverEnum.JACK_CAMPBELL.getDriver(),
                DriverEnum.ARTHUR_FERREIRA.getDriver()));

        defaultTeams.add(new Team(Manufacture.HONDA, ++garage,
                DriverEnum.DAVID_STARK.getDriver(),
                DriverEnum.JOZEF_KLOOSTERMAN.getDriver()));

        defaultTeams.add(new Team(Manufacture.ALFA_ROMEO, ++garage,
                DriverEnum.BRUNO_CAMPIONI.getDriver(),
                DriverEnum.MIKKO_VANHALA.getDriver()));

        defaultTeams.add(new Team(Manufacture.CHEVROLET, ++garage,
                DriverEnum.FELIPE_PINTO.getDriver(),
                DriverEnum.NAVEEN_DAGGUBATI.getDriver()));

        defaultTeams.add(new Team(Manufacture.JAGUAR, ++garage,
                DriverEnum.RUAIRI_FERRITER.getDriver(),
                DriverEnum.RICK_SWART.getDriver()));

        defaultTeams.add(new Team(Manufacture.PORSCHE, ++garage,
                DriverEnum.LUCAS_PEREYRA.getDriver(),
                DriverEnum.JAIME_OLIVEIRA.getDriver()));

        defaultTeams.add(new Team(Manufacture.TOYOTA, ++garage,
                DriverEnum.HIRO_IWATA.getDriver(),
                DriverEnum.MARCOS_PERALTA.getDriver()));

        defaultTeams.add(new Team(Manufacture.BMW, ++garage,
                DriverEnum.SEBASTIAN_DUDA.getDriver(),
                DriverEnum.SAMUEL_SCHMID.getDriver()));

    return defaultTeams;
    }
}
