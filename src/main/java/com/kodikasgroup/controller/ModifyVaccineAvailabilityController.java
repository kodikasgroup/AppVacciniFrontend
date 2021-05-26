package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodikasgroup.App;
import com.kodikasgroup.model.VaccineWrapper;
import com.kodikasgroup.utils.RequestMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static com.kodikasgroup.App.newWindow;

public class ModifyVaccineAvailabilityController {
    @FXML private TextField addAvailabilityField;
    @FXML private TextField actualAvailabilityField;
    @FXML private MenuButton vaccineMenuButton;
    @FXML private Button confirmButton;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static VaccineWrapper vaccineWrapper;
    private static final String VACCINE_ENDPOINT = "/vaccines";


    @FXML
    public void initialize() throws IOException {
        initializeVaccineMenuButton();
    }

    private void initializeVaccineMenuButton() throws IOException {
        AtomicReference<Integer> counter = new AtomicReference<>(0);
        String jsonString = RequestMaker.sendGET(VACCINE_ENDPOINT);
        vaccineWrapper = objectMapper.readValue(jsonString, VaccineWrapper.class);
        if (! vaccineWrapper.getVaccines().isEmpty()) {
            vaccineWrapper.getVaccines().forEach(vaccine -> vaccineMenuButton.getItems().add(
                    new MenuItem(
                            vaccine.getVaccineName()
                    )
            ));

            vaccineMenuButton.getItems().forEach(menuItem -> {
                menuItem.setOnAction(
                        actionEvent -> {
                            vaccineMenuButton.setText(menuItem.getText());
                            actualAvailabilityField.setText(
                                    vaccineWrapper.getVaccines()
                                            .get(
                                                    Integer.parseInt(menuItem.getId())
                                            )
                                            .getQuantity()
                                            .toString()
                            );
                        }
                );
                menuItem.setId(Integer.toString(counter.get()));
                counter.getAndSet(counter.get() + 1);
            });
            vaccineMenuButton.setText(vaccineMenuButton.getItems().get(0).getText());
            actualAvailabilityField.setText(vaccineWrapper.getVaccines().get(0).getQuantity().toString());
        } else {
            newWindow("popupNoVaccineAvailable", 300, 200);
            confirmButton.setDisable(true);
            vaccineMenuButton.setDisable(true);
        }
    }

    public void goBack() throws IOException {
        App.setRoot("adminMainPage", 600, 450);
    }

    public void cancel() throws IOException {
        goBack();
    }

    public void confirm() throws IOException {
        goBack();
    }
}
