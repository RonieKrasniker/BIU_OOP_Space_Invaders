package game;
// File: /space-invaders/space-invaders/src/game/GameLevel.java

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Collidable;
import interfaces.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GameLevel {
    private List<Sprite> sprites;
    private List<Collidable> collidables;
    private String background;
    private KeyboardSensor keyboardSensor;
    private boolean running;

    public GameLevel(KeyboardSensor keyboardSensor) {
        this.sprites = new ArrayList<>();
        this.collidables = new ArrayList<>();
        this.keyboardSensor = keyboardSensor;
        this.running = true;
    }

    public void initialize() {
        // Set up the background and other initial configurations for the level
        this.background = "space_background.png"; // Example background image
        // Add initial sprites and collidables
    }

    public void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
    }

    public void addCollidable(Collidable collidable) {
        this.collidables.add(collidable);
    }

    public void draw(DrawSurface d) {
        // Draw the background
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        // Draw all sprites
        for (Sprite sprite : sprites) {
            sprite.drawOn(d);
        }
    }

    public void update() {
        // Update all sprites
        for (Sprite sprite : sprites) {
            sprite.update();
        }
        // Check for collisions and handle them
    }

    public void run() {
        while (running) {
            // Handle user input
            if (keyboardSensor.isPressed("p")) {
                running = false; // Pause the game
            }
            // Update the game state
            update();
            // Draw the current state
            // Assuming a method to get the DrawSurface is available
            DrawSurface d = getDrawSurface();
            draw(d);
            // Show the drawn surface
            // Assuming a method to show the surface is available
            showDrawSurface(d);
        }
    }

    private DrawSurface getDrawSurface() {
        // Implementation to get the current DrawSurface
        return null; // Placeholder
    }

    private void showDrawSurface(DrawSurface d) {
        // Implementation to show the drawn surface
    }
}