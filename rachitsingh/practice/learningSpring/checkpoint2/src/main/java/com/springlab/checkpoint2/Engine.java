package com.springlab.checkpoint2;

public class Engine {
	private Integer power;
	
	public Engine() {
		System.out.println("Engine instance has been created.");
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public void start() {
		System.out.println("Engine has started.");
		
	}
}
