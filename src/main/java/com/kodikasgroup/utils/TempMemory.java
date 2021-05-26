package com.kodikasgroup.utils;

import com.kodikasgroup.model.Vaccine;

import java.util.ArrayList;
import java.util.List;

public class TempMemory {
	private List<Vaccine> vaccines = new ArrayList<>();
	private boolean fromVaccinationCampaignController = false;
	private static final TempMemory INSTANCE = new TempMemory();

	private TempMemory(){}

	public static TempMemory getINSTANCE() {
		return INSTANCE;
	}

	public List<Vaccine> getVaccines() {
		return vaccines;
	}

	public void addVaccine(Vaccine vaccine) {
		vaccines.add(vaccine);
	}

	public void resetVaccines() {
		vaccines = new ArrayList<>();
	}

	public boolean isFromVaccinationCampaignController() {
		return fromVaccinationCampaignController;
	}

	public void setFromVaccinationCampaignController(boolean fromVaccinationCampaignController) {
		this.fromVaccinationCampaignController = fromVaccinationCampaignController;
	}
}
