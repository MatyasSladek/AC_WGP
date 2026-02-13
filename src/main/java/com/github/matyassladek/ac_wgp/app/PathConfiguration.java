package com.github.matyassladek.ac_wgp.app;

/**
 * This record class stores configuration of path for game folder and path for champ folder.
 * Usually, the game folder is in C:\\Program Files (x86)\\Steam\\steamapps\\common\\assettocorsa
 * and the user folder is in C:\\Users\\username\\Documents\\Assetto Corsa
 */
public record PathConfiguration (String gameFolderPath, String userFolderPath) {}
