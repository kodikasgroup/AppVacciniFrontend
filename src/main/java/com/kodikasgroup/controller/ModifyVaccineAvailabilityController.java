package com.kodikasgroup.controller;

import com.kodikasgroup.App;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ModifyVaccineAvailabilityController {
    public void goBack() throws IOException {
        App.setRoot("adminMainPage", 600, 450);
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        goBack();
    }

    public void confirm(ActionEvent actionEvent) throws IOException {
        goBack();
    }
}
