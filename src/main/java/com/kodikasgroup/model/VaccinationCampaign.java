package com.kodikasgroup.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
}