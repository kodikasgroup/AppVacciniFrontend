package com.kodikasgroup.controller;

import com.google.gson.Gson;
import com.kodikasgroup.model.Citizen;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

import static com.kodikasgroup.App.setRoot;
import static com.kodikasgroup.utils.RequestMaker.sendGET;
import static com.kodikasgroup.utils.RequestMaker.sendPUT;
import static com.kodikasgroup.utils.Utils.isValidFiscalCode;
import static com.kodikasgroup.App.newWindow;

public class RegistrationController {
    private static final String CITIZEN_ENDPOINT = "/citizens";

    @FXML private TextField fiscalCodeField;
    @FXML private TextField cardNumberField;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private DatePicker dobField;
    @FXML private MenuButton categoryField;

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
            newWindow("popup");
        } else {
            // check if user is registered
            String fiscalCode = fiscalCodeField.getText();
            try {
                String response = sendGET(CITIZEN_ENDPOINT +"/"+fiscalCode);
                if (! response.equals("{}")) {
                    // set registered to true
                    response = sendPUT(CITIZEN_ENDPOINT+"/registered/"+fiscalCode, null);
                    Citizen citizen = new Gson().fromJson(response, Citizen.class);
                } else {
                    setRoot("anomalia");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
