package com.springlab.checkpoint5.SmartOrganicFarm.components;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.springlab.checkpoint5.SmartOrganicFarm.model.CropType;

public class PlotManager {
	private static final AtomicInteger counter = new AtomicInteger(1);
	private final String plotID;

	private CropType assignedCrop;
	
	public PlotManager() {
		this.plotID = "FARMPLOT00" + counter.getAndIncrement();
	}

	public void assignCrop(CropType crop) {
		this.assignedCrop = crop;
		System.out.println(crop + " has been assigned to the plot " + plotID);
	}

	public String getPlotID() {
		return plotID;
	}

	public CropType getAssignedCrop() {
		return assignedCrop;
	}
}
