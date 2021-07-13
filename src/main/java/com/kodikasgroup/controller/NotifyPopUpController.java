package com.kodikasgroup.controller;

import com.kodikasgroup.utils.UserTempMemory;
import com.kodikasgroup.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class NotifyPopUpController {

    @FXML
    private Button closeButton;
    @FXML
    private Text notifystring;
    @FXML private Label nametitle;

    UserTempMemory userTempMemory;

    @FXML
    public void initialize() throws IOException {
        userTempMemory = UserTempMemory.getINSTANCE();
        if(userTempMemory.getNewAvailabilityNotify().contains("Nuove")){
            notifystring.setText(userTempMemory.getNewAvailabilityNotify());
        }
        else
        {
            nametitle.setText("Registrazione Avvenuta con successo ");
            notifystring.setText(userTempMemory.getNewAvailabilityNotify());
        }

    }
    public void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
