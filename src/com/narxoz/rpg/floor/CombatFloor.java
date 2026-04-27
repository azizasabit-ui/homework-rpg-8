package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.ArrayList;
import java.util.List;

public class CombatFloor extends TowerFloor {
    private final String floorName;
    private List<Monster> monsters;
    private int totalDamageTaken = 0;
    
    public CombatFloor(String name, List<Monster> monsters) {
        this.floorName = name;
        this.monsters = new ArrayList<>(monsters);
    }
    
    @Override
    protected String getFloorName() {
        return floorName;
    }
    
    @Override
    protected void setup(List<Hero> party) {
        System.out.println("\n⚔️ Combat Setup: " + monsters.size() + " monsters await!");
        for (Monster m : monsters) {
            System.out.println("  • " + m.getStatus());
        }
    }
    
    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("\n⚔️ BATTLE START!");
        int round = 1;
        
        while (!monsters.isEmpty() && party.stream().anyMatch(Hero::isAlive)) {
            System.out.println("\n--- Round " + round + " ---");
            
            // Heroes attack
            System.out.println("Heroes turn:");
            for (Hero hero : party) {
                if (hero.isAlive() && !monsters.isEmpty()) {
                    hero.onTurnStart();
                    Monster target = monsters.get(0);
                    hero.attack(target);
                    hero.onTurnEnd();
                    
                    if (!target.isAlive()) {
                        System.out.println("     ✓ " + target.getName() + " defeated!");
                        monsters.remove(target);
                    }
                }
            }
            
            if (monsters.isEmpty()) break;
            
           
            System.out.println("\nMonsters turn:");
            for (Monster monster : monsters) {
                List<Hero> aliveHeroes = party.stream().filter(Hero::isAlive).toList();
                if (!aliveHeroes.isEmpty()) {
                    Hero target = aliveHeroes.get(0);
                    monster.attack(target);
                }
            }
            
            round++;
            
            if (party.stream().noneMatch(Hero::isAlive)) {
                System.out.println("\n💀 All heroes have fallen!");
                return new FloorResult(false, totalDamageTaken, "Party wiped out!");
            }
        }
        
        System.out.println("\n🎉 Combat floor cleared in " + (round-1) + " rounds!");
        return new FloorResult(true, totalDamageTaken, "Monsters defeated!");
    }
    
    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("\n💰 LOOT: Each hero gains 50 gold and heals 20 HP!");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(20);
                }
            }
        }
    }
    
    @Override
    protected void announce() {
        super.announce();
        System.out.println("⚠️  Danger! Monsters lurk in the shadows!");
    }
}