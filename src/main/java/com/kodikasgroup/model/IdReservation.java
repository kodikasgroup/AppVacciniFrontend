package com.kodikasgroup.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IdReservation implements  Serializable{
    private Long idVaccine;
    private String fiscalCode;
}
