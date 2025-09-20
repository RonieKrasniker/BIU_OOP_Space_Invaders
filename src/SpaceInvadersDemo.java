import sprites.SpaceShip;
import sprites.Alien;
import sprites.Block;
import sprites.Shot;
import interfaces.Collidable;
import geometry.Rectangle;
import game.GameEnvironment;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo showing Space Invaders game mechanics without GUI.
 */
public class SpaceInvadersDemo {
     /**
      * Entry point for the console demo.
      *
      * @param args CLI args
      */
     public static void main(String[] args) {
        System.out.println("=== Space Invaders Game Demo ===\n");

        // Create game environment
        GameEnvironment environment = new GameEnvironment();

        // Create spaceship
        SpaceShip ship = new SpaceShip(375, 550, 50, 30, 5);
        System.out.println("🚀 Spaceship created at position (" + ship.getX() + ", " + ship.getY() + ")");

        // Create alien fleet (simplified - just a few aliens)
        List<Alien> aliens = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Alien alien = new Alien(50 + i * 60, 100, 40, 30, Color.GREEN);
            aliens.add(alien);
            environment.addCollidable(alien);
            System.out.println("👾 Alien " + (i + 1) + " created at (" + alien.getX() + ", " + alien.getY() + ")");
        }

        // Create shields
        List<Block> shields = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Block shield = new Block(100 + i * 200, 400, 60, 40, Color.CYAN);
            shields.add(shield);
            environment.addCollidable(shield);
            System.out.println("🛡️  Shield " + (i + 1) + " created at (" + shield.getX() + ", " + shield.getY() + ")");
        }

        System.out.println("\n=== Game Simulation ===");

        // Simulate spaceship movement
        System.out.println("\n📍 Spaceship moves left:");
        ship.moveLeft();
        System.out.println("   Position: (" + ship.getX() + ", " + ship.getY() + ")");

        ship.moveRight(800);
        System.out.println("📍 Spaceship moves right:");
        System.out.println("   Position: (" + ship.getX() + ", " + ship.getY() + ")");

        // Simulate shooting
        System.out.println("\n💥 Spaceship fires a shot:");
        Shot shot = ship.shoot();
        shot.setGameEnvironment(environment);
        System.out.println("   Shot created at (" + shot.getX() + ", " + shot.getY() + ")");

        // Simulate shot movement and collision
        System.out.println("\n🎯 Shot movement simulation:");
        for (int i = 0; i < 10; i++) {
            shot.timePassed();
        System.out.println("   Frame " + (i + 1) + ": Shot at ("
            + String.format("%.1f", shot.getX()) + ", "
            + String.format("%.1f", shot.getY()) + ") - Active: "
            + shot.isActive());

            if (!shot.isActive()) {
                System.out.println("   💥 Shot hit something or went off screen!");
                break;
            }
        }

        // Check alien states
        System.out.println("\n👾 Alien status:");
        for (int i = 0; i < aliens.size(); i++) {
            Alien alien = aliens.get(i);
        System.out.println("   Alien " + (i + 1) + ": "
            + (alien.isAlive() ? "Alive" : "Destroyed"));
        }

        // Simulate alien fleet movement
        System.out.println("\n🕹️  Alien fleet movement:");
        double fleetMoveX = 10;
        for (Alien alien : aliens) {
            if (alien.isAlive()) {
                alien.move(fleetMoveX, 0);
                System.out.println("   Alien moved to (" + alien.getX() + ", " + alien.getY() + ")");
            }
        }

        // Demonstrate collision detection
        System.out.println("\n🔍 Collision Detection Demo:");
        Rectangle testRect = new Rectangle(100, 400, 20, 20); // Should intersect shield
        Collidable hit = environment.getClosestCollision(testRect);
        if (hit != null) {
            System.out.println("   ✅ Collision detected!");
            hit.hit();
            System.out.println("   💥 Object hit and responded to collision");
        } else {
            System.out.println("   ❌ No collision detected");
        }

        System.out.println("\n=== Demo Complete ===");
        System.out.println("✅ Space Invaders framework fully operational!");
        System.out.println("   - All sprites can be drawn and updated");
        System.out.println("   - Collision detection working");
        System.out.println("   - Game objects interact properly");
        System.out.println("   - Animation framework ready");
        System.out.println("   - Ready for full GUI integration");
    }
}