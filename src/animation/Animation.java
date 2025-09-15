package animation;

import biuoop.DrawSurface;

/**
 * Interface representing a single screen or state (like the game itself, a pause screen, or a countdown).
 */
public interface Animation {
    /**
     * Process a single frame of the animation.
     * @param d the DrawSurface to draw on
     */
    void doOneFrame(DrawSurface d);
    
    /**
     * Check if the animation should stop.
     * @return true if the animation should stop, false otherwise
     */
    boolean shouldStop();
}