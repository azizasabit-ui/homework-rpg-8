package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonState implements HeroState {
    private int duration = 3; 
    private static final int POISON_DAMAGE = 8;
    
    @Override
    public String getName() {
        return "Poisoned";
    }
    
    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int)(basePower * 0.8);
    }
    
    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int)(rawDamage * 1.1); 
    }
    
    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("  ☠️ " + hero.getName() + " suffers " + POISON_DAMAGE + 
                          " poison damage! (Duration: " + duration + " turns remaining)");
        hero.takeDamage(POISON_DAMAGE);
    }
    
    @Override
    public void onTurnEnd(Hero hero) {
        duration--;
        hero.incrementTurnCount();
        if (duration <= 0) {
            System.out.println("  🌿 " + hero.getName() + " recovers from poison!");
            hero.setState(new NormalState());
        }
    }
    
    @Override
    public boolean canAct() {
        return true; 
    }
}