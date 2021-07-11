package com.kodikasgroup.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodikasgroup.model.*;
import com.kodikasgroup.utils.RequestMaker;
import com.kodikasgroup.utils.UserTempMemory;
import com.kodikasgroup.wrapper.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;

public class CampaignController {

    private static final String Campaign_ENDPOINT = "/vaccinationCampaign";
    private static final String CITIZEN_ENDPOINT = "/citizens";
    private static final String ENTITLED_ENDPOINT = "/entitled";
    private static final String VACCINE_ENDPOINT = "/vaccines";
    private static final String RESERVATION_ENDPOINT = "/reservations";
    private static final String NOTIFICATIONS_ENDPOINT = "/notifications";
    private static final String AVAILABILITY_ENDPOINT = "/availability";
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private UserTempMemory userTempMemory;

    String DEFAULT_CONTROL_INNER_BACKGROUND = "derive(-fx-base,80%)";
    String ACTIVE_CONTROL_INNER_BACKGROUND = " #0e5b92";
    String DISABLED_CONTROL_INNER_BACKGROUND = " #9EBDD3";

    @FXML
    ListView viewcampaign ;


    Set<VaccinationCampaign> availabelecampain;
    Set<VaccinationCampaign> deactivatescampain;

    @FXML
    public void initialize() throws IOException {


        userTempMemory = UserTempMemory.getINSTANCE();
        viewall();
        notificationCheck();
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
                            } else if (campaigndeactivates(item)) {
                                setText(item.toString());
                                setStyle("-fx-font-weight: bold;" + "-fx-alignment: center ;" + "-fx-font-size: 18 ;" + "-fx-control-inner-background: " + DISABLED_CONTROL_INNER_BACKGROUND + ";");
                            }else
                             {
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
       // String jsonString2 = RequestMaker.sendGET(ENTITLED_ENDPOINT);
        String jsonString2 = RequestMaker.sendGET(ENTITLED_ENDPOINT + "/" + URLEncoder.encode(citizen.getCategory(), "UTF-8"));
        EntitledWrapper entitledWrapper = objectMapper.readValue(jsonString2, EntitledWrapper.class);

        Set<Long> vaccineID = new LinkedHashSet<>();

        //assert entitledWrapper.getEntitles() != null;
        for (Entitled entry : entitledWrapper.getEntitles()){
            vaccineID.add((Long)entry.getVaccine().getVaccineID());
        }

        Set<VaccinationCampaign> allcampaign = new LinkedHashSet<>();
        Set<VaccinationCampaign> reservationcampaign = new LinkedHashSet<>();
        List<Long> idreservationVaccine =reservationcheck();
        //List di Campagna
        //assert vaccineID != null;
        for(Long id : vaccineID){
            jsonString = RequestMaker.sendGET(VACCINE_ENDPOINT+ "/campaign/" + id);
            VaccinationCampaign campaign = objectMapper.readValue(jsonString, VaccinationCampaign.class);
            if(idreservationVaccine != null && idreservationVaccine.contains(id)){
                reservationcampaign.add(campaign);
            }
            allcampaign.add(campaign);
        }
        availabelecampain =  allcampaign;
        deactivatescampain = reservationcampaign;
    }

    private List<Long> reservationcheck() throws IOException {
        String jsonString2 = RequestMaker.sendGET(RESERVATION_ENDPOINT+"/"+userTempMemory.getFiscalcode());
        ReservationWrapper reservationWrapperWrapper = objectMapper.readValue(jsonString2, ReservationWrapper.class);

        List<Long> reservationids = new ArrayList<>();
        if(reservationWrapperWrapper!=null &&  reservationWrapperWrapper.getReservations() != null) {
            for (Reservation obj : reservationWrapperWrapper.getReservations()) {
                reservationids.add(obj.getReservationId().getIdVaccine());
            }
            return reservationids;
        }
        return null;
    }

    private boolean campaigndeactivates(String name){
        if(deactivatescampain != null) {
            for (VaccinationCampaign obj : deactivatescampain) {
                if (name.equals(obj.getDiseaseName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void onClickConfirm() throws IOException {
        String choice = (String) viewcampaign.getSelectionModel().getSelectedItem();

        if (choice == null || choice.equals("Al momento non risultano campagne attive")) {
            //TODO: ERROR POPUP uno o piu cambi non sembrano selezionati
            newWindow("unselectedfieldsERROR", 300, 200);
        } else if (campaigndeactivates(choice)){
            //TODO: ERROR POP UP gia prenotato
            newWindow("alreadyreserved", 300, 200);
        }else{
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


    private void notificationCheck() throws IOException {
        String jsonString = RequestMaker.sendGET(NOTIFICATIONS_ENDPOINT+"/"+userTempMemory.getFiscalcode());
        List<Notification> notifications = objectMapper.readValue(jsonString, new TypeReference<List<Notification>>(){});
        Set<Long> idcampaignnotify = new HashSet<>();
        String campaignname = "Nuove Disponibilitá in : ";

        if(!notifications.isEmpty()) {
            for (Notification notification : notifications) {
                if (newAvailanility(notification)) {
                    idcampaignnotify.add(notification.getIdNotification().getCampaignId());
                    RequestMaker.sendDELETE(NOTIFICATIONS_ENDPOINT+"/"+notification,null);
                }
            }
            if(!idcampaignnotify.isEmpty()) {
                for (VaccinationCampaign entry : availabelecampain) {
                    for (Long id : idcampaignnotify)
                        if (entry.getCampaignID() == id) {
                            campaignname = campaignname + entry.getDiseaseName();
                        }
                }
                userTempMemory.setNewAvailabilityNotify(campaignname);
                newWindow("notifypopup",300,200);
            }
        }
    }

    private boolean newAvailanility(Notification notification) throws IOException {
        for(VaccinationCampaign entry : availabelecampain){
            if(notification.getIdNotification().getCampaignId() == entry.getCampaignID()){
                AvailabilityWrapper availabilityWrapper = getAllIdvaccines(getIdVaccines(entry));

                for (Availability obj : availabilityWrapper.getAvailability()) {
                    if (notification.getStartDate().isAfter(obj.getStartDate().minusDays(1)) && notification.getEndDate().isBefore(obj.getEndDate().plusDays(1)))
                        return true;
                }
            }
        }
        return false;
    }

    public VaccineIdWrapper getIdVaccines(VaccinationCampaign campaign) {

        List<Vaccine> vaccines = List.copyOf(campaign.getVaccines());
            if (vaccines.isEmpty()) {
                return null;
            }

        List<Long> ids = new ArrayList<>();
        for (Vaccine vaccine : vaccines){
            ids.add(vaccine.getVaccineID());
        }
        return new VaccineIdWrapper(ids);
    }
    private AvailabilityWrapper getAllIdvaccines(VaccineIdWrapper ids) throws IOException {
        String data = RequestMaker.sendGET(AVAILABILITY_ENDPOINT + "/idvaccine" + "?ids=" , ids);
        AvailabilityWrapper result = objectMapper.readValue(data, AvailabilityWrapper.class);
        return result;
    }
}
