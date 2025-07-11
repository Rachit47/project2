package com.springlab.checkpoint5.SmartOrganicFarm.service;

import com.springlab.checkpoint5.SmartOrganicFarm.components.PlotManager;
import com.springlab.checkpoint5.SmartOrganicFarm.components.PlotTracker;
import com.springlab.checkpoint5.SmartOrganicFarm.model.CropType;

public class PlannerService {
	private final FarmService farmService;
	private final PlotManager plotManager;
	private final PlotTracker plotTracker;

	public PlannerService(FarmService farmService, PlotManager plotManager, PlotTracker plotTracker) {
		this.farmService = farmService;
		this.plotManager = plotManager;
		this.plotTracker = plotTracker;
	}

	public void plan(String cropInput) {
		if (!CropType.isValid(cropInput)) {
			System.out.println("Invalid Crop: " + cropInput);
			return;
		}

		CropType crop = CropType.from(cropInput);

		plotManager.assignCrop(crop);

		int days = farmService.getDaysToHarvest(crop);
		String fertilizer = farmService.selectFertilizer();

		System.out.printf("Plot %s will grow %s for %d days using %s\n", plotManager.getPlotID(), crop, days,
				fertilizer);

		plotTracker.record(plotManager.getPlotID(), crop.name());

	}

	public PlotManager getPlotManager() {
		return plotManager;
	}
}
