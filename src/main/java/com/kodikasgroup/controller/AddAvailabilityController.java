package com.kodikasgroup.controller;

import java.io.IOException;

import static com.kodikasgroup.App.setRoot;


// REGEX: https://stackoverflow.com/a/51177696
public class AddAvailabilityController {
	private void goBack() throws IOException {
		setRoot("adminMainPage", 600, 400);
	}

	public void cancel() throws IOException {
		goBack();
	}

	public void confirm() throws IOException {
		goBack();
	}
}
