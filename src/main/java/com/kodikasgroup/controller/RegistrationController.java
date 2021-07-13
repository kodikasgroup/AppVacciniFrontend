package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodikasgroup.model.Citizen;
import com.kodikasgroup.utils.UserTempMemory;
import com.kodikasgroup.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;
import static com.kodikasgroup.utils.RequestMaker.sendGET;
import static com.kodikasgroup.utils.RequestMaker.sendPUT;
import static com.kodikasgroup.utils.Utils.isValidFiscalCode;

public class RegistrationController {
	private static final String CITIZEN_ENDPOINT = "/citizens";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	UserTempMemory userTempMemory;
	@FXML
	private TextField fiscalCodeField;
	@FXML
	private TextField nameField;
	@FXML
	private TextField surnameField;

	public void initialize() {
		userTempMemory = UserTempMemory.getINSTANCE();
		objectMapper.registerModule(new JavaTimeModule());
	}

	private boolean areEmpty() {
		return fiscalCodeField.getText().isEmpty() ||
				nameField.getText().isEmpty() ||
				surnameField.getText().isEmpty();
	}

	private boolean isValidData() {
		return !areEmpty() && isValidFiscalCode(fiscalCodeField.getText());
	}

	public void goToNextPage() throws IOException {
		if (!isValidData()) {
			newWindow("popup", 300, 200);
		} else {
			// check if user is registered
			String fiscalCode = fiscalCodeField.getText();
			try {
				String response = sendGET(CITIZEN_ENDPOINT + "/" + fiscalCode);
				if (!response.equals("{}")) {
					// set registered to true
					response = sendPUT(CITIZEN_ENDPOINT + "/registered/" + fiscalCode, null);
					Citizen citizen = objectMapper.readValue(response, Citizen.class);
					String name = nameField.getText();
					String surname = surnameField.getText();
					if (!citizen.getName().equals(name) || !citizen.getSurname().equals(surname)) {
						newWindow("popup", 300, 200);
					} else {
						userTempMemory.setNewAvailabilityNotify("La tua cattegoria é : " + citizen.getCategory());
						newWindow("notifypopup", 300, 200);
						setRoot("login");
					}
				} else {
					setRoot("anomalia");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
