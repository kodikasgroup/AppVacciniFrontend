package com.kodikasgroup.controller;

import java.io.IOException;
import java.util.Set;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodikasgroup.model.Availability;
import com.kodikasgroup.model.Vaccine;
import com.kodikasgroup.utils.RequestMaker;
import com.kodikasgroup.utils.UserTempMemory;
import com.kodikasgroup.wrapper.AvailabilityWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ViewAvailabilityController {
    private static final String AVAILABILITY_ENDPOINT = "availability";
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @FXML
    private ListView listviewclinic;

    private AvailabilityWrapper availabilityWrapper;

    String DEFAULT_CONTROL_INNER_BACKGROUND = "derive(-fx-base,80%)";
    String ACTIVE_CONTROL_INNER_BACKGROUND = " #0e5b92";
    String DESACTIVED_CONTROL_INNER_BACKGROUND = "derive(#0e5b92,60%)";


    UserTempMemory userTempMemory;
    Set<Vaccine> vaccines;

    @FXML
    public void initialize() throws IOException {

        //optain istance temp memory
        userTempMemory = UserTempMemory.getINSTANCE();

        //Save only
        availabilityWrapper = getAllIdvaccines();

        if(availabilityWrapper.getAvailability() != null) {
            for (Availability entity : availabilityWrapper.getAvailability()) {
                listviewclinic.getItems().addAll(entity.getAvailabilityId().getClinicName());
            }
            listviewclinic.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
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
                                setStyle("-fx-font-weight: bold;" + "-fx-alignment: center ;" + "-fx-control-inner-background: " + ACTIVE_CONTROL_INNER_BACKGROUND + ";");

                            }

                        }
                    };
                }
            });
        }
        else{
            listviewclinic.getItems().addAll("Al momento non risultano sedi disponibili");
        }
    }


    private AvailabilityWrapper getAllIdvaccines() throws IOException {
        String data = RequestMaker.sendGET(AVAILABILITY_ENDPOINT + "/idvaccine" + "?ids=" , userTempMemory.getIdVaccines());
        AvailabilityWrapper result = objectMapper.readValue(data, AvailabilityWrapper.class);
        return result;
    }

    public void listViewBUttonPushed() throws IOException {

        String selected = (String) listviewclinic.getSelectionModel().getSelectedItem();

        if(selected  == null || selected.equals("Al momento non risultano sedi disponibili")){
            //TODO: EROR POPUP
            newWindow("unselectedfieldsERROR", 300, 200);
        }
        else {
            nextPage(selected);
        }
    }

    private void nextPage(String select) throws IOException {
        userTempMemory.resetAvailability();
        for (Availability entity : availabilityWrapper.getAvailability()) {
            if (entity.getAvailabilityId().getClinicName() == select) {
                userTempMemory.addAvailability(entity);
            }
        }
        setRoot("availabilitydate");
    }

    public void backPage() throws IOException {
        setRoot("campaign");
    }
}