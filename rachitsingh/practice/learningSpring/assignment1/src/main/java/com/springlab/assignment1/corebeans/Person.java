package com.springlab.assignment1.corebeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Person => Vehicle => Services => Components [Tyres, Speakers]

@Component
public class Person {
	private final Vehicle vehicle;
	
	@Autowired
	public Person(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public void useVehicle() {
		System.out.println("Person: Time to get this show on the road....");
		vehicle.startVehicle();
	}
}
