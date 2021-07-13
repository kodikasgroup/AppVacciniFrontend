package com.kodikasgroup.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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

	public Citizen(String fiscalCode, Long cardNumber, String name, String surname, String birthPlace, LocalDate dob, String category) {
		this.fiscalCode = fiscalCode;
		this.cardNumber = cardNumber;
		this.name = name;
		this.surname = surname;
		this.birthPlace = birthPlace;
		this.dob = dob;
		this.category = category;
	}
}
