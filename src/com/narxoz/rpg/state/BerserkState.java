package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {
    @Override
    public String getName() {
        return "Berserk";
    }
    
    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int)(basePower * 1.5);
    }
    
    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int)(rawDamage * 1.3); 
    }
    
    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("  😤 " + hero.getName() + " is in Berserk state - attacking fiercely!");
      
        double hpPercent = (double)hero.getHp() / hero.getMaxHp();
        if (hpPercent < 0.3) {
            System.out.println("  💪 " + hero.getName() + " is low on HP and becomes even more aggressive!");
        }
    }
    
    @Override
    public void onTurnEnd(Hero hero) {
        hero.incrementTurnCount();
        double hpPercent = (double)hero.getHp() / hero.getMaxHp();
        if (hpPercent < 0.15) {
            System.out.println("  🛡️ " + hero.getName() + " calms down from berserk state due to critical condition!");
            hero.setState(new NormalState());
        }
    }
    
    @Override
    public boolean canAct() {
        return true;
    }
}