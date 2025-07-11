package com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.model;

public class Medicine {
	private String name;
	private Double price;
	private String expiry;

	public Medicine(String name, Double price, String expiry) {
		this.name = name;
		this.price = price;
		this.expiry = expiry;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getExpiry() {
		return expiry;
	}
	
	@Override
	public String toString() {
		 return name + " (Rs." + price + ", Expiry: " + expiry + ")";
	}
}
