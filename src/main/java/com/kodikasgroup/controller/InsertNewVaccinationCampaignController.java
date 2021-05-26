package com.kodikasgroup.controller;

import com.kodikasgroup.App;
import com.kodikasgroup.model.VaccinationCampaign;
import com.kodikasgroup.model.Vaccine;
import com.kodikasgroup.utils.RequestMaker;
import com.kodikasgroup.utils.TempMemory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;

public class InsertNewVaccinationCampaignController {
    @FXML
    private TableView<Vaccine> vaccineTable;
    @FXML
    private TextField issueNameField;
    @FXML
    private TableColumn<Vaccine, Long> idVaccinoColumn;
    @FXML
    private TableColumn<Vaccine, String> nomeVaccinoColumn;
    @FXML
    private TableColumn<Vaccine, Long> quantitaColumn;
    private TempMemory tempMemory;
    private static final String VACCINATION_CAMPAIGN_ENDPOINT = "/vaccinationCampaign";

    private void initializeColumns() {
        idVaccinoColumn.setCellValueFactory(new PropertyValueFactory<>("vaccineID"));
        nomeVaccinoColumn.setCellValueFactory(new PropertyValueFactory<>("vaccineName"));
        quantitaColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @FXML
    public void initialize() {
        tempMemory = TempMemory.getINSTANCE();
        List<Vaccine> vaccines = tempMemory.getVaccines();
        initializeColumns();
        if (!vaccines.isEmpty()) {
            vaccines.forEach(vaccine -> vaccineTable.getItems().add(vaccine));
        }
    }

    private void goBack() throws IOException {
        tempMemory.setFromVaccinationCampaignController(false);
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
            tempMemory.setFromVaccinationCampaignController(false);
            tempMemory.resetVaccines();
            VaccinationCampaign vaccinationCampaign = new VaccinationCampaign(
                    issueName,
                    new HashSet<>(vaccines)
            );
            RequestMaker.sendPOST(VACCINATION_CAMPAIGN_ENDPOINT, vaccinationCampaign);
            goBack();
        }
    }

    public void insertVaccine() throws IOException {
        tempMemory.setFromVaccinationCampaignController(true);
        setRoot("insertNewVaccine", 600, 160);
    }
}
