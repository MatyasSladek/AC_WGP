package com.github.matyassladek.ac_wgp.helpers;

import com.github.matyassladek.ac_wgp.model.Driver;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UIHelper {

    public static void populateChoiceBoxes(List<ChoiceBox<String>> choiceBoxes, List<Driver> drivers) {
        for (int i = 0; i < choiceBoxes.size(); i++) {
            ChoiceBox<String> box = choiceBoxes.get(i);
            box.getItems().clear();
            drivers.forEach(driver -> box.getItems().add(driver.getName()));
            if (i < drivers.size()) box.setValue(drivers.get(i).getName());
        }
    }

    public static boolean validateUniqueSelections(List<ChoiceBox<String>> choiceBoxes) {
        Set<String> selected = new HashSet<>();
        for (ChoiceBox<String> box : choiceBoxes) {
            if (box.getValue() != null && !selected.add(box.getValue())) {
                return false;
            }
        }
        return true;
    }

    public static List<String> getSelectedValues(List<ChoiceBox<String>> choiceBoxes) {
        return choiceBoxes.stream()
                .map(ChoiceBox::getValue)
                .toList();
    }

    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
