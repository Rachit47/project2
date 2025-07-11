package com.myrupeelog.dto;

public class CategoryStatsDTO {
	private String category;
	private Double percentage;

	public CategoryStatsDTO() {
	}

	public CategoryStatsDTO(String category, Double percentage) {
		this.setCategory(category);
		this.setPercentage(percentage);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
