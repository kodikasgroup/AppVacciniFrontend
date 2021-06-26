package com.kodikasgroup.utils;

import com.kodikasgroup.model.Availability;
import com.kodikasgroup.model.VaccinationCampaign;
import com.kodikasgroup.model.Vaccine;
import com.kodikasgroup.wrapper.VaccineIdWrapper;

import java.util.ArrayList;
import java.util.List;

public class UserTempMemory {

    private String fiscalcode;
    private VaccinationCampaign campaign;
    private List<Vaccine> vaccines = new ArrayList<>();
    private String clinicName ;
    private Availability availability;

    private boolean fromVaccinationCampaignController = false;
    private static final UserTempMemory INSTANCE = new UserTempMemory();

    private UserTempMemory(){}

    public static UserTempMemory getINSTANCE() {
        return INSTANCE;
    }

    public List<Vaccine> getVaccines() {
        return vaccines;
    }

    public void addVaccine(Vaccine vaccine) {
        vaccines.add(vaccine);
    }

    public VaccineIdWrapper getIdVaccines() {
        vaccines = List.copyOf(campaign.getVaccines());
        if (vaccines.isEmpty()){
            return null;
        }
       List<Long> ids = new ArrayList<>();
        for (Vaccine vaccine : vaccines){
            ids.add(vaccine.getVaccineID());
        }


        return new VaccineIdWrapper(ids);
    }

    public void resetVaccines() {
        vaccines = new ArrayList<>();
    }

    public boolean isFromVaccinationCampaignController() {
        return fromVaccinationCampaignController;
    }

    public void setFromVaccinationCampaignController(boolean fromVaccinationCampaignController) {
        this.fromVaccinationCampaignController = fromVaccinationCampaignController;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public VaccinationCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(VaccinationCampaign campaign) {
        this.campaign = campaign;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public String getFiscalcode() {
        return fiscalcode;
    }

    public void setFiscalcode(String fiscalcode) {
        this.fiscalcode = fiscalcode;
    }

    public void setVaccines(List<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }
}
