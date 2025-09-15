package sprites;
import interfaces.Sprite;

public class Shot implements Sprite {
    private int x;
    private int y;
    private int speed;
    private int width;
    private int height;

    public Shot(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = 5; // Width of the shot
        this.height = 10; // Height of the shot
    }

    public void move() {
        this.y -= speed; // Move the shot upwards
    }

    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.YELLOW); // Set the color of the shot
        d.fillRectangle(x, y, width, height); // Draw the shot
    }

    public void timePassed() {
        move(); // Update the position of the shot
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getCollisionRectangle() {
        return new Rectangle(x, y, width, height); // Return the collision rectangle
    }

    public void hit() {
        // Logic for when the shot hits an object (e.g., alien or block)
    }
}