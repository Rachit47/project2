package com.springlab.checkpoint4.IndianNavalCombatSystem.weapons;

import org.springframework.stereotype.Component;

@Component("akash")
public class Akash implements Missile {
    @Override
    public void launch() {
        System.out.println("Initiating launch sequence, Akash – Air Defence activated!");
    }
}
