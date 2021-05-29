package com.kodikasgroup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vaccine {

    private long vaccineID;
    private String vaccineName;
    private Long quantity;

    private Set<Availability> availabilities = new HashSet<>();


    public Vaccine(String vaccineName, long quantity) {
        this.vaccineName = vaccineName;
        this.quantity = quantity;
    }

    public Vaccine(long vaccineID, String vaccineName, long quantity) {
        this.vaccineID = vaccineID;
        this.vaccineName = vaccineName;
        this.quantity = quantity;
    }
}