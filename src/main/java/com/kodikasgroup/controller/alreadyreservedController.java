package com.kodikasgroup.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class alreadyreservedController {
	@FXML private Button closeButton;

	public void closeWindow() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
}
