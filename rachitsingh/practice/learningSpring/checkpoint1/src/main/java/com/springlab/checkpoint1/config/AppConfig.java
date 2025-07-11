package com.springlab.checkpoint1.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springlab.checkpoint1.controller.SmartHomeController;
import com.springlab.checkpoint1.devices.Device;
import com.springlab.checkpoint1.devices.Fan;
import com.springlab.checkpoint1.devices.Light;
@Configuration
public class AppConfig {
	
	@Bean
	public Device light() {
		return new Light();
	}
	
	@Bean
	public Device fan() {
		return new Fan();
	}

	@Bean
	public SmartHomeController controller() {
		return new SmartHomeController(fan());
		
	}	
}
