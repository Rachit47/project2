package com.springlab.checkpoint4.IndianNavalCombatSystem.model;

public class Warship {
	private String shipName;

	public Warship() {
	};

	public Warship(String shipName) {
		this.shipName = shipName;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		if (!shipName.isBlank()) {
			this.shipName = shipName;
		}
	}

	public void deployWarShip() {
		System.out.println("Deploying Naval Combat Vessel: " + shipName);
	}
}
