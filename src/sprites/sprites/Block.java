package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Collidable;
import interfaces.Sprite;

import java.awt.Color;

public class Block implements Collidable, Sprite {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;

    public Block(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle(this.x, this.y, this.width, this.height);
    }

    @Override
    public void timePassed() {
        // Blocks do not move, so this can remain empty
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    @Override
    public void hit() {
        // Logic for when the block is hit (e.g., change color or remove)
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}