package interfaces;

import geometry.Rectangle;

/**
 * An object that can be collided with (like aliens, shields, and the
 * spaceship).
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     *
     * @return the collision rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it.
     */
    void hit();
}