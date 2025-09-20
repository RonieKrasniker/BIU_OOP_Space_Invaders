package sprites;

import biuoop.DrawSurface;
import interfaces.Sprite;
import interfaces.Collidable;
import geometry.Rectangle;

import java.awt.Color;
import java.awt.Image;
import game.ImageManager;

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
    private Image image;
    private int imageIndex = -1; // optional mapping to alien frame
    private int bobOffset = 0;
    private boolean bobDown = true;

    /**
     * Construct an Alien.
     *
     * @param x      initial x
     * @param y      initial y
     * @param width  width for collisions/fallback
     * @param height height for collisions/fallback
     * @param color  fallback color if image not available
     */
    public Alien(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = 1.0;
        this.isAlive = true;
    }

    /**
     * Move alien by delta.
     *
     * @param dx delta x
     * @param dy delta y
     */
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Draw the alien sprite or a fallback rectangle.
     *
     * {@inheritDoc}
     */
    @Override
    public void drawOn(DrawSurface d) {
        if (isAlive) {
            if (image == null) {
                String path = "media/sprites/alien0.png";
                if (imageIndex >= 0 && imageIndex <= 4) {
                    path = "media/sprites/alien" + imageIndex + ".png";
                }
                image = ImageManager.getScaled(path, (int) width, (int) height);
            }
            if (image != null) {
                d.drawImage((int) x, (int) (y + bobOffset), image);
            } else {
                d.setColor(color);
                d.fillRectangle((int) x, (int) (y + bobOffset), (int) width, (int) height);
                d.setColor(Color.BLACK);
                d.drawRectangle((int) x, (int) (y + bobOffset), (int) width, (int) height);
            }
        }
    }

    /**
     * Update method. Aliens move as a group.
     *
     * {@inheritDoc}
     */
    @Override
    public void timePassed() {
        // Bob up/down for subtle animation
        if (bobDown) {
            bobOffset++;
            if (bobOffset > 3) { bobDown = false; }
        } else {
            bobOffset--;
            if (bobOffset < -3) { bobDown = true; }
        }
    }

    /**
     * Get alien collision rectangle.
     *
     * {@inheritDoc}
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Handle a hit and mark alien as dead.
     *
     * {@inheritDoc}
     */
    @Override
    public void hit() {
        // Logic for handling hits - alien is destroyed
        this.isAlive = false;
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
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return alive status
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Set alien speed.
     *
     * @param speed pixels per frame
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Choose image index 0..4 to pick alien frame.
     *
     * @param index alien image index
     */
    public void setImageIndex(int index) {
        this.imageIndex = index;
        this.image = null; // force reload with new index
    }
}