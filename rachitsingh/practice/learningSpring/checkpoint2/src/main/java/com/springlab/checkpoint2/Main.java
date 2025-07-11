package com.springlab.checkpoint2;


import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
			// this Engine instance is stored and can be reused.
			Engine engine1 = context.getBean(Engine.class);
			
			// now Spring is going to create a Car bean and injects the above already created Engine Bean dependency
			Car car1 = context.getBean(Car.class);
			
			// now engine2 is also going to refer the same Bean instance of Engine class returned by getEngine() method of Car.
			Engine engine2 = car1.getEngine();
			
			// both engine1 and engine2 instances are the same object or refer to same singleton Bean instance
			System.out.println(engine1 == engine2);
			
			AppConfig config = context.getBean(AppConfig.class);
			System.out.println(config.getClass());
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}
}
