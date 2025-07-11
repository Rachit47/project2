package com.springlab.assignment1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.springlab.assignment1.speakers.Speakers;
import com.springlab.assignment1.tyres.Tyres;

@Service
public class VehicleServices {
	private final Speakers speakers;
	private final Tyres tyres;
	
	
	@Autowired
	public VehicleServices(@Qualifier("boseSpeakers") Speakers speakers, @Qualifier("michelinTyres") Tyres tyres) {
		this.speakers = speakers;
		this.tyres = tyres;
	}
	
	public void playMusic() {
		speakers.makeSound();
	}
	
	public void moveVehicle() {
		tyres.rotate();
	}
}
