package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Championship.DriverSlot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.io.IOException;
import java.util.logging.Logger;

public class DriverStandingsController extends ViewController {

    private static final Logger log = Logger.getLogger(DriverStandingsController.class.getName());

    @FXML
    private TableView<DriverSlot> standingsTable;

    @FXML
    private TableColumn<DriverSlot, Integer> positionColumn;

    @FXML
    private TableColumn<DriverSlot, String> flagColumn;

    @FXML
    private TableColumn<DriverSlot, String> driverColumn;

    @FXML
    private TableColumn<DriverSlot, String> teamColumn;

    @FXML
    private TableColumn<DriverSlot, Integer> pointsColumn;

    @Override
    public void setGame(Game game) {
        this.game = game;
        loadDriverStandings();
    }

    public DriverStandingsController() {
        super(FXMLFile.CONSTRUCTORS_STANDINGS.getFileName());
    }

    public void initialize() {
        positionColumn.setCellValueFactory(data -> {
            int position = standingsTable.getItems().indexOf(data.getValue()) + 1;
            return new javafx.beans.property.SimpleIntegerProperty(position).asObject();
        });

        flagColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDriver().getCountry().getFlag())
        );

        // Custom TableCell for SVG rendering
        flagColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String flagPath, boolean empty) {
                super.updateItem(flagPath, empty);
                if (empty || flagPath == null || flagPath.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        Image flagImage = convertSVGToImage(flagPath);
                        imageView.setImage(flagImage);
                        imageView.setFitWidth(30); // Adjust width
                        imageView.setFitHeight(20); // Adjust height
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(null);
                        e.printStackTrace();
                    }
                }
            }
        });

        driverColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDriver().getName()));
        teamColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTeam().getManufacture().getNameShort()));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
    }

    private Image convertSVGToImage(String svgPath) throws TranscoderException {
        InputStream svgInputStream = getClass().getResourceAsStream(svgPath);
        if (svgInputStream == null) {
            throw new IllegalArgumentException("SVG file not found: " + svgPath);
        }

        PNGTranscoder transcoder = new PNGTranscoder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TranscoderInput input = new TranscoderInput(svgInputStream);
        TranscoderOutput output = new TranscoderOutput(outputStream);

        transcoder.transcode(input, output);

        return new Image(new ByteArrayInputStream(outputStream.toByteArray()));
    }

    private void loadDriverStandings() {
        if (game != null) {
            ObservableList<DriverSlot> standings = FXCollections.observableArrayList(game.getCurrentChampionship().getDriversStandings());
            standingsTable.setItems(standings);
        }
    }

    @FXML
    private void onContinueButtonClick() throws IOException {
        log.info("Continue button clicked!");
        showNextScreen();
    }
}
