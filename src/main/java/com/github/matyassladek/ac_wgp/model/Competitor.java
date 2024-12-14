package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
public abstract class Competitor {

    @JsonProperty("championshipPoints")
    private List<Optional<List<Integer>>> championshipPoints;

    protected Competitor() {}
}
