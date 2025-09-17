package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.DriverEnum;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.model.Team;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class TeamFactory {

    private int garage = 0;

    public List<Team> createTeamList(Driver player, Manufacture playerTeam) {
        List<Team> teams = teamInit();
        for (Team team : teams) {
            if (team.getManufacture().equals(playerTeam)) {
                team.setDriver2(player);
                return teams;
            }
        }
        return teams;
    }

    private List<Team> teamInit() {

        List<Team> defaultTeams = new ArrayList<>();

        defaultTeams.add(new Team(Manufacture.BWT, ++garage,
                DriverEnum.SAMUEL_SCHMID.getDriver(),
                DriverEnum.MARCOS_PERALTA.getDriver()));

        defaultTeams.add(new Team(Manufacture.CASTROL, ++garage,
                DriverEnum.FREDERIC_GASCOIGNE.getDriver(),
                DriverEnum.BRUNO_CAMPIONI.getDriver()));

        defaultTeams.add(new Team(Manufacture.EM, ++garage,
                DriverEnum.JOERI_KAANDORP.getDriver(),
                DriverEnum.JAN_VALENTA.getDriver()));

        defaultTeams.add(new Team(Manufacture.GULF, ++garage,
                DriverEnum.LUCAS_PEREYRA.getDriver(),
                DriverEnum.DAVID_STARK.getDriver()));

        defaultTeams.add(new Team(Manufacture.JPS, ++garage,
                DriverEnum.RICK_SWART.getDriver(),
                DriverEnum.ANTOINE_LAVOIE.getDriver()));

        defaultTeams.add(new Team(Manufacture.MARTINI, ++garage,
                DriverEnum.FELIPE_PINTO.getDriver(),
                DriverEnum.MIKKO_VANHALA.getDriver()));

        defaultTeams.add(new Team(Manufacture.NEWMAN, ++garage,
                DriverEnum.HAKARU_KOJIMA.getDriver(),
                DriverEnum.EMILIO_CEVALLOS.getDriver()));

        defaultTeams.add(new Team(Manufacture.RBR, ++garage,
                DriverEnum.FRANZ_HERMANN.getDriver(),
                DriverEnum.BRENT_HARRIS.getDriver()));

        defaultTeams.add(new Team(Manufacture.TARGET, ++garage,
                DriverEnum.DIEGO_CABRERA.getDriver(),
                DriverEnum.JACK_CAMPBELL.getDriver()));

        defaultTeams.add(new Team(Manufacture.VODAFONE, ++garage,
                DriverEnum.JAIME_OLIVEIRA.getDriver(),
                DriverEnum.NOAH_TANNER.getDriver()));

        return defaultTeams;
    }
}
