package com.kodikasgroup.utils;

import com.kodikasgroup.model.Vaccine;

import java.util.ArrayList;
import java.util.List;

public class TempMemory {
	private static TempMemory INSTANCE;
	private List<Vaccine> vaccines = new ArrayList<>();
	private boolean fromVaccinationCampaignController = false;

	private TempMemory() {
	}

	public static TempMemory getINSTANCE() {
		if (INSTANCE == null) {
			INSTANCE = new TempMemory();
		}
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
