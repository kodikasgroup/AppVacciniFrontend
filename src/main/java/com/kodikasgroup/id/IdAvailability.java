package com.kodikasgroup.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
public class IdAvailability implements Serializable {
    private String clinicName;
    private Long idVaccine;
}
