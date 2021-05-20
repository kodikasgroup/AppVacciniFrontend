package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodikasgroup.model.Citizen;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.kodikasgroup.App.setRoot;
import static com.kodikasgroup.utils.RequestMaker.sendGET;
import static com.kodikasgroup.utils.Utils.isValidFiscalCode;

public class LoginController {
    private static final String CITIZEN_ENDPOINT = "/citizens";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @FXML private TextField inputField;
    @FXML private Text errorMessage;

    public LoginController() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void goToNextPage() {
        String text = inputField.getText();
        if (text.equals("admin")) {
            hideErrorMessage();
            // TODO GOTO ADMIN PAGE
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
            if (! response.equals("{}")) {
                System.out.println(response);
                Citizen citizen = objectMapper.readValue(response, Citizen.class);
                // check if user is registered
                if (citizen.isRegistered()){
                    // TODO go To Main Page
                } else {
                    setRoot("registration", 755, 300);
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
