
// File: /space-invaders/space-invaders/src/game/GameEnvironment.java
package game;

import java.util.ArrayList;
import java.util.List;
import interfaces.Collidable;
import geometry.Rectangle;

/**
 * A class to hold and manage all the Collidable objects.
 * A Shot object will use this class to check if it's about to hit anything.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Construct an empty game environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Add the given collidable to the environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Remove the given collidable from the environment.
     *
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Get all collidables in the environment.
     *
     * @return a list of all collidables
     */
    public List<Collidable> getCollidables() {
        return new ArrayList<>(this.collidables);
    }

    /**
     * Check for collision with the given rectangle.
     *
     * @param rect the rectangle to check
     * @return the collidable that was hit, or null if no collision
     */
    public Collidable getClosestCollision(Rectangle rect) {
        for (Collidable collidable : this.collidables) {
            if (collidable.getCollisionRectangle().intersects(rect)) {
                return collidable;
            }
        }
        return null;
    }
}