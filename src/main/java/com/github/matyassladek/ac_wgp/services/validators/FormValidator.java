package com.github.matyassladek.ac_wgp.services.validators;

import com.github.matyassladek.ac_wgp.utils.UIHelper;
import com.github.matyassladek.ac_wgp.model.DriverFormData;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Service for validating form data.
 * Provides centralized validation logic with user feedback.
 */
public class FormValidator {

    private final AcInstallationValidator acValidator;

    public FormValidator() {
        this(new AcInstallationValidator());
    }

    // Constructor injection for testing
    FormValidator(AcInstallationValidator acValidator) {
        this.acValidator = acValidator;
    }

    /**
     * Validates all driver form data including required fields and paths.
     * Shows appropriate error messages to the user.
     *
     * @param formData The form data to validate
     * @param owner The window that owns alert dialogs
     * @return true if all validation passes, false otherwise
     */
    public boolean validateDriverForm(DriverFormData formData, Window owner) {
        if (!validateRequiredFields(formData, owner)) {
            return false;
        }

        if (!validatePaths(formData, owner)) {
            return false;
        }

        if (!validateJsonFile(formData.getJsonPath(), owner)) {
            return false;
        }

        if (!validateAcDirectory(formData.getAcGamePath(), owner)) {
            return false;
        }

        return true;
    }

    private boolean validateRequiredFields(DriverFormData formData, Window owner) {
        if (formData.hasEmptyRequiredFields()) {
            UIHelper.showAlert("Missing Information",
                    "Please fill out all required fields.", owner);
            return false;
        }
        return true;
    }

    private boolean validatePaths(DriverFormData formData, Window owner) {
        if (formData.hasEmptyPaths()) {
            if (formData.getJsonPath() == null || formData.getJsonPath().isEmpty()) {
                UIHelper.showAlert("Missing JSON Path",
                        "Please select a JSON results file path.", owner);
            } else {
                UIHelper.showAlert("Missing AC Path",
                        "Please select the Assetto Corsa game directory.", owner);
            }
            return false;
        }
        return true;
    }

    private boolean validateJsonFile(String jsonPath, Window owner) {
        if (!Files.exists(Paths.get(jsonPath))) {
            UIHelper.showAlert("Invalid JSON Path",
                    "The selected JSON file does not exist.", owner);
            return false;
        }
        return true;
    }

    private boolean validateAcDirectory(String acGamePath, Window owner) {
        File acDir = new File(acGamePath);
        if (!acDir.exists() || !acValidator.isValidInstallation(acDir)) {
            UIHelper.showAlert("Invalid AC Path",
                    "The selected Assetto Corsa directory is not valid.", owner);
            return false;
        }
        return true;
    }
}