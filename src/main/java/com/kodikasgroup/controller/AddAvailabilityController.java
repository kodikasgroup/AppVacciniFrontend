package com.kodikasgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodikasgroup.model.VaccineWrapper;
import com.kodikasgroup.utils.RequestMaker;
import com.kodikasgroup.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static com.kodikasgroup.App.newWindow;
import static com.kodikasgroup.App.setRoot;


// REGEX: https://stackoverflow.com/a/51177696
public class AddAvailabilityController {
	@FXML private MenuButton vaccineMenuButton;
	@FXML private TextField startHourField;
	@FXML private TextField endHourField;
	@FXML private DatePicker startDateField;
	@FXML private DatePicker endDateField;
	@FXML private TextField clinicNameField;
	@FXML private Button confirmButton;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static VaccineWrapper vaccineWrapper;
	private static final String VACCINE_ENDPOINT = "/vaccines";

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
			newWindow("popupNoVaccineAvailable", 300, 200);
			confirmButton.setDisable(true);
			vaccineMenuButton.setDisable(true);
		}
	}

	@FXML
	private void initialize() throws IOException {
		Utils.setDatePickerDateFormat(startDateField);
		Utils.setDatePickerDateFormat(endDateField);
		initializeVaccineMenuButton();
	}

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
