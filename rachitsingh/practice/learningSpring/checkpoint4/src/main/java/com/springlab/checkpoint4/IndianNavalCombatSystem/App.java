package com.springlab.checkpoint4.IndianNavalCombatSystem;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springlab.checkpoint4.IndianNavalCombatSystem.config.AppConfig;
import com.springlab.checkpoint4.IndianNavalCombatSystem.service.WeaponControlUnit;

public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        WeaponControlUnit controlUnit = context.getBean(WeaponControlUnit.class);
        controlUnit.launchMissile();
        context.close();
    }
}
