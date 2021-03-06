package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodikasgroup.App;
import com.kodikasgroup.model.Vaccine;
import com.kodikasgroup.utils.RequestMaker;
import com.kodikasgroup.utils.TempMemory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;

public class InsertNewVaccineController {
	private static final String VACCINE_ENDPOINT = "/vaccines";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private TempMemory tempMemory;

	@FXML private TextField vaccineNameField;
	@FXML private TextField vaccineQuantityField;
	@FXML private Button confirmButton;
	@FXML private Button annullaButton;

	@FXML
	public void initialize() {
		tempMemory = TempMemory.getINSTANCE();
	}

	private boolean areEmpty() {
		return vaccineNameField.getText().isEmpty() || vaccineQuantityField.getText().isEmpty();
	}

	private boolean isValidData() {
		String vaccineQuantity = vaccineQuantityField.getText();
		String pattern = "^\\d+$";
		return Pattern.matches(pattern, vaccineQuantity) && !areEmpty();
	}

	private void changeWindow() throws IOException {
		if (tempMemory.isFromVaccinationCampaignController()) {
			App.setRoot("insertNewVaccinationCampaign");
			Stage stage = (Stage) confirmButton.getScene().getWindow();
			stage.close();
		} else {
			setRoot("adminMainPage");
		}
	}

	public void cancel() throws IOException {
		Stage stage = (Stage) annullaButton.getScene().getWindow();
		stage.close();
	}

	private void sendData() throws IOException {
		Vaccine vaccine = new Vaccine(
				vaccineNameField.getText(),
				Long.parseLong(vaccineQuantityField.getText())
		);

		String response = RequestMaker.sendPOST(VACCINE_ENDPOINT, vaccine);
		vaccine = objectMapper.readValue(response, Vaccine.class);
		tempMemory.addVaccine(vaccine);
		changeWindow();
	}

	public void confirm() throws IOException {
		if (isValidData()) {
			sendData();
		} else {
			newWindow("popup", 300, 200);
		}
	}
}
