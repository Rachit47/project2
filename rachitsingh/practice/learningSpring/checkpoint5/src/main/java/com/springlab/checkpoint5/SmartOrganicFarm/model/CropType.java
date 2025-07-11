package com.springlab.checkpoint5.SmartOrganicFarm.model;

public enum CropType {
	CORN, WHEAT, SOY, PADDY;

	public static boolean isValid(String crop) {
		try {
			CropType.valueOf(crop.toUpperCase());
			return true;
		} catch (IllegalArgumentException exception) {
			return false;
		}
	}
	
	public static CropType from(String crop) {
		return CropType.valueOf(crop.toUpperCase());
	}
}
