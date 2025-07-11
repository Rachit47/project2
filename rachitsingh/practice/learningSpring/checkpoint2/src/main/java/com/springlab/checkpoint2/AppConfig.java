package com.springlab.checkpoint2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ProxyBeanMethods = true")
public class AppConfig {
	@Bean
	public Engine engine() {
		System.out.println("Within @Bean engine()");
		return new Engine();
	}

	@Bean
	public Car car() {
		System.out.println("Within @Bean car()");
		return new Car(engine());
	}
}
