package com.kodikasgroup.controller;

import java.io.File;
import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodikasgroup.model.Availability;
import com.kodikasgroup.utils.RequestMaker;
import com.kodikasgroup.wrapper.AvailabilityWrapper;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Window;

public class ViewAvailabilityController {
    private static final String AVAILABILITY_ENDPOINT = "/availability";
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @FXML private ListView listviewclinic ;

    private AvailabilityWrapper response ;

    private static Scene scene;

    @FXML
    public void initialize () throws IOException {

        //Only 4 testing
        Long idVaccine = 2341244L;

        response = getAll(idVaccine);
         for (Availability entity:response.getAvailability()){
             listviewclinic.getItems().addAll(entity.getAvailabilityId().getClinicName());
         }
    }


    private AvailabilityWrapper getAll (long idVaccine) throws IOException {

        String data  = RequestMaker.sendGET(AVAILABILITY_ENDPOINT+"/"+idVaccine);

        AvailabilityWrapper result = objectMapper.readValue(data,AvailabilityWrapper.class);

        return result;
    }

    public void listViewBUttonPushed() throws IOException {

        String selected = (String) listviewclinic.getSelectionModel().getSelectedItem();

       nextPage(selected);
    }

    private void nextPage (String select) throws IOException {
        List<Availability> selection = null;
        for (Availability entity : response.getAvailability()){

            if (entity.getAvailabilityId().getClinicName()==select){
                if(selection== null){
                    selection = List.of(entity);
                }
                else
                    selection.add(entity);
            }
        }
        setRoot("availabilitydate");
    }
}
