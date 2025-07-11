package com.springlab.checkpoint3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.config.AppConfig;
import com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.service.DispatchService;
import com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.service.InventoryService;

public class App 
{
    public static void main( String[] args )
    {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    	
    	DispatchService dispatcher = context.getBean(DispatchService.class);
    	System.out.println();
    	dispatcher.prepareShipment();
    	
    	InventoryService inventory1 = context.getBean(InventoryService.class);
    	
    	InventoryService inventory2 = dispatcher.getInventory();
    	
    	System.out.println("\nAre both inventory beans (inventory1, inventory2) same ?");
    	System.out.println("Answer: " + (inventory1 == inventory2));
    	
    	AppConfig config = context.getBean(AppConfig.class);
    	System.out.println("Proxied version of AppConfig class used by Spring: " + config.getClass());
    }
}
