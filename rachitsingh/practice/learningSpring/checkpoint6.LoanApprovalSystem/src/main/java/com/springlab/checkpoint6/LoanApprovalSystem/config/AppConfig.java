package com.springlab.checkpoint6.LoanApprovalSystem.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.springlab.checkpoint6.LoanApprovalSystem")
@EnableAspectJAutoProxy
public class AppConfig {

}
