package com.springlab.assignment1.tyres;

import org.springframework.stereotype.Component;

@Component("bridgeStoneTyres")
public class BridgeStoneTyres implements Tyres{
	 @Override
	    public void rotate() {
	        System.out.println("BridgeStoneTyres: Designed for durability, turning with Bridgestone quality....");
	    }
}
