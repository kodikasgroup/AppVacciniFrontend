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
    IdReservation reservationId;
    private String clinicName;
    private LocalDate  date;
    private LocalTime  time;
}
