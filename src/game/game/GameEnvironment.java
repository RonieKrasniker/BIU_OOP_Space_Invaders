
// File: /space-invaders/space-invaders/src/game/GameEnvironment.java
package game;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import interfaces.Collidable;

public class GameEnvironment {
    private List<Collidable> collidables;

    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    public List<Collidable> getCollidables() {
        return new ArrayList<>(this.collidables);
    }

    public void checkCollisions(DrawSurface d) {
        // Logic to check for collisions between collidables
        // This method will iterate through collidables and check for collisions
        // with other game objects, updating their states accordingly.
    }
}