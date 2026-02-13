package com.github.matyassladek.ac_wgp.config.path;

import com.github.matyassladek.ac_wgp.app.PathConfiguration;
import com.github.matyassladek.ac_wgp.exception.NotImplemented;
import lombok.NoArgsConstructor;

import java.io.FileNotFoundException;

@NoArgsConstructor
public class DefaultPathConfiguartionService implements PathConfiguartionService {
    @Override
    public PathConfiguration load() throws FileNotFoundException {
        throw new NotImplemented();
    }
}
