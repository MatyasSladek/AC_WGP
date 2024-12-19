package com.github.matyassladek.ac_wgp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CareerEndController extends ViewController {

    @FXML
    private Label text;

    public CareerEndController() {
        super(FXMLFile.CAREER_END.getFileName());
    }
}
