package game;
// File: /space-invaders/space-invaders/src/game/GameLevel.java

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import animation.Animation;
import interfaces.Collidable;
import interfaces.Sprite;
import sprites.SpaceShip;
import sprites.Alien;
import sprites.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single level and implements the Animation interface.
 * Responsibilities include initializing the level, running main game logic,
 * keeping track of score and lives, and determining when the level is over.
 */
public class GameLevel implements Animation {
    private List<Sprite> sprites;
    private GameEnvironment environment;
    private KeyboardSensor keyboard;
    private boolean running;
    private SpaceShip spaceShip;
    private List<Alien> aliens;
    private int score;
    private int lives;
    
    public GameLevel(KeyboardSensor keyboard) {
        this.sprites = new ArrayList<>();
        this.environment = new GameEnvironment();
        this.keyboard = keyboard;
        this.running = false;
        this.aliens = new ArrayList<>();
        this.score = 0;
        this.lives = 3;
    }

    /**
     * Initialize the level: create the fleet of aliens, the shields, and the spaceship.
     */
    public void initialize() {
        // Create spaceship
        this.spaceShip = new SpaceShip(375, 550, 50, 30, 5);
        addSprite(spaceShip);
        
        // Create aliens fleet
        createAlienFleet();
        
        // Create shields
        createShields();
        
        this.running = true;
    }
    
    private void createAlienFleet() {
        // Create a 5x11 grid of aliens
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 11; col++) {
                int x = 50 + col * 60;
                int y = 50 + row * 40;
                Alien alien = new Alien(x, y, 40, 30, Color.GREEN);
                aliens.add(alien);
                addSprite(alien);
                addCollidable(alien);
            }
        }
    }
    
    private void createShields() {
        // Create 4 shields
        for (int i = 0; i < 4; i++) {
            int x = 100 + i * 150;
            int y = 450;
            // Create shield as multiple blocks
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 6; col++) {
                    Block block = new Block(x + col * 10, y + row * 10, 10, 10, Color.CYAN);
                    addSprite(block);
                    addCollidable(block);
                }
            }
        }
    }

    public void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
    }

    public void addCollidable(Collidable collidable) {
        this.environment.addCollidable(collidable);
    }
    
    public void removeSprite(Sprite sprite) {
        this.sprites.remove(sprite);
    }
    
    public void removeCollidable(Collidable collidable) {
        this.environment.removeCollidable(collidable);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw background
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        
        // Draw all sprites
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(d);
        }
        
        // Handle input
        handleInput();
        
        // Update all sprites
        for (Sprite sprite : this.sprites) {
            sprite.timePassed();
        }
        
        // Check for collisions
        // TODO: implement collision detection
        
        // Check win/lose conditions
        if (aliens.isEmpty()) {
            this.running = false; // Player wins
        }
        
        // Draw UI elements
        d.setColor(Color.WHITE);
        d.drawText(10, 20, "Score: " + score, 16);
        d.drawText(10, 40, "Lives: " + lives, 16);
    }
    
    private void handleInput() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            spaceShip.moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            spaceShip.moveRight(800); // screen width
        }
        if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            spaceShip.shoot();
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public boolean hasMoreEnemies() {
        return !aliens.isEmpty();
    }
    
    public void stop() {
        this.running = false;
    }
}