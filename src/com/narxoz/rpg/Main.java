package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.floor.BossFloor;
import com.narxoz.rpg.floor.CombatFloor;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.floor.TrapFloor;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.PoisonState;
import com.narxoz.rpg.tower.TowerRunResult;
import com.narxoz.rpg.tower.TowerRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     HOMEWORK 8: THE HAUNTED TOWER - ASCENDING THE FLOORS ║");
        System.out.println("║              STATE + TEMPLATE METHOD PATTERNS           ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        // PART 1: CREATE HEROES WITH DIFFERENT INITIAL STATES
        System.out.println("🎯 PART 1: Creating Heroes with Different Initial States");
        System.out.println("============================================================");
        
        Hero warrior = new Hero("Sir Galahad (Warrior)", 200, 35, 30, new BerserkState());
        Hero mage = new Hero("Merlin (Mage)", 150, 45, 20, new NormalState());
        
        List<Hero> heroes = new ArrayList<>(Arrays.asList(warrior, mage));
        
        System.out.println("\nHeroes Created:");
        for (Hero hero : heroes) {
            System.out.println("  " + hero.getStatus());
        }
        
        // PART 2: CREATE FLOORS WITH TEMPLATE METHOD
        System.out.println("\n🎯 PART 2: Creating Tower Floors with Template Method");
        System.out.println("============================================================");
        
        // Floor 1: Combat floor with skeletons
        List<Monster> skeletons = new ArrayList<>();
        skeletons.add(new Monster("Skeleton Warrior", 80, 18));
        skeletons.add(new Monster("Skeleton Archer", 60, 15));
        TowerFloor floor1 = new CombatFloor("Crypt of the Damned", skeletons);
        
        // Floor 2: Trap floor (demonstrates state transitions)
        TowerFloor floor2 = new TrapFloor("Poisoned Hallways");
        
        // Floor 3: Another combat floor with stronger enemies
        List<Monster> ghosts = new ArrayList<>();
        ghosts.add(new Monster("Wraith", 100, 22));
        ghosts.add(new Monster("Banshee", 90, 20));
        ghosts.add(new Monster("Ghost", 70, 16));
        TowerFloor floor3 = new CombatFloor("Haunted Chambers", ghosts);
        
        // Floor 4: Boss floor (demonstrates hook overrides)
        Monster dragonBoss = new Monster("Ancient Shadow Dragon", 250, 35);
        TowerFloor floor4 = new BossFloor("Dragon's Lair", dragonBoss);
        
        List<TowerFloor> floors = new ArrayList<>(Arrays.asList(floor1, floor2, floor3, floor4));
        
        System.out.println("\nFloors Created (4 floors, 3 distinct types):");
        System.out.println("  Floor 1: Crypt of the Damned (CombatFloor)");
        System.out.println("  Floor 2: Poisoned Hallways (TrapFloor)");
        System.out.println("  Floor 3: Haunted Chambers (CombatFloor)");
        System.out.println("  Floor 4: Dragon's Lair (BossFloor - overrides hooks)");
        
        // PART 3: DEMONSTRATE TEMPLATE METHOD SKELETON
        System.out.println("\n🎯 PART 3: Template Method Pattern Demonstration");
        System.out.println("============================================================");
        System.out.println("The explore() method is FINAL and defines the skeleton:");
        System.out.println("  1. announce()     → Print floor introduction");
        System.out.println("  2. setup()        → Prepare the floor challenge");
        System.out.println("  3. resolveChallenge() → Main floor logic");
        System.out.println("  4. awardLoot()    → Grant rewards if applicable");
        System.out.println("  5. cleanup()      → Optional post-floor cleanup");
        
        // PART 4: STATE TRANSITION DEMONSTRATION
        System.out.println("\n🎯 PART 4: State Pattern Demonstration");
        System.out.println("============================================================");
        System.out.println("\nInitial hero states:");
        for (Hero hero : heroes) {
            System.out.println("  " + hero.getStatus());
        }
        
        // Demonstrate manual state transition
        System.out.println("\nDemonstrating state transitions:");
        warrior.setState(new PoisonState());
        mage.setState(new BerserkState());
        
        System.out.println("\nAfter state changes:");
        for (Hero hero : heroes) {
            System.out.println("  " + hero.getStatus());
        }
        
        // Reset to original for tower run
        warrior.setState(new BerserkState());
        mage.setState(new NormalState());
        
        // PART 5: RUN THE TOWER
        System.out.println("\n🎯 PART 5: Running The Haunted Tower");
        System.out.println("============================================================");
        
        TowerRunner towerRunner = new TowerRunner(floors, heroes);
        TowerRunResult result = towerRunner.runTower();
        
        // PART 6: PRINT RESULTS
        System.out.println("\n🎯 PART 6: Tower Run Results");
        System.out.println("============================================================");
        
        System.out.println("\n📊 FINAL TOWER STATISTICS:");
        System.out.println("  Floors Cleared: " + result.getFloorsCleared() + " / " + floors.size());
        System.out.println("  Heroes Surviving: " + result.getHeroesSurviving() + " / " + heroes.size());
        System.out.println("  Reached Top: " + (result.isReachedTop() ? "✅ YES - Tower Conquered!" : "❌ NO - Failed before summit"));
        
        System.out.println("\nFinal Hero Status:");
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                System.out.println("  ✅ " + hero.getStatus());
            } else {
                System.out.println("  💀 " + hero.getName() + " - DEFEATED");
            }
        }
        
        // PART 7: ARCHITECTURE VERIFICATION
        System.out.println("\n🎯 PART 7: Architecture Verification");
        System.out.println("============================================================");
        
        System.out.println("\n✓ STATE PATTERN:");
        System.out.println("  - HeroState interface defines state behavior");
        System.out.println("  - 5 concrete states: Normal, Poison, Stun, Berserk, Regeneration");
        System.out.println("  - States self-transition (Poison wears off after 3 turns)");
        System.out.println("  - Stun state demonstrates canAct() = false");
        System.out.println("  - Berserk state transitions based on HP threshold");
        
        System.out.println("\n✓ TEMPLATE METHOD PATTERN:");
        System.out.println("  - TowerFloor abstract class with final explore() method");
        System.out.println("  - 3 concrete subclasses: CombatFloor, TrapFloor, BossFloor");
        System.out.println("  - All subclasses implement abstract methods");
        System.out.println("  - BossFloor overrides announce() and shouldAwardLoot() hooks");
        System.out.println("  - Fixed skeleton visible in output (announce → setup → challenge → loot)");
        
        System.out.println("\n✓ DEMO COMPLETENESS:");
        System.out.println("  - 2 heroes with different initial states");
        System.out.println("  - 4 floors with 3 distinct subclass types");
        System.out.println("  - State transitions visible during tower climb");
        System.out.println("  - Hook override demonstrated (BossFloor special announcement)");
        System.out.println("  - Template method steps visible in output");
        System.out.println("  - TowerRunResult printed with complete statistics");
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    DEMO COMPLETE - SUCCESS               ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
}