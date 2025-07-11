package com.springlab.checkpoint1.controller;

import com.springlab.checkpoint1.devices.Device;

public class SmartHomeController {
	private Device device;
	
	// Constructor injection - here dependency 'device' has been declared as parameters in this class's constructor. 
	
	// When Spring needs to create an instance of SmartHomeController, it looks at the constructor, identifies 
	// Device as a dependency, and then resolves and injects an instance of Device into the constructor
	public SmartHomeController(Device device) {
		this.device = device;
	}
	
	public void activateDevice() {
		device.turnOn();
	}
	
	public void deactivateDevice() {
		device.turnOff();
	}
}
