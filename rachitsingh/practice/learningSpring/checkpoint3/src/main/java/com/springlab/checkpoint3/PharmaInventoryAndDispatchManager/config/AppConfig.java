package com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.model.Medicine;
import com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.service.DispatchService;
import com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.service.InventoryService;

@Configuration // indicating that AppConfig is the source of all Bean definitions
public class AppConfig {
	@Bean
	public Medicine montelukast() {
		return new Medicine("Montelukast 10 mg", 120.00, "2027-05");
	}
	
	@Bean
	public Medicine amoxicillin() {
		return new Medicine("Amoxicillin 500 mg", 80.26, "2026-10" );
	}
	
	@Bean
	public List<Medicine> medicines(){
		return Arrays.asList(montelukast(), amoxicillin());
	}
	
	@Bean
	public InventoryService inventoryService() {
		return new InventoryService(medicines());
	}
	
	@Bean
	public DispatchService dispatchService() {
		return new DispatchService(inventoryService());
	}
}
