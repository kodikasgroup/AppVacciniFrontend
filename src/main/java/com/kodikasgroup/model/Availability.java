package com.kodikasgroup.model;
import com.kodikasgroup.id.IdAvailability;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Availability {
    Vaccine vaccine = null;
    IdAvailability availabilityId;
    //Date
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startHour;
    private LocalTime endHour;

    public Availability(String clinicName, Long idVaccine, LocalDate startDate,
                        LocalDate endDate, LocalTime startHour, LocalTime endHour) {
        this.availabilityId = new IdAvailability(clinicName,idVaccine);
        this.startDate = startDate;
        this.endDate = endDate;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Availability(String clinicName, Long idVaccine, LocalDate startDate,
                        LocalDate endDate, LocalTime startHour, LocalTime endHour,
                        Vaccine vaccine) {
        this.availabilityId = new IdAvailability(clinicName,idVaccine);
        this.startDate = startDate;
        this.endDate = endDate;
        this.startHour = startHour;
        this.endHour = endHour;

    }

}