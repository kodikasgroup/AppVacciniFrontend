package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodikasgroup.model.Availability;
import com.kodikasgroup.model.IdAvailability;
import com.kodikasgroup.model.Vaccine;
import com.kodikasgroup.model.VaccineWrapper;
import com.kodikasgroup.utils.RequestMaker;
import com.kodikasgroup.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;


public class AvailabilityController {
	@FXML private MenuButton vaccineMenuButton;
	@FXML private TextField startHourField;
	@FXML private TextField endHourField;
	@FXML private DatePicker startDateField;
	@FXML private DatePicker endDateField;
	@FXML private TextField clinicNameField;
	@FXML private Button confirmButton;
	@FXML private AnchorPane anchorpane;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static VaccineWrapper vaccineWrapper;
	private static final String VACCINE_ENDPOINT = "/vaccines";
	private static final String AVAILABILITY_ENDPOINT = "/availability";

	private void initializeVaccineMenuButton() throws IOException {
		AtomicReference<Integer> counter = new AtomicReference<>(0);
		String jsonString = RequestMaker.sendGET(VACCINE_ENDPOINT);
		vaccineWrapper = objectMapper.readValue(jsonString, VaccineWrapper.class);
		if (! vaccineWrapper.getVaccines().isEmpty()) {
			vaccineWrapper.getVaccines().forEach(vaccine -> vaccineMenuButton.getItems().add(
					new MenuItem(
							vaccine.getVaccineName()
					)
			));

			vaccineMenuButton.getItems().forEach(menuItem -> {
				menuItem.setOnAction(
						actionEvent -> vaccineMenuButton.setText(menuItem.getText())
				);
				menuItem.setId(Integer.toString(counter.get()));
				counter.getAndSet(counter.get() + 1);
			});
			vaccineMenuButton.setText(vaccineMenuButton.getItems().get(0).getText());
		} else {
			Stage stage = (Stage) anchorpane.getScene().getWindow();
			newWindow("popupNoVaccineAvailable", 300, 200);
			stage.close();
			//confirmButton.setDisable(true);
			//vaccineMenuButton.setDisable(true);
		}
	}

	private boolean isValidTime(TextField field) {
		String content = field.getText();
		String pattern = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
		return !content.isEmpty() && Pattern.matches(pattern, content);
	}

	private boolean isValidData() {
		boolean validTime = isValidTime(startHourField) && isValidTime(endHourField);
		boolean dateNotNull = startDateField.getValue() != null && endDateField.getValue() != null;
		boolean clinicNotEmpty = ! clinicNameField.getText().isEmpty();
		boolean validDate = startDateField.getValue().isBefore(endDateField.getValue());
		return validTime && validDate && dateNotNull && clinicNotEmpty;
	}

	@FXML
	private void initialize() throws IOException {
		Utils.setDatePickerDateFormat(startDateField);
		Utils.setDatePickerDateFormat(endDateField);
		initializeVaccineMenuButton();
	}

	private void goBack() throws IOException {
		//setRoot("adminMainPage", 600, 400)

		// get a handle to the stage
		Stage stage = (Stage) confirmButton.getScene().getWindow();
		// do what you have to do
		stage.close();
	}

	public void cancel() throws IOException {
		// get a handle to the stage
		Stage stage = (Stage) confirmButton.getScene().getWindow();
		// do what you have to do
		stage.close();
	}

	public Availability getData() {
		Optional<Vaccine> vaccine = vaccineWrapper.getVaccines().stream().filter(
						vaccine1 -> vaccine1.getVaccineName().equals(vaccineMenuButton.getText())
		).findFirst();

		return new Availability (
				new IdAvailability(clinicNameField.getText()),
				startDateField.getValue(),
				endDateField.getValue(),
				LocalTime.parse(startHourField.getText()),
				LocalTime.parse(endHourField.getText()),
				vaccine.get()
		);
	}

	public void confirm() throws IOException {
		if (isValidData()) {
			Availability availability = getData();
			RequestMaker.sendPOST(AVAILABILITY_ENDPOINT,availability);
			goBack();
		} else
			newWindow("popup", 300, 200);
	}
}
