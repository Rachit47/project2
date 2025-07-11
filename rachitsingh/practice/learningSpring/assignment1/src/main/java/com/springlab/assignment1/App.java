package com.springlab.assignment1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springlab.assignment1.corebeans.Person;
import com.springlab.assignment1.config.AppConfig;

public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Person person = context.getBean(Person.class);
        person.useVehicle();
//        for (String name : context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
        context.close();
    }
}
