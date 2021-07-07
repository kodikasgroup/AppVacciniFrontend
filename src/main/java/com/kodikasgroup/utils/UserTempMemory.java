package com.kodikasgroup.utils;

import com.kodikasgroup.model.Availability;
import com.kodikasgroup.model.VaccinationCampaign;
import com.kodikasgroup.model.Vaccine;
import com.kodikasgroup.wrapper.AvailabilityWrapper;
import com.kodikasgroup.wrapper.VaccineIdWrapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UserTempMemory {

    private String fiscalcode;
    private VaccinationCampaign campaign;
    private List<Vaccine> vaccines = new ArrayList<>();
    private List<Availability> availability = new ArrayList<>();
    private String clinicname ;
    private LocalDate localDate;
    private LocalTime localTime;
    private static UserTempMemory INSTANCE;

    private UserTempMemory(){}

    public static UserTempMemory getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new UserTempMemory();
        }
        return INSTANCE;
    }

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public List<Vaccine> getVaccines (){
        if(vaccines.isEmpty())
            vaccines = List.copyOf(campaign.getVaccines());
        return vaccines;
    }

    public VaccineIdWrapper getIdVaccines() {
        if(vaccines.isEmpty()) {
            vaccines = List.copyOf(campaign.getVaccines());
            if (vaccines.isEmpty()) {
                return null;
            }
        }
       List<Long> ids = new ArrayList<>();
        for (Vaccine vaccine : vaccines){
            ids.add(vaccine.getVaccineID());
        }
        return new VaccineIdWrapper(ids);
    }

    public void setCampaign(VaccinationCampaign campaign) {
        this.campaign = campaign;
    }

    public String getFiscalcode() {
        return fiscalcode;
    }

    public void setFiscalcode(String fiscalcode) {
        this.fiscalcode = fiscalcode;
    }

    public List<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }
    public void addAvailability(Availability availability){
        this.availability.add(availability);
    }

    public void resetAvailability(){
        this.availability = new ArrayList<>();
    }
}
