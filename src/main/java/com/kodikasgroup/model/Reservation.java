package com.kodikasgroup.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Reservation {
    private String fiscalCode;
    private String clinicName;
    private Long idVaccine;
    private LocalDate  date;
    private LocalTime  time;
}
