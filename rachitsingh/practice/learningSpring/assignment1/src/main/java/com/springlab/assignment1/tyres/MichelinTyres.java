package com.springlab.assignment1.tyres;

import org.springframework.stereotype.Component;

@Component("michelinTyres")
public class MichelinTyres implements Tyres{
	 @Override
	    public void rotate() {
	        System.out.println("MichelinTyres: Your journey, enhanced by Michelin quality....");
	    }
}
