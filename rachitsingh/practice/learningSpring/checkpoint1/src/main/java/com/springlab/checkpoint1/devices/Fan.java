package com.springlab.checkpoint1.devices;

public class Fan implements Device {
	public void turnOn() {
		System.out.println("Fan turned ON");
	}

	public void turnOff() {
		System.out.println("Fan turned OFF");
	}
}
