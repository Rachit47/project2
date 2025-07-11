package com.springlab.checkpoint1.devices;

public class Light implements Device {
	public void turnOn() {
		System.out.println("Light turned ON");
	}

	public void turnOff() {
		System.out.println("Light turned OFF");
	}
}
