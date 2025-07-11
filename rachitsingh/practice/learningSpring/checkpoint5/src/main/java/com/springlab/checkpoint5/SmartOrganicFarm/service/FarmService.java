package com.springlab.checkpoint5.SmartOrganicFarm.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springlab.checkpoint5.SmartOrganicFarm.model.CropType;

public class FarmService {

	private final List<String> fertilizers;
	private final Map<CropType, Integer> cropToDaysMap;

	public FarmService() {
		fertilizers = Arrays.asList("Organic Compost", "Cow Manure", "Biochar");
		cropToDaysMap = new HashMap<CropType, Integer>();
		cropToDaysMap.put(CropType.CORN, 90);
		cropToDaysMap.put(CropType.WHEAT, 120);
		cropToDaysMap.put(CropType.PADDY, 150);
		cropToDaysMap.put(CropType.SOY, 130);
	}

	public List<String> getFertilizers() {
		return fertilizers;
	}

	public boolean isValidCrop(String crop) {
		return CropType.isValid(crop);
	}

	public int getDaysToHarvest(CropType crop) {
		return cropToDaysMap.getOrDefault(crop, -1);
	}

	public String selectFertilizer() {
		return fertilizers.get((int) (Math.random() * fertilizers.size()));
	}
}
