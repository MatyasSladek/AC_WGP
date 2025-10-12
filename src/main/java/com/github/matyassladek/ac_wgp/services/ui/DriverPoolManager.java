package com.github.matyassladek.ac_wgp.services.ui;

import com.github.matyassladek.ac_wgp.model.Driver;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

/**
 * Service for managing the driver pool GridPane.
 * Handles adding/removing drivers and grid layout.
 */
public class DriverPoolManager {

    private static final int MAX_ROWS_PER_COLUMN = 11;
    private static final int ROW_HEIGHT = 30;
    private static final int COLUMN_WIDTH = 150;
    private static final String DRIVER_STYLE = "-fx-border-color: black; -fx-background-color: lightgray; -fx-padding: 5px;";

    private final GridPane driversList;

    public DriverPoolManager(GridPane driversList) {
        this.driversList = driversList;
    }

    /**
     * Populates the grid with all drivers from the championship.
     *
     * @param drivers List of drivers
     */
    public void populateDriversList(List<Driver> drivers) {
        clearGrid();

        int row = 0;
        int col = 0;

        for (Driver driver : drivers) {
            Label driverLabel = createDriverLabel(driver.getName());
            driversList.add(driverLabel, col, row);

            row++;
            if (row >= MAX_ROWS_PER_COLUMN) {
                row = 0;
                col++;
            }
        }

        setupGridConstraints(row == 0 ? col : col + 1);
    }

    /**
     * Adds a driver back to the pool.
     *
     * @param driverName The driver name to add
     */
    public void addDriverToPool(String driverName) {
        // Check if driver already exists
        if (isDriverInPool(driverName)) {
            return;
        }

        Label driverLabel = createDriverLabel(driverName);
        addToNextAvailableCell(driverLabel);
    }

    /**
     * Removes a driver from the pool.
     *
     * @param driverName The driver name to remove
     */
    public void removeDriverFromPool(String driverName) {
        driversList.getChildren().removeIf(node ->
                node instanceof Label && ((Label) node).getText().equals(driverName));
    }

    /**
     * Checks if a driver is currently in the pool.
     *
     * @param driverName The driver name
     * @return true if driver is in pool
     */
    private boolean isDriverInPool(String driverName) {
        for (Node node : driversList.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                if (label.getText().equals(driverName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates a draggable driver label.
     *
     * @param driverName The driver name
     * @return Configured Label
     */
    private Label createDriverLabel(String driverName) {
        Label driverLabel = new Label(driverName);
        driverLabel.setId("driver-" + driverName);
        driverLabel.setStyle(DRIVER_STYLE);

        setupDragDetection(driverLabel);
        setupDragDone(driverLabel);

        return driverLabel;
    }

    private void setupDragDetection(Label driverLabel) {
        driverLabel.setOnDragDetected(event -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(driverLabel.getText());
            driverLabel.startDragAndDrop(TransferMode.MOVE).setContent(content);
            event.consume();
        });
    }

    private void setupDragDone(Label driverLabel) {
        driverLabel.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                driversList.getChildren().remove(driverLabel);
            }
            event.consume();
        });
    }

    /**
     * Adds a label to the next available cell in the grid.
     * Expands grid if necessary.
     */
    private void addToNextAvailableCell(Label driverLabel) {
        int rows = driversList.getRowConstraints().size();
        int cols = driversList.getColumnConstraints().size();

        // Find first empty cell
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!isCellOccupied(row, col)) {
                    driversList.add(driverLabel, col, row);
                    return;
                }
            }
        }

        // No empty cell found - expand grid
        if (rows < MAX_ROWS_PER_COLUMN) {
            addRow();
            driversList.add(driverLabel, 0, rows);
        } else {
            addColumn();
            driversList.add(driverLabel, cols, 0);
        }
    }

    /**
     * Checks if a grid cell is occupied.
     */
    private boolean isCellOccupied(int row, int col) {
        for (Node node : driversList.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);

            if (nodeRow != null && nodeCol != null && nodeRow == row && nodeCol == col) {
                return true;
            }
        }
        return false;
    }

    private void clearGrid() {
        driversList.getChildren().clear();
        driversList.getRowConstraints().clear();
        driversList.getColumnConstraints().clear();
    }

    private void setupGridConstraints(int columns) {
        for (int i = 0; i < MAX_ROWS_PER_COLUMN; i++) {
            RowConstraints rowConstraint = new RowConstraints(ROW_HEIGHT);
            driversList.getRowConstraints().add(rowConstraint);
        }

        for (int i = 0; i < columns; i++) {
            ColumnConstraints columnConstraint = new ColumnConstraints(COLUMN_WIDTH);
            driversList.getColumnConstraints().add(columnConstraint);
        }
    }

    private void addRow() {
        RowConstraints newRow = new RowConstraints(ROW_HEIGHT);
        driversList.getRowConstraints().add(newRow);
    }

    private void addColumn() {
        ColumnConstraints newColumn = new ColumnConstraints(COLUMN_WIDTH);
        driversList.getColumnConstraints().add(newColumn);
    }
}