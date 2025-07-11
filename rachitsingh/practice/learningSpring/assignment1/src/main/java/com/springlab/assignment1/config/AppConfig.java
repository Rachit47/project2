package com.springlab.assignment1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


//@ComponentScan(basePackages = { "com.springlab.assignment1", "com.springlab.assignment1.corebeans.Person",
//		"com.springlab.assignment1.service", "com.springlab.assignment1.speakers", "com.springlab.assignment1.tyres" })
@Configuration
@ComponentScan(basePackages = "com.springlab.assignment1")
public class AppConfig {
}
