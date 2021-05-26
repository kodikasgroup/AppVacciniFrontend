package com.kodikasgroup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Entitled {
    private long entitledId;
    private String category;
    private Vaccine vaccine;

    public Entitled(String category, Vaccine vaccine) {
        this.category = category;
        this.vaccine = vaccine;
    }
}
