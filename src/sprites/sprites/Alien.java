package sprites;
import interfaces.Sprite;

public class Alien implements Sprite {
    private int x;
    private int y;
    private int width;
    private int height;
    private java.awt.Color color;
    private int speed;

    public Alien(int x, int y, int width, int height, java.awt.Color color, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = speed;
    }

    public void move() {
        this.x += speed;
        // Add logic for changing direction when hitting screen edges
    }

    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillRectangle(x, y, width, height);
    }

    public void timePassed() {
        move();
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

    public void hit() {
        // Logic for handling hits, e.g., reducing health or removing the alien
    }
}