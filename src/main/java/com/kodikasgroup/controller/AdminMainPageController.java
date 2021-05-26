package com.kodikasgroup.controller;

import com.kodikasgroup.App;

import java.io.IOException;


public class AdminMainPageController {

    public void goToAddVaccine() throws IOException {
        App.setRoot("insertNewVaccine", 600, 160);
    }

    public void goToModifyVaccinationCampaign() throws IOException {
        App.setRoot("modifyVaccinationCampaignPage");
    }

    public void goToAssignVaccineCategory() throws IOException {
        App.setRoot("assignCategoryVaccine", 484, 160);
    }

    public void goToAddVaccinationCampaign() throws IOException {
        App.setRoot("insertNewVaccinationCampaign", 720, 420);
    }
}
