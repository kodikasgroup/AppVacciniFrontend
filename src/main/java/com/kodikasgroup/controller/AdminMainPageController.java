package com.kodikasgroup.controller;

import com.kodikasgroup.App;

import java.io.IOException;

public class AdminMainPageController {

    public void goToAddVaccine() {
    }

    public void goToModifyVaccinationCampaing() {
    }

    public void goToAssociateVaccineCategory() {
    }

    public void goToAddVaccinationCampaign() throws IOException {
        App.setRoot("insertNewVaccinationCampaign", 720, 420);
    }
}
