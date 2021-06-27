package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodikasgroup.model.*;
import com.kodikasgroup.utils.RequestMaker;
import com.kodikasgroup.utils.UserTempMemory;
import com.kodikasgroup.wrapper.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;

import static com.kodikasgroup.App.setRoot;

public class CampaignController {

    private static final String Campaign_ENDPOINT = "/vaccinationCampaign";
    private static final String CITIZEN_ENDPOINT = "/citizens";
    private static final String ENTITLED_ENDPOINT = "/entitled";
    private static final String VACCINE_ENDPOINT = "/vaccines";
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private UserTempMemory userTempMemory;

    String DEFAULT_CONTROL_INNER_BACKGROUND = "derive(-fx-base,80%)";
    String ACTIVE_CONTROL_INNER_BACKGROUND = " #0e5b92";

    @FXML
    ListView viewcampaign ;


    Set<VaccinationCampaign> availabelecampain;

    @FXML
    public void initialize() throws IOException {


        userTempMemory = UserTempMemory.getINSTANCE();
        viewall();
    }
    private void viewall() throws IOException {

        getallCampaign();
        if(!(availabelecampain.isEmpty())) {
            for (VaccinationCampaign entry : availabelecampain) {
                viewcampaign.getItems().addAll(entry.getDiseaseName());
            }
            viewcampaign.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> param) {
                    return new ListCell<String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty) {
                                setText(null);
                                setStyle("-fx-control-inner-background: " + DEFAULT_CONTROL_INNER_BACKGROUND + ";");
                            } else {
                                setText(item.toString());
                                setStyle("-fx-font-weight: bold;" + "-fx-alignment: center ;" + "-fx-font-size: 18 ;" + "-fx-control-inner-background: " + ACTIVE_CONTROL_INNER_BACKGROUND + ";");

                            }

                        }
                    };
                }
            });
        }
        else
        {
            viewcampaign.getItems().addAll("Al momento non risultano campagne attive");
        }
    }


    private void getallCampaign() throws IOException {
        String jsonString = RequestMaker.sendGET(CITIZEN_ENDPOINT + "/" + userTempMemory.getFiscalcode());
        Citizen citizen = objectMapper.readValue(jsonString, Citizen.class);

        //Todo: da sistemare !!!!!!!!
        //SEE non va dove deve
        String jsonString2 = RequestMaker.sendGET(ENTITLED_ENDPOINT);
        EntitledWrapper entitledWrapper = objectMapper.readValue(jsonString2, EntitledWrapper.class);

        Set<Long> vaccineID = new LinkedHashSet<>();

        //assert entitledWrapper.getEntitles() != null;
        for (Entitled entry : entitledWrapper.getEntitles()){
            vaccineID.add((Long)entry.getVaccine().getVaccineID());
        }

        Set<VaccinationCampaign> allcampaign = new LinkedHashSet<>();
        //List di Campagna
        //assert vaccineID != null;
        for(Long id : vaccineID){
            jsonString = RequestMaker.sendGET(VACCINE_ENDPOINT+ "/campaign/" + id);
            VaccinationCampaign campaign = objectMapper.readValue(jsonString, VaccinationCampaign.class);
            allcampaign.add(campaign);
        }
        availabelecampain =  allcampaign;
    }

    public void onClickConfirm() throws IOException {
        //TODO: check confirm not null save idcampaign in temp memory
        String choice = (String)viewcampaign.getSelectionModel().getSelectedItem();

        if(choice == null || choice.equals("Al momento non risultano campagne attive")){
            //TODO: EROR POPUP
        }
        else {
            for (VaccinationCampaign obj : availabelecampain) {
                if (obj.getDiseaseName().equals(choice))
                    userTempMemory.setCampaign(obj);
            }
            setRoot("availabilitypage");
        }
    }

    public void onClickBack() throws IOException {
        setRoot("login");
    }


}
