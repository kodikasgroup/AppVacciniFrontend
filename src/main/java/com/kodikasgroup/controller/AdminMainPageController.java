package com.kodikasgroup.controller;

import com.kodikasgroup.App;
import com.kodikasgroup.utils.TempMemory;
import javafx.stage.Window;

import java.io.IOException;

import static com.kodikasgroup.App.newWindow;

//TODO: MODIFY insertNewVaccinationCampaign ALLOWING user TO SELECT EXISTING VACCINES
public class AdminMainPageController {

	public void goToAddVaccine() throws IOException {
		App.setRoot("insertNewVaccine", 600, 160);
	}

	public void goToAssignVaccineCategory() throws IOException {
		//App.setRoot("assignCategoryVaccine", 484, 160);
		newWindow("assignCategoryVaccine", 484, 160);
	}

	public void goToAddVaccinationCampaign() throws IOException {
		TempMemory.getINSTANCE().resetVaccines();
//		App.setRoot("insertNewVaccinationCampaign");
		newWindow("insertNewVaccinationCampaign", 550, 480);
//		App.setRoot("insertNewVaccinationCampaign", 720, 420);
	}

	public void goToModifyVaccineAvailability() throws IOException {
		//App.setRoot("modifyVaccineAvailability", 484, 250);
		newWindow("modifyVaccineAvailability", 570, 350);
	}

	public void goToAddAvailabilityPage() throws IOException {
		//App.setRoot("addAvailabilityPage", 520, 450);
		newWindow("addAvailabilityPage", 520, 450);
	}

	public void goBack() throws IOException {
		App.setRoot("login");
	}
}