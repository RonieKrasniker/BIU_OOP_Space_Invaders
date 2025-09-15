package sprites;

import biuoop.DrawSurface;
import interfaces.Sprite;
import geometry.Rectangle;

import java.awt.Color;

/**
 * The player's ship. Implements Sprite.
 * It needs methods to move left and right based on keyboard input and a method to fire a shot.
 */
public class SpaceShip implements Sprite {
    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;

    public SpaceShip(double x, double y, double width, double height, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public void moveLeft() {
        if (x - speed >= 0) {
            x -= speed;
        }
    }

    public void moveRight(double screenWidth) {
        if (x + width + speed <= screenWidth) {
            x += speed;
        }
    }

    public Shot shoot() {
        // Create a shot at the center of the spaceship
        double shotX = x + width / 2 - 2.5; // Center the 5-pixel wide shot
        double shotY = y - 5; // Start shot just above the ship
        return new Shot(shotX, shotY, 5, 10, -5); // Move upward with negative velocity
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLUE);
        d.fillRectangle((int) x, (int) y, (int) width, (int) height);
        d.setColor(Color.WHITE);
        d.drawRectangle((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void timePassed() {
        // Update spaceship state if needed
        // For now, spaceship doesn't have autonomous behavior
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}