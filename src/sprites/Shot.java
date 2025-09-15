package sprites;

import biuoop.DrawSurface;
import interfaces.Sprite;
import geometry.Rectangle;
import game.GameEnvironment;

import java.awt.Color;

/**
 * The projectile. Implements Sprite.
 * It moves in a straight line and is responsible for detecting collisions with Collidable objects.
 */
public class Shot implements Sprite {
    private double x;
    private double y;
    private double width;
    private double height;
    private double velocityY;
    private boolean isActive;
    private GameEnvironment environment;

    public Shot(double x, double y, double width, double height, double velocityY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityY = velocityY;
        this.isActive = true;
    }
    
    public void setGameEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }

    public void move() {
        this.y += velocityY; // Move the shot (up if velocityY is negative, down if positive)
        
        // Check screen boundaries
        if (y < 0 || y > 600) { // Assuming screen height is 600
            this.isActive = false;
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        if (isActive) {
            d.setColor(Color.YELLOW); 
            d.fillRectangle((int) x, (int) y, (int) width, (int) height);
        }
    }

    @Override
    public void timePassed() {
        if (isActive) {
            move();
            checkCollisions();
        }
    }
    
    private void checkCollisions() {
        if (environment != null) {
            Rectangle shotRect = getCollisionRectangle();
            interfaces.Collidable hit = environment.getClosestCollision(shotRect);
            if (hit != null) {
                hit.hit();
                this.isActive = false;
            }
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void deactivate() {
        this.isActive = false;
    }

    public Rectangle getCollisionRectangle() {
        return new Rectangle(x, y, width, height);
    }
}