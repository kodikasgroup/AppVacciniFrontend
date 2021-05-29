package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodikasgroup.model.Entitled;
import com.kodikasgroup.model.Vaccine;
import com.kodikasgroup.model.VaccineWrapper;
import com.kodikasgroup.utils.RequestMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.Optional;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;

public class AssignCategoryVaccineController {
    @FXML private MenuButton vaccineMenuButton;
    @FXML private MenuButton categoryMenuButton;
    @FXML private Button confirmButton;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ENTITLED_ENDPOINT = "/entitled";
    private static final String VACCINE_ENDPOINT = "/vaccines";
    private static VaccineWrapper vaccineWrapper;

    @FXML
    public void initialize() throws IOException {
        categoryMenuButton.getItems().forEach(menuItem -> menuItem.setOnAction(
                actionEvent -> categoryMenuButton.setText(
                        menuItem.getText()
                )
        ));
        initializeVaccineMenuButton();
    }

    private void initializeVaccineMenuButton() throws IOException {
        String jsonString = RequestMaker.sendGET(VACCINE_ENDPOINT);
        vaccineWrapper = objectMapper.readValue(jsonString, VaccineWrapper.class);
        if (! vaccineWrapper.getVaccines().isEmpty()) {
            vaccineWrapper.getVaccines().forEach(vaccine -> vaccineMenuButton.getItems().add(
                    new MenuItem(
                            vaccine.getVaccineName()
                    )
            ));
            vaccineMenuButton.getItems().forEach(menuItem -> menuItem.setOnAction(
                    actionEvent -> vaccineMenuButton.setText(
                            menuItem.getText()
                    )
            ));
            vaccineMenuButton.setText(vaccineMenuButton.getItems().get(0).getText());
        } else {
            newWindow("popupNoVaccineAvailable", 300, 200);
            confirmButton.setDisable(true);
            vaccineMenuButton.setDisable(true);
        }
    }

    private void goBack() throws IOException {
        setRoot("adminMainPage", 600, 400);
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        goBack();
    }

    public void confirm(ActionEvent actionEvent) throws IOException {
        Optional<Vaccine> optionalVaccine = vaccineWrapper.getVaccines().stream()
                .filter(vaccine -> vaccine.getVaccineName().equals(vaccineMenuButton.getText()))
                .findFirst();
        if (optionalVaccine.isPresent()) {
            Entitled entitled = new Entitled(categoryMenuButton.getText(), optionalVaccine.get());
            RequestMaker.sendPOST(ENTITLED_ENDPOINT, entitled);
        }
        goBack();
    }
}
