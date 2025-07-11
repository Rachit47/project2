package com.springlab.assignment1.corebeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springlab.assignment1.service.VehicleServices;

@Component
public class Vehicle {
	private final VehicleServices services;

	@Autowired
	public Vehicle(VehicleServices services) {
		this.services = services;
	}

	public void startVehicle() {
		System.out.println("Vehicle: Vehicle ignition sequence initiated....");
		services.moveVehicle();
		services.playMusic();
	}
}
