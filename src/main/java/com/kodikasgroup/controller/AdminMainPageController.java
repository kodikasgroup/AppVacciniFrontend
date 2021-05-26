package com.kodikasgroup.controller;

import com.kodikasgroup.App;

import java.io.IOException;


public class AdminMainPageController {

    public void goToAddVaccine() throws IOException {
        App.setRoot("insertNewVaccine", 600, 160);
    }

    public void goToModifyVaccinationCampaing() {
    }

    public void goToAssociateVaccineCategory() {
    }

    public void goToAddVaccinationCampaign() throws IOException {
        App.setRoot("insertNewVaccinationCampaign", 720, 420);
    }
}
