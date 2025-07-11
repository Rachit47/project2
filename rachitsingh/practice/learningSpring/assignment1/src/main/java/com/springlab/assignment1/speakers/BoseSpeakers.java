package com.springlab.assignment1.speakers;

import org.springframework.stereotype.Component;

@Component("boseSpeakers")
public class BoseSpeakers implements Speakers {

	@Override
	public void makeSound() {
		System.out.println("BoseSpeakers: Get ready for an unforgettable audio experience with Bose....");
	}
}
