package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class RegenerationState implements HeroState {
    private int duration = 2;
    private static final int HEAL_AMOUNT = 15;
    
    @Override
    public String getName() {
        return "Regenerating";
    }
    
    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int)(basePower * 0.9); // Slightly reduced damage while focusing on healing
    }
    
    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int)(rawDamage * 0.9); // Slightly reduced damage taken
    }
    
    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("  🌟 " + hero.getName() + " regenerates " + HEAL_AMOUNT + " HP!");
        hero.heal(HEAL_AMOUNT);
    }
    
    @Override
    public void onTurnEnd(Hero hero) {
        duration--;
        hero.incrementTurnCount();
        if (duration <= 0) {
            System.out.println("  ✨ " + hero.getName() + "'s regeneration effect ends!");
            hero.setState(new NormalState());
        }
    }
    
    @Override
    public boolean canAct() {
        return true;
    }
}