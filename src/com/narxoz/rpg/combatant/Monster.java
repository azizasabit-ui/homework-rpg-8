package com.narxoz.rpg.combatant;

public class Monster {
    private final String name;
    private int hp;
    private final int attackPower;

    public Monster(String name, int hp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.attackPower = attackPower;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getAttackPower() { return attackPower; }
    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int amount) {
        int actualDamage = Math.min(amount, hp);
        hp = Math.max(0, hp - amount);
        System.out.println("     " + name + " takes " + actualDamage + " damage! (HP: " + hp + ")");
    }

    public void attack(Hero hero) {
        int damage = Math.max(1, attackPower - 2);
        System.out.println("     " + name + " attacks " + hero.getName() + " for " + damage + " damage!");
        hero.takeDamage(damage);
    }
    
    public String getStatus() {
        return String.format("%s [HP: %d, ATK: %d]", name, hp, attackPower);
    }
}