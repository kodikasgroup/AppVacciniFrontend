package com.kodikasgroup.controller;

import com.kodikasgroup.model.Vaccine;
import com.kodikasgroup.utils.TempMemory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;

public class InsertNewVaccinationCampaignController {
    @FXML private TableView<Vaccine> vaccineTable;
    @FXML private TextField issueNameField;
    private TempMemory tempMemory;

    @FXML
    public void initialize() {
        tempMemory = TempMemory.getINSTANCE();
    }

    private void goBack() throws IOException {
        tempMemory.setFromVaccinationCampaignController(true);
        setRoot("adminMainPage", 600, 400);
    }

    public void cancel() throws IOException {
        goBack();
    }

    public void confirm() throws IOException {
        String issueName = issueNameField.getText();
        ObservableList<Vaccine> vaccines = vaccineTable.getItems();
        if (issueName.isEmpty() || vaccines.isEmpty()) {
            newWindow("popup", 300, 200);
        } else {
            goBack();
        }
    }

    public void insertVaccine() throws IOException {
        tempMemory.setFromVaccinationCampaignController(true);
        setRoot("insertNewVaccine", 600, 160);
    }
}
