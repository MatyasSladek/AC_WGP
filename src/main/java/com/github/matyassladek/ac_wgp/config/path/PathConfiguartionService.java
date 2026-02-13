package com.github.matyassladek.ac_wgp.config.path;

import com.github.matyassladek.ac_wgp.app.PathConfiguration;

import java.io.FileNotFoundException;

/**
 *
 */
public interface PathConfiguartionService {

    /**
     * Loads path configuration file if exists
     * @return path configuration
     * @throws FileNotFoundException
     */
    PathConfiguration load() throws FileNotFoundException;


}
