package com.kodikasgroup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IdAvailability {
	private String clinicName;
	private Long idVaccine;

	public IdAvailability(String clinicName) {
		this.clinicName = clinicName;
	}
}
