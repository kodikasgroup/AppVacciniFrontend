package com.kodikasgroup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vaccine {

    private long vaccineID;
    private String vaccineName;
    private Long quantity;
    private VaccinationCampaign vaccinationCampaign;
//    private Set<Availability> availabilities = new HashSet<>();
//    private Set<Entitled> entitleds = new HashSet<>();
}