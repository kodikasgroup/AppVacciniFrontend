package com.kodikasgroup.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

import static com.kodikasgroup.App.setRoot;

public class addAvailabilityController {
	private void goBack() throws IOException {
		setRoot("adminMainPage", 600, 400);
	}

	public void cancel(ActionEvent actionEvent) throws IOException {
		goBack();
	}

	public void confirm(ActionEvent actionEvent) throws IOException {
		goBack();
	}
}
