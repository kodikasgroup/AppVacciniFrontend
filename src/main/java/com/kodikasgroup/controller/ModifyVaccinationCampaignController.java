package com.kodikasgroup.controller;

import com.kodikasgroup.App;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ModifyVaccinationCampaignController {

    public void goBack() throws IOException {
        App.setRoot("adminMainPage");
    }

    public void goToModifyVaccinationCampaignTimetables() {
    }

    public void goToModifyVaccineAvailability() throws IOException {
        App.setRoot("modifyVaccineAvailability", 484, 250);
    }

    public void goToAddClinic() {
    }
}
