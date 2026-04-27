package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;
import java.util.ArrayList;
import java.util.List;

public class TowerRunner {
    private List<TowerFloor> floors;
    private List<Hero> heroes;
    
    public TowerRunner(List<TowerFloor> floors, List<Hero> heroes) {
        this.floors = new ArrayList<>(floors);
        this.heroes = heroes;
    }
    
    public TowerRunResult runTower() {
        System.out.println("\n🏰═══════════════════════════════════════════════════════🏰");
        System.out.println("🏰            THE HAUNTED TOWER ASCENT BEGINS            🏰");
        System.out.println("🏰═══════════════════════════════════════════════════════🏰");
        
        int floorsCleared = 0;
        
        for (int i = 0; i < floors.size(); i++) {
            TowerFloor floor = floors.get(i);
            System.out.println("\n📜 Floor " + (i + 1) + " of " + floors.size());
            
            FloorResult result = floor.explore(heroes);
            
            if (result.isCleared()) {
                floorsCleared++;
                System.out.println("\n✅ Floor " + (i + 1) + " cleared!");
                System.out.println("   Result: " + result.getSummary());
            } else {
                System.out.println("\n❌ Tower climb failed on floor " + (i + 1) + "!");
                System.out.println("   " + result.getSummary());
                break;
            }
            
            if (heroes.stream().noneMatch(Hero::isAlive)) {
                System.out.println("\n💀 All heroes have perished! Tower climb ends.");
                break;
            }
        }
        
        boolean reachedTop = floorsCleared == floors.size() && heroes.stream().anyMatch(Hero::isAlive);
        int heroesSurviving = (int)heroes.stream().filter(Hero::isAlive).count();
        
        System.out.println("\n🏰═══════════════════════════════════════════════════════🏰");
        System.out.println("🏰                  TOWER CLIMB COMPLETE                 🏰");
        System.out.println("🏰═══════════════════════════════════════════════════════🏰");
        
        return new TowerRunResult(floorsCleared, heroesSurviving, reachedTop);
    }
}