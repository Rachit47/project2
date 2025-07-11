package com.springlab.checkpoint5.SmartOrganicFarm.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlotTracker {
	private final List<String> records = new ArrayList<>();

	public void record(String plotID, String crop) {
		String ackMessage = "Plot " + plotID + " has been planted  with " + crop;
		records.add(ackMessage);
		System.out.println("Tracker: " + ackMessage);
	}

	public List<String> getRecords() {
		return Collections.unmodifiableList(records);
	}
}
