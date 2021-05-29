package com.kodikasgroup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vaccine {
    private long vaccineID;
    private String vaccineName;
    private Long quantity;
//    private Set<Availability> availabilities = new HashSet<>();
//    private Set<Entitled> entitleds = new HashSet<>();

    public Vaccine(String vaccineName, Long quantity) {
        this.vaccineName = vaccineName;
        this.quantity = quantity;
    }
}