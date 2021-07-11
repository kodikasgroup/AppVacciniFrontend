package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodikasgroup.model.Citizen;
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

	@FXML private TextField fiscalCodeField;
	@FXML private TextField cardNumberField;
	@FXML private TextField nameField;
	@FXML private TextField surnameField;
	@FXML private DatePicker dobField;
	@FXML private MenuButton categoryField;
	@FXML private TextField placeOfBirthField;

	@FXML
	public void initialize() {
		objectMapper.registerModule(new JavaTimeModule());
		addListeners();
		Utils.setDatePickerDateFormat(dobField);
	}

	private void addListeners() {
		categoryField.getItems().forEach(
				menuItem -> menuItem.setOnAction(
						actionEvent -> categoryField.setText(
								menuItem.getText()
						)
				)
		);
	}

	private boolean areEmpty() {
		return fiscalCodeField.getText().isEmpty() ||
				cardNumberField.getText().isEmpty() ||
				nameField.getText().isEmpty() ||
				surnameField.getText().isEmpty() ||
				dobField.getValue() == null;
	}

	private boolean isValidCardNumber() {
		String cardNumber = cardNumberField.getText();
		String pattern = "^\\d{18}$";
		return Pattern.matches(pattern, cardNumber);
	}

	private boolean isValidData() {
		return !areEmpty() && isValidCardNumber() && isValidFiscalCode(fiscalCodeField.getText());
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
					Citizen insertedCitizen = getData();
					if (!citizen.equals(insertedCitizen)) {
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

	private Citizen getData() {
		return new Citizen(
				fiscalCodeField.getText(),
				Long.parseLong(cardNumberField.getText()),
				nameField.getText(),
				surnameField.getText(),
				placeOfBirthField.getText(),
				dobField.getValue(),
				categoryField.getText()
		);
	}
}
