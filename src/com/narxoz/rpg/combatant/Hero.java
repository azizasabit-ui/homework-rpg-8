package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.NormalState;

public class Hero {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private HeroState state;
    private int turnsInState = 0;

    public Hero(String name, int hp, int attackPower, int defense) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.state = new NormalState();
    }
    
    public Hero(String name, int hp, int attackPower, int defense, HeroState initialState) {
        this(name, hp, attackPower, defense);
        this.state = initialState;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public boolean isAlive() { return hp > 0; }
    public HeroState getState() { return state; }
    public int getTurnsInState() { return turnsInState; }
    
    public void setState(HeroState newState) {
        System.out.println("  🔄 " + name + " transitions from " + state.getName() + 
                          " to " + newState.getName() + "!");
        this.state = newState;
        this.turnsInState = 0;
    }
    
    public void incrementTurnCount() {
        turnsInState++;
    }

    public void takeDamage(int amount) {
        int modifiedDamage = state.modifyIncomingDamage(amount);
        int actualDamage = Math.max(0, modifiedDamage);
        int oldHp = hp;
        hp = Math.max(0, hp - actualDamage);
        System.out.println("  💔 " + name + " takes " + actualDamage + " damage! (HP: " + hp + "/" + maxHp + ")");
    }

    public void heal(int amount) {
        int oldHp = hp;
        hp = Math.min(maxHp, hp + amount);
        int actualHeal = hp - oldHp;
        if (actualHeal > 0) {
            System.out.println("  💚 " + name + " heals for " + actualHeal + " HP! (HP: " + hp + "/" + maxHp + ")");
        }
    }
    
    public int calculateDamage() {
        return state.modifyOutgoingDamage(attackPower);
    }
    
    public void onTurnStart() {
        state.onTurnStart(this);
    }
    
    public void onTurnEnd() {
        state.onTurnEnd(this);
    }
    
    public boolean canAct() {
        return state.canAct();
    }
    
    public void attack(Monster monster) {
        if (canAct()) {
            int damage = calculateDamage();
            monster.takeDamage(damage);
            System.out.println("  ⚔️ " + name + " (" + state.getName() + ") attacks for " + damage + " damage!");
        } else {
            System.out.println("  😵 " + name + " (" + state.getName() + ") cannot act this turn!");
        }
    }
    
    public String getStatus() {
        return String.format("%s [HP: %d/%d, State: %s]", name, hp, maxHp, state.getName());
    }
}