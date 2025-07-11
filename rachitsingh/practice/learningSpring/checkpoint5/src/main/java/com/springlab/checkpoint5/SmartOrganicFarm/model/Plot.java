package com.springlab.checkpoint5.SmartOrganicFarm.model;

public class Plot {
	private final String plotID;
	private final CropType crop;

	public Plot(String plotID, CropType crop) {
		this.plotID = plotID;
		this.crop = crop;
	}

	public String getPlotID() {
		return plotID;
	}

	public CropType getCrop() {
		return crop;
	}

	@Override
	public String toString() {
		return "Plot{" + "plotId='" + plotID + '\'' + ", crop=" + crop + '}';
	}
}
