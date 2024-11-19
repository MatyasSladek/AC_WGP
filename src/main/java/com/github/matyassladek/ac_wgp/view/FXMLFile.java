package com.github.matyassladek.ac_wgp.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FXMLFile {

    CREATE_DRIVER("view/create-driver.fxml"),
    NEXT_EVENT("view/next-event.fxml"),
    EVENT_RESULTS("view/event-results.fxml"),
    DRIVERS_STANDINGS("view/drivers-standings.fxml"),
    CONSTRUCTORS_STANDINGS("view/constructors-standings.fxml");

    private final String fileName;
}
