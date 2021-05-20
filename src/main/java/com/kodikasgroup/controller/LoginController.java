package com.kodikasgroup.controller;

import com.google.gson.Gson;
import com.kodikasgroup.model.Citizen;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.kodikasgroup.utils.RequestMaker.sendGET;
import static com.kodikasgroup.utils.Utils.isValidFiscalCode;

public class LoginController {
    private static final String CITIZEN_ENDPOINT = "/citizens";

    @FXML private TextField inputField;
    @FXML private Text errorMessage;

    public void goToNextPage() {
        String text = inputField.getText();
        if (text.equals("admin")) {
            hideErrorMessage();
            // TODO GOTO ADMIN PAGE
        } else if (text.equals("no vax")){
            // TODO MANDA A FANCULO
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
                Citizen citizen = new Gson().fromJson(response, Citizen.class);
                // check if user is registered
                if (citizen.isRegistered()){
                    // TODO go To Main Page
                } else {
                    // TODO go To Registration Page
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
