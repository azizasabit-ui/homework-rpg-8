package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.RegenerationState;
import java.util.ArrayList;
import java.util.List;

public class BossFloor extends TowerFloor {
    private final String floorName;
    private Monster boss;
    private int totalDamageTaken = 0;
    
    public BossFloor(String name, Monster boss) {
        this.floorName = name;
        this.boss = boss;
    }
    
    @Override
    protected String getFloorName() {
        return floorName;
    }
    
    @Override
    protected void announce() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   ⚠️  BOSS FLOOR: " + getFloorName() + " ⚠️   ║");
        System.out.println("║   THE FINAL CHALLENGE AWAITS!                 ║");
        System.out.println("╚════════════════════════════════════════════════╝");
    }
    
    @Override
    protected void setup(List<Hero> party) {
        System.out.println("\n👹 BOSS Encounter Setup:");
        System.out.println("  " + boss.getStatus());
        System.out.println("  The boss radiates immense power!");
        
        for (Hero hero : party) {
            if (hero.isAlive() && hero.getHp() < hero.getMaxHp() * 0.5) {
                System.out.println("  🌟 " + hero.getName() + " receives a regeneration blessing before the boss!");
                hero.setState(new RegenerationState());
            }
        }
    }
    
    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("\n👹 BOSS BATTLE START!");
        int round = 1;
        
        while (boss.isAlive() && party.stream().anyMatch(Hero::isAlive)) {
            System.out.println("\n╔════════════════════════════╗");
            System.out.println("║   BOSS ROUND " + round + "   ║");
            System.out.println("╚════════════════════════════╝");
            
            System.out.println("Heroes turn:");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.onTurnStart();
                    hero.attack(boss);
                    hero.onTurnEnd();
                    
                    if (boss.getHp() <= boss.getAttackPower() * 2 && boss.getHp() > 0) {
                        System.out.println("  😤 The boss enters berserk mode!");
                    }
                }
            }
            
            if (!boss.isAlive()) break;
            
            System.out.println("\nBoss turn:");
            int bossDamage = boss.getAttackPower();
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    System.out.println("  👹 " + boss.getName() + " attacks " + hero.getName() + "!");
                    hero.takeDamage(bossDamage);
                    totalDamageTaken += bossDamage;
                    
                    if (boss.getHp() < boss.getAttackPower()) {
                        System.out.println("  🔥 The boss's rage intensifies!");
                        if (hero.isAlive()) {
                            hero.setState(new BerserkState());
                        }
                    }
                }
            }
            
            round++;
        }
        
        boolean cleared = !boss.isAlive();
        if (cleared) {
            System.out.println("\n🎉 BOSS DEFEATED! The tower is conquered!");
        } else {
            System.out.println("\n💀 The boss has defeated the heroes!");
        }
        
        return new FloorResult(cleared, totalDamageTaken, 
                              cleared ? "Boss vanquished!" : "Defeated by the boss");
    }
    
    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("\n🏆 LEGENDARY LOOT:");
            System.out.println("  • 500 gold each!");
            System.out.println("  • Epic item: Dragon's Heart (max HP +20)");
            System.out.println("  • All heroes fully healed!");
            
            for (Hero hero : party) {
                if (hero.isAlive()) {
                   
                    int healAmount = hero.getMaxHp() - hero.getHp();
                    hero.heal(healAmount);
                }
            }
        }
    }
    
    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return result.isCleared(); 
    }
}