package com.springlab.checkpoint4.IndianNavalCombatSystem.weapons;

import org.springframework.stereotype.Component;

@Component("nag")
public class Nag implements Missile {
    @Override
    public void launch() {
        System.out.println("Initiating launch sequence, Nag – Anti-tank target locked!");
    }
}
