package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodikasgroup.model.Citizen;
import com.kodikasgroup.model.IdNotification;
import com.kodikasgroup.model.Notification;
import com.kodikasgroup.utils.RequestMaker;
import com.kodikasgroup.utils.UserTempMemory;
import com.kodikasgroup.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static com.kodikasgroup.App.setRoot;

public class NotifyController {

    @FXML private DatePicker startDateField;
    @FXML private DatePicker endDateField;
    @FXML private Button confirmButton;
    @FXML private Button backButton;
    @FXML private Text errorMessage;

    UserTempMemory userTempMemory;
    private static final String NOTIFICATIONS_ENDPOINT = "/notifications";
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @FXML
    public void initialize() throws IOException {
        Utils.setDatePickerDateFormat(startDateField);
        Utils.setDatePickerDateFormat(endDateField);
        userTempMemory = UserTempMemory.getINSTANCE();
    }
    private boolean isValidData() {
        if(startDateField.getValue() != null && endDateField.getValue() != null)
            return startDateField.getValue().isBefore(endDateField.getValue());
        return false;
    }

    private Notification getData(){
        return new Notification( new IdNotification
                (userTempMemory.getCampaignId(),
                userTempMemory.getFiscalcode()),
                startDateField.getValue(),
                endDateField.getValue());
    }

    public void onClickConfirme() throws IOException {
        if(isValidData()){
            Notification notification = getData();
            RequestMaker.sendPOST(NOTIFICATIONS_ENDPOINT,notification);

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            setRoot("login");
            stage.close();
        }
        else
        {
            showErrorMessage();
        }
    }

    public void onCLickBackLogin() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        setRoot("login");
        stage.close();
    }

    private void showErrorMessage() {
        errorMessage.setVisible(true);
    }
}
