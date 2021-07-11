package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodikasgroup.model.Citizen;
import com.kodikasgroup.utils.RequestMaker;
import com.kodikasgroup.utils.UserTempMemory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class doneController {

    @FXML
    Label labelPlace;
    @FXML
    Label cflabel;
    @FXML
    Label datelabel;
    @FXML
    Label labelcampaign;
    @FXML
    Button confirmButton;

    UserTempMemory userTempMemory;
    private static final String CITIZEN_ENDPOINT = "/citizens";
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @FXML
    public void initialize() throws IOException {

        userTempMemory = UserTempMemory.getINSTANCE();

        labelPlace.setText(userTempMemory.getClinicname());
        String jsonString = RequestMaker.sendGET(CITIZEN_ENDPOINT + "/" + userTempMemory.getFiscalcode());
        Citizen citizen = objectMapper.readValue(jsonString, Citizen.class);
        cflabel.setText(citizen.getSurname()+" "+ citizen.getName()+" - "+userTempMemory.getFiscalcode());
        datelabel.setText("Data: " + userTempMemory.getLocalDate()+ " alle " + userTempMemory.getLocalTime());
    }

    @FXML
    private void onClickConfirme() throws IOException {
        //setRoot("adminMainPage", 600, 400)

        // get a handle to the stage
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void onCLickBackLogin(ActionEvent actionEvent) {
    }
}
