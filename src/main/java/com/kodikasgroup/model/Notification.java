package com.kodikasgroup.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

	private IdNotification idNotification;
	private LocalDate startDate;
	private LocalDate endDate;
}