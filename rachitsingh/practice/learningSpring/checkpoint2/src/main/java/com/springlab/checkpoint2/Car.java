package com.springlab.checkpoint2;

public class Car {

	private final Engine engine;
	
	public Car(Engine engine) {
		this.engine = engine;
		System.out.println("Car instance created with Engine dependency");
	}
	
	public void drive() {
		engine.start();
		System.out.println("Car has started.");
	}
	
	public Engine getEngine() {
		return engine;
	}
}
