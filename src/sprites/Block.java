package sprites;

import biuoop.DrawSurface;
import interfaces.Collidable;
import interfaces.Sprite;
import geometry.Rectangle;

import java.awt.Color;
import java.awt.Image;
import game.ImageManager;

/**
 * A simple rectangular block. This is the basic building block for the shields.
 * Implements both Sprite and Collidable.
 */
public class Block implements Collidable, Sprite {
    private double x;
    private double y;
    private double width;
    private double height;
    private Color color;
    private boolean destroyed = false;
    private Image image; // optional texture

    /**
     * Construct a shield/block tile.
     *
     * @param x      x position
     * @param y      y position
     * @param width  tile width
     * @param height tile height
     * @param color  fallback color when image not available
     */
    public Block(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * Draw the block using an image if available, else fallback to colored rect.
     */
    @Override
    public void drawOn(DrawSurface d) {
        if (image == null) {
            // note: filename has typo (sheild.png) in assets
            image = ImageManager.getScaled(
                    "media/sprites/sheild.png",
                    (int) this.width,
                    (int) this.height);
        }
        if (image != null) {
            d.drawImage((int) this.x, (int) this.y, image);
        } else {
            d.setColor(this.color);
            d.fillRectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
            d.setColor(Color.BLACK);
            d.drawRectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
        }
    }

    @Override
    public void timePassed() {
        // Blocks do not move or change over time
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    @Override
    public void hit() {
        // Destroy block on hit
        this.destroyed = true;
    }

    /**
     * @return true if this block has been destroyed
     */
    public boolean isDestroyed() {
        return this.destroyed;
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
}