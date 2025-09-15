package sprites;
import interfaces.Sprite;

public class SpaceShip implements Sprite {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private DrawSurface drawSurface;

    public SpaceShip(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public void moveLeft() {
        if (x - speed >= 0) {
            x -= speed;
        }
    }

    public void moveRight(int screenWidth) {
        if (x + width + speed <= screenWidth) {
            x += speed;
        }
    }

    public void shoot() {
        // Logic for shooting a shot
        Shot shot = new Shot(x + width / 2, y, 5); // Example shot creation
        // Add shot to the game environment
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.BLUE);
        d.fillRectangle(x, y, width, height);
    }

    @Override
    public void timePassed() {
        // Update spaceship state if needed
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