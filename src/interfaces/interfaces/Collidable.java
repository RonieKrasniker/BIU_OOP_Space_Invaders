package interfaces;

public interface Collidable {
    Rectangle getCollisionRectangle();
    void hit(Ball hitter);
}