package sprites;

import biuoop.DrawSurface;
import interfaces.Sprite;
import interfaces.Collidable;
import geometry.Rectangle;

import java.awt.Color;
import java.awt.Image;
import game.ImageManager;

/**
 * The player's ship. Implements Sprite.
 * It needs methods to move left and right based on keyboard input and a method
 * to fire a shot.
 */
public class SpaceShip implements Sprite, Collidable {
    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;
    private Image image;
    private boolean hitPending = false;

    /**
     * Construct a SpaceShip.
     *
     * @param x      initial x coordinate
     * @param y      initial y coordinate
     * @param width  ship width (for collisions/fallback drawing)
     * @param height ship height (for collisions/fallback drawing)
     * @param speed  movement speed in pixels per frame
     */
    public SpaceShip(double x, double y, double width, double height, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    /**
     * Move ship left within screen bounds.
     */
    public void moveLeft() {
        if (x - speed >= 0) {
            x -= speed;
        }
    }

    /**
     * Move ship right within given screen width.
     *
     * @param screenWidth the width of the screen
     */
    public void moveRight(double screenWidth) {
        if (x + width + speed <= screenWidth) {
            x += speed;
        }
    }

    /**
     * Fire a new shot from the ship's top center.
     *
     * @return a new Shot moving upward
     */
    public Shot shoot() {
        // Create a larger shot at the center of the spaceship
    double shotW = 15;
    double shotH = 30;
    double shotX = x + width / 2 - shotW / 2;
    double shotY = y - shotH - 2; // start just above the ship
    Shot s = new Shot(shotX, shotY, shotW, shotH, -9); // faster upward
        s.setFromPlayer(true);
        return s;
    }

    /** {@inheritDoc} */
    @Override
    public void drawOn(DrawSurface d) {
        if (image == null) {
            image = ImageManager.getScaled("media/sprites/spaceship.png", (int) width, (int) height);
        }
        if (image != null) {
            d.drawImage((int) x, (int) y, image);
        } else {
            d.setColor(Color.BLUE);
            d.fillRectangle((int) x, (int) y, (int) width, (int) height);
            d.setColor(Color.WHITE);
            d.drawRectangle((int) x, (int) y, (int) width, (int) height);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void timePassed() {
        // Update spaceship state if needed
        // For now, spaceship doesn't have autonomous behavior
    }

    /**
     * @return current x position
     */
    public double getX() {
        return x;
    }

    /**
     * @return current y position
     */
    public double getY() {
        return y;
    }

    /**
     * @return ship width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return ship height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Get the collision rectangle representing the ship.
     *
     * @return Rectangle of the ship
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    /** {@inheritDoc} */
    @Override
    public void hit() {
        this.hitPending = true;
    }

    /**
     * Consume the hit flag if set.
     *
     * @return true if a hit was pending, false otherwise
     */
    public boolean consumeHit() {
        if (this.hitPending) {
            this.hitPending = false;
            return true;
        }
        return false;
    }
}