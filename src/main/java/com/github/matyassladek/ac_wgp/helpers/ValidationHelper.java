package com.github.matyassladek.ac_wgp.helpers;

import javafx.scene.control.ChoiceBox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidationHelper {

    /**
     * Validates that all selected drivers in the list of ChoiceBoxes are unique.
     *
     * @param choiceBoxes List of ChoiceBoxes containing driver selections.
     * @return true if all selections are unique; false otherwise.
     */
    public static boolean validateUniqueSelections(List<ChoiceBox<String>> choiceBoxes) {
        Set<String> selectedDrivers = new HashSet<>();
        for (ChoiceBox<String> choiceBox : choiceBoxes) {
            String selectedDriver = choiceBox.getValue();
            if (selectedDriver != null && !selectedDrivers.add(selectedDriver)) {
                // Duplicate driver found
                return false;
            }
        }
        return true;
    }
}
