package com.springlab.checkpoint1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springlab.checkpoint1.config.AppConfig;
import com.springlab.checkpoint1.controller.SmartHomeController;
public class App 
{
    public static void main( String[] args )
    {
       ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
       SmartHomeController controller = context.getBean(SmartHomeController.class);
       
       controller.activateDevice();
       controller.deactivateDevice();
    }
}
