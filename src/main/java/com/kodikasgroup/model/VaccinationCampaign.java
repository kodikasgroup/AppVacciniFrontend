package com.kodikasgroup.model;


import lombok.*;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class VaccinationCampaign {
    private long campaignID;
    private String diseaseName;
    private Set<Vaccine> vaccines = new HashSet<>();

    public VaccinationCampaign(String diseaseName, Set<Vaccine> vaccines) {
        this.diseaseName = diseaseName;
        this.vaccines = vaccines;
    }
    @Override
    public int hashCode() {
        int hash = 10;
        hash = 32 * hash + (int)this.campaignID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof VaccinationCampaign && ((VaccinationCampaign) obj).campaignID == this.campaignID);

    }
}