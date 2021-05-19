package com.kodikasgroup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Citizen {
    private String fiscalCode;
    private Long cardNumber;
    private String name;
    private String surname;
    private String birthPlace;
    private LocalDate dob;
    private String category;
    private boolean registered = false;
}
