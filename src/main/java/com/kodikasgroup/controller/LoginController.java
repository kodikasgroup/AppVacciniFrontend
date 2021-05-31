package com.kodikasgroup.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodikasgroup.model.Citizen;
import com.kodikasgroup.utils.RequestMaker;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;
import static com.kodikasgroup.utils.RequestMaker.sendGET;
import static com.kodikasgroup.utils.Utils.isValidFiscalCode;

public class LoginController {
	private static final String CITIZEN_ENDPOINT = "/citizens";
	private static final String ENTITLED_ENDPOINT = "/entitled";
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@FXML private TextField inputField;
	@FXML private Text errorMessage;

	public LoginController() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	public void goToNextPage() throws IOException {
		String text = inputField.getText();
		if (text.equals("admin")) {
			hideErrorMessage();
			setRoot("adminMainPage", 600, 400);
		} else if (text.equals("no vax")){
			// TODO EASTEREGG
		} else {
			if (isValidFiscalCode(text)) {
				hideErrorMessage();
				goToUserPage(text);
			} else {
				showErrorMessage();
			}
		}
	}

	private void goToUserPage(String text) {
		try {
			String response = sendGET(CITIZEN_ENDPOINT +"/"+text);
			String respoonse1 = RequestMaker.sendGET(ENTITLED_ENDPOINT+"/categories");
			Set<String> categories = objectMapper.readValue(respoonse1, new TypeReference<>() {});
			if (! response.equals("{}")) {
				Citizen citizen = objectMapper.readValue(response, Citizen.class);
				if (!categories.contains(citizen.getCategory())) {
					newWindow("popupCitizenNotInCategory", 300, 200);
				} else {
					// check if user is registered
					if (citizen.isRegistered()){
						// TODO go To Main Page
					} else {
						setRoot("registration", 755, 400);
					}
				}
			} else {
				showErrorMessage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showErrorMessage() {
		errorMessage.setVisible(true);
	}

	private void hideErrorMessage() {
		errorMessage.setVisible(false);
	}
}
