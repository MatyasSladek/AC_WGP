package com.github.matyassladek.ac_wgp.services.ui;

import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;

import java.util.List;
import java.util.function.Consumer;

/**
 * Service for managing drag-and-drop functionality for race results.
 * Handles drag detection, drop events, and visual feedback.
 */
public class DragDropManager {

    private static final String EMPTY_TEXT = "Drop Here";
    private static final String DEFAULT_STYLE = "-fx-border-color: gray; -fx-background-color: #f0f0f0;";
    private static final String HOVER_STYLE = "-fx-border-color: green; -fx-background-color: #e0ffe0;";

    private final Consumer<String> onDriverAdded;
    private final Consumer<String> onDriverRemoved;

    public DragDropManager(Consumer<String> onDriverAdded, Consumer<String> onDriverRemoved) {
        this.onDriverAdded = onDriverAdded;
        this.onDriverRemoved = onDriverRemoved;
    }

    /**
     * Initializes drag-and-drop functionality for all drop target labels.
     *
     * @param dropTargets List of labels representing finishing positions
     */
    public void initializeDropTargets(List<Label> dropTargets) {
        for (Label dropTarget : dropTargets) {
            setupDropTarget(dropTarget);
        }
    }

    /**
     * Sets up a single drop target label with drag-and-drop handlers.
     */
    private void setupDropTarget(Label dropTarget) {
        setupDragOver(dropTarget);
        setupDragDropped(dropTarget);
        setupDragEntered(dropTarget);
        setupDragExited(dropTarget);
        setupDragDetected(dropTarget);
    }

    private void setupDragOver(Label dropTarget) {
        dropTarget.setOnDragOver(event -> {
            if (event.getGestureSource() != dropTarget && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
    }

    private void setupDragDropped(Label dropTarget) {
        dropTarget.setOnDragDropped(event -> {
            if (event.getDragboard().hasString()) {
                String driverName = event.getDragboard().getString();

                // If position is occupied, return that driver to pool
                if (!isEmpty(dropTarget)) {
                    onDriverAdded.accept(dropTarget.getText());
                }

                // Place new driver in position
                dropTarget.setText(driverName);
                onDriverRemoved.accept(driverName);

                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
    }

    private void setupDragEntered(Label dropTarget) {
        dropTarget.setOnDragEntered(event -> {
            if (event.getDragboard().hasString()) {
                dropTarget.setStyle(HOVER_STYLE);
            }
            event.consume();
        });
    }

    private void setupDragExited(Label dropTarget) {
        dropTarget.setOnDragExited(event -> {
            dropTarget.setStyle(DEFAULT_STYLE);
            event.consume();
        });
    }

    private void setupDragDetected(Label dropTarget) {
        dropTarget.setOnDragDetected(event -> {
            if (!isEmpty(dropTarget)) {
                // Start drag operation
                ClipboardContent content = new ClipboardContent();
                content.putString(dropTarget.getText());
                dropTarget.startDragAndDrop(TransferMode.MOVE).setContent(content);

                // Remove driver from position and return to pool
                String removedDriver = dropTarget.getText();
                dropTarget.setText(EMPTY_TEXT);
                onDriverAdded.accept(removedDriver);

                event.consume();
            }
        });
    }

    /**
     * Clears all drop targets and returns drivers to pool.
     *
     * @param dropTargets List of drop target labels
     */
    public void clearAllTargets(List<Label> dropTargets) {
        for (Label dropTarget : dropTargets) {
            if (!isEmpty(dropTarget)) {
                onDriverAdded.accept(dropTarget.getText());
                dropTarget.setText(EMPTY_TEXT);
            }
        }
    }

    /**
     * Sets results in drop targets and removes drivers from pool.
     *
     * @param dropTargets List of drop target labels
     * @param results List of driver names in finishing order
     */
    public void setResults(List<Label> dropTargets, List<String> results) {
        int maxSize = Math.min(dropTargets.size(), results.size());

        for (int i = 0; i < maxSize; i++) {
            String driverName = results.get(i);
            dropTargets.get(i).setText(driverName);
            onDriverRemoved.accept(driverName);
        }
    }

    /**
     * Gets the current results from drop targets.
     *
     * @param dropTargets List of drop target labels
     * @return List of driver names in finishing order, or null if incomplete
     */
    public List<String> getResults(List<Label> dropTargets) {
        List<String> results = new java.util.ArrayList<>();

        for (Label dropTarget : dropTargets) {
            if (isEmpty(dropTarget)) {
                return null; // Incomplete results
            }
            results.add(dropTarget.getText());
        }

        return results;
    }

    private boolean isEmpty(Label dropTarget) {
        String text = dropTarget.getText();
        return text == null || text.equals(EMPTY_TEXT);
    }
}