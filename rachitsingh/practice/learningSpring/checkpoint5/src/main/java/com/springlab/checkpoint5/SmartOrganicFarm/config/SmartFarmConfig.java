package com.springlab.checkpoint5.SmartOrganicFarm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.springlab.checkpoint5.SmartOrganicFarm.components.PlotManager;
import com.springlab.checkpoint5.SmartOrganicFarm.components.PlotTracker;
import com.springlab.checkpoint5.SmartOrganicFarm.service.FarmService;
import com.springlab.checkpoint5.SmartOrganicFarm.service.PlannerService;

@Configuration
public class SmartFarmConfig {
	@Bean
	public FarmService farmService() {
		return new FarmService();
	}
	
	@Bean
	@Scope("prototype") // One plot manager per plot (new instance for every plot)
	public PlotManager plotManager() {
		return new PlotManager();
	}
	
	@Bean
	public PlotTracker plotTracker() {
		return new PlotTracker();
	}
	
	@Bean
	@Scope("prototype") // Spring will be notified to inject a bean of type PlotManager and will create a new one if it's a prototype scoped bean
	// similarly for FarmService, it will create a SingleTon scoped bean, and
	// for PlotTracker also it will create a SingleTon scoped Bean in the context
	public PlannerService plannerService() {
		return new PlannerService(farmService(), plotManager(), plotTracker());
	}
}
