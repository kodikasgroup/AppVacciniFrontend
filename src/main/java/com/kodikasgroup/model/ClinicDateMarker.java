package com.kodikasgroup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ClinicDateMarker {

    LocalDate datestart;
    LocalDate enddate;

    //Flag red 0 / green 1
    Byte flag ;


}
