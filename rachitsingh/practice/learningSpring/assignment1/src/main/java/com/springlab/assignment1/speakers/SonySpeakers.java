package com.springlab.assignment1.speakers;

import org.springframework.stereotype.Component;

@Component("sonySpeakers")
public class SonySpeakers implements Speakers {
	@Override
	public void makeSound() {
		System.out.println("SonySpeakers: Get ready for premium SONY audio....");
	}
}
