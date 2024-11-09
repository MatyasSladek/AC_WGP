package com.github.matyassladek.ac_wgp.ac_wgp.model;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@Setter
@Getter
@NonNullByDefault
public class Race {
    private List<Driver> result;
}
