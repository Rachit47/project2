package com.springlab.checkpoint4.IndianNavalCombatSystem.weapons;

import org.springframework.stereotype.Component;

@Component("brahmos")
public class Brahmos implements Missile {
	@Override
	public void launch() {
		System.out.println("Initiating launch sequence, Brahmos - Supersonic precision strike!");
	}
}
