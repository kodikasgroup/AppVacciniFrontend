package com.kodikasgroup.controller;

import com.kodikasgroup.model.VaccinationCampaign;
import com.kodikasgroup.model.Vaccine;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;

public class InsertNewVaccinationCampaignController {
    @FXML private TableView<Vaccine> vaccineTable;
    @FXML private TextField issueNameField;


    public void cancel() throws IOException {
        setRoot("adminMainPage", 600, 400);
    }
    public void confirm() throws IOException {
        String issueName = issueNameField.getText();
        ObservableList<Vaccine> vaccines = vaccineTable.getItems();
        if (issueName.isEmpty() || vaccines.isEmpty()) {
            newWindow("popup", 300, 200);
        }
    }
    public void insertVaccine() throws IOException {
        setRoot("insertNewVaccine", 600, 160);
    }
}
