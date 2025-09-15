package sprites;

import biuoop.DrawSurface;
import interfaces.Collidable;
import interfaces.Sprite;
import geometry.Rectangle;

import java.awt.Color;

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

    public Block(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
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
        // Logic for when the block is hit (e.g., destroy block)
        // For now, we could change color or mark for removal
        this.color = Color.DARK_GRAY;
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
}