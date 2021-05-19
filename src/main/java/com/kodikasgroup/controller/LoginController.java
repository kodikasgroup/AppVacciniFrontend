package com.kodikasgroup.controller;

import com.google.gson.Gson;
import com.kodikasgroup.model.Citizen;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.regex.Pattern;

import static com.kodikasgroup.utils.RequestMaker.sendGET;

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
            if (checkFiscalCode(text)) {
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

    private boolean checkFiscalCode(String text) {
        String pattern = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$";
        return Pattern.matches(pattern, text);
    }

    private void showErrorMessage() {
        errorMessage.setVisible(true);
    }

    private void hideErrorMessage() {
        errorMessage.setVisible(false);
    }
}
