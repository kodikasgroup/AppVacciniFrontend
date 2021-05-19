package com.kodikasgroup.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class LoginController {
    @FXML
    private TextField inputField;

    public void goToNextPage() {
        String text = inputField.getText();
        if (text.equals("admin")) {
            // TODO GOTO ADMIN PAGE
        } else if (text.equals("no vax")){
            // TODO MANDA A FANCULO
        } else {
            if (checkFiscalCode(text)) {
                goToUserPage();
            } else {
            }
        }
    }

    private void goToUserPage() {
        // TODO check if user already registered
    }

    private boolean checkFiscalCode(String text) {
        String pattern = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$";
        return Pattern.matches(pattern, text);
    }
}
