package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class StunState implements HeroState {
    private int duration = 1; 
    
    @Override
    public String getName() {
        return "Stunned";
    }
    
    @Override
    public int modifyOutgoingDamage(int basePower) {
        return 0;
    }
    
    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int)(rawDamage * 1.2); 
    }
    
    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("  ⚡ " + hero.getName() + " is stunned and cannot act!");
    }
    
    @Override
    public void onTurnEnd(Hero hero) {
        duration--;
        hero.incrementTurnCount();
        if (duration <= 0) {
            System.out.println("  ✨ " + hero.getName() + " recovers from stun!");
            hero.setState(new NormalState());
        }
    }
    
    @Override
    public boolean canAct() {
        return false; 
    }
}