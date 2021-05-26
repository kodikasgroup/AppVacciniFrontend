package com.kodikasgroup.model;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Availability {
   IdAvailability availabilityId;
    // Date
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startHour;
    private LocalTime endHour;
    private Vaccine vaccine;
}