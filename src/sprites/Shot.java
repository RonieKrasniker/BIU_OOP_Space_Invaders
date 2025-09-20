package sprites;

import biuoop.DrawSurface;
import interfaces.Sprite;
import geometry.Rectangle;
import game.GameEnvironment;

import java.awt.Color;
import java.awt.Image;
import game.ImageManager;

/**
 * The projectile. Implements Sprite.
 * It moves in a straight line and is responsible for detecting collisions with
 * Collidable objects.
 */
public class Shot implements Sprite {
    private double x;
    private double y;
    private double width;
    private double height;
    private double velocityY;
    private boolean isActive;
    private GameEnvironment environment;
    private Image image;
    private String imagePath = "media/sprites/blast.png"; // default player shot
    private boolean fromPlayer = true;

    /**
     * Construct a Shot.
     *
     * @param x          initial x
     * @param y          initial y
     * @param width      width for collisions/fallback
     * @param height     height for collisions/fallback
     * @param velocityY  vertical velocity (negative for upward)
     */
    public Shot(double x, double y, double width, double height, double velocityY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityY = velocityY;
        this.isActive = true;
    }

    /**
     * Wire the game environment to enable collision checks.
     *
     * @param environment GameEnvironment
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Move the shot by its velocity and deactivate when leaving screen.
     */
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
            if (image == null) {
                image = ImageManager.getScaled(imagePath, (int) width, (int) height);
            }
            if (image != null) {
                d.drawImage((int) x, (int) y, image);
            } else {
                d.setColor(Color.YELLOW);
                d.fillRectangle((int) x, (int) y, (int) width, (int) height);
            }
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
                // Simple team filtering: player shots should not hit the player; alien shots should not hit aliens
                boolean hitIsShip = (hit instanceof SpaceShip);
                boolean hitIsAlien = (hit instanceof Alien);
                if ((fromPlayer && !hitIsShip) || (!fromPlayer && !hitIsAlien)) {
                    hit.hit();
                    this.isActive = false;
                }
            }
        }
    }

    /**
     * @return x position
     */
    public double getX() {
        return x;
    }

    /**
     * @return y position
     */
    public double getY() {
        return y;
    }

    /**
     * @return whether shot is active
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Deactivate the shot (e.g., on hit).
     */
    public void deactivate() {
        this.isActive = false;
    }

    /**
     * @return collision rectangle of the shot
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Set the image path for this shot (e.g., alien shot vs player shot).
     *
     * @param path image path
     */
    public void setImagePath(String path) {
        this.imagePath = path;
        this.image = null; // reload lazily next draw
    }

    /**
     * Mark whether this shot originates from the player (true) or aliens (false).
     *
     * @param isFromPlayer flag
     */
    public void setFromPlayer(boolean isFromPlayer) {
        this.fromPlayer = isFromPlayer;
    }
    /**
     * @return true if this shot is from the player
     */
    public boolean isFromPlayer() {
        return this.fromPlayer;
    }
}