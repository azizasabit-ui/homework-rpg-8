package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.PoisonState;
import com.narxoz.rpg.state.StunState;
import java.util.List;
import java.util.Random;

public class TrapFloor extends TowerFloor {
    private final String floorName;
    private Random random = new Random();
    
    public TrapFloor(String name) {
        this.floorName = name;
    }
    
    public TrapFloor(String name, long seed) {
        this.floorName = name;
        this.random = new Random(seed);
    }
    
    @Override
    protected String getFloorName() {
        return floorName;
    }
    
    @Override
    protected void setup(List<Hero> party) {
        System.out.println("\n⚠️ Trap Setup: Hidden dangers everywhere!");
        System.out.println("  The floor is filled with poisonous darts and stunning gas!");
    }
    
    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("\n🎲 TRIGGERING TRAPS!");
        int totalDamage = 0;
        
        for (Hero hero : party) {
            if (!hero.isAlive()) continue;
            
            int trapType = random.nextInt(3);
            switch(trapType) {
                case 0:
                    int poisonDamage = 15;
                    System.out.println("  ☠️ " + hero.getName() + " triggers a poison dart trap! Takes " + 
                                     poisonDamage + " damage and gets poisoned!");
                    hero.takeDamage(poisonDamage);
                    totalDamage += poisonDamage;
                    if (hero.isAlive()) {
                        hero.setState(new PoisonState());
                    }
                    break;
                case 1:
                    System.out.println("  ⚡ " + hero.getName() + " triggers a stunning gas trap! Gets stunned!");
                    hero.setState(new StunState());
                    break;
                default:
                    int spikeDamage = 12;
                    System.out.println("  🗡️ " + hero.getName() + " triggers a spike pit! Takes " + 
                                     spikeDamage + " damage!");
                    hero.takeDamage(spikeDamage);
                    totalDamage += spikeDamage;
                    break;
            }
        }
        
        boolean cleared = party.stream().anyMatch(Hero::isAlive);
        System.out.println("\n" + (cleared ? "🎉 Trap floor survived!" : "💀 All heroes perished in the traps!"));
        
        return new FloorResult(cleared, totalDamage, "Traps triggered and survived");
    }
    
    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("\n🔑 LOOT: Found antidotes and healing potions! Each hero heals 15 HP!");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(15);
                }
            }
        }
    }
    
    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return result.isCleared(); 
    }
}