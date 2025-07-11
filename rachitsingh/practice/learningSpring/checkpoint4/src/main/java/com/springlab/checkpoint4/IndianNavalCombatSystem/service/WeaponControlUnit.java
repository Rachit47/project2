package com.springlab.checkpoint4.IndianNavalCombatSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.springlab.checkpoint4.IndianNavalCombatSystem.weapons.Missile;

@Component
public class WeaponControlUnit {
	
	
	private Missile missile;
	
	@Autowired
	public WeaponControlUnit(@Qualifier("brahmos")Missile missile) {
		this.missile = missile;
	}
	
	public void launchMissile() {
		missile.launch();
	}
}
