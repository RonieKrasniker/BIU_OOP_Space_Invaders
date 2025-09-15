package sprites;

import biuoop.DrawSurface;
import interfaces.Sprite;
import interfaces.Collidable;
import geometry.Rectangle;

import java.awt.Color;

/**
 * A single alien. Implements both Sprite and Collidable.
 */
public class Alien implements Sprite, Collidable {
    private double x;
    private double y;
    private double width;
    private double height;
    private Color color;
    private double speed;
    private boolean isAlive;

    public Alien(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = 1.0;
        this.isAlive = true;
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void drawOn(DrawSurface d) {
        if (isAlive) {
            d.setColor(color);
            d.fillRectangle((int) x, (int) y, (int) width, (int) height);
            d.setColor(Color.BLACK);
            d.drawRectangle((int) x, (int) y, (int) width, (int) height);
        }
    }

    @Override
    public void timePassed() {
        // Aliens move as a group, so individual movement is handled by the fleet
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void hit() {
        // Logic for handling hits - alien is destroyed
        this.isAlive = false;
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
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    public double getSpeed() {
        return speed;
    }
}